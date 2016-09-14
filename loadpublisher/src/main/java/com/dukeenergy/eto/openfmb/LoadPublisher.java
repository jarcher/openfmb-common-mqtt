/*
 * Licensed to Duke Energy Corporation (www.duke-energy.com) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. Duke Energy
 * Corporation licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.dukeenergy.eto.openfmb;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.xml.datatype.DatatypeConfigurationException;

import org.jasypt.properties.EncryptableProperties;
import org.openfmb.xsd._2015._12.openfmb.commonmodule.DateTimeInterval;
import org.openfmb.xsd._2015._12.openfmb.commonmodule.FlowDirectionKind;
import org.openfmb.xsd._2015._12.openfmb.commonmodule.Meter;
import org.openfmb.xsd._2015._12.openfmb.commonmodule.PhaseCodeKind;
import org.openfmb.xsd._2015._12.openfmb.commonmodule.PowerSystemResource;
import org.openfmb.xsd._2015._12.openfmb.commonmodule.Reading;
import org.openfmb.xsd._2015._12.openfmb.commonmodule.ReadingType;
import org.openfmb.xsd._2015._12.openfmb.commonmodule.UnitMultiplierKind;
import org.openfmb.xsd._2015._12.openfmb.commonmodule.UnitSymbolKind;
import org.openfmb.xsd._2015._12.openfmb.resourcemodule.ResourceReadingProfile;
import org.openfmb.xsd.converters.utilities.Common;

import openfmb.resourcemodule.abstracts.AbsResourceReadingProfileMqttPublisher;

/**
 * This Load Publisher class simulates a 24 hour commercial load. The scenario is
 * one of a factory that runs 2 full shifts 7AM until midnight and then a third
 * maintenance shift from midnight until 7AM. KW values are published at a defined
 * rate via the MQTT publisher on port 1883. The output can be monitored via the
 * Mosquitto_sub subscriber or via Wireshark.
 * 
 * LoadPublisher extends the abstract class AbsResourceReadingProfileMqttPublisher.
 * 
 * There are 2 configuration files used to drive the simulation:
 * LoadPublisher.log4j.properties defines the logging mechanism.
 * LoadPublisher.properties defines the broker location, the publish rate,
 * and other MQTT parameters.
 * 
 * The simulator uses real time to build and index into a 24 hour table. The current
 * hour and the next hour KW values are obtained, linear interpolation is performed
 * between the 2 values, and a random jitter is applied. The purpose is to provide
 * a reasonable 150 kW peak load curve for a simulated factory.
 * 
 * @author David Lawrence and Dwayne Bradley
 */
public class LoadPublisher extends AbsResourceReadingProfileMqttPublisher {

	private int [] kwLoadTable = null;
	
	private String logicalDeviceID = null;
	private String meterMRID = null;
	private String meterName = null;
	private String meterDesc = null;
	private String psrMRID = null;
	private String psrName = null;
	private String psrDesc = null;
	
	/**
	 * The retrieveResourceReadingProfile populates the ResourceReadingProfile
	 * data structure. It consists of reading, reading type, meter, and power
	 * system resource.
	 */
	@Override
	public ResourceReadingProfile retrieveResourceReadingProfile() {
		// 
		ResourceReadingProfile profile = new ResourceReadingProfile();
		
		// Reading Type is KW 3-phase
		ReadingType	type = new ReadingType();
		type.setFlowDirection(FlowDirectionKind.FORWARD);
		type.setMultiplier(UnitMultiplierKind.KILO);
		type.setName("KW");
		type.setPhases(PhaseCodeKind.ABC);
		type.setUnit(UnitSymbolKind.W);
				
		// Reading is pulled from LoadTable based on time of day
		Reading read = new Reading();
		byte[] quality = new byte[2];
		quality[0] = 0;				// Quality
		quality[1] = 0;
		read.setQualityFlag(quality);
		read.setReadingType(type);	// ReadingType
		read.setSource("Load1");	// Description is Load1
		DateTimeInterval timestamp = new DateTimeInterval();
		try
		{
			timestamp.setStart(Common.LongToXmlGregorianCalendar(Calendar.getInstance().getTimeInMillis()));
		}
		catch (DatatypeConfigurationException e)
		{
					
		}
		read.setTimePeriod(timestamp);	// Timestamp in milliseconds since 1970
				
		// Index into LoadTable with hour index and interpolate between the values
		Calendar cal = GregorianCalendar.getInstance();	// Use hours as LoadTable index and minutes for interpolation
		int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int nexthour = (hourOfDay +1) % 24;
		float kw = kwLoadTable[hourOfDay];
		kw = kw + Math.abs(kwLoadTable[nexthour] - kwLoadTable[hourOfDay]) * (float)minute / 60f;
				
		// Add jitter to the KW value +/- 1.00
		Random rn = new Random();
		float kwVARIANCE = 1.0f;
		kw = kw + (float)rn.nextGaussian() * kwVARIANCE;
		read.setValue(kw);
		profile.getReadings().add(read);
				
		// Finish up the Meter reading profile
		profile.setLogicalDeviceID(logicalDeviceID);
		profile.setTimestamp(timestamp.getStart());
				
		// Load up the Meter and the Power System Resource
		if (meterMRID != null && meterName != null && meterDesc != null)
		{
			Meter meter = new Meter();
			
			meter.setDescription(meterDesc);
			meter.setMRID(meterMRID);
			meter.setName(meterName);
			
			if (psrMRID != null && psrName != null && psrDesc != null)
			{
				PowerSystemResource power = new PowerSystemResource();
				power.setDescription(psrDesc);
				power.setMRID(psrMRID);
				power.setName(psrName);
				
				meter.setPowerSystemResource(power);
			}
			
			profile.setMeter(meter);
		}
		
		return profile;
						
	}
		
/**
 * main is the main entry point into the application. LoadPublisher builds the
 * ResourceReadingProfile with Load values and publishes the packet in XML.
 */
	public static void main(String[] args) {
		// Create an instance of LoadPublisher and run it
		LoadPublisher load = new LoadPublisher();
		load.run();

	}
	
	@Override
	protected boolean loadAppConfigProperties(EncryptableProperties prop)
	{
		if (!super.loadAppConfigProperties(prop))
		{
			return false;
		}
		
		// Start loading custom properties after this point.
		
		// #####################################################
		// BEGIN - 24 hour KW load table
		// #####################################################
		String csvLoadTable = prop.getProperty("kwLoadTable");
		if (csvLoadTable == null || csvLoadTable.length() == 0)
		{
			logger.error("Load table is missing from the properties file.");
			return false;
		}
		else
		{
			try
			{
				String[] tempLoadTable = csvLoadTable.split(",");
				if (tempLoadTable.length != 24)
				{
					logger.error("Load table property must contain exactly 24 elements to represent 24 hours per day.");
					return false;
				}
				
				kwLoadTable = new int[tempLoadTable.length];
				
				for (int i = 0; i < tempLoadTable.length; i++)
				{
					try
					{
						kwLoadTable[i] = Integer.parseInt(tempLoadTable[i]);
					}
					catch (NumberFormatException e)
					{
						logger.error("Load table value is invalid in position " + i + ".");
						return false;
					}
				}
				
			}
			catch (Exception e)
			{
				logger.error("There was a problem reading the load table vales from the properties file.", e);
				return false;
			}
		}
		// #####################################################
		// END - 24 hour KW load table
		// #####################################################
		
		logicalDeviceID = prop.getProperty("logicalDeviceID");
		if (logicalDeviceID == null || logicalDeviceID.length() == 0)
		{
			logger.error("logicalDeviceID value missing in properties file.");
			return false;
		}
		
		meterMRID = prop.getProperty("meterMRID");
		if (meterMRID != null && meterMRID.length() == 0)
		{
			meterMRID = null;
		}
		
		meterDesc = prop.getProperty("meterDesc");
		if (meterDesc != null && meterDesc.length() == 0)
		{
			meterDesc = null;
		}
		
		meterName = prop.getProperty("meterName");
		if (meterName != null && meterName.length() == 0)
		{
			meterName = null;
		}
		
		psrMRID = prop.getProperty("psrMRID");
		if (psrMRID != null && psrMRID.length() == 0)
		{
			psrMRID = null;;
		}
		
		psrDesc = prop.getProperty("psrDesc");
		if (psrDesc != null && psrDesc.length() == 0)
		{
			psrDesc = null;
		}
		
		psrName = prop.getProperty("psrName");
		if (psrName != null && psrName.length() == 0)
		{
			psrName = null;
		}
		
		logger.info(this.getClass().getSimpleName() + " specific properties:");
		logger.info("  kwLoadTable = " + csvLoadTable);
		logger.info("  logicalDeviceID = " + logicalDeviceID);
		logger.info("  meterMRID       = " + (meterMRID == null ? "<blank>" : meterMRID));
		logger.info("  meterName       = " + (meterName == null ? "<blank>" : meterName));
		logger.info("  meterDesc       = " + (meterDesc == null ? "<blank>" : meterDesc));
		logger.info("  psrMRID         = " + (psrMRID == null ? "<blank>" : psrMRID));
		logger.info("  psrName         = " + (psrName == null ? "<blank>" : psrName));
		logger.info("  psrDesc         = " + (psrDesc == null ? "<blank>" : psrDesc));
		
		return true;
	}
}

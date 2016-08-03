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

	private int [] LoadTable = {25,30,25,30,20,15,10,50,120,130,140,145,150,135,130,140,150,135,125,120,90,85,95,45};

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
		float kw = LoadTable[hourOfDay];
		kw = kw + Math.abs(LoadTable[nexthour] - LoadTable[hourOfDay]) * (float)minute / 60f;
				
		// Add jitter to the KW value +/- 1.00
		Random rn = new Random();
		float kwVARIANCE = 1.0f;
		kw = kw + (float)rn.nextGaussian() * kwVARIANCE;
		read.setValue(kw);
		profile.getReadings().add(read);
				
		// Finish up the Meter reading profile
		profile.setLogicalDeviceID("DEMO.MGRID.RESOURCE.1");
				
		// Load up the Meter and the Power System Resource
		Meter meter = new Meter();
		meter.setDescription("Load1");
		meter.setMRID("DEMO.MGRID.RESOURCE.1");
		meter.setName("Load1");
		PowerSystemResource power = new PowerSystemResource();
		power.setDescription("Load1");
		power.setMRID("DEMO.MGRID.RESOURCE.1");
		power.setName("Load1");
		profile.setMeter(meter);
		profile.setTimestamp(timestamp.getStart());
				
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

}

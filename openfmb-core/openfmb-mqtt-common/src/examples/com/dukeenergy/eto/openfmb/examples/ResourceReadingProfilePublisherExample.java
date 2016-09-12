/**
 * Copyright 2016 Duke Energy.
 *
 * Licensed to Duke Energy (www.duke-energy.com) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. Duke Energy
 * licenses this file to you under the Apache License, Version 2.0 (the
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
package com.dukeenergy.eto.openfmb.examples;

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

public class ResourceReadingProfilePublisherExample extends AbsResourceReadingProfileMqttPublisher
{

	public static void main(String[] args)
	{
		ResourceReadingProfilePublisherExample publisher = new ResourceReadingProfilePublisherExample();
		publisher.run();
	}
	
	@Override
	protected boolean loadAppConfigProperties(EncryptableProperties prop)
	{
		if (!super.loadAppConfigProperties(prop))
		{
			return false;
		}
		
		// Load additional values from the properties file here.
		
		return true;
	};

	@Override
	public ResourceReadingProfile retrieveResourceReadingProfile()
	{
		logger.debug("retrieveResourceReadingProfile ENTRY...");
		
		ResourceReadingProfile profile = new ResourceReadingProfile();
		
		profile.setLogicalDeviceID("METER-ID-1");
		try
		{
			profile.setTimestamp(Common.LongToXmlGregorianCalendar(System.currentTimeMillis()));
		}
		catch (DatatypeConfigurationException e)
		{
			// This really should never happen...but...
		}
		
		Meter meter = new Meter();
		profile.setMeter(meter);
		meter.setMRID("METER-MRID-1");
		meter.setName("Meter Name");
		meter.setDescription("Meter Description");
		
		PowerSystemResource psr = new PowerSystemResource();
		meter.setPowerSystemResource(psr);
		psr.setMRID("PSR-MRID-1");
		psr.setName("PSR Name");
		psr.setDescription("PSR Description");
		
		ReadingType readingType = new ReadingType();
		readingType.setName("KW on Phase A");
		readingType.setFlowDirection(FlowDirectionKind.TOTAL);
		readingType.setMultiplier(UnitMultiplierKind.KILO);
		readingType.setPhases(PhaseCodeKind.A);
		readingType.setUnit(UnitSymbolKind.W);
		
		DateTimeInterval dti = new DateTimeInterval();
		try
		{
			dti.setStart(Common.LongToXmlGregorianCalendar(System.currentTimeMillis()));
			dti.setEnd(Common.LongToXmlGregorianCalendar(System.currentTimeMillis()));
		}
		catch (DatatypeConfigurationException e)
		{
			
		}
		
		Reading reading = new Reading();
		reading.setReadingType(readingType);
		reading.setSource("Reading Source");
		reading.setTimePeriod(dti);
		
		float value = (new Random().nextFloat() * (242 - 238)) + 238;
		
		reading.setValue(value);
		
		profile.getReadings().add(reading);
		
		logger.debug("retrieveResourceReadingProfile EXIT...");
		return profile;
	}

}

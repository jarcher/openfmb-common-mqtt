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
package openfmb.shuntmodule.abstracts;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import org.openfmb.xsd._2016._04.openfmb.shuntmodule.ShuntStatusProfile;
import org.openfmb.xsd.converters.ShuntStatusProfileXsdConverter;

import openfmb.mqtt.common.AbsOpenFmbMqttPublisher;

public abstract class AbsShuntStatusProfileMqttPublisher extends AbsOpenFmbMqttPublisher
{
	@Override
	public void run()
	{
		// Use the DDS class name to create the MQTT topic.  Just simply replace
		// the "." class separators with "/" for MQTT.
		String t = ShuntStatusProfile.class.getName();
		int pos = t.lastIndexOf(".openfmb.");
		t = t.substring(pos + 1);
		
		this.initialize(t.replace(".", "/"));
		if (!this.isInitialized())
		{
			return;
		}
		
		while (!this.shutDown)
		{
			ShuntStatusProfile profile = retrieveShuntStatusProfile();
			
			if (profile == null)
			{
				logger.info("Could not retrieve ShuntStatusProfile data. Skipping...");
			}
			else
			{
				// Publish the message
				String message = null;
				try
				{
        			switch (this.mqttMessageFormat)
        			{
						case JSON:
							message = ShuntStatusProfileXsdConverter.convertPojoToJson(profile, mqttIsFormattedOutput);
							break;
							
						case XML:
						default:
	            			message = ShuntStatusProfileXsdConverter.convertPojoToXml(profile, mqttIsFormattedOutput);
							break;
        				
        			}
        			
        			if (message != null && message.length() > 0)
        			{
						logger.debug("Payload length: " + message.length());
						logger.debug("Payload:\n" + message);
						
						publishMessage(profile.getLogicalDeviceID(), message);
        			}
				}
				catch (DatatypeConfigurationException | JAXBException e)
				{
					logger.error("ShuntStatusProfileXsdConverter conversion error:", e);
				}
			}
			
			// Sleep for mqttPublishRate seconds and try to reconnect
			try
			{
				logger.debug("Sleeping for " + mqttPublishRate + " seconds before publishing next value...");
				Thread.sleep(mqttPublishRate * 1000);
			}
			catch (Exception e)
			{
				logger.fatal("Sleep interrupted while waiting to publish next value. Shutting down...", e);
				shutDown = true;
				return;
			}
		}
		
	}
	
	public abstract ShuntStatusProfile retrieveShuntStatusProfile();
}

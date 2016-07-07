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
package openfmb.regulatormodule.abstracts;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import org.openfmb.xsd._2016._05.openfmb.regulatormodule.RegulatorStatusProfile;
import org.openfmb.xsd.converters.RegulatorStatusProfileXsdConverter;

import openfmb.mqtt.common.AbsOpenFmbMqttPublisher;

public abstract class AbsRegulatorStatusProfileMqttPublisher extends AbsOpenFmbMqttPublisher
{
	@Override
	public void run()
	{
		// Use the DDS class name to create the MQTT topic.  Just simply replace
		// the "." class separators with "/" for MQTT.
		String t = RegulatorStatusProfile.class.getName();
		int pos = t.lastIndexOf(".openfmb.");
		t = t.substring(pos + 1);
		
		this.initialize(t.replace(".", "/"));
		if (!this.isInitialized())
		{
			return;
		}
		
		while (!this.shutDown)
		{
			RegulatorStatusProfile profile = retrieveRegulatorStatusProfile();
			
			if (profile == null)
			{
				logger.info("Could not retrieve RegulatorStatusProfile data. Skipping...");
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
							message = RegulatorStatusProfileXsdConverter.convertPojoToJson(profile, mqttIsFormattedOutput);
							break;
							
						case XML:
						default:
	            			message = RegulatorStatusProfileXsdConverter.convertPojoToXml(profile, mqttIsFormattedOutput);
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
					logger.error("RegulatorStatusProfileXsdConverter conversion error:", e);
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
	
	public abstract RegulatorStatusProfile retrieveRegulatorStatusProfile();
}

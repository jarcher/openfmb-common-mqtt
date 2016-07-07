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
package openfmb.mqtt.common;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.jasypt.properties.EncryptableProperties;

/**
 * 
 * @author Dwayne Bradley
 * @version 1.0
 *
 */
public abstract class AbsOpenFmbMqttSubscriber extends AbsOpenFmbMqttBase
{
	protected String[] mqttSubscriptionTopics = null;
	protected int mqttSubscriptionQos = -1;
	protected String[] mqttSubscriptionSubTopics = null;
	
	/**
	 * This method MUST be called before calling the implementing class' run() method.
	 * 
	 * @param topicNames Array of MQTT topics that will be subscribed to for data.
	 */
	protected void initialize(String[] topicNames)
	{
		mqttSubscriptionTopics = topicNames;
		
		initialize();
	}
	
	/**
	 * This method MUST be called before calling the implementing class' run() method.
	 * 
	 * @param topicName A single MQTT topic that will be subscribed to for data.
	 */
	protected void initialize(String topicName)
	{
		initialize(topicName, null);
	}
	
	/**
	 * This method MUST be called before calling the implementing class' run() method.
	 * 
	 * @param topicName A single MQTT topic that will be subscribed to for data.
	 * @param subTopicNames MQTT subtopics will be appended to topicName for subscriptions.
	 */
	protected void initialize(String topicName, String[] subTopicNames)
	{
		mqttSubscriptionTopics = new String[1];
		mqttSubscriptionTopics[0] = topicName;
		mqttSubscriptionSubTopics = subTopicNames;
		
		initialize();
	}
	
	/**
	 * This class MUST be overridden to initialize any target objects that are
	 * relevant for the implementing class.
	 */
	public abstract void initializeTarget() throws Exception;
	
	protected void startSubscbription()
		throws MqttSecurityException, MqttException
	{
		mqttSubscribe();
	}
	
	protected void connectionEstablished()
	{
		try
		{
			mqttSubscribe();
		}
		catch (MqttSecurityException e)
		{
			logger.fatal(e);
			this.shutDown = true;
			return;
		}
		catch (MqttException e)
		{
			logger.fatal(e);
			this.shutDown = true;
			return;
		}
	}
	
	/**
	 * Creates a subscription to the appropriate topic.  If the QOS (Quality of Service)
	 * property in the startup properties file is either 1 or 2, a durable/persistent 
	 * subscription is created.  This allow the application to be restarted and any pending
	 * messages in the broker will be transferred to the application.
	 */
	private void mqttSubscribe()
		throws MqttSecurityException, MqttException
	{
		if (mqttSubscriptionTopics != null && mqttSubscriptionTopics.length > 1)
		{
			try
			{
				int[] allQos = new int[mqttSubscriptionTopics.length];
				
				for (int i = 0; i < mqttSubscriptionTopics.length; i++)
				{
					allQos[i] = mqttSubscriptionQos;
				}
				
				mqttClient.subscribe(mqttSubscriptionTopics, allQos);
			}
			catch (MqttSecurityException e)
			{
				logger.fatal("MQTT security exception encountered. Shutting down...", e);
				throw e;
			}
			catch (MqttException e)
			{
				logger.error(e);
				throw e;
			}
		}
		else if (mqttSubscriptionTopics != null && mqttSubscriptionTopics.length == 1)
		{
			try
			{
				if (mqttSubscriptionSubTopics == null)
				{
					mqttClient.subscribe(
							mqttSubscriptionTopics[0] + "/#",
							mqttSubscriptionQos);
				}
				else
				{
					String[] allTopics = new String[mqttSubscriptionSubTopics.length];
					int[] allQos = new int[mqttSubscriptionSubTopics.length];
					
					for (int i = 0; i < mqttSubscriptionSubTopics.length; i++)
					{
						allTopics[i] = mqttSubscriptionTopics[0] + "/" + mqttSubscriptionSubTopics[i];
						allQos[i] = mqttSubscriptionQos;
					}
					
					mqttClient.subscribe(allTopics, allQos);
				}
			}
			catch (MqttSecurityException e)
			{
				logger.fatal("MQTT security exception encountered. Shutting down...", e);
				throw e;
			}
			catch (MqttException e)
			{
				logger.error(e);
				throw e;
			}
		}
	}
	
	/**
	 * This method is called when the message has been successfully
	 * delivered to the broker.
	 * 
	 * @param token
	 */
	@Override
    public void deliveryComplete(IMqttDeliveryToken token)
    {
	    // Nothing will happen here since this class only subscribes.
    }
	
	/**
	 * Abstract method that allows the inheriting class to read additional
	 * property values from the properties file.
	 * 
	 * @param prop EncryptableProperties object to read the properties file.
	 * @return true if all properties were read successfully.
	 */
	protected boolean loadAppConfigProperties(EncryptableProperties prop)
	{
		try
		{
			try
			{
				mqttSubscriptionQos = Integer.parseInt(prop.getProperty("mqttSubscriptionQos"));
				if (mqttSubscriptionQos < 0 || mqttSubscriptionQos > 2)
				{
					throw new Exception("mqttSubscriptionQos can only be 0, 1 or 2.");
				}
			}
			catch (NumberFormatException e)
			{
				throw new Exception("mqttSubscriptionQos is not a valid integer value.");
			}
			
			// mqttSubscriptionTopics will already be set from the initialize() method.
			if (mqttSubscriptionTopics == null || mqttSubscriptionTopics.length == 0)
			{
				throw new Exception("Invalid mqttSubscriptionTopics");
			}
			
			if (mqttSubscriptionSubTopics != null)
			{
				for (String subTopic : mqttSubscriptionSubTopics)
				{
					if (subTopic == null || subTopic.length() == 0)
					{
						throw new Exception("Invalid mqttSubscriptionSubTopic");
					}
				}
			}
			
			logger.info("  mqttSubscriptionQos = " + mqttSubscriptionQos);
			logger.info("  mqttSubscriptionTopics:");
			for (int i = 0; i < mqttSubscriptionTopics.length; i++)
			{
				logger.info("    [" + i + "] = " + mqttSubscriptionTopics[i]);
			}
			if (mqttSubscriptionSubTopics != null && mqttSubscriptionSubTopics.length > 0)
			{
				logger.info("  mqttSubscriptionSubTopics:");
				for (int i = 0; i < mqttSubscriptionSubTopics.length; i++)
				{
					logger.info("    [" + i + "] = " + mqttSubscriptionSubTopics[i]);
				}
			}
		}
		catch (Exception e)
		{
			logger.error(e);
			return false;
		}
		
		return true;
	}
}

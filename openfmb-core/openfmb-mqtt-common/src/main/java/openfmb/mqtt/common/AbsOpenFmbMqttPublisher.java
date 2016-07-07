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
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.jasypt.properties.EncryptableProperties;

/**
 * 
 * @author Dwayne Bradley
 * @version 1.0
 * 
 */
public abstract class AbsOpenFmbMqttPublisher extends AbsOpenFmbMqttBase
{
	static final int DEFAULT_PUBLISH_RATE = 60;
	
	protected String mqttPublishTopic = null;
	protected int mqttPublishQos = -1;
	protected int mqttPublishRate = -1;
	protected boolean mqttIsRetained = false;
	
	/**
	 * This method MUST be called before calling the implementing class' run() method.
	 * 
	 * @param topicName MQTT topic that will be subscribed to for data.
	 */
	protected void initialize(String topicName)
	{
		mqttPublishTopic = topicName;
		
		this.initialize();
	}
	
	/**
	 * This method is called when the message has been successfully
	 * delivered to the broker.
	 * 
	 * @param arg0
	 */
	@Override
    public void deliveryComplete(IMqttDeliveryToken token)
    {
	    logger.debug("Message delivered: Token = " + token.getMessageId());
    }
	
	public void publishMessage(String key, String message)
	{
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setQos(mqttPublishQos);
		mqttMessage.setPayload(message.getBytes());
		mqttMessage.setRetained(mqttIsRetained);
		
		try
		{
			MqttDeliveryToken token = mqttClient.getTopic(mqttPublishTopic + keyValidator(key)).publish(mqttMessage);
			logger.debug("Message published. Message ID=" + token.getMessageId());
			logger.debug("Message: " + message);
		}
		catch (MqttPersistenceException e)
		{
			logger.error(e);
		}
		catch (MqttException e)
		{
			logger.error(e);
		}
	}
	
	private String keyValidator(String key)
	{
		return key.replace(' ', '_')
				  .replace('#', '`')
				  .replace('+', '~')
				  .replace('/',  '^');
	}
	
	/**
	 * Abstract method that allows the inheriting class to read additional
	 * property values from the properties file.
	 * 
	 * @param prop EncryptableProperties object to read the properties file.
	 * 
	 * @return true if all properties were read successfully.
	 */
	protected boolean loadAppConfigProperties(EncryptableProperties prop)
	{
		try
		{
			try
			{
				mqttPublishQos = Integer.parseInt(prop.getProperty("mqttPublishQos"));
				if (mqttPublishQos < 0 || mqttPublishQos > 2)
				{
					throw new Exception("mqttPublishQos can only be 0, 1 or 2.");
				}
			}
			catch (NumberFormatException e)
			{
				throw new Exception("mqttPublishQos is not a valid integer value.");
			}
			
			// mqttPublishTopic will already be set from the initialize() method.
			if (mqttPublishTopic == null || mqttPublishTopic.length() == 0)
			{
				throw new Exception("Invalid mqttPublishTopic");
			}
			if (!mqttPublishTopic.endsWith("/"))
			{
				mqttPublishTopic += "/";
			}
			
			try
			{
				mqttPublishRate = Integer.parseInt(prop.getProperty("mqttPublishRate"));
				if (mqttPublishRate < 0)
				{
					logger.info("mqttPublishRate must be greater than 0 seconds. Defaulting to " + DEFAULT_PUBLISH_RATE + " seconds...");
					mqttPublishRate = DEFAULT_PUBLISH_RATE;
				}
			}
			catch (NumberFormatException e)
			{
				logger.info("mqttPublishRate is not a valid integer value. Defaulting to " + DEFAULT_PUBLISH_RATE + " seconds...");
				mqttPublishRate = DEFAULT_PUBLISH_RATE;
			}
			
			try
			{
				mqttIsRetained = Boolean.parseBoolean(prop.getProperty("mqttIsRetained"));
			}
			catch (Exception e)
			{
				mqttIsRetained = false;
			}
			
			logger.info("  mqttPublishTopic = " + mqttPublishTopic);
			logger.info("  mqttPublishQos = " + mqttPublishQos);
			logger.info("  mqttPublishRate = " + mqttPublishRate);
			logger.info("  mqttIsRetained = " + mqttIsRetained);
		}
		catch (Exception e)
		{
			logger.error(e);
			return false;
		}
		
		return true;
	}
	
	@Override
    public void messageArrived(String topic, MqttMessage message)
    	throws Exception
    {
		// Not subscribing to any topics in this application.
    }
	
	protected void connectionEstablished()
	{
		// Not subscribing so nothing to do here
	}
	
}

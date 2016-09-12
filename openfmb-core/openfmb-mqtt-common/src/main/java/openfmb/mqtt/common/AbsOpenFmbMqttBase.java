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

import java.io.FileInputStream;
import java.io.IOException;

import javax.net.ssl.SSLHandshakeException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.openfmb.xsd.converters.XsdConverterFormat;

/**
 * 
 * @author Dwayne Bradley
 * @version 1.0
 * 
 */
public abstract class AbsOpenFmbMqttBase implements MqttCallback, Runnable
{
	// log4j object
	protected Logger logger = null;
	
	private boolean initialized = false;
	
	//=====================================================
	// MQTT variables
	//=====================================================
	protected MqttClient mqttClient = null;
	protected MqttConnectOptions mqttClientOptions = null;
	
	protected boolean shutDown = false;
	
	protected String mqttClientId = null;
	protected String mqttBrokerUrl = null;
	protected String mqttCaCertPath = null;
	protected String mqttClientCertPath = null;
	protected String mqttClientKeyPath = null;
	protected String mqttClientKeyPassword = null;
	protected boolean isMqttBrokerSsl = false;
	protected String mqttSslProtocol = null;
	protected String mqttUsername = null;
	protected String mqttPassword = null;
	protected int mqttReconnectionDelayInterval = -1;
	protected XsdConverterFormat mqttMessageFormat = null;
	protected boolean mqttIsFormattedOutput = false;
	protected boolean mqttIsCleanSession = false;
	
	protected String propertiesFilePath = null;
	
	private void setPropertiesFilePath()
	{
		propertiesFilePath = System.getenv("OPENFMB_PROPERTIES_FILE_PATH");
		if (propertiesFilePath == null || propertiesFilePath.length() == 0)
		{
			propertiesFilePath = "";
		}
		
	}
	
	/**
	 * This method should be called first so the log4j logger can be created.
	 */
	protected void initializeLogger()
	{
		setPropertiesFilePath();
		
		// Initial the logger objects.
		logger = Logger.getLogger(this.getClass().getSimpleName());
		
		// Load the properties for the log4j loggers.
		
	    PropertyConfigurator.configure(propertiesFilePath + this.getClass().getSimpleName() + ".log4j.properties");
	}
	
//	/**
//	 * This method MUST be called before calling the implementing class' run() method.
//	 * 
//	 * @param topicName MQTT topic that will be subscribed to for data.
//	 */
//	protected void initialize(String topicName)
//	{
//		mqttPublishTopic = topicName;
//		
//		this.initialize();
//	}
	
	/**
	 * This method MUST be called before calling the implementing class' run() method.
	 */
	protected void initialize()
	{
		this.initializeLogger();
		
		try
		{
			initializeMQTT();
		}
		catch (Exception e)
		{
			logger.error(e);
			return;
		}
		
		initialized = true;
	}
	
	public boolean isInitialized()
	{
		return this.initialized;
	}
	
//	/**
//	 * Initializes the MQTT object base on the data located in the
//	 * properties file for the class.
//	 * 
//	 * @param topicName Topic to publish to.
//	 */
//	private void initializeMQTT(String topicName)
//		throws Exception
//	{
//		mqttPublishTopic = topicName;
//		
//		initializeMQTT();
//	}
	
	/**
	 * Initializes the MQTT object base on the data located in the
	 * properties file for the class.
	 */
	private void initializeMQTT()
		throws Exception
	{
	    // Load the application's startup properties.
	    if (!loadMqttStartupProperties())
	    {
	    	throw new Exception("Unable to load MQTT startup properties...");
	    }
	    
		// Connect to the MQTT broker and create the subscription.
		mqttConnect();
	}
	
	/**
	 * This method loads all of the application's startup properties from the
	 * appropriate properties file.
	 * 
	 * @return True if all properties are loaded and validated; false otherwise.
	 */
	private boolean loadMqttStartupProperties()
	{
		setPropertiesFilePath();
		
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setAlgorithm("PBEWithMD5AndDES");
		String encryptionKey = System.getenv("OPENFMB_PROPERTY_ENCRYPTION_KEY");
		if (encryptionKey == null)
		{
			encryptor.setPassword(this.getClass().getSimpleName());
		}
		else
		{
			encryptor.setPassword(encryptionKey);
		}
		
		// Load the properties for this application
		//Properties prop = new Properties();
		EncryptableProperties prop = new EncryptableProperties(encryptor);
		
		try
		{
			logger.debug("Loading application startup properties...");
			prop.load(new FileInputStream(propertiesFilePath + this.getClass().getSimpleName() + ".properties"));
			
			mqttClientId = prop.getProperty("mqttClientId");
			if (mqttClientId == null || mqttClientId.length() == 0)
			{
				throw new Exception("Invalid mqttClientId.");
			}
			
			mqttBrokerUrl = prop.getProperty("mqttBrokerUrl");
			if (mqttBrokerUrl == null || mqttBrokerUrl.length() == 0)
			{
				throw new Exception("Invalid mqttBrokerUrl");
			}
			
			isMqttBrokerSsl = (mqttBrokerUrl.startsWith("ssl"));
			
			// If a SSL connection is going to be established to the MQTT broker, the following
			// values are required.  Otherwise, they are not needed.
			if (isMqttBrokerSsl)
			{
				mqttCaCertPath = prop.getProperty("mqttCaCertPath");
				if (mqttCaCertPath == null || mqttCaCertPath.length() == 0)
				{
					throw new Exception("mqttCaCertPath is required for a SSL connection to the MQTT broker.");
				}
				
				mqttClientCertPath = prop.getProperty("mqttClientCertPath");
				if (mqttClientCertPath == null || mqttClientCertPath.length() == 0)
				{
					throw new Exception("mqttClientCertPath is required for a SSL connection to the MQTT broker.");
				}
				
				mqttClientKeyPath = prop.getProperty("mqttClientKeyPath");
				if (mqttClientKeyPath == null || mqttClientKeyPath.length() == 0)
				{
					throw new Exception("mqttClientKeyPath is required for a SSL connection to the MQTT broker.");
				}
				
				// It is Ok if the client key password is null.  Depends on how the
				// was generated.
				mqttClientKeyPassword = prop.getProperty("mqttClientKeyPassword");
				
				// Check to see if a specific TLS encryption version is specified.
				mqttSslProtocol = prop.getProperty("mqttSslProtocol");
				if (mqttSslProtocol != null)
				{
					switch (mqttSslProtocol)
					{
						case "SSL":
						case "SSLv2":
						case "SSLv3":
							throw new Exception("Non-TLS protocols are not accepted by this application.");
						case "TLS":
						case "TLSv1":
						case "TLSv1.1":
							logger.warn(mqttSslProtocol + " is an older SSL protocol standard. Not recommended but continuing...");
						case "TLSv1.2":
							// These are valid
							break;
						default:
							throw new Exception("Invalid SSL protocol selected.");
					}
				}
			}
			
			mqttUsername = prop.getProperty("mqttUsername");
			
			mqttPassword = prop.getProperty("mqttPassword");
			
			try
			{
				mqttReconnectionDelayInterval = Integer.parseInt(prop.getProperty("mqttReconnectionDelayInterval"));
				
				if (mqttReconnectionDelayInterval < 0)
				{
					throw new Exception("Invalid mqttReconnectionDelayInterval.");
				}
			}
			catch (NumberFormatException e)
			{
				throw new Exception("Invalid mqttReconnectionDelayInterval.");
			}
			
			try
			{
				mqttMessageFormat = XsdConverterFormat.valueOf(prop.getProperty("mqttMessageFormat"));
				if (mqttMessageFormat == null)
				{
					mqttMessageFormat = XsdConverterFormat.XML;
				}
			}
			catch (Exception e)
			{
				// Default to XML at the format.
				mqttMessageFormat = XsdConverterFormat.XML;
			}
			
			try
			{
				mqttIsFormattedOutput = Boolean.parseBoolean(prop.getProperty("mqttIsFormattedOutput"));
			}
			catch (Exception e)
			{
				mqttIsFormattedOutput = false;
			}
			
			try
			{
				mqttIsCleanSession = Boolean.parseBoolean(prop.getProperty("mqttIsCleanSession"));
			}
			catch (Exception e)
			{
				mqttIsCleanSession = false;
			}
			
			logger.debug("Loading application startup properties...FINISHED.");
			
			logger.info("Application startup properties...");
			logger.info("  mqttClientId = " + mqttClientId);
			logger.info("  mqttBrokerUrl = " + mqttBrokerUrl);
			logger.info("  isMqttBrokerSsl = " + isMqttBrokerSsl);
			if (isMqttBrokerSsl)
			{
				logger.info("  mqttCaCertPath = " + mqttCaCertPath);
				logger.info("  mqttClientCertPath = " + mqttClientCertPath);
				logger.info("  mqttClientKeyPath = " + mqttClientKeyPath);
				logger.info("  mqttClientKeyPassword = " + (mqttClientKeyPassword == null ? "<blank>" : "***************"));
			}
			if (mqttSslProtocol != null)
			{
				logger.info("  mqttSslProtocol = " + mqttSslProtocol);
			}
			logger.info("  mqttUsername = " + (mqttUsername == null ? "<blank>" : mqttUsername));
			logger.info("  mqttPassword = " + (mqttPassword == null ? "<blank>" : "***************"));
			logger.info("  mqttReconnectionDelayInterval = " + mqttReconnectionDelayInterval);
			logger.info("  mqttMessageFormat = " + mqttMessageFormat.name());
			logger.info("  mqttIsFormattedOutput = " + mqttIsFormattedOutput);
			logger.info("  mqttIsCleanSesion = " + mqttIsCleanSession);
		}
		catch (IOException e)
		{
			logger.fatal("Could not load application properties. Shutting down.", e);
			return false;
		}
		catch (Exception e)
		{
			logger.fatal("Invalid property value. Shutting down.", e);
			return false;
		}
		
		return loadAppConfigProperties(prop);
	}
	
	/**
	 * Establishes the connection to the MQTT broker and creates a subscription
	 * to the appropriate topic.  If the QOS (Quality of Service) property in
	 * the startup properties file is either 1 or 2, a durable/persistent subscription
	 * is created.  This allow the application to be restarted and any pending
	 * messages in the broker will be transferred to the application.
	 * 
	 * @throws Exception
	 */
	private void mqttConnect()
		throws Exception
	{
		if (mqttClient != null && mqttClient.isConnected())
		{
			mqttClient.disconnect();
		}
		
		if (mqttClient == null)
		{
			mqttClient = new MqttClient(mqttBrokerUrl, mqttClientId);
			mqttClient.setCallback(this);
		}
		
		if (mqttClientOptions == null)
		{
			mqttClientOptions = new MqttConnectOptions();
			mqttClientOptions.setCleanSession(mqttIsCleanSession);
			
			// Only include the user name and password if both are populated
			// in the startup properties file.
			if (mqttUsername != null && mqttUsername.length() > 0 &&
				mqttPassword != null && mqttPassword.length() > 0)
			{
				mqttClientOptions.setUserName(mqttUsername);
				mqttClientOptions.setPassword(mqttPassword.toCharArray());
			}
			
			if (isMqttBrokerSsl)
			{
				mqttClientOptions.setSocketFactory(
						SslUtil.getSslSocketFactory(
								mqttCaCertPath,
								mqttClientCertPath,
								mqttClientKeyPath,
								mqttClientKeyPassword));
			}
		}
		
		int attempt = 0;
		while (!mqttClient.isConnected())
		{
			if (shutDown)
			{
				return;
			}
			
			attempt++;
		    logger.debug("Attempting connection to MQTT broker...Attempt #" + attempt + "...");
		    
			try
			{
				logger.info("Connecting to MQTT broker at " + mqttBrokerUrl + " with Client ID \"" + mqttClientId + "\".");
				
				mqttClient.connect(mqttClientOptions);
				
				logger.info("MQTT broker connenction successful.");
				
				connectionEstablished();
			}
			catch (MqttSecurityException e)
			{
				logger.fatal("MQTT security exception encountered. Shutting down.", e);
				shutDown = true;
				throw e;
			}
			catch (MqttException e)
			{
				if (e.getCause() instanceof SSLHandshakeException)
				{
					shutDown = true;
				}
				
				if (shutDown)
				{
					throw e;
				}
				else
				{
					logger.fatal("MQTT broker connection failed.", e);
					
					// Sleep for mqttReconnectionDelayInterval seconds and try to reconnect
					try
					{
						logger.info("Sleeping for " + mqttReconnectionDelayInterval + " seconds before attempting MQTT reconnect.");
						Thread.sleep(mqttReconnectionDelayInterval * 1000);
					}
					catch (Exception se)
					{
						logger.fatal("Sleep interrupted while waiting for a reconnect to the MQTT broker. Shutting down.", se);
						shutDown = true;
						throw se;
					}
				}
			}
		}
	}
	
	/**
	 * If the connection to the MQTT broker is lost, this method is called
	 * through the MqttCallback interface.  The application will then attempt
	 * to reestablish this connection after waiting for the reconnection delay
	 * interval value from the startup properties file.
	 * 
	 * @param arg0
	 */
	@Override
    public void connectionLost(Throwable arg0)
    {
		logger.info("MQTT broker connection lost.", arg0);
		
		// Sleep for mqttReconnectionDelayInterval seconds and try to reconnect
		try
		{
			logger.info("Sleeping for " + mqttReconnectionDelayInterval + " seconds before attempting MQTT reconnect.");
			Thread.sleep(mqttReconnectionDelayInterval * 1000);
		}
		catch (Exception e)
		{
			logger.fatal("Sleep interrupted while waiting for a reconnect to the MQTT broker. Shutting down.", e);
			shutDown = true;
			return;
		}
		
		try
		{
			mqttConnect();
		}
		catch (Exception e)
		{
		}
    }
	
	/**
	 * Abstract method that allows the inheriting class to read additional
	 * property values from the properties file.
	 * 
	 * @param prop EncryptableProperties object to read the properties file.
	 * 
	 * @return true if all properties were read successfully.
	 */
	protected abstract boolean loadAppConfigProperties(EncryptableProperties prop);
	
	/**
	 * Abstract method that notifies the application that the connection to the MQTT
	 * broker was successfully established.
	 */
	protected abstract void connectionEstablished();
}

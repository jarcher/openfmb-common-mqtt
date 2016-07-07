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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

/**
 * This utility class is for handling SSL connections between MQTT message
 * brokers and client applications.
 * 
 * @author Dwayne Bradley
 */
public class SslUtil
{
	/**
	 * This method creates a SSLSocketFactory instance which can be passed to
	 * the Paho MQTT client connection to establish secure communications.  It
	 * utilizes the Bouncy Castle SSL library to read PEM encoded certificates
	 * and key files which normally cannot be read using the standard Java API's.
	 * Defaults to using TLSv1.2 protocol for the SSL context.
	 * 
	 * @param caCrtFile Fully qualified path to the PEM encoded Certificate Authority certificate file.
	 * @param crtFile Fully qualified path to the PEM encoded client certificate file.
	 * @param keyFile Fully qualified path to the PEM encoded client key file.
	 * @param password Password to the PEM encoded client key file.
	 * 
	 * @return SSLSocketFactory instance
	 * 
	 * @throws InvalidPathException
	 * @throws IOException
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws CertificateException 
	 * @throws UnrecoverableKeyException 
	 * @throws KeyManagementException 
	 * @throws Exception
	 */
	public static SSLSocketFactory getSslSocketFactory(
			final String caCrtFile, 
			final String crtFile, 
			final String keyFile, 
			final String password)
		throws
			InvalidPathException,
			IOException,
			KeyStoreException,
			NoSuchAlgorithmException,
			CertificateException,
			UnrecoverableKeyException,
			KeyManagementException,
			Exception
	{
		return getSslSocketFactory(caCrtFile, crtFile, keyFile, password, "TLSv1.2");
	}
	
	/**
	 * This method creates a SSLSocketFactory instance which can be passed to
	 * the Paho MQTT client connection to establish secure communications.  It
	 * utilizes the Bouncy Castle SSL library to read PEM encoded certificates
	 * and key files which normally cannot be read using the standard Java API's.
	 * 
	 * @param caCrtFile Fully qualified path to the PEM encoded Certificate Authority certificate file.
	 * @param crtFile Fully qualified path to the PEM encoded client certificate file.
	 * @param keyFile Fully qualified path to the PEM encoded client key file.
	 * @param password Password to the PEM encoded client key file.
	 * @param tlsVersion TLS protocol version to use. Valid values: TLS, TLSv1, TLSv1.1, TLSv1.2
	 * @see https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SSLContext
	 * 
	 * @return SSLSocketFactory instance
	 * 
	 * @throws InvalidPathException
	 * @throws IOException
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws CertificateException 
	 * @throws UnrecoverableKeyException 
	 * @throws KeyManagementException 
	 * @throws Exception
	 */
	public static SSLSocketFactory getSslSocketFactory(
			final String caCrtFile, 
			final String crtFile, 
			final String keyFile, 
			final String password,
			final String protocol)
		throws
			InvalidPathException,
			IOException,
			KeyStoreException,
			NoSuchAlgorithmException,
			CertificateException,
			UnrecoverableKeyException,
			KeyManagementException,
			Exception
	{
		Provider[] providers = Security.getProviders();
		if (providers == null || providers.length == 0)
		{
			Security.addProvider(new BouncyCastleProvider());
		}
		else
		{
			boolean found = false;
			
			for (int i = 0; i < providers.length; i++)
			{
				if (providers[i].getName().equalsIgnoreCase("BC"))
				{
					found = true;
					break;
				}
			}
			
			if (!found)
			{
				Security.addProvider(new BouncyCastleProvider());
				providers = Security.getProviders();
			}
		}
		
		// load CA certificate
		PEMParser parser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(caCrtFile)))));
		X509CertificateHolder caCert = (X509CertificateHolder) parser.readObject();
		parser.close();
 
		// load client certificate
		parser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(crtFile)))));
		X509CertificateHolder cert = (X509CertificateHolder) parser.readObject();
		parser.close();
 
		// load client private key
		parser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(keyFile)))));
		Object obj = parser.readObject();
		KeyPair key = null;
		JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
		if (obj instanceof PEMEncryptedKeyPair)
		{
			PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder().build(password.toCharArray());
			converter = new JcaPEMKeyConverter().setProvider("BC");
			key = converter.getKeyPair(((PEMEncryptedKeyPair) obj).decryptKeyPair(decProv));
		}
		else
		{
			key = converter.getKeyPair((PEMKeyPair) obj);
		}
		parser.close();
		
		JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
		certConverter.setProvider("BC");
		
		// CA certificate is used to authenticate server
		KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
		caKs.load(null, null);
		caKs.setCertificateEntry("ca-certificate", certConverter.getCertificate(caCert));
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(caKs);
 
		// Client key and certificates are sent to server so it can authenticate us
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(null, null);
		ks.setCertificateEntry("certificate", certConverter.getCertificate(cert));
		ks.setKeyEntry("private-key", key.getPrivate(), password.toCharArray(), new java.security.cert.Certificate[]{certConverter.getCertificate(cert)});
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, password.toCharArray());
 
		// Finally, create SSL socket factory
		SSLContext context = SSLContext.getInstance(protocol);
		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
 
		return context.getSocketFactory();
	}
}
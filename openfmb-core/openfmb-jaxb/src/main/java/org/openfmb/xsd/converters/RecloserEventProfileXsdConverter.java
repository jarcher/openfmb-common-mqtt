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
package org.openfmb.xsd.converters;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.openfmb.xsd._2015._12.openfmb.reclosermodule.ObjectFactory;
import org.openfmb.xsd._2015._12.openfmb.reclosermodule.RecloserEventProfile;

/**
 * This class handles all of the conversions between the XML message
 * formats, JSON message formats and POJO objects for the OpenFMB
 * RecloserEventProfile structure.
 * 
 * @author Dwayne Bradley
 * @version 1.0
 */
public class RecloserEventProfileXsdConverter
{
	private RecloserEventProfileXsdConverter()
	{
		// Just prevent anyone from creating an instance of this class.
	}
	
	/**
	 * This method takes a POJO RecloserEventProfile object and translates it 
	 * into its corresponding XML representation based on the XSD definition.
	 * 
	 * @param pojo POJO object
	 * 
	 * @return XML string representing the RecloserEventProfile pojo
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static String convertPojo(RecloserEventProfile pojo)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertPojoToXml(pojo);
	}
	
	/**
	 * This method takes a POJO RecloserEventProfile object and translates it 
	 * into its corresponding XML or JSON representation based on the XSD definition.
	 * 
	 * @param pojo POJO object
	 * @param format Convert the output to either XML or JSON
	 * 
	 * @return XML or JSON string representing the RecloserEventProfile pojo
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static String convertPojo(RecloserEventProfile pojo, XsdConverterFormat format)
			throws DatatypeConfigurationException, JAXBException
	{
		switch (format)
		{
			case XML:
				return convertPojoToXml(pojo);
			case JSON:
				return convertPojoToJson(pojo);
			default:
				return null;
		}
	}
	
	/**
	 * This method takes a POJO RecloserEventProfile object and translates it 
	 * into its corresponding XML representation based on the XSD definition.
	 * 
	 * @param pojo POJO object
	 * @param prettyPrintOutput Flag to indicate whether or not for format the returned string
	 * 
	 * @return XML string representing the RecloserEventProfile pojo
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static String convertPojo(RecloserEventProfile pojo, boolean prettyPrintOutput)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertPojoToXml(pojo, prettyPrintOutput);
	}
	
	/**
	 * This method takes a POJO RecloserEventProfile object and translates it 
	 * into its corresponding XML or JSON representation based on the XSD definition.
	 * 
	 * @param pojo POJO object
	 * @param format Convert the output to either XML or JSON
	 * @param prettyPrintOutput Flag to indicate whether or not to "pretty print" the returned string
	 * 
	 * @return XML string representing the RecloserEventProfile pojo
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static String convertPojo(RecloserEventProfile pojo, XsdConverterFormat format, boolean prettyPrintOutput)
			throws DatatypeConfigurationException, JAXBException
	{
		switch (format)
		{
			case XML:
				return convertPojoToXml(pojo, prettyPrintOutput);
			case JSON:
				return convertPojoToJson(pojo, prettyPrintOutput);
			default:
				return null;
		}
	}
	
	/**
	 * This method takes an XML representation of a RecloserEventProfile object
	 * and converts it into a POJO object.
	 * 
	 * @param input XML representation of a RecloserEventProfile object
	 * 
	 * @return POJO RecloserEventProfile object
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static RecloserEventProfile convertToPojo(String input)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertXmlToPojo(input);
	}
	
	/**
	 * This method takes an XML or JSON representation of a RecloserEventProfile object
	 * and converts it into a POJO object.
	 * 
	 * @param input XML representation of a RecloserEventProfile object
	 * @param format Indicates if the input string is either XML or JSON
	 * 
	 * @return POJO RecloserEventProfile object
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static RecloserEventProfile convertToPojo(String input, XsdConverterFormat format)
			throws DatatypeConfigurationException, JAXBException
	{
		switch (format)
		{
			case XML:
				return convertXmlToPojo(input);
			case JSON:
				return convertJsonToPojo(input);
			default:
				return null;
		}
	}
	
	/**
	 * This method takes a POJO RecloserEventProfile object and translates it 
	 * into its corresponding XML representation based on the XSD definition.
	 * 
	 * @param pojo POJO object
	 * 
	 * @return XML string representing
	 */
	public static String convertPojoToXml(RecloserEventProfile pojo)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertPojoToXml(pojo, false);
	}
	
	/**
	 * This method takes a POJO RecloserEventProfile object and translates it 
	 * into its corresponding XML representation based on the XSD definition.
	 * 
	 * @param pojo POJO object
	 * @param formatXml Flag to indicate whether or not for format the returned XML
	 * 
	 * @return XML string representing
	 */
	public static String convertPojoToXml(RecloserEventProfile pojo, boolean formatXml)
			throws DatatypeConfigurationException, JAXBException
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(RecloserEventProfile.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatXml);
		
		ObjectFactory objFactory = new ObjectFactory();
		
		jaxbMarshaller.marshal(objFactory.createRecloserEventProfile(pojo), stream);
		
		return new String(stream.toByteArray());
	}
	
	/**
	 * This method takes an XML representation of a RecloserEventProfile object
	 * and converts it into a POJO object.
	 * 
	 * @param xml XML representation of a RecloserEventProfile object
	 * 
	 * @return POJO RecloserEventProfile object
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static RecloserEventProfile convertXmlToPojo(String xml)
			throws DatatypeConfigurationException, JAXBException
	{
		StringReader reader = new StringReader(xml);
		
		JAXBContext jaxbContext = JAXBContext.newInstance(RecloserEventProfile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		
		JAXBElement<RecloserEventProfile> root =
				jaxbUnmarshaller.unmarshal(new StreamSource(reader),
										   RecloserEventProfile.class);
		
		return root.getValue();
	}
	
	/**
	 * This method takes an XML representation of a RecloserEventProfile object
	 * and converts it into a JSON representation.
	 * 
	 * @param xml XML representation of a RecloserEventProfile object
	 * 
	 * @return String containing the corresponding JSON representation of the RecloserEventProfile
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static String convertXmlToJson(String xml)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertXmlToJson(xml, false);
	}
	
	/**
	 * This method takes an XML representation of a RecloserEventProfile object
	 * and converts it into a JSON representation.
	 * 
	 * @param xml XML representation of a RecloserEventProfile object
	 * @param formatJson Flag to indicate whether or not for format the returned JSON
	 * 
	 * @return String containing the corresponding JSON representation of the RecloserEventProfile
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static String convertXmlToJson(String xml, boolean formatJson)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertPojoToJson(convertXmlToPojo(xml), formatJson);
	}
	
	/**
	 * This method takes a POJO RecloserEventProfile object and translates it 
	 * into its corresponding JSON representation.
	 * 
	 * @param pojo RecloserEventProfile POJO object
	 * 
	 * @return String containing the corresponding JSON representation of the RecloserEventProfile
	 */
	public static String convertPojoToJson(RecloserEventProfile pojo)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertPojoToJson(pojo, false);
	}
	
	/**
	 * This method takes a POJO RecloserEventProfile object and translates it 
	 * into its corresponding JSON representation.
	 * 
	 * @param pojo RecloserEventProfile POJO object
	 * @param formatJson Flag to indicate whether or not for format the returned JSON
	 * 
	 * @return String containing the corresponding JSON representation of the RecloserEventProfile
	 */
	public static String convertPojoToJson(RecloserEventProfile pojo, boolean formatJson)
			throws DatatypeConfigurationException, JAXBException
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(RecloserEventProfile.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatJson);
		jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
		jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
		
		jaxbMarshaller.marshal(pojo, stream);
		
		return new String(stream.toByteArray());
	}
	
	/**
	 * This method takes an JSON representation of a RecloserEventProfile object
	 * and converts it into a XML representation.
	 * 
	 * @param json JSON representation of a RecloserEventProfile object
	 * 
	 * @return String containing the corresponding XML representation of the RecloserEventProfile
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static String convertJsonToXml(String json)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertJsonToXml(json, false);
	}
	
	/**
	 * This method takes an JSON representation of a RecloserEventProfile object
	 * and converts it into a XML representation.
	 * 
	 * @param json JSON representation of a RecloserEventProfile object
	 * @param formatXml Flag to indicate whether or not for format the returned XML
	 * 
	 * @return String containing the corresponding XML representation of the RecloserEventProfile
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static String convertJsonToXml(String json, boolean formatXml)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertPojoToXml(convertJsonToPojo(json), formatXml);
	}
	
	/**
	 * This method takes an JSON representation of a RecloserEventProfile object
	 * and converts it into a POJO object.
	 * 
	 * @param json JSON representation of a RecloserEventProfile object
	 * 
	 * @return POJO RecloserEventProfile object
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static RecloserEventProfile convertJsonToPojo(String json)
			throws DatatypeConfigurationException, JAXBException
	{
		StringReader reader = new StringReader(json);
		
		JAXBContext jaxbContext = JAXBContext.newInstance(RecloserEventProfile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		jaxbUnmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
		jaxbUnmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		
		JAXBElement<RecloserEventProfile> root =
				jaxbUnmarshaller.unmarshal(new StreamSource(reader),
										   RecloserEventProfile.class);
		
		return root.getValue();
	}
}

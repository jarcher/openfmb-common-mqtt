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
package org.openfmb.xsd.converters;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;

import org.openfmb.xsd._2015._12.openfmb.commonmodule.PhaseCodeKind;
import org.openfmb.xsd._2015._12.openfmb.commonmodule.UnitMultiplierKind;
import org.openfmb.xsd._2015._12.openfmb.commonmodule.UnitSymbolKind;
import org.openfmb.xsd._2016._04.openfmb.shuntmodule.ObjectFactory;
import org.openfmb.xsd._2016._04.openfmb.shuntmodule.ShuntStatusProfile;
import org.openfmb.xsd.converters.gson.PhaseCodeKindConverter;
import org.openfmb.xsd.converters.gson.UnitMultiplierKindConverter;
import org.openfmb.xsd.converters.gson.UnitSymbolKindConverter;
import org.openfmb.xsd.converters.gson.XMLGregorianCalendarConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class handles all of the conversions between the XML message
 * formats, JSON message formats and POJO objects for the OpenFMB
 * ShuntStatusProfile structure.
 * 
 * @author Dwayne Bradley
 * @version 1.0
 */
public class ShuntStatusProfileXsdConverter
{
	private static Gson toJsonConverter = null;
	private static Gson toJsonPrettyPrintConverter = null;
	private static Gson fromJsonConverter = null;
	
	static
	{
		GsonBuilder toGsonBuilder = new GsonBuilder()
	            .registerTypeAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarConverter.Serializer())
		        .registerTypeAdapter(UnitMultiplierKind.class, new UnitMultiplierKindConverter.Serializer())
		        .registerTypeAdapter(UnitSymbolKind.class, new UnitSymbolKindConverter.Serializer())
		        .registerTypeAdapter(PhaseCodeKind.class, new PhaseCodeKindConverter.Serializer())
		        ;
		
		toJsonConverter = toGsonBuilder.create();
		toJsonPrettyPrintConverter = toGsonBuilder.setPrettyPrinting().create();
		
		GsonBuilder fromGsonBuilder = new GsonBuilder()
	            .registerTypeAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarConverter.Deserializer())
		        .registerTypeAdapter(UnitMultiplierKind.class, new UnitMultiplierKindConverter.Deserializer())
		        .registerTypeAdapter(UnitSymbolKind.class, new UnitSymbolKindConverter.Deserializer())
		        .registerTypeAdapter(PhaseCodeKind.class, new PhaseCodeKindConverter.Deserializer())
		        ;
		
		fromJsonConverter = fromGsonBuilder.create();
	}
	
	private ShuntStatusProfileXsdConverter()
	{
		// Just prevent anyone from creating an instance of this class.
	}
	
	/**
	 * This method takes a POJO ShuntStatusProfile object and translates it 
	 * into its corresponding XML representation based on the XSD definition.
	 * 
	 * @param pojo POJO object
	 * 
	 * @return XML string representing the ShuntStatusProfile pojo
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static String convertPojo(ShuntStatusProfile pojo)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertPojoToXml(pojo);
	}
	
	/**
	 * This method takes a POJO ShuntStatusProfile object and translates it 
	 * into its corresponding XML or JSON representation based on the XSD definition.
	 * 
	 * @param pojo POJO object
	 * @param format Convert the output to either XML or JSON
	 * 
	 * @return XML or JSON string representing the ShuntStatusProfile pojo
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static String convertPojo(ShuntStatusProfile pojo, XsdConverterFormat format)
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
	 * This method takes a POJO ShuntStatusProfile object and translates it 
	 * into its corresponding XML representation based on the XSD definition.
	 * 
	 * @param pojo POJO object
	 * @param prettyPrintOutput Flag to indicate whether or not for format the returned string
	 * 
	 * @return XML string representing the ShuntStatusProfile pojo
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static String convertPojo(ShuntStatusProfile pojo, boolean prettyPrintOutput)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertPojoToXml(pojo, prettyPrintOutput);
	}
	
	/**
	 * This method takes a POJO ShuntStatusProfile object and translates it 
	 * into its corresponding XML or JSON representation based on the XSD definition.
	 * 
	 * @param pojo POJO object
	 * @param format Convert the output to either XML or JSON
	 * @param prettyPrintOutput Flag to indicate whether or not to "pretty print" the returned string
	 * 
	 * @return XML string representing the ShuntStatusProfile pojo
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static String convertPojo(ShuntStatusProfile pojo, XsdConverterFormat format, boolean prettyPrintOutput)
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
	 * This method takes an XML representation of a ShuntStatusProfile object
	 * and converts it into a POJO object.
	 * 
	 * @param input XML representation of a ShuntStatusProfile object
	 * 
	 * @return POJO ShuntStatusProfile object
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static ShuntStatusProfile convertToPojo(String input)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertXmlToPojo(input);
	}
	
	/**
	 * This method takes an XML or JSON representation of a ShuntStatusProfile object
	 * and converts it into a POJO object.
	 * 
	 * @param input XML representation of a ShuntStatusProfile object
	 * @param format Indicates if the input string is either XML or JSON
	 * 
	 * @return POJO ShuntStatusProfile object
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static ShuntStatusProfile convertToPojo(String input, XsdConverterFormat format)
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
	 * This method takes a POJO ShuntStatusProfile object and translates it 
	 * into its corresponding XML representation based on the XSD definition.
	 * 
	 * @param pojo POJO object
	 * 
	 * @return XML string representing
	 */
	public static String convertPojoToXml(ShuntStatusProfile pojo)
			throws DatatypeConfigurationException, JAXBException
	{
		return convertPojoToXml(pojo, false);
	}
	
	/**
	 * This method takes a POJO ShuntStatusProfile object and translates it 
	 * into its corresponding XML representation based on the XSD definition.
	 * 
	 * @param pojo POJO object
	 * @param formatXml Flag to indicate whether or not for format the returned XML
	 * 
	 * @return XML string representing
	 */
	public static String convertPojoToXml(ShuntStatusProfile pojo, boolean formatXml)
			throws DatatypeConfigurationException, JAXBException
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(ShuntStatusProfile.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatXml);
		
		ObjectFactory objFactory = new ObjectFactory();
		
		jaxbMarshaller.marshal(objFactory.createShuntStatusProfile(pojo), stream);
		
		return new String(stream.toByteArray());
	}
	
	/**
	 * This method takes an XML representation of a ShuntStatusProfile object
	 * and converts it into a POJO object.
	 * 
	 * @param xml XML representation of a ShuntStatusProfile object
	 * 
	 * @return JAXB ShuntStatusProfile object
	 * 
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 */
	public static ShuntStatusProfile convertXmlToPojo(String xml)
			throws DatatypeConfigurationException, JAXBException
	{
		StringReader reader = new StringReader(xml);
		
		JAXBContext jaxbContext = JAXBContext.newInstance(ShuntStatusProfile.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		
		JAXBElement<ShuntStatusProfile> root =
				jaxbUnmarshaller.unmarshal(new StreamSource(reader),
										   ShuntStatusProfile.class);
		
		return root.getValue();
	}
	
	/**
	 * This method takes an XML representation of a ShuntStatusProfile object
	 * and converts it into a JSON representation.
	 * 
	 * @param xml XML representation of a ShuntStatusProfile object
	 * 
	 * @return String containing the corresponding JSON representation of the ShuntStatusProfile
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
	 * This method takes an XML representation of a ShuntStatusProfile object
	 * and converts it into a JSON representation.
	 * 
	 * @param xml XML representation of a ShuntStatusProfile object
	 * @param formatJson Flag to indicate whether or not for format the returned JSON
	 * 
	 * @return String containing the corresponding JSON representation of the ShuntStatusProfile
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
	 * This method takes a POJO ShuntStatusProfile object and translates it 
	 * into its corresponding JSON representation.
	 * 
	 * @param pojo ShuntStatusProfile POJO object
	 * 
	 * @return String containing the corresponding JSON representation of the ShuntStatusProfile
	 */
	public static String convertPojoToJson(ShuntStatusProfile pojo)
			throws DatatypeConfigurationException
	{
		return convertPojoToJson(pojo, false);
	}
	
	/**
	 * This method takes a POJO ShuntStatusProfile object and translates it 
	 * into its corresponding JSON representation.
	 * 
	 * @param pojo ShuntStatusProfile POJO object
	 * @param formatJson Flag to indicate whether or not for format the returned JSON
	 * 
	 * @return String containing the corresponding JSON representation of the ShuntStatusProfile
	 */
	public static String convertPojoToJson(ShuntStatusProfile pojo, boolean formatJson)
			throws DatatypeConfigurationException
	{
		if (formatJson)
		{
			return ShuntStatusProfileXsdConverter.toJsonPrettyPrintConverter.toJson(pojo);
		}
		else
		{
			return ShuntStatusProfileXsdConverter.toJsonConverter.toJson(pojo);
		}
	}
	
	/**
	 * This method takes an JSON representation of a ShuntStatusProfile object
	 * and converts it into a XML representation.
	 * 
	 * @param json JSON representation of a ShuntStatusProfile object
	 * 
	 * @return String containing the corresponding XML representation of the ShuntStatusProfile
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
	 * This method takes an JSON representation of a ShuntStatusProfile object
	 * and converts it into a XML representation.
	 * 
	 * @param json JSON representation of a ShuntStatusProfile object
	 * @param formatXml Flag to indicate whether or not for format the returned XML
	 * 
	 * @return String containing the corresponding XML representation of the ShuntStatusProfile
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
	 * This method takes an JSON representation of a ShuntStatusProfile object
	 * and converts it into a POJO object.
	 * 
	 * @param json JSON representation of a ShuntStatusProfile object
	 * 
	 * @return POJO ShuntStatusProfile object
	 * 
	 * @throws DatatypeConfigurationException
	 */
	public static ShuntStatusProfile convertJsonToPojo(String json)
			throws DatatypeConfigurationException
	{
		return ShuntStatusProfileXsdConverter.fromJsonConverter.fromJson(json, ShuntStatusProfile.class);
	}
}

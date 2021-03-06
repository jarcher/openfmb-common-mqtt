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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.04 at 11:04:22 AM EDT 
//


package org.openfmb.xsd._2015._12.openfmb.weathermodule;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openfmb.xsd._2015._12.openfmb.weathermodule package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Wind_QNAME = new QName("http://openfmb.org/xsd/2015/12/openfmb/weathermodule", "Wind");
    private final static QName _Temperature_QNAME = new QName("http://openfmb.org/xsd/2015/12/openfmb/weathermodule", "Temperature");
    private final static QName _TemperatureData_QNAME = new QName("http://openfmb.org/xsd/2015/12/openfmb/weathermodule", "TemperatureData");
    private final static QName _Humidity_QNAME = new QName("http://openfmb.org/xsd/2015/12/openfmb/weathermodule", "Humidity");
    private final static QName _SunRadiationData_QNAME = new QName("http://openfmb.org/xsd/2015/12/openfmb/weathermodule", "SunRadiationData");
    private final static QName _SunRadiation_QNAME = new QName("http://openfmb.org/xsd/2015/12/openfmb/weathermodule", "SunRadiation");
    private final static QName _HumidityData_QNAME = new QName("http://openfmb.org/xsd/2015/12/openfmb/weathermodule", "HumidityData");
    private final static QName _WeatherData_QNAME = new QName("http://openfmb.org/xsd/2015/12/openfmb/weathermodule", "WeatherData");
    private final static QName _WeatherDataProfile_QNAME = new QName("http://openfmb.org/xsd/2015/12/openfmb/weathermodule", "WeatherDataProfile");
    private final static QName _WindData_QNAME = new QName("http://openfmb.org/xsd/2015/12/openfmb/weathermodule", "WindData");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openfmb.xsd._2015._12.openfmb.weathermodule
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Wind }
     * 
     */
    public Wind createWind() {
        return new Wind();
    }

    /**
     * Create an instance of {@link Temperature }
     * 
     */
    public Temperature createTemperature() {
        return new Temperature();
    }

    /**
     * Create an instance of {@link WeatherDataProfile }
     * 
     */
    public WeatherDataProfile createWeatherDataProfile() {
        return new WeatherDataProfile();
    }

    /**
     * Create an instance of {@link SunRadiationData }
     * 
     */
    public SunRadiationData createSunRadiationData() {
        return new SunRadiationData();
    }

    /**
     * Create an instance of {@link TemperatureData }
     * 
     */
    public TemperatureData createTemperatureData() {
        return new TemperatureData();
    }

    /**
     * Create an instance of {@link SunRadiation }
     * 
     */
    public SunRadiation createSunRadiation() {
        return new SunRadiation();
    }

    /**
     * Create an instance of {@link Humidity }
     * 
     */
    public Humidity createHumidity() {
        return new Humidity();
    }

    /**
     * Create an instance of {@link WindData }
     * 
     */
    public WindData createWindData() {
        return new WindData();
    }

    /**
     * Create an instance of {@link HumidityData }
     * 
     */
    public HumidityData createHumidityData() {
        return new HumidityData();
    }

    /**
     * Create an instance of {@link WeatherData }
     * 
     */
    public WeatherData createWeatherData() {
        return new WeatherData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Wind }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openfmb.org/xsd/2015/12/openfmb/weathermodule", name = "Wind")
    public JAXBElement<Wind> createWind(Wind value) {
        return new JAXBElement<Wind>(_Wind_QNAME, Wind.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Temperature }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openfmb.org/xsd/2015/12/openfmb/weathermodule", name = "Temperature")
    public JAXBElement<Temperature> createTemperature(Temperature value) {
        return new JAXBElement<Temperature>(_Temperature_QNAME, Temperature.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TemperatureData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openfmb.org/xsd/2015/12/openfmb/weathermodule", name = "TemperatureData")
    public JAXBElement<TemperatureData> createTemperatureData(TemperatureData value) {
        return new JAXBElement<TemperatureData>(_TemperatureData_QNAME, TemperatureData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Humidity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openfmb.org/xsd/2015/12/openfmb/weathermodule", name = "Humidity")
    public JAXBElement<Humidity> createHumidity(Humidity value) {
        return new JAXBElement<Humidity>(_Humidity_QNAME, Humidity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SunRadiationData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openfmb.org/xsd/2015/12/openfmb/weathermodule", name = "SunRadiationData")
    public JAXBElement<SunRadiationData> createSunRadiationData(SunRadiationData value) {
        return new JAXBElement<SunRadiationData>(_SunRadiationData_QNAME, SunRadiationData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SunRadiation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openfmb.org/xsd/2015/12/openfmb/weathermodule", name = "SunRadiation")
    public JAXBElement<SunRadiation> createSunRadiation(SunRadiation value) {
        return new JAXBElement<SunRadiation>(_SunRadiation_QNAME, SunRadiation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HumidityData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openfmb.org/xsd/2015/12/openfmb/weathermodule", name = "HumidityData")
    public JAXBElement<HumidityData> createHumidityData(HumidityData value) {
        return new JAXBElement<HumidityData>(_HumidityData_QNAME, HumidityData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WeatherData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openfmb.org/xsd/2015/12/openfmb/weathermodule", name = "WeatherData")
    public JAXBElement<WeatherData> createWeatherData(WeatherData value) {
        return new JAXBElement<WeatherData>(_WeatherData_QNAME, WeatherData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WeatherDataProfile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openfmb.org/xsd/2015/12/openfmb/weathermodule", name = "WeatherDataProfile")
    public JAXBElement<WeatherDataProfile> createWeatherDataProfile(WeatherDataProfile value) {
        return new JAXBElement<WeatherDataProfile>(_WeatherDataProfile_QNAME, WeatherDataProfile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WindData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://openfmb.org/xsd/2015/12/openfmb/weathermodule", name = "WindData")
    public JAXBElement<WindData> createWindData(WindData value) {
        return new JAXBElement<WindData>(_WindData_QNAME, WindData.class, null, value);
    }

}

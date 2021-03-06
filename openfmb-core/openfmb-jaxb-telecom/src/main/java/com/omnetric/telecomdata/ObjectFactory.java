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
// Generated on: 2015.12.29 at 11:19:49 AM EST 
//


package com.omnetric.telecomdata;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.omnetric.telecomdata package. 
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

    private final static QName _TelecomData_QNAME = new QName("http://www.omnetric.com/TelecomData.xsd#", "TelecomData");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.omnetric.telecomdata
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TelecomData }
     * 
     */
    public TelecomData createTelecomData() {
        return new TelecomData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TelecomData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.omnetric.com/TelecomData.xsd#", name = "TelecomData")
    public JAXBElement<TelecomData> createTelecomData(TelecomData value) {
        return new JAXBElement<TelecomData>(_TelecomData_QNAME, TelecomData.class, null, value);
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.05 at 08:43:06 PM EDT 
//


package org.openfmb.xsd._2015._12.openfmb.commonmodule;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * String measurement
 * 
 * <p>Java class for Analog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Analog">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="measurementType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mRID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="multiplier" type="{http://openfmb.org/xsd/2015/12/openfmb/commonmodule}UnitMultiplierKind" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phases" type="{http://openfmb.org/xsd/2015/12/openfmb/commonmodule}PhaseCodeKind" minOccurs="0"/>
 *         &lt;element name="unit" type="{http://openfmb.org/xsd/2015/12/openfmb/commonmodule}UnitSymbolKind" minOccurs="0"/>
 *         &lt;element name="AnalogValue" type="{http://openfmb.org/xsd/2015/12/openfmb/commonmodule}AnalogValue"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Analog", propOrder = {
    "description",
    "measurementType",
    "mrid",
    "multiplier",
    "name",
    "phases",
    "unit",
    "analogValue"
})
public class Analog {

    protected String description;
    protected String measurementType;
    @XmlElement(name = "mRID")
    protected String mrid;
    @XmlSchemaType(name = "string")
    protected UnitMultiplierKind multiplier;
    protected String name;
    @XmlSchemaType(name = "string")
    protected PhaseCodeKind phases;
    @XmlSchemaType(name = "string")
    protected UnitSymbolKind unit;
    @XmlElement(name = "AnalogValue", required = true)
    protected AnalogValue analogValue;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the measurementType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasurementType() {
        return measurementType;
    }

    /**
     * Sets the value of the measurementType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasurementType(String value) {
        this.measurementType = value;
    }

    /**
     * Gets the value of the mrid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMRID() {
        return mrid;
    }

    /**
     * Sets the value of the mrid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMRID(String value) {
        this.mrid = value;
    }

    /**
     * Gets the value of the multiplier property.
     * 
     * @return
     *     possible object is
     *     {@link UnitMultiplierKind }
     *     
     */
    public UnitMultiplierKind getMultiplier() {
        return multiplier;
    }

    /**
     * Sets the value of the multiplier property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitMultiplierKind }
     *     
     */
    public void setMultiplier(UnitMultiplierKind value) {
        this.multiplier = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the phases property.
     * 
     * @return
     *     possible object is
     *     {@link PhaseCodeKind }
     *     
     */
    public PhaseCodeKind getPhases() {
        return phases;
    }

    /**
     * Sets the value of the phases property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhaseCodeKind }
     *     
     */
    public void setPhases(PhaseCodeKind value) {
        this.phases = value;
    }

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link UnitSymbolKind }
     *     
     */
    public UnitSymbolKind getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitSymbolKind }
     *     
     */
    public void setUnit(UnitSymbolKind value) {
        this.unit = value;
    }

    /**
     * Gets the value of the analogValue property.
     * 
     * @return
     *     possible object is
     *     {@link AnalogValue }
     *     
     */
    public AnalogValue getAnalogValue() {
        return analogValue;
    }

    /**
     * Sets the value of the analogValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnalogValue }
     *     
     */
    public void setAnalogValue(AnalogValue value) {
        this.analogValue = value;
    }

}
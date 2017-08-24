
package com.tim_wro.skupstina.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.skustinans.rs/akti}pravni_osnov"/>
 *         &lt;element name="organ" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oblast" type="{http://www.skustinans.rs/akti}oblast"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pravniOsnov",
    "organ",
    "oblast"
})
@XmlRootElement(name = "preambula", namespace = "http://www.skustinans.rs/akti")
public class Preambula {

    @XmlElement(name = "pravni_osnov", namespace = "http://www.skustinans.rs/akti", required = true)
    protected String pravniOsnov;
    @XmlElement(namespace = "http://www.skustinans.rs/akti", required = true)
    protected String organ;
    @XmlElement(namespace = "http://www.skustinans.rs/akti", required = true)
    @XmlSchemaType(name = "string")
    protected Oblast oblast;

    /**
     * Gets the value of the pravniOsnov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPravniOsnov() {
        return pravniOsnov;
    }

    /**
     * Sets the value of the pravniOsnov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPravniOsnov(String value) {
        this.pravniOsnov = value;
    }

    /**
     * Gets the value of the organ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgan() {
        return organ;
    }

    /**
     * Sets the value of the organ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgan(String value) {
        this.organ = value;
    }

    /**
     * Gets the value of the oblast property.
     * 
     * @return
     *     possible object is
     *     {@link Oblast }
     *     
     */
    public Oblast getOblast() {
        return oblast;
    }

    /**
     * Sets the value of the oblast property.
     * 
     * @param value
     *     allowed object is
     *     {@link Oblast }
     *     
     */
    public void setOblast(Oblast value) {
        this.oblast = value;
    }

}

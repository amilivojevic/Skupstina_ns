
package com.tim_wro.skupstina.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;element ref="{http://www.skustinans.rs/akti}alineja"/>
 *         &lt;/sequence>
 *         &lt;element ref="{http://www.skustinans.rs/akti}sadrzaj"/>
 *       &lt;/choice>
 *       &lt;attribute name="br" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *             &lt;minInclusive value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "alineja",
    "sadrzaj"
})
@XmlRootElement(name = "podtacka", namespace = "http://www.skustinans.rs/akti")
public class Podtacka {

    @XmlElement(namespace = "http://www.skustinans.rs/akti")
    protected List<Alineja> alineja;
    @XmlElement(namespace = "http://www.skustinans.rs/akti")
    protected String sadrzaj;
    @XmlAttribute(name = "br", required = true)
    protected int br;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the alineja property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alineja property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlineja().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Alineja }
     * 
     * 
     */
    public List<Alineja> getAlineja() {
        if (alineja == null) {
            alineja = new ArrayList<Alineja>();
        }
        return this.alineja;
    }

    /**
     * Gets the value of the sadrzaj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSadrzaj() {
        return sadrzaj;
    }

    /**
     * Sets the value of the sadrzaj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSadrzaj(String value) {
        this.sadrzaj = value;
    }

    /**
     * Gets the value of the br property.
     * 
     */
    public int getBr() {
        return br;
    }

    /**
     * Sets the value of the br property.
     * 
     */
    public void setBr(int value) {
        this.br = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}

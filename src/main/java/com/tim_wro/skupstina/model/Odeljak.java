
package com.tim_wro.skupstina.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *       &lt;choice>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;element ref="{http://www.skustinans.rs/akti}pododeljak"/>
 *         &lt;/sequence>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;element ref="{http://www.skustinans.rs/akti}clan"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *       &lt;attribute name="naziv" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="br" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *             &lt;minInclusive value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pododeljak",
    "clan"
})
@XmlRootElement(name = "odeljak", namespace = "http://www.skustinans.rs/akti")
public class Odeljak {

    @XmlElement(namespace = "http://www.skustinans.rs/akti")
    protected List<Pododeljak> pododeljak;
    @XmlElement(namespace = "http://www.skustinans.rs/akti")
    protected List<Clan> clan;
    @XmlAttribute(name = "naziv", required = true)
    protected String naziv;
    @XmlAttribute(name = "br", required = true)
    protected int br;

    /**
     * Gets the value of the pododeljak property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pododeljak property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPododeljak().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Pododeljak }
     * 
     * 
     */
    public List<Pododeljak> getPododeljak() {
        if (pododeljak == null) {
            pododeljak = new ArrayList<Pododeljak>();
        }
        return this.pododeljak;
    }

    /**
     * Gets the value of the clan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClan().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Clan }
     * 
     * 
     */
    public List<Clan> getClan() {
        if (clan == null) {
            clan = new ArrayList<Clan>();
        }
        return this.clan;
    }

    /**
     * Gets the value of the naziv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Sets the value of the naziv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaziv(String value) {
        this.naziv = value;
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

}

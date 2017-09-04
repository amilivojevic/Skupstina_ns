
package com.tim_wro.skupstina.model;

import java.math.BigInteger;
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
 *       &lt;sequence>
 *         &lt;element ref="{http://www.skustinans.rs/akti}preambula"/>
 *         &lt;element ref="{http://www.skustinans.rs/akti}redniBrSednice"/>
 *         &lt;choice>
 *           &lt;sequence maxOccurs="unbounded" minOccurs="2">
 *             &lt;element ref="{http://www.skustinans.rs/akti}deo"/>
 *           &lt;/sequence>
 *           &lt;sequence maxOccurs="unbounded">
 *             &lt;element ref="{http://www.skustinans.rs/akti}clan"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="naziv" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="drzava" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="regija" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="grad" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="stanje" use="required" type="{http://www.skustinans.rs/akti}stanje_akta" />
 *       &lt;attribute name="kreirao" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="za" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="protiv" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="suzdrzani" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="redniBrojSednice" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "preambula",
    "redniBrSednice",
    "deo",
    "clan"
})
@XmlRootElement(name = "akt", namespace = "http://www.skustinans.rs/akti")
public class Akt {

    @XmlElement(namespace = "http://www.skustinans.rs/akti", required = true)
    protected Preambula preambula;
    @XmlElement(namespace = "http://www.skustinans.rs/akti", required = true)
    protected String redniBrSednice;
    @XmlElement(namespace = "http://www.skustinans.rs/akti")
    protected List<Deo> deo;
    @XmlElement(namespace = "http://www.skustinans.rs/akti")
    protected List<Clan> clan;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "naziv", required = true)
    protected String naziv;
    @XmlAttribute(name = "drzava", required = true)
    protected String drzava;
    @XmlAttribute(name = "regija", required = true)
    protected String regija;
    @XmlAttribute(name = "grad", required = true)
    protected String grad;
    @XmlAttribute(name = "stanje", required = true)
    protected StanjeAkta stanje;
    @XmlAttribute(name = "kreirao", required = true)
    protected String kreirao;
    @XmlAttribute(name = "za")
    protected BigInteger za;
    @XmlAttribute(name = "protiv")
    protected BigInteger protiv;
    @XmlAttribute(name = "suzdrzani")
    protected BigInteger suzdrzani;
    @XmlAttribute(name = "redniBrojSednice")
    protected String redniBrojSednice;

    /**
     * Gets the value of the preambula property.
     * 
     * @return
     *     possible object is
     *     {@link Preambula }
     *     
     */
    public Preambula getPreambula() {
        return preambula;
    }

    /**
     * Sets the value of the preambula property.
     * 
     * @param value
     *     allowed object is
     *     {@link Preambula }
     *     
     */
    public void setPreambula(Preambula value) {
        this.preambula = value;
    }

    /**
     * Gets the value of the redniBrSednice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRedniBrSednice() {
        return redniBrSednice;
    }

    /**
     * Sets the value of the redniBrSednice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRedniBrSednice(String value) {
        this.redniBrSednice = value;
    }

    /**
     * Gets the value of the deo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Deo }
     * 
     * 
     */
    public List<Deo> getDeo() {
        if (deo == null) {
            deo = new ArrayList<Deo>();
        }
        return this.deo;
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
     * Gets the value of the drzava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrzava() {
        return drzava;
    }

    /**
     * Sets the value of the drzava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrzava(String value) {
        this.drzava = value;
    }

    /**
     * Gets the value of the regija property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegija() {
        return regija;
    }

    /**
     * Sets the value of the regija property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegija(String value) {
        this.regija = value;
    }

    /**
     * Gets the value of the grad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrad() {
        return grad;
    }

    /**
     * Sets the value of the grad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrad(String value) {
        this.grad = value;
    }

    /**
     * Gets the value of the stanje property.
     * 
     * @return
     *     possible object is
     *     {@link StanjeAkta }
     *     
     */
    public StanjeAkta getStanje() {
        return stanje;
    }

    /**
     * Sets the value of the stanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link StanjeAkta }
     *     
     */
    public void setStanje(StanjeAkta value) {
        this.stanje = value;
    }

    /**
     * Gets the value of the kreirao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKreirao() {
        return kreirao;
    }

    /**
     * Sets the value of the kreirao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKreirao(String value) {
        this.kreirao = value;
    }

    /**
     * Gets the value of the za property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getZa() {
        return za;
    }

    /**
     * Sets the value of the za property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setZa(BigInteger value) {
        this.za = value;
    }

    /**
     * Gets the value of the protiv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getProtiv() {
        return protiv;
    }

    /**
     * Sets the value of the protiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setProtiv(BigInteger value) {
        this.protiv = value;
    }

    /**
     * Gets the value of the suzdrzani property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSuzdrzani() {
        return suzdrzani;
    }

    /**
     * Sets the value of the suzdrzani property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSuzdrzani(BigInteger value) {
        this.suzdrzani = value;
    }

    /**
     * Gets the value of the redniBrojSednice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRedniBrojSednice() {
        return redniBrojSednice;
    }

    /**
     * Sets the value of the redniBrojSednice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRedniBrojSednice(String value) {
        this.redniBrojSednice = value;
    }

}


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
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="stanje" type="{http://www.skustinans.rs/sednice}stanje_sednice"/>
 *         &lt;element name="brojPrisutnih" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element ref="{http://www.skustinans.rs/akti}akt" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="datum" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="redniBroj" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="naziv" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="korisnickoIme" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stanje",
    "brojPrisutnih",
    "akt"
})
@XmlRootElement(name = "sednica", namespace = "http://www.skustinans.rs/sednice")
public class Sednica {

    @XmlElement(namespace = "http://www.skustinans.rs/sednice", required = true)
    @XmlSchemaType(name = "string")
    protected StanjeSednice stanje;
    @XmlElement(namespace = "http://www.skustinans.rs/sednice", required = true)
    protected BigInteger brojPrisutnih;
    @XmlElement(namespace = "http://www.skustinans.rs/akti", required = true)
    protected List<Akt> akt;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "datum", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datum;
    @XmlAttribute(name = "redniBroj", required = true)
    protected String redniBroj;
    @XmlAttribute(name = "naziv")
    protected String naziv;
    @XmlAttribute(name = "korisnickoIme")
    protected String korisnickoIme;

    /**
     * Gets the value of the stanje property.
     * 
     * @return
     *     possible object is
     *     {@link StanjeSednice }
     *     
     */
    public StanjeSednice getStanje() {
        return stanje;
    }

    /**
     * Sets the value of the stanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link StanjeSednice }
     *     
     */
    public void setStanje(StanjeSednice value) {
        this.stanje = value;
    }

    /**
     * Gets the value of the brojPrisutnih property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBrojPrisutnih() {
        return brojPrisutnih;
    }

    /**
     * Sets the value of the brojPrisutnih property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBrojPrisutnih(BigInteger value) {
        this.brojPrisutnih = value;
    }

    /**
     * Gets the value of the akt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the akt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAkt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Akt }
     * 
     * 
     */
    public List<Akt> getAkt() {
        if (akt == null) {
            akt = new ArrayList<Akt>();
        }
        return this.akt;
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
     * Gets the value of the datum property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatum() {
        return datum;
    }

    /**
     * Sets the value of the datum property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatum(XMLGregorianCalendar value) {
        this.datum = value;
    }

    /**
     * Gets the value of the redniBroj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRedniBroj() {
        return redniBroj;
    }

    /**
     * Sets the value of the redniBroj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRedniBroj(String value) {
        this.redniBroj = value;
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
     * Gets the value of the korisnickoIme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    /**
     * Sets the value of the korisnickoIme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKorisnickoIme(String value) {
        this.korisnickoIme = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sednica sednica = (Sednica) o;

        return id.equals(sednica.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

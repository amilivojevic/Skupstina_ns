
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
 *       &lt;sequence>
 *         &lt;element name="stanje" type="{http://www.skustinans.rs/amandmani}stanjeAmandmana"/>
 *         &lt;element ref="{http://www.skustinans.rs/amandmani}obrazlozenje"/>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;element ref="{http://www.skustinans.rs/amandmani}stavkaAmandmana"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="pravniOsnov" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="datumObjave" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="broj" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="naziv" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="kreirao" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="sednicaID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="aktID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "stanje",
    "obrazlozenje",
    "stavkaAmandmana"
})
@XmlRootElement(name = "amandman", namespace = "http://www.skustinans.rs/amandmani")
public class Amandman {

    @XmlElement(namespace = "http://www.skustinans.rs/amandmani", required = true)
    @XmlSchemaType(name = "token")
    protected StanjeAmandmana stanje;
    @XmlElement(namespace = "http://www.skustinans.rs/amandmani", required = true)
    protected String obrazlozenje;
    @XmlElement(namespace = "http://www.skustinans.rs/amandmani", required = true)
    protected List<StavkaAmandmana> stavkaAmandmana;
    @XmlAttribute(name = "pravniOsnov", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String pravniOsnov;
    @XmlAttribute(name = "datumObjave", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String datumObjave;
    @XmlAttribute(name = "broj", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String broj;
    @XmlAttribute(name = "naziv", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String naziv;
    @XmlAttribute(name = "kreirao", required = true)
    protected String kreirao;
    @XmlAttribute(name = "sednicaID", required = true)
    protected String sednicaID;
    @XmlAttribute(name = "aktID", required = true)
    protected String aktID;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the stanje property.
     * 
     * @return
     *     possible object is
     *     {@link StanjeAmandmana }
     *     
     */
    public StanjeAmandmana getStanje() {
        return stanje;
    }

    /**
     * Sets the value of the stanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link StanjeAmandmana }
     *     
     */
    public void setStanje(StanjeAmandmana value) {
        this.stanje = value;
    }

    /**
     * Gets the value of the obrazlozenje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObrazlozenje() {
        return obrazlozenje;
    }

    /**
     * Sets the value of the obrazlozenje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObrazlozenje(String value) {
        this.obrazlozenje = value;
    }

    /**
     * Gets the value of the stavkaAmandmana property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stavkaAmandmana property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStavkaAmandmana().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StavkaAmandmana }
     * 
     * 
     */
    public List<StavkaAmandmana> getStavkaAmandmana() {
        if (stavkaAmandmana == null) {
            stavkaAmandmana = new ArrayList<StavkaAmandmana>();
        }
        return this.stavkaAmandmana;
    }

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
     * Gets the value of the datumObjave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatumObjave() {
        return datumObjave;
    }

    /**
     * Sets the value of the datumObjave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatumObjave(String value) {
        this.datumObjave = value;
    }

    /**
     * Gets the value of the broj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBroj() {
        return broj;
    }

    /**
     * Sets the value of the broj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBroj(String value) {
        this.broj = value;
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
     * Gets the value of the sednicaID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSednicaID() {
        return sednicaID;
    }

    /**
     * Sets the value of the sednicaID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSednicaID(String value) {
        this.sednicaID = value;
    }

    /**
     * Gets the value of the aktID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAktID() {
        return aktID;
    }

    /**
     * Sets the value of the aktID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAktID(String value) {
        this.aktID = value;
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

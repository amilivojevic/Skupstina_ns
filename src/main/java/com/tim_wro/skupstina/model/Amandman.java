
package com.tim_wro.skupstina.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="stanje" type="{http://www.skustinans.rs/amandmani}stanjeAmandmana"/>
 *         &lt;element ref="{http://www.skustinans.rs/amandmani}obrazlozenje"/>
 *         &lt;element ref="{http://www.skustinans.rs/amandmani}stavkaAmandmana"/>
 *       &lt;/sequence>
 *       &lt;attribute name="pravniOsnov" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="datumObjave" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="broj" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="naziv" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="kreirao" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="sednicaID" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="aktID" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stanjeAndObrazlozenjeAndStavkaAmandmana"
})
@XmlRootElement(name = "amandman", namespace = "http://www.skustinans.rs/amandmani")
public class Amandman {

    @XmlElements({
        @XmlElement(name = "stanje", namespace = "http://www.skustinans.rs/amandmani", required = true, type = StanjeAmandmana.class),
        @XmlElement(name = "obrazlozenje", namespace = "http://www.skustinans.rs/amandmani", required = true, type = String.class),
        @XmlElement(name = "stavkaAmandmana", namespace = "http://www.skustinans.rs/amandmani", required = true, type = StavkaAmandmana.class)
    })
    protected List<Object> stanjeAndObrazlozenjeAndStavkaAmandmana;
    @XmlAttribute(name = "pravniOsnov")
    @XmlSchemaType(name = "anySimpleType")
    protected String pravniOsnov;
    @XmlAttribute(name = "datumObjave")
    @XmlSchemaType(name = "anySimpleType")
    protected String datumObjave;
    @XmlAttribute(name = "broj")
    @XmlSchemaType(name = "anySimpleType")
    protected String broj;
    @XmlAttribute(name = "naziv")
    @XmlSchemaType(name = "anySimpleType")
    protected String naziv;
    @XmlAttribute(name = "kreirao")
    protected Integer kreirao;
    @XmlAttribute(name = "sednicaID")
    protected Integer sednicaID;
    @XmlAttribute(name = "aktID")
    protected Integer aktID;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the stanjeAndObrazlozenjeAndStavkaAmandmana property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stanjeAndObrazlozenjeAndStavkaAmandmana property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStanjeAndObrazlozenjeAndStavkaAmandmana().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StanjeAmandmana }
     * {@link String }
     * {@link StavkaAmandmana }
     * 
     * 
     */
    public List<Object> getStanjeAndObrazlozenjeAndStavkaAmandmana() {
        if (stanjeAndObrazlozenjeAndStavkaAmandmana == null) {
            stanjeAndObrazlozenjeAndStavkaAmandmana = new ArrayList<Object>();
        }
        return this.stanjeAndObrazlozenjeAndStavkaAmandmana;
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
     *     {@link Integer }
     *     
     */
    public Integer getKreirao() {
        return kreirao;
    }

    /**
     * Sets the value of the kreirao property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKreirao(Integer value) {
        this.kreirao = value;
    }

    /**
     * Gets the value of the sednicaID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSednicaID() {
        return sednicaID;
    }

    /**
     * Sets the value of the sednicaID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSednicaID(Integer value) {
        this.sednicaID = value;
    }

    /**
     * Gets the value of the aktID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAktID() {
        return aktID;
    }

    /**
     * Sets the value of the aktID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAktID(Integer value) {
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

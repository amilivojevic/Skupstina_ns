
package com.tim_wro.skupstina.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element ref="{}obrazlozenje"/>
 *         &lt;element ref="{}stavkaAmandmana"/>
 *       &lt;/sequence>
 *       &lt;attribute name="pravniOsnov" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="broj" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="naziv" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "obrazlozenjeAndStavkaAmandmana"
})
@XmlRootElement(name = "amandman")
public class Amandman {

    @XmlElements({
        @XmlElement(name = "obrazlozenje", required = true, type = String.class),
        @XmlElement(name = "stavkaAmandmana", required = true, type = StavkaAmandmana.class)
    })
    protected List<Object> obrazlozenjeAndStavkaAmandmana;
    @XmlAttribute(name = "pravniOsnov")
    @XmlSchemaType(name = "anySimpleType")
    protected String pravniOsnov;
    @XmlAttribute(name = "broj")
    @XmlSchemaType(name = "anySimpleType")
    protected String broj;
    @XmlAttribute(name = "naziv")
    @XmlSchemaType(name = "anySimpleType")
    protected String naziv;

    /**
     * Gets the value of the obrazlozenjeAndStavkaAmandmana property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the obrazlozenjeAndStavkaAmandmana property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObrazlozenjeAndStavkaAmandmana().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * {@link StavkaAmandmana }
     * 
     * 
     */
    public List<Object> getObrazlozenjeAndStavkaAmandmana() {
        if (obrazlozenjeAndStavkaAmandmana == null) {
            obrazlozenjeAndStavkaAmandmana = new ArrayList<Object>();
        }
        return this.obrazlozenjeAndStavkaAmandmana;
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

}

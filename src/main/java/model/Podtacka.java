
package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;sequence>
 *           &lt;element ref="{}alineja"/>
 *         &lt;/sequence>
 *         &lt;element ref="{}sadrzaj"/>
 *       &lt;/choice>
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
    "alineja",
    "sadrzaj"
})
@XmlRootElement(name = "podtacka")
public class Podtacka {

    protected Object alineja;
    protected String sadrzaj;
    @XmlAttribute(name = "br", required = true)
    protected int br;

    /**
     * Gets the value of the alineja property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getAlineja() {
        return alineja;
    }

    /**
     * Sets the value of the alineja property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setAlineja(Object value) {
        this.alineja = value;
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

}

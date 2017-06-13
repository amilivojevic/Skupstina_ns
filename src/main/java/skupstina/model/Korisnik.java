
package skupstina.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="ime" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="prezime" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="korisnicko_ime" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="lozinka" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="tip" type="{}tip_korisnika" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "korisnik")
public class Korisnik {

    @XmlAttribute(name = "ime")
    @XmlSchemaType(name = "anySimpleType")
    protected String ime;
    @XmlAttribute(name = "prezime")
    @XmlSchemaType(name = "anySimpleType")
    protected String prezime;
    @XmlAttribute(name = "korisnicko_ime")
    @XmlSchemaType(name = "anySimpleType")
    protected String korisnickoIme;
    @XmlAttribute(name = "lozinka")
    @XmlSchemaType(name = "anySimpleType")
    protected String lozinka;
    @XmlAttribute(name = "tip")
    protected TipKorisnika tip;

    /**
     * Gets the value of the ime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIme() {
        return ime;
    }

    /**
     * Sets the value of the ime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIme(String value) {
        this.ime = value;
    }

    /**
     * Gets the value of the prezime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Sets the value of the prezime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrezime(String value) {
        this.prezime = value;
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

    /**
     * Gets the value of the lozinka property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLozinka() {
        return lozinka;
    }

    /**
     * Sets the value of the lozinka property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLozinka(String value) {
        this.lozinka = value;
    }

    /**
     * Gets the value of the tip property.
     * 
     * @return
     *     possible object is
     *     {@link TipKorisnika }
     *     
     */
    public TipKorisnika getTip() {
        return tip;
    }

    /**
     * Sets the value of the tip property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipKorisnika }
     *     
     */
    public void setTip(TipKorisnika value) {
        this.tip = value;
    }

}

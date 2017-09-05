
package com.tim_wro.skupstina.model;

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
 *       &lt;sequence>
 *         &lt;element name="novo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element ref="{http://www.skustinans.rs/akti}sadrzaj"/>
 *                   &lt;element ref="{http://www.skustinans.rs/akti}deo"/>
 *                   &lt;element ref="{http://www.skustinans.rs/akti}tacka"/>
 *                   &lt;element ref="{http://www.skustinans.rs/akti}odeljak"/>
 *                   &lt;element ref="{http://www.skustinans.rs/akti}stav"/>
 *                   &lt;element ref="{http://www.skustinans.rs/akti}clan"/>
 *                   &lt;element ref="{http://www.skustinans.rs/akti}pododeljak"/>
 *                   &lt;element ref="{http://www.skustinans.rs/akti}glava"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="tipIzmene" type="{http://www.skustinans.rs/amandmani}tipIzmene" />
 *       &lt;attribute name="mestoIzmene" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="putanja" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "novo"
})
@XmlRootElement(name = "stavkaAmandmana", namespace = "http://www.skustinans.rs/amandmani")
public class StavkaAmandmana {

    @XmlElement(namespace = "http://www.skustinans.rs/amandmani", required = true)
    protected StavkaAmandmana.Novo novo;
    @XmlAttribute(name = "tipIzmene")
    protected TipIzmene tipIzmene;
    @XmlAttribute(name = "mestoIzmene")
    protected Integer mestoIzmene;
    @XmlAttribute(name = "putanja")
    protected String putanja;

    /**
     * Gets the value of the novo property.
     * 
     * @return
     *     possible object is
     *     {@link StavkaAmandmana.Novo }
     *     
     */
    public StavkaAmandmana.Novo getNovo() {
        return novo;
    }

    /**
     * Sets the value of the novo property.
     * 
     * @param value
     *     allowed object is
     *     {@link StavkaAmandmana.Novo }
     *     
     */
    public void setNovo(StavkaAmandmana.Novo value) {
        this.novo = value;
    }

    /**
     * Gets the value of the tipIzmene property.
     * 
     * @return
     *     possible object is
     *     {@link TipIzmene }
     *     
     */
    public TipIzmene getTipIzmene() {
        return tipIzmene;
    }

    /**
     * Sets the value of the tipIzmene property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipIzmene }
     *     
     */
    public void setTipIzmene(TipIzmene value) {
        this.tipIzmene = value;
    }

    /**
     * Gets the value of the mestoIzmene property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMestoIzmene() {
        return mestoIzmene;
    }

    /**
     * Sets the value of the mestoIzmene property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMestoIzmene(Integer value) {
        this.mestoIzmene = value;
    }

    /**
     * Gets the value of the putanja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPutanja() {
        return putanja;
    }

    /**
     * Sets the value of the putanja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPutanja(String value) {
        this.putanja = value;
    }


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
     *         &lt;element ref="{http://www.skustinans.rs/akti}sadrzaj"/>
     *         &lt;element ref="{http://www.skustinans.rs/akti}deo"/>
     *         &lt;element ref="{http://www.skustinans.rs/akti}tacka"/>
     *         &lt;element ref="{http://www.skustinans.rs/akti}odeljak"/>
     *         &lt;element ref="{http://www.skustinans.rs/akti}stav"/>
     *         &lt;element ref="{http://www.skustinans.rs/akti}clan"/>
     *         &lt;element ref="{http://www.skustinans.rs/akti}pododeljak"/>
     *         &lt;element ref="{http://www.skustinans.rs/akti}glava"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "sadrzaj",
        "deo",
        "tacka",
        "odeljak",
        "stav",
        "clan",
        "pododeljak",
        "glava"
    })
    public static class Novo {

        @XmlElement(namespace = "http://www.skustinans.rs/akti")
        protected String sadrzaj;
        @XmlElement(namespace = "http://www.skustinans.rs/akti")
        protected Deo deo;
        @XmlElement(namespace = "http://www.skustinans.rs/akti")
        protected Tacka tacka;
        @XmlElement(namespace = "http://www.skustinans.rs/akti")
        protected Odeljak odeljak;
        @XmlElement(namespace = "http://www.skustinans.rs/akti")
        protected Stav stav;
        @XmlElement(namespace = "http://www.skustinans.rs/akti")
        protected Clan clan;
        @XmlElement(namespace = "http://www.skustinans.rs/akti")
        protected Pododeljak pododeljak;
        @XmlElement(namespace = "http://www.skustinans.rs/akti")
        protected Glava glava;

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
         * Gets the value of the deo property.
         * 
         * @return
         *     possible object is
         *     {@link Deo }
         *     
         */
        public Deo getDeo() {
            return deo;
        }

        /**
         * Sets the value of the deo property.
         * 
         * @param value
         *     allowed object is
         *     {@link Deo }
         *     
         */
        public void setDeo(Deo value) {
            this.deo = value;
        }

        /**
         * Gets the value of the tacka property.
         * 
         * @return
         *     possible object is
         *     {@link Tacka }
         *     
         */
        public Tacka getTacka() {
            return tacka;
        }

        /**
         * Sets the value of the tacka property.
         * 
         * @param value
         *     allowed object is
         *     {@link Tacka }
         *     
         */
        public void setTacka(Tacka value) {
            this.tacka = value;
        }

        /**
         * Gets the value of the odeljak property.
         * 
         * @return
         *     possible object is
         *     {@link Odeljak }
         *     
         */
        public Odeljak getOdeljak() {
            return odeljak;
        }

        /**
         * Sets the value of the odeljak property.
         * 
         * @param value
         *     allowed object is
         *     {@link Odeljak }
         *     
         */
        public void setOdeljak(Odeljak value) {
            this.odeljak = value;
        }

        /**
         * Gets the value of the stav property.
         * 
         * @return
         *     possible object is
         *     {@link Stav }
         *     
         */
        public Stav getStav() {
            return stav;
        }

        /**
         * Sets the value of the stav property.
         * 
         * @param value
         *     allowed object is
         *     {@link Stav }
         *     
         */
        public void setStav(Stav value) {
            this.stav = value;
        }

        /**
         * Gets the value of the clan property.
         * 
         * @return
         *     possible object is
         *     {@link Clan }
         *     
         */
        public Clan getClan() {
            return clan;
        }

        /**
         * Sets the value of the clan property.
         * 
         * @param value
         *     allowed object is
         *     {@link Clan }
         *     
         */
        public void setClan(Clan value) {
            this.clan = value;
        }

        /**
         * Gets the value of the pododeljak property.
         * 
         * @return
         *     possible object is
         *     {@link Pododeljak }
         *     
         */
        public Pododeljak getPododeljak() {
            return pododeljak;
        }

        /**
         * Sets the value of the pododeljak property.
         * 
         * @param value
         *     allowed object is
         *     {@link Pododeljak }
         *     
         */
        public void setPododeljak(Pododeljak value) {
            this.pododeljak = value;
        }

        /**
         * Gets the value of the glava property.
         * 
         * @return
         *     possible object is
         *     {@link Glava }
         *     
         */
        public Glava getGlava() {
            return glava;
        }

        /**
         * Sets the value of the glava property.
         * 
         * @param value
         *     allowed object is
         *     {@link Glava }
         *     
         */
        public void setGlava(Glava value) {
            this.glava = value;
        }

    }

}

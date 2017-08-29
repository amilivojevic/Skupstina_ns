
package com.tim_wro.skupstina.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tim_wro.skupstina.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Sadrzaj_QNAME = new QName("http://www.skustinans.rs/akti", "sadrzaj");
    private final static QName _Alineja_QNAME = new QName("http://www.skustinans.rs/akti", "alineja");
    private final static QName _RedniBrSednice_QNAME = new QName("http://www.skustinans.rs/akti", "redniBrSednice");
    private final static QName _PravniOsnov_QNAME = new QName("http://www.skustinans.rs/akti", "pravni_osnov");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tim_wro.skupstina.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Stav }
     * 
     */
    public Stav createStav() {
        return new Stav();
    }

    /**
     * Create an instance of {@link Tacka }
     * 
     */
    public Tacka createTacka() {
        return new Tacka();
    }

    /**
     * Create an instance of {@link Podtacka }
     * 
     */
    public Podtacka createPodtacka() {
        return new Podtacka();
    }

    /**
     * Create an instance of {@link Odeljak }
     * 
     */
    public Odeljak createOdeljak() {
        return new Odeljak();
    }

    /**
     * Create an instance of {@link Pododeljak }
     * 
     */
    public Pododeljak createPododeljak() {
        return new Pododeljak();
    }

    /**
     * Create an instance of {@link Clan }
     * 
     */
    public Clan createClan() {
        return new Clan();
    }

    /**
     * Create an instance of {@link Akt }
     * 
     */
    public Akt createAkt() {
        return new Akt();
    }

    /**
     * Create an instance of {@link Preambula }
     * 
     */
    public Preambula createPreambula() {
        return new Preambula();
    }

    /**
     * Create an instance of {@link Deo }
     * 
     */
    public Deo createDeo() {
        return new Deo();
    }

    /**
     * Create an instance of {@link Glava }
     * 
     */
    public Glava createGlava() {
        return new Glava();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.skustinans.rs/akti", name = "sadrzaj")
    public JAXBElement<String> createSadrzaj(String value) {
        return new JAXBElement<String>(_Sadrzaj_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.skustinans.rs/akti", name = "alineja")
    public JAXBElement<String> createAlineja(String value) {
        return new JAXBElement<String>(_Alineja_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.skustinans.rs/akti", name = "redniBrSednice")
    public JAXBElement<String> createRedniBrSednice(String value) {
        return new JAXBElement<String>(_RedniBrSednice_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.skustinans.rs/akti", name = "pravni_osnov")
    public JAXBElement<String> createPravniOsnov(String value) {
        return new JAXBElement<String>(_PravniOsnov_QNAME, String.class, null, value);
    }

}

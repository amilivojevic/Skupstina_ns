
package com.tim_wro.skupstina.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipIzmene.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipIzmene">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="DODAVANJE"/>
 *     &lt;enumeration value="IZMENA"/>
 *     &lt;enumeration value="BRISANJE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipIzmene", namespace = "http://www.skustinans.rs/amandmani")
@XmlEnum
public enum TipIzmene {

    DODAVANJE,
    IZMENA,
    BRISANJE;

    public String value() {
        return name();
    }

    public static TipIzmene fromValue(String v) {
        return valueOf(v);
    }

}

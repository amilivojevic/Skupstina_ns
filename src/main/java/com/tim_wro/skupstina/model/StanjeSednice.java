
package com.tim_wro.skupstina.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stanje_sednice.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="stanje_sednice">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="zakazana"/>
 *     &lt;enumeration value="aktivna"/>
 *     &lt;enumeration value="zavrsena"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stanje_sednice", namespace = "http://www.skustinans.rs/sednice")
@XmlEnum
public enum StanjeSednice {

    @XmlEnumValue("zakazana")
    ZAKAZANA("zakazana"),
    @XmlEnumValue("aktivna")
    AKTIVNA("aktivna"),
    @XmlEnumValue("zavrsena")
    ZAVRSENA("zavrsena");
    private final String value;

    StanjeSednice(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StanjeSednice fromValue(String v) {
        for (StanjeSednice c: StanjeSednice.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

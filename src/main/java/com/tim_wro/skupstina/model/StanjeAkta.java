
package com.tim_wro.skupstina.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stanje_akta.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="stanje_akta">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="u_proceduri"/>
 *     &lt;enumeration value="u_nacelu"/>
 *     &lt;enumeration value="u_celosti"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stanje_akta", namespace = "skupstinaNS")
@XmlEnum
public enum StanjeAkta {

    @XmlEnumValue("u_proceduri")
    U_PROCEDURI("u_proceduri"),
    @XmlEnumValue("u_nacelu")
    U_NACELU("u_nacelu"),
    @XmlEnumValue("u_celosti")
    U_CELOSTI("u_celosti");
    private final String value;

    StanjeAkta(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StanjeAkta fromValue(String v) {
        for (StanjeAkta c: StanjeAkta.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

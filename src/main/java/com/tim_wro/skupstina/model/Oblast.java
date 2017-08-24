
package com.tim_wro.skupstina.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oblast.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="oblast">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Zdravstvo"/>
 *     &lt;enumeration value="Skolstvo"/>
 *     &lt;enumeration value="Vojska"/>
 *     &lt;enumeration value="Policija"/>
 *     &lt;enumeration value="Pravosudje"/>
 *     &lt;enumeration value="Ostalo"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "oblast", namespace = "http://www.tim_wro.com/skupstina/sednica")
@XmlEnum
public enum Oblast {

    @XmlEnumValue("Zdravstvo")
    ZDRAVSTVO("Zdravstvo"),
    @XmlEnumValue("Skolstvo")
    SKOLSTVO("Skolstvo"),
    @XmlEnumValue("Vojska")
    VOJSKA("Vojska"),
    @XmlEnumValue("Policija")
    POLICIJA("Policija"),
    @XmlEnumValue("Pravosudje")
    PRAVOSUDJE("Pravosudje"),
    @XmlEnumValue("Ostalo")
    OSTALO("Ostalo");
    private final String value;

    Oblast(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Oblast fromValue(String v) {
        for (Oblast c: Oblast.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

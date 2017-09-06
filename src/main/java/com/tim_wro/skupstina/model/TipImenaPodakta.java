
package com.tim_wro.skupstina.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipImenaPodakta.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipImenaPodakta">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="SADRZAJ"/>
 *     &lt;enumeration value="DEO"/>
 *     &lt;enumeration value="TACKA"/>
 *     &lt;enumeration value="ODELJAK"/>
 *     &lt;enumeration value="STAV"/>
 *     &lt;enumeration value="CLAN"/>
 *     &lt;enumeration value="PODODELJAK"/>
 *     &lt;enumeration value="GLAVA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipImenaPodakta", namespace = "http://www.skustinans.rs/amandmani")
@XmlEnum
public enum TipImenaPodakta {

    SADRZAJ,
    DEO,
    TACKA,
    ODELJAK,
    STAV,
    CLAN,
    PODODELJAK,
    GLAVA;

    public String value() {
        return name();
    }

    public static TipImenaPodakta fromValue(String v) {
        return valueOf(v);
    }

}

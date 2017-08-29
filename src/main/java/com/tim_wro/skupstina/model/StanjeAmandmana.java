
package com.tim_wro.skupstina.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stanjeAmandmana.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="stanjeAmandmana">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="PRIHVACEN"/>
 *     &lt;enumeration value="ODBIJEN"/>
 *     &lt;enumeration value="ZAKAZAN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stanjeAmandmana", namespace = "http://www.skustinans.rs/amandmani")
@XmlEnum
public enum StanjeAmandmana {

    PRIHVACEN,
    ODBIJEN,
    ZAKAZAN;

    public String value() {
        return name();
    }

    public static StanjeAmandmana fromValue(String v) {
        return valueOf(v);
    }

}

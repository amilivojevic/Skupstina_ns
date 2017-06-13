
package skupstina.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tip_korisnika.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tip_korisnika">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="predsednik"/>
 *     &lt;enumeration value="poslanik"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tip_korisnika")
@XmlEnum
public enum TipKorisnika {

    @XmlEnumValue("predsednik")
    PREDSEDNIK("predsednik"),
    @XmlEnumValue("poslanik")
    POSLANIK("poslanik");
    private final String value;

    TipKorisnika(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipKorisnika fromValue(String v) {
        for (TipKorisnika c: TipKorisnika.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

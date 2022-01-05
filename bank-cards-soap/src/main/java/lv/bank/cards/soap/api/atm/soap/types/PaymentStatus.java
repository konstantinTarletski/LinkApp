
package lv.bank.cards.soap.api.atm.soap.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for PaymentStatus.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="PaymentStatus"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="active"/&amp;gt;
 *     &amp;lt;enumeration value="returned"/&amp;gt;
 *     &amp;lt;enumeration value="finished"/&amp;gt;
 *     &amp;lt;enumeration value="cancelled"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "PaymentStatus")
@XmlEnum
public enum PaymentStatus {

    @XmlEnumValue("active")
    ACTIVE("active"),
    @XmlEnumValue("returned")
    RETURNED("returned"),
    @XmlEnumValue("finished")
    FINISHED("finished"),
    @XmlEnumValue("cancelled")
    CANCELLED("cancelled");
    private final String value;

    PaymentStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PaymentStatus fromValue(String v) {
        for (PaymentStatus c: PaymentStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

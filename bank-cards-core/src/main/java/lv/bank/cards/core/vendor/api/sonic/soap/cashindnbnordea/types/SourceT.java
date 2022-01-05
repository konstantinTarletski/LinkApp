
package lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for Source_t.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="Source_t"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="D"/&amp;gt;
 *     &amp;lt;enumeration value="N"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "Source_t")
@XmlEnum
public enum SourceT {

    D,
    N;

    public String value() {
        return name();
    }

    public static SourceT fromValue(String v) {
        return valueOf(v);
    }

}

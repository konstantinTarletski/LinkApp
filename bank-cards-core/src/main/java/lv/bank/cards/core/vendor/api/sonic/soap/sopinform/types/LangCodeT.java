
package lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for LangCode_t.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="LangCode_t"&amp;gt;
 *   &amp;lt;restriction base="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}CountryCode_t"&amp;gt;
 *     &amp;lt;length value="2"/&amp;gt;
 *     &amp;lt;enumeration value="LV"/&amp;gt;
 *     &amp;lt;enumeration value="EN"/&amp;gt;
 *     &amp;lt;enumeration value="RU"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "LangCode_t")
@XmlEnum
public enum LangCodeT {

    LV,
    EN,
    RU;

    public String value() {
        return name();
    }

    public static LangCodeT fromValue(String v) {
        return valueOf(v);
    }

}

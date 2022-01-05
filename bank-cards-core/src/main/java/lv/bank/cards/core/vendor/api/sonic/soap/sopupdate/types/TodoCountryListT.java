
package lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for TodoCountryList_t.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="TodoCountryList_t"&amp;gt;
 *   &amp;lt;restriction base="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}CountryCode_t"&amp;gt;
 *     &amp;lt;length value="2"/&amp;gt;
 *     &amp;lt;enumeration value="LV"/&amp;gt;
 *     &amp;lt;enumeration value="PL"/&amp;gt;
 *     &amp;lt;enumeration value="LT"/&amp;gt;
 *     &amp;lt;enumeration value="DK"/&amp;gt;
 *     &amp;lt;enumeration value="FI"/&amp;gt;
 *     &amp;lt;enumeration value="EE"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "TodoCountryList_t")
@XmlEnum
public enum TodoCountryListT {

    LV,
    PL,
    LT,
    DK,
    FI,
    EE;

    public String value() {
        return name();
    }

    public static TodoCountryListT fromValue(String v) {
        return valueOf(v);
    }

}

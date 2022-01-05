
package lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Fixing country to LV
 * 
 * &lt;p&gt;Java class for SystemNameLV_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SystemNameLV_t"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;restriction base="&amp;lt;http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV&amp;gt;SystemName_t"&amp;gt;
 *       &amp;lt;attribute name="Country" use="required" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}TodoCountryList_t" fixed="LV" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SystemNameLV_t")
public class SystemNameLVT
    extends SystemNameT
{


}

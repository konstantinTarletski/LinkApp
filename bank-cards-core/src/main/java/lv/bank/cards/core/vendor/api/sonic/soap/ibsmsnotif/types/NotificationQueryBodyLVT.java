
package lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for NotificationQueryBody_LV_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="NotificationQueryBody_LV_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}NotificationQueryBody_Core_t"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Notification" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}Notification_t"/&amp;gt;
 *         &amp;lt;element name="CommonParamList" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}CommonParamList_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DefaultStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DefaultExpDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RecipientList" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}RecipientList_t"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificationQueryBody_LV_t")
public class NotificationQueryBodyLVT
    extends NotificationQueryBodyCoreT
{


}

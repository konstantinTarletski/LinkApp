
package lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for Notification_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="Notification_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;element name="NotifTextList" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}NotifTextList_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NotifCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Notification_t", propOrder = {
    "notifTextList",
    "notifCode"
})
public class NotificationT {

    @XmlElement(name = "NotifTextList")
    protected NotifTextListT notifTextList;
    @XmlElement(name = "NotifCode")
    protected String notifCode;

    /**
     * Gets the value of the notifTextList property.
     * 
     * @return
     *     possible object is
     *     {@link NotifTextListT }
     *     
     */
    public NotifTextListT getNotifTextList() {
        return notifTextList;
    }

    /**
     * Sets the value of the notifTextList property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotifTextListT }
     *     
     */
    public void setNotifTextList(NotifTextListT value) {
        this.notifTextList = value;
    }

    /**
     * Gets the value of the notifCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotifCode() {
        return notifCode;
    }

    /**
     * Sets the value of the notifCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotifCode(String value) {
        this.notifCode = value;
    }

}

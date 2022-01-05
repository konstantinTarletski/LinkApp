
package lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for NotificationQueryBody_Core_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="NotificationQueryBody_Core_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}QueryBody_t"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Notification" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}Notification_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CommonParamList" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}CommonParamList_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DefaultStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DefaultExpDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RecipientList" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}RecipientList_t" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificationQueryBody_Core_t", propOrder = {
    "notification",
    "commonParamList",
    "defaultStartDate",
    "defaultExpDate",
    "recipientList"
})
@XmlSeeAlso({
    NotificationQueryBodyLVT.class
})
public abstract class NotificationQueryBodyCoreT
    extends QueryBodyT
{

    @XmlElement(name = "Notification")
    protected NotificationT notification;
    @XmlElement(name = "CommonParamList")
    protected CommonParamListT commonParamList;
    @XmlElement(name = "DefaultStartDate", type = String.class)
    @XmlJavaTypeAdapter(Adapter3 .class)
    @XmlSchemaType(name = "date")
    protected Date defaultStartDate;
    @XmlElement(name = "DefaultExpDate", type = String.class)
    @XmlJavaTypeAdapter(Adapter3 .class)
    @XmlSchemaType(name = "date")
    protected Date defaultExpDate;
    @XmlElement(name = "RecipientList")
    protected RecipientListT recipientList;

    /**
     * Gets the value of the notification property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationT }
     *     
     */
    public NotificationT getNotification() {
        return notification;
    }

    /**
     * Sets the value of the notification property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationT }
     *     
     */
    public void setNotification(NotificationT value) {
        this.notification = value;
    }

    /**
     * Gets the value of the commonParamList property.
     * 
     * @return
     *     possible object is
     *     {@link CommonParamListT }
     *     
     */
    public CommonParamListT getCommonParamList() {
        return commonParamList;
    }

    /**
     * Sets the value of the commonParamList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonParamListT }
     *     
     */
    public void setCommonParamList(CommonParamListT value) {
        this.commonParamList = value;
    }

    /**
     * Gets the value of the defaultStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDefaultStartDate() {
        return defaultStartDate;
    }

    /**
     * Sets the value of the defaultStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultStartDate(Date value) {
        this.defaultStartDate = value;
    }

    /**
     * Gets the value of the defaultExpDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDefaultExpDate() {
        return defaultExpDate;
    }

    /**
     * Sets the value of the defaultExpDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultExpDate(Date value) {
        this.defaultExpDate = value;
    }

    /**
     * Gets the value of the recipientList property.
     * 
     * @return
     *     possible object is
     *     {@link RecipientListT }
     *     
     */
    public RecipientListT getRecipientList() {
        return recipientList;
    }

    /**
     * Sets the value of the recipientList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecipientListT }
     *     
     */
    public void setRecipientList(RecipientListT value) {
        this.recipientList = value;
    }

}

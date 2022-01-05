
package lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for Query_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="Query_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="QueryHeader" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}NotificationQueryHeader_LV_t"/&amp;gt;
 *         &amp;lt;element name="QueryBody" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}NotificationQueryBody_LV_t"/&amp;gt;
 *         &amp;lt;element name="Extension" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}NotificationQueryExtension_LV_t"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Query_t", propOrder = {
    "queryHeader",
    "queryBody",
    "extension"
})
public class QueryT {

    @XmlElement(name = "QueryHeader", required = true)
    protected NotificationQueryHeaderLVT queryHeader;
    @XmlElement(name = "QueryBody", required = true)
    protected NotificationQueryBodyLVT queryBody;
    @XmlElement(name = "Extension", required = true)
    protected NotificationQueryExtensionLVT extension;

    /**
     * Gets the value of the queryHeader property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationQueryHeaderLVT }
     *     
     */
    public NotificationQueryHeaderLVT getQueryHeader() {
        return queryHeader;
    }

    /**
     * Sets the value of the queryHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationQueryHeaderLVT }
     *     
     */
    public void setQueryHeader(NotificationQueryHeaderLVT value) {
        this.queryHeader = value;
    }

    /**
     * Gets the value of the queryBody property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationQueryBodyLVT }
     *     
     */
    public NotificationQueryBodyLVT getQueryBody() {
        return queryBody;
    }

    /**
     * Sets the value of the queryBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationQueryBodyLVT }
     *     
     */
    public void setQueryBody(NotificationQueryBodyLVT value) {
        this.queryBody = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationQueryExtensionLVT }
     *     
     */
    public NotificationQueryExtensionLVT getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationQueryExtensionLVT }
     *     
     */
    public void setExtension(NotificationQueryExtensionLVT value) {
        this.extension = value;
    }

}

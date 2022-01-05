
package lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for Result_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="Result_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="ResultHeader" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}NotificationResultHeader_LV_t"/&amp;gt;
 *         &amp;lt;element name="ResultBody" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}NotificationResultBody_LV_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Extension" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}NotificationResultExtension_LV_t"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Result_t", propOrder = {
    "resultHeader",
    "resultBody",
    "extension"
})
public class ResultT {

    @XmlElement(name = "ResultHeader", required = true)
    protected NotificationResultHeaderLVT resultHeader;
    @XmlElement(name = "ResultBody")
    protected NotificationResultBodyLVT resultBody;
    @XmlElement(name = "Extension", required = true)
    protected NotificationResultExtensionLVT extension;

    /**
     * Gets the value of the resultHeader property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationResultHeaderLVT }
     *     
     */
    public NotificationResultHeaderLVT getResultHeader() {
        return resultHeader;
    }

    /**
     * Sets the value of the resultHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationResultHeaderLVT }
     *     
     */
    public void setResultHeader(NotificationResultHeaderLVT value) {
        this.resultHeader = value;
    }

    /**
     * Gets the value of the resultBody property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationResultBodyLVT }
     *     
     */
    public NotificationResultBodyLVT getResultBody() {
        return resultBody;
    }

    /**
     * Sets the value of the resultBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationResultBodyLVT }
     *     
     */
    public void setResultBody(NotificationResultBodyLVT value) {
        this.resultBody = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationResultExtensionLVT }
     *     
     */
    public NotificationResultExtensionLVT getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationResultExtensionLVT }
     *     
     */
    public void setExtension(NotificationResultExtensionLVT value) {
        this.extension = value;
    }

}

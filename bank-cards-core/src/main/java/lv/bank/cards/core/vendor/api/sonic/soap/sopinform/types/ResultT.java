
package lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types;

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
 *         &amp;lt;element name="ResultHeader" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}SOPInformationResultHeader_LV_t"/&amp;gt;
 *         &amp;lt;element name="ResultBody" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}SOPInformationResultBody_LV_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Extension" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}SOPInformationQueryExtension_LV_t"/&amp;gt;
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
    protected SOPInformationResultHeaderLVT resultHeader;
    @XmlElement(name = "ResultBody")
    protected SOPInformationResultBodyLVT resultBody;
    @XmlElement(name = "Extension", required = true)
    protected SOPInformationQueryExtensionLVT extension;

    /**
     * Gets the value of the resultHeader property.
     * 
     * @return
     *     possible object is
     *     {@link SOPInformationResultHeaderLVT }
     *     
     */
    public SOPInformationResultHeaderLVT getResultHeader() {
        return resultHeader;
    }

    /**
     * Sets the value of the resultHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link SOPInformationResultHeaderLVT }
     *     
     */
    public void setResultHeader(SOPInformationResultHeaderLVT value) {
        this.resultHeader = value;
    }

    /**
     * Gets the value of the resultBody property.
     * 
     * @return
     *     possible object is
     *     {@link SOPInformationResultBodyLVT }
     *     
     */
    public SOPInformationResultBodyLVT getResultBody() {
        return resultBody;
    }

    /**
     * Sets the value of the resultBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link SOPInformationResultBodyLVT }
     *     
     */
    public void setResultBody(SOPInformationResultBodyLVT value) {
        this.resultBody = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link SOPInformationQueryExtensionLVT }
     *     
     */
    public SOPInformationQueryExtensionLVT getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link SOPInformationQueryExtensionLVT }
     *     
     */
    public void setExtension(SOPInformationQueryExtensionLVT value) {
        this.extension = value;
    }

}

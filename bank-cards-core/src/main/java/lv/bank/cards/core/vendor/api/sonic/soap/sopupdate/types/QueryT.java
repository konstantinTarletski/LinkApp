
package lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types;

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
 *         &amp;lt;element name="QueryHeader" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}SOPInformationUpdateQueryHeader_LV_t"/&amp;gt;
 *         &amp;lt;element name="QueryBody" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}SOPInformationUpdateQueryBody_LV_t"/&amp;gt;
 *         &amp;lt;element name="Extension" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}SOPInformationUpdateQueryExtension_LV_t"/&amp;gt;
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
    protected SOPInformationUpdateQueryHeaderLVT queryHeader;
    @XmlElement(name = "QueryBody", required = true)
    protected SOPInformationUpdateQueryBodyLVT queryBody;
    @XmlElement(name = "Extension", required = true)
    protected SOPInformationUpdateQueryExtensionLVT extension;

    /**
     * Gets the value of the queryHeader property.
     * 
     * @return
     *     possible object is
     *     {@link SOPInformationUpdateQueryHeaderLVT }
     *     
     */
    public SOPInformationUpdateQueryHeaderLVT getQueryHeader() {
        return queryHeader;
    }

    /**
     * Sets the value of the queryHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link SOPInformationUpdateQueryHeaderLVT }
     *     
     */
    public void setQueryHeader(SOPInformationUpdateQueryHeaderLVT value) {
        this.queryHeader = value;
    }

    /**
     * Gets the value of the queryBody property.
     * 
     * @return
     *     possible object is
     *     {@link SOPInformationUpdateQueryBodyLVT }
     *     
     */
    public SOPInformationUpdateQueryBodyLVT getQueryBody() {
        return queryBody;
    }

    /**
     * Sets the value of the queryBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link SOPInformationUpdateQueryBodyLVT }
     *     
     */
    public void setQueryBody(SOPInformationUpdateQueryBodyLVT value) {
        this.queryBody = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link SOPInformationUpdateQueryExtensionLVT }
     *     
     */
    public SOPInformationUpdateQueryExtensionLVT getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link SOPInformationUpdateQueryExtensionLVT }
     *     
     */
    public void setExtension(SOPInformationUpdateQueryExtensionLVT value) {
        this.extension = value;
    }

}

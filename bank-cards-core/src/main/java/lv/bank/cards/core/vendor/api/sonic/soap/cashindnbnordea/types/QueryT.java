
package lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types;

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
 *         &amp;lt;element name="QueryHeader" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}SOPInformationQueryHeader_LV_t"/&amp;gt;
 *         &amp;lt;element name="QueryBody" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}CashInDNBNordeaQueryBody_LV_t"/&amp;gt;
 *         &amp;lt;element name="Extension" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}CashInDNBNordeaQueryExtension_LV_t"/&amp;gt;
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
    protected SOPInformationQueryHeaderLVT queryHeader;
    @XmlElement(name = "QueryBody", required = true)
    protected CashInDNBNordeaQueryBodyLVT queryBody;
    @XmlElement(name = "Extension", required = true)
    protected CashInDNBNordeaQueryExtensionLVT extension;

    /**
     * Gets the value of the queryHeader property.
     * 
     * @return
     *     possible object is
     *     {@link SOPInformationQueryHeaderLVT }
     *     
     */
    public SOPInformationQueryHeaderLVT getQueryHeader() {
        return queryHeader;
    }

    /**
     * Sets the value of the queryHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link SOPInformationQueryHeaderLVT }
     *     
     */
    public void setQueryHeader(SOPInformationQueryHeaderLVT value) {
        this.queryHeader = value;
    }

    /**
     * Gets the value of the queryBody property.
     * 
     * @return
     *     possible object is
     *     {@link CashInDNBNordeaQueryBodyLVT }
     *     
     */
    public CashInDNBNordeaQueryBodyLVT getQueryBody() {
        return queryBody;
    }

    /**
     * Sets the value of the queryBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashInDNBNordeaQueryBodyLVT }
     *     
     */
    public void setQueryBody(CashInDNBNordeaQueryBodyLVT value) {
        this.queryBody = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link CashInDNBNordeaQueryExtensionLVT }
     *     
     */
    public CashInDNBNordeaQueryExtensionLVT getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashInDNBNordeaQueryExtensionLVT }
     *     
     */
    public void setExtension(CashInDNBNordeaQueryExtensionLVT value) {
        this.extension = value;
    }

}

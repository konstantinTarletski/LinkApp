
package lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SOPInformationResultBody_Core_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SOPInformationResultBody_Core_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}ResultBody_t"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="UniqueClientID" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="SOPList" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}SOPList_t"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SOPInformationResultBody_Core_t", propOrder = {
    "uniqueClientID",
    "sopList"
})
@XmlSeeAlso({
    SOPInformationResultBodyLVT.class
})
public abstract class SOPInformationResultBodyCoreT
    extends ResultBodyT
{

    @XmlElement(name = "UniqueClientID", required = true)
    protected String uniqueClientID;
    @XmlElement(name = "SOPList", required = true)
    protected SOPListT sopList;

    /**
     * Gets the value of the uniqueClientID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueClientID() {
        return uniqueClientID;
    }

    /**
     * Sets the value of the uniqueClientID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueClientID(String value) {
        this.uniqueClientID = value;
    }

    /**
     * Gets the value of the sopList property.
     * 
     * @return
     *     possible object is
     *     {@link SOPListT }
     *     
     */
    public SOPListT getSOPList() {
        return sopList;
    }

    /**
     * Sets the value of the sopList property.
     * 
     * @param value
     *     allowed object is
     *     {@link SOPListT }
     *     
     */
    public void setSOPList(SOPListT value) {
        this.sopList = value;
    }

}

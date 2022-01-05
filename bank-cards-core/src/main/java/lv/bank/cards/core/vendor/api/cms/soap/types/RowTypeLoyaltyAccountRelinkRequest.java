
package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_LoyaltyAccountRelinkRequest complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_LoyaltyAccountRelinkRequest"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="SOURCE_CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="LS_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="DESTINATION_CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_LoyaltyAccountRelinkRequest", propOrder = {
    "sourcecard",
    "lscode",
    "destinationcard"
})
public class RowTypeLoyaltyAccountRelinkRequest {

    @XmlElement(name = "SOURCE_CARD", required = true)
    protected String sourcecard;
    @XmlElement(name = "LS_CODE", required = true)
    protected String lscode;
    @XmlElement(name = "DESTINATION_CARD", required = true)
    protected String destinationcard;

    /**
     * Gets the value of the sourcecard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOURCECARD() {
        return sourcecard;
    }

    /**
     * Sets the value of the sourcecard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOURCECARD(String value) {
        this.sourcecard = value;
    }

    /**
     * Gets the value of the lscode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLSCODE() {
        return lscode;
    }

    /**
     * Sets the value of the lscode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLSCODE(String value) {
        this.lscode = value;
    }

    /**
     * Gets the value of the destinationcard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESTINATIONCARD() {
        return destinationcard;
    }

    /**
     * Sets the value of the destinationcard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESTINATIONCARD(String value) {
        this.destinationcard = value;
    }

}

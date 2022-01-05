
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_GetInstallmentConditionsForRequest_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_GetInstallmentConditionsForRequest_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD_ACCT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CCY" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="AMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MCC_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MERCHANT_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_GetInstallmentConditionsForRequest_Request", propOrder = {
    "cardacct",
    "ccy",
    "amount",
    "mcccode",
    "merchantid"
})
public class RowTypeGetInstallmentConditionsForRequestRequest {

    @XmlElement(name = "CARD_ACCT", required = true)
    protected String cardacct;
    @XmlElement(name = "CCY", required = true)
    protected String ccy;
    @XmlElement(name = "AMOUNT")
    protected BigDecimal amount;
    @XmlElement(name = "MCC_CODE")
    protected String mcccode;
    @XmlElement(name = "MERCHANT_ID")
    protected String merchantid;

    /**
     * Gets the value of the cardacct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDACCT() {
        return cardacct;
    }

    /**
     * Sets the value of the cardacct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDACCT(String value) {
        this.cardacct = value;
    }

    /**
     * Gets the value of the ccy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCCY() {
        return ccy;
    }

    /**
     * Sets the value of the ccy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCCY(String value) {
        this.ccy = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAMOUNT() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAMOUNT(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the mcccode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMCCCODE() {
        return mcccode;
    }

    /**
     * Sets the value of the mcccode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMCCCODE(String value) {
        this.mcccode = value;
    }

    /**
     * Gets the value of the merchantid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMERCHANTID() {
        return merchantid;
    }

    /**
     * Sets the value of the merchantid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMERCHANTID(String value) {
        this.merchantid = value;
    }

}

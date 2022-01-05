
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_LinkCardToLoyaltyAccountRequest complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_LinkCardToLoyaltyAccountRequest"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="LS_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="LY_ACCOUNT_NO" type="{http://www.w3.org/2001/XMLSchema}integer"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_LinkCardToLoyaltyAccountRequest", propOrder = {
    "card",
    "lscode",
    "lyaccountno"
})
public class RowTypeLinkCardToLoyaltyAccountRequest {

    @XmlElement(name = "CARD", required = true)
    protected String card;
    @XmlElement(name = "LS_CODE", required = true)
    protected String lscode;
    @XmlElement(name = "LY_ACCOUNT_NO", required = true)
    protected BigInteger lyaccountno;

    /**
     * Gets the value of the card property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARD() {
        return card;
    }

    /**
     * Sets the value of the card property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARD(String value) {
        this.card = value;
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
     * Gets the value of the lyaccountno property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLYACCOUNTNO() {
        return lyaccountno;
    }

    /**
     * Sets the value of the lyaccountno property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLYACCOUNTNO(BigInteger value) {
        this.lyaccountno = value;
    }

}

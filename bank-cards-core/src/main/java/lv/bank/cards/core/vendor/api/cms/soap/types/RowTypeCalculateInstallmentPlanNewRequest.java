
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_CalculateInstallmentPlanNew_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_CalculateInstallmentPlanNew_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD_ACCT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="METHOD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="PERIOD" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="RATE" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="AMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_CalculateInstallmentPlanNew_Request", propOrder = {
    "cardacct",
    "ccy",
    "method",
    "period",
    "rate",
    "amount"
})
public class RowTypeCalculateInstallmentPlanNewRequest {

    @XmlElement(name = "CARD_ACCT")
    protected String cardacct;
    @XmlElement(name = "CCY")
    protected String ccy;
    @XmlElement(name = "METHOD", required = true)
    protected String method;
    @XmlElement(name = "PERIOD", required = true)
    protected BigDecimal period;
    @XmlElement(name = "RATE", required = true)
    protected BigDecimal rate;
    @XmlElement(name = "AMOUNT", required = true)
    protected BigDecimal amount;

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
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMETHOD() {
        return method;
    }

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMETHOD(String value) {
        this.method = value;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPERIOD() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPERIOD(BigDecimal value) {
        this.period = value;
    }

    /**
     * Gets the value of the rate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRATE() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRATE(BigDecimal value) {
        this.rate = value;
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

}

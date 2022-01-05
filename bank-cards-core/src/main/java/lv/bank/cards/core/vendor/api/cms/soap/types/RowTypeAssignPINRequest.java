
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_AssignPIN_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_AssignPIN_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="EXPIRY" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="PINBLOCK" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="STAN" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="TRANSACTION_DATETIME" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD_SECURE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ACQUIRER_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TERMINAL_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MERCHANT_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TERMINAL_LOCATION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_AssignPIN_Request", propOrder = {
    "card",
    "expiry",
    "pinblock",
    "stan",
    "transactiondatetime",
    "cardsecurecode",
    "acquirerid",
    "terminalid",
    "merchantid",
    "terminallocation"
})
public class RowTypeAssignPINRequest {

    @XmlElement(name = "CARD", required = true)
    protected String card;
    @XmlElement(name = "EXPIRY", required = true)
    protected String expiry;
    @XmlElement(name = "PINBLOCK", required = true)
    protected String pinblock;
    @XmlElement(name = "STAN", required = true)
    protected String stan;
    @XmlElement(name = "TRANSACTION_DATETIME", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date transactiondatetime;
    @XmlElement(name = "CARD_SECURE_CODE")
    protected String cardsecurecode;
    @XmlElement(name = "ACQUIRER_ID")
    protected String acquirerid;
    @XmlElement(name = "TERMINAL_ID")
    protected String terminalid;
    @XmlElement(name = "MERCHANT_ID")
    protected String merchantid;
    @XmlElement(name = "TERMINAL_LOCATION")
    protected String terminallocation;

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
     * Gets the value of the expiry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXPIRY() {
        return expiry;
    }

    /**
     * Sets the value of the expiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXPIRY(String value) {
        this.expiry = value;
    }

    /**
     * Gets the value of the pinblock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPINBLOCK() {
        return pinblock;
    }

    /**
     * Sets the value of the pinblock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPINBLOCK(String value) {
        this.pinblock = value;
    }

    /**
     * Gets the value of the stan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTAN() {
        return stan;
    }

    /**
     * Sets the value of the stan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTAN(String value) {
        this.stan = value;
    }

    /**
     * Gets the value of the transactiondatetime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getTRANSACTIONDATETIME() {
        return transactiondatetime;
    }

    /**
     * Sets the value of the transactiondatetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRANSACTIONDATETIME(Date value) {
        this.transactiondatetime = value;
    }

    /**
     * Gets the value of the cardsecurecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDSECURECODE() {
        return cardsecurecode;
    }

    /**
     * Sets the value of the cardsecurecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDSECURECODE(String value) {
        this.cardsecurecode = value;
    }

    /**
     * Gets the value of the acquirerid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACQUIRERID() {
        return acquirerid;
    }

    /**
     * Sets the value of the acquirerid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACQUIRERID(String value) {
        this.acquirerid = value;
    }

    /**
     * Gets the value of the terminalid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTERMINALID() {
        return terminalid;
    }

    /**
     * Sets the value of the terminalid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTERMINALID(String value) {
        this.terminalid = value;
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

    /**
     * Gets the value of the terminallocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTERMINALLOCATION() {
        return terminallocation;
    }

    /**
     * Sets the value of the terminallocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTERMINALLOCATION(String value) {
        this.terminallocation = value;
    }

}


package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_EnterLoyaltyAccountTransaction complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_EnterLoyaltyAccountTransaction"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="LS_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="KEY_TYPE" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="KEY_VALUE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="TR_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="AMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="SOURCE_DOCUMENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_EnterLoyaltyAccountTransaction", propOrder = {
    "lscode",
    "keytype",
    "keyvalue",
    "trtype",
    "amount",
    "sourcedocument"
})
public class RowTypeEnterLoyaltyAccountTransaction {

    @XmlElement(name = "LS_CODE", required = true)
    protected String lscode;
    @XmlElement(name = "KEY_TYPE", required = true)
    protected BigDecimal keytype;
    @XmlElement(name = "KEY_VALUE", required = true)
    protected String keyvalue;
    @XmlElement(name = "TR_TYPE", required = true)
    protected String trtype;
    @XmlElement(name = "AMOUNT", required = true)
    protected BigDecimal amount;
    @XmlElement(name = "SOURCE_DOCUMENT")
    protected String sourcedocument;

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
     * Gets the value of the keytype property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKEYTYPE() {
        return keytype;
    }

    /**
     * Sets the value of the keytype property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKEYTYPE(BigDecimal value) {
        this.keytype = value;
    }

    /**
     * Gets the value of the keyvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKEYVALUE() {
        return keyvalue;
    }

    /**
     * Sets the value of the keyvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEYVALUE(String value) {
        this.keyvalue = value;
    }

    /**
     * Gets the value of the trtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRTYPE() {
        return trtype;
    }

    /**
     * Sets the value of the trtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRTYPE(String value) {
        this.trtype = value;
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
     * Gets the value of the sourcedocument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOURCEDOCUMENT() {
        return sourcedocument;
    }

    /**
     * Sets the value of the sourcedocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOURCEDOCUMENT(String value) {
        this.sourcedocument = value;
    }

}

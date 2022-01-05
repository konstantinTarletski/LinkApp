
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_TransferLoyaltyPoints complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_TransferLoyaltyPoints"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="LS_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="KEY_TYPE_SOURCE" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="KEY_VALUE_SOURCE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="KEY_TYPE_TARGET" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="KEY_VALUE_TARGET" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
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
@XmlType(name = "RowType_TransferLoyaltyPoints", propOrder = {
    "lscode",
    "keytypesource",
    "keyvaluesource",
    "keytypetarget",
    "keyvaluetarget",
    "amount"
})
public class RowTypeTransferLoyaltyPoints {

    @XmlElement(name = "LS_CODE", required = true)
    protected String lscode;
    @XmlElement(name = "KEY_TYPE_SOURCE", required = true)
    protected BigDecimal keytypesource;
    @XmlElement(name = "KEY_VALUE_SOURCE", required = true)
    protected String keyvaluesource;
    @XmlElement(name = "KEY_TYPE_TARGET", required = true)
    protected BigDecimal keytypetarget;
    @XmlElement(name = "KEY_VALUE_TARGET", required = true)
    protected String keyvaluetarget;
    @XmlElement(name = "AMOUNT", required = true)
    protected BigDecimal amount;

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
     * Gets the value of the keytypesource property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKEYTYPESOURCE() {
        return keytypesource;
    }

    /**
     * Sets the value of the keytypesource property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKEYTYPESOURCE(BigDecimal value) {
        this.keytypesource = value;
    }

    /**
     * Gets the value of the keyvaluesource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKEYVALUESOURCE() {
        return keyvaluesource;
    }

    /**
     * Sets the value of the keyvaluesource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEYVALUESOURCE(String value) {
        this.keyvaluesource = value;
    }

    /**
     * Gets the value of the keytypetarget property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKEYTYPETARGET() {
        return keytypetarget;
    }

    /**
     * Sets the value of the keytypetarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKEYTYPETARGET(BigDecimal value) {
        this.keytypetarget = value;
    }

    /**
     * Gets the value of the keyvaluetarget property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKEYVALUETARGET() {
        return keyvaluetarget;
    }

    /**
     * Sets the value of the keyvaluetarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEYVALUETARGET(String value) {
        this.keyvaluetarget = value;
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

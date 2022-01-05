
package lv.bank.cards.soap.api.atm.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for anonymous complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="sessionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="paymentID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="paymentRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="switchingID" type="{urn:PaymentServer}OptionalInt" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="billerRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="payinstrRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="details" type="{urn:PaymentServer}DetailArray" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="confirmed" type="{urn:PaymentServer}OptionalBoolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="finished" type="{urn:PaymentServer}OptionalBoolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="pinBlock" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="pinFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="approved" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;any namespace='urn:PaymentServer' minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sessionID",
    "paymentID",
    "paymentRef",
    "language",
    "switchingID",
    "billerRef",
    "payinstrRef",
    "details",
    "confirmed",
    "finished",
    "pinBlock",
    "pinFormat",
    "approved",
    "any"
})
@XmlRootElement(name = "Request")
public class Request {

    protected String sessionID;
    protected String paymentID;
    protected String paymentRef;
    protected String language;
    protected OptionalInt switchingID;
    protected String billerRef;
    protected String payinstrRef;
    protected DetailArray details;
    protected OptionalBoolean confirmed;
    protected OptionalBoolean finished;
    protected String pinBlock;
    protected String pinFormat;
    protected Boolean approved;
    @XmlAnyElement(lax = true)
    protected Object any;

    /**
     * Gets the value of the sessionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionID() {
        return sessionID;
    }

    /**
     * Sets the value of the sessionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionID(String value) {
        this.sessionID = value;
    }

    /**
     * Gets the value of the paymentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentID() {
        return paymentID;
    }

    /**
     * Sets the value of the paymentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentID(String value) {
        this.paymentID = value;
    }

    /**
     * Gets the value of the paymentRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentRef() {
        return paymentRef;
    }

    /**
     * Sets the value of the paymentRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentRef(String value) {
        this.paymentRef = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the switchingID property.
     * 
     * @return
     *     possible object is
     *     {@link OptionalInt }
     *     
     */
    public OptionalInt getSwitchingID() {
        return switchingID;
    }

    /**
     * Sets the value of the switchingID property.
     * 
     * @param value
     *     allowed object is
     *     {@link OptionalInt }
     *     
     */
    public void setSwitchingID(OptionalInt value) {
        this.switchingID = value;
    }

    /**
     * Gets the value of the billerRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillerRef() {
        return billerRef;
    }

    /**
     * Sets the value of the billerRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillerRef(String value) {
        this.billerRef = value;
    }

    /**
     * Gets the value of the payinstrRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayinstrRef() {
        return payinstrRef;
    }

    /**
     * Sets the value of the payinstrRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayinstrRef(String value) {
        this.payinstrRef = value;
    }

    /**
     * Gets the value of the details property.
     * 
     * @return
     *     possible object is
     *     {@link DetailArray }
     *     
     */
    public DetailArray getDetails() {
        return details;
    }

    /**
     * Sets the value of the details property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailArray }
     *     
     */
    public void setDetails(DetailArray value) {
        this.details = value;
    }

    /**
     * Gets the value of the confirmed property.
     * 
     * @return
     *     possible object is
     *     {@link OptionalBoolean }
     *     
     */
    public OptionalBoolean getConfirmed() {
        return confirmed;
    }

    /**
     * Sets the value of the confirmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link OptionalBoolean }
     *     
     */
    public void setConfirmed(OptionalBoolean value) {
        this.confirmed = value;
    }

    /**
     * Gets the value of the finished property.
     * 
     * @return
     *     possible object is
     *     {@link OptionalBoolean }
     *     
     */
    public OptionalBoolean getFinished() {
        return finished;
    }

    /**
     * Sets the value of the finished property.
     * 
     * @param value
     *     allowed object is
     *     {@link OptionalBoolean }
     *     
     */
    public void setFinished(OptionalBoolean value) {
        this.finished = value;
    }

    /**
     * Gets the value of the pinBlock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPinBlock() {
        return pinBlock;
    }

    /**
     * Sets the value of the pinBlock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPinBlock(String value) {
        this.pinBlock = value;
    }

    /**
     * Gets the value of the pinFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPinFormat() {
        return pinFormat;
    }

    /**
     * Sets the value of the pinFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPinFormat(String value) {
        this.pinFormat = value;
    }

    /**
     * Gets the value of the approved property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isApproved() {
        return approved;
    }

    /**
     * Sets the value of the approved property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setApproved(Boolean value) {
        this.approved = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getAny() {
        return any;
    }

    /**
     * Sets the value of the any property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setAny(Object value) {
        this.any = value;
    }

}

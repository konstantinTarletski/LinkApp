
package lv.bank.cards.soap.api.atm.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
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
 *         &amp;lt;element name="sessionID" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="paymentID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="paymentRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="details" type="{urn:PaymentServer}DetailArray" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="needDetails" type="{urn:PaymentServer}NeedDetailArray" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="labels" type="{urn:PaymentServer}LabelArray" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="paymentInstruments" type="{urn:PaymentServer}PaymentInstrumentArray" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="confirmation" type="{urn:PaymentServer}ConfirmationParameters" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}int"/&amp;gt;
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
    "details",
    "needDetails",
    "labels",
    "paymentInstruments",
    "confirmation",
    "action",
    "any"
})
@XmlRootElement(name = "RequestResponse")
public class RequestResponse {

    @XmlElement(required = true)
    protected String sessionID;
    protected String paymentID;
    protected String paymentRef;
    protected DetailArray details;
    protected NeedDetailArray needDetails;
    protected LabelArray labels;
    protected PaymentInstrumentArray paymentInstruments;
    protected ConfirmationParameters confirmation;
    protected int action;
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
     * Gets the value of the needDetails property.
     * 
     * @return
     *     possible object is
     *     {@link NeedDetailArray }
     *     
     */
    public NeedDetailArray getNeedDetails() {
        return needDetails;
    }

    /**
     * Sets the value of the needDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link NeedDetailArray }
     *     
     */
    public void setNeedDetails(NeedDetailArray value) {
        this.needDetails = value;
    }

    /**
     * Gets the value of the labels property.
     * 
     * @return
     *     possible object is
     *     {@link LabelArray }
     *     
     */
    public LabelArray getLabels() {
        return labels;
    }

    /**
     * Sets the value of the labels property.
     * 
     * @param value
     *     allowed object is
     *     {@link LabelArray }
     *     
     */
    public void setLabels(LabelArray value) {
        this.labels = value;
    }

    /**
     * Gets the value of the paymentInstruments property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentInstrumentArray }
     *     
     */
    public PaymentInstrumentArray getPaymentInstruments() {
        return paymentInstruments;
    }

    /**
     * Sets the value of the paymentInstruments property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentInstrumentArray }
     *     
     */
    public void setPaymentInstruments(PaymentInstrumentArray value) {
        this.paymentInstruments = value;
    }

    /**
     * Gets the value of the confirmation property.
     * 
     * @return
     *     possible object is
     *     {@link ConfirmationParameters }
     *     
     */
    public ConfirmationParameters getConfirmation() {
        return confirmation;
    }

    /**
     * Sets the value of the confirmation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfirmationParameters }
     *     
     */
    public void setConfirmation(ConfirmationParameters value) {
        this.confirmation = value;
    }

    /**
     * Gets the value of the action property.
     * 
     */
    public int getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     */
    public void setAction(int value) {
        this.action = value;
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

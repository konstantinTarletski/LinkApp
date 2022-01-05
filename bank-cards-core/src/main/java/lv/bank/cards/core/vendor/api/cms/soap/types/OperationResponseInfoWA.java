
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for OperationResponseInfoWA complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="OperationResponseInfoWA"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="response_code" type="{http://www.w3.org/2001/XMLSchema}integer"/&amp;gt;
 *         &amp;lt;element name="error_description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="error_action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EXTERNAL_SESSION_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationResponseInfoWA", propOrder = {
    "responseCode",
    "errorDescription",
    "errorAction",
    "externalsessionid"
})
public class OperationResponseInfoWA {

    @XmlElement(name = "response_code", required = true)
    protected BigInteger responseCode;
    @XmlElement(name = "error_description")
    protected String errorDescription;
    @XmlElement(name = "error_action")
    protected String errorAction;
    @XmlElement(name = "EXTERNAL_SESSION_ID")
    protected String externalsessionid;

    /**
     * Gets the value of the responseCode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getResponseCode() {
        return responseCode;
    }

    /**
     * Sets the value of the responseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setResponseCode(BigInteger value) {
        this.responseCode = value;
    }

    /**
     * Gets the value of the errorDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Sets the value of the errorDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDescription(String value) {
        this.errorDescription = value;
    }

    /**
     * Gets the value of the errorAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorAction() {
        return errorAction;
    }

    /**
     * Sets the value of the errorAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorAction(String value) {
        this.errorAction = value;
    }

    /**
     * Gets the value of the externalsessionid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXTERNALSESSIONID() {
        return externalsessionid;
    }

    /**
     * Sets the value of the externalsessionid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXTERNALSESSIONID(String value) {
        this.externalsessionid = value;
    }

}

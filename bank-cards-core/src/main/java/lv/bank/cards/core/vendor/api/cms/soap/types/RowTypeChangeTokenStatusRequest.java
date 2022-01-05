
package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_ChangeTokenStatus_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_ChangeTokenStatus_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TOKEN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TOKEN_REF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NEW_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="REASON" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_ChangeTokenStatus_Request", propOrder = {
    "card",
    "token",
    "tokenref",
    "newstatus",
    "reason"
})
public class RowTypeChangeTokenStatusRequest {

    @XmlElement(name = "CARD")
    protected String card;
    @XmlElement(name = "TOKEN")
    protected String token;
    @XmlElement(name = "TOKEN_REF")
    protected String tokenref;
    @XmlElement(name = "NEW_STATUS", required = true)
    protected String newstatus;
    @XmlElement(name = "REASON")
    protected String reason;

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
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOKEN() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOKEN(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the tokenref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOKENREF() {
        return tokenref;
    }

    /**
     * Sets the value of the tokenref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOKENREF(String value) {
        this.tokenref = value;
    }

    /**
     * Gets the value of the newstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNEWSTATUS() {
        return newstatus;
    }

    /**
     * Sets the value of the newstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNEWSTATUS(String value) {
        this.newstatus = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREASON() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREASON(String value) {
        this.reason = value;
    }

}

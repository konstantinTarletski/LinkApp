
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_QueryAuthorizations_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_QueryAuthorizations_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="BEGIN_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="END_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AUT_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RESPONSE_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RESPONSE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOCKING_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_QueryAuthorizations_Request", propOrder = {
    "card",
    "begindate",
    "enddate",
    "auttype",
    "responsetype",
    "responsecode",
    "lockingflag"
})
public class RowTypeQueryAuthorizationsRequest {

    @XmlElement(name = "CARD", required = true)
    protected String card;
    @XmlElement(name = "BEGIN_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date begindate;
    @XmlElement(name = "END_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date enddate;
    @XmlElement(name = "AUT_TYPE")
    protected String auttype;
    @XmlElement(name = "RESPONSE_TYPE")
    protected String responsetype;
    @XmlElement(name = "RESPONSE_CODE")
    protected String responsecode;
    @XmlElement(name = "LOCKING_FLAG")
    protected String lockingflag;

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
     * Gets the value of the begindate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getBEGINDATE() {
        return begindate;
    }

    /**
     * Sets the value of the begindate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBEGINDATE(Date value) {
        this.begindate = value;
    }

    /**
     * Gets the value of the enddate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getENDDATE() {
        return enddate;
    }

    /**
     * Sets the value of the enddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setENDDATE(Date value) {
        this.enddate = value;
    }

    /**
     * Gets the value of the auttype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAUTTYPE() {
        return auttype;
    }

    /**
     * Sets the value of the auttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAUTTYPE(String value) {
        this.auttype = value;
    }

    /**
     * Gets the value of the responsetype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRESPONSETYPE() {
        return responsetype;
    }

    /**
     * Sets the value of the responsetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRESPONSETYPE(String value) {
        this.responsetype = value;
    }

    /**
     * Gets the value of the responsecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRESPONSECODE() {
        return responsecode;
    }

    /**
     * Sets the value of the responsecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRESPONSECODE(String value) {
        this.responsecode = value;
    }

    /**
     * Gets the value of the lockingflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCKINGFLAG() {
        return lockingflag;
    }

    /**
     * Sets the value of the lockingflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCKINGFLAG(String value) {
        this.lockingflag = value;
    }

}

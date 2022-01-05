
package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_RelinkAllAgreements_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_RelinkAllAgreements_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="BANK_C" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="GROUPC" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="TARGET_CLIENT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="TARGET_CLIENT_B" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="SOURCE_CLIENT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="SOURCE_CLIENT_B" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="MOMENT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="REMOVE_CLIENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_RelinkAllAgreements_Request", propOrder = {
    "bankc",
    "groupc",
    "targetclient",
    "targetclientb",
    "sourceclient",
    "sourceclientb",
    "moment",
    "removeclient"
})
public class RowTypeRelinkAllAgreementsRequest {

    @XmlElement(name = "BANK_C", required = true)
    protected String bankc;
    @XmlElement(name = "GROUPC", required = true)
    protected String groupc;
    @XmlElement(name = "TARGET_CLIENT", required = true)
    protected String targetclient;
    @XmlElement(name = "TARGET_CLIENT_B", required = true)
    protected String targetclientb;
    @XmlElement(name = "SOURCE_CLIENT", required = true)
    protected String sourceclient;
    @XmlElement(name = "SOURCE_CLIENT_B", required = true)
    protected String sourceclientb;
    @XmlElement(name = "MOMENT", required = true)
    protected String moment;
    @XmlElement(name = "REMOVE_CLIENT")
    protected String removeclient;

    /**
     * Gets the value of the bankc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKC() {
        return bankc;
    }

    /**
     * Sets the value of the bankc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKC(String value) {
        this.bankc = value;
    }

    /**
     * Gets the value of the groupc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGROUPC() {
        return groupc;
    }

    /**
     * Sets the value of the groupc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGROUPC(String value) {
        this.groupc = value;
    }

    /**
     * Gets the value of the targetclient property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTARGETCLIENT() {
        return targetclient;
    }

    /**
     * Sets the value of the targetclient property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTARGETCLIENT(String value) {
        this.targetclient = value;
    }

    /**
     * Gets the value of the targetclientb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTARGETCLIENTB() {
        return targetclientb;
    }

    /**
     * Sets the value of the targetclientb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTARGETCLIENTB(String value) {
        this.targetclientb = value;
    }

    /**
     * Gets the value of the sourceclient property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOURCECLIENT() {
        return sourceclient;
    }

    /**
     * Sets the value of the sourceclient property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOURCECLIENT(String value) {
        this.sourceclient = value;
    }

    /**
     * Gets the value of the sourceclientb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOURCECLIENTB() {
        return sourceclientb;
    }

    /**
     * Sets the value of the sourceclientb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOURCECLIENTB(String value) {
        this.sourceclientb = value;
    }

    /**
     * Gets the value of the moment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOMENT() {
        return moment;
    }

    /**
     * Sets the value of the moment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOMENT(String value) {
        this.moment = value;
    }

    /**
     * Gets the value of the removeclient property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREMOVECLIENT() {
        return removeclient;
    }

    /**
     * Sets the value of the removeclient property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREMOVECLIENT(String value) {
        this.removeclient = value;
    }

}

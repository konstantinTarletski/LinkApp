
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_AgreementReLinkRequest complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_AgreementReLinkRequest"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="BANK_C" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="GROUPC" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="TARGET_CLIENT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="TARGET_CLIENT_B" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="AGRE_NOM" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CONTRACT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="MOMENT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="KS_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_AgreementReLinkRequest", propOrder = {
    "bankc",
    "groupc",
    "targetclient",
    "targetclientb",
    "agrenom",
    "contract",
    "moment",
    "ksdate"
})
public class RowTypeAgreementReLinkRequest {

    @XmlElement(name = "BANK_C", required = true)
    protected String bankc;
    @XmlElement(name = "GROUPC", required = true)
    protected String groupc;
    @XmlElement(name = "TARGET_CLIENT", required = true)
    protected String targetclient;
    @XmlElement(name = "TARGET_CLIENT_B", required = true)
    protected String targetclientb;
    @XmlElement(name = "AGRE_NOM", required = true)
    protected String agrenom;
    @XmlElement(name = "CONTRACT", required = true)
    protected String contract;
    @XmlElement(name = "MOMENT", required = true)
    protected String moment;
    @XmlElement(name = "KS_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date ksdate;

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
     * Gets the value of the agrenom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAGRENOM() {
        return agrenom;
    }

    /**
     * Sets the value of the agrenom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAGRENOM(String value) {
        this.agrenom = value;
    }

    /**
     * Gets the value of the contract property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTRACT() {
        return contract;
    }

    /**
     * Sets the value of the contract property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTRACT(String value) {
        this.contract = value;
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
     * Gets the value of the ksdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getKSDATE() {
        return ksdate;
    }

    /**
     * Sets the value of the ksdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKSDATE(Date value) {
        this.ksdate = value;
    }

}

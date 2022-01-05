
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_TransactionHist_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_TransactionHist_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD_ACCT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ACCNT_CCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BEGIN_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *         &amp;lt;element name="END_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *         &amp;lt;element name="BANK_C" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="GROUPC" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="LOCKING_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SPEC_CARD_TRX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SPEC_ACCNT_TRX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_TransactionHist_Request", propOrder = {
    "card",
    "cardacct",
    "accntccy",
    "begindate",
    "enddate",
    "bankc",
    "groupc",
    "lockingflag",
    "speccardtrx",
    "specaccnttrx"
})
public class RowTypeTransactionHistRequest {

    @XmlElement(name = "CARD")
    protected String card;
    @XmlElement(name = "CARD_ACCT")
    protected String cardacct;
    @XmlElement(name = "ACCNT_CCY")
    protected String accntccy;
    @XmlElement(name = "BEGIN_DATE", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date begindate;
    @XmlElement(name = "END_DATE", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date enddate;
    @XmlElement(name = "BANK_C", required = true)
    protected String bankc;
    @XmlElement(name = "GROUPC", required = true)
    protected String groupc;
    @XmlElement(name = "LOCKING_FLAG")
    protected String lockingflag;
    @XmlElement(name = "SPEC_CARD_TRX")
    protected String speccardtrx;
    @XmlElement(name = "SPEC_ACCNT_TRX")
    protected String specaccnttrx;

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
     * Gets the value of the cardacct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDACCT() {
        return cardacct;
    }

    /**
     * Sets the value of the cardacct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDACCT(String value) {
        this.cardacct = value;
    }

    /**
     * Gets the value of the accntccy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACCNTCCY() {
        return accntccy;
    }

    /**
     * Sets the value of the accntccy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACCNTCCY(String value) {
        this.accntccy = value;
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

    /**
     * Gets the value of the speccardtrx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPECCARDTRX() {
        return speccardtrx;
    }

    /**
     * Sets the value of the speccardtrx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPECCARDTRX(String value) {
        this.speccardtrx = value;
    }

    /**
     * Gets the value of the specaccnttrx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPECACCNTTRX() {
        return specaccnttrx;
    }

    /**
     * Sets the value of the specaccnttrx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPECACCNTTRX(String value) {
        this.specaccnttrx = value;
    }

}

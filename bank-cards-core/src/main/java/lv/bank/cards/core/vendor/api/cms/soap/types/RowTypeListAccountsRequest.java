
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_ListAccounts_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_ListAccounts_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD_ACCT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="TRANZ_ACCT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="ACC_PRTY" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="C_ACCNT_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CCY" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="COND_SET" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CLIENT_B" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CLIENT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="F_NAMES" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="SURNAME" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="B_BR_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="OFFICE_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="MAIN_ROW" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="ACCOUNT_NO" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="AGR_STATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_ListAccounts_Request", propOrder = {
    "cardacct",
    "tranzacct",
    "status",
    "accprty",
    "caccnttype",
    "ccy",
    "condset",
    "clientb",
    "client",
    "fnames",
    "surname",
    "bbrid",
    "officeid",
    "mainrow",
    "accountno",
    "agrstatus"
})
public class RowTypeListAccountsRequest {

    @XmlElement(name = "CARD_ACCT", required = true)
    protected String cardacct;
    @XmlElement(name = "TRANZ_ACCT", required = true)
    protected String tranzacct;
    @XmlElement(name = "STATUS", required = true)
    protected String status;
    @XmlElement(name = "ACC_PRTY", required = true)
    protected String accprty;
    @XmlElement(name = "C_ACCNT_TYPE", required = true)
    protected String caccnttype;
    @XmlElement(name = "CCY", required = true)
    protected String ccy;
    @XmlElement(name = "COND_SET", required = true)
    protected String condset;
    @XmlElement(name = "CLIENT_B", required = true)
    protected String clientb;
    @XmlElement(name = "CLIENT", required = true)
    protected String client;
    @XmlElement(name = "F_NAMES", required = true)
    protected String fnames;
    @XmlElement(name = "SURNAME", required = true)
    protected String surname;
    @XmlElement(name = "B_BR_ID", required = true)
    protected BigDecimal bbrid;
    @XmlElement(name = "OFFICE_ID", required = true)
    protected BigDecimal officeid;
    @XmlElement(name = "MAIN_ROW", required = true)
    protected BigDecimal mainrow;
    @XmlElement(name = "ACCOUNT_NO", required = true)
    protected BigDecimal accountno;
    @XmlElement(name = "AGR_STATUS")
    protected String agrstatus;

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
     * Gets the value of the tranzacct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRANZACCT() {
        return tranzacct;
    }

    /**
     * Sets the value of the tranzacct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRANZACCT(String value) {
        this.tranzacct = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATUS() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATUS(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the accprty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACCPRTY() {
        return accprty;
    }

    /**
     * Sets the value of the accprty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACCPRTY(String value) {
        this.accprty = value;
    }

    /**
     * Gets the value of the caccnttype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCACCNTTYPE() {
        return caccnttype;
    }

    /**
     * Sets the value of the caccnttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCACCNTTYPE(String value) {
        this.caccnttype = value;
    }

    /**
     * Gets the value of the ccy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCCY() {
        return ccy;
    }

    /**
     * Sets the value of the ccy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCCY(String value) {
        this.ccy = value;
    }

    /**
     * Gets the value of the condset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONDSET() {
        return condset;
    }

    /**
     * Sets the value of the condset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONDSET(String value) {
        this.condset = value;
    }

    /**
     * Gets the value of the clientb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLIENTB() {
        return clientb;
    }

    /**
     * Sets the value of the clientb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLIENTB(String value) {
        this.clientb = value;
    }

    /**
     * Gets the value of the client property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLIENT() {
        return client;
    }

    /**
     * Sets the value of the client property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLIENT(String value) {
        this.client = value;
    }

    /**
     * Gets the value of the fnames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFNAMES() {
        return fnames;
    }

    /**
     * Sets the value of the fnames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFNAMES(String value) {
        this.fnames = value;
    }

    /**
     * Gets the value of the surname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSURNAME() {
        return surname;
    }

    /**
     * Sets the value of the surname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSURNAME(String value) {
        this.surname = value;
    }

    /**
     * Gets the value of the bbrid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBBRID() {
        return bbrid;
    }

    /**
     * Sets the value of the bbrid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBBRID(BigDecimal value) {
        this.bbrid = value;
    }

    /**
     * Gets the value of the officeid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOFFICEID() {
        return officeid;
    }

    /**
     * Sets the value of the officeid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOFFICEID(BigDecimal value) {
        this.officeid = value;
    }

    /**
     * Gets the value of the mainrow property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMAINROW() {
        return mainrow;
    }

    /**
     * Sets the value of the mainrow property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMAINROW(BigDecimal value) {
        this.mainrow = value;
    }

    /**
     * Gets the value of the accountno property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getACCOUNTNO() {
        return accountno;
    }

    /**
     * Sets the value of the accountno property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setACCOUNTNO(BigDecimal value) {
        this.accountno = value;
    }

    /**
     * Gets the value of the agrstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAGRSTATUS() {
        return agrstatus;
    }

    /**
     * Sets the value of the agrstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAGRSTATUS(String value) {
        this.agrstatus = value;
    }

}

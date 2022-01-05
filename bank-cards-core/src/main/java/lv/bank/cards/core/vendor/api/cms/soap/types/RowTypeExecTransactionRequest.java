
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_ExecTransaction_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_ExecTransaction_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="PAYMENT_MODE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="ACCOUNT_NO" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD_ACCT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD_ACCT_CCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EXECUTE_ON" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TRAN_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="TRAN_CCY" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="TRAN_AMNT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="BRANCH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BATCH_NR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SLIP_NR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEAL_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="COUNTERPARTY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="INTERNAL_NO" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BANK_C" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="GROUPC" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="TRAN_DATE_TIME" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EXECUTION_TYPE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BOOKING_MSG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_FEE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_CODE2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_FEE2" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_CODE3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_FEE3" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_CODE4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_FEE4" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_CODE5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_FEE5" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_CODE6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_FEE6" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_CODE7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_FEE7" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_CODE8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_FEE8" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_CODE9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_FEE9" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_CODE10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_FEE10" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CHECK_DUPL" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="INSTL_AGR_NO" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ACCNT_TYPE" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TIME_STAMP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_ExecTransaction_Request", propOrder = {
    "paymentmode",
    "accountno",
    "cardacct",
    "cardacctccy",
    "card",
    "executeon",
    "trantype",
    "tranccy",
    "tranamnt",
    "branch",
    "batchnr",
    "slipnr",
    "dealdesc",
    "counterparty",
    "internalno",
    "bankc",
    "groupc",
    "trandatetime",
    "executiontype",
    "bookingmsg",
    "trcode",
    "trfee",
    "trcode2",
    "trfee2",
    "trcode3",
    "trfee3",
    "trcode4",
    "trfee4",
    "trcode5",
    "trfee5",
    "trcode6",
    "trfee6",
    "trcode7",
    "trfee7",
    "trcode8",
    "trfee8",
    "trcode9",
    "trfee9",
    "trcode10",
    "trfee10",
    "checkdupl",
    "instlagrno",
    "accnttype",
    "timestamp"
})
public class RowTypeExecTransactionRequest {

    @XmlElement(name = "PAYMENT_MODE", required = true)
    protected String paymentmode;
    @XmlElement(name = "ACCOUNT_NO")
    protected BigDecimal accountno;
    @XmlElement(name = "CARD_ACCT")
    protected String cardacct;
    @XmlElement(name = "CARD_ACCT_CCY")
    protected String cardacctccy;
    @XmlElement(name = "CARD")
    protected String card;
    @XmlElement(name = "EXECUTE_ON", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date executeon;
    @XmlElement(name = "TRAN_TYPE", required = true)
    protected String trantype;
    @XmlElement(name = "TRAN_CCY", required = true)
    protected String tranccy;
    @XmlElement(name = "TRAN_AMNT", required = true)
    protected BigDecimal tranamnt;
    @XmlElement(name = "BRANCH")
    protected String branch;
    @XmlElement(name = "BATCH_NR")
    protected String batchnr;
    @XmlElement(name = "SLIP_NR")
    protected String slipnr;
    @XmlElement(name = "DEAL_DESC")
    protected String dealdesc;
    @XmlElement(name = "COUNTERPARTY")
    protected String counterparty;
    @XmlElement(name = "INTERNAL_NO")
    protected BigDecimal internalno;
    @XmlElement(name = "BANK_C", required = true)
    protected String bankc;
    @XmlElement(name = "GROUPC", required = true)
    protected String groupc;
    @XmlElement(name = "TRAN_DATE_TIME", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date trandatetime;
    @XmlElement(name = "EXECUTION_TYPE")
    protected BigDecimal executiontype;
    @XmlElement(name = "BOOKING_MSG")
    protected String bookingmsg;
    @XmlElement(name = "TR_CODE")
    protected String trcode;
    @XmlElement(name = "TR_FEE")
    protected BigDecimal trfee;
    @XmlElement(name = "TR_CODE2")
    protected String trcode2;
    @XmlElement(name = "TR_FEE2")
    protected BigDecimal trfee2;
    @XmlElement(name = "TR_CODE3")
    protected String trcode3;
    @XmlElement(name = "TR_FEE3")
    protected BigDecimal trfee3;
    @XmlElement(name = "TR_CODE4")
    protected String trcode4;
    @XmlElement(name = "TR_FEE4")
    protected BigDecimal trfee4;
    @XmlElement(name = "TR_CODE5")
    protected String trcode5;
    @XmlElement(name = "TR_FEE5")
    protected BigDecimal trfee5;
    @XmlElement(name = "TR_CODE6")
    protected String trcode6;
    @XmlElement(name = "TR_FEE6")
    protected BigDecimal trfee6;
    @XmlElement(name = "TR_CODE7")
    protected String trcode7;
    @XmlElement(name = "TR_FEE7")
    protected BigDecimal trfee7;
    @XmlElement(name = "TR_CODE8")
    protected String trcode8;
    @XmlElement(name = "TR_FEE8")
    protected BigDecimal trfee8;
    @XmlElement(name = "TR_CODE9")
    protected String trcode9;
    @XmlElement(name = "TR_FEE9")
    protected BigDecimal trfee9;
    @XmlElement(name = "TR_CODE10")
    protected String trcode10;
    @XmlElement(name = "TR_FEE10")
    protected BigDecimal trfee10;
    @XmlElement(name = "CHECK_DUPL")
    protected BigInteger checkdupl;
    @XmlElement(name = "INSTL_AGR_NO")
    protected BigDecimal instlagrno;
    @XmlElement(name = "ACCNT_TYPE")
    protected BigInteger accnttype;
    @XmlElement(name = "TIME_STAMP")
    protected String timestamp;

    /**
     * Gets the value of the paymentmode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAYMENTMODE() {
        return paymentmode;
    }

    /**
     * Sets the value of the paymentmode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAYMENTMODE(String value) {
        this.paymentmode = value;
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
     * Gets the value of the cardacctccy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDACCTCCY() {
        return cardacctccy;
    }

    /**
     * Sets the value of the cardacctccy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDACCTCCY(String value) {
        this.cardacctccy = value;
    }

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
     * Gets the value of the executeon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getEXECUTEON() {
        return executeon;
    }

    /**
     * Sets the value of the executeon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXECUTEON(Date value) {
        this.executeon = value;
    }

    /**
     * Gets the value of the trantype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRANTYPE() {
        return trantype;
    }

    /**
     * Sets the value of the trantype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRANTYPE(String value) {
        this.trantype = value;
    }

    /**
     * Gets the value of the tranccy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRANCCY() {
        return tranccy;
    }

    /**
     * Sets the value of the tranccy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRANCCY(String value) {
        this.tranccy = value;
    }

    /**
     * Gets the value of the tranamnt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRANAMNT() {
        return tranamnt;
    }

    /**
     * Sets the value of the tranamnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRANAMNT(BigDecimal value) {
        this.tranamnt = value;
    }

    /**
     * Gets the value of the branch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBRANCH() {
        return branch;
    }

    /**
     * Sets the value of the branch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBRANCH(String value) {
        this.branch = value;
    }

    /**
     * Gets the value of the batchnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBATCHNR() {
        return batchnr;
    }

    /**
     * Sets the value of the batchnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBATCHNR(String value) {
        this.batchnr = value;
    }

    /**
     * Gets the value of the slipnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSLIPNR() {
        return slipnr;
    }

    /**
     * Sets the value of the slipnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSLIPNR(String value) {
        this.slipnr = value;
    }

    /**
     * Gets the value of the dealdesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEALDESC() {
        return dealdesc;
    }

    /**
     * Sets the value of the dealdesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEALDESC(String value) {
        this.dealdesc = value;
    }

    /**
     * Gets the value of the counterparty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOUNTERPARTY() {
        return counterparty;
    }

    /**
     * Sets the value of the counterparty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOUNTERPARTY(String value) {
        this.counterparty = value;
    }

    /**
     * Gets the value of the internalno property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getINTERNALNO() {
        return internalno;
    }

    /**
     * Sets the value of the internalno property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setINTERNALNO(BigDecimal value) {
        this.internalno = value;
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
     * Gets the value of the trandatetime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getTRANDATETIME() {
        return trandatetime;
    }

    /**
     * Sets the value of the trandatetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRANDATETIME(Date value) {
        this.trandatetime = value;
    }

    /**
     * Gets the value of the executiontype property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEXECUTIONTYPE() {
        return executiontype;
    }

    /**
     * Sets the value of the executiontype property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEXECUTIONTYPE(BigDecimal value) {
        this.executiontype = value;
    }

    /**
     * Gets the value of the bookingmsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOOKINGMSG() {
        return bookingmsg;
    }

    /**
     * Sets the value of the bookingmsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOOKINGMSG(String value) {
        this.bookingmsg = value;
    }

    /**
     * Gets the value of the trcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRCODE() {
        return trcode;
    }

    /**
     * Sets the value of the trcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRCODE(String value) {
        this.trcode = value;
    }

    /**
     * Gets the value of the trfee property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRFEE() {
        return trfee;
    }

    /**
     * Sets the value of the trfee property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRFEE(BigDecimal value) {
        this.trfee = value;
    }

    /**
     * Gets the value of the trcode2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRCODE2() {
        return trcode2;
    }

    /**
     * Sets the value of the trcode2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRCODE2(String value) {
        this.trcode2 = value;
    }

    /**
     * Gets the value of the trfee2 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRFEE2() {
        return trfee2;
    }

    /**
     * Sets the value of the trfee2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRFEE2(BigDecimal value) {
        this.trfee2 = value;
    }

    /**
     * Gets the value of the trcode3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRCODE3() {
        return trcode3;
    }

    /**
     * Sets the value of the trcode3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRCODE3(String value) {
        this.trcode3 = value;
    }

    /**
     * Gets the value of the trfee3 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRFEE3() {
        return trfee3;
    }

    /**
     * Sets the value of the trfee3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRFEE3(BigDecimal value) {
        this.trfee3 = value;
    }

    /**
     * Gets the value of the trcode4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRCODE4() {
        return trcode4;
    }

    /**
     * Sets the value of the trcode4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRCODE4(String value) {
        this.trcode4 = value;
    }

    /**
     * Gets the value of the trfee4 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRFEE4() {
        return trfee4;
    }

    /**
     * Sets the value of the trfee4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRFEE4(BigDecimal value) {
        this.trfee4 = value;
    }

    /**
     * Gets the value of the trcode5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRCODE5() {
        return trcode5;
    }

    /**
     * Sets the value of the trcode5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRCODE5(String value) {
        this.trcode5 = value;
    }

    /**
     * Gets the value of the trfee5 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRFEE5() {
        return trfee5;
    }

    /**
     * Sets the value of the trfee5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRFEE5(BigDecimal value) {
        this.trfee5 = value;
    }

    /**
     * Gets the value of the trcode6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRCODE6() {
        return trcode6;
    }

    /**
     * Sets the value of the trcode6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRCODE6(String value) {
        this.trcode6 = value;
    }

    /**
     * Gets the value of the trfee6 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRFEE6() {
        return trfee6;
    }

    /**
     * Sets the value of the trfee6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRFEE6(BigDecimal value) {
        this.trfee6 = value;
    }

    /**
     * Gets the value of the trcode7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRCODE7() {
        return trcode7;
    }

    /**
     * Sets the value of the trcode7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRCODE7(String value) {
        this.trcode7 = value;
    }

    /**
     * Gets the value of the trfee7 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRFEE7() {
        return trfee7;
    }

    /**
     * Sets the value of the trfee7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRFEE7(BigDecimal value) {
        this.trfee7 = value;
    }

    /**
     * Gets the value of the trcode8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRCODE8() {
        return trcode8;
    }

    /**
     * Sets the value of the trcode8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRCODE8(String value) {
        this.trcode8 = value;
    }

    /**
     * Gets the value of the trfee8 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRFEE8() {
        return trfee8;
    }

    /**
     * Sets the value of the trfee8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRFEE8(BigDecimal value) {
        this.trfee8 = value;
    }

    /**
     * Gets the value of the trcode9 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRCODE9() {
        return trcode9;
    }

    /**
     * Sets the value of the trcode9 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRCODE9(String value) {
        this.trcode9 = value;
    }

    /**
     * Gets the value of the trfee9 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRFEE9() {
        return trfee9;
    }

    /**
     * Sets the value of the trfee9 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRFEE9(BigDecimal value) {
        this.trfee9 = value;
    }

    /**
     * Gets the value of the trcode10 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRCODE10() {
        return trcode10;
    }

    /**
     * Sets the value of the trcode10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRCODE10(String value) {
        this.trcode10 = value;
    }

    /**
     * Gets the value of the trfee10 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRFEE10() {
        return trfee10;
    }

    /**
     * Sets the value of the trfee10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRFEE10(BigDecimal value) {
        this.trfee10 = value;
    }

    /**
     * Gets the value of the checkdupl property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCHECKDUPL() {
        return checkdupl;
    }

    /**
     * Sets the value of the checkdupl property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCHECKDUPL(BigInteger value) {
        this.checkdupl = value;
    }

    /**
     * Gets the value of the instlagrno property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getINSTLAGRNO() {
        return instlagrno;
    }

    /**
     * Sets the value of the instlagrno property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setINSTLAGRNO(BigDecimal value) {
        this.instlagrno = value;
    }

    /**
     * Gets the value of the accnttype property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getACCNTTYPE() {
        return accnttype;
    }

    /**
     * Sets the value of the accnttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setACCNTTYPE(BigInteger value) {
        this.accnttype = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIMESTAMP() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIMESTAMP(String value) {
        this.timestamp = value;
    }

}

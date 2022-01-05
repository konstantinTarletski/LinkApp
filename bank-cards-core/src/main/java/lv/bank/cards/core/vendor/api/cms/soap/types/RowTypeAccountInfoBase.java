
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_AccountInfo_Base complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_AccountInfo_Base"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="ACCOUNT_NO" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ACC_PRTY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="C_ACCNT_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD_ACCT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TRANZ_ACCT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CYCLE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MIN_BAL" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="COND_SET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STAT_CHANGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STA_COMENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AUTH_BONUS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AB_EXPIRITY" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CRD" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CRD_EXPIRY" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ATM_LIMIT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NON_REDUCE_BAL" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ADJUST_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PAY_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PAY_FREQ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CALCUL_MODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PAY_AMNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PAY_INTR" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LIM_AMNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LIM_INTR" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CREATED_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *         &amp;lt;element name="STOP_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_COD7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_COD8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="UFIELD_5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEPOSIT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEPOSIT_COMENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEPOSIT_ACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AGR_AMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_EXP_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_OPEN_F" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_FRONT_F" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_OPER_ACCT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_OPER_ACCTB" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_OPER_BACCT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IN_FILE_NUM" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="OPEN_INSTL" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="INSTL_LINE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="INSTL_CONDSET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_AccountInfo_Base", propOrder = {
    "accountno",
    "accprty",
    "caccnttype",
    "cardacct",
    "ccy",
    "tranzacct",
    "cycle",
    "minbal",
    "condset",
    "status",
    "statchange",
    "stacoment",
    "authbonus",
    "abexpirity",
    "crd",
    "crdexpiry",
    "atmlimit",
    "nonreducebal",
    "adjustflag",
    "paycode",
    "payfreq",
    "calculmode",
    "payamnt",
    "payintr",
    "limamnt",
    "limintr",
    "createddate",
    "stopdate",
    "message",
    "ucod7",
    "ucod8",
    "ufield5",
    "ufield6",
    "deposit",
    "depositcoment",
    "depositaccount",
    "agramount",
    "depexpdate",
    "depopenf",
    "depfrontf",
    "depoperacct",
    "depoperacctb",
    "depoperbacct",
    "infilenum",
    "openinstl",
    "instlline",
    "instlcondset"
})
public class RowTypeAccountInfoBase {

    @XmlElement(name = "ACCOUNT_NO")
    protected BigDecimal accountno;
    @XmlElement(name = "ACC_PRTY")
    protected String accprty;
    @XmlElement(name = "C_ACCNT_TYPE")
    protected String caccnttype;
    @XmlElement(name = "CARD_ACCT")
    protected String cardacct;
    @XmlElement(name = "CCY")
    protected String ccy;
    @XmlElement(name = "TRANZ_ACCT")
    protected String tranzacct;
    @XmlElement(name = "CYCLE")
    protected String cycle;
    @XmlElement(name = "MIN_BAL")
    protected BigDecimal minbal;
    @XmlElement(name = "COND_SET")
    protected String condset;
    @XmlElement(name = "STATUS")
    protected String status;
    @XmlElement(name = "STAT_CHANGE")
    protected String statchange;
    @XmlElement(name = "STA_COMENT")
    protected String stacoment;
    @XmlElement(name = "AUTH_BONUS")
    protected BigDecimal authbonus;
    @XmlElement(name = "AB_EXPIRITY", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date abexpirity;
    @XmlElement(name = "CRD")
    protected BigDecimal crd;
    @XmlElement(name = "CRD_EXPIRY", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date crdexpiry;
    @XmlElement(name = "ATM_LIMIT")
    protected BigDecimal atmlimit;
    @XmlElement(name = "NON_REDUCE_BAL")
    protected BigDecimal nonreducebal;
    @XmlElement(name = "ADJUST_FLAG")
    protected String adjustflag;
    @XmlElement(name = "PAY_CODE")
    protected String paycode;
    @XmlElement(name = "PAY_FREQ")
    protected String payfreq;
    @XmlElement(name = "CALCUL_MODE")
    protected String calculmode;
    @XmlElement(name = "PAY_AMNT")
    protected BigDecimal payamnt;
    @XmlElement(name = "PAY_INTR")
    protected BigDecimal payintr;
    @XmlElement(name = "LIM_AMNT")
    protected BigDecimal limamnt;
    @XmlElement(name = "LIM_INTR")
    protected BigDecimal limintr;
    @XmlElement(name = "CREATED_DATE", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date createddate;
    @XmlElement(name = "STOP_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date stopdate;
    @XmlElement(name = "MESSAGE")
    protected String message;
    @XmlElement(name = "U_COD7")
    protected String ucod7;
    @XmlElement(name = "U_COD8")
    protected String ucod8;
    @XmlElement(name = "UFIELD_5")
    protected String ufield5;
    @XmlElement(name = "U_FIELD6")
    protected String ufield6;
    @XmlElement(name = "DEPOSIT")
    protected BigDecimal deposit;
    @XmlElement(name = "DEPOSIT_COMENT")
    protected String depositcoment;
    @XmlElement(name = "DEPOSIT_ACCOUNT")
    protected String depositaccount;
    @XmlElement(name = "AGR_AMOUNT")
    protected BigDecimal agramount;
    @XmlElement(name = "DEP_EXP_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date depexpdate;
    @XmlElement(name = "DEP_OPEN_F")
    protected String depopenf;
    @XmlElement(name = "DEP_FRONT_F")
    protected String depfrontf;
    @XmlElement(name = "DEP_OPER_ACCT")
    protected BigDecimal depoperacct;
    @XmlElement(name = "DEP_OPER_ACCTB")
    protected BigDecimal depoperacctb;
    @XmlElement(name = "DEP_OPER_BACCT")
    protected String depoperbacct;
    @XmlElement(name = "IN_FILE_NUM")
    protected BigDecimal infilenum;
    @XmlElement(name = "OPEN_INSTL")
    protected BigDecimal openinstl;
    @XmlElement(name = "INSTL_LINE")
    protected BigDecimal instlline;
    @XmlElement(name = "INSTL_CONDSET")
    protected String instlcondset;

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
     * Gets the value of the cycle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCYCLE() {
        return cycle;
    }

    /**
     * Sets the value of the cycle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCYCLE(String value) {
        this.cycle = value;
    }

    /**
     * Gets the value of the minbal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMINBAL() {
        return minbal;
    }

    /**
     * Sets the value of the minbal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMINBAL(BigDecimal value) {
        this.minbal = value;
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
     * Gets the value of the statchange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATCHANGE() {
        return statchange;
    }

    /**
     * Sets the value of the statchange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATCHANGE(String value) {
        this.statchange = value;
    }

    /**
     * Gets the value of the stacoment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTACOMENT() {
        return stacoment;
    }

    /**
     * Sets the value of the stacoment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTACOMENT(String value) {
        this.stacoment = value;
    }

    /**
     * Gets the value of the authbonus property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAUTHBONUS() {
        return authbonus;
    }

    /**
     * Sets the value of the authbonus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAUTHBONUS(BigDecimal value) {
        this.authbonus = value;
    }

    /**
     * Gets the value of the abexpirity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getABEXPIRITY() {
        return abexpirity;
    }

    /**
     * Sets the value of the abexpirity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setABEXPIRITY(Date value) {
        this.abexpirity = value;
    }

    /**
     * Gets the value of the crd property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCRD() {
        return crd;
    }

    /**
     * Sets the value of the crd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCRD(BigDecimal value) {
        this.crd = value;
    }

    /**
     * Gets the value of the crdexpiry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getCRDEXPIRY() {
        return crdexpiry;
    }

    /**
     * Sets the value of the crdexpiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRDEXPIRY(Date value) {
        this.crdexpiry = value;
    }

    /**
     * Gets the value of the atmlimit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getATMLIMIT() {
        return atmlimit;
    }

    /**
     * Sets the value of the atmlimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setATMLIMIT(BigDecimal value) {
        this.atmlimit = value;
    }

    /**
     * Gets the value of the nonreducebal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNONREDUCEBAL() {
        return nonreducebal;
    }

    /**
     * Sets the value of the nonreducebal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNONREDUCEBAL(BigDecimal value) {
        this.nonreducebal = value;
    }

    /**
     * Gets the value of the adjustflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADJUSTFLAG() {
        return adjustflag;
    }

    /**
     * Sets the value of the adjustflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADJUSTFLAG(String value) {
        this.adjustflag = value;
    }

    /**
     * Gets the value of the paycode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAYCODE() {
        return paycode;
    }

    /**
     * Sets the value of the paycode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAYCODE(String value) {
        this.paycode = value;
    }

    /**
     * Gets the value of the payfreq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAYFREQ() {
        return payfreq;
    }

    /**
     * Sets the value of the payfreq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAYFREQ(String value) {
        this.payfreq = value;
    }

    /**
     * Gets the value of the calculmode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCALCULMODE() {
        return calculmode;
    }

    /**
     * Sets the value of the calculmode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCALCULMODE(String value) {
        this.calculmode = value;
    }

    /**
     * Gets the value of the payamnt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPAYAMNT() {
        return payamnt;
    }

    /**
     * Sets the value of the payamnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPAYAMNT(BigDecimal value) {
        this.payamnt = value;
    }

    /**
     * Gets the value of the payintr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPAYINTR() {
        return payintr;
    }

    /**
     * Sets the value of the payintr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPAYINTR(BigDecimal value) {
        this.payintr = value;
    }

    /**
     * Gets the value of the limamnt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLIMAMNT() {
        return limamnt;
    }

    /**
     * Sets the value of the limamnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLIMAMNT(BigDecimal value) {
        this.limamnt = value;
    }

    /**
     * Gets the value of the limintr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLIMINTR() {
        return limintr;
    }

    /**
     * Sets the value of the limintr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLIMINTR(BigDecimal value) {
        this.limintr = value;
    }

    /**
     * Gets the value of the createddate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getCREATEDDATE() {
        return createddate;
    }

    /**
     * Sets the value of the createddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCREATEDDATE(Date value) {
        this.createddate = value;
    }

    /**
     * Gets the value of the stopdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getSTOPDATE() {
        return stopdate;
    }

    /**
     * Sets the value of the stopdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTOPDATE(Date value) {
        this.stopdate = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGE() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGE(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the ucod7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCOD7() {
        return ucod7;
    }

    /**
     * Sets the value of the ucod7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCOD7(String value) {
        this.ucod7 = value;
    }

    /**
     * Gets the value of the ucod8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCOD8() {
        return ucod8;
    }

    /**
     * Sets the value of the ucod8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCOD8(String value) {
        this.ucod8 = value;
    }

    /**
     * Gets the value of the ufield5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFIELD5() {
        return ufield5;
    }

    /**
     * Sets the value of the ufield5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFIELD5(String value) {
        this.ufield5 = value;
    }

    /**
     * Gets the value of the ufield6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFIELD6() {
        return ufield6;
    }

    /**
     * Sets the value of the ufield6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFIELD6(String value) {
        this.ufield6 = value;
    }

    /**
     * Gets the value of the deposit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEPOSIT() {
        return deposit;
    }

    /**
     * Sets the value of the deposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEPOSIT(BigDecimal value) {
        this.deposit = value;
    }

    /**
     * Gets the value of the depositcoment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEPOSITCOMENT() {
        return depositcoment;
    }

    /**
     * Sets the value of the depositcoment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPOSITCOMENT(String value) {
        this.depositcoment = value;
    }

    /**
     * Gets the value of the depositaccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEPOSITACCOUNT() {
        return depositaccount;
    }

    /**
     * Sets the value of the depositaccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPOSITACCOUNT(String value) {
        this.depositaccount = value;
    }

    /**
     * Gets the value of the agramount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAGRAMOUNT() {
        return agramount;
    }

    /**
     * Sets the value of the agramount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAGRAMOUNT(BigDecimal value) {
        this.agramount = value;
    }

    /**
     * Gets the value of the depexpdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDEPEXPDATE() {
        return depexpdate;
    }

    /**
     * Sets the value of the depexpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPEXPDATE(Date value) {
        this.depexpdate = value;
    }

    /**
     * Gets the value of the depopenf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEPOPENF() {
        return depopenf;
    }

    /**
     * Sets the value of the depopenf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPOPENF(String value) {
        this.depopenf = value;
    }

    /**
     * Gets the value of the depfrontf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEPFRONTF() {
        return depfrontf;
    }

    /**
     * Sets the value of the depfrontf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPFRONTF(String value) {
        this.depfrontf = value;
    }

    /**
     * Gets the value of the depoperacct property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEPOPERACCT() {
        return depoperacct;
    }

    /**
     * Sets the value of the depoperacct property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEPOPERACCT(BigDecimal value) {
        this.depoperacct = value;
    }

    /**
     * Gets the value of the depoperacctb property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEPOPERACCTB() {
        return depoperacctb;
    }

    /**
     * Sets the value of the depoperacctb property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEPOPERACCTB(BigDecimal value) {
        this.depoperacctb = value;
    }

    /**
     * Gets the value of the depoperbacct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEPOPERBACCT() {
        return depoperbacct;
    }

    /**
     * Sets the value of the depoperbacct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPOPERBACCT(String value) {
        this.depoperbacct = value;
    }

    /**
     * Gets the value of the infilenum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getINFILENUM() {
        return infilenum;
    }

    /**
     * Sets the value of the infilenum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setINFILENUM(BigDecimal value) {
        this.infilenum = value;
    }

    /**
     * Gets the value of the openinstl property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOPENINSTL() {
        return openinstl;
    }

    /**
     * Sets the value of the openinstl property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOPENINSTL(BigDecimal value) {
        this.openinstl = value;
    }

    /**
     * Gets the value of the instlline property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getINSTLLINE() {
        return instlline;
    }

    /**
     * Sets the value of the instlline property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setINSTLLINE(BigDecimal value) {
        this.instlline = value;
    }

    /**
     * Gets the value of the instlcondset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINSTLCONDSET() {
        return instlcondset;
    }

    /**
     * Sets the value of the instlcondset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINSTLCONDSET(String value) {
        this.instlcondset = value;
    }

}

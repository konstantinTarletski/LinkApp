
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
 * &lt;p&gt;Java class for RowType_AccountInfo_Additional complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_AccountInfo_Additional"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD_NUMB" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="IC_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOST_CARD" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="POST_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BEGIN_BAL" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="DEBIT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="CREDIT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="END_BAL" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="AVAIL_AMT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="DEBT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="DEBT1" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="PREV_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MBEGIN_BAL" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="MDEBIT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="MCREDIT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="MEND_BAL" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="MPREV_BAL" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="BRUTTO" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="USED_AMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="DBEGIN_BAL" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="DDEBIT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="DCREDIT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="DEND_BAL" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="DEP_CAP_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_INT_CUR" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_INT_LAST" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_INT_TOT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_INT_GTOT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_CUR_TRNSF" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_INT_TRNSF" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DEP_LPER_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="REVERS_SUM" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="END_BAL1" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PAYM_AMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PAYM_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PAYM_INTERNAL_NO" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TA_OTB" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="MPREV_DEBIT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MPREV_CREDIT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PROC_ID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CRD_CHANGE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_AccountInfo_Additional", propOrder = {
    "cardnumb",
    "icdate",
    "lostcard",
    "postdate",
    "beginbal",
    "debit",
    "credit",
    "endbal",
    "availamt",
    "debt",
    "debt1",
    "prevdate",
    "mbeginbal",
    "mdebit",
    "mcredit",
    "mendbal",
    "mprevbal",
    "brutto",
    "usedamount",
    "dbeginbal",
    "ddebit",
    "dcredit",
    "dendbal",
    "depcapdate",
    "depintcur",
    "depintlast",
    "depinttot",
    "depintgtot",
    "depcurtrnsf",
    "depinttrnsf",
    "deplperdate",
    "reverssum",
    "endbal1",
    "paymamount",
    "paymdate",
    "payminternalno",
    "taotb",
    "mprevdebit",
    "mprevcredit",
    "procid",
    "crdchangedate"
})
public class RowTypeAccountInfoAdditional {

    @XmlElement(name = "CARD_NUMB", required = true)
    protected BigDecimal cardnumb;
    @XmlElement(name = "IC_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date icdate;
    @XmlElement(name = "LOST_CARD", required = true)
    protected BigDecimal lostcard;
    @XmlElement(name = "POST_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date postdate;
    @XmlElement(name = "BEGIN_BAL", required = true)
    protected BigDecimal beginbal;
    @XmlElement(name = "DEBIT", required = true)
    protected BigDecimal debit;
    @XmlElement(name = "CREDIT", required = true)
    protected BigDecimal credit;
    @XmlElement(name = "END_BAL", required = true)
    protected BigDecimal endbal;
    @XmlElement(name = "AVAIL_AMT", required = true)
    protected BigDecimal availamt;
    @XmlElement(name = "DEBT", required = true)
    protected BigDecimal debt;
    @XmlElement(name = "DEBT1", required = true)
    protected BigDecimal debt1;
    @XmlElement(name = "PREV_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date prevdate;
    @XmlElement(name = "MBEGIN_BAL", required = true)
    protected BigDecimal mbeginbal;
    @XmlElement(name = "MDEBIT", required = true)
    protected BigDecimal mdebit;
    @XmlElement(name = "MCREDIT", required = true)
    protected BigDecimal mcredit;
    @XmlElement(name = "MEND_BAL", required = true)
    protected BigDecimal mendbal;
    @XmlElement(name = "MPREV_BAL", required = true)
    protected BigDecimal mprevbal;
    @XmlElement(name = "BRUTTO", required = true)
    protected BigDecimal brutto;
    @XmlElement(name = "USED_AMOUNT", required = true)
    protected BigDecimal usedamount;
    @XmlElement(name = "DBEGIN_BAL", required = true)
    protected BigDecimal dbeginbal;
    @XmlElement(name = "DDEBIT", required = true)
    protected BigDecimal ddebit;
    @XmlElement(name = "DCREDIT", required = true)
    protected BigDecimal dcredit;
    @XmlElement(name = "DEND_BAL", required = true)
    protected BigDecimal dendbal;
    @XmlElement(name = "DEP_CAP_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date depcapdate;
    @XmlElement(name = "DEP_INT_CUR")
    protected BigDecimal depintcur;
    @XmlElement(name = "DEP_INT_LAST")
    protected BigDecimal depintlast;
    @XmlElement(name = "DEP_INT_TOT")
    protected BigDecimal depinttot;
    @XmlElement(name = "DEP_INT_GTOT")
    protected BigDecimal depintgtot;
    @XmlElement(name = "DEP_CUR_TRNSF")
    protected BigDecimal depcurtrnsf;
    @XmlElement(name = "DEP_INT_TRNSF")
    protected BigDecimal depinttrnsf;
    @XmlElement(name = "DEP_LPER_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date deplperdate;
    @XmlElement(name = "REVERS_SUM", required = true)
    protected BigDecimal reverssum;
    @XmlElement(name = "END_BAL1")
    protected BigDecimal endbal1;
    @XmlElement(name = "PAYM_AMOUNT")
    protected BigDecimal paymamount;
    @XmlElement(name = "PAYM_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date paymdate;
    @XmlElement(name = "PAYM_INTERNAL_NO")
    protected BigDecimal payminternalno;
    @XmlElement(name = "TA_OTB", required = true)
    protected BigDecimal taotb;
    @XmlElement(name = "MPREV_DEBIT")
    protected BigDecimal mprevdebit;
    @XmlElement(name = "MPREV_CREDIT")
    protected BigDecimal mprevcredit;
    @XmlElement(name = "PROC_ID")
    protected BigDecimal procid;
    @XmlElement(name = "CRD_CHANGE_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date crdchangedate;

    /**
     * Gets the value of the cardnumb property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCARDNUMB() {
        return cardnumb;
    }

    /**
     * Sets the value of the cardnumb property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCARDNUMB(BigDecimal value) {
        this.cardnumb = value;
    }

    /**
     * Gets the value of the icdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getICDATE() {
        return icdate;
    }

    /**
     * Sets the value of the icdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setICDATE(Date value) {
        this.icdate = value;
    }

    /**
     * Gets the value of the lostcard property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLOSTCARD() {
        return lostcard;
    }

    /**
     * Sets the value of the lostcard property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLOSTCARD(BigDecimal value) {
        this.lostcard = value;
    }

    /**
     * Gets the value of the postdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getPOSTDATE() {
        return postdate;
    }

    /**
     * Sets the value of the postdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSTDATE(Date value) {
        this.postdate = value;
    }

    /**
     * Gets the value of the beginbal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBEGINBAL() {
        return beginbal;
    }

    /**
     * Sets the value of the beginbal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBEGINBAL(BigDecimal value) {
        this.beginbal = value;
    }

    /**
     * Gets the value of the debit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEBIT() {
        return debit;
    }

    /**
     * Sets the value of the debit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEBIT(BigDecimal value) {
        this.debit = value;
    }

    /**
     * Gets the value of the credit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCREDIT() {
        return credit;
    }

    /**
     * Sets the value of the credit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCREDIT(BigDecimal value) {
        this.credit = value;
    }

    /**
     * Gets the value of the endbal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getENDBAL() {
        return endbal;
    }

    /**
     * Sets the value of the endbal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setENDBAL(BigDecimal value) {
        this.endbal = value;
    }

    /**
     * Gets the value of the availamt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAVAILAMT() {
        return availamt;
    }

    /**
     * Sets the value of the availamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAVAILAMT(BigDecimal value) {
        this.availamt = value;
    }

    /**
     * Gets the value of the debt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEBT() {
        return debt;
    }

    /**
     * Sets the value of the debt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEBT(BigDecimal value) {
        this.debt = value;
    }

    /**
     * Gets the value of the debt1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEBT1() {
        return debt1;
    }

    /**
     * Sets the value of the debt1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEBT1(BigDecimal value) {
        this.debt1 = value;
    }

    /**
     * Gets the value of the prevdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getPREVDATE() {
        return prevdate;
    }

    /**
     * Sets the value of the prevdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPREVDATE(Date value) {
        this.prevdate = value;
    }

    /**
     * Gets the value of the mbeginbal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMBEGINBAL() {
        return mbeginbal;
    }

    /**
     * Sets the value of the mbeginbal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMBEGINBAL(BigDecimal value) {
        this.mbeginbal = value;
    }

    /**
     * Gets the value of the mdebit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMDEBIT() {
        return mdebit;
    }

    /**
     * Sets the value of the mdebit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMDEBIT(BigDecimal value) {
        this.mdebit = value;
    }

    /**
     * Gets the value of the mcredit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMCREDIT() {
        return mcredit;
    }

    /**
     * Sets the value of the mcredit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMCREDIT(BigDecimal value) {
        this.mcredit = value;
    }

    /**
     * Gets the value of the mendbal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMENDBAL() {
        return mendbal;
    }

    /**
     * Sets the value of the mendbal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMENDBAL(BigDecimal value) {
        this.mendbal = value;
    }

    /**
     * Gets the value of the mprevbal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMPREVBAL() {
        return mprevbal;
    }

    /**
     * Sets the value of the mprevbal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMPREVBAL(BigDecimal value) {
        this.mprevbal = value;
    }

    /**
     * Gets the value of the brutto property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBRUTTO() {
        return brutto;
    }

    /**
     * Sets the value of the brutto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBRUTTO(BigDecimal value) {
        this.brutto = value;
    }

    /**
     * Gets the value of the usedamount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUSEDAMOUNT() {
        return usedamount;
    }

    /**
     * Sets the value of the usedamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUSEDAMOUNT(BigDecimal value) {
        this.usedamount = value;
    }

    /**
     * Gets the value of the dbeginbal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDBEGINBAL() {
        return dbeginbal;
    }

    /**
     * Sets the value of the dbeginbal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDBEGINBAL(BigDecimal value) {
        this.dbeginbal = value;
    }

    /**
     * Gets the value of the ddebit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDDEBIT() {
        return ddebit;
    }

    /**
     * Sets the value of the ddebit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDDEBIT(BigDecimal value) {
        this.ddebit = value;
    }

    /**
     * Gets the value of the dcredit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDCREDIT() {
        return dcredit;
    }

    /**
     * Sets the value of the dcredit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDCREDIT(BigDecimal value) {
        this.dcredit = value;
    }

    /**
     * Gets the value of the dendbal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDENDBAL() {
        return dendbal;
    }

    /**
     * Sets the value of the dendbal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDENDBAL(BigDecimal value) {
        this.dendbal = value;
    }

    /**
     * Gets the value of the depcapdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDEPCAPDATE() {
        return depcapdate;
    }

    /**
     * Sets the value of the depcapdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPCAPDATE(Date value) {
        this.depcapdate = value;
    }

    /**
     * Gets the value of the depintcur property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEPINTCUR() {
        return depintcur;
    }

    /**
     * Sets the value of the depintcur property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEPINTCUR(BigDecimal value) {
        this.depintcur = value;
    }

    /**
     * Gets the value of the depintlast property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEPINTLAST() {
        return depintlast;
    }

    /**
     * Sets the value of the depintlast property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEPINTLAST(BigDecimal value) {
        this.depintlast = value;
    }

    /**
     * Gets the value of the depinttot property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEPINTTOT() {
        return depinttot;
    }

    /**
     * Sets the value of the depinttot property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEPINTTOT(BigDecimal value) {
        this.depinttot = value;
    }

    /**
     * Gets the value of the depintgtot property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEPINTGTOT() {
        return depintgtot;
    }

    /**
     * Sets the value of the depintgtot property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEPINTGTOT(BigDecimal value) {
        this.depintgtot = value;
    }

    /**
     * Gets the value of the depcurtrnsf property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEPCURTRNSF() {
        return depcurtrnsf;
    }

    /**
     * Sets the value of the depcurtrnsf property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEPCURTRNSF(BigDecimal value) {
        this.depcurtrnsf = value;
    }

    /**
     * Gets the value of the depinttrnsf property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEPINTTRNSF() {
        return depinttrnsf;
    }

    /**
     * Sets the value of the depinttrnsf property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEPINTTRNSF(BigDecimal value) {
        this.depinttrnsf = value;
    }

    /**
     * Gets the value of the deplperdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDEPLPERDATE() {
        return deplperdate;
    }

    /**
     * Sets the value of the deplperdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPLPERDATE(Date value) {
        this.deplperdate = value;
    }

    /**
     * Gets the value of the reverssum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getREVERSSUM() {
        return reverssum;
    }

    /**
     * Sets the value of the reverssum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setREVERSSUM(BigDecimal value) {
        this.reverssum = value;
    }

    /**
     * Gets the value of the endbal1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getENDBAL1() {
        return endbal1;
    }

    /**
     * Sets the value of the endbal1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setENDBAL1(BigDecimal value) {
        this.endbal1 = value;
    }

    /**
     * Gets the value of the paymamount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPAYMAMOUNT() {
        return paymamount;
    }

    /**
     * Sets the value of the paymamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPAYMAMOUNT(BigDecimal value) {
        this.paymamount = value;
    }

    /**
     * Gets the value of the paymdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getPAYMDATE() {
        return paymdate;
    }

    /**
     * Sets the value of the paymdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAYMDATE(Date value) {
        this.paymdate = value;
    }

    /**
     * Gets the value of the payminternalno property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPAYMINTERNALNO() {
        return payminternalno;
    }

    /**
     * Sets the value of the payminternalno property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPAYMINTERNALNO(BigDecimal value) {
        this.payminternalno = value;
    }

    /**
     * Gets the value of the taotb property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTAOTB() {
        return taotb;
    }

    /**
     * Sets the value of the taotb property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTAOTB(BigDecimal value) {
        this.taotb = value;
    }

    /**
     * Gets the value of the mprevdebit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMPREVDEBIT() {
        return mprevdebit;
    }

    /**
     * Sets the value of the mprevdebit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMPREVDEBIT(BigDecimal value) {
        this.mprevdebit = value;
    }

    /**
     * Gets the value of the mprevcredit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMPREVCREDIT() {
        return mprevcredit;
    }

    /**
     * Sets the value of the mprevcredit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMPREVCREDIT(BigDecimal value) {
        this.mprevcredit = value;
    }

    /**
     * Gets the value of the procid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPROCID() {
        return procid;
    }

    /**
     * Sets the value of the procid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPROCID(BigDecimal value) {
        this.procid = value;
    }

    /**
     * Gets the value of the crdchangedate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getCRDCHANGEDATE() {
        return crdchangedate;
    }

    /**
     * Sets the value of the crdchangedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRDCHANGEDATE(Date value) {
        this.crdchangedate = value;
    }

}

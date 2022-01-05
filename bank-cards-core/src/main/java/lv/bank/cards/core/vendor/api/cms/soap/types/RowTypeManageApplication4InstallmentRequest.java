
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
 * &lt;p&gt;Java class for RowType_ManageApplication4Installment_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_ManageApplication4Installment_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD_ACCT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CARD_ACCT_CCY" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CARD_ACCT_AMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ACTION" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="INSTL_APPLICATION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ROW_NUM" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STAN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TRAN_DATE_TIME" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MCC_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="REF_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="APR_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MERCHANT_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PROC_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TRAN_AMT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TRAN_CCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CHECK_SLIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_ManageApplication4Installment_Request", propOrder = {
    "cardacct",
    "cardacctccy",
    "cardacctamount",
    "action",
    "instlapplicationid",
    "code",
    "rownum",
    "stan",
    "card",
    "trandatetime",
    "mcccode",
    "refnumber",
    "aprcode",
    "merchantid",
    "proccode",
    "tranamt",
    "tranccy",
    "checkslip"
})
public class RowTypeManageApplication4InstallmentRequest {

    @XmlElement(name = "CARD_ACCT", required = true)
    protected String cardacct;
    @XmlElement(name = "CARD_ACCT_CCY", required = true)
    protected String cardacctccy;
    @XmlElement(name = "CARD_ACCT_AMOUNT")
    protected BigDecimal cardacctamount;
    @XmlElement(name = "ACTION", required = true)
    protected String action;
    @XmlElement(name = "INSTL_APPLICATION_ID")
    protected BigDecimal instlapplicationid;
    @XmlElement(name = "CODE")
    protected String code;
    @XmlElement(name = "ROW_NUM")
    protected BigDecimal rownum;
    @XmlElement(name = "STAN")
    protected String stan;
    @XmlElement(name = "CARD")
    protected String card;
    @XmlElement(name = "TRAN_DATE_TIME", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date trandatetime;
    @XmlElement(name = "MCC_CODE")
    protected String mcccode;
    @XmlElement(name = "REF_NUMBER")
    protected String refnumber;
    @XmlElement(name = "APR_CODE")
    protected String aprcode;
    @XmlElement(name = "MERCHANT_ID")
    protected String merchantid;
    @XmlElement(name = "PROC_CODE")
    protected String proccode;
    @XmlElement(name = "TRAN_AMT")
    protected BigDecimal tranamt;
    @XmlElement(name = "TRAN_CCY")
    protected String tranccy;
    @XmlElement(name = "CHECK_SLIP")
    protected String checkslip;

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
     * Gets the value of the cardacctamount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCARDACCTAMOUNT() {
        return cardacctamount;
    }

    /**
     * Sets the value of the cardacctamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCARDACCTAMOUNT(BigDecimal value) {
        this.cardacctamount = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACTION() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACTION(String value) {
        this.action = value;
    }

    /**
     * Gets the value of the instlapplicationid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getINSTLAPPLICATIONID() {
        return instlapplicationid;
    }

    /**
     * Sets the value of the instlapplicationid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setINSTLAPPLICATIONID(BigDecimal value) {
        this.instlapplicationid = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODE() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODE(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the rownum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getROWNUM() {
        return rownum;
    }

    /**
     * Sets the value of the rownum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setROWNUM(BigDecimal value) {
        this.rownum = value;
    }

    /**
     * Gets the value of the stan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTAN() {
        return stan;
    }

    /**
     * Sets the value of the stan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTAN(String value) {
        this.stan = value;
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
     * Gets the value of the mcccode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMCCCODE() {
        return mcccode;
    }

    /**
     * Sets the value of the mcccode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMCCCODE(String value) {
        this.mcccode = value;
    }

    /**
     * Gets the value of the refnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREFNUMBER() {
        return refnumber;
    }

    /**
     * Sets the value of the refnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREFNUMBER(String value) {
        this.refnumber = value;
    }

    /**
     * Gets the value of the aprcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPRCODE() {
        return aprcode;
    }

    /**
     * Sets the value of the aprcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPRCODE(String value) {
        this.aprcode = value;
    }

    /**
     * Gets the value of the merchantid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMERCHANTID() {
        return merchantid;
    }

    /**
     * Sets the value of the merchantid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMERCHANTID(String value) {
        this.merchantid = value;
    }

    /**
     * Gets the value of the proccode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROCCODE() {
        return proccode;
    }

    /**
     * Sets the value of the proccode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROCCODE(String value) {
        this.proccode = value;
    }

    /**
     * Gets the value of the tranamt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRANAMT() {
        return tranamt;
    }

    /**
     * Sets the value of the tranamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRANAMT(BigDecimal value) {
        this.tranamt = value;
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
     * Gets the value of the checkslip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCHECKSLIP() {
        return checkslip;
    }

    /**
     * Sets the value of the checkslip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCHECKSLIP(String value) {
        this.checkslip = value;
    }

}

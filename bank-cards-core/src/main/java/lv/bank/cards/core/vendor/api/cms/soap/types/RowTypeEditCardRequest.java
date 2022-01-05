
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
 * &lt;p&gt;Java class for RowType_EditCard_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_EditCard_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CARD_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BASE_SUPP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="COND_SET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RISK_LEVEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD_SERVICES_SET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="REC_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="M_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RELATION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ID_CARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="B_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CALL_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="F_NAMES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SURNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="F_NAME1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MIDLE_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SERIAL_NO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DOC_SINCE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CMPG_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="INSURANC_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="INSURANC_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CRD_HOLD_MSG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_COD9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_COD10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IN_FILE_NUM" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="OUT_FILE_NUM" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HINT_QUESTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HINT_ANSWER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PVV_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PVV_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PVV2_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PVV2_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MC_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="COND_SET_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="COND_CHANGE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CHANGE_BACK_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BRANCH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CVV1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CVV2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RENEW" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PIN_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PIN_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PIN_E_MAIL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ICO_EXPORT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MB_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PARTNER_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="VAU_Q_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="VAU_REPLACE_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="WAIVED_EMB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_EditCard_Request", propOrder = {
    "card",
    "cardtype",
    "basesupp",
    "condset",
    "risklevel",
    "cardservicesset",
    "recdate",
    "mname",
    "relation",
    "idcard",
    "bdate",
    "callid",
    "fnames",
    "surname",
    "fname1",
    "midlename",
    "serialno",
    "docsince",
    "cmpgname",
    "insuranctype",
    "insurancdate",
    "crdholdmsg",
    "ucod9",
    "ucod10",
    "ufield7",
    "ufield8",
    "infilenum",
    "outfilenum",
    "hintquestion",
    "hintanswer",
    "pvv1",
    "pvv2",
    "pvv21",
    "pvv22",
    "cardname",
    "mcname",
    "condset2",
    "condchangedate",
    "changebackdate",
    "branch",
    "ufield11",
    "ufield12",
    "ufield13",
    "ufield14",
    "cvv1",
    "cvv2",
    "renew",
    "pinflag",
    "pinphone",
    "pinemail",
    "icoexport",
    "mbphone",
    "partnerid",
    "vauqflag",
    "vaureplaceflag",
    "waivedemb"
})
public class RowTypeEditCardRequest {

    @XmlElement(name = "CARD", required = true)
    protected String card;
    @XmlElement(name = "CARD_TYPE")
    protected String cardtype;
    @XmlElement(name = "BASE_SUPP")
    protected String basesupp;
    @XmlElement(name = "COND_SET")
    protected String condset;
    @XmlElement(name = "RISK_LEVEL")
    protected String risklevel;
    @XmlElement(name = "CARD_SERVICES_SET")
    protected String cardservicesset;
    @XmlElement(name = "REC_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date recdate;
    @XmlElement(name = "M_NAME")
    protected String mname;
    @XmlElement(name = "RELATION")
    protected String relation;
    @XmlElement(name = "ID_CARD")
    protected String idcard;
    @XmlElement(name = "B_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date bdate;
    @XmlElement(name = "CALL_ID")
    protected String callid;
    @XmlElement(name = "F_NAMES")
    protected String fnames;
    @XmlElement(name = "SURNAME")
    protected String surname;
    @XmlElement(name = "F_NAME1")
    protected String fname1;
    @XmlElement(name = "MIDLE_NAME")
    protected String midlename;
    @XmlElement(name = "SERIAL_NO")
    protected String serialno;
    @XmlElement(name = "DOC_SINCE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date docsince;
    @XmlElement(name = "CMPG_NAME")
    protected String cmpgname;
    @XmlElement(name = "INSURANC_TYPE")
    protected String insuranctype;
    @XmlElement(name = "INSURANC_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date insurancdate;
    @XmlElement(name = "CRD_HOLD_MSG")
    protected String crdholdmsg;
    @XmlElement(name = "U_COD9")
    protected String ucod9;
    @XmlElement(name = "U_COD10")
    protected String ucod10;
    @XmlElement(name = "U_FIELD7")
    protected String ufield7;
    @XmlElement(name = "U_FIELD8")
    protected String ufield8;
    @XmlElement(name = "IN_FILE_NUM")
    protected BigDecimal infilenum;
    @XmlElement(name = "OUT_FILE_NUM")
    protected BigDecimal outfilenum;
    @XmlElement(name = "HINT_QUESTION")
    protected String hintquestion;
    @XmlElement(name = "HINT_ANSWER")
    protected String hintanswer;
    @XmlElement(name = "PVV_1")
    protected String pvv1;
    @XmlElement(name = "PVV_2")
    protected String pvv2;
    @XmlElement(name = "PVV2_1")
    protected String pvv21;
    @XmlElement(name = "PVV2_2")
    protected String pvv22;
    @XmlElement(name = "CARD_NAME")
    protected String cardname;
    @XmlElement(name = "MC_NAME")
    protected String mcname;
    @XmlElement(name = "COND_SET_2")
    protected String condset2;
    @XmlElement(name = "COND_CHANGE_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date condchangedate;
    @XmlElement(name = "CHANGE_BACK_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date changebackdate;
    @XmlElement(name = "BRANCH")
    protected String branch;
    @XmlElement(name = "U_FIELD11")
    protected String ufield11;
    @XmlElement(name = "U_FIELD12")
    protected String ufield12;
    @XmlElement(name = "U_FIELD13")
    protected String ufield13;
    @XmlElement(name = "U_FIELD14")
    protected String ufield14;
    @XmlElement(name = "CVV1")
    protected String cvv1;
    @XmlElement(name = "CVV2")
    protected String cvv2;
    @XmlElement(name = "RENEW")
    protected String renew;
    @XmlElement(name = "PIN_FLAG")
    protected String pinflag;
    @XmlElement(name = "PIN_PHONE")
    protected String pinphone;
    @XmlElement(name = "PIN_E_MAIL")
    protected String pinemail;
    @XmlElement(name = "ICO_EXPORT")
    protected String icoexport;
    @XmlElement(name = "MB_PHONE")
    protected String mbphone;
    @XmlElement(name = "PARTNER_ID")
    protected String partnerid;
    @XmlElement(name = "VAU_Q_FLAG")
    protected String vauqflag;
    @XmlElement(name = "VAU_REPLACE_FLAG")
    protected String vaureplaceflag;
    @XmlElement(name = "WAIVED_EMB")
    protected String waivedemb;

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
     * Gets the value of the cardtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDTYPE() {
        return cardtype;
    }

    /**
     * Sets the value of the cardtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDTYPE(String value) {
        this.cardtype = value;
    }

    /**
     * Gets the value of the basesupp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBASESUPP() {
        return basesupp;
    }

    /**
     * Sets the value of the basesupp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBASESUPP(String value) {
        this.basesupp = value;
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
     * Gets the value of the risklevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRISKLEVEL() {
        return risklevel;
    }

    /**
     * Sets the value of the risklevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRISKLEVEL(String value) {
        this.risklevel = value;
    }

    /**
     * Gets the value of the cardservicesset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDSERVICESSET() {
        return cardservicesset;
    }

    /**
     * Sets the value of the cardservicesset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDSERVICESSET(String value) {
        this.cardservicesset = value;
    }

    /**
     * Gets the value of the recdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getRECDATE() {
        return recdate;
    }

    /**
     * Sets the value of the recdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRECDATE(Date value) {
        this.recdate = value;
    }

    /**
     * Gets the value of the mname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNAME() {
        return mname;
    }

    /**
     * Sets the value of the mname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNAME(String value) {
        this.mname = value;
    }

    /**
     * Gets the value of the relation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRELATION() {
        return relation;
    }

    /**
     * Sets the value of the relation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRELATION(String value) {
        this.relation = value;
    }

    /**
     * Gets the value of the idcard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCARD() {
        return idcard;
    }

    /**
     * Sets the value of the idcard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCARD(String value) {
        this.idcard = value;
    }

    /**
     * Gets the value of the bdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getBDATE() {
        return bdate;
    }

    /**
     * Sets the value of the bdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBDATE(Date value) {
        this.bdate = value;
    }

    /**
     * Gets the value of the callid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCALLID() {
        return callid;
    }

    /**
     * Sets the value of the callid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCALLID(String value) {
        this.callid = value;
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
     * Gets the value of the fname1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFNAME1() {
        return fname1;
    }

    /**
     * Sets the value of the fname1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFNAME1(String value) {
        this.fname1 = value;
    }

    /**
     * Gets the value of the midlename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMIDLENAME() {
        return midlename;
    }

    /**
     * Sets the value of the midlename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMIDLENAME(String value) {
        this.midlename = value;
    }

    /**
     * Gets the value of the serialno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSERIALNO() {
        return serialno;
    }

    /**
     * Sets the value of the serialno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSERIALNO(String value) {
        this.serialno = value;
    }

    /**
     * Gets the value of the docsince property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDOCSINCE() {
        return docsince;
    }

    /**
     * Sets the value of the docsince property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOCSINCE(Date value) {
        this.docsince = value;
    }

    /**
     * Gets the value of the cmpgname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCMPGNAME() {
        return cmpgname;
    }

    /**
     * Sets the value of the cmpgname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCMPGNAME(String value) {
        this.cmpgname = value;
    }

    /**
     * Gets the value of the insuranctype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINSURANCTYPE() {
        return insuranctype;
    }

    /**
     * Sets the value of the insuranctype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINSURANCTYPE(String value) {
        this.insuranctype = value;
    }

    /**
     * Gets the value of the insurancdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getINSURANCDATE() {
        return insurancdate;
    }

    /**
     * Sets the value of the insurancdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINSURANCDATE(Date value) {
        this.insurancdate = value;
    }

    /**
     * Gets the value of the crdholdmsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRDHOLDMSG() {
        return crdholdmsg;
    }

    /**
     * Sets the value of the crdholdmsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRDHOLDMSG(String value) {
        this.crdholdmsg = value;
    }

    /**
     * Gets the value of the ucod9 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCOD9() {
        return ucod9;
    }

    /**
     * Sets the value of the ucod9 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCOD9(String value) {
        this.ucod9 = value;
    }

    /**
     * Gets the value of the ucod10 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCOD10() {
        return ucod10;
    }

    /**
     * Sets the value of the ucod10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCOD10(String value) {
        this.ucod10 = value;
    }

    /**
     * Gets the value of the ufield7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFIELD7() {
        return ufield7;
    }

    /**
     * Sets the value of the ufield7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFIELD7(String value) {
        this.ufield7 = value;
    }

    /**
     * Gets the value of the ufield8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFIELD8() {
        return ufield8;
    }

    /**
     * Sets the value of the ufield8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFIELD8(String value) {
        this.ufield8 = value;
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
     * Gets the value of the outfilenum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOUTFILENUM() {
        return outfilenum;
    }

    /**
     * Sets the value of the outfilenum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOUTFILENUM(BigDecimal value) {
        this.outfilenum = value;
    }

    /**
     * Gets the value of the hintquestion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHINTQUESTION() {
        return hintquestion;
    }

    /**
     * Sets the value of the hintquestion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHINTQUESTION(String value) {
        this.hintquestion = value;
    }

    /**
     * Gets the value of the hintanswer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHINTANSWER() {
        return hintanswer;
    }

    /**
     * Sets the value of the hintanswer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHINTANSWER(String value) {
        this.hintanswer = value;
    }

    /**
     * Gets the value of the pvv1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPVV1() {
        return pvv1;
    }

    /**
     * Sets the value of the pvv1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPVV1(String value) {
        this.pvv1 = value;
    }

    /**
     * Gets the value of the pvv2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPVV2() {
        return pvv2;
    }

    /**
     * Sets the value of the pvv2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPVV2(String value) {
        this.pvv2 = value;
    }

    /**
     * Gets the value of the pvv21 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPVV21() {
        return pvv21;
    }

    /**
     * Sets the value of the pvv21 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPVV21(String value) {
        this.pvv21 = value;
    }

    /**
     * Gets the value of the pvv22 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPVV22() {
        return pvv22;
    }

    /**
     * Sets the value of the pvv22 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPVV22(String value) {
        this.pvv22 = value;
    }

    /**
     * Gets the value of the cardname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDNAME() {
        return cardname;
    }

    /**
     * Sets the value of the cardname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDNAME(String value) {
        this.cardname = value;
    }

    /**
     * Gets the value of the mcname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMCNAME() {
        return mcname;
    }

    /**
     * Sets the value of the mcname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMCNAME(String value) {
        this.mcname = value;
    }

    /**
     * Gets the value of the condset2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONDSET2() {
        return condset2;
    }

    /**
     * Sets the value of the condset2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONDSET2(String value) {
        this.condset2 = value;
    }

    /**
     * Gets the value of the condchangedate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getCONDCHANGEDATE() {
        return condchangedate;
    }

    /**
     * Sets the value of the condchangedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONDCHANGEDATE(Date value) {
        this.condchangedate = value;
    }

    /**
     * Gets the value of the changebackdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getCHANGEBACKDATE() {
        return changebackdate;
    }

    /**
     * Sets the value of the changebackdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCHANGEBACKDATE(Date value) {
        this.changebackdate = value;
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
     * Gets the value of the ufield11 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFIELD11() {
        return ufield11;
    }

    /**
     * Sets the value of the ufield11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFIELD11(String value) {
        this.ufield11 = value;
    }

    /**
     * Gets the value of the ufield12 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFIELD12() {
        return ufield12;
    }

    /**
     * Sets the value of the ufield12 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFIELD12(String value) {
        this.ufield12 = value;
    }

    /**
     * Gets the value of the ufield13 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFIELD13() {
        return ufield13;
    }

    /**
     * Sets the value of the ufield13 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFIELD13(String value) {
        this.ufield13 = value;
    }

    /**
     * Gets the value of the ufield14 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFIELD14() {
        return ufield14;
    }

    /**
     * Sets the value of the ufield14 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFIELD14(String value) {
        this.ufield14 = value;
    }

    /**
     * Gets the value of the cvv1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCVV1() {
        return cvv1;
    }

    /**
     * Sets the value of the cvv1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCVV1(String value) {
        this.cvv1 = value;
    }

    /**
     * Gets the value of the cvv2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCVV2() {
        return cvv2;
    }

    /**
     * Sets the value of the cvv2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCVV2(String value) {
        this.cvv2 = value;
    }

    /**
     * Gets the value of the renew property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRENEW() {
        return renew;
    }

    /**
     * Sets the value of the renew property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRENEW(String value) {
        this.renew = value;
    }

    /**
     * Gets the value of the pinflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPINFLAG() {
        return pinflag;
    }

    /**
     * Sets the value of the pinflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPINFLAG(String value) {
        this.pinflag = value;
    }

    /**
     * Gets the value of the pinphone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPINPHONE() {
        return pinphone;
    }

    /**
     * Sets the value of the pinphone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPINPHONE(String value) {
        this.pinphone = value;
    }

    /**
     * Gets the value of the pinemail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPINEMAIL() {
        return pinemail;
    }

    /**
     * Sets the value of the pinemail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPINEMAIL(String value) {
        this.pinemail = value;
    }

    /**
     * Gets the value of the icoexport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getICOEXPORT() {
        return icoexport;
    }

    /**
     * Sets the value of the icoexport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setICOEXPORT(String value) {
        this.icoexport = value;
    }

    /**
     * Gets the value of the mbphone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMBPHONE() {
        return mbphone;
    }

    /**
     * Sets the value of the mbphone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMBPHONE(String value) {
        this.mbphone = value;
    }

    /**
     * Gets the value of the partnerid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARTNERID() {
        return partnerid;
    }

    /**
     * Sets the value of the partnerid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARTNERID(String value) {
        this.partnerid = value;
    }

    /**
     * Gets the value of the vauqflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVAUQFLAG() {
        return vauqflag;
    }

    /**
     * Sets the value of the vauqflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVAUQFLAG(String value) {
        this.vauqflag = value;
    }

    /**
     * Gets the value of the vaureplaceflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVAUREPLACEFLAG() {
        return vaureplaceflag;
    }

    /**
     * Sets the value of the vaureplaceflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVAUREPLACEFLAG(String value) {
        this.vaureplaceflag = value;
    }

    /**
     * Gets the value of the waivedemb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWAIVEDEMB() {
        return waivedemb;
    }

    /**
     * Sets the value of the waivedemb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWAIVEDEMB(String value) {
        this.waivedemb = value;
    }

}

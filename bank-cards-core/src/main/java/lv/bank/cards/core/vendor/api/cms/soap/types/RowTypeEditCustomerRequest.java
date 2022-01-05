
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
 * &lt;p&gt;Java class for RowType_EditCustomer_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_EditCustomer_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CLIENT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="BANK_C" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CLIENT_B" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CL_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CLN_CAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="REC_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="F_NAMES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SURNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TITLE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="M_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="B_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RESIDENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ID_CARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DOC_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EMP_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="POSITION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EMP_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="WORK_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_STREET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_CNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_PCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="C_SINCE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CMP_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CMPG_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CO_POSITON" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CONTACT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CNT_TITLE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CNT_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CNT_FAX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MNG_POSIT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MNG_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MNG_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MNG_TITLE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MNG_FAX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="REG_NR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CR_STREET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CR_CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CR_CNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CR_PCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="COMENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="REGION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PERSON_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RESIDENT_SINCE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="YEAR_INC" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CCY_FOR_INCOM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IMM_PROP_VALUE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SEARCH_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MARITAL_STATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CO_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EMP_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SEX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SERIAL_NO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DOC_SINCE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ISSUED_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EMP_ADR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EMP_FAX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EMP_E_MAILS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_E_MAILS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_MOB_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_FAX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CNT_E_MAILS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CNT_MOB_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MNG_MOB_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MNG_E_MAILS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CR_E_MAILS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IN_FILE_NUM" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_COD1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_COD2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_COD3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CALL_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HOME_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BUILDING" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STREET1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="APARTMENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MIDLE_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NATIONALITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AMEX_MEMBER_SINCE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DCI_MEMBER_SINCE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DISCOVER_MEMBER_SINCE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="REWARD_NO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_EditCustomer_Request", propOrder = {
    "client",
    "bankc",
    "clientb",
    "cltype",
    "clncat",
    "recdate",
    "fnames",
    "surname",
    "title",
    "mname",
    "bdate",
    "resident",
    "idcard",
    "doctype",
    "rphone",
    "empname",
    "position",
    "empdate",
    "workphone",
    "rstreet",
    "rcity",
    "rcntry",
    "rpcode",
    "csince",
    "cmpname",
    "cmpgname",
    "copositon",
    "contact",
    "cnttitle",
    "cntphone",
    "cntfax",
    "mngposit",
    "mngname",
    "mngphone",
    "mngtitle",
    "mngfax",
    "regnr",
    "crstreet",
    "crcity",
    "crcntry",
    "crpcode",
    "coment",
    "region",
    "personcode",
    "residentsince",
    "yearinc",
    "ccyforincom",
    "immpropvalue",
    "searchname",
    "maritalstatus",
    "cocode",
    "empcode",
    "sex",
    "serialno",
    "docsince",
    "issuedby",
    "empadr",
    "empfax",
    "empemails",
    "remails",
    "rmobphone",
    "rfax",
    "cntemails",
    "cntmobphone",
    "mngmobphone",
    "mngemails",
    "cremails",
    "infilenum",
    "ucod1",
    "ucod2",
    "ucod3",
    "ufield1",
    "ufield2",
    "callid",
    "homenumber",
    "building",
    "street1",
    "apartment",
    "midlename",
    "status",
    "nationality",
    "amexmembersince",
    "dcimembersince",
    "discovermembersince",
    "rewardno"
})
public class RowTypeEditCustomerRequest {

    @XmlElement(name = "CLIENT", required = true)
    protected String client;
    @XmlElement(name = "BANK_C", required = true)
    protected String bankc;
    @XmlElement(name = "CLIENT_B")
    protected String clientb;
    @XmlElement(name = "CL_TYPE")
    protected String cltype;
    @XmlElement(name = "CLN_CAT")
    protected String clncat;
    @XmlElement(name = "REC_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date recdate;
    @XmlElement(name = "F_NAMES")
    protected String fnames;
    @XmlElement(name = "SURNAME")
    protected String surname;
    @XmlElement(name = "TITLE")
    protected String title;
    @XmlElement(name = "M_NAME")
    protected String mname;
    @XmlElement(name = "B_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date bdate;
    @XmlElement(name = "RESIDENT")
    protected String resident;
    @XmlElement(name = "ID_CARD")
    protected String idcard;
    @XmlElement(name = "DOC_TYPE")
    protected String doctype;
    @XmlElement(name = "R_PHONE")
    protected String rphone;
    @XmlElement(name = "EMP_NAME")
    protected String empname;
    @XmlElement(name = "POSITION")
    protected String position;
    @XmlElement(name = "EMP_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date empdate;
    @XmlElement(name = "WORK_PHONE")
    protected String workphone;
    @XmlElement(name = "R_STREET")
    protected String rstreet;
    @XmlElement(name = "R_CITY")
    protected String rcity;
    @XmlElement(name = "R_CNTRY")
    protected String rcntry;
    @XmlElement(name = "R_PCODE")
    protected String rpcode;
    @XmlElement(name = "C_SINCE")
    protected String csince;
    @XmlElement(name = "CMP_NAME")
    protected String cmpname;
    @XmlElement(name = "CMPG_NAME")
    protected String cmpgname;
    @XmlElement(name = "CO_POSITON")
    protected String copositon;
    @XmlElement(name = "CONTACT")
    protected String contact;
    @XmlElement(name = "CNT_TITLE")
    protected String cnttitle;
    @XmlElement(name = "CNT_PHONE")
    protected String cntphone;
    @XmlElement(name = "CNT_FAX")
    protected String cntfax;
    @XmlElement(name = "MNG_POSIT")
    protected String mngposit;
    @XmlElement(name = "MNG_NAME")
    protected String mngname;
    @XmlElement(name = "MNG_PHONE")
    protected String mngphone;
    @XmlElement(name = "MNG_TITLE")
    protected String mngtitle;
    @XmlElement(name = "MNG_FAX")
    protected String mngfax;
    @XmlElement(name = "REG_NR")
    protected String regnr;
    @XmlElement(name = "CR_STREET")
    protected String crstreet;
    @XmlElement(name = "CR_CITY")
    protected String crcity;
    @XmlElement(name = "CR_CNTRY")
    protected String crcntry;
    @XmlElement(name = "CR_PCODE")
    protected String crpcode;
    @XmlElement(name = "COMENT")
    protected String coment;
    @XmlElement(name = "REGION")
    protected String region;
    @XmlElement(name = "PERSON_CODE")
    protected String personcode;
    @XmlElement(name = "RESIDENT_SINCE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date residentsince;
    @XmlElement(name = "YEAR_INC")
    protected BigDecimal yearinc;
    @XmlElement(name = "CCY_FOR_INCOM")
    protected String ccyforincom;
    @XmlElement(name = "IMM_PROP_VALUE")
    protected BigDecimal immpropvalue;
    @XmlElement(name = "SEARCH_NAME")
    protected String searchname;
    @XmlElement(name = "MARITAL_STATUS")
    protected String maritalstatus;
    @XmlElement(name = "CO_CODE")
    protected String cocode;
    @XmlElement(name = "EMP_CODE")
    protected String empcode;
    @XmlElement(name = "SEX")
    protected String sex;
    @XmlElement(name = "SERIAL_NO")
    protected String serialno;
    @XmlElement(name = "DOC_SINCE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date docsince;
    @XmlElement(name = "ISSUED_BY")
    protected String issuedby;
    @XmlElement(name = "EMP_ADR")
    protected String empadr;
    @XmlElement(name = "EMP_FAX")
    protected String empfax;
    @XmlElement(name = "EMP_E_MAILS")
    protected String empemails;
    @XmlElement(name = "R_E_MAILS")
    protected String remails;
    @XmlElement(name = "R_MOB_PHONE")
    protected String rmobphone;
    @XmlElement(name = "R_FAX")
    protected String rfax;
    @XmlElement(name = "CNT_E_MAILS")
    protected String cntemails;
    @XmlElement(name = "CNT_MOB_PHONE")
    protected String cntmobphone;
    @XmlElement(name = "MNG_MOB_PHONE")
    protected String mngmobphone;
    @XmlElement(name = "MNG_E_MAILS")
    protected String mngemails;
    @XmlElement(name = "CR_E_MAILS")
    protected String cremails;
    @XmlElement(name = "IN_FILE_NUM")
    protected BigDecimal infilenum;
    @XmlElement(name = "U_COD1")
    protected String ucod1;
    @XmlElement(name = "U_COD2")
    protected String ucod2;
    @XmlElement(name = "U_COD3")
    protected String ucod3;
    @XmlElement(name = "U_FIELD1")
    protected String ufield1;
    @XmlElement(name = "U_FIELD2")
    protected String ufield2;
    @XmlElement(name = "CALL_ID")
    protected String callid;
    @XmlElement(name = "HOME_NUMBER")
    protected String homenumber;
    @XmlElement(name = "BUILDING")
    protected String building;
    @XmlElement(name = "STREET1")
    protected String street1;
    @XmlElement(name = "APARTMENT")
    protected String apartment;
    @XmlElement(name = "MIDLE_NAME")
    protected String midlename;
    @XmlElement(name = "STATUS")
    protected String status;
    @XmlElement(name = "NATIONALITY")
    protected String nationality;
    @XmlElement(name = "AMEX_MEMBER_SINCE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date amexmembersince;
    @XmlElement(name = "DCI_MEMBER_SINCE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date dcimembersince;
    @XmlElement(name = "DISCOVER_MEMBER_SINCE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date discovermembersince;
    @XmlElement(name = "REWARD_NO")
    protected String rewardno;

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
     * Gets the value of the cltype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLTYPE() {
        return cltype;
    }

    /**
     * Sets the value of the cltype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLTYPE(String value) {
        this.cltype = value;
    }

    /**
     * Gets the value of the clncat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLNCAT() {
        return clncat;
    }

    /**
     * Sets the value of the clncat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLNCAT(String value) {
        this.clncat = value;
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
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTITLE() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTITLE(String value) {
        this.title = value;
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
     * Gets the value of the resident property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRESIDENT() {
        return resident;
    }

    /**
     * Sets the value of the resident property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRESIDENT(String value) {
        this.resident = value;
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
     * Gets the value of the doctype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOCTYPE() {
        return doctype;
    }

    /**
     * Sets the value of the doctype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOCTYPE(String value) {
        this.doctype = value;
    }

    /**
     * Gets the value of the rphone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRPHONE() {
        return rphone;
    }

    /**
     * Sets the value of the rphone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRPHONE(String value) {
        this.rphone = value;
    }

    /**
     * Gets the value of the empname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPNAME() {
        return empname;
    }

    /**
     * Sets the value of the empname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPNAME(String value) {
        this.empname = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSITION() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSITION(String value) {
        this.position = value;
    }

    /**
     * Gets the value of the empdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getEMPDATE() {
        return empdate;
    }

    /**
     * Sets the value of the empdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPDATE(Date value) {
        this.empdate = value;
    }

    /**
     * Gets the value of the workphone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWORKPHONE() {
        return workphone;
    }

    /**
     * Sets the value of the workphone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWORKPHONE(String value) {
        this.workphone = value;
    }

    /**
     * Gets the value of the rstreet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRSTREET() {
        return rstreet;
    }

    /**
     * Sets the value of the rstreet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRSTREET(String value) {
        this.rstreet = value;
    }

    /**
     * Gets the value of the rcity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRCITY() {
        return rcity;
    }

    /**
     * Sets the value of the rcity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRCITY(String value) {
        this.rcity = value;
    }

    /**
     * Gets the value of the rcntry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRCNTRY() {
        return rcntry;
    }

    /**
     * Sets the value of the rcntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRCNTRY(String value) {
        this.rcntry = value;
    }

    /**
     * Gets the value of the rpcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRPCODE() {
        return rpcode;
    }

    /**
     * Sets the value of the rpcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRPCODE(String value) {
        this.rpcode = value;
    }

    /**
     * Gets the value of the csince property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCSINCE() {
        return csince;
    }

    /**
     * Sets the value of the csince property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCSINCE(String value) {
        this.csince = value;
    }

    /**
     * Gets the value of the cmpname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCMPNAME() {
        return cmpname;
    }

    /**
     * Sets the value of the cmpname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCMPNAME(String value) {
        this.cmpname = value;
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
     * Gets the value of the copositon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOPOSITON() {
        return copositon;
    }

    /**
     * Sets the value of the copositon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOPOSITON(String value) {
        this.copositon = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACT() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACT(String value) {
        this.contact = value;
    }

    /**
     * Gets the value of the cnttitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCNTTITLE() {
        return cnttitle;
    }

    /**
     * Sets the value of the cnttitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCNTTITLE(String value) {
        this.cnttitle = value;
    }

    /**
     * Gets the value of the cntphone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCNTPHONE() {
        return cntphone;
    }

    /**
     * Sets the value of the cntphone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCNTPHONE(String value) {
        this.cntphone = value;
    }

    /**
     * Gets the value of the cntfax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCNTFAX() {
        return cntfax;
    }

    /**
     * Sets the value of the cntfax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCNTFAX(String value) {
        this.cntfax = value;
    }

    /**
     * Gets the value of the mngposit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNGPOSIT() {
        return mngposit;
    }

    /**
     * Sets the value of the mngposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNGPOSIT(String value) {
        this.mngposit = value;
    }

    /**
     * Gets the value of the mngname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNGNAME() {
        return mngname;
    }

    /**
     * Sets the value of the mngname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNGNAME(String value) {
        this.mngname = value;
    }

    /**
     * Gets the value of the mngphone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNGPHONE() {
        return mngphone;
    }

    /**
     * Sets the value of the mngphone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNGPHONE(String value) {
        this.mngphone = value;
    }

    /**
     * Gets the value of the mngtitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNGTITLE() {
        return mngtitle;
    }

    /**
     * Sets the value of the mngtitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNGTITLE(String value) {
        this.mngtitle = value;
    }

    /**
     * Gets the value of the mngfax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNGFAX() {
        return mngfax;
    }

    /**
     * Sets the value of the mngfax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNGFAX(String value) {
        this.mngfax = value;
    }

    /**
     * Gets the value of the regnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGNR() {
        return regnr;
    }

    /**
     * Sets the value of the regnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGNR(String value) {
        this.regnr = value;
    }

    /**
     * Gets the value of the crstreet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRSTREET() {
        return crstreet;
    }

    /**
     * Sets the value of the crstreet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRSTREET(String value) {
        this.crstreet = value;
    }

    /**
     * Gets the value of the crcity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRCITY() {
        return crcity;
    }

    /**
     * Sets the value of the crcity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRCITY(String value) {
        this.crcity = value;
    }

    /**
     * Gets the value of the crcntry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRCNTRY() {
        return crcntry;
    }

    /**
     * Sets the value of the crcntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRCNTRY(String value) {
        this.crcntry = value;
    }

    /**
     * Gets the value of the crpcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRPCODE() {
        return crpcode;
    }

    /**
     * Sets the value of the crpcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRPCODE(String value) {
        this.crpcode = value;
    }

    /**
     * Gets the value of the coment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMENT() {
        return coment;
    }

    /**
     * Sets the value of the coment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMENT(String value) {
        this.coment = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGION() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGION(String value) {
        this.region = value;
    }

    /**
     * Gets the value of the personcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSONCODE() {
        return personcode;
    }

    /**
     * Sets the value of the personcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSONCODE(String value) {
        this.personcode = value;
    }

    /**
     * Gets the value of the residentsince property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getRESIDENTSINCE() {
        return residentsince;
    }

    /**
     * Sets the value of the residentsince property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRESIDENTSINCE(Date value) {
        this.residentsince = value;
    }

    /**
     * Gets the value of the yearinc property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getYEARINC() {
        return yearinc;
    }

    /**
     * Sets the value of the yearinc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setYEARINC(BigDecimal value) {
        this.yearinc = value;
    }

    /**
     * Gets the value of the ccyforincom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCCYFORINCOM() {
        return ccyforincom;
    }

    /**
     * Sets the value of the ccyforincom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCCYFORINCOM(String value) {
        this.ccyforincom = value;
    }

    /**
     * Gets the value of the immpropvalue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMMPROPVALUE() {
        return immpropvalue;
    }

    /**
     * Sets the value of the immpropvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMMPROPVALUE(BigDecimal value) {
        this.immpropvalue = value;
    }

    /**
     * Gets the value of the searchname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEARCHNAME() {
        return searchname;
    }

    /**
     * Sets the value of the searchname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEARCHNAME(String value) {
        this.searchname = value;
    }

    /**
     * Gets the value of the maritalstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMARITALSTATUS() {
        return maritalstatus;
    }

    /**
     * Sets the value of the maritalstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMARITALSTATUS(String value) {
        this.maritalstatus = value;
    }

    /**
     * Gets the value of the cocode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOCODE() {
        return cocode;
    }

    /**
     * Sets the value of the cocode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOCODE(String value) {
        this.cocode = value;
    }

    /**
     * Gets the value of the empcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPCODE() {
        return empcode;
    }

    /**
     * Sets the value of the empcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPCODE(String value) {
        this.empcode = value;
    }

    /**
     * Gets the value of the sex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEX() {
        return sex;
    }

    /**
     * Sets the value of the sex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEX(String value) {
        this.sex = value;
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
     * Gets the value of the issuedby property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISSUEDBY() {
        return issuedby;
    }

    /**
     * Sets the value of the issuedby property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISSUEDBY(String value) {
        this.issuedby = value;
    }

    /**
     * Gets the value of the empadr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPADR() {
        return empadr;
    }

    /**
     * Sets the value of the empadr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPADR(String value) {
        this.empadr = value;
    }

    /**
     * Gets the value of the empfax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPFAX() {
        return empfax;
    }

    /**
     * Sets the value of the empfax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPFAX(String value) {
        this.empfax = value;
    }

    /**
     * Gets the value of the empemails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPEMAILS() {
        return empemails;
    }

    /**
     * Sets the value of the empemails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPEMAILS(String value) {
        this.empemails = value;
    }

    /**
     * Gets the value of the remails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREMAILS() {
        return remails;
    }

    /**
     * Sets the value of the remails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREMAILS(String value) {
        this.remails = value;
    }

    /**
     * Gets the value of the rmobphone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRMOBPHONE() {
        return rmobphone;
    }

    /**
     * Sets the value of the rmobphone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRMOBPHONE(String value) {
        this.rmobphone = value;
    }

    /**
     * Gets the value of the rfax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRFAX() {
        return rfax;
    }

    /**
     * Sets the value of the rfax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRFAX(String value) {
        this.rfax = value;
    }

    /**
     * Gets the value of the cntemails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCNTEMAILS() {
        return cntemails;
    }

    /**
     * Sets the value of the cntemails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCNTEMAILS(String value) {
        this.cntemails = value;
    }

    /**
     * Gets the value of the cntmobphone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCNTMOBPHONE() {
        return cntmobphone;
    }

    /**
     * Sets the value of the cntmobphone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCNTMOBPHONE(String value) {
        this.cntmobphone = value;
    }

    /**
     * Gets the value of the mngmobphone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNGMOBPHONE() {
        return mngmobphone;
    }

    /**
     * Sets the value of the mngmobphone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNGMOBPHONE(String value) {
        this.mngmobphone = value;
    }

    /**
     * Gets the value of the mngemails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNGEMAILS() {
        return mngemails;
    }

    /**
     * Sets the value of the mngemails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNGEMAILS(String value) {
        this.mngemails = value;
    }

    /**
     * Gets the value of the cremails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCREMAILS() {
        return cremails;
    }

    /**
     * Sets the value of the cremails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCREMAILS(String value) {
        this.cremails = value;
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
     * Gets the value of the ucod1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCOD1() {
        return ucod1;
    }

    /**
     * Sets the value of the ucod1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCOD1(String value) {
        this.ucod1 = value;
    }

    /**
     * Gets the value of the ucod2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCOD2() {
        return ucod2;
    }

    /**
     * Sets the value of the ucod2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCOD2(String value) {
        this.ucod2 = value;
    }

    /**
     * Gets the value of the ucod3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCOD3() {
        return ucod3;
    }

    /**
     * Sets the value of the ucod3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCOD3(String value) {
        this.ucod3 = value;
    }

    /**
     * Gets the value of the ufield1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFIELD1() {
        return ufield1;
    }

    /**
     * Sets the value of the ufield1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFIELD1(String value) {
        this.ufield1 = value;
    }

    /**
     * Gets the value of the ufield2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFIELD2() {
        return ufield2;
    }

    /**
     * Sets the value of the ufield2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFIELD2(String value) {
        this.ufield2 = value;
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
     * Gets the value of the homenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHOMENUMBER() {
        return homenumber;
    }

    /**
     * Sets the value of the homenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHOMENUMBER(String value) {
        this.homenumber = value;
    }

    /**
     * Gets the value of the building property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUILDING() {
        return building;
    }

    /**
     * Sets the value of the building property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUILDING(String value) {
        this.building = value;
    }

    /**
     * Gets the value of the street1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTREET1() {
        return street1;
    }

    /**
     * Sets the value of the street1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTREET1(String value) {
        this.street1 = value;
    }

    /**
     * Gets the value of the apartment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPARTMENT() {
        return apartment;
    }

    /**
     * Sets the value of the apartment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPARTMENT(String value) {
        this.apartment = value;
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
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNATIONALITY() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNATIONALITY(String value) {
        this.nationality = value;
    }

    /**
     * Gets the value of the amexmembersince property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getAMEXMEMBERSINCE() {
        return amexmembersince;
    }

    /**
     * Sets the value of the amexmembersince property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAMEXMEMBERSINCE(Date value) {
        this.amexmembersince = value;
    }

    /**
     * Gets the value of the dcimembersince property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDCIMEMBERSINCE() {
        return dcimembersince;
    }

    /**
     * Sets the value of the dcimembersince property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDCIMEMBERSINCE(Date value) {
        this.dcimembersince = value;
    }

    /**
     * Gets the value of the discovermembersince property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDISCOVERMEMBERSINCE() {
        return discovermembersince;
    }

    /**
     * Sets the value of the discovermembersince property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDISCOVERMEMBERSINCE(Date value) {
        this.discovermembersince = value;
    }

    /**
     * Gets the value of the rewardno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREWARDNO() {
        return rewardno;
    }

    /**
     * Sets the value of the rewardno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREWARDNO(String value) {
        this.rewardno = value;
    }

}

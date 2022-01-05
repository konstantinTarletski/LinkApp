
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
 * &lt;p&gt;Java class for RowType_Agreement complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_Agreement"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="AGRE_NOM" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BINCOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BANK_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BRANCH" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="B_BR_ID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="OFFICE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="OFFICE_ID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CLIENT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="COMENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CONTRACT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DISTRIB_MODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ENROLLED" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *         &amp;lt;element name="E_MAILS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ISURANCE_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="POST_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PRODUCT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="REP_LANG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RISK_LEVEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STREET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_COD4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_CODE5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_CODE6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IN_FILE_NUM" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CTIME" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *         &amp;lt;element name="USRID" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="COMBI_ID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="COMBI_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_Agreement", propOrder = {
    "agrenom",
    "bincod",
    "bankcode",
    "branch",
    "bbrid",
    "office",
    "officeid",
    "city",
    "client",
    "coment",
    "contract",
    "country",
    "distribmode",
    "enrolled",
    "emails",
    "isurancetype",
    "postind",
    "product",
    "replang",
    "risklevel",
    "street",
    "ucod4",
    "ucode5",
    "ucode6",
    "ufield3",
    "ufield4",
    "infilenum",
    "status",
    "ctime",
    "usrid",
    "combiid",
    "combitype"
})
public class RowTypeAgreement {

    @XmlElement(name = "AGRE_NOM")
    protected BigDecimal agrenom;
    @XmlElement(name = "BINCOD")
    protected String bincod;
    @XmlElement(name = "BANK_CODE")
    protected String bankcode;
    @XmlElement(name = "BRANCH", required = true)
    protected String branch;
    @XmlElement(name = "B_BR_ID")
    protected BigDecimal bbrid;
    @XmlElement(name = "OFFICE")
    protected String office;
    @XmlElement(name = "OFFICE_ID")
    protected BigDecimal officeid;
    @XmlElement(name = "CITY")
    protected String city;
    @XmlElement(name = "CLIENT", required = true)
    protected String client;
    @XmlElement(name = "COMENT")
    protected String coment;
    @XmlElement(name = "CONTRACT", required = true)
    protected String contract;
    @XmlElement(name = "COUNTRY")
    protected String country;
    @XmlElement(name = "DISTRIB_MODE")
    protected String distribmode;
    @XmlElement(name = "ENROLLED", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date enrolled;
    @XmlElement(name = "E_MAILS")
    protected String emails;
    @XmlElement(name = "ISURANCE_TYPE")
    protected String isurancetype;
    @XmlElement(name = "POST_IND")
    protected String postind;
    @XmlElement(name = "PRODUCT")
    protected String product;
    @XmlElement(name = "REP_LANG")
    protected String replang;
    @XmlElement(name = "RISK_LEVEL")
    protected String risklevel;
    @XmlElement(name = "STREET")
    protected String street;
    @XmlElement(name = "U_COD4")
    protected String ucod4;
    @XmlElement(name = "U_CODE5")
    protected String ucode5;
    @XmlElement(name = "U_CODE6")
    protected String ucode6;
    @XmlElement(name = "U_FIELD3")
    protected String ufield3;
    @XmlElement(name = "U_FIELD4")
    protected String ufield4;
    @XmlElement(name = "IN_FILE_NUM")
    protected BigDecimal infilenum;
    @XmlElement(name = "STATUS")
    protected String status;
    @XmlElement(name = "CTIME", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date ctime;
    @XmlElement(name = "USRID", required = true)
    protected String usrid;
    @XmlElement(name = "COMBI_ID")
    protected BigDecimal combiid;
    @XmlElement(name = "COMBI_TYPE")
    protected String combitype;

    /**
     * Gets the value of the agrenom property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAGRENOM() {
        return agrenom;
    }

    /**
     * Sets the value of the agrenom property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAGRENOM(BigDecimal value) {
        this.agrenom = value;
    }

    /**
     * Gets the value of the bincod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBINCOD() {
        return bincod;
    }

    /**
     * Sets the value of the bincod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBINCOD(String value) {
        this.bincod = value;
    }

    /**
     * Gets the value of the bankcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKCODE() {
        return bankcode;
    }

    /**
     * Sets the value of the bankcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKCODE(String value) {
        this.bankcode = value;
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
     * Gets the value of the office property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOFFICE() {
        return office;
    }

    /**
     * Sets the value of the office property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOFFICE(String value) {
        this.office = value;
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
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCITY() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCITY(String value) {
        this.city = value;
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
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOUNTRY() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOUNTRY(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the distribmode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDISTRIBMODE() {
        return distribmode;
    }

    /**
     * Sets the value of the distribmode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDISTRIBMODE(String value) {
        this.distribmode = value;
    }

    /**
     * Gets the value of the enrolled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getENROLLED() {
        return enrolled;
    }

    /**
     * Sets the value of the enrolled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setENROLLED(Date value) {
        this.enrolled = value;
    }

    /**
     * Gets the value of the emails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMAILS() {
        return emails;
    }

    /**
     * Sets the value of the emails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMAILS(String value) {
        this.emails = value;
    }

    /**
     * Gets the value of the isurancetype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISURANCETYPE() {
        return isurancetype;
    }

    /**
     * Sets the value of the isurancetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISURANCETYPE(String value) {
        this.isurancetype = value;
    }

    /**
     * Gets the value of the postind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSTIND() {
        return postind;
    }

    /**
     * Sets the value of the postind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSTIND(String value) {
        this.postind = value;
    }

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRODUCT() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRODUCT(String value) {
        this.product = value;
    }

    /**
     * Gets the value of the replang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREPLANG() {
        return replang;
    }

    /**
     * Sets the value of the replang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREPLANG(String value) {
        this.replang = value;
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
     * Gets the value of the street property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTREET() {
        return street;
    }

    /**
     * Sets the value of the street property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTREET(String value) {
        this.street = value;
    }

    /**
     * Gets the value of the ucod4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCOD4() {
        return ucod4;
    }

    /**
     * Sets the value of the ucod4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCOD4(String value) {
        this.ucod4 = value;
    }

    /**
     * Gets the value of the ucode5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCODE5() {
        return ucode5;
    }

    /**
     * Sets the value of the ucode5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCODE5(String value) {
        this.ucode5 = value;
    }

    /**
     * Gets the value of the ucode6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUCODE6() {
        return ucode6;
    }

    /**
     * Sets the value of the ucode6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUCODE6(String value) {
        this.ucode6 = value;
    }

    /**
     * Gets the value of the ufield3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFIELD3() {
        return ufield3;
    }

    /**
     * Sets the value of the ufield3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFIELD3(String value) {
        this.ufield3 = value;
    }

    /**
     * Gets the value of the ufield4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUFIELD4() {
        return ufield4;
    }

    /**
     * Sets the value of the ufield4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUFIELD4(String value) {
        this.ufield4 = value;
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
     * Gets the value of the ctime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getCTIME() {
        return ctime;
    }

    /**
     * Sets the value of the ctime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCTIME(Date value) {
        this.ctime = value;
    }

    /**
     * Gets the value of the usrid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSRID() {
        return usrid;
    }

    /**
     * Sets the value of the usrid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSRID(String value) {
        this.usrid = value;
    }

    /**
     * Gets the value of the combiid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCOMBIID() {
        return combiid;
    }

    /**
     * Sets the value of the combiid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCOMBIID(BigDecimal value) {
        this.combiid = value;
    }

    /**
     * Gets the value of the combitype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMBITYPE() {
        return combitype;
    }

    /**
     * Sets the value of the combitype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMBITYPE(String value) {
        this.combitype = value;
    }

}

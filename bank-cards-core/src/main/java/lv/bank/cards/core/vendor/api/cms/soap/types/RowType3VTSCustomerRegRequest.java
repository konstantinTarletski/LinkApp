
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_3VTSCustomerReg_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_3VTSCustomerReg_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CARD_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CLIENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CLIENT_B" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CLN_CAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="F_NAMES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SURNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NATIONALITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="B_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_E_MAILS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_MOB_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_COD1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_STREET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_CNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_PCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="A3VTS_COUNTY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="A3VTS_FLAG1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="A3VTS_FLAG2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HINT_QUESTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HINT_ANSWER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PASSWORD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ADDITIONAL_CARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ADDITIONAL_CVV2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ADDITIONAL_EXPIRY" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SUPPLEMENTARY_PAN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SUPPLEMENTARY_CVV2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SUPPLEMENTARY_EXPIRY" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NOTES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BANK_C" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="GROUPC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_3VTSCustomerReg_Request", propOrder = {
    "card",
    "cardtype",
    "client",
    "status",
    "clientb",
    "clncat",
    "fnames",
    "surname",
    "nationality",
    "bdate",
    "remails",
    "rmobphone",
    "ucod1",
    "rstreet",
    "rcity",
    "rcntry",
    "rpcode",
    "a3VTSCOUNTY",
    "a3VTSFLAG1",
    "a3VTSFLAG2",
    "hintquestion",
    "hintanswer",
    "password",
    "additionalcard",
    "additionalcvv2",
    "additionalexpiry",
    "supplementarypan",
    "supplementarycvv2",
    "supplementaryexpiry",
    "notes",
    "bankc",
    "groupc"
})
public class RowType3VTSCustomerRegRequest {

    @XmlElement(name = "CARD", required = true)
    protected String card;
    @XmlElement(name = "CARD_TYPE")
    protected String cardtype;
    @XmlElement(name = "CLIENT")
    protected String client;
    @XmlElement(name = "STATUS")
    protected String status;
    @XmlElement(name = "CLIENT_B")
    protected String clientb;
    @XmlElement(name = "CLN_CAT")
    protected String clncat;
    @XmlElement(name = "F_NAMES")
    protected String fnames;
    @XmlElement(name = "SURNAME")
    protected String surname;
    @XmlElement(name = "NATIONALITY")
    protected String nationality;
    @XmlElement(name = "B_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date bdate;
    @XmlElement(name = "R_E_MAILS")
    protected String remails;
    @XmlElement(name = "R_MOB_PHONE")
    protected String rmobphone;
    @XmlElement(name = "U_COD1")
    protected String ucod1;
    @XmlElement(name = "R_STREET")
    protected String rstreet;
    @XmlElement(name = "R_CITY")
    protected String rcity;
    @XmlElement(name = "R_CNTRY")
    protected String rcntry;
    @XmlElement(name = "R_PCODE")
    protected String rpcode;
    @XmlElement(name = "A3VTS_COUNTY")
    protected String a3VTSCOUNTY;
    @XmlElement(name = "A3VTS_FLAG1")
    protected String a3VTSFLAG1;
    @XmlElement(name = "A3VTS_FLAG2")
    protected String a3VTSFLAG2;
    @XmlElement(name = "HINT_QUESTION")
    protected String hintquestion;
    @XmlElement(name = "HINT_ANSWER")
    protected String hintanswer;
    @XmlElement(name = "PASSWORD")
    protected String password;
    @XmlElement(name = "ADDITIONAL_CARD")
    protected String additionalcard;
    @XmlElement(name = "ADDITIONAL_CVV2")
    protected String additionalcvv2;
    @XmlElement(name = "ADDITIONAL_EXPIRY", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date additionalexpiry;
    @XmlElement(name = "SUPPLEMENTARY_PAN")
    protected String supplementarypan;
    @XmlElement(name = "SUPPLEMENTARY_CVV2")
    protected String supplementarycvv2;
    @XmlElement(name = "SUPPLEMENTARY_EXPIRY", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date supplementaryexpiry;
    @XmlElement(name = "NOTES")
    protected String notes;
    @XmlElement(name = "BANK_C")
    protected String bankc;
    @XmlElement(name = "GROUPC")
    protected String groupc;

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
     * Gets the value of the a3VTSCOUNTY property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getA3VTSCOUNTY() {
        return a3VTSCOUNTY;
    }

    /**
     * Sets the value of the a3VTSCOUNTY property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setA3VTSCOUNTY(String value) {
        this.a3VTSCOUNTY = value;
    }

    /**
     * Gets the value of the a3VTSFLAG1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getA3VTSFLAG1() {
        return a3VTSFLAG1;
    }

    /**
     * Sets the value of the a3VTSFLAG1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setA3VTSFLAG1(String value) {
        this.a3VTSFLAG1 = value;
    }

    /**
     * Gets the value of the a3VTSFLAG2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getA3VTSFLAG2() {
        return a3VTSFLAG2;
    }

    /**
     * Sets the value of the a3VTSFLAG2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setA3VTSFLAG2(String value) {
        this.a3VTSFLAG2 = value;
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
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPASSWORD() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPASSWORD(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the additionalcard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADDITIONALCARD() {
        return additionalcard;
    }

    /**
     * Sets the value of the additionalcard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADDITIONALCARD(String value) {
        this.additionalcard = value;
    }

    /**
     * Gets the value of the additionalcvv2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADDITIONALCVV2() {
        return additionalcvv2;
    }

    /**
     * Sets the value of the additionalcvv2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADDITIONALCVV2(String value) {
        this.additionalcvv2 = value;
    }

    /**
     * Gets the value of the additionalexpiry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getADDITIONALEXPIRY() {
        return additionalexpiry;
    }

    /**
     * Sets the value of the additionalexpiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADDITIONALEXPIRY(Date value) {
        this.additionalexpiry = value;
    }

    /**
     * Gets the value of the supplementarypan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUPPLEMENTARYPAN() {
        return supplementarypan;
    }

    /**
     * Sets the value of the supplementarypan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUPPLEMENTARYPAN(String value) {
        this.supplementarypan = value;
    }

    /**
     * Gets the value of the supplementarycvv2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUPPLEMENTARYCVV2() {
        return supplementarycvv2;
    }

    /**
     * Sets the value of the supplementarycvv2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUPPLEMENTARYCVV2(String value) {
        this.supplementarycvv2 = value;
    }

    /**
     * Gets the value of the supplementaryexpiry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getSUPPLEMENTARYEXPIRY() {
        return supplementaryexpiry;
    }

    /**
     * Sets the value of the supplementaryexpiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUPPLEMENTARYEXPIRY(Date value) {
        this.supplementaryexpiry = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOTES() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOTES(String value) {
        this.notes = value;
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

}

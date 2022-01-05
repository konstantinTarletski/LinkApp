
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_ListCustomers_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_ListCustomers_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CLIENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CLIENT_B" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="F_NAMES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SURNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PERSON_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="B_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_STREET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_CNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_PCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_E_MAILS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="R_MOB_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_COD1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="A3VTS_COUNTY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="A3VTS_FLAG1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="A3VTS_FLAG2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SUPPLEMENTARY_PAN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SUPPLEMENTARY_CVV2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SUPPLEMENTARY_EXPIRY" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NOTES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EMP_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BANK_C" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_ListCustomers_Request", propOrder = {
    "client",
    "status",
    "clientb",
    "fnames",
    "surname",
    "personcode",
    "bdate",
    "rstreet",
    "rcity",
    "rcntry",
    "rpcode",
    "remails",
    "rmobphone",
    "card",
    "ucod1",
    "a3VTSCOUNTY",
    "a3VTSFLAG1",
    "a3VTSFLAG2",
    "supplementarypan",
    "supplementarycvv2",
    "supplementaryexpiry",
    "notes",
    "empname",
    "bankc"
})
public class RowTypeListCustomersRequest {

    @XmlElement(name = "CLIENT")
    protected String client;
    @XmlElement(name = "STATUS")
    protected String status;
    @XmlElement(name = "CLIENT_B")
    protected String clientb;
    @XmlElement(name = "F_NAMES")
    protected String fnames;
    @XmlElement(name = "SURNAME")
    protected String surname;
    @XmlElement(name = "PERSON_CODE")
    protected String personcode;
    @XmlElement(name = "B_DATE", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date bdate;
    @XmlElement(name = "R_STREET")
    protected String rstreet;
    @XmlElement(name = "R_CITY")
    protected String rcity;
    @XmlElement(name = "R_CNTRY")
    protected String rcntry;
    @XmlElement(name = "R_PCODE")
    protected String rpcode;
    @XmlElement(name = "R_E_MAILS")
    protected String remails;
    @XmlElement(name = "R_MOB_PHONE")
    protected String rmobphone;
    @XmlElement(name = "CARD")
    protected String card;
    @XmlElement(name = "U_COD1")
    protected String ucod1;
    @XmlElement(name = "A3VTS_COUNTY")
    protected String a3VTSCOUNTY;
    @XmlElement(name = "A3VTS_FLAG1")
    protected String a3VTSFLAG1;
    @XmlElement(name = "A3VTS_FLAG2")
    protected String a3VTSFLAG2;
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
    @XmlElement(name = "EMP_NAME")
    protected String empname;
    @XmlElement(name = "BANK_C", required = true)
    protected String bankc;

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

}

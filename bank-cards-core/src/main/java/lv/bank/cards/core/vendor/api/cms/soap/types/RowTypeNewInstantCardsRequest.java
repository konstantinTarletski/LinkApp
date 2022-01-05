
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_NewInstantCards_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_NewInstantCards_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="BANK_C" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="GROUPC" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="G_PROFILE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="P_PROFILE_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="COUNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AGR_COUNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PRODUCT_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BRANCH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="OFFICE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="A_COND_SET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="C_COND_SET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CL_CATEGORY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD_FNAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STREET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="OBJ_TREE_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CLIENT_NUM_ALGO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ACCOUNT_NUM_ALGO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD_NUM_ALGO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BIN_RANGE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="WITH_MONEY_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TR_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EXECUTION_TYPE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LOCKED_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_NewInstantCards_Request", propOrder = {
    "bankc",
    "groupc",
    "gprofilecode",
    "pprofilecode",
    "count",
    "agrcount",
    "productcode",
    "branch",
    "office",
    "bin",
    "ccy",
    "acondset",
    "ccondset",
    "clcategory",
    "cardfname",
    "street",
    "city",
    "objtreetype",
    "clientnumalgo",
    "accountnumalgo",
    "cardnumalgo",
    "binrange",
    "withmoneyflag",
    "amount",
    "trtype",
    "executiontype",
    "lockedflag"
})
public class RowTypeNewInstantCardsRequest {

    @XmlElement(name = "BANK_C", required = true)
    protected String bankc;
    @XmlElement(name = "GROUPC", required = true)
    protected String groupc;
    @XmlElement(name = "G_PROFILE_CODE")
    protected String gprofilecode;
    @XmlElement(name = "P_PROFILE_CODE")
    protected String pprofilecode;
    @XmlElement(name = "COUNT")
    protected BigDecimal count;
    @XmlElement(name = "AGR_COUNT")
    protected BigDecimal agrcount;
    @XmlElement(name = "PRODUCT_CODE")
    protected String productcode;
    @XmlElement(name = "BRANCH")
    protected String branch;
    @XmlElement(name = "OFFICE")
    protected String office;
    @XmlElement(name = "BIN")
    protected String bin;
    @XmlElement(name = "CCY")
    protected String ccy;
    @XmlElement(name = "A_COND_SET")
    protected String acondset;
    @XmlElement(name = "C_COND_SET")
    protected String ccondset;
    @XmlElement(name = "CL_CATEGORY")
    protected String clcategory;
    @XmlElement(name = "CARD_FNAME")
    protected String cardfname;
    @XmlElement(name = "STREET")
    protected String street;
    @XmlElement(name = "CITY")
    protected String city;
    @XmlElement(name = "OBJ_TREE_TYPE")
    protected String objtreetype;
    @XmlElement(name = "CLIENT_NUM_ALGO")
    protected String clientnumalgo;
    @XmlElement(name = "ACCOUNT_NUM_ALGO")
    protected String accountnumalgo;
    @XmlElement(name = "CARD_NUM_ALGO")
    protected String cardnumalgo;
    @XmlElement(name = "BIN_RANGE")
    protected BigDecimal binrange;
    @XmlElement(name = "WITH_MONEY_FLAG")
    protected String withmoneyflag;
    @XmlElement(name = "AMOUNT")
    protected BigDecimal amount;
    @XmlElement(name = "TR_TYPE")
    protected String trtype;
    @XmlElement(name = "EXECUTION_TYPE")
    protected BigDecimal executiontype;
    @XmlElement(name = "LOCKED_FLAG")
    protected String lockedflag;

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
     * Gets the value of the gprofilecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGPROFILECODE() {
        return gprofilecode;
    }

    /**
     * Sets the value of the gprofilecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGPROFILECODE(String value) {
        this.gprofilecode = value;
    }

    /**
     * Gets the value of the pprofilecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPPROFILECODE() {
        return pprofilecode;
    }

    /**
     * Sets the value of the pprofilecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPPROFILECODE(String value) {
        this.pprofilecode = value;
    }

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCOUNT() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCOUNT(BigDecimal value) {
        this.count = value;
    }

    /**
     * Gets the value of the agrcount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAGRCOUNT() {
        return agrcount;
    }

    /**
     * Sets the value of the agrcount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAGRCOUNT(BigDecimal value) {
        this.agrcount = value;
    }

    /**
     * Gets the value of the productcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRODUCTCODE() {
        return productcode;
    }

    /**
     * Sets the value of the productcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRODUCTCODE(String value) {
        this.productcode = value;
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
     * Gets the value of the bin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBIN() {
        return bin;
    }

    /**
     * Sets the value of the bin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBIN(String value) {
        this.bin = value;
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
     * Gets the value of the acondset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACONDSET() {
        return acondset;
    }

    /**
     * Sets the value of the acondset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACONDSET(String value) {
        this.acondset = value;
    }

    /**
     * Gets the value of the ccondset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCCONDSET() {
        return ccondset;
    }

    /**
     * Sets the value of the ccondset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCCONDSET(String value) {
        this.ccondset = value;
    }

    /**
     * Gets the value of the clcategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLCATEGORY() {
        return clcategory;
    }

    /**
     * Sets the value of the clcategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLCATEGORY(String value) {
        this.clcategory = value;
    }

    /**
     * Gets the value of the cardfname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDFNAME() {
        return cardfname;
    }

    /**
     * Sets the value of the cardfname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDFNAME(String value) {
        this.cardfname = value;
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
     * Gets the value of the objtreetype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOBJTREETYPE() {
        return objtreetype;
    }

    /**
     * Sets the value of the objtreetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOBJTREETYPE(String value) {
        this.objtreetype = value;
    }

    /**
     * Gets the value of the clientnumalgo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLIENTNUMALGO() {
        return clientnumalgo;
    }

    /**
     * Sets the value of the clientnumalgo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLIENTNUMALGO(String value) {
        this.clientnumalgo = value;
    }

    /**
     * Gets the value of the accountnumalgo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACCOUNTNUMALGO() {
        return accountnumalgo;
    }

    /**
     * Sets the value of the accountnumalgo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACCOUNTNUMALGO(String value) {
        this.accountnumalgo = value;
    }

    /**
     * Gets the value of the cardnumalgo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDNUMALGO() {
        return cardnumalgo;
    }

    /**
     * Sets the value of the cardnumalgo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDNUMALGO(String value) {
        this.cardnumalgo = value;
    }

    /**
     * Gets the value of the binrange property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBINRANGE() {
        return binrange;
    }

    /**
     * Sets the value of the binrange property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBINRANGE(BigDecimal value) {
        this.binrange = value;
    }

    /**
     * Gets the value of the withmoneyflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWITHMONEYFLAG() {
        return withmoneyflag;
    }

    /**
     * Sets the value of the withmoneyflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWITHMONEYFLAG(String value) {
        this.withmoneyflag = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAMOUNT() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAMOUNT(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the trtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRTYPE() {
        return trtype;
    }

    /**
     * Sets the value of the trtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRTYPE(String value) {
        this.trtype = value;
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
     * Gets the value of the lockedflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCKEDFLAG() {
        return lockedflag;
    }

    /**
     * Sets the value of the lockedflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCKEDFLAG(String value) {
        this.lockedflag = value;
    }

}

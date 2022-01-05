
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
 * &lt;p&gt;Java class for RowType_RelinkCard_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_RelinkCard_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD_FOR_LINK" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="TARGET_CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="BANK_C" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="GROUPC" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="DEST_ACCNT_TYPE" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="DEST_ACCNT_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="RELINK_MODE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="COND_SET" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="FEE_CALC_MODE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="PRODUCT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="LINK_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="MOMENT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="LOCKS" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CONTRACT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="KS_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_RelinkCard_Request", propOrder = {
    "cardforlink",
    "targetcard",
    "bankc",
    "groupc",
    "destaccnttype",
    "destaccntnumber",
    "relinkmode",
    "condset",
    "feecalcmode",
    "product",
    "linktype",
    "moment",
    "locks",
    "contract",
    "ksdate"
})
public class RowTypeRelinkCardRequest {

    @XmlElement(name = "CARD_FOR_LINK", required = true)
    protected String cardforlink;
    @XmlElement(name = "TARGET_CARD", required = true)
    protected String targetcard;
    @XmlElement(name = "BANK_C", required = true)
    protected String bankc;
    @XmlElement(name = "GROUPC", required = true)
    protected String groupc;
    @XmlElement(name = "DEST_ACCNT_TYPE", required = true)
    protected BigDecimal destaccnttype;
    @XmlElement(name = "DEST_ACCNT_NUMBER", required = true)
    protected String destaccntnumber;
    @XmlElement(name = "RELINK_MODE", required = true)
    protected String relinkmode;
    @XmlElement(name = "COND_SET", required = true)
    protected String condset;
    @XmlElement(name = "FEE_CALC_MODE", required = true)
    protected String feecalcmode;
    @XmlElement(name = "PRODUCT", required = true)
    protected String product;
    @XmlElement(name = "LINK_TYPE", required = true)
    protected String linktype;
    @XmlElement(name = "MOMENT", required = true)
    protected String moment;
    @XmlElement(name = "LOCKS", required = true)
    protected String locks;
    @XmlElement(name = "CONTRACT", required = true)
    protected String contract;
    @XmlElement(name = "KS_DATE", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date ksdate;

    /**
     * Gets the value of the cardforlink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDFORLINK() {
        return cardforlink;
    }

    /**
     * Sets the value of the cardforlink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDFORLINK(String value) {
        this.cardforlink = value;
    }

    /**
     * Gets the value of the targetcard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTARGETCARD() {
        return targetcard;
    }

    /**
     * Sets the value of the targetcard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTARGETCARD(String value) {
        this.targetcard = value;
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
     * Gets the value of the destaccnttype property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDESTACCNTTYPE() {
        return destaccnttype;
    }

    /**
     * Sets the value of the destaccnttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDESTACCNTTYPE(BigDecimal value) {
        this.destaccnttype = value;
    }

    /**
     * Gets the value of the destaccntnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESTACCNTNUMBER() {
        return destaccntnumber;
    }

    /**
     * Sets the value of the destaccntnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESTACCNTNUMBER(String value) {
        this.destaccntnumber = value;
    }

    /**
     * Gets the value of the relinkmode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRELINKMODE() {
        return relinkmode;
    }

    /**
     * Sets the value of the relinkmode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRELINKMODE(String value) {
        this.relinkmode = value;
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
     * Gets the value of the feecalcmode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFEECALCMODE() {
        return feecalcmode;
    }

    /**
     * Sets the value of the feecalcmode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFEECALCMODE(String value) {
        this.feecalcmode = value;
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
     * Gets the value of the linktype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLINKTYPE() {
        return linktype;
    }

    /**
     * Sets the value of the linktype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLINKTYPE(String value) {
        this.linktype = value;
    }

    /**
     * Gets the value of the moment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOMENT() {
        return moment;
    }

    /**
     * Sets the value of the moment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOMENT(String value) {
        this.moment = value;
    }

    /**
     * Gets the value of the locks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCKS() {
        return locks;
    }

    /**
     * Sets the value of the locks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCKS(String value) {
        this.locks = value;
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
     * Gets the value of the ksdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getKSDATE() {
        return ksdate;
    }

    /**
     * Sets the value of the ksdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKSDATE(Date value) {
        this.ksdate = value;
    }

}

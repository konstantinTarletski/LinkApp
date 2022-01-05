
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_RenewCard complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_RenewCard"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="NO_CHARGE" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NEW_EXPIRY" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NEW_RISK_LEVEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CHIP_ID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DESIGN_ID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="OFF_COND_SET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NEW_CARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="U_FIELD13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NEW_BIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NEW_COND_SET" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AGR_BRANCH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD_BRANCH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CONTRACT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AGRE_NOM" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
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
@XmlType(name = "RowType_RenewCard", propOrder = {
    "card",
    "nocharge",
    "newexpiry",
    "newrisklevel",
    "chipid",
    "designid",
    "offcondset",
    "newcard",
    "ufield14",
    "ufield13",
    "newbin",
    "newcondset",
    "agrbranch",
    "cardbranch",
    "contract",
    "agrenom",
    "waivedemb"
})
public class RowTypeRenewCard {

    @XmlElement(name = "CARD", required = true)
    protected String card;
    @XmlElement(name = "NO_CHARGE")
    protected BigInteger nocharge;
    @XmlElement(name = "NEW_EXPIRY", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date newexpiry;
    @XmlElement(name = "NEW_RISK_LEVEL")
    protected String newrisklevel;
    @XmlElement(name = "CHIP_ID")
    protected BigDecimal chipid;
    @XmlElement(name = "DESIGN_ID")
    protected BigDecimal designid;
    @XmlElement(name = "OFF_COND_SET")
    protected String offcondset;
    @XmlElement(name = "NEW_CARD")
    protected String newcard;
    @XmlElement(name = "U_FIELD14")
    protected String ufield14;
    @XmlElement(name = "U_FIELD13")
    protected String ufield13;
    @XmlElement(name = "NEW_BIN")
    protected String newbin;
    @XmlElement(name = "NEW_COND_SET")
    protected String newcondset;
    @XmlElement(name = "AGR_BRANCH")
    protected String agrbranch;
    @XmlElement(name = "CARD_BRANCH")
    protected String cardbranch;
    @XmlElement(name = "CONTRACT")
    protected String contract;
    @XmlElement(name = "AGRE_NOM")
    protected BigDecimal agrenom;
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
     * Gets the value of the nocharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNOCHARGE() {
        return nocharge;
    }

    /**
     * Sets the value of the nocharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNOCHARGE(BigInteger value) {
        this.nocharge = value;
    }

    /**
     * Gets the value of the newexpiry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getNEWEXPIRY() {
        return newexpiry;
    }

    /**
     * Sets the value of the newexpiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNEWEXPIRY(Date value) {
        this.newexpiry = value;
    }

    /**
     * Gets the value of the newrisklevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNEWRISKLEVEL() {
        return newrisklevel;
    }

    /**
     * Sets the value of the newrisklevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNEWRISKLEVEL(String value) {
        this.newrisklevel = value;
    }

    /**
     * Gets the value of the chipid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCHIPID() {
        return chipid;
    }

    /**
     * Sets the value of the chipid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCHIPID(BigDecimal value) {
        this.chipid = value;
    }

    /**
     * Gets the value of the designid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDESIGNID() {
        return designid;
    }

    /**
     * Sets the value of the designid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDESIGNID(BigDecimal value) {
        this.designid = value;
    }

    /**
     * Gets the value of the offcondset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOFFCONDSET() {
        return offcondset;
    }

    /**
     * Sets the value of the offcondset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOFFCONDSET(String value) {
        this.offcondset = value;
    }

    /**
     * Gets the value of the newcard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNEWCARD() {
        return newcard;
    }

    /**
     * Sets the value of the newcard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNEWCARD(String value) {
        this.newcard = value;
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
     * Gets the value of the newbin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNEWBIN() {
        return newbin;
    }

    /**
     * Sets the value of the newbin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNEWBIN(String value) {
        this.newbin = value;
    }

    /**
     * Gets the value of the newcondset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNEWCONDSET() {
        return newcondset;
    }

    /**
     * Sets the value of the newcondset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNEWCONDSET(String value) {
        this.newcondset = value;
    }

    /**
     * Gets the value of the agrbranch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAGRBRANCH() {
        return agrbranch;
    }

    /**
     * Sets the value of the agrbranch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAGRBRANCH(String value) {
        this.agrbranch = value;
    }

    /**
     * Gets the value of the cardbranch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDBRANCH() {
        return cardbranch;
    }

    /**
     * Sets the value of the cardbranch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDBRANCH(String value) {
        this.cardbranch = value;
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

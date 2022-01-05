
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigInteger;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_DuplicateCard complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_DuplicateCard"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="NO_CHARGE" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NO_EMB_SESSION" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NEW_EXPIRY" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="NEW_RISK_LEVEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AGR_BRANCH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD_BRANCH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_DuplicateCard", propOrder = {
    "card",
    "nocharge",
    "noembsession",
    "newexpiry",
    "newrisklevel",
    "agrbranch",
    "cardbranch"
})
public class RowTypeDuplicateCard {

    @XmlElement(name = "CARD", required = true)
    protected String card;
    @XmlElement(name = "NO_CHARGE")
    protected BigInteger nocharge;
    @XmlElement(name = "NO_EMB_SESSION")
    protected BigInteger noembsession;
    @XmlElement(name = "NEW_EXPIRY", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date newexpiry;
    @XmlElement(name = "NEW_RISK_LEVEL")
    protected String newrisklevel;
    @XmlElement(name = "AGR_BRANCH")
    protected String agrbranch;
    @XmlElement(name = "CARD_BRANCH")
    protected String cardbranch;

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
     * Gets the value of the noembsession property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNOEMBSESSION() {
        return noembsession;
    }

    /**
     * Sets the value of the noembsession property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNOEMBSESSION(BigInteger value) {
        this.noembsession = value;
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

}

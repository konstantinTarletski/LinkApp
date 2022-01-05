
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
 * &lt;p&gt;Java class for RowType_CardInfo_PhysicalCard complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_CardInfo_PhysicalCard"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="STATUS1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STATUS2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="STOP_CAUSE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EXPIRY1" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EXPIRITY2" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RENEW" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CARD_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MC_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RENEWED_CARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DESIGN_ID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="INSTANT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_CardInfo_PhysicalCard", propOrder = {
    "status1",
    "status2",
    "stopcause",
    "expiry1",
    "expirity2",
    "renew",
    "cardname",
    "mcname",
    "renewedcard",
    "designid",
    "instant"
})
public class RowTypeCardInfoPhysicalCard {

    @XmlElement(name = "STATUS1")
    protected String status1;
    @XmlElement(name = "STATUS2")
    protected String status2;
    @XmlElement(name = "STOP_CAUSE")
    protected String stopcause;
    @XmlElement(name = "EXPIRY1", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date expiry1;
    @XmlElement(name = "EXPIRITY2", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date expirity2;
    @XmlElement(name = "RENEW")
    protected String renew;
    @XmlElement(name = "CARD_NAME")
    protected String cardname;
    @XmlElement(name = "MC_NAME")
    protected String mcname;
    @XmlElement(name = "RENEWED_CARD")
    protected String renewedcard;
    @XmlElement(name = "DESIGN_ID")
    protected BigDecimal designid;
    @XmlElement(name = "INSTANT")
    protected String instant;

    /**
     * Gets the value of the status1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATUS1() {
        return status1;
    }

    /**
     * Sets the value of the status1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATUS1(String value) {
        this.status1 = value;
    }

    /**
     * Gets the value of the status2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATUS2() {
        return status2;
    }

    /**
     * Sets the value of the status2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATUS2(String value) {
        this.status2 = value;
    }

    /**
     * Gets the value of the stopcause property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTOPCAUSE() {
        return stopcause;
    }

    /**
     * Sets the value of the stopcause property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTOPCAUSE(String value) {
        this.stopcause = value;
    }

    /**
     * Gets the value of the expiry1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getEXPIRY1() {
        return expiry1;
    }

    /**
     * Sets the value of the expiry1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXPIRY1(Date value) {
        this.expiry1 = value;
    }

    /**
     * Gets the value of the expirity2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getEXPIRITY2() {
        return expirity2;
    }

    /**
     * Sets the value of the expirity2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXPIRITY2(Date value) {
        this.expirity2 = value;
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
     * Gets the value of the renewedcard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRENEWEDCARD() {
        return renewedcard;
    }

    /**
     * Sets the value of the renewedcard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRENEWEDCARD(String value) {
        this.renewedcard = value;
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
     * Gets the value of the instant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINSTANT() {
        return instant;
    }

    /**
     * Sets the value of the instant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINSTANT(String value) {
        this.instant = value;
    }

}

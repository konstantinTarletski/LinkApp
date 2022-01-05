
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_CardInfo_EMV_Data complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_CardInfo_EMV_Data"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="SEQUENCE_NR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LAST_SEQ_NR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DESIGN_ID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CHIP_APP_ID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="INDIVIDUALIZED_PREV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DKI_0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DKI_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DKI_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TRACK2_EQUIV_DATA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="INDIVIDUALIZED" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_CardInfo_EMV_Data", propOrder = {
    "sequencenr",
    "lastseqnr",
    "designid",
    "chipappid",
    "individualizedprev",
    "dki0",
    "dki1",
    "dki2",
    "track2EQUIVDATA",
    "individualized"
})
public class RowTypeCardInfoEMVData {

    @XmlElement(name = "SEQUENCE_NR")
    protected String sequencenr;
    @XmlElement(name = "LAST_SEQ_NR")
    protected String lastseqnr;
    @XmlElement(name = "DESIGN_ID")
    protected BigDecimal designid;
    @XmlElement(name = "CHIP_APP_ID")
    protected BigDecimal chipappid;
    @XmlElement(name = "INDIVIDUALIZED_PREV")
    protected String individualizedprev;
    @XmlElement(name = "DKI_0")
    protected String dki0;
    @XmlElement(name = "DKI_1")
    protected String dki1;
    @XmlElement(name = "DKI_2")
    protected String dki2;
    @XmlElement(name = "TRACK2_EQUIV_DATA")
    protected String track2EQUIVDATA;
    @XmlElement(name = "INDIVIDUALIZED")
    protected String individualized;

    /**
     * Gets the value of the sequencenr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEQUENCENR() {
        return sequencenr;
    }

    /**
     * Sets the value of the sequencenr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEQUENCENR(String value) {
        this.sequencenr = value;
    }

    /**
     * Gets the value of the lastseqnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLASTSEQNR() {
        return lastseqnr;
    }

    /**
     * Sets the value of the lastseqnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLASTSEQNR(String value) {
        this.lastseqnr = value;
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
     * Gets the value of the chipappid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCHIPAPPID() {
        return chipappid;
    }

    /**
     * Sets the value of the chipappid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCHIPAPPID(BigDecimal value) {
        this.chipappid = value;
    }

    /**
     * Gets the value of the individualizedprev property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDIVIDUALIZEDPREV() {
        return individualizedprev;
    }

    /**
     * Sets the value of the individualizedprev property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDIVIDUALIZEDPREV(String value) {
        this.individualizedprev = value;
    }

    /**
     * Gets the value of the dki0 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDKI0() {
        return dki0;
    }

    /**
     * Sets the value of the dki0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDKI0(String value) {
        this.dki0 = value;
    }

    /**
     * Gets the value of the dki1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDKI1() {
        return dki1;
    }

    /**
     * Sets the value of the dki1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDKI1(String value) {
        this.dki1 = value;
    }

    /**
     * Gets the value of the dki2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDKI2() {
        return dki2;
    }

    /**
     * Sets the value of the dki2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDKI2(String value) {
        this.dki2 = value;
    }

    /**
     * Gets the value of the track2EQUIVDATA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRACK2EQUIVDATA() {
        return track2EQUIVDATA;
    }

    /**
     * Sets the value of the track2EQUIVDATA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRACK2EQUIVDATA(String value) {
        this.track2EQUIVDATA = value;
    }

    /**
     * Gets the value of the individualized property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDIVIDUALIZED() {
        return individualized;
    }

    /**
     * Sets the value of the individualized property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDIVIDUALIZED(String value) {
        this.individualized = value;
    }

}

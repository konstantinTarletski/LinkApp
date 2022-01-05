
package lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for CashInDNBNordeaQueryBody_Core_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="CashInDNBNordeaQueryBody_Core_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}QueryBody_t"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="FLD_002" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="FLD_041" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="FLD_043" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="FLD_004" type="{http://www.w3.org/2001/XMLSchema}long"/&amp;gt;
 *         &amp;lt;element name="FLD_049" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="FLD_006" type="{http://www.w3.org/2001/XMLSchema}long"/&amp;gt;
 *         &amp;lt;element name="FLD_051" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="FLD_007" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="FLD_038" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="FLD_011" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="FLD_037" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="SOURCE" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}Source_t"/&amp;gt;
 *         &amp;lt;element name="TYPE" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}Type_t"/&amp;gt;
 *         &amp;lt;element name="ACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashInDNBNordeaQueryBody_Core_t", propOrder = {
    "fld002",
    "fld041",
    "fld043",
    "fld004",
    "fld049",
    "fld006",
    "fld051",
    "fld007",
    "fld038",
    "fld011",
    "fld037",
    "source",
    "type",
    "account"
})
@XmlSeeAlso({
    CashInDNBNordeaQueryBodyLVT.class
})
public abstract class CashInDNBNordeaQueryBodyCoreT
    extends QueryBodyT
{

    @XmlElement(name = "FLD_002", required = true)
    protected String fld002;
    @XmlElement(name = "FLD_041", required = true)
    protected String fld041;
    @XmlElement(name = "FLD_043", required = true)
    protected String fld043;
    @XmlElement(name = "FLD_004")
    protected long fld004;
    @XmlElement(name = "FLD_049", required = true)
    protected String fld049;
    @XmlElement(name = "FLD_006")
    protected long fld006;
    @XmlElement(name = "FLD_051", required = true)
    protected String fld051;
    @XmlElement(name = "FLD_007", required = true)
    protected String fld007;
    @XmlElement(name = "FLD_038", required = true)
    protected String fld038;
    @XmlElement(name = "FLD_011", required = true)
    protected String fld011;
    @XmlElement(name = "FLD_037", required = true)
    protected String fld037;
    @XmlElement(name = "SOURCE", required = true)
    @XmlSchemaType(name = "string")
    protected SourceT source;
    @XmlElement(name = "TYPE", required = true)
    @XmlSchemaType(name = "string")
    protected TypeT type;
    @XmlElement(name = "ACCOUNT")
    protected String account;

    /**
     * Gets the value of the fld002 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLD002() {
        return fld002;
    }

    /**
     * Sets the value of the fld002 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLD002(String value) {
        this.fld002 = value;
    }

    /**
     * Gets the value of the fld041 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLD041() {
        return fld041;
    }

    /**
     * Sets the value of the fld041 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLD041(String value) {
        this.fld041 = value;
    }

    /**
     * Gets the value of the fld043 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLD043() {
        return fld043;
    }

    /**
     * Sets the value of the fld043 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLD043(String value) {
        this.fld043 = value;
    }

    /**
     * Gets the value of the fld004 property.
     * 
     */
    public long getFLD004() {
        return fld004;
    }

    /**
     * Sets the value of the fld004 property.
     * 
     */
    public void setFLD004(long value) {
        this.fld004 = value;
    }

    /**
     * Gets the value of the fld049 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLD049() {
        return fld049;
    }

    /**
     * Sets the value of the fld049 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLD049(String value) {
        this.fld049 = value;
    }

    /**
     * Gets the value of the fld006 property.
     * 
     */
    public long getFLD006() {
        return fld006;
    }

    /**
     * Sets the value of the fld006 property.
     * 
     */
    public void setFLD006(long value) {
        this.fld006 = value;
    }

    /**
     * Gets the value of the fld051 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLD051() {
        return fld051;
    }

    /**
     * Sets the value of the fld051 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLD051(String value) {
        this.fld051 = value;
    }

    /**
     * Gets the value of the fld007 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLD007() {
        return fld007;
    }

    /**
     * Sets the value of the fld007 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLD007(String value) {
        this.fld007 = value;
    }

    /**
     * Gets the value of the fld038 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLD038() {
        return fld038;
    }

    /**
     * Sets the value of the fld038 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLD038(String value) {
        this.fld038 = value;
    }

    /**
     * Gets the value of the fld011 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLD011() {
        return fld011;
    }

    /**
     * Sets the value of the fld011 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLD011(String value) {
        this.fld011 = value;
    }

    /**
     * Gets the value of the fld037 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLD037() {
        return fld037;
    }

    /**
     * Sets the value of the fld037 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLD037(String value) {
        this.fld037 = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link SourceT }
     *     
     */
    public SourceT getSOURCE() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceT }
     *     
     */
    public void setSOURCE(SourceT value) {
        this.source = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link TypeT }
     *     
     */
    public TypeT getTYPE() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeT }
     *     
     */
    public void setTYPE(TypeT value) {
        this.type = value;
    }

    /**
     * Gets the value of the account property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACCOUNT() {
        return account;
    }

    /**
     * Sets the value of the account property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACCOUNT(String value) {
        this.account = value;
    }

}


package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_RemoveCardFromStop_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_RemoveCardFromStop_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="TEXT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BANK_C" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="GROUPC" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="STOP_CAUSE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ALL_COMBI" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_RemoveCardFromStop_Request", propOrder = {
    "card",
    "text",
    "bankc",
    "groupc",
    "stopcause",
    "allcombi"
})
public class RowTypeRemoveCardFromStopRequest {

    @XmlElement(name = "CARD", required = true)
    protected String card;
    @XmlElement(name = "TEXT")
    protected String text;
    @XmlElement(name = "BANK_C", required = true)
    protected String bankc;
    @XmlElement(name = "GROUPC", required = true)
    protected String groupc;
    @XmlElement(name = "STOP_CAUSE")
    protected String stopcause;
    @XmlElement(name = "ALL_COMBI", required = true)
    protected String allcombi;

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
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTEXT() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTEXT(String value) {
        this.text = value;
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
     * Gets the value of the allcombi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALLCOMBI() {
        return allcombi;
    }

    /**
     * Sets the value of the allcombi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALLCOMBI(String value) {
        this.allcombi = value;
    }

}

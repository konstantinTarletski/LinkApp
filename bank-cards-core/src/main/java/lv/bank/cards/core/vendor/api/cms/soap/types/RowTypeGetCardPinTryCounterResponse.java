
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
 * &lt;p&gt;Java class for RowType_GetCardPinTryCounter_Response complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_GetCardPinTryCounter_Response"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="PIN_TRY_COUNT" type="{http://www.w3.org/2001/XMLSchema}integer"/&amp;gt;
 *         &amp;lt;element name="UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_GetCardPinTryCounter_Response", propOrder = {
    "card",
    "pintrycount",
    "updatedate"
})
public class RowTypeGetCardPinTryCounterResponse {

    @XmlElement(name = "CARD", required = true)
    protected String card;
    @XmlElement(name = "PIN_TRY_COUNT", required = true)
    protected BigInteger pintrycount;
    @XmlElement(name = "UPDATE_DATE", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date updatedate;

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
     * Gets the value of the pintrycount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPINTRYCOUNT() {
        return pintrycount;
    }

    /**
     * Sets the value of the pintrycount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPINTRYCOUNT(BigInteger value) {
        this.pintrycount = value;
    }

    /**
     * Gets the value of the updatedate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getUPDATEDATE() {
        return updatedate;
    }

    /**
     * Sets the value of the updatedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUPDATEDATE(Date value) {
        this.updatedate = value;
    }

}

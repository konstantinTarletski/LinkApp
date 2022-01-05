
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_LimitBalance complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_LimitBalance"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="limit_value" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="limit_balance_value" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *         &amp;lt;element name="limit_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="limit_date_from" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="limit_date_to" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_LimitBalance", propOrder = {
    "limitValue",
    "limitBalanceValue",
    "limitName",
    "limitDateFrom",
    "limitDateTo"
})
public class RowTypeLimitBalance {

    @XmlElement(name = "limit_value", required = true)
    protected BigDecimal limitValue;
    @XmlElement(name = "limit_balance_value", required = true)
    protected BigDecimal limitBalanceValue;
    @XmlElement(name = "limit_name")
    protected String limitName;
    @XmlElement(name = "limit_date_from")
    protected String limitDateFrom;
    @XmlElement(name = "limit_date_to")
    protected String limitDateTo;

    /**
     * Gets the value of the limitValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLimitValue() {
        return limitValue;
    }

    /**
     * Sets the value of the limitValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLimitValue(BigDecimal value) {
        this.limitValue = value;
    }

    /**
     * Gets the value of the limitBalanceValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLimitBalanceValue() {
        return limitBalanceValue;
    }

    /**
     * Sets the value of the limitBalanceValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLimitBalanceValue(BigDecimal value) {
        this.limitBalanceValue = value;
    }

    /**
     * Gets the value of the limitName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitName() {
        return limitName;
    }

    /**
     * Sets the value of the limitName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitName(String value) {
        this.limitName = value;
    }

    /**
     * Gets the value of the limitDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitDateFrom() {
        return limitDateFrom;
    }

    /**
     * Sets the value of the limitDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitDateFrom(String value) {
        this.limitDateFrom = value;
    }

    /**
     * Gets the value of the limitDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitDateTo() {
        return limitDateTo;
    }

    /**
     * Sets the value of the limitDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitDateTo(String value) {
        this.limitDateTo = value;
    }

}

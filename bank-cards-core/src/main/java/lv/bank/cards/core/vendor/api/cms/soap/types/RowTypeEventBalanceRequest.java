
package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_EventBalanceRequest complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_EventBalanceRequest"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="code_owner" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="limit_type" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="limit_key_type" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="limit_key_value" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_EventBalanceRequest", propOrder = {
    "codeOwner",
    "limitType",
    "limitKeyType",
    "limitKeyValue"
})
public class RowTypeEventBalanceRequest {

    @XmlElement(name = "code_owner", required = true)
    protected String codeOwner;
    @XmlElement(name = "limit_type", required = true)
    protected String limitType;
    @XmlElement(name = "limit_key_type", required = true)
    protected String limitKeyType;
    @XmlElement(name = "limit_key_value", required = true)
    protected String limitKeyValue;

    /**
     * Gets the value of the codeOwner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeOwner() {
        return codeOwner;
    }

    /**
     * Sets the value of the codeOwner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeOwner(String value) {
        this.codeOwner = value;
    }

    /**
     * Gets the value of the limitType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitType() {
        return limitType;
    }

    /**
     * Sets the value of the limitType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitType(String value) {
        this.limitType = value;
    }

    /**
     * Gets the value of the limitKeyType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitKeyType() {
        return limitKeyType;
    }

    /**
     * Sets the value of the limitKeyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitKeyType(String value) {
        this.limitKeyType = value;
    }

    /**
     * Gets the value of the limitKeyValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitKeyValue() {
        return limitKeyValue;
    }

    /**
     * Sets the value of the limitKeyValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitKeyValue(String value) {
        this.limitKeyValue = value;
    }

}

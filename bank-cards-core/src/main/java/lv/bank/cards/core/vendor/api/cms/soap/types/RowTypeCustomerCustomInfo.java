
package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_CustomerCustomInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_CustomerCustomInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="F_KEY" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="F_VALUE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="F_VALUE_OTHER" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_CustomerCustomInfo", propOrder = {
    "fkey",
    "fvalue",
    "fvalueother"
})
public class RowTypeCustomerCustomInfo {

    @XmlElement(name = "F_KEY", required = true)
    protected String fkey;
    @XmlElement(name = "F_VALUE", required = true)
    protected String fvalue;
    @XmlElement(name = "F_VALUE_OTHER", required = true)
    protected String fvalueother;

    /**
     * Gets the value of the fkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFKEY() {
        return fkey;
    }

    /**
     * Sets the value of the fkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFKEY(String value) {
        this.fkey = value;
    }

    /**
     * Gets the value of the fvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFVALUE() {
        return fvalue;
    }

    /**
     * Sets the value of the fvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFVALUE(String value) {
        this.fvalue = value;
    }

    /**
     * Gets the value of the fvalueother property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFVALUEOTHER() {
        return fvalueother;
    }

    /**
     * Sets the value of the fvalueother property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFVALUEOTHER(String value) {
        this.fvalueother = value;
    }

}

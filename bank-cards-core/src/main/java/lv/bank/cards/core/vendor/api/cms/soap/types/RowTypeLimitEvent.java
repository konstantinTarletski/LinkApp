
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_LimitEvent complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_LimitEvent"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="KEY_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CODE_OWNER" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="KEY_VALUE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="Limit" type="{urn:issuing_v_01_02_xsd}RowType_LimitEvent_Limit" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_LimitEvent", propOrder = {
    "keytype",
    "codeowner",
    "keyvalue",
    "limit"
})
public class RowTypeLimitEvent {

    @XmlElement(name = "KEY_TYPE", required = true)
    protected String keytype;
    @XmlElement(name = "CODE_OWNER", required = true)
    protected String codeowner;
    @XmlElement(name = "KEY_VALUE", required = true)
    protected String keyvalue;
    @XmlElement(name = "Limit")
    protected List<RowTypeLimitEventLimit> limit;

    /**
     * Gets the value of the keytype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKEYTYPE() {
        return keytype;
    }

    /**
     * Sets the value of the keytype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEYTYPE(String value) {
        this.keytype = value;
    }

    /**
     * Gets the value of the codeowner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODEOWNER() {
        return codeowner;
    }

    /**
     * Sets the value of the codeowner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODEOWNER(String value) {
        this.codeowner = value;
    }

    /**
     * Gets the value of the keyvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKEYVALUE() {
        return keyvalue;
    }

    /**
     * Sets the value of the keyvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEYVALUE(String value) {
        this.keyvalue = value;
    }

    /**
     * Gets the value of the limit property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the limit property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getLimit().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RowTypeLimitEventLimit }
     * 
     * 
     */
    public List<RowTypeLimitEventLimit> getLimit() {
        if (limit == null) {
            limit = new ArrayList<RowTypeLimitEventLimit>();
        }
        return this.limit;
    }

}

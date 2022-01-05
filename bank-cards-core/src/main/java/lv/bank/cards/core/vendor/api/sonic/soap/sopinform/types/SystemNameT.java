
package lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Each system belongs to some country
 * 
 * &lt;p&gt;Java class for SystemName_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SystemName_t"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;extension base="&amp;lt;http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV&amp;gt;SystemTextName_t"&amp;gt;
 *       &amp;lt;attribute name="Country" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}TodoCountryList_t" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SystemName_t", propOrder = {
    "value"
})
@XmlSeeAlso({
    SystemNameLVT.class,
    SystemNameLTT.class
})
public class SystemNameT {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "Country")
    protected TodoCountryListT country;

    /**
     * Type for system names
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link TodoCountryListT }
     *     
     */
    public TodoCountryListT getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link TodoCountryListT }
     *     
     */
    public void setCountry(TodoCountryListT value) {
        this.country = value;
    }

}


package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ComplexType_RetrieveDataDetails complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ComplexType_RetrieveDataDetails"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Data" type="{urn:issuing_v_01_02_xsd}ListType_Generic" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComplexType_RetrieveDataDetails", propOrder = {
    "data"
})
public class ComplexTypeRetrieveDataDetails {

    @XmlElement(name = "Data")
    protected ListTypeGeneric data;

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link ListTypeGeneric }
     *     
     */
    public ListTypeGeneric getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListTypeGeneric }
     *     
     */
    public void setData(ListTypeGeneric value) {
        this.data = value;
    }

}

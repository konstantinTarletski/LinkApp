
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ComplexType_RetrieveDataParameters complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ComplexType_RetrieveDataParameters"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="QueryName" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="QueryVariant" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="FilterCondition" type="{urn:issuing_v_01_02_xsd}RowType_FilterCondition" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComplexType_RetrieveDataParameters", propOrder = {
    "queryName",
    "queryVariant",
    "filterCondition"
})
public class ComplexTypeRetrieveDataParameters {

    @XmlElement(name = "QueryName", required = true)
    protected String queryName;
    @XmlElement(name = "QueryVariant", required = true)
    protected String queryVariant;
    @XmlElement(name = "FilterCondition")
    protected List<RowTypeFilterCondition> filterCondition;

    /**
     * Gets the value of the queryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryName() {
        return queryName;
    }

    /**
     * Sets the value of the queryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryName(String value) {
        this.queryName = value;
    }

    /**
     * Gets the value of the queryVariant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryVariant() {
        return queryVariant;
    }

    /**
     * Sets the value of the queryVariant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryVariant(String value) {
        this.queryVariant = value;
    }

    /**
     * Gets the value of the filterCondition property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the filterCondition property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getFilterCondition().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RowTypeFilterCondition }
     * 
     * 
     */
    public List<RowTypeFilterCondition> getFilterCondition() {
        if (filterCondition == null) {
            filterCondition = new ArrayList<RowTypeFilterCondition>();
        }
        return this.filterCondition;
    }

}

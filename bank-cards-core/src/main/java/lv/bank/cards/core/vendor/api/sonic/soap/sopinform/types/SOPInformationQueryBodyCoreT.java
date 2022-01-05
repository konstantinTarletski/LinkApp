
package lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SOPInformationQueryBody_Core_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SOPInformationQueryBody_Core_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}QueryBody_t"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Criteria" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}ClientIDUniqueClientIDQueryCriteria_t"/&amp;gt;
 *         &amp;lt;element name="SOPStatusList" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}SOPStatusList_t" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SOPInformationQueryBody_Core_t", propOrder = {
    "criteria",
    "sopStatusList"
})
@XmlSeeAlso({
    SOPInformationQueryBodyLVT.class
})
public abstract class SOPInformationQueryBodyCoreT
    extends QueryBodyT
{

    @XmlElement(name = "Criteria", required = true)
    protected ClientIDUniqueClientIDQueryCriteriaT criteria;
    @XmlElement(name = "SOPStatusList")
    protected SOPStatusListT sopStatusList;

    /**
     * Gets the value of the criteria property.
     * 
     * @return
     *     possible object is
     *     {@link ClientIDUniqueClientIDQueryCriteriaT }
     *     
     */
    public ClientIDUniqueClientIDQueryCriteriaT getCriteria() {
        return criteria;
    }

    /**
     * Sets the value of the criteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientIDUniqueClientIDQueryCriteriaT }
     *     
     */
    public void setCriteria(ClientIDUniqueClientIDQueryCriteriaT value) {
        this.criteria = value;
    }

    /**
     * Gets the value of the sopStatusList property.
     * 
     * @return
     *     possible object is
     *     {@link SOPStatusListT }
     *     
     */
    public SOPStatusListT getSOPStatusList() {
        return sopStatusList;
    }

    /**
     * Sets the value of the sopStatusList property.
     * 
     * @param value
     *     allowed object is
     *     {@link SOPStatusListT }
     *     
     */
    public void setSOPStatusList(SOPStatusListT value) {
        this.sopStatusList = value;
    }

}


package lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for SOPInformationUpdateQueryBody_Core_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SOPInformationUpdateQueryBody_Core_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}QueryBody_t"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Criteria" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}ClientIDUniqueClientIDQueryCriteria_t"/&amp;gt;
 *         &amp;lt;element name="ProductCode" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}StringWithTextCode_t"/&amp;gt;
 *         &amp;lt;element name="CustomerAnswer" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}StringWithCode_t"/&amp;gt;
 *         &amp;lt;element name="EmailOfProductResponsible" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ChangeBy" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="ChangeDate" type="{http://www.w3.org/2001/XMLSchema}date"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SOPInformationUpdateQueryBody_Core_t", propOrder = {
    "criteria",
    "productCode",
    "customerAnswer",
    "emailOfProductResponsible",
    "changeBy",
    "changeDate"
})
@XmlSeeAlso({
    SOPInformationUpdateQueryBodyLVT.class
})
public abstract class SOPInformationUpdateQueryBodyCoreT
    extends QueryBodyT
{

    @XmlElement(name = "Criteria", required = true)
    protected ClientIDUniqueClientIDQueryCriteriaT criteria;
    @XmlElement(name = "ProductCode", required = true)
    protected StringWithTextCodeT productCode;
    @XmlElement(name = "CustomerAnswer", required = true)
    protected StringWithCodeT customerAnswer;
    @XmlElement(name = "EmailOfProductResponsible")
    protected String emailOfProductResponsible;
    @XmlElement(name = "ChangeBy", required = true)
    protected String changeBy;
    @XmlElement(name = "ChangeDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter3 .class)
    @XmlSchemaType(name = "date")
    protected Date changeDate;

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
     * Gets the value of the productCode property.
     * 
     * @return
     *     possible object is
     *     {@link StringWithTextCodeT }
     *     
     */
    public StringWithTextCodeT getProductCode() {
        return productCode;
    }

    /**
     * Sets the value of the productCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringWithTextCodeT }
     *     
     */
    public void setProductCode(StringWithTextCodeT value) {
        this.productCode = value;
    }

    /**
     * Gets the value of the customerAnswer property.
     * 
     * @return
     *     possible object is
     *     {@link StringWithCodeT }
     *     
     */
    public StringWithCodeT getCustomerAnswer() {
        return customerAnswer;
    }

    /**
     * Sets the value of the customerAnswer property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringWithCodeT }
     *     
     */
    public void setCustomerAnswer(StringWithCodeT value) {
        this.customerAnswer = value;
    }

    /**
     * Gets the value of the emailOfProductResponsible property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailOfProductResponsible() {
        return emailOfProductResponsible;
    }

    /**
     * Sets the value of the emailOfProductResponsible property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailOfProductResponsible(String value) {
        this.emailOfProductResponsible = value;
    }

    /**
     * Gets the value of the changeBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangeBy() {
        return changeBy;
    }

    /**
     * Sets the value of the changeBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangeBy(String value) {
        this.changeBy = value;
    }

    /**
     * Gets the value of the changeDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getChangeDate() {
        return changeDate;
    }

    /**
     * Sets the value of the changeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangeDate(Date value) {
        this.changeDate = value;
    }

}

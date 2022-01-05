
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_CalculateInstallmentPlanNew_Response complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_CalculateInstallmentPlanNew_Response"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="OPEN_FEE_CCY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="OPEN_FEE_AMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Plan" type="{urn:issuing_v_01_02_xsd}ListType_Generic"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_CalculateInstallmentPlanNew_Response", propOrder = {
    "openfeeccy",
    "openfeeamount",
    "plan"
})
public class RowTypeCalculateInstallmentPlanNewResponse {

    @XmlElement(name = "OPEN_FEE_CCY")
    protected String openfeeccy;
    @XmlElement(name = "OPEN_FEE_AMOUNT")
    protected BigDecimal openfeeamount;
    @XmlElement(name = "Plan", required = true)
    protected ListTypeGeneric plan;

    /**
     * Gets the value of the openfeeccy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOPENFEECCY() {
        return openfeeccy;
    }

    /**
     * Sets the value of the openfeeccy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOPENFEECCY(String value) {
        this.openfeeccy = value;
    }

    /**
     * Gets the value of the openfeeamount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOPENFEEAMOUNT() {
        return openfeeamount;
    }

    /**
     * Sets the value of the openfeeamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOPENFEEAMOUNT(BigDecimal value) {
        this.openfeeamount = value;
    }

    /**
     * Gets the value of the plan property.
     * 
     * @return
     *     possible object is
     *     {@link ListTypeGeneric }
     *     
     */
    public ListTypeGeneric getPlan() {
        return plan;
    }

    /**
     * Sets the value of the plan property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListTypeGeneric }
     *     
     */
    public void setPlan(ListTypeGeneric value) {
        this.plan = value;
    }

}

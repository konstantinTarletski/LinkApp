
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_GetInstallmentConditionsForTransaction_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_GetInstallmentConditionsForTransaction_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="SLIP_INTERNAL_NO" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_GetInstallmentConditionsForTransaction_Request", propOrder = {
    "slipinternalno"
})
public class RowTypeGetInstallmentConditionsForTransactionRequest {

    @XmlElement(name = "SLIP_INTERNAL_NO", required = true)
    protected BigDecimal slipinternalno;

    /**
     * Gets the value of the slipinternalno property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSLIPINTERNALNO() {
        return slipinternalno;
    }

    /**
     * Sets the value of the slipinternalno property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSLIPINTERNALNO(BigDecimal value) {
        this.slipinternalno = value;
    }

}

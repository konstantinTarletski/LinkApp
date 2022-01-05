
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_TransferLoyaltyPoints_Response complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_TransferLoyaltyPoints_Response"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="TRANSFERED_AMOUNT" type="{http://www.w3.org/2001/XMLSchema}decimal"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_TransferLoyaltyPoints_Response", propOrder = {
    "transferedamount"
})
public class RowTypeTransferLoyaltyPointsResponse {

    @XmlElement(name = "TRANSFERED_AMOUNT", required = true)
    protected BigDecimal transferedamount;

    /**
     * Gets the value of the transferedamount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRANSFEREDAMOUNT() {
        return transferedamount;
    }

    /**
     * Sets the value of the transferedamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRANSFEREDAMOUNT(BigDecimal value) {
        this.transferedamount = value;
    }

}

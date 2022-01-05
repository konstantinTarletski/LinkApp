
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_CloseInstallmentAgreement_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_CloseInstallmentAgreement_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="INSTL_AGR_NO" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="INSTL_INTER" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_CloseInstallmentAgreement_Request", propOrder = {
    "instlagrno",
    "instlinter"
})
public class RowTypeCloseInstallmentAgreementRequest {

    @XmlElement(name = "INSTL_AGR_NO", required = true)
    protected String instlagrno;
    @XmlElement(name = "INSTL_INTER")
    protected BigDecimal instlinter;

    /**
     * Gets the value of the instlagrno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINSTLAGRNO() {
        return instlagrno;
    }

    /**
     * Sets the value of the instlagrno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINSTLAGRNO(String value) {
        this.instlagrno = value;
    }

    /**
     * Gets the value of the instlinter property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getINSTLINTER() {
        return instlinter;
    }

    /**
     * Sets the value of the instlinter property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setINSTLINTER(BigDecimal value) {
        this.instlinter = value;
    }

}

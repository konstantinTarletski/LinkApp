
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_MakeAccountDormant_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_MakeAccountDormant_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD_ACCT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CCY" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="COMMENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="FEE_CALC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
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
@XmlType(name = "RowType_MakeAccountDormant_Request", propOrder = {
    "cardacct",
    "ccy",
    "comment",
    "feecalc",
    "instlinter"
})
public class RowTypeMakeAccountDormantRequest {

    @XmlElement(name = "CARD_ACCT", required = true)
    protected String cardacct;
    @XmlElement(name = "CCY", required = true)
    protected String ccy;
    @XmlElement(name = "COMMENT")
    protected String comment;
    @XmlElement(name = "FEE_CALC")
    protected String feecalc;
    @XmlElement(name = "INSTL_INTER")
    protected BigDecimal instlinter;

    /**
     * Gets the value of the cardacct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDACCT() {
        return cardacct;
    }

    /**
     * Sets the value of the cardacct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDACCT(String value) {
        this.cardacct = value;
    }

    /**
     * Gets the value of the ccy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCCY() {
        return ccy;
    }

    /**
     * Sets the value of the ccy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCCY(String value) {
        this.ccy = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMMENT() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMMENT(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the feecalc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFEECALC() {
        return feecalc;
    }

    /**
     * Sets the value of the feecalc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFEECALC(String value) {
        this.feecalc = value;
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

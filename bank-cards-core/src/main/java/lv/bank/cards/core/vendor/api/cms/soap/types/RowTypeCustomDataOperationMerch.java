
package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_CustomDataOperationMerch complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_CustomDataOperationMerch"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="OPERATION" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="MERCHANT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="MCC" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="PROC_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_CustomDataOperationMerch", propOrder = {
    "operation",
    "merchant",
    "mcc",
    "proccode"
})
public class RowTypeCustomDataOperationMerch {

    @XmlElement(name = "OPERATION", required = true)
    protected String operation;
    @XmlElement(name = "MERCHANT", required = true)
    protected String merchant;
    @XmlElement(name = "MCC", required = true)
    protected String mcc;
    @XmlElement(name = "PROC_CODE")
    protected String proccode;

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOPERATION() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOPERATION(String value) {
        this.operation = value;
    }

    /**
     * Gets the value of the merchant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMERCHANT() {
        return merchant;
    }

    /**
     * Sets the value of the merchant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMERCHANT(String value) {
        this.merchant = value;
    }

    /**
     * Gets the value of the mcc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMCC() {
        return mcc;
    }

    /**
     * Sets the value of the mcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMCC(String value) {
        this.mcc = value;
    }

    /**
     * Gets the value of the proccode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROCCODE() {
        return proccode;
    }

    /**
     * Sets the value of the proccode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROCCODE(String value) {
        this.proccode = value;
    }

}

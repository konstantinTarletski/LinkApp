
package lv.bank.cards.soap.api.atm.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ConfirmationParameters complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ConfirmationParameters"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="screen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="receipt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="isReceiptOptional" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfirmationParameters", propOrder = {
    "screen",
    "receipt",
    "isReceiptOptional"
})
public class ConfirmationParameters {

    protected String screen;
    protected String receipt;
    protected Boolean isReceiptOptional;

    /**
     * Gets the value of the screen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreen() {
        return screen;
    }

    /**
     * Sets the value of the screen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreen(String value) {
        this.screen = value;
    }

    /**
     * Gets the value of the receipt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceipt() {
        return receipt;
    }

    /**
     * Sets the value of the receipt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceipt(String value) {
        this.receipt = value;
    }

    /**
     * Gets the value of the isReceiptOptional property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsReceiptOptional() {
        return isReceiptOptional;
    }

    /**
     * Sets the value of the isReceiptOptional property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsReceiptOptional(Boolean value) {
        this.isReceiptOptional = value;
    }

}

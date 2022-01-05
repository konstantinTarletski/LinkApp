
package lv.bank.cards.soap.api.atm.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for anonymous complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="details" type="{urn:PaymentServer}DetailArray" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="needDetails" type="{urn:PaymentServer}NeedDetailArray" minOccurs="0"/&amp;gt;
 *         &amp;lt;any namespace='urn:PaymentServer' minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "details",
    "needDetails",
    "any"
})
@XmlRootElement(name = "BlockResponse")
public class BlockResponse {

    protected DetailArray details;
    protected NeedDetailArray needDetails;
    @XmlAnyElement(lax = true)
    protected Object any;

    /**
     * Gets the value of the details property.
     * 
     * @return
     *     possible object is
     *     {@link DetailArray }
     *     
     */
    public DetailArray getDetails() {
        return details;
    }

    /**
     * Sets the value of the details property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailArray }
     *     
     */
    public void setDetails(DetailArray value) {
        this.details = value;
    }

    /**
     * Gets the value of the needDetails property.
     * 
     * @return
     *     possible object is
     *     {@link NeedDetailArray }
     *     
     */
    public NeedDetailArray getNeedDetails() {
        return needDetails;
    }

    /**
     * Sets the value of the needDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link NeedDetailArray }
     *     
     */
    public void setNeedDetails(NeedDetailArray value) {
        this.needDetails = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getAny() {
        return any;
    }

    /**
     * Sets the value of the any property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setAny(Object value) {
        this.any = value;
    }

}


package lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SOP_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SOP_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="SOPStatus" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}StringWithTextCode_t"/&amp;gt;
 *         &amp;lt;element name="Product" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}StringWithTextCode_t"/&amp;gt;
 *         &amp;lt;element name="IBPictureURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IBGeneralTermsAndConditions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IBProductTermsAndConditions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EmailOfProductResponsible" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SOP_t", propOrder = {
    "sopStatus",
    "product",
    "ibPictureURL",
    "ibGeneralTermsAndConditions",
    "ibProductTermsAndConditions",
    "emailOfProductResponsible"
})
public class SOPT {

    @XmlElement(name = "SOPStatus", required = true)
    protected StringWithTextCodeT sopStatus;
    @XmlElement(name = "Product", required = true)
    protected StringWithTextCodeT product;
    @XmlElement(name = "IBPictureURL")
    protected String ibPictureURL;
    @XmlElement(name = "IBGeneralTermsAndConditions")
    protected String ibGeneralTermsAndConditions;
    @XmlElement(name = "IBProductTermsAndConditions")
    protected String ibProductTermsAndConditions;
    @XmlElement(name = "EmailOfProductResponsible")
    protected String emailOfProductResponsible;

    /**
     * Gets the value of the sopStatus property.
     * 
     * @return
     *     possible object is
     *     {@link StringWithTextCodeT }
     *     
     */
    public StringWithTextCodeT getSOPStatus() {
        return sopStatus;
    }

    /**
     * Sets the value of the sopStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringWithTextCodeT }
     *     
     */
    public void setSOPStatus(StringWithTextCodeT value) {
        this.sopStatus = value;
    }

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link StringWithTextCodeT }
     *     
     */
    public StringWithTextCodeT getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringWithTextCodeT }
     *     
     */
    public void setProduct(StringWithTextCodeT value) {
        this.product = value;
    }

    /**
     * Gets the value of the ibPictureURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIBPictureURL() {
        return ibPictureURL;
    }

    /**
     * Sets the value of the ibPictureURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIBPictureURL(String value) {
        this.ibPictureURL = value;
    }

    /**
     * Gets the value of the ibGeneralTermsAndConditions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIBGeneralTermsAndConditions() {
        return ibGeneralTermsAndConditions;
    }

    /**
     * Sets the value of the ibGeneralTermsAndConditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIBGeneralTermsAndConditions(String value) {
        this.ibGeneralTermsAndConditions = value;
    }

    /**
     * Gets the value of the ibProductTermsAndConditions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIBProductTermsAndConditions() {
        return ibProductTermsAndConditions;
    }

    /**
     * Sets the value of the ibProductTermsAndConditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIBProductTermsAndConditions(String value) {
        this.ibProductTermsAndConditions = value;
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

}

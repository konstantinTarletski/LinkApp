
package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_GetRealCard_Response complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_GetRealCard_Response"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="RCARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_GetRealCard_Response", propOrder = {
    "rcard"
})
public class RowTypeGetRealCardResponse {

    @XmlElement(name = "RCARD", required = true)
    protected String rcard;

    /**
     * Gets the value of the rcard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRCARD() {
        return rcard;
    }

    /**
     * Sets the value of the rcard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRCARD(String value) {
        this.rcard = value;
    }

}

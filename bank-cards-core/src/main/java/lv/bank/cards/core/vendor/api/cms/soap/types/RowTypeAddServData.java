
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_AddServData complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_AddServData"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="SERV_TYPE_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SERV_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EXPIRY" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RENEW" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_AddServData", propOrder = {
    "servtypeid",
    "servnumber",
    "expiry",
    "renew"
})
public class RowTypeAddServData {

    @XmlElement(name = "SERV_TYPE_ID")
    protected String servtypeid;
    @XmlElement(name = "SERV_NUMBER")
    protected String servnumber;
    @XmlElement(name = "EXPIRY", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date expiry;
    @XmlElement(name = "RENEW")
    protected String renew;

    /**
     * Gets the value of the servtypeid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSERVTYPEID() {
        return servtypeid;
    }

    /**
     * Sets the value of the servtypeid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSERVTYPEID(String value) {
        this.servtypeid = value;
    }

    /**
     * Gets the value of the servnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSERVNUMBER() {
        return servnumber;
    }

    /**
     * Sets the value of the servnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSERVNUMBER(String value) {
        this.servnumber = value;
    }

    /**
     * Gets the value of the expiry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getEXPIRY() {
        return expiry;
    }

    /**
     * Sets the value of the expiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXPIRY(Date value) {
        this.expiry = value;
    }

    /**
     * Gets the value of the renew property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRENEW() {
        return renew;
    }

    /**
     * Sets the value of the renew property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRENEW(String value) {
        this.renew = value;
    }

}

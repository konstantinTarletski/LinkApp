
package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_ChangeAdditionalService_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_ChangeAdditionalService_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="OPERATION" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="SERV_TYPE_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="SERV_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_ChangeAdditionalService_Request", propOrder = {
    "operation",
    "card",
    "servtypeid",
    "servnumber"
})
public class RowTypeChangeAdditionalServiceRequest {

    @XmlElement(name = "OPERATION", required = true)
    protected String operation;
    @XmlElement(name = "CARD", required = true)
    protected String card;
    @XmlElement(name = "SERV_TYPE_ID", required = true)
    protected String servtypeid;
    @XmlElement(name = "SERV_NUMBER")
    protected String servnumber;

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
     * Gets the value of the card property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARD() {
        return card;
    }

    /**
     * Sets the value of the card property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARD(String value) {
        this.card = value;
    }

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

}


package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_CardInfo_TSM_Data complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_CardInfo_TSM_Data"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="TSM_CLIENT_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TSM_AUTH_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TSM_AUTH_EXPIRY" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_CardInfo_TSM_Data", propOrder = {
    "tsmclientid",
    "tsmauthcode",
    "tsmauthexpiry"
})
public class RowTypeCardInfoTSMData {

    @XmlElement(name = "TSM_CLIENT_ID")
    protected String tsmclientid;
    @XmlElement(name = "TSM_AUTH_CODE")
    protected String tsmauthcode;
    @XmlElement(name = "TSM_AUTH_EXPIRY", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date tsmauthexpiry;

    /**
     * Gets the value of the tsmclientid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTSMCLIENTID() {
        return tsmclientid;
    }

    /**
     * Sets the value of the tsmclientid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTSMCLIENTID(String value) {
        this.tsmclientid = value;
    }

    /**
     * Gets the value of the tsmauthcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTSMAUTHCODE() {
        return tsmauthcode;
    }

    /**
     * Sets the value of the tsmauthcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTSMAUTHCODE(String value) {
        this.tsmauthcode = value;
    }

    /**
     * Gets the value of the tsmauthexpiry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getTSMAUTHEXPIRY() {
        return tsmauthexpiry;
    }

    /**
     * Sets the value of the tsmauthexpiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTSMAUTHEXPIRY(Date value) {
        this.tsmauthexpiry = value;
    }

}

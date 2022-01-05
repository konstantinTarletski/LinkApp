
package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_manageTSMWallet_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_manageTSMWallet_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="OPERATION" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="WALLET_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TSM_SERVICE_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_manageTSMWallet_Request", propOrder = {
    "operation",
    "walletid",
    "card",
    "tsmserviceid"
})
public class RowTypeManageTSMWalletRequest {

    @XmlElement(name = "OPERATION", required = true)
    protected String operation;
    @XmlElement(name = "WALLET_ID", required = true)
    protected String walletid;
    @XmlElement(name = "CARD")
    protected String card;
    @XmlElement(name = "TSM_SERVICE_ID")
    protected String tsmserviceid;

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
     * Gets the value of the walletid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWALLETID() {
        return walletid;
    }

    /**
     * Sets the value of the walletid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWALLETID(String value) {
        this.walletid = value;
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
     * Gets the value of the tsmserviceid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTSMSERVICEID() {
        return tsmserviceid;
    }

    /**
     * Sets the value of the tsmserviceid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTSMSERVICEID(String value) {
        this.tsmserviceid = value;
    }

}

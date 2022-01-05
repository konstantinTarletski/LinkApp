
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_GetSuitableTransactionsForInstallments_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_GetSuitableTransactionsForInstallments_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD_ACCT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CCY" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="OPEN_MODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DATE_FROM" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DATE_TO" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MCC_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TRAN_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_GetSuitableTransactionsForInstallments_Request", propOrder = {
    "cardacct",
    "ccy",
    "code",
    "openmode",
    "datefrom",
    "dateto",
    "mcccode",
    "trantype"
})
public class RowTypeGetSuitableTransactionsForInstallmentsRequest {

    @XmlElement(name = "CARD_ACCT", required = true)
    protected String cardacct;
    @XmlElement(name = "CCY", required = true)
    protected String ccy;
    @XmlElement(name = "CODE")
    protected String code;
    @XmlElement(name = "OPEN_MODE")
    protected String openmode;
    @XmlElement(name = "DATE_FROM", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date datefrom;
    @XmlElement(name = "DATE_TO", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date dateto;
    @XmlElement(name = "MCC_CODE")
    protected String mcccode;
    @XmlElement(name = "TRAN_TYPE")
    protected String trantype;

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
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODE() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODE(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the openmode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOPENMODE() {
        return openmode;
    }

    /**
     * Sets the value of the openmode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOPENMODE(String value) {
        this.openmode = value;
    }

    /**
     * Gets the value of the datefrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDATEFROM() {
        return datefrom;
    }

    /**
     * Sets the value of the datefrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATEFROM(Date value) {
        this.datefrom = value;
    }

    /**
     * Gets the value of the dateto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDATETO() {
        return dateto;
    }

    /**
     * Sets the value of the dateto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATETO(Date value) {
        this.dateto = value;
    }

    /**
     * Gets the value of the mcccode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMCCCODE() {
        return mcccode;
    }

    /**
     * Sets the value of the mcccode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMCCCODE(String value) {
        this.mcccode = value;
    }

    /**
     * Gets the value of the trantype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRANTYPE() {
        return trantype;
    }

    /**
     * Sets the value of the trantype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRANTYPE(String value) {
        this.trantype = value;
    }

}


package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigInteger;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_QueryBillInfo_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_QueryBillInfo_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CLIENT" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="CLIENT_B" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="ACCOUNT_NO" type="{http://www.w3.org/2001/XMLSchema}integer"/&amp;gt;
 *         &amp;lt;element name="BACCOUNT_NO" type="{http://www.w3.org/2001/XMLSchema}integer"/&amp;gt;
 *         &amp;lt;element name="INTERNAL_NO" type="{http://www.w3.org/2001/XMLSchema}integer"/&amp;gt;
 *         &amp;lt;element name="BILL_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *         &amp;lt;element name="BANK_C" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_QueryBillInfo_Request", propOrder = {
    "client",
    "clientb",
    "accountno",
    "baccountno",
    "internalno",
    "billdate",
    "bankc"
})
public class RowTypeQueryBillInfoRequest {

    @XmlElement(name = "CLIENT", required = true)
    protected String client;
    @XmlElement(name = "CLIENT_B", required = true)
    protected String clientb;
    @XmlElement(name = "ACCOUNT_NO", required = true)
    protected BigInteger accountno;
    @XmlElement(name = "BACCOUNT_NO", required = true)
    protected BigInteger baccountno;
    @XmlElement(name = "INTERNAL_NO", required = true)
    protected BigInteger internalno;
    @XmlElement(name = "BILL_DATE", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date billdate;
    @XmlElement(name = "BANK_C", required = true)
    protected String bankc;

    /**
     * Gets the value of the client property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLIENT() {
        return client;
    }

    /**
     * Sets the value of the client property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLIENT(String value) {
        this.client = value;
    }

    /**
     * Gets the value of the clientb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLIENTB() {
        return clientb;
    }

    /**
     * Sets the value of the clientb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLIENTB(String value) {
        this.clientb = value;
    }

    /**
     * Gets the value of the accountno property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getACCOUNTNO() {
        return accountno;
    }

    /**
     * Sets the value of the accountno property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setACCOUNTNO(BigInteger value) {
        this.accountno = value;
    }

    /**
     * Gets the value of the baccountno property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBACCOUNTNO() {
        return baccountno;
    }

    /**
     * Sets the value of the baccountno property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBACCOUNTNO(BigInteger value) {
        this.baccountno = value;
    }

    /**
     * Gets the value of the internalno property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getINTERNALNO() {
        return internalno;
    }

    /**
     * Sets the value of the internalno property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setINTERNALNO(BigInteger value) {
        this.internalno = value;
    }

    /**
     * Gets the value of the billdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getBILLDATE() {
        return billdate;
    }

    /**
     * Sets the value of the billdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBILLDATE(Date value) {
        this.billdate = value;
    }

    /**
     * Gets the value of the bankc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKC() {
        return bankc;
    }

    /**
     * Sets the value of the bankc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKC(String value) {
        this.bankc = value;
    }

}

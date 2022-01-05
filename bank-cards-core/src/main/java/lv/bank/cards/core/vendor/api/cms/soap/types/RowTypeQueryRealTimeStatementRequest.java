
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for RowType_QueryRealTimeStatement_Request complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_QueryRealTimeStatement_Request"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CARD" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="BEGIN_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *         &amp;lt;element name="END_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *         &amp;lt;element name="INCL_UNPROC_TR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="INCL_LOCKS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_QueryRealTimeStatement_Request", propOrder = {
    "card",
    "begindate",
    "enddate",
    "inclunproctr",
    "incllocks"
})
public class RowTypeQueryRealTimeStatementRequest {

    @XmlElement(name = "CARD", required = true)
    protected String card;
    @XmlElement(name = "BEGIN_DATE", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date begindate;
    @XmlElement(name = "END_DATE", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date enddate;
    @XmlElement(name = "INCL_UNPROC_TR")
    protected String inclunproctr;
    @XmlElement(name = "INCL_LOCKS")
    protected String incllocks;

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
     * Gets the value of the begindate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getBEGINDATE() {
        return begindate;
    }

    /**
     * Sets the value of the begindate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBEGINDATE(Date value) {
        this.begindate = value;
    }

    /**
     * Gets the value of the enddate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getENDDATE() {
        return enddate;
    }

    /**
     * Sets the value of the enddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setENDDATE(Date value) {
        this.enddate = value;
    }

    /**
     * Gets the value of the inclunproctr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINCLUNPROCTR() {
        return inclunproctr;
    }

    /**
     * Sets the value of the inclunproctr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINCLUNPROCTR(String value) {
        this.inclunproctr = value;
    }

    /**
     * Gets the value of the incllocks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINCLLOCKS() {
        return incllocks;
    }

    /**
     * Sets the value of the incllocks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINCLLOCKS(String value) {
        this.incllocks = value;
    }

}

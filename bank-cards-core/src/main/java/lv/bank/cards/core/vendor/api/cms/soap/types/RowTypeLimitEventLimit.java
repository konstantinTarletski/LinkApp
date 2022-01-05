
package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_LimitEvent_Limit complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_LimitEvent_Limit"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="LIMIT_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="LIMIT_VALUE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LIMIT_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SUBJECT_VALUE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LIMIT_DATE_FROM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LIMIT_DATE_TO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_LimitEvent_Limit", propOrder = {
    "limittype",
    "limitvalue",
    "limitname",
    "subjectvalue",
    "limitdatefrom",
    "limitdateto"
})
public class RowTypeLimitEventLimit {

    @XmlElement(name = "LIMIT_TYPE", required = true)
    protected String limittype;
    @XmlElement(name = "LIMIT_VALUE")
    protected BigDecimal limitvalue;
    @XmlElement(name = "LIMIT_NAME")
    protected String limitname;
    @XmlElement(name = "SUBJECT_VALUE")
    protected String subjectvalue;
    @XmlElement(name = "LIMIT_DATE_FROM")
    protected String limitdatefrom;
    @XmlElement(name = "LIMIT_DATE_TO")
    protected String limitdateto;

    /**
     * Gets the value of the limittype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLIMITTYPE() {
        return limittype;
    }

    /**
     * Sets the value of the limittype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLIMITTYPE(String value) {
        this.limittype = value;
    }

    /**
     * Gets the value of the limitvalue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLIMITVALUE() {
        return limitvalue;
    }

    /**
     * Sets the value of the limitvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLIMITVALUE(BigDecimal value) {
        this.limitvalue = value;
    }

    /**
     * Gets the value of the limitname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLIMITNAME() {
        return limitname;
    }

    /**
     * Sets the value of the limitname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLIMITNAME(String value) {
        this.limitname = value;
    }

    /**
     * Gets the value of the subjectvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUBJECTVALUE() {
        return subjectvalue;
    }

    /**
     * Sets the value of the subjectvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUBJECTVALUE(String value) {
        this.subjectvalue = value;
    }

    /**
     * Gets the value of the limitdatefrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLIMITDATEFROM() {
        return limitdatefrom;
    }

    /**
     * Sets the value of the limitdatefrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLIMITDATEFROM(String value) {
        this.limitdatefrom = value;
    }

    /**
     * Gets the value of the limitdateto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLIMITDATETO() {
        return limitdateto;
    }

    /**
     * Sets the value of the limitdateto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLIMITDATETO(String value) {
        this.limitdateto = value;
    }

}


package lv.bank.cards.core.vendor.api.cms.soap.types;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_AddTechAcctInitInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_AddTechAcctInitInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="ACCOUNT_NO" type="{http://www.w3.org/2001/XMLSchema}integer"/&amp;gt;
 *         &amp;lt;element name="ACCNT_SCHEME" type="{http://www.w3.org/2001/XMLSchema}integer"/&amp;gt;
 *         &amp;lt;element name="TA_TYPE" type="{http://www.w3.org/2001/XMLSchema}integer"/&amp;gt;
 *         &amp;lt;element name="GL_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_AddTechAcctInitInfo", propOrder = {
    "accountno",
    "accntscheme",
    "tatype",
    "glcode"
})
public class RowTypeAddTechAcctInitInfo {

    @XmlElement(name = "ACCOUNT_NO", required = true)
    protected BigInteger accountno;
    @XmlElement(name = "ACCNT_SCHEME", required = true)
    protected BigInteger accntscheme;
    @XmlElement(name = "TA_TYPE", required = true)
    protected BigInteger tatype;
    @XmlElement(name = "GL_CODE", required = true)
    protected String glcode;

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
     * Gets the value of the accntscheme property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getACCNTSCHEME() {
        return accntscheme;
    }

    /**
     * Sets the value of the accntscheme property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setACCNTSCHEME(BigInteger value) {
        this.accntscheme = value;
    }

    /**
     * Gets the value of the tatype property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTATYPE() {
        return tatype;
    }

    /**
     * Sets the value of the tatype property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTATYPE(BigInteger value) {
        this.tatype = value;
    }

    /**
     * Gets the value of the glcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGLCODE() {
        return glcode;
    }

    /**
     * Sets the value of the glcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGLCODE(String value) {
        this.glcode = value;
    }

}

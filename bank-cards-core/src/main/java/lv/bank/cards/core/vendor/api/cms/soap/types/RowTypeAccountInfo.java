
package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_AccountInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_AccountInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="base_info" type="{urn:issuing_v_01_02_xsd}RowType_AccountInfo_Base"/&amp;gt;
 *         &amp;lt;element name="additional_info" type="{urn:issuing_v_01_02_xsd}RowType_AccountInfo_Additional"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_AccountInfo", propOrder = {
    "baseInfo",
    "additionalInfo"
})
public class RowTypeAccountInfo {

    @XmlElement(name = "base_info", required = true)
    protected RowTypeAccountInfoBase baseInfo;
    @XmlElement(name = "additional_info", required = true)
    protected RowTypeAccountInfoAdditional additionalInfo;

    /**
     * Gets the value of the baseInfo property.
     * 
     * @return
     *     possible object is
     *     {@link RowTypeAccountInfoBase }
     *     
     */
    public RowTypeAccountInfoBase getBaseInfo() {
        return baseInfo;
    }

    /**
     * Sets the value of the baseInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link RowTypeAccountInfoBase }
     *     
     */
    public void setBaseInfo(RowTypeAccountInfoBase value) {
        this.baseInfo = value;
    }

    /**
     * Gets the value of the additionalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link RowTypeAccountInfoAdditional }
     *     
     */
    public RowTypeAccountInfoAdditional getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets the value of the additionalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link RowTypeAccountInfoAdditional }
     *     
     */
    public void setAdditionalInfo(RowTypeAccountInfoAdditional value) {
        this.additionalInfo = value;
    }

}

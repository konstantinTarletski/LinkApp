
package lv.bank.cards.core.vendor.api.cms.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RowType_CardInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RowType_CardInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="LogicalCard" type="{urn:issuing_v_01_02_xsd}RowType_CardInfo_LogicalCard"/&amp;gt;
 *         &amp;lt;element name="PhysicalCard" type="{urn:issuing_v_01_02_xsd}RowType_CardInfo_PhysicalCard" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EMV_Data" type="{urn:issuing_v_01_02_xsd}RowType_CardInfo_EMV_Data" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AddServInfo" type="{urn:issuing_v_01_02_xsd}ListType_AddServInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TSM_Data" type="{urn:issuing_v_01_02_xsd}RowType_CardInfo_TSM_Data" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RowType_CardInfo", propOrder = {
    "logicalCard",
    "physicalCard",
    "emvData",
    "addServInfo",
    "tsmData"
})
public class RowTypeCardInfo {

    @XmlElement(name = "LogicalCard", required = true)
    protected RowTypeCardInfoLogicalCard logicalCard;
    @XmlElement(name = "PhysicalCard")
    protected RowTypeCardInfoPhysicalCard physicalCard;
    @XmlElement(name = "EMV_Data")
    protected RowTypeCardInfoEMVData emvData;
    @XmlElement(name = "AddServInfo")
    protected ListTypeAddServInfo addServInfo;
    @XmlElement(name = "TSM_Data")
    protected RowTypeCardInfoTSMData tsmData;

    /**
     * Gets the value of the logicalCard property.
     * 
     * @return
     *     possible object is
     *     {@link RowTypeCardInfoLogicalCard }
     *     
     */
    public RowTypeCardInfoLogicalCard getLogicalCard() {
        return logicalCard;
    }

    /**
     * Sets the value of the logicalCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link RowTypeCardInfoLogicalCard }
     *     
     */
    public void setLogicalCard(RowTypeCardInfoLogicalCard value) {
        this.logicalCard = value;
    }

    /**
     * Gets the value of the physicalCard property.
     * 
     * @return
     *     possible object is
     *     {@link RowTypeCardInfoPhysicalCard }
     *     
     */
    public RowTypeCardInfoPhysicalCard getPhysicalCard() {
        return physicalCard;
    }

    /**
     * Sets the value of the physicalCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link RowTypeCardInfoPhysicalCard }
     *     
     */
    public void setPhysicalCard(RowTypeCardInfoPhysicalCard value) {
        this.physicalCard = value;
    }

    /**
     * Gets the value of the emvData property.
     * 
     * @return
     *     possible object is
     *     {@link RowTypeCardInfoEMVData }
     *     
     */
    public RowTypeCardInfoEMVData getEMVData() {
        return emvData;
    }

    /**
     * Sets the value of the emvData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RowTypeCardInfoEMVData }
     *     
     */
    public void setEMVData(RowTypeCardInfoEMVData value) {
        this.emvData = value;
    }

    /**
     * Gets the value of the addServInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ListTypeAddServInfo }
     *     
     */
    public ListTypeAddServInfo getAddServInfo() {
        return addServInfo;
    }

    /**
     * Sets the value of the addServInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListTypeAddServInfo }
     *     
     */
    public void setAddServInfo(ListTypeAddServInfo value) {
        this.addServInfo = value;
    }

    /**
     * Gets the value of the tsmData property.
     * 
     * @return
     *     possible object is
     *     {@link RowTypeCardInfoTSMData }
     *     
     */
    public RowTypeCardInfoTSMData getTSMData() {
        return tsmData;
    }

    /**
     * Sets the value of the tsmData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RowTypeCardInfoTSMData }
     *     
     */
    public void setTSMData(RowTypeCardInfoTSMData value) {
        this.tsmData = value;
    }

}

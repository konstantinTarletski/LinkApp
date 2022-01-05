
package lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ResultHeader_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ResultHeader_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}integer"/&amp;gt;
 *         &amp;lt;element name="CodeInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Language" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}CountryCode_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MessageID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="QueryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SystemName" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}SystemName_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="XMLVersion" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}XMLVersion_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Extension" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}Extension_t" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultHeader_t", propOrder = {
    "code",
    "codeInfo",
    "language",
    "messageID",
    "queryName",
    "systemName",
    "xmlVersion",
    "extension"
})
@XmlSeeAlso({
    SOPInformationResultHeaderCoreT.class
})
public abstract class ResultHeaderT {

    @XmlElement(name = "Code", required = true)
    protected BigInteger code;
    @XmlElement(name = "CodeInfo")
    protected String codeInfo;
    @XmlElement(name = "Language")
    protected String language;
    @XmlElement(name = "MessageID")
    protected String messageID;
    @XmlElement(name = "QueryName")
    protected String queryName;
    @XmlElement(name = "SystemName")
    protected SystemNameT systemName;
    @XmlElement(name = "XMLVersion")
    protected String xmlVersion;
    @XmlElement(name = "Extension")
    protected ExtensionT extension;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCode(BigInteger value) {
        this.code = value;
    }

    /**
     * Gets the value of the codeInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeInfo() {
        return codeInfo;
    }

    /**
     * Sets the value of the codeInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeInfo(String value) {
        this.codeInfo = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the messageID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageID() {
        return messageID;
    }

    /**
     * Sets the value of the messageID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageID(String value) {
        this.messageID = value;
    }

    /**
     * Gets the value of the queryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryName() {
        return queryName;
    }

    /**
     * Sets the value of the queryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryName(String value) {
        this.queryName = value;
    }

    /**
     * Gets the value of the systemName property.
     * 
     * @return
     *     possible object is
     *     {@link SystemNameT }
     *     
     */
    public SystemNameT getSystemName() {
        return systemName;
    }

    /**
     * Sets the value of the systemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SystemNameT }
     *     
     */
    public void setSystemName(SystemNameT value) {
        this.systemName = value;
    }

    /**
     * Gets the value of the xmlVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXMLVersion() {
        return xmlVersion;
    }

    /**
     * Sets the value of the xmlVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXMLVersion(String value) {
        this.xmlVersion = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionT }
     *     
     */
    public ExtensionT getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionT }
     *     
     */
    public void setExtension(ExtensionT value) {
        this.extension = value;
    }

}

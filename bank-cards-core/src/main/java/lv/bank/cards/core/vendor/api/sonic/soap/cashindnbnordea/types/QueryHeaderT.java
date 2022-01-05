
package lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for QueryHeader_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="QueryHeader_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="MessageID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="QueryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SystemName" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}SystemName_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="QueryCriteria" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}QueryCriteria_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="XMLVersion" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}XMLVersion_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Language" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}CountryCode_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Extension" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}Extension_t" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryHeader_t", propOrder = {
    "messageID",
    "queryName",
    "systemName",
    "queryCriteria",
    "xmlVersion",
    "language",
    "extension"
})
@XmlSeeAlso({
    CashInDNBNordeaQueryHeaderCoreT.class
})
public abstract class QueryHeaderT {

    @XmlElement(name = "MessageID")
    protected String messageID;
    @XmlElement(name = "QueryName")
    protected String queryName;
    @XmlElement(name = "SystemName")
    protected SystemNameT systemName;
    @XmlElement(name = "QueryCriteria")
    protected QueryCriteriaT queryCriteria;
    @XmlElement(name = "XMLVersion")
    protected String xmlVersion;
    @XmlElement(name = "Language")
    protected String language;
    @XmlElement(name = "Extension")
    protected ExtensionT extension;

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
     * Gets the value of the queryCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link QueryCriteriaT }
     *     
     */
    public QueryCriteriaT getQueryCriteria() {
        return queryCriteria;
    }

    /**
     * Sets the value of the queryCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryCriteriaT }
     *     
     */
    public void setQueryCriteria(QueryCriteriaT value) {
        this.queryCriteria = value;
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

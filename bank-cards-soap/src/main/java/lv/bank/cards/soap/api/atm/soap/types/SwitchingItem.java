
package lv.bank.cards.soap.api.atm.soap.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SwitchingItem complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SwitchingItem"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="objectID" type="{http://www.w3.org/2001/XMLSchema}int"/&amp;gt;
 *         &amp;lt;element name="isFolder" type="{http://www.w3.org/2001/XMLSchema}boolean"/&amp;gt;
 *         &amp;lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SwitchingItem", propOrder = {
    "objectID",
    "isFolder",
    "label"
})
public class SwitchingItem {

    protected int objectID;
    protected boolean isFolder;
    @XmlElement(required = true)
    protected String label;

    /**
     * Gets the value of the objectID property.
     * 
     */
    public int getObjectID() {
        return objectID;
    }

    /**
     * Sets the value of the objectID property.
     * 
     */
    public void setObjectID(int value) {
        this.objectID = value;
    }

    /**
     * Gets the value of the isFolder property.
     * 
     */
    public boolean isIsFolder() {
        return isFolder;
    }

    /**
     * Sets the value of the isFolder property.
     * 
     */
    public void setIsFolder(boolean value) {
        this.isFolder = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

}

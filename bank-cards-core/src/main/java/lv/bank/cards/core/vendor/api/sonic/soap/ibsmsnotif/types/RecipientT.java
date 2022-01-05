
package lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for Recipient_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="Recipient_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Addr" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}Addr_t"/&amp;gt;
 *         &amp;lt;element name="ParamList" type="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}ParamList_t" maxOccurs="unbounded"/&amp;gt;
 *         &amp;lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ExpDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Recipient_t", propOrder = {
    "addr",
    "paramList",
    "startDate",
    "expDate"
})
public class RecipientT {

    @XmlElement(name = "Addr", required = true)
    protected AddrT addr;
    @XmlElement(name = "ParamList", required = true)
    protected List<ParamListT> paramList;
    @XmlElement(name = "StartDate", type = String.class)
    @XmlJavaTypeAdapter(Adapter3 .class)
    @XmlSchemaType(name = "date")
    protected Date startDate;
    @XmlElement(name = "ExpDate", type = String.class)
    @XmlJavaTypeAdapter(Adapter3 .class)
    @XmlSchemaType(name = "date")
    protected Date expDate;

    /**
     * Gets the value of the addr property.
     * 
     * @return
     *     possible object is
     *     {@link AddrT }
     *     
     */
    public AddrT getAddr() {
        return addr;
    }

    /**
     * Sets the value of the addr property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddrT }
     *     
     */
    public void setAddr(AddrT value) {
        this.addr = value;
    }

    /**
     * Gets the value of the paramList property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the paramList property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getParamList().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ParamListT }
     * 
     * 
     */
    public List<ParamListT> getParamList() {
        if (paramList == null) {
            paramList = new ArrayList<ParamListT>();
        }
        return this.paramList;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(Date value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the expDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getExpDate() {
        return expDate;
    }

    /**
     * Sets the value of the expDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpDate(Date value) {
        this.expDate = value;
    }

}

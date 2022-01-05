
package lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for CashInDNBNordeaResultHeader_Core_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="CashInDNBNordeaResultHeader_Core_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}ResultHeader_t"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}integer"/&amp;gt;
 *         &amp;lt;element name="CodeInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Language" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}CountryCode_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="QueryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SystemName" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}SystemName_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="XMLVersion" type="{http://dnb.lv/dnb-xst/dnb-linkapp-CashInDNBNordea-LV}XMLVersion_t" minOccurs="0"/&amp;gt;
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
@XmlType(name = "CashInDNBNordeaResultHeader_Core_t")
@XmlSeeAlso({
    CashInDNBNordeaResultHeaderLVT.class
})
public abstract class CashInDNBNordeaResultHeaderCoreT
    extends ResultHeaderT
{


}

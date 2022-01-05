
package lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SOPInformationUpdateQueryHeader_Core_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SOPInformationUpdateQueryHeader_Core_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}QueryHeader_t"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="QueryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SystemName" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}SystemName_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="QueryCriteria" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}QueryCriteria_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="XMLVersion" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}XMLVersion_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Language" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}CountryCode_t" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Extension" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}Extension_t" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SOPInformationUpdateQueryHeader_Core_t")
@XmlSeeAlso({
    SOPInformationUpdateQueryHeaderLVT.class
})
public abstract class SOPInformationUpdateQueryHeaderCoreT
    extends QueryHeaderT
{


}

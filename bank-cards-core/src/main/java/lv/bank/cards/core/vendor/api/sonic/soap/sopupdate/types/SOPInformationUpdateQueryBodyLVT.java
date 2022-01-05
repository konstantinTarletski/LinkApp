
package lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SOPInformationUpdateQueryBody_LV_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SOPInformationUpdateQueryBody_LV_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}SOPInformationUpdateQueryBody_Core_t"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Criteria" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}ClientIDUniqueClientIDQueryCriteria_t"/&amp;gt;
 *         &amp;lt;element name="ProductCode" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}StringWithTextCode_t"/&amp;gt;
 *         &amp;lt;element name="CustomerAnswer" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV}StringWithCode_t"/&amp;gt;
 *         &amp;lt;element name="EmailOfProductResponsible" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ChangeBy" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="ChangeDate" type="{http://www.w3.org/2001/XMLSchema}date"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SOPInformationUpdateQueryBody_LV_t")
public class SOPInformationUpdateQueryBodyLVT
    extends SOPInformationUpdateQueryBodyCoreT
{


}

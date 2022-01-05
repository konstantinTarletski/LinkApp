
package lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SOPInformationQueryBody_LV_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SOPInformationQueryBody_LV_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}SOPInformationQueryBody_Core_t"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Criteria" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}ClientIDUniqueClientIDQueryCriteria_t"/&amp;gt;
 *         &amp;lt;element name="SOPStatusList" type="{http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV}SOPStatusList_t" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SOPInformationQueryBody_LV_t")
public class SOPInformationQueryBodyLVT
    extends SOPInformationQueryBodyCoreT
{


}

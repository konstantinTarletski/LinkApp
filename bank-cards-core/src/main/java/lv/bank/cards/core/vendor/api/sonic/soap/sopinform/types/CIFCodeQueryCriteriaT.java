
package lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Query criteria by Latvian CIF code
 * 
 * &lt;p&gt;Java class for CIFCodeQueryCriteria_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="CIFCodeQueryCriteria_t"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;restriction base="&amp;lt;http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformation-LV&amp;gt;QueryCriteria_t"&amp;gt;
 *       &amp;lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="CIFCode" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CIFCodeQueryCriteria_t")
public class CIFCodeQueryCriteriaT
    extends QueryCriteriaT
{


}

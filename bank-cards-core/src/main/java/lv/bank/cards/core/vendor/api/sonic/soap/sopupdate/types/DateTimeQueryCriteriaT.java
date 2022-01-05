
package lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                         Type used for queries that depend on date and
 *                         time.
 *                     
 * 
 * &lt;p&gt;Java class for DateTimeQueryCriteria_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="DateTimeQueryCriteria_t"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;restriction base="&amp;lt;http://dnb.lv/dnb-xst/dnb-linkapp-SOPInformationUpdate-LV&amp;gt;QueryCriteria_t"&amp;gt;
 *       &amp;lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="DateTime" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateTimeQueryCriteria_t")
public class DateTimeQueryCriteriaT
    extends QueryCriteriaT
{


}

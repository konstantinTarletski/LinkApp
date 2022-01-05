
package lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Query criteria that allows to use either CardNumber or CADApplicationID.
 * 
 * &lt;p&gt;Java class for CardNumberOrCADApplicationIDQueryCriteria_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="CardNumberOrCADApplicationIDQueryCriteria_t"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;restriction base="&amp;lt;http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV&amp;gt;QueryCriteria_t"&amp;gt;
 *       &amp;lt;attribute name="type" use="required"&amp;gt;
 *         &amp;lt;simpleType&amp;gt;
 *           &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *             &amp;lt;enumeration value="CardNumber"/&amp;gt;
 *             &amp;lt;enumeration value="CADApplicationID"/&amp;gt;
 *           &amp;lt;/restriction&amp;gt;
 *         &amp;lt;/simpleType&amp;gt;
 *       &amp;lt;/attribute&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardNumberOrCADApplicationIDQueryCriteria_t")
public class CardNumberOrCADApplicationIDQueryCriteriaT
    extends QueryCriteriaT
{


}

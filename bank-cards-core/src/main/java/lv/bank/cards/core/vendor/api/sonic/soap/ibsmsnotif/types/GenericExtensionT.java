
package lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                         A common type for extensions that do not have any additional fields.
 *                         Please, note that this type can only be used in the implementation files.
 *                         For abstract structures Extension_t can be used.
 *                         For other cases a specific type derived from Extension_t should be used.
 *                     
 * 
 * &lt;p&gt;Java class for GenericExtension_t complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="GenericExtension_t"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://dnb.lv/dnb-xst/dnb-linkapp-Notification-LV}Extension_t"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;any processContents='skip' maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenericExtension_t")
public class GenericExtensionT
    extends ExtensionT
{


}

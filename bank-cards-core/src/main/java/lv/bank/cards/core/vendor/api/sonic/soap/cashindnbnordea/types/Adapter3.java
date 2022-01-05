
package lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter3
    extends XmlAdapter<String, Date>
{


    public Date unmarshal(String value) {
        return (lv.bank.cards.core.utils.DataTypeAdapter.parseDate(value));
    }

    public String marshal(Date value) {
        return (lv.bank.cards.core.utils.DataTypeAdapter.printDate(value));
    }

}

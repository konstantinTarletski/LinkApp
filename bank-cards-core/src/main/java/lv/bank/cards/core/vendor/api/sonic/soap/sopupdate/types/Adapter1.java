
package lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, Date>
{


    public Date unmarshal(String value) {
        return (lv.bank.cards.core.utils.DataTypeAdapter.parseDateTime(value));
    }

    public String marshal(Date value) {
        return (lv.bank.cards.core.utils.DataTypeAdapter.printDateTime(value));
    }

}

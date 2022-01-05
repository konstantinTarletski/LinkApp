
package lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter2
    extends XmlAdapter<String, Date>
{


    public Date unmarshal(String value) {
        return (lv.bank.cards.core.utils.DataTypeAdapter.parseTime(value));
    }

    public String marshal(Date value) {
        return (lv.bank.cards.core.utils.DataTypeAdapter.printTime(value));
    }

}

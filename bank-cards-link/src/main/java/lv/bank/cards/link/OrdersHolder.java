package lv.bank.cards.link;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class OrdersHolder {

    protected final ObjectMapper mapper = new ObjectMapper();
    protected final Document doc;
    protected final Iterator<?> orderIterator;
    @Getter
    protected final String bankc;

    public OrdersHolder(Document document) {
        super();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        doc = document;
        orderIterator = doc.getRootElement().elementIterator("order");
        bankc = getFirstAttributeValue(doc.getRootElement(), "bank_c");
    }

    public boolean hasMoreOrders() {
        return (orderIterator == null) ? false : orderIterator.hasNext();
    }

    String getFirstTagValue(Element e, String name) {
        for (Iterator<?> i = e.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            if (element.getName().equals(name)) {
                return element.getText();
            }
        }
        return null;
    }

    protected String getFirstAttributeValue(Element e, String name) {
        for (Iterator<?> i = e.attributeIterator(); i.hasNext(); ) {
            Attribute element = (Attribute) i.next();
            if (element.getName().equals(name)) {
                return element.getText();
            }
        }
        return null;
    }

    public Order getNextOrder() {
        Element thisOrderHolder = (Element) orderIterator.next();
        final Map<String, String> map = new HashMap<>();
        map.put("bankc", bankc);
        map.put("groupc", getFirstTagValue(thisOrderHolder, "groupc"));
        map.put("action", getFirstAttributeValue(thisOrderHolder, "action"));
        for (Iterator<?> i = thisOrderHolder.elementIterator(); i.hasNext(); ) {
            Element e = (Element) i.next();
            map.put(e.getName(), e.getText());
        }
        log.info("Order after parsing to map = {}", map);
        Order ret = mapper.convertValue(map, Order.class);
        log.info("Order after parsing to object = {}", ret);
        return ret;
    }

}

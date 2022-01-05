package lv.bank.cards.core.utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class XMLHelperTest {

    @Test
    public void getElementValue() {
        Element doElement = DocumentHelper.createElement("do");
        doElement.addAttribute("what", "card-info");
        Element card = DocumentHelper.createElement("card");
        card.setText("31231231231");
        doElement.add(card);
        Element status0 = DocumentHelper.createElement("status");
        status0.setText("0");
        doElement.add(status0);
        Element status1 = DocumentHelper.createElement("status");
        status1.setText("1");
        doElement.add(status1);
        Document doc = DocumentHelper.createDocument(doElement);

        assertEquals("31231231231", XmlUtils.getElementValue(doc, "/do/card"));
        assertEquals("0", XmlUtils.getElementValue(doc, "/do/status")); // If more than one then gets first one
        assertNull(XmlUtils.getElementValue(doc, "/do/description"));
        // Wrong xpath
        boolean hadError = false;
        try {
            XmlUtils.getElementValue(doc, "/do/@what");
        } catch (ClassCastException e) {
            hadError = true;
        }
        assertTrue(hadError);
    }

    @Test
    public void getAttributeValue() {
        Element doElement = DocumentHelper.createElement("do");
        doElement.addAttribute("what", "card-info");
        doElement.addAttribute("template", "t1");
        doElement.addAttribute("template", "t2");
        Element card = DocumentHelper.createElement("card");
        card.setText("31231231231");
        doElement.add(card);
        Document doc = DocumentHelper.createDocument(doElement);

        assertEquals("card-info", XmlUtils.getAttributeValue(doc, "/do/@what"));
        assertEquals("t2", XmlUtils.getAttributeValue(doc, "/do/@template")); // If more than one then gets last one
        assertNull(XmlUtils.getAttributeValue(doc, "/do/@operator"));
        // Wrong xpath
        boolean hadError = false;
        try {
            XmlUtils.getAttributeValue(doc, "/do/card");
        } catch (ClassCastException e) {
            hadError = true;
        }
        assertTrue(hadError);
    }
}

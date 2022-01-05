package lv.bank.cards.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.xml.sax.InputSource;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XmlUtils {

    private static Object selectIt(Document doc, String selector) {
        XPath xpathSelector = DocumentHelper.createXPath(selector);
        List<?> xmlResult = xpathSelector.selectNodes(doc);
        Iterator<?> xmlIter = xmlResult.iterator();
        if (xmlIter.hasNext()) {
            return xmlIter.next();
        }
        return null;
    }

    public static String getElementValue(Document doc, String selector) {
        Element element = (Element) selectIt(doc, selector);
        return element == null ? null : element.getText();
    }

    public static String getAttributeValue(Document doc, String selector) {
        Attribute attribute = (Attribute) selectIt(doc, selector);
        return attribute == null ? null : attribute.getValue();
    }

    public static String getFormattedXml(String unFormattedXml) {
        try {
            Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            Source xmlSource = new SAXSource(new InputSource(new ByteArrayInputStream(unFormattedXml.getBytes())));
            StreamResult res = new StreamResult(new ByteArrayOutputStream());
            serializer.transform(xmlSource, res);
            return new String(((ByteArrayOutputStream) res.getOutputStream()).toByteArray());
        } catch (Exception e) {
            log.info("Unable format XML", e);
            return unFormattedXml;
        }
    }

}

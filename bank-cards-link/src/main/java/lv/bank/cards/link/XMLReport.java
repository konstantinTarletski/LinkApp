package lv.bank.cards.link;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.Serializable;

@Slf4j
public class XMLReport implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Document report;
    protected Element reportRoot;

    public XMLReport() {
        super();
        report = DocumentHelper.createDocument();
        reportRoot = report.addElement("report");
    }

    public void addError(String id, String text) {
        Element e = reportRoot.addElement("order");
        e.addElement("id").addText(id == null ? "?" : id);
        e.addElement("status").addText("ERROR");
        e.addElement("error").addText(text == null ? "?" : text);
    }

    @Override
    public String toString() {
        String ret = report.asXML();
        log.info("Process order result is = {}", ret);
        return ret;
    }

    public Document getDocument() {
        return report;
    }

    public void addOrderInfoOK(String id) {
        Element e = reportRoot.addElement("order");
        e.addElement("id").addText(id == null ? "?" : id);
        e.addElement("status").addText("OK");
    }
}

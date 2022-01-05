package lv.bank.cards.soap.requests;

import lombok.Getter;
import lombok.Setter;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ResponseElement {

    protected Element root;
    @Getter
    @Setter
    protected String outTemplateName;
    @Getter
    @Setter
    private boolean maskPan = false;

    public ResponseElement addAttribute(String name, String value) {
        root.addAttribute(name, value);
        return this;
    }

    public ResponseElement(boolean maskPan) {
        this.maskPan = maskPan;
        root = null;
    }

    public ResponseElement(String name, boolean maskPan) {
        this.maskPan = maskPan;
        root = DocumentHelper.createElement(name);
    }

    public ResponseElement createElement(String name) {
        ResponseElement re = new ResponseElement(name, maskPan);
        if (root == null) root = re.getElement();
        else root.add(re.getElement());
        return re;
    }

    public ResponseElement createElement(String name, String text) {
        ResponseElement newElement = createElement(name).addText(maskPan ? maskCardNumber(text) : text);
        return newElement;
    }

    public ResponseElement addText(String text) {
        if (text != null) {
            root.addText(maskPan ? maskCardNumber(text) : text);
        }
        return this;
    }

    public Element getElement() {
        return root;
    }

    public String asXML() {
        return root.asXML();
    }

    public static String maskCardNumber(String text) {
        return text.replaceAll("([^\\d]|^)(\\d{6})\\d{6}(\\d{4})([^\\d]|$)", "$1$2******$3$4");
    }
}

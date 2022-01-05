package lv.bank.cards.soap.requests;

import lombok.Getter;
import lombok.Setter;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.service.RtcuNGTemplatesService;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SubRequest {

    private static final String MASK_PAN_PATTERN = "pan";

    @Getter
    @Setter
    protected DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    // this subrequest will be stored here
    @Getter
    protected Document req = null;

    // Function from /do/@what
    @Getter
    @Setter
    protected String function = null;

    // subrequest id from /do/@doId
    @Getter
    @Setter
    protected String doId = null;

    @Getter
    private boolean maskPan = false;

    private Object getRequestElement(String addr) {
        return getRequestElement(this.req, addr);
    }

    private static Object getRequestElement(Document req, String addr) {
        return DocumentHelper.createXPath(addr).evaluate(req);
    }

    public SubRequest(Element req) throws RequestPreparationException, RequestFormatException {
        this(req, new RtcuNGTemplatesService(getTemplateName(DocumentHelper.createDocument(req.createCopy()))));
    }

    protected SubRequest(Element req, RtcuNGTemplatesService templateManager) throws RequestPreparationException, RequestFormatException {
        // save request
        this.req = DocumentHelper.createDocument(req.createCopy());

        // Need to check for mask parameter before transformation because transformation will remove this parameter
        Object o = getRequestElement("/do/@mask");
        if (o != null && o instanceof Node) {
            maskPan = ((Node) o).getText().contains(MASK_PAN_PATTERN);
        }

        // First af all we should check for template ...
        if (templateManager.isTemplateLoaded()) {
            this.req = templateManager.applyTemplate(this.req);
        }
        // get function name
        o = null;
        o = getRequestElement("/do/@what");
        if (o instanceof Attribute)
            function = ((Node) o).getText();
        else
            throw new RequestFormatException("do without what :(");

        // Get unessential params
        o = getRequestElement("/do/@doId");
        if ((o != null) && (o instanceof Node)) {
            doId = ((Node) o).getText();
        }

        if (!maskPan) { // Check again because transformation can add this parameter
            o = getRequestElement("/do/@mask");
            if (o != null && o instanceof Node) {
                maskPan = ((Node) o).getText().contains(MASK_PAN_PATTERN);
            }
        }

        o = getRequestElement("/do/@dateFormat");
        if ((o != null) && (o instanceof Node)) {
            try {
                dateFormat = new SimpleDateFormat(((Node) o).getText());
            } catch (RuntimeException e) {
                throw new RequestPreparationException(
                        "Error preparing date format :" + e.getMessage(), e);
            }
        }
    }

    private static String getTemplateName(Document req) {
        Object o = getRequestElement(req, "/do/@template");
        if ((o != null) && (o instanceof Node)) {
            return ((Node) o).getText();
        }
        return null;
    }

    public String getOutTemplateName() {
        Object o = getRequestElement("/do/@outTemplate");
        if ((o != null) && (o instanceof Node)) {
            return ((Node) o).getText();
        }
        return null;
    }

}

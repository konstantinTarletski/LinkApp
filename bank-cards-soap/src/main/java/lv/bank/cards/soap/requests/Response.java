package lv.bank.cards.soap.requests;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.service.RtcuNGTemplatesService;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

@Slf4j
public class Response extends ResponseElement {

    protected Document doc = DocumentHelper.createDocument();
    protected RtcuNGTemplatesService templateManager;

    public Response() {
        super(false);
    }

    public void createBatch() {
        root = doc.addElement("done");
        root.addAttribute("format", "batch");
    }

    public void addSubResponse(ResponseElement done) {
        if (root == null) {
            root = done.getElement();
            doc.setRootElement(root);
            this.templateManager = new RtcuNGTemplatesService(done.getOutTemplateName());
        } else {
            if (StringUtils.isBlank(done.getOutTemplateName()))
                root.add(done.getElement());
            else {
                RtcuNGTemplatesService template = new RtcuNGTemplatesService(done.getOutTemplateName());
                Document doneDoc = DocumentHelper.createDocument(done.getElement().createCopy());
                try {
                    root.add(template.applyTemplate(doneDoc));
                } catch (RequestPreparationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String asXML() {
        Document tmp;
        if (this.templateManager != null && this.templateManager.isTemplateLoaded()) {
            try {
                tmp = DocumentHelper.createDocument(this.doc.getRootElement().createCopy());
                return this.templateManager.applyTemplate(tmp).asXML();
            } catch (RequestPreparationException e) {
                log.warn("asXML, error", e);
            }
        }
        return doc.asXML();
    }
}

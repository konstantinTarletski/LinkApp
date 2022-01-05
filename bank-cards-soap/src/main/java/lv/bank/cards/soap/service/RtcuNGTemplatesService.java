package lv.bank.cards.soap.service;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.linkApp.RtcuRequestTemplate;
import lv.bank.cards.core.linkApp.dao.TemplatesDAO;
import lv.bank.cards.core.linkApp.impl.TemplatesDAOHibernate;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import org.dom4j.Document;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

@Slf4j
public class RtcuNGTemplatesService {

    protected TemplatesDAO templatesDAO;
    protected RtcuRequestTemplate template;

    public RtcuNGTemplatesService(String templateName) {
        templatesDAO = new TemplatesDAOHibernate();
        if (templateName != null) {
            template = templatesDAO.findTemplateByName(templateName);
            if (template != null) {
                log.debug("TId = {}", template.getId()); // There is problem with object initializing. Getting ID initialize object and fix problem. Might need to investigate this more
            }
        }
    }

    public Document applyTemplate(Document request) throws RequestPreparationException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            Source a = new StreamSource(new StringReader(template.getXslt()));
            transformer = factory.newTransformer(a);
        } catch (TransformerConfigurationException e) {
            throw new RequestPreparationException("Error during transformer instantiation", e);
        }

        DocumentSource source = new DocumentSource(request);
        DocumentResult result = new DocumentResult();
        try {
            transformer.transform(source, result);
            return result.getDocument();
        } catch (TransformerException e) {
            throw new RequestPreparationException("Error during applying template", e);
        }
    }

    public boolean isTemplateLoaded() {
        return (this.template != null);
    }

    public String getTemplateName() {
        if (this.isTemplateLoaded()) return this.template.getName();
        else return null;
    }

    public String getTemplateXslt() {
        if (this.isTemplateLoaded()) return this.template.getXslt();
        else return null;
    }
}

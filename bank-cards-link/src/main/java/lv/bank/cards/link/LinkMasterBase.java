package lv.bank.cards.link;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.AccountDAO;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.cms.dao.ProductDAO;
import lv.bank.cards.core.cms.impl.AccountDAOHibernate;
import lv.bank.cards.core.cms.impl.CardDAOHibernate;
import lv.bank.cards.core.cms.impl.ClientDAOHibernate;
import lv.bank.cards.core.cms.impl.CommonDAOHibernate;
import lv.bank.cards.core.cms.impl.ProductDAOHibernate;
import lv.bank.cards.core.linkApp.dao.AccountsDAO;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dao.ClientsDAO;
import lv.bank.cards.core.linkApp.impl.AccountsDAOHibernate;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.linkApp.impl.ClientsDAOHibernate;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.utils.XmlUtils;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.soap.ejb.CMSSoapAPIWrapperBean;
import lv.bank.cards.rtcu.util.BankCardsWSWrapperDelegate;
import lv.bank.cards.rtcu.util.BankCardsWSWrapperService;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.hibernate.exception.GenericJDBCException;

import javax.naming.NamingException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;

@Slf4j
public abstract class LinkMasterBase {

    public static final String TRANSFORMER_NAME = "transformer.xsl";

    protected CMSSoapAPIWrapperBean cmsSoapAPIWrapperBean;
    protected CMSCallAPIWrapper cmsCallAPIWrapper;
    protected BankCardsWSWrapperDelegate bankCardsWSWrapperDelegate;
    protected Transformer transformer;
    protected CommonDAO commonDAO;
    protected ProductDAO productDAO;
    protected ClientDAO clientDAO;
    protected ClientsDAO clientsDAO;
    protected CardDAO cardDAO;
    protected CardsDAO cardsDAO;
    protected AccountDAO accountDAO;
    protected AccountsDAO accountsDAO;

    public void init() throws NamingException {
        commonDAO = new CommonDAOHibernate();
        productDAO = new ProductDAOHibernate();
        clientDAO = new ClientDAOHibernate();
        clientsDAO = new ClientsDAOHibernate();
        cardDAO = new CardDAOHibernate();
        cardsDAO = new CardsDAOHibernate();
        accountDAO = new AccountDAOHibernate();
        accountsDAO = new AccountsDAOHibernate();
        try {
            URL url = new URL(LinkAppProperties.getLinkAppHost() + LinkAppProperties.getBankCardsWsWrapperPortWsdl());
            BankCardsWSWrapperService service = new BankCardsWSWrapperService(url);
            this.bankCardsWSWrapperDelegate = service.getBankCardsWSWrapperPort();
        } catch (Exception e) {
            log.error("OrderProcessor, bankCardsWSWrapperDelegate can not be loaded.", e);
        }
        try {
            cmsSoapAPIWrapperBean = new CMSSoapAPIWrapperBean();
        } catch (Exception e) {
            log.error("init, unable to start CMSSoapAPIWrapper", e);
        }
        cmsCallAPIWrapper = new CMSCallAPIWrapper();
    }

    public String processOrders(String orders) {
        XMLReport report = new XMLReport();

        log.info("processOrders, received request with orders = {}", orders);

        if (!isNeedToProcess(orders)) {
            log.info("processOrders, no need to process");
            report.addError(null, "No need to process this order");
            return report.toString();
        }

        if (transformer == null || isTimeToUpdateTransformer()) {
            try {
                transformer = getXMLTransformer(TRANSFORMER_NAME);
            } catch (TransformerConfigurationException e) {
                log.error("processOrders, can not init transformer", e);
                report.addError(null, e.getMessage());
                return report.toString();
            }
        }

        if (transformer == null) {
            log.error("processOrders, can not init transformer");
            report.addError(null, "Can not init transformer");
            return report.toString();
        }

        Document doc;
        try {
            doc = transformXml(transformer, orders);
        } catch (TransformerException | DocumentException e) {
            log.info("processOrders, error while transforming orders = {}", orders, e);
            report.addError(null, e.getMessage());
            return report.toString();
        }
        OrdersHolder ordersHolder = new OrdersHolder(doc);

        while (ordersHolder.hasMoreOrders()) {
            Order thisOrder = ordersHolder.getNextOrder();
            processOrder(thisOrder, report);
        }
        return report.toString();
    }

    protected void processOrder(Order order, XMLReport report) {
        try {
            validateAndProcessOrder(order);
            report.addOrderInfoOK(order.getOrderId());
        } catch (Exception e) {
            log.error("processOrders", e);
            if (e.getCause() instanceof GenericJDBCException && e.getCause().getCause() instanceof SQLException) {
                String exception = e.getCause().getCause().getMessage();
                exception = StringUtils.substringAfter(StringUtils.substringAfter(exception, "Error cause: "), "] ");
                exception = StringUtils.substringBefore(exception, "\n");
                report.addError(order.getOrderId(), exception);
            } else {
                report.addError(order.getOrderId(), e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage());
            }
        }
    }

    protected Document transformXml(Transformer transformer, String xml) throws TransformerException, DocumentException {
        StringReader reader = new StringReader(xml);
        StringWriter writer = new StringWriter();
        StreamSource source = new StreamSource(reader);
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String transformedOrder = writer.toString();
        log.info("transformXml, formatted platon order after transformation = {}", XmlUtils.getFormattedXml(transformedOrder));
        return DocumentHelper.parseText(transformedOrder);
    }

    protected boolean isTimeToUpdateTransformer() {
        Calendar now = Calendar.getInstance();
        return LinkAppProperties.TRANSFORMER_INIT_DATE.get() == null ||
                LinkAppProperties.TRANSFORMER_INIT_DATE.get().get(Calendar.DATE) != now.get(Calendar.DATE);
    }

    protected Transformer getXMLTransformer(String filename) throws TransformerConfigurationException {

        Transformer transformer = null;

        String confPath = System.getProperty("jboss.server.config.dir") + File.separator + filename;
        File file = new File(confPath);
        log.info("Reading transformer file = {}", confPath);

        if (file.exists()) {
            try (InputStream str = new FileInputStream(file)) {
                TransformerFactory factory = TransformerFactory.newInstance();
                transformer = factory.newTransformer(new StreamSource(str));
                LinkAppProperties.TRANSFORMER_INIT_DATE.set(Calendar.getInstance());
            } catch (IOException e) {
                log.error("Could not read {} file in configuration folder", filename);
            }
        } else {
            log.error("Did not find {} file in configuration folder", filename);
        }
        return transformer;
    }

    public abstract boolean isNeedToProcess(String orders);

    public abstract void validateAndProcessOrder(Order order) throws Exception;

}

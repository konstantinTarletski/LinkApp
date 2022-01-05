package lv.bank.cards.link.lv;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.link.LinkMasterBase;
import lv.bank.cards.link.Order;
import lv.bank.cards.rtcu.util.LinkMaster;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jboss.ws.api.annotation.WebContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Slf4j
@Stateless
@NoArgsConstructor
@WebService(portName = "LinkMasterBeanPort", name = "BankCardsLink")
@WebContext(contextRoot = "BankCardsLink", urlPattern = "/LinkMasterBean")
@HandlerChain(file = "/META-INF/handlers.xml")
public class LinkMasterBean extends LinkMasterBase implements LinkMaster {

    protected OrderProcessor orderProcessor;
    protected OrderValidator orderValidator;
    protected Mapper mapper;
    protected LinkMaster self;
    @Resource
    protected EJBContext ejbContext;

    @PostConstruct
    public void init() throws NamingException {
        super.init();
        mapper = new Mapper(commonDAO, cardDAO, cardsDAO, clientDAO, clientsDAO, productDAO, accountsDAO);
        orderValidator = new OrderValidator(commonDAO, cardDAO, cardsDAO, clientDAO, mapper);
        orderProcessor = new OrderProcessor(cmsSoapAPIWrapperBean, cmsCallAPIWrapper, bankCardsWSWrapperDelegate,
                mapper, commonDAO, cardDAO, cardsDAO, accountDAO, clientDAO, clientsDAO);
        InitialContext ctx = new InitialContext();
        self = (LinkMaster) ctx.lookup(LinkMaster.JNDI_NAME);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public String processOrders(String orders) {
        return super.processOrders(orders);
    }

    @Override
    public void validateAndProcessOrder(Order order) throws Exception {
        self.processOrder(order);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void processOrder(Order order) throws Exception {
        try {
            orderValidator.validateOrder(order);
            orderProcessor.processOrder(order);
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw e;
        }
    }

    @Override
    public boolean isNeedToProcess(String orders) {
        try {
            Document doc = DocumentHelper.parseText(orders);
            if (doc != null) {
                String name = doc.getRootElement().getName();
                if ("DOCUMENT".equals(name)) {
                    return true;
                }
            }
        } catch (DocumentException e) {
            log.error("processOrders, can not process order", e);
        }
        return false;
    }

}

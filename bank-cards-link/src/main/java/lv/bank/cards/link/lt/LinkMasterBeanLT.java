package lv.bank.cards.link.lt;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.link.LinkMasterBase;
import lv.bank.cards.link.Order;
import lv.bank.cards.rtcu.util.LinkMasterLT;
import org.jboss.ws.api.annotation.WebContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Slf4j
@Stateless
@NoArgsConstructor
@WebService(portName = "LinkMasterBeanPort", name = "BankCardsLink")
//Need to be renamed to '/BankCardsLinkWS/BankCardsLinkWSWrapperPort', done in 'standalone.xml'
@WebContext(contextRoot = "BankCardsLink", urlPattern = "/LinkMasterBeanLT")
@HandlerChain(file = "/META-INF/handlers.xml")
public class LinkMasterBeanLT extends LinkMasterBase implements LinkMasterLT {

    protected OrderProcessor orderProcessor;
    protected OrderValidator orderValidator;
    protected Mapper mapper;
    protected LinkMasterLT self;
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
        self = (LinkMasterLT) ctx.lookup(LinkMasterLT.JNDI_NAME);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @WebMethod(operationName = "ProcessOrders", action = "ProcessOrders")
    public String processOrders(String applications) {
        return super.processOrders(applications);
    }

    @Override
    public void validateAndProcessOrder(Order order) throws Exception {
        self.processOrder(order);
    }

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
        return true;
    }

    @Override
    public void runHouseKeeper() {
        throw new UnsupportedOperationException("Not supported anymore.");
    }

    @Override
    public void cleanupPcdFileStorage(String arg0) {
        throw new UnsupportedOperationException("Not supported anymore.");
    }

}

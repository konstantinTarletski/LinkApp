/* $Id: RTCUBean.java 5 2006-08-16 12:57:50Z ays $
 * Created on 2005.18.3
 */
package lv.nordlb.cards.transmaster.ejb;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.XmlUtils;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.exceptions.RequestProcessingSoftException;
import lv.bank.cards.soap.exceptions.ResponseHoldingException;
import lv.bank.cards.soap.handlers.QueryHandler;
import lv.bank.cards.soap.requests.Request;
import lv.bank.cards.soap.requests.Response;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.nordlb.cards.transmaster.interfaces.RTCU;
import lv.nordlb.cards.transmaster.requests.handlers.HandlerManager;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

import static lv.nordlb.cards.transmaster.requests.handlers.HandlerManager.INFORMATIVE_SERVICE_WITHOUT_ROLLBACK;

@Stateless
@WebService(serviceName = "BankCardsWSWrapperService", portName = "BankCardsWSWrapperPort", targetNamespace = "http://util.rtcu.cards.bank.lv/", wsdlLocation = "/META-INF/wsdl/BankCardsWSWrapperService.wsdl")
@WebContext(contextRoot = "BankCardsWS", urlPattern = "/BankCardsWSWrapperPort")
@HandlerChain(file = "/META-INF/handlers.xml")
@Slf4j
public class RTCUBean implements RTCU {

    private HandlerManager handlerMngr = null;
    private SubRequestHandler queryCallHandler = null;

    void setHandlerMngr(HandlerManager handlerMngr) {
        this.handlerMngr = handlerMngr;
    }

    void setQueryCallHandler(SubRequestHandler queryCallHandler) {
        this.queryCallHandler = queryCallHandler;
    }

    public RTCUBean() {
        handlerMngr = new HandlerManager();
        queryCallHandler = new QueryHandler();
    }

    // If we're calling this method from another method (for example, blocking a card before replacement),
    // we need a new transaction, because otherwise the session that was started by the caller method will be closed.
    // To ensure that a new transaction is created with each call of RTCUNGCall, the "REQUIRES_NEW"
    // attribute is being applied to this method.

    @WebMethod(operationName = "RTCUNGCall", action = "RTCUNGCall")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String RTCUNGCall(String cmd) throws DataIntegrityException {
        Document doc;
        long timeMs = System.currentTimeMillis();
        log.info("-> [" + cmd + "]");
        Request request = new Request();
        Response response = new Response();
        try {
            try {
                log.debug("Will parse input doc : " + cmd);
                doc = DocumentHelper.parseText(cmd);
            } catch (DocumentException e) {
                throw new RequestFormatException("Error in input XML format");
            }

            {
                String format = XmlUtils.getAttributeValue(doc, "/do/@format");
                // If we're in batch mode preparing request separately
                if ((format != null) && (format.equals("batch"))) {
                    response.createBatch();

                    XPath xpathSelector = DocumentHelper.createXPath("/do/do");
                    for (Object o : xpathSelector.selectNodes(doc)) {
                        request.addSubRequest(new SubRequest((Element) o));
                    }
                }
                // And if we're in single request mode, we creating simple request
                else {
                    request.addSubRequest(new SubRequest(doc.getRootElement()));
                }
            }
            // Trying to satisfy all dependencies

            for (SubRequest subR : request.getSubRequests())
                handlerMngr.prepareHandlerForFunction(subR.getFunction());

            // Processing all subrequests from request
            for (SubRequest subR : request.getSubRequests()) {
                if (INFORMATIVE_SERVICE_WITHOUT_ROLLBACK.contains(subR.getFunction())) {
                    try {
                        response.addSubResponse(handlerMngr.handle(subR));
                    } catch (ResponseHoldingException e) {
                        response.addSubResponse(e.compileResponseElement());
                    }
                } else {
                    response.addSubResponse(handlerMngr.handle(subR));
                }
            }
            // clearing handler manager
            handlerMngr.collectGarbage(60);
            // commiting changes
//			depHelper.commit();

        } catch (RequestProcessingSoftException e) { // Not an Exception but much softer way to say to app that we need to stop&rollback
            response.addSubResponse(e.compileResponseElement());
        } catch (RequestFormatException e) {
            response.addSubResponse(e.compileResponseElement());
            log.error("RequestFormatException", e);
        } catch (RequestPreparationException e) {
            response.addSubResponse(e.compileResponseElement());
            log.error("RequestPreparationException", e);
        } catch (RequestProcessingException e) {
            response.addSubResponse(e.compileResponseElement());
            log.error("RequestProcessingException", e);
        }
        String responseToReturn = response.asXML();
        log.info("<- [" + responseToReturn + "] in ms " + (System.currentTimeMillis() - timeMs));
        return responseToReturn;
    }

    @WebMethod(operationName = "QueryCall", action = "QueryCall")
    public String QueryCall(String cmd) throws DataIntegrityException {
        Document doc;
        log.info("-> [" + cmd + "]");

        Request request = new Request();
        Response response = new Response();
        try {
            try {
                log.debug("Will parse input doc : " + cmd);
                doc = DocumentHelper.parseText(cmd);
            } catch (DocumentException e) {
                throw new RequestFormatException("Error in input XML format");
            }

            {
                String format = XmlUtils.getAttributeValue(doc, "/do/@format");
                // If we're in batch mode preparing request separately
                if ((format != null) && (format.equals("batch"))) {
                    response.createBatch();

                    XPath xpathSelector = DocumentHelper.createXPath("/do/do");
                    for (Object o : xpathSelector.selectNodes(doc)) {
                        request.addSubRequest(new SubRequest((Element) o));
                    }
                }
                // And if we're in single request mode, we creating simple request
                else {
                    request.addSubRequest(new SubRequest(doc.getRootElement()));
                }
            }

            // Processing all subrequests from request
            for (SubRequest subR : request.getSubRequests()) {
                if (log.isDebugEnabled()) log.debug("handling request " + subR.getReq().asXML());
                queryCallHandler.handle(subR);
                response.addSubResponse(queryCallHandler.compileResponse());
            }

        } catch (RequestFormatException e) {
            response.addSubResponse(e.compileResponseElement());
            log.error("RequestFormatException", e);
        } catch (RequestPreparationException e) {
            response.addSubResponse(e.compileResponseElement());
            log.error("RequestPreparationException", e);
        } catch (RequestProcessingException e) {
            response.addSubResponse(e.compileResponseElement());
            log.error("RequestProcessingException", e);
        }
        request = null;
        String responseToReturn = response.asXML();
        log.info("<- [" + responseToReturn + "]");
        return responseToReturn;
    }
}

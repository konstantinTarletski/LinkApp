package lv.bank.cards.rtcu.util;

import lv.bank.cards.link.Order;

import javax.ejb.Local;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@Local
@WebService
public interface LinkMasterLT {

    String JNDI_NAME = "java:app/bankCardsLink/LinkMasterBeanLT!lv.bank.cards.rtcu.util.LinkMasterLT";

    @WebMethod(operationName = "RunHouseKeeper")
    void runHouseKeeper();

    @WebMethod
    void cleanupPcdFileStorage(@WebParam(name = "arg0", partName = "arg0") String arg0);

    @WebMethod(operationName = "ProcessOrders")
    @WebResult(partName = "return")
    String processOrders(@WebParam(name = "arg0", partName = "arg0") String arg0) throws Exception;

    void processOrder(Order thisOrder) throws Exception;
}

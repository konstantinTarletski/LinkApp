package lv.bank.cards.rtcu.util;

import lv.bank.cards.link.Order;

import javax.ejb.Local;
import javax.jws.WebService;

@WebService
@Local
public interface LinkMaster {

    String COMP_NAME = "java:comp/env/ejb/LinkMaster";
    String JNDI_NAME = "java:app/bankCardsLink/LinkMasterBean!lv.bank.cards.rtcu.util.LinkMaster";

    String processOrders(String orders);

    void processOrder(Order thisOrder) throws Exception;

}

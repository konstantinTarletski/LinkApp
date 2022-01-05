package lv.bank.cards.soap.handlers;

import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.entity.cms.IzdStopCause;
import lv.bank.cards.core.entity.cms.IzdStopCausePK;
import lv.bank.cards.core.entity.cms.IzdStoplist;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class to test Card status in BO information handler
 *
 * @author saldabols
 */
public class CardStatusBOTest extends JUnitTestBase {

    protected CardStatusBO handler;
    protected CardDAO cardDAO = mock(CardDAO.class);

    @Before
    public void setUpTest() throws RequestPreparationException {
        handler = new CardStatusBO();
        handler.cardsDAO = cardsDAO;
        handler.cardDAO = cardDAO;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, DataIntegrityException {
        SubRequest request = getSubrequest("card-status-bo");

        // There is no card number
        checkRequestFormatException(handler, request, "Please provide valid card number");

        addElementOnRootElement(request, "card", "4775733282237579");

        // Cannot find card in LinkApp DB
        checkRequestProcessingException(handler, request, "Card with given number couldn't be found");

        PcdCard card = new PcdCard();
        card.setStatus1("1");
        card.setStatus2("0");
        when(cardsDAO.findByCardNumber("4775733282237579")).thenReturn(card);
        IzdStoplist stopList = new IzdStoplist();
        stopList.setIzdStopCause(new IzdStopCause());
        stopList.getIzdStopCause().setStatusCode("11");
        stopList.getIzdStopCause().setComp_id(new IzdStopCausePK("Stolen", "23"));
        stopList.setText("It is blocked");
        when(cardDAO.getObject(IzdStoplist.class, "4775733282237579")).thenReturn(stopList);

        handler.handle(request);

        Element info = handler.compileResponse().getElement().element("card-status-bo");
        assertEquals("4775733282237579", info.element("card").getTextTrim());
        assertEquals("1", info.element("card-status-1").getTextTrim());
        assertEquals("0", info.element("card-status-2").getTextTrim());
        assertEquals("11", info.element("stoplist").element("answer-code").getTextTrim());
        assertEquals("Stolen", info.element("stoplist").element("cause").getTextTrim());
        assertEquals("It is blocked", info.element("stoplist").element("description").getTextTrim());
    }

}

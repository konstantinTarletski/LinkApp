package lv.bank.cards.soap.handlers;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.rtps.AnswerCode;
import lv.bank.cards.core.entity.rtps.CardsException;
import lv.bank.cards.core.entity.rtps.StipCard;
import lv.bank.cards.core.entity.rtps.StipStoplist;
import lv.bank.cards.core.rtps.dao.CardDAO;
import lv.bank.cards.core.rtps.dao.CardsExceptionsDAO;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.service.CardService;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardStatusTest extends JUnitTestBase {

    protected CardStatus handler;
    protected CardsExceptionsDAO cardsExceptionsDAO = mock(CardsExceptionsDAO.class);
    protected CardDAO cardDAO = mock(CardDAO.class);
    protected CardService helper = mock(CardService.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        handler = new CardStatus();
        handler.cardDAO = cardDAO;
        handler.cardsExceptionsDAO = cardsExceptionsDAO;
        handler.helper = helper;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException,
            DataIntegrityException {
        SubRequest request = getSubrequest("card-status");

        // There is no card number
        checkRequestFormatException(handler, request, "Please provide valid card number");

        addElementOnRootElement(request, "card", "4775733282237579");
        PcdCard card = new PcdCard();
        card.setCard("4775733282237579");
        card.setBankC("23");
        card.setGroupc("50");
        when(helper.getPcdCard("4775733282237579")).thenReturn(card);

        // Cannot find stip card
        checkRequestProcessingException(handler, request, "Card with given number couldn't be found");

        StipCard stipCard = new StipCard();
        stipCard.setAnswerCodeByStatCode1(new AnswerCode());
        stipCard.getAnswerCodeByStatCode1().setActionCode("aCode");
        StipStoplist list = new StipStoplist();
        list.setAnswerCode(new AnswerCode());
        list.getAnswerCode().setActionCode("aCode2");
        list.setDescription("desc2");
        stipCard.setStipStoplist(new HashSet<>(Arrays.asList(list)));

        CardsException exception = new CardsException();
        exception.setAnswerCode(new AnswerCode());
        exception.getAnswerCode().setActionCode("aCode3");
        exception.setDescription("desc3");

        when(cardDAO.load("4775733282237579", "42800202350")).thenReturn(stipCard);
        when(cardsExceptionsDAO.findByCardNumber("4775733282237579")).thenReturn(exception);
        when(helper.getCentreIdByCard("4775733282237579")).thenReturn(CardUtils.composeCentreIdFromPcdCard(card));

        handler.handle(request);

        Element info = handler.compileResponse().getElement().element("card-status");
        assertEquals("4775733282237579", info.element("card").getText());
        assertEquals("aCode", info.element("stip-card-status").getText());
        assertEquals("aCode2", info.element("stip-stoplist").element("answer-code").getText());
        assertEquals("desc2", info.element("stip-stoplist").element("description").getText());
        assertEquals("aCode3", info.element("card-exception").element("answer-code").getText());
        assertEquals("desc3", info.element("card-exception").element("description").getText());
    }

}

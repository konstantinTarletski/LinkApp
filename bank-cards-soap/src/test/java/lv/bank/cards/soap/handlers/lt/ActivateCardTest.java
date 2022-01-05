package lv.bank.cards.soap.handlers.lt;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.service.CardService;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test Remove card from RMS stoplist handler
 *
 * @author saldabols
 */
public class ActivateCardTest extends JUnitTestBase {

    protected CardService helper = mock(CardService.class);
    protected ActivateCard handler;

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        handler = spy(new ActivateCard());
        handler.mainWrapper = rtpsCallApiWraper;
        handler.helper = helper;
        handler.cardsDAO = cardsDAO;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, DataIntegrityException, RTPSCallAPIException {
        SubRequest request = getSubrequest("activate-card");

        // There is no card number
        checkRequestFormatException(handler, request, "Please provide valid card number");

        addElementOnRootElement(request, "card", "4775733282237579");

        // Cannot get centre id
        checkRequestFormatException(handler, request, "Card with given number couldn't be found");

        PcdCard card = new PcdCard();
        card.setCard("4775733282237579");
        card.setExpiry1(new Date());
        when(cardsDAO.findByCardNumber("4775733282237579")).thenReturn(card);
        addElementOnRootElement(request, "centre-id", "42800202350");
        addElementOnRootElement(request, "priority", "1");
        addElementOnRootElement(request, "rule-expr", "rule");

        // Wrong priority
        checkRequestProcessingException(handler, request, "Incorrect priority value");

        request = getSubrequest("activate-card");
        addAttributeOnRootElement(request, "operator", "User");
        addElementOnRootElement(request, "card", "4775733282237579");
        addElementOnRootElement(request, "centre-id", "42800202350");
        addElementOnRootElement(request, "priority", "-1");
        addElementOnRootElement(request, "rule-expr", "rule");
        addElementOnRootElement(request, "action-code", "aCode");
        addElementOnRootElement(request, "description", "desc");
        addAttributeOnRootElement(request, "template", "deliveryTimeBlock");

        handler.handle(request);

        assertEquals("4775733282237579", handler.compileResponse().getElement().element("activate-card").element("activated").getText());
        assertEquals("6", card.getDeliveryBlock());
        assertEquals("User", card.getIssuedBy());

        verify(rtpsCallApiWraper).RemoveCardFromRMSStop("42800202350", "4775733282237579", "rule", -1, "aCode", "desc");
        verify(cardsDAO).saveOrUpdate(card);
    }
}

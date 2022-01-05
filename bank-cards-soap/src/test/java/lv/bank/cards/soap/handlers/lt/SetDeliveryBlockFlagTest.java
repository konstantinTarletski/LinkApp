package lv.bank.cards.soap.handlers.lt;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test GetCardsByPersonalCode handler
 *
 * @author saldabols
 */
public class SetDeliveryBlockFlagTest extends JUnitTestBase {

    private SetDeliveryBlockFlag handler;

    @Before
    public void setUpTest() throws RequestPreparationException {
        handler = new SetDeliveryBlockFlag();
        handler.cardsDAO = cardsDAO;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        // card and tracking
        SubRequest request = getSubrequest("set-delivery-block-flag");
        checkRequestFormatException(handler, request, "Card or tracking number must be specified");

        addElementOnRootElement(request, "card", "4775733282237579");

        // Wrong delivery block value
        checkRequestFormatException(handler, request, "delivery-block tag must contain 5, 6, 8 or auto");
        addElementOnRootElement(request, "delivery-block", "x");
        checkRequestFormatException(handler, request, "delivery-block tag must contain 5, 6, 8 or auto");

        // Cannot find card
        request = getSubrequest("set-delivery-block-flag");
        addElementOnRootElement(request, "card", "4775733282237579");
        addElementOnRootElement(request, "delivery-block", "auto");
        checkRequestFormatException(handler, request, ErrorConstants.cantFindCard);

        PcdCard card = new PcdCard();
        card.setCard("4775733282237579");
        card.setUCod10(Constants.DELIVERY_BRANCH_LT_080);
        when(cardsDAO.findByCardNumber("4775733282237579")).thenReturn(card);

        handler.handle(request);

        assertEquals("<done><set-delivery-block-flag><card>4775733282237579</card>"
                        + "<delivery-block>8</delivery-block></set-delivery-block-flag></done>",
                handler.compileResponse().asXML());

        assertEquals("8", card.getDeliveryBlock());
        verify(cardsDAO).saveOrUpdate(card);

        // Did not change because block was already set
        card.setDeliveryBlock("x");
        handler.handle(request);
        assertEquals("x", card.getDeliveryBlock());

    }

    @Test
    public void handle_tracking() throws RequestPreparationException, RequestFormatException, RequestProcessingException, DataIntegrityException {
        // card and tracking
        SubRequest request = getSubrequest("set-delivery-block-flag");
        checkRequestFormatException(handler, request, "Card or tracking number must be specified");

        addElementOnRootElement(request, "tracking-no", "4775733");
        addElementOnRootElement(request, "delivery-block", "6");

        // Cannot find card
        checkRequestFormatException(handler, request, ErrorConstants.cantFindCard);

        PcdCard card = new PcdCard();
        card.setCard("4775733282237579");
        when(cardsDAO.getCardByTrackingNoLT("4775733")).thenReturn(card);

        handler.handle(request);

        assertEquals("<done><set-delivery-block-flag><card>4775733282237579</card>"
                        + "<delivery-block>6</delivery-block></set-delivery-block-flag></done>",
                handler.compileResponse().asXML());

        assertEquals("6", card.getDeliveryBlock());
        verify(cardsDAO).saveOrUpdate(card);

    }
}

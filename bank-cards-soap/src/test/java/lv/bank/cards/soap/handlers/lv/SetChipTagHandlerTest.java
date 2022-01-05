package lv.bank.cards.soap.handlers.lv;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SetChipTagHandlerTest extends JUnitTestBase {

    private SetChipTagHandler handler;
    private CMSCallAPIWrapper wraper = mock(CMSCallAPIWrapper.class);

    @Before
    public void setUpTest() throws RequestPreparationException {
        handler = spy(new SetChipTagHandler());
        handler.wrap = wraper;
        handler.cardsDAO = cardsDAO;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, CMSCallAPIException {
        SubRequest request = getSubrequest("set-chip-tag-value");

        checkRequestFormatException(handler, request, "Missing card tag");

        addElementOnRootElement(request, "card", "4775733282237579");
        checkRequestFormatException(handler, request, "Missing chip tag");

        addElementOnRootElement(request, "chip-tag", "DF01");
        checkRequestFormatException(handler, request, "Missing value tag");

        addElementOnRootElement(request, "value", "0000");
        checkRequestFormatException(handler, request, "Cannot find card 4775733282237579");

        PcdCard card = new PcdCard();
        card.setCard("4775733282237579");

        when(cardsDAO.findByCardNumber("4775733282237579")).thenReturn(card);

        handler.handle(request);

        verify(wraper).setChipTagValue("4775733282237579", "DF01", "0000");

        assertEquals("<done><set-chip-tag-value>done</set-chip-tag-value></done>", handler.compileResponse().asXML());
    }

}

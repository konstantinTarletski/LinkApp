package lv.nordlb.cards.transmaster.requests.handlers.rtps;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.rtps.StipCard;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.nordlb.cards.transmaster.fo.interfaces.StipCardManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.JUnitHandlerTestBase;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RemoveCardFromStoplistTest extends JUnitHandlerTestBase {

    protected RemoveCardFromStoplist handler;
    protected StipCardManager stipCardManager = mock(StipCardManager.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        when(context.lookup(StipCardManager.JNDI_NAME)).thenReturn(stipCardManager);
        when(context.lookup(CMSSoapAPIWrapper.JNDI_NAME)).thenReturn(cmsSoapAPIWrapper);
        handler = spy(new RemoveCardFromStoplist());
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, DataIntegrityException, RTPSCallAPIException, CMSCallAPIException, CMSSoapAPIException {
        SubRequest request = getSubrequest("remove-card-from-stoplist");

        // There is no card number
        checkRequestFormatException(handler, request, "Please provide valid card number");

        addElementOnRootElement(request, "card", "4775733282237579");

        // Cannot find card
        checkRequestFormatException(handler, request, "Card with given number couldn't be found");

        addElementOnRootElement(request, "description", "desc");
        PcdCard card = new PcdCard();
        card.setCard("4775733282237579");
        card.setBankC("23");
        card.setGroupc("50");
        when(pcdabaNGManager.getCardByCardNumber("4775733282237579")).thenReturn(card);

        handler.handle(request);

        assertEquals("4775733282237579", handler.compileResponse().getElement().element("remove-card-from-stoplist").element("removed").getText());
        assertEquals("0", card.getStatus1());
        assertEquals("0", card.getStatus2());

        verify(pcdabaNGManager).saveOrUpdate(card);
        verify(cmsSoapAPIWrapper).removeCardFromStop("4775733282237579", "desc");
    }

    @Test
    public void handle_stip() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, RTPSCallAPIException, CMSSoapAPIException {
        SubRequest request = getSubrequest("remove-card-from-stoplist");
        addElementOnRootElement(request, "card", "4775733282237579");
        addElementOnRootElement(request, "description", "desc");
        PcdCard card = new PcdCard();
        card.setCard("4775733282237579");
        card.setBankC("23");
        card.setGroupc("50");
        when(pcdabaNGManager.getCardByCardNumber("4775733282237579")).thenReturn(card);
        when(stipCardManager.loadStipCard("4775733282237579", "42800202350")).thenReturn(new StipCard());

        handler.handle(request);

        assertEquals("4775733282237579", handler.compileResponse().getElement().element("remove-card-from-stoplist").element("removed").getText());
        assertEquals("0", card.getStatus1());
        assertEquals("0", card.getStatus2());

        verify(pcdabaNGManager).saveOrUpdate(card);
        verify(cmsSoapAPIWrapper).removeCardFromStop("4775733282237579", "desc");
    }
}

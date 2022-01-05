package lv.bank.cards.soap.handlers;

import lombok.SneakyThrows;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdStopCause;
import lv.bank.cards.core.entity.linkApp.PcdStopCausePK;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.service.CardService;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddCardToStoplistTest extends JUnitTestBase {

    protected AddCardToStoplist handler;
    protected final CMSSoapAPIWrapper cmsSoapAPIWrapperBean = mock(CMSSoapAPIWrapper.class);
    protected CardDAO cardDAO = mock(CardDAO.class);
    protected CardService helper = mock(CardService.class);

    public static final String CARD = "4775733282237579";

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        when(context.lookup(CMSSoapAPIWrapper.JNDI_NAME)).thenReturn(cmsSoapAPIWrapperBean);
        handler = spy(new AddCardToStoplist());
        handler.cardDAO = this.cardDAO;
        handler.cardHelper = helper;
    }

    @Test
    @SneakyThrows
    public void handleStandardWithActionCode() {
        SubRequest request = getSubrequest("add-card-to-stoplist");

        // TEST 1 There is no card number
        checkRequestFormatException(handler, request, "Please provide valid card number");

        addElementOnRootElement(request, "card", "4775733282237579");

        // TEST 2 Cannot find card
        checkRequestFormatException(handler, request, "Card with given number couldn't be found");

        addElementOnRootElement(request, "action-code", "24");
        addElementOnRootElement(request, "description", "desc\rdesc");

        LocalDate expired = LocalDate.now().plusYears(1);

        PcdCard card = new PcdCard();
        card.setStatus1("1");
        card.setCard(CARD);
        card.setBankC("23");
        card.setGroupc("50");
        card.setExpiry1(Date.from(expired.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        IzdCard izdCard = new IzdCard();
        izdCard.setCard(CARD);

        PcdStopCause cause = new PcdStopCause();
        cause.setComp_id(new PcdStopCausePK("cause", "23"));

        when(helper.getPcdCard(CARD)).thenReturn(card);
        when(helper.getActionCodeByCauseBankC("24", "23")).thenReturn("cause");
        when(cardDAO.getObject(IzdCard.class, CARD)).thenReturn(izdCard);
        when(helper.getCauseByActionCodeBankC("24", "23")).thenReturn("cause");
        // TEST 3
        handler.handle(request);

        assertEquals(CARD, handler.compileResponse().getElement().element("add-card-to-stoplist").element("added").getText());
        assertEquals("2", card.getStatus1());
        assertEquals("2", card.getStatus2());

        verify(helper).saveOrUpdate(card);
        verify(cmsSoapAPIWrapperBean).addCardToStop(CARD, "cause", "desc desc");
    }

    @Test
    @SneakyThrows
    public void handleExpiredWithActionCode() {
        SubRequest request = getSubrequest("add-card-to-stoplist");

        addElementOnRootElement(request, "card", "4775733282237579");
        addElementOnRootElement(request, "action-code", "24");
        addElementOnRootElement(request, "description", "desc\rdesc");

        LocalDate expired = LocalDate.now().minusYears(1);

        PcdCard card = new PcdCard();
        card.setStatus1("1");
        card.setCard(CARD);
        card.setBankC("23");
        card.setGroupc("50");
        card.setExpiry1(Date.from(expired.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        IzdCard izdCard = new IzdCard();
        izdCard.setCard(CARD);

        PcdStopCause cause = new PcdStopCause();
        cause.setComp_id(new PcdStopCausePK("cause", "23"));

        when(helper.getPcdCard(CARD)).thenReturn(card);
        when(helper.getActionCodeByCauseBankC("24", "23")).thenReturn("cause");
        when(cardDAO.getObject(IzdCard.class, CARD)).thenReturn(izdCard);

        handler.handle(request);

        assertEquals(CARD, handler.compileResponse().getElement().element("add-card-to-stoplist").element("added").getText());
        assertEquals("2", card.getStatus1());
        assertEquals("2", card.getStatus2());

        verify(helper).saveOrUpdate(card);
        verify(cardDAO).saveOrUpdate(izdCard);
        verify(cmsSoapAPIWrapperBean, never()).addCardToStop(any(), any(), any());
    }

    @Test
    @SneakyThrows
    public void handleStandardWithoutActionCode() {
        SubRequest request = getSubrequest("add-card-to-stoplist");

        addElementOnRootElement(request, "card", CARD);
        addElementOnRootElement(request, "cause", "cause");
        addElementOnRootElement(request, "description", "desc\rdesc");

        LocalDate expired = LocalDate.now().plusYears(1);

        PcdCard card = new PcdCard();
        card.setStatus1("0");
        card.setCard(CARD);
        card.setBankC("23");
        card.setGroupc("50");
        card.setExpiry1(Date.from(expired.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        PcdStopCause cause = new PcdStopCause();
        cause.setComp_id(new PcdStopCausePK("cause", "23"));
        cause.setStatusCode("1");
        when(helper.getPcdCard(CARD)).thenReturn(card);
        when(helper.getActionCodeByCauseBankC("cause", "23")).thenReturn("1");

        handler.handle(request);

        assertEquals(CARD, handler.compileResponse().getElement().element("add-card-to-stoplist").element("added").getText());
        assertEquals("1", card.getStatus1());
        assertEquals("1", card.getStatus2());

        verify(helper).saveOrUpdate(card);
        verify(cmsSoapAPIWrapperBean).addCardToStop(CARD, "cause", "desc desc");
    }

    @Test
    @SneakyThrows
    public void handleStandardWithoutActionCodeAndRemoveCardFromStop() {
        SubRequest request = getSubrequest("add-card-to-stoplist");

        addElementOnRootElement(request, "card", CARD);
        addElementOnRootElement(request, "cause", "cause");
        addElementOnRootElement(request, "description", "desc\rdesc");

        LocalDate expired = LocalDate.now().plusYears(1);

        PcdCard card = new PcdCard();
        card.setStatus1("1");
        card.setCard(CARD);
        card.setBankC("23");
        card.setGroupc("50");
        card.setExpiry1(Date.from(expired.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        PcdStopCause cause = new PcdStopCause();
        cause.setComp_id(new PcdStopCausePK("cause", "23"));
        cause.setStatusCode("2");

        when(helper.getPcdCard(CARD)).thenReturn(card);
        when(helper.getCauseByActionCodeBankC("cause", "23")).thenReturn("cause");
        when(helper.getActionCodeByCauseBankC("cause", "23")).thenReturn("2");

        handler.handle(request);

        assertEquals(CARD, handler.compileResponse().getElement().element("add-card-to-stoplist").element("added").getText());
        assertEquals("2", card.getStatus1());
        assertEquals("2", card.getStatus2());

        verify(helper).saveOrUpdate(card);
        verify(cmsSoapAPIWrapperBean).addCardToStop(CARD, "cause", "desc desc");
        verify(cmsSoapAPIWrapperBean).removeCardFromStop(CARD, "Unblocking to apply hard block");
    }

}

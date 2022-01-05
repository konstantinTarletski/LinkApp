package lv.bank.cards.soap.service;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdStopCause;
import lv.bank.cards.core.entity.linkApp.PcdStopCausePK;
import lv.bank.cards.core.linkApp.PcdabaNGConstants;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.requests.MockInitialContextRule;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test Card helper methods
 *
 * @author saldabols
 */
public class CardServiceTest {

    protected final Context context = mock(Context.class);
    protected final SessionFactory sessionFactory = mock(SessionFactory.class);
    protected final CardsDAO cardsDAO = mock(CardsDAO.class);
    protected CardService helper;

    @Rule
    public MockInitialContextRule mockInitialContextRule = new MockInitialContextRule(context);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        when(context.lookup(PcdabaNGConstants.HibernateSessionFactory)).thenReturn(sessionFactory);
        helper = new CardService();
        helper.cardsDAO = cardsDAO;
    }

    @Test
    public void cardCouldBeValid() {
        assertTrue(CardUtils.cardCouldBeValid("212312423121"));
        assertFalse(CardUtils.cardCouldBeValid("123"));
        assertFalse(CardUtils.cardCouldBeValid(null));
    }

    @Test
    public void getPcdCard() {
        helper.getPcdCard("4775733282237579");
        verify(cardsDAO).findByCardNumber("4775733282237579");
    }

    @Test
    public void getCentreIdByCard() {
        PcdCard card = new PcdCard();
        card.setBankC("23");
        card.setGroupc("50");
        when(cardsDAO.findByCardNumber("4775733282237579")).thenReturn(card);

        assertEquals("42800202350", helper.getCentreIdByCard("4775733282237579"));
        assertNull(helper.getCentreIdByCard("5775733282237579"));
        assertNull(helper.getCentreIdByCard(null));
    }

    @Test
    public void composeCentreIdFromPcdCard() {
        PcdCard card = new PcdCard();
        card.setBankC("23");
        card.setGroupc("50");
        assertEquals("42800202350", CardUtils.composeCentreIdFromPcdCard(card));
        assertNull(CardUtils.composeCentreIdFromPcdCard(null));
    }

    @Test
    public void getCauseByActionCodeBankC() throws DataIntegrityException {
        PcdStopCause cause = new PcdStopCause();
        cause.setComp_id(new PcdStopCausePK("cause", "23"));
        when(cardsDAO.findStopCauseByActionCodeBankC("1", "23")).thenReturn(Arrays.asList(cause));
        when(cardsDAO.findStopCauseByActionCodeBankC("2", "23")).thenReturn(Arrays.asList(new PcdStopCause(), new PcdStopCause()));
        when(cardsDAO.findStopCauseByActionCodeBankC("3", "23")).thenReturn(new ArrayList<PcdStopCause>());

        assertEquals("cause", helper.getCauseByActionCodeBankC("1", "23"));

        boolean hadError = false;
        try {
            helper.getCauseByActionCodeBankC("2", "23");
        } catch (DataIntegrityException e) {
            assertEquals("There are too many Causes for this Action Code", e.getMessage());
            hadError = true;
        }
        assertTrue(hadError);

        hadError = false;
        try {
            helper.getCauseByActionCodeBankC("3", "23");
        } catch (DataIntegrityException e) {
            assertEquals("Cause for given Action code couldn't be found", e.getMessage());
            hadError = true;
        }
        assertTrue(hadError);
    }

    @Test
    public void getActionCodeByCauseBankC() throws DataIntegrityException {
        PcdStopCause cause = new PcdStopCause();
        cause.setStatusCode("100");
        when(cardsDAO.findActionCodeByStopCauseBankC("cause1", "23")).thenReturn(Arrays.asList(cause));
        when(cardsDAO.findActionCodeByStopCauseBankC("cause2", "23")).thenReturn(Arrays.asList(new PcdStopCause(), new PcdStopCause()));
        when(cardsDAO.findActionCodeByStopCauseBankC("cause3", "23")).thenReturn(new ArrayList<>());

        assertEquals("100", helper.getActionCodeByCauseBankC("cause1", "23"));

        boolean hadError = false;
        try {
            helper.getActionCodeByCauseBankC("cause2", "23");
        } catch (DataIntegrityException e) {
            assertEquals("There are too many Action Codes for this Cause", e.getMessage());
            hadError = true;
        }
        assertTrue(hadError);

        hadError = false;
        try {
            helper.getActionCodeByCauseBankC("cause3", "23");
        } catch (DataIntegrityException e) {
            assertEquals("Action code for given cause couldn't be found", e.getMessage());
            hadError = true;
        }
        assertTrue(hadError);
    }
}

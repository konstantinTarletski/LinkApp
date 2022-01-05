package lv.bank.cards.soap.service;

import lv.bank.cards.core.entity.linkApp.PcdAtmAdvert;
import lv.bank.cards.core.entity.linkApp.PcdAtmAdvertPk;
import lv.bank.cards.core.entity.linkApp.PcdAtmAdvertSpecial;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.linkApp.dao.AtmAdvertDAO;
import lv.bank.cards.core.linkApp.dao.AtmAdvertSpecialDAO;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test ATM advertisement handler
 *
 * @author saldabols
 */
public class AtmAdvertServiceTest extends JUnitTestBase {

    protected final AtmAdvertSpecialDAO atmAdvertSpecialDAO = mock(AtmAdvertSpecialDAO.class);
    protected final AtmAdvertDAO atmAdvertDAO = mock(AtmAdvertDAO.class);

    private AtmAdvertService handler = mock(AtmAdvertService.class);

    @Before
    public void setUpTest() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        when(handler.handle(anyString(), anyString(), anyString(), anyString())).thenCallRealMethod();
        handler.cardsDAO = cardsDAO;
        handler.atmAdvertSpecialDAO = atmAdvertSpecialDAO;
        handler.atmAdvertDAO = atmAdvertDAO;
    }

    @Test
    public void handle_specialAdQuestion() throws RequestFormatException, RequestProcessingException {
        String cardNumber = "4775733282237579";
        PcdCard card = new PcdCard();
        card.setCondSet("N07");
        card.setIdCard("123123-1232");
        card.setRegion(Constants.DEFAULT_COUNTRY_LV);
        when(cardsDAO.findByCardNumber(cardNumber)).thenReturn(card);

        PcdAtmAdvertSpecial specialAd = new PcdAtmAdvertSpecial();
        specialAd.setAdvertId("ad1");
        when(atmAdvertSpecialDAO.findAtmAdvertSpecial(eq("123123-1232"), eq("t1"), eq(true))).thenReturn(specialAd);
        // On question answer with add number
        assertEquals("ad1", handler.handle(cardNumber, "t1", null, AtmAdvertService.QUESTION_TYPE));
        assertNotNull(specialAd.getRecDate());
        verify(atmAdvertSpecialDAO).saveOrUpdate(eq(specialAd));
    }

    @Test
    public void handle_specialAdAnwer() throws RequestFormatException, RequestProcessingException {
        String cardNumber = "4775733282237579";
        PcdCard card = new PcdCard();
        card.setCondSet("N07");
        card.setIdCard("123123-1232");
        card.setRegion(Constants.DEFAULT_COUNTRY_LV);
        when(cardsDAO.findByCardNumber(cardNumber)).thenReturn(card);

        PcdAtmAdvertSpecial specialAd = new PcdAtmAdvertSpecial();
        specialAd.setAdvertId("ad1");

        // On answer - answer with default value
        when(atmAdvertSpecialDAO.findAtmAdvertSpecial(eq("123123-1232"), eq("t1"), eq(false))).thenReturn(specialAd);
        assertEquals(AtmAdvertService.DEFAULT_VALUE, handler.handle(cardNumber, "t1", "YES", AtmAdvertService.ANSWER_TYPE));
        assertEquals("YES", specialAd.getAnswer());
        verify(atmAdvertSpecialDAO).saveOrUpdate(eq(specialAd));
    }

    @Test
    public void dataCheck() throws RequestFormatException, RequestProcessingException {
        String cardNumber = "4775733282237579";

        // Wrong card number format
        assertEquals(AtmAdvertService.DEFAULT_VALUE, handler.handle("worng", null, null, null));

        // Missing terminal id
        assertEquals(AtmAdvertService.DEFAULT_VALUE, handler.handle(cardNumber, null, null, null));

        // Cannot find card
        assertEquals(AtmAdvertService.DEFAULT_VALUE, handler.handle(cardNumber, "t1", null, null));

        PcdCard card = new PcdCard();
        when(cardsDAO.findByCardNumber(cardNumber)).thenReturn(card);

        // Legal card
        assertEquals(AtmAdvertService.DEFAULT_VALUE, handler.handle(cardNumber, "t1", null, null));

        card.setCondSet("N07");

        // Don't have personal code
        assertEquals(AtmAdvertService.DEFAULT_VALUE, handler.handle(cardNumber, "t1", null, null));

        card.setIdCard("123123-1232");
        card.setRegion("EE");
        // Not LV card
        assertEquals(AtmAdvertService.DEFAULT_VALUE, handler.handle(cardNumber, "t1", null, null));

        card.setRegion("LV");
        when(atmAdvertDAO.findTodayShownAds(eq("123123-1232"))).thenReturn(Arrays.asList(new PcdAtmAdvert()));

        // Already shown ad
        assertEquals(AtmAdvertService.DEFAULT_VALUE, handler.handle(cardNumber, "t1", null, AtmAdvertService.QUESTION_TYPE));

        when(atmAdvertDAO.findTodayShownAds(eq("123123-1232"))).thenReturn(new ArrayList<PcdAtmAdvert>());
        when(handler.getATMAdvertsFromSonic(eq("123123-1232"))).thenReturn(new HashMap<String, String>());

        // No ads to show
        assertEquals(AtmAdvertService.DEFAULT_VALUE, handler.handle(cardNumber, "t1", null, AtmAdvertService.QUESTION_TYPE));
    }

    @Test
    public void handle_AdQuestion() throws RequestFormatException, RequestProcessingException {
        String cardNumber = "4775733282237579";
        PcdCard card = new PcdCard();
        card.setCondSet("N07");
        card.setIdCard("123123-1232");
        card.setRegion(Constants.DEFAULT_COUNTRY_LV);
        Map<String, String> adMap = new HashMap<String, String>();
        adMap.put("ad1", "email@mail.com;Ad name");

        when(cardsDAO.findByCardNumber(cardNumber)).thenReturn(card);
        when(atmAdvertDAO.findTodayShownAds(eq("123123-1232"))).thenReturn(new ArrayList<PcdAtmAdvert>());
        when(handler.getATMAdvertsFromSonic(eq("123123-1232"))).thenReturn(adMap);

        // On question answer with add number
        assertEquals("ad1", handler.handle(cardNumber, "t1", null, AtmAdvertService.QUESTION_TYPE));
        verify(atmAdvertDAO).saveOrUpdate(any(PcdAtmAdvert.class));
    }

    @Test
    public void handle_AdAnswer() throws RequestFormatException, RequestProcessingException {
        String cardNumber = "4775733282237579";
        PcdCard card = new PcdCard();
        card.setCondSet("N07");
        card.setIdCard("123123-1232");
        card.setRegion(Constants.DEFAULT_COUNTRY_LV);
        @SuppressWarnings("deprecation")
        Date date = new Date(2016, 2, 8);
        PcdAtmAdvert ad = new PcdAtmAdvert();
        ad.setAtmAdvertPk(new PcdAtmAdvertPk(cardNumber, "ad1", date));
        ad.setMessage("email@mail.com;Ad name");

        when(cardsDAO.findByCardNumber(cardNumber)).thenReturn(card);
        when(atmAdvertDAO.findAtmAd(eq("123123-1232"), eq("t1"))).thenReturn(ad);
        when(handler.sendResult(eq("YES"), eq("123123-1232"), eq("ad1"), eq("t1"), eq(date), eq("email@mail.com;Ad name"))).thenReturn("Done");

        // Save answer and send email
        assertEquals(AtmAdvertService.DEFAULT_VALUE, handler.handle(cardNumber, "t1", "YES", AtmAdvertService.ANSWER_TYPE));
        verify(atmAdvertDAO).saveOrUpdate(eq(ad));
        assertTrue(ad.getMessage().endsWith(";Done")); // Answer is added to message
    }
}

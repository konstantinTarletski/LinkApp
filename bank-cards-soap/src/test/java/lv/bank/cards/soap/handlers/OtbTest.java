package lv.bank.cards.soap.handlers;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.rtps.CurrencyCode;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.entity.rtps.StipAccountPK;
import lv.bank.cards.core.rtps.dao.StipAccountDAO;
import lv.bank.cards.core.rtps.dao.StipLocksDAO;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.service.CardService;
import lv.bank.cards.soap.service.OtbService;
import lv.bank.cards.soap.service.dto.OtbDo;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class to test OTB information handler
 *
 * @author saldabols
 */
public class OtbTest extends JUnitTestBase {

    protected Otb handler;
    protected CardService helper = mock(CardService.class);
    protected StipAccountDAO stipAccountDAO = mock(StipAccountDAO.class);
    protected OtbService otbService = mock(OtbService.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        handler = new Otb();
        handler.helper = helper;
        handler.stipAccountDAO = stipAccountDAO;
        handler.otbService = otbService;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException, DataIntegrityException {
        SubRequest request = getSubrequest("otb");

        // There is no card number
        checkRequestFormatException(handler, request, "Please provide valid card number");

        addElementOnRootElement(request, "card", "4775733282237579");

        // Cannot find centre ID
        checkRequestProcessingException(handler, request, "Error getting centreId");

        PcdCard card = new PcdCard();
        card.setCard("4775733282237579");
        card.setBankC("23");
        card.setGroupc("50");
        when(helper.getCentreIdByCard("4775733282237579")).thenReturn(CardUtils.composeCentreIdFromPcdCard(card));
        StipAccount account1 = new StipAccount();
        account1.setComp_id(new StipAccountPK("42800202350", "1"));
        account1.setCurrencyCode(new CurrencyCode());
        account1.getCurrencyCode().setCcyAlpha("EUR");
        account1.getCurrencyCode().setCcyNum("E1");
        account1.getCurrencyCode().setExpDot(2);
        account1.setInitialAmount(100L);
        account1.setBonusAmount(10L);
        account1.setCreditLimit(1000L);

        OtbDo otbDo1 = new OtbDo();
        otbDo1.setAmtInitial("1.00");
        otbDo1.setAmtBonus("0.10");
        otbDo1.setAmtCrd("10.00");
        otbDo1.setOtb("-118.90");
        otbDo1.setAmtLocked("120.00");
        when(otbService.calculateOtb(account1)).thenReturn(otbDo1);

        StipAccount account2 = new StipAccount();
        account2.setComp_id(new StipAccountPK("42800202350", "2"));
        account2.setCurrencyCode(new CurrencyCode());
        account2.getCurrencyCode().setCcyAlpha("EUR");
        account2.getCurrencyCode().setCcyNum("E1");
        account2.getCurrencyCode().setExpDot(2);
        account2.setInitialAmount(200L);
        account2.setBonusAmount(20L);
        account2.setCreditLimit(2000L);

        OtbDo otbDo2 = new OtbDo();
        otbDo2.setAmtInitial("2.00");
        otbDo2.setAmtBonus("0.20");
        otbDo2.setAmtCrd("20.00");
        otbDo2.setOtb("-217.80");
        otbDo2.setAmtLocked("220.00");

        when(otbService.calculateOtb(account2)).thenReturn(otbDo2);

        when(stipAccountDAO.findByCardAndCentreId("4775733282237579", "42800202350")).thenReturn(Arrays.asList(account1, account2));
        when(helper.getCentreIdByCard("4775733282237579")).thenReturn(CardUtils.composeCentreIdFromPcdCard(card));

        handler.handle(request);

        Element accInfo1 = (Element) handler.compileResponse().getElement().elements("account").get(0);
        assertEquals("1", accInfo1.element("account_id").getText());
        assertEquals("EUR", accInfo1.element("ccy-acc-alpha").getText());
        assertEquals("E1", accInfo1.element("ccy-acc-num").getText());
        assertEquals("1.00", accInfo1.element("amt-initial").getText());
        assertEquals("0.10", accInfo1.element("amt-bonus").getText());
        assertEquals("10.00", accInfo1.element("amt-crd").getText());
        assertEquals("-118.90", accInfo1.element("otb").getText());
        assertEquals("120.00", accInfo1.element("amt-locked").getText());

        Element accInfo2 = (Element) handler.compileResponse().getElement().elements("account").get(1);
        assertEquals("2", accInfo2.element("account_id").getText());
        assertEquals("EUR", accInfo2.element("ccy-acc-alpha").getText());
        assertEquals("E1", accInfo2.element("ccy-acc-num").getText());
        assertEquals("2.00", accInfo2.element("amt-initial").getText());
        assertEquals("0.20", accInfo2.element("amt-bonus").getText());
        assertEquals("20.00", accInfo2.element("amt-crd").getText());
        assertEquals("-217.80", accInfo2.element("otb").getText());
        assertEquals("220.00", accInfo2.element("amt-locked").getText());
    }

}

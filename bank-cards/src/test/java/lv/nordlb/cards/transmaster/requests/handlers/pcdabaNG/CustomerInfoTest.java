package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.entity.linkApp.PcdAccParam;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.entity.linkApp.PcdCurrency;
import lv.bank.cards.core.entity.rtps.CurrencyCode;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.entity.rtps.StipRmsStoplist;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.service.OtbService;
import lv.bank.cards.soap.service.dto.OtbDo;
import lv.nordlb.cards.transmaster.fo.interfaces.StipCardManager;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Class to test CustomerInfo message handler
 *
 * @author saldabols
 */
public class CustomerInfoTest extends JUnitHandlerTestBase {

    private CustomerInfo handler;
    private StipCardManager stipCardManager = mock(StipCardManager.class);
    private OtbService otbService = mock(OtbService.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        when(context.lookup(StipCardManager.JNDI_NAME)).thenReturn(stipCardManager);
        handler = spy(new CustomerInfo());
        handler.otbService = otbService;
    }

    @Test
    public void handle() throws RequestPreparationException,
            RequestFormatException, RequestProcessingException, RTPSCallAPIException,
            DataIntegrityException {

        SubRequest request = getSubrequest("customer-info");

        // There is no client id
        checkRequestFormatException(handler, request, "Specify customer identifier");

        request = getSubrequest("customer-info");
        addElementOnRootElement(request, "id", "123123-1234");
        addElementOnRootElement(request, "country", "LV");

        // There is no client by given id
        handler.handle(request);
        assertEquals("<done><customer-info/></done>", handler.compileResponse().asXML());

        Calendar renewDate = Calendar.getInstance();
        renewDate.add(Calendar.DAY_OF_MONTH, -1);
        PcdClient client = new PcdClient();
        PcdAccount account1 = new PcdAccount();
        account1.setAccountNo(new BigDecimal(1));

        PcdAccParam accParam = new PcdAccParam();
        accParam.setUfield5("accountNumber");
        accParam.setPcdCurrency(new PcdCurrency("EUR", null, null, null, null));
        account1.setPcdAccParam(accParam);

        PcdCard card1 = new PcdCard();
        card1.setStatus1("0");
        card1.setCard("4775733282237579");
        card1.setExpiry1(new Date());
        card1.setCondSet("003");
        card1.setBaseSupp("0");
        card1.setRenewDate(renewDate.getTime());
        card1.setGroupc("50");
        card1.setBankC("23");

        PcdCard card2 = new PcdCard();
        card2.setStatus1("1");
        card2.setCard("4775733282237570");
        card2.setExpiry1(new Date());
        card2.setCondSet("003");
        card2.setBaseSupp("0");
        card2.setRenewDate(renewDate.getTime());
        card2.setGroupc("50");
        card2.setBankC("23");

        account1.setPcdCards(Arrays.asList(card1, card2));
        PcdAccount account2 = new PcdAccount();
        account2.setAccountNo(new BigDecimal(2));
        PcdAccParam accParam2 = new PcdAccParam();
        accParam2.setUfield5("accountNumber2");
        accParam2.setPcdCurrency(new PcdCurrency("EUR", null, null, null, null));
        account2.setPcdAccParam(accParam2);
        PcdCard card3 = new PcdCard();
        card3.setStatus1("1");
        card3.setCard("4775733282237571");
        card3.setExpiry1(new Date());
        card3.setCondSet("003");
        card3.setBaseSupp("0");
        card3.setRenewDate(renewDate.getTime());
        card3.setGroupc("50");
        card3.setBankC("23");
        account2.setPcdCards(Arrays.asList(card3));
        client.setPcdAccounts(Arrays.asList(account2, account1));

        StipAccount stipAccount = new StipAccount();
        stipAccount.setInitialAmount(200L);
        stipAccount.setBonusAmount(100L);
        stipAccount.setCreditLimit(500L);
        stipAccount.setCurrencyCode(new CurrencyCode(null, null, 2, null, null));

        StipRmsStoplist rmsStoplist = new StipRmsStoplist();
        rmsStoplist.setDescription("Owner block from iNORD");

        String today = new SimpleDateFormat("MM.yyyy").format(new Date());

        Calendar pday = Calendar.getInstance();
        pday.add(Calendar.YEAR, 1);
        pday.add(Calendar.DAY_OF_MONTH, -1);
        String nextPay = new SimpleDateFormat("yyyy-MM-dd").format(pday.getTime());

        OtbDo otb = new OtbDo();
        otb.setOtb("3.00");

        when(pcdabaNGManager.getClientInfo("123123-1234", "LV")).thenReturn(Arrays.asList(client));
        when(stipCardManager.getStipRmsStoplist(anyString(), anyString(), anyLong())).thenReturn(new ArrayList<>());
        when(stipCardManager.getStipRmsStoplist(eq("4775733282237570"), eq("42800202350"), anyLong())).thenReturn(Arrays.asList(rmsStoplist));
        when(tmfManager.findStipAccountsByCardNumberAndCentreId(eq("4775733282237579"), eq("42800202350"))).thenReturn(Arrays.asList(stipAccount));
        when(otbService.calculateOtb(stipAccount)).thenReturn(otb);
        when(pcdabaNGManager.getAnnualFee(eq("003"), eq("EUR"), eq(true))).thenReturn(50);

        handler.handle(request);

        Element customerInfo = handler.compileResponse().getElement().element("customer-info");
        assertEquals("123123-1234", customerInfo.element("customer-id").getTextTrim());
        assertEquals("0", customerInfo.element("cust-pos").element("pos-agreement").getTextTrim());

        Element acc1 = (Element) customerInfo.element("accounts-list").elements().get(1);
        assertEquals("accountNumber", acc1.element("account-nr").getTextTrim());
        assertEquals("3.00", acc1.element("balance-available").getTextTrim());
        assertEquals("EUR", acc1.element("currency").getTextTrim());

        Element ce1 = (Element) acc1.element("cards-list").elements().get(0);
        assertEquals("4775733282237579", ce1.element("card-number").getTextTrim());
        assertEquals(today, ce1.element("valid-thru").getTextTrim());
        assertEquals("yearly", ce1.element("next-payment-type").getTextTrim());
        assertEquals(nextPay, ce1.element("next-payment-date").getTextTrim());
        assertEquals("3.00", ce1.element("balance-available").getTextTrim());
        assertEquals("EUR", ce1.element("currency").getTextTrim());

        Element ce2 = (Element) acc1.element("cards-list").elements().get(1);
        assertEquals("ibBlock", ce2.element("blocks").element("block-info").getTextTrim());
        assertEquals("4775733282237570", ce2.element("card-number").getTextTrim());
        assertEquals(today, ce2.element("valid-thru").getTextTrim());
        assertEquals("yearly", ce2.element("next-payment-type").getTextTrim());
        assertEquals(nextPay, ce2.element("next-payment-date").getTextTrim());

        Element acc2 = (Element) customerInfo.element("accounts-list").elements().get(0);
        assertEquals("accountNumber2", acc2.element("account-nr").getTextTrim());
        assertEquals("EUR", acc2.element("currency").getTextTrim());

        Element ce3 = (Element) acc2.element("cards-list").elements().get(0);
        assertEquals("softBlock", ce3.element("blocks").element("block-info").getTextTrim());
        assertEquals("4775733282237571", ce3.element("card-number").getTextTrim());
        assertEquals(today, ce3.element("valid-thru").getTextTrim());
        assertEquals("yearly", ce3.element("next-payment-type").getTextTrim());
        assertEquals(nextPay, ce3.element("next-payment-date").getTextTrim());

    }
}

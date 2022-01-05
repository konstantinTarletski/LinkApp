package lv.nordlb.cards.transmaster.requests.handlers.cms;

import lv.bank.cards.core.linkApp.dto.CardInfoDTO;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.JUnitHandlerTestBase;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test PP payment execution handler
 *
 * @author saldabols
 */
public class ExecutePPPaymentTest extends JUnitHandlerTestBase {

    private ExecutePPPayment handler;

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        when(context.lookup(CMSSoapAPIWrapper.JNDI_NAME)).thenReturn(cmsSoapAPIWrapper);
        handler = spy(new ExecutePPPayment());
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, CMSCallAPIException, CMSSoapAPIException {
        SubRequest request = getSubrequest("execute-payment");
        addElementOnRootElement(request, "ppcard", "720141128000012343");

        // Cannot find PP card
        checkRequestProcessingException(handler, request, "No such Priority Pass card!");

        PcdPpCard priorityPassCard = new PcdPpCard();
        priorityPassCard.setStatus(BigDecimal.ZERO);
        when(pcdabaNGManager.getPPCardInfoByCardNumber("720141128000012343")).thenReturn(priorityPassCard);

        // Card is blocked
        checkRequestProcessingException(handler, request, "PP is blocked!");

        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, -2);
        priorityPassCard.setExpiryDate(date.getTime());

        // Card is blocked, checked by expiry date
        checkRequestProcessingException(handler, request, "PP is blocked!");

        priorityPassCard.setStatus(BigDecimal.ONE);
        priorityPassCard.setPcdCard(new PcdCard());
        priorityPassCard.getPcdCard().setCard("4775733282237579");
        priorityPassCard.getPcdCard().setBankC("23");
        PcdAccount account = new PcdAccount();
        account.setAccountNo(BigDecimal.valueOf(47757));
        priorityPassCard.getPcdCard().setPcdAccounts(new HashSet<>(Arrays.asList(account)));

        // Cannot find card info
        checkRequestProcessingException(handler, request, "Card with given number couldn't be found");

        CardInfoDTO infoDto = getCardInfo("4775733282237579", "");
        infoDto.setBillingCurrency("USD");
        when(pcdabaNGManager.getCardInfo("4775733282237579")).thenReturn(infoDto);
        when(pcdabaNGManager.getConvRate("EUR", "USD")).thenReturn(2D);
        addElementOnRootElement(request, "amt", "300");
        addElementOnRootElement(request, "description", "desc");
        addElementOnRootElement(request, "slip", "Slip");

        handler.handle(request);

        assertEquals("1", handler.compileResponse().getElement().element("job").element("Executed").getText());

        verify(cmsSoapAPIWrapper).executeTransaction(eq("0"), eq("47757"), eq("52B"),
                eq("USD"), eq("600"), eq("Slip"), eq("desc"));

    }

}

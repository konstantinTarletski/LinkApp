package lv.nordlb.cards.transmaster.requests.handlers.cms;

import lv.bank.cards.core.entity.cms.IzdAccParam;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdCardGroupCcy;
import lv.bank.cards.core.entity.cms.IzdCardGroupCcyPK;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test payment execution handler
 *
 * @author saldabols
 */
public class ExecutePaymentTest extends JUnitHandlerTestBase {

    private ExecutePayment handler;

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        when(context.lookup(CMSSoapAPIWrapper.JNDI_NAME)).thenReturn(cmsSoapAPIWrapper);
        handler = spy(new ExecutePayment());
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, CMSCallAPIException, CMSSoapAPIException {
        SubRequest request = getSubrequest("execute-payment");
        addElementOnRootElement(request, "account", "4775733");

        // Wrong account number
        checkRequestProcessingException(handler, request, "Account number provided with the query doesn't exist");

        IzdAccount account = new IzdAccount();
        account.setBankC("23");
        account.setGroupC("50");
        account.setIzdAccParam(new IzdAccParam());
        account.getIzdAccParam().setIzdCardGroupCcy(new IzdCardGroupCcy());
        account.getIzdAccParam().getIzdCardGroupCcy().setComp_id(new IzdCardGroupCcyPK("EUR", "50", "23"));
        account.setEndBal(10L);
        when(cardManager.getIzdAccountByAccountNr(BigDecimal.valueOf(4775733))).thenReturn(account);

        addElementOnRootElement(request, "ccy", "USD");

        // Wrong currency
        checkRequestProcessingException(handler, request, "Currency provided with the query doesn't match the currency of account");

        request = getSubrequest("execute-payment");
        addElementOnRootElement(request, "account", "4775733");
        addElementOnRootElement(request, "ccy", "EUR");
        addElementOnRootElement(request, "chkamt", "chkamt");
        addElementOnRootElement(request, "amt", "100");

        // Wrong end balance
        checkRequestProcessingException(handler, request, "Balance provided with the query doesn't match the balance in account");

        account.setEndBal(100L);

        // Missing transaction type
        checkRequestProcessingException(handler, request, "Transaction type is not provided with the query");

        addElementOnRootElement(request, "trtype", "TType");
        addElementOnRootElement(request, "description", "desc");
        addElementOnRootElement(request, "slip", "slip");

        handler.handle(request);

        assertEquals("1", handler.compileResponse().getElement().element("job").element("Executed").getText());
        verify(cmsSoapAPIWrapper).executeTransaction(eq("0"),eq("4775733"), eq("TType"),
                eq("EUR"), eq("100"), eq("slip"), eq("desc"));

    }

}

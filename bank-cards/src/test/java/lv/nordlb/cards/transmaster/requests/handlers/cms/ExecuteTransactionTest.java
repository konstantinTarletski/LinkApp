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
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class ExecuteTransactionTest extends JUnitHandlerTestBase {

    private ExecuteTransaction handler;

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        when(context.lookup(CMSSoapAPIWrapper.JNDI_NAME)).thenReturn(cmsSoapAPIWrapper);
        handler = spy(new ExecuteTransaction());
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, CMSCallAPIException, CMSSoapAPIException {
        SubRequest request = getSubrequest("execute-transaction");
        addElementOnRootElement(request, "card-account", "4775733");

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

        // Wrong currency
        addElementOnRootElement(request, "currency", "USD");
        checkRequestProcessingException(handler, request, "Currency provided with the query doesn't match the currency of account");

        request = getSubrequest("execute-transaction");
        addElementOnRootElement(request, "card-account", "4775733");
        addElementOnRootElement(request, "currency", "EUR");
        addElementOnRootElement(request, "amount", "100");

        // Missing transaction type
        checkRequestProcessingException(handler, request, "Transaction type is not provided with the query");

        addElementOnRootElement(request, "tr-type", "15M");
        addElementOnRootElement(request, "description", "desc");

        account.getIzdAccParam().setStatus("0");

        when(cmsSoapAPIWrapper.executeTransaction(any(),any(), any(), any(), any(), any(), any())).thenReturn(BigDecimal.ONE);
        handler.handle(request);

        assertFalse(new BigDecimal(handler.compileResponse().getElement().element("internal-no").getText()).compareTo(new BigDecimal(0)) <= 0);
        verify(cmsSoapAPIWrapper).executeTransaction(eq("3"),eq("4775733"), eq("15M"),
                eq("EUR"), eq("100"), eq(""), eq("desc"));

    }
}

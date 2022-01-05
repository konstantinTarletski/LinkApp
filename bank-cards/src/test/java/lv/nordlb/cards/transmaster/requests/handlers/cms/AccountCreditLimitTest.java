package lv.nordlb.cards.transmaster.requests.handlers.cms;

import lv.bank.cards.core.entity.linkApp.PcdAccParam;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdCurrency;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.soap.types.OperationConnectionInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.OperationResponseInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditAccountRequest;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.JUnitHandlerTestBase;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test account credit limit information handler
 *
 * @author saldabols
 */
public class AccountCreditLimitTest extends JUnitHandlerTestBase {

    protected AccountCreditLimit handler;

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException, MalformedURLException {
        when(context.lookup(CMSSoapAPIWrapper.JNDI_NAME)).thenReturn(cmsSoapAPIWrapper);
        handler = spy(new AccountCreditLimit());
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, DataIntegrityException, CMSCallAPIException, CMSSoapAPIException {
        SubRequest request = getSubrequest("get-pp-info");
        String accountNoS = "4775733";
        addElementOnRootElement(request, "account_no", accountNoS);
        addElementOnRootElement(request, "credit_limit", "10000");

        // Cannot find account
        checkRequestProcessingException(handler, request, "Can't find account [" + accountNoS + "]");

        PcdAccount accountPcd = new PcdAccount();
        accountPcd.setBankC(CMS_BANK_CODE);
        accountPcd.setAccountNo(BigDecimal.ONE);
        PcdAccParam pcdAccParam = new PcdAccParam();
        PcdCurrency currency = new PcdCurrency();
        currency.setIsoAlpha("EUR");
        pcdAccParam.setPcdCurrency(currency);
        accountPcd.setPcdAccParam(pcdAccParam);

        when(pcdabaNGManager.getAccountByAccountNo(new BigDecimal(accountNoS))).thenReturn(accountPcd);

        OperationConnectionInfo info = new OperationConnectionInfo();
        info.setBANKC(CMS_BANK_CODE);
        info.setGROUPC(CMS_GROUP_CODE);
        RowTypeEditAccountRequest param = new RowTypeEditAccountRequest();
        param.setCRD(BigDecimal.valueOf(10000));
        OperationResponseInfo editAccountResponse = new OperationResponseInfo();
        editAccountResponse.setResponseCode(BigInteger.ZERO);

        when(issuingPort.editAccount(info, param)).thenReturn(editAccountResponse);

        handler.handle(request);

        assertEquals(10000L, accountPcd.getPcdAccParam().getCrd());
        assertEquals(accountNoS, handler.compileResponse().getElement().element("account_no").getTextTrim());
        assertEquals("10000", handler.compileResponse().getElement().element("credit_limit").getTextTrim());
        assertEquals("EUR", handler.compileResponse().getElement().element("ccy").getTextTrim());

        verify(pcdabaNGManager).saveOrUpdate(accountPcd.getPcdAccParam());
        verify(cmsSoapAPIWrapper).editAccount(BigDecimal.ONE, 10000);
    }

}

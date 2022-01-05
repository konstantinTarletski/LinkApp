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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test make Dormant handler
 *
 * @author saldabols
 */
public class ActivateDormantAccountHandlerTest extends JUnitHandlerTestBase {

    protected ActivateDormantAccountHandler handler;

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        when(context.lookup(CMSSoapAPIWrapper.JNDI_NAME)).thenReturn(cmsSoapAPIWrapper);
        handler = spy(new ActivateDormantAccountHandler());
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, CMSCallAPIException, CMSSoapAPIException {
        SubRequest request = getSubrequest("activate-dormant");
        addElementOnRootElement(request, "account", "4775733");

        // Cannot find account
        checkRequestProcessingException(handler, request, "Account number provided with the query doesn't exist");

        IzdAccount account = new IzdAccount();
        account.setBankC("23");
        account.setGroupC("50");
        account.setCardAcct("cAccount");
        account.setIzdAccParam(new IzdAccParam());
        account.getIzdAccParam().setIzdCardGroupCcy(new IzdCardGroupCcy());
        account.getIzdAccParam().getIzdCardGroupCcy().setComp_id(new IzdCardGroupCcyPK("USD", "50", "23"));

        when(cardManager.getIzdAccountByAccountNr(new BigDecimal("4775733"))).thenReturn(account);
        addElementOnRootElement(request, "ccy", "EUR");

        // Wrong currency
        checkRequestProcessingException(handler, request, "Currency provided with the query doesn't match the currency of account");

        account.getIzdAccParam().getIzdCardGroupCcy().getComp_id().setCcy("EUR");
        account.getIzdAccParam().setStatus("1");

        // Wrong account status
        checkRequestProcessingException(handler, request, "Can activate only dormant account");

        account.getIzdAccParam().setStatus("3");
        addElementOnRootElement(request, "comment", "comment");

        handler.handle(request);

        assertEquals("done", handler.compileResponse().getElement().element("activate-dormant").getText());

        verify(cmsSoapAPIWrapper).activateAccount("cAccount", "EUR", "comment");
    }

}

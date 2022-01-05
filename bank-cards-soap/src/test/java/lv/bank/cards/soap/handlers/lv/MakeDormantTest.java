package lv.bank.cards.soap.handlers.lv;

import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.entity.cms.IzdAccParam;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdCardGroupCcy;
import lv.bank.cards.core.entity.cms.IzdCardGroupCcyPK;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.requests.SubRequest;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test make Dormant handler
 *
 * @author saldabols
 */
public class MakeDormantTest extends JUnitTestBase {

    protected MakeDormant handler;
    protected CardDAO cardDAO = mock(CardDAO.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        handler = spy(new MakeDormant());
        handler.cardDAO = cardDAO;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, CMSCallAPIException, CMSSoapAPIException {
        SubRequest request = getSubrequest("make-dormant");
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

        when(cardDAO.findIzdAccountByAcctNr(new BigDecimal("4775733"))).thenReturn(account);
        addElementOnRootElement(request, "ccy", "EUR");

        // Wrong currency
        checkRequestProcessingException(handler, request, "Currency provided with the query doesn't match the currency of account");

        account.getIzdAccParam().getIzdCardGroupCcy().getComp_id().setCcy("EUR");
        account.getIzdAccParam().setStatus("1");

        // Wrong account status
        checkRequestProcessingException(handler, request, "Account [4775733] is already made dormant or closed");

        account.getIzdAccParam().setStatus("0");
        addElementOnRootElement(request, "description", "desc");
        addElementOnRootElement(request, "interest", "1");

        handler.handle(request);

        assertEquals("done", handler.compileResponse().getElement().element("make-dormant").getText());

        verify(cmsSoapAPIWrapper).makeAccountDormant("cAccount", "EUR", "desc", "1");
    }

}

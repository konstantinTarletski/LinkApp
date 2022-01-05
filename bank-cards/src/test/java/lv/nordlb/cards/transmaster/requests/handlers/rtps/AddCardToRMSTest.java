package lv.nordlb.cards.transmaster.requests.handlers.rtps;

import lv.bank.cards.core.entity.rtps.AnswerCode;
import lv.bank.cards.core.entity.rtps.StipRmsStoplist;
import lv.bank.cards.core.entity.rtps.StipRmsStoplistPK;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.service.CardService;
import lv.nordlb.cards.transmaster.fo.interfaces.StipCardManager;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.JUnitHandlerTestBase;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class to test Card blocking in RMS handler
 *
 * @author saldabols
 */
public class AddCardToRMSTest extends JUnitHandlerTestBase {

    private AddCardToRMS handler;
    private StipCardManager stipCardManager = mock(StipCardManager.class);
    protected CardService helper = mock(CardService.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        when(context.lookup(StipCardManager.JNDI_NAME)).thenReturn(stipCardManager);
        handler = spy(new AddCardToRMS());
        handler.mainWrapper = rtpsCallApiWraper;
        handler.helper = helper;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException,
            RequestProcessingException, RTPSCallAPIException {
        SubRequest request = getSubrequest("add-card-to-rms");

        // There is no card number
        checkRequestProcessingException(handler, request, "Please provide valid card number");

        addElementOnRootElement(request, "card", "4775733282237579");

        // Rule expr must be specified
        checkRequestFormatException(handler, request, "rule-expr tag value is mandatory");

        addElementOnRootElement(request, "rule-expr", "rule");

        when(helper.getCentreIdByCard("4775733282237579")).thenReturn(null);

        // Don't have centre id
        checkRequestProcessingException(handler, request, "Card with given number couldn't be found");

        addElementOnRootElement(request, "centre-id", "cId");
        addElementOnRootElement(request, "priority", "1");

        // Wrong priority type
        checkRequestFormatException(handler, request, "Incorrect priority value");

        request = getSubrequest("add-card-to-rms");
        addElementOnRootElement(request, "card", "4775733282237579");
        addElementOnRootElement(request, "rule-expr", "rule");
        addElementOnRootElement(request, "centre-id", "cId");
        addElementOnRootElement(request, "priority", "-1");
        addElementOnRootElement(request, "description", "desc\rdesc");
        addElementOnRootElement(request, "action-code", "aCode");

        StipRmsStoplist rmsStoplist = new StipRmsStoplist();
        rmsStoplist.setAnswerCode(new AnswerCode());
        rmsStoplist.getAnswerCode().setActionCode("aCode");
        rmsStoplist.setCompId(new StipRmsStoplistPK("cId", "4775733282237579", -1L));
        rmsStoplist.setRuleExpr("rule");
        rmsStoplist.setDescription("desc"); // Different description

        when(stipCardManager.getStipRmsStoplist("4775733282237579", "cId", -1L)).thenReturn(Arrays.asList(rmsStoplist));

        handler.handle(request);

        assertEquals("4775733282237579", handler.compileResponse().getElement().element("add-card-to-rms").element("added").getText());

        verify(stipCardManager).getStipRmsStoplist("4775733282237579", "cId", -1L);
        verify(rtpsCallApiWraper).AddCardToRMSStop("cId", "4775733282237579", "rule", -1L, "aCode", "desc desc");
    }

}

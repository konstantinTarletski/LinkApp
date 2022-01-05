package lv.bank.cards.soap.handlers;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.rtps.AnswerCode;
import lv.bank.cards.core.entity.rtps.StipRmsStoplist;
import lv.bank.cards.core.entity.rtps.StipRmsStoplistPK;
import lv.bank.cards.core.rtps.dao.StipRmsStoplistDAO;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.service.CardService;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.naming.NamingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardStatusRMSTest extends JUnitTestBase {

    protected CardStatusRMS handler;
    protected StipRmsStoplistDAO stipRmsStoplistDAO = mock(StipRmsStoplistDAO.class);
    protected CardService helper = mock(CardService.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        handler = Mockito.spy(new CardStatusRMS());
        handler.stipRmsStoplistDAO = stipRmsStoplistDAO;
        handler.helper = helper;
    }

    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException,
            DataIntegrityException, RTPSCallAPIException {
        SubRequest request = getSubrequest("card-status", "yyyy-MM-dd");
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // There is no card number
        checkRequestFormatException(handler, request, "Please provide valid card number");

        addElementOnRootElement(request, "card", "4775733282237579");

        // Cannot get centre id
        checkRequestProcessingException(handler, request, "Card with given number couldn't be found");

        PcdCard card = new PcdCard();
        card.setBankC("23");
        card.setGroupc("50");
        when(helper.getPcdCard("4775733282237579")).thenReturn(card);

        StipRmsStoplist rmsStoplist1 = new StipRmsStoplist();
        rmsStoplist1.setAnswerCode(new AnswerCode());
        rmsStoplist1.getAnswerCode().setActionCode("aCode1");
        rmsStoplist1.setCompId(new StipRmsStoplistPK("42800202350", "4775733282237579", -1L));
        rmsStoplist1.setRuleExpr("rule1");
        rmsStoplist1.setDescription("desc1");
        rmsStoplist1.setEffectiveDate(new Date());
        rmsStoplist1.setUpdateDate(new Date());
        rmsStoplist1.setPurgeDate(new Date());

        StipRmsStoplist rmsStoplist2 = new StipRmsStoplist();
        rmsStoplist2.setAnswerCode(new AnswerCode());
        rmsStoplist2.getAnswerCode().setActionCode("aCode2");
        rmsStoplist2.setCompId(new StipRmsStoplistPK("42800202350", "4775733282237579", -2L));
        rmsStoplist2.setRuleExpr("rule2");
        rmsStoplist2.setDescription("desc2");

        when(helper.getCentreIdByCard("4775733282237579")).thenReturn(CardUtils.composeCentreIdFromPcdCard(card));
        when(stipRmsStoplistDAO.getStipRmsStoplist("4775733282237579", "42800202350", null)).thenReturn(Arrays.asList(rmsStoplist1, rmsStoplist2));

        handler.handle(request);

        Element info = handler.compileResponse().getElement().element("card-status-rms");
        assertEquals("4775733282237579", info.element("card").getText());
        Element entry1 = (Element) info.elements("entry").get(0);
        assertEquals("42800202350", entry1.element("centre-id").getText());
        assertEquals("4775733282237579", entry1.element("card").getText());
        assertEquals("aCode1", entry1.element("action-code").getText());
        assertEquals("desc1", entry1.element("description").getText());
        assertEquals("rule1", entry1.element("rule-expression").getText());
        assertEquals("-1", entry1.element("priority").getText());
        assertEquals(today, entry1.element("effective-date").getText());
        assertEquals(today, entry1.element("update-date").getText());
        assertEquals(today, entry1.element("purge-date").getText());
        Element entry2 = (Element) info.elements("entry").get(1);
        assertEquals("42800202350", entry2.element("centre-id").getText());
        assertEquals("4775733282237579", entry2.element("card").getText());
        assertEquals("aCode2", entry2.element("action-code").getText());
        assertEquals("desc2", entry2.element("description").getText());
        assertEquals("rule2", entry2.element("rule-expression").getText());
        assertEquals("-2", entry2.element("priority").getText());
        assertEquals("", entry2.element("effective-date").getText());
        assertEquals("", entry2.element("update-date").getText());
        assertEquals("", entry2.element("purge-date").getText());

    }

}

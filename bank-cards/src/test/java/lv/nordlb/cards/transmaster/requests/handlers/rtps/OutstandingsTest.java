package lv.nordlb.cards.transmaster.requests.handlers.rtps;

import lv.bank.cards.core.entity.cms.IzdCcyTable;
import lv.bank.cards.core.entity.cms.IzdLock;
import lv.bank.cards.core.entity.rtps.CurrencyCode;
import lv.bank.cards.core.entity.rtps.StipLocks;
import lv.bank.cards.core.entity.rtps.StipLocksMatch;
import lv.nordlb.cards.transmaster.fo.interfaces.TMFManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.JUnitHandlerTestBase;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.NamingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author saldabols
 */
@RunWith(MockitoJUnitRunner.class)
public class OutstandingsTest extends JUnitHandlerTestBase {

    private Outstandings handler;

    @Mock
    private TMFManager tmfManager;

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        when(context.lookup(TMFManager.JNDI_NAME)).thenReturn(tmfManager);
        handler = new Outstandings();
    }

    @Test
    public void testExceptionForMissingCardNumber() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        // given
        SubRequest request = getSubrequest("outstandings", "yyyy-MM-dd");
        // when, then
        checkRequestFormatException(handler, request, "Please provide valid card number");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReturnLocksFromCmsAndRtps() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        // given
        SubRequest request = getSubrequest("outstandings", "yyyy-MM-dd");
        addElementOnRootElement(request, "card", "4775733282237579");
        addElementOnRootElement(request, "show-all", "1");

        IzdLock lock1 = new IzdLock();
        lock1.setRowNumb(1L);
        lock1.setFld043("f1");
        lock1.setRequestDate(new Date());
        lock1.setLockingSign(1);
        lock1.setFld006(1000L);
        lock1.setIzdCcyFld049(new IzdCcyTable());
        lock1.getIzdCcyFld049().setExp("2");
        lock1.getIzdCcyFld049().setCcyCode("EUR1");
        lock1.setFld004(100L);
        lock1.setIzdCcyFld051(new IzdCcyTable());
        lock1.getIzdCcyFld051().setExp("2");
        lock1.getIzdCcyFld051().setCcyCode("USD1");
        lock1.setFld049("EUR");
        lock1.setFld051("USD");
        lock1.setFld038("38_1");
        lock1.setFld011("11_1");
        IzdLock lock2 = new IzdLock();
        lock2.setRowNumb(2L);
        lock2.setFld043("f2");
        lock2.setRequestDate(new Date());
        lock2.setLockingSign(0);
        lock2.setFld006(2000L);
        lock2.setIzdCcyFld049(new IzdCcyTable());
        lock2.getIzdCcyFld049().setExp("2");
        lock2.getIzdCcyFld049().setCcyCode("EUR1");
        lock2.setFld004(200L);
        lock2.setIzdCcyFld051(new IzdCcyTable());
        lock2.getIzdCcyFld051().setExp("2");
        lock2.getIzdCcyFld051().setCcyCode("USD1");
        lock2.setFld049("EUR");
        lock2.setFld051("USD");
        lock2.setFld038("38_2");
        lock2.setFld011("11_2");
        when(izdLockManager.findIzdLocksByCard("4775733282237579", true)).thenReturn(asList(lock1, lock2));

        StipLocks lock3 = new StipLocks();
        lock3.setRowNumb(3L);
        lock3.setStipLocksMatch(new StipLocksMatch());
        lock3.getStipLocksMatch().setFld043("f3");
        lock3.setRequestDate(new Date());
        lock3.getStipLocksMatch().setFld004(300L);
        lock3.setAmount(303L);
        lock3.getStipLocksMatch().setCurrencyCodeByFld049(new CurrencyCode());
        lock3.getStipLocksMatch().getCurrencyCodeByFld049().setExpDot(2);
        lock3.getStipLocksMatch().getCurrencyCodeByFld049().setCcyAlpha("EUR");
        lock3.getStipLocksMatch().setCurrencyCodeByFld051(new CurrencyCode());
        lock3.getStipLocksMatch().getCurrencyCodeByFld051().setExpDot(2);
        lock3.getStipLocksMatch().getCurrencyCodeByFld051().setCcyAlpha("EUR");
        lock3.getStipLocksMatch().setFld049("EUR1");
        lock3.getStipLocksMatch().setFld051("EUR1");
        lock3.getStipLocksMatch().setFld038("38_3");
        lock3.getStipLocksMatch().setFld011("11_3");
        StipLocks lock4 = new StipLocks(); // Not included because have same row number as pcd record 2
        lock4.setRowNumb(2L);
        lock4.setStipLocksMatch(new StipLocksMatch());
        lock4.getStipLocksMatch().setFld043("f2");

        when(tmfManager.findStipLocksByCardNumber("4775733282237579", true)).thenReturn(asList(lock3, lock4));

        // when
        handler.handle(request);

        // then
        List<Element> outstanding = handler.compileResponse().getElement().elements("outstanding");
        assertEquals(3, outstanding.size());

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Element lInfo1 = outstanding.get(0);
        assertEquals("cms", lInfo1.attribute("src").getText());
        assertEquals("f1", lInfo1.element("where").getText());
        assertEquals(today, lInfo1.element("when").getText());
        assertEquals("1.0", lInfo1.element("amt-trxn").getText());
        assertEquals("10.0", lInfo1.element("amt-acc").getText());
        assertEquals("EUR1", lInfo1.element("ccy-trxn-num").getText());
        assertEquals("USD1", lInfo1.element("ccy-acc-num").getText());
        assertEquals("EUR", lInfo1.element("ccy-trxn-alpha").getText());
        assertEquals("USD", lInfo1.element("ccy-acc-alpha").getText());
        assertEquals("38_1", lInfo1.element("appr-code").getText());
        assertEquals("11_1", lInfo1.element("stan").getText());

        Element lInfo2 = outstanding.get(1);
        assertEquals("cms", lInfo2.attribute("src").getText());
        assertEquals("f2", lInfo2.element("where").getText());
        assertEquals(today, lInfo2.element("when").getText());
        assertEquals("-2.0", lInfo2.element("amt-trxn").getText());
        assertEquals("-20.0", lInfo2.element("amt-acc").getText());
        assertEquals("EUR1", lInfo2.element("ccy-trxn-num").getText());
        assertEquals("USD1", lInfo2.element("ccy-acc-num").getText());
        assertEquals("EUR", lInfo2.element("ccy-trxn-alpha").getText());
        assertEquals("USD", lInfo2.element("ccy-acc-alpha").getText());
        assertEquals("38_2", lInfo2.element("appr-code").getText());
        assertEquals("11_2", lInfo2.element("stan").getText());

        Element lInfo3 = outstanding.get(2);
        assertEquals("rtps", lInfo3.attribute("src").getText());
        assertEquals("f3", lInfo3.element("where").getText());
        assertEquals(today, lInfo3.element("when").getText());
        assertEquals("-3.0", lInfo3.element("amt-trxn").getText());
        assertEquals("-3.03", lInfo3.element("amt-acc").getText());
        assertEquals("EUR1", lInfo3.element("ccy-trxn-num").getText());
        assertEquals("EUR1", lInfo3.element("ccy-acc-num").getText());
        assertEquals("EUR", lInfo3.element("ccy-trxn-alpha").getText());
        assertEquals("EUR", lInfo3.element("ccy-acc-alpha").getText());
        assertEquals("38_3", lInfo3.element("appr-code").getText());
        assertEquals("11_3", lInfo3.element("stan").getText());
    }
}

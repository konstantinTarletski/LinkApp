package lv.bank.cards.soap.service;

import lv.bank.cards.core.entity.rtps.CurrencyCode;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.entity.rtps.StipAccountPK;
import lv.bank.cards.core.linkApp.PcdabaNGConstants;
import lv.bank.cards.core.rtps.dao.StipLocksDAO;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.requests.MockInitialContextRule;
import lv.bank.cards.soap.service.dto.OtbDo;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.NamingException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OtbServiceTest {

    protected final Context context = mock(Context.class);
    protected final SessionFactory sessionFactory = mock(SessionFactory.class);
    protected final StipLocksDAO stipLocksDAO = mock(StipLocksDAO.class);

    protected OtbService service;

    @Rule
    public MockInitialContextRule mockInitialContextRule = new MockInitialContextRule(context);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        when(context.lookup(PcdabaNGConstants.HibernateSessionFactory)).thenReturn(sessionFactory);
        service = new OtbService(stipLocksDAO);
    }

    @Test
    public void calculateOtbTest() {
        StipAccount account1 = new StipAccount();
        account1.setComp_id(new StipAccountPK("42800202350", "1"));
        account1.setCurrencyCode(new CurrencyCode());
        account1.getCurrencyCode().setCcyAlpha("EUR");
        account1.getCurrencyCode().setCcyNum("E1");
        account1.getCurrencyCode().setExpDot(2);
        account1.setInitialAmount(100L);
        account1.setBonusAmount(10L);
        account1.setCreditLimit(1000L);
        when(stipLocksDAO.calculateTotalLockedAmount(account1)).thenReturn(12000L);

        OtbDo otbDo1 = service.calculateOtb(account1);

        assertEquals("1.00", otbDo1.getAmtInitial());
        assertEquals("0.10", otbDo1.getAmtBonus());
        assertEquals("10.00", otbDo1.getAmtCrd());
        assertEquals("-118.90", otbDo1.getOtb());
        assertEquals("120.00", otbDo1.getAmtLocked());

        StipAccount account2 = new StipAccount();
        account2.setComp_id(new StipAccountPK("42800202350", "2"));
        account2.setCurrencyCode(new CurrencyCode());
        account2.getCurrencyCode().setCcyAlpha("EUR");
        account2.getCurrencyCode().setCcyNum("E1");
        account2.getCurrencyCode().setExpDot(2);
        account2.setInitialAmount(200L);
        account2.setBonusAmount(20L);
        account2.setCreditLimit(2000L);
        when(stipLocksDAO.calculateTotalLockedAmount(account2)).thenReturn(22000L);

        OtbDo otbDo2 = service.calculateOtb(account2);

        assertEquals("2.00", otbDo2.getAmtInitial());
        assertEquals("0.20", otbDo2.getAmtBonus());
        assertEquals("20.00", otbDo2.getAmtCrd());
        assertEquals("-217.80", otbDo2.getOtb());
        assertEquals("220.00", otbDo2.getAmtLocked());

    }

}

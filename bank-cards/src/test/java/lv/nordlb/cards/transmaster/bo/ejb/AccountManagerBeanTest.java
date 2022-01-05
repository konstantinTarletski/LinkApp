package lv.nordlb.cards.transmaster.bo.ejb;

import lv.bank.cards.core.cms.dao.AccountDAO;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountManagerBeanTest {

    private AccountDAO accountDAO = mock(AccountDAO.class);

    private AccountManagerBean manager = new AccountManagerBean(true);

    @Before
    public void initTest() {
        manager.setAccountDAO(accountDAO);
    }

    @Test
    public void findAccountByIzdClientAndExternalNo() throws DataIntegrityException {
        String clientId = "abc";
        List<IzdAccount> accounts = new ArrayList<>();
        when(accountDAO.findByIzdClientAndExternalNo(clientId, "e1")).thenReturn(accounts);

        assertNull(manager.findAccountByIzdClientAndExternalNo(clientId, "e0"));
        assertNull(manager.findAccountByIzdClientAndExternalNo(clientId, "e1"));

        IzdAccount acc1 = new IzdAccount();
        acc1.setCardAcct("a1");
        accounts.add(acc1);

        assertEquals(acc1, manager.findAccountByIzdClientAndExternalNo(clientId, "e1"));

        IzdAccount acc2 = new IzdAccount();
        acc2.setCardAcct("a2");
        accounts.add(acc2);

        boolean hadError = false;
        try {
            manager.findAccountByIzdClientAndExternalNo(clientId, "e1");
        } catch (DataIntegrityException e) {
            hadError = true;
            assertEquals("There are 2 accounts found instead of one : [a1][a2]", e.getMessage());
        }
        assertTrue(hadError);
    }

    @Test
    public void findAccountByAccountNo() throws DataIntegrityException {
        boolean hadError = false;
        try {
            manager.findAccountByAccountNo(BigDecimal.ONE);
        } catch (DataIntegrityException e) {
            hadError = true;
            assertEquals("Account with such accountNo [1] does not exist", e.getMessage());
        }
        assertTrue(hadError);

        IzdAccount acc = new IzdAccount();
        when(accountDAO.findByAccountNo(BigDecimal.ONE)).thenReturn(acc);

        assertEquals(acc, manager.findAccountByAccountNo(BigDecimal.ONE));
    }
}

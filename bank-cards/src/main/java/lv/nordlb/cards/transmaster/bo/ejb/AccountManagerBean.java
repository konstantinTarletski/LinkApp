/*
 * $Id: AccountManagerBean.java 1 2006-08-09 13:17:00Z just $
 * Created on 2005.1.3
 */
package lv.nordlb.cards.transmaster.bo.ejb;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.AccountDAO;
import lv.bank.cards.core.cms.impl.AccountDAOHibernate;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.nordlb.cards.transmaster.bo.interfaces.AccountManager;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.List;


@Slf4j
@Stateless
public class AccountManagerBean implements AccountManager {

    @Setter
    protected AccountDAO accountDAO;

    public AccountManagerBean() {
        accountDAO = new AccountDAOHibernate();
    }

    protected AccountManagerBean(boolean test) {
    }


    public IzdAccount findAccountByIzdClientAndExternalNo(String clientId, String externalNo) throws DataIntegrityException {

        log.info("AccountManager.findAccountByIzdClientAndExternalNo clientId = {}, externalNo = {}", clientId, externalNo);

        List<IzdAccount> accountList = accountDAO.findByIzdClientAndExternalNo(clientId, externalNo);

        if (accountList == null || accountList.isEmpty()) {
            return null;
        }

        if (accountList.size() == 1) {
            return accountList.get(0);
        } else {
            StringBuffer accounts = new StringBuffer();
            for (IzdAccount account : accountList) {
                accounts.append("[" + account.getCardAcct() + "]");
            }
            throw new DataIntegrityException("There are " + accountList.size() + " accounts found instead of one : " + accounts);
        }
    }

    public IzdAccount findAccountByAccountNo(BigDecimal accountNo) throws DataIntegrityException {
        log.info("AccountManager.findAccountByAccountNo accountNo = {}", accountNo);
        IzdAccount a = accountDAO.findByAccountNo(accountNo);
        if (a == null) {
            throw new DataIntegrityException("Account with such accountNo [" + accountNo + "] does not exist");
        }
        return a;
    }

    public void saveOrUpdate(Object o) throws DataIntegrityException {
        accountDAO.saveOrUpdate(o);
    }
}

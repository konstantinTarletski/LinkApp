package lv.nordlb.cards.transmaster.bo.interfaces;

import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.utils.DataIntegrityException;

import javax.ejb.Local;
import java.math.BigDecimal;

@Local
public interface AccountManager {

    String COMP_NAME = "java:comp/env/ejb/AccountManager";
    String JNDI_NAME = "java:app/bankCards/AccountManagerBean";

    IzdAccount findAccountByIzdClientAndExternalNo(String clientId, String externalNo) throws DataIntegrityException;

    IzdAccount findAccountByAccountNo(BigDecimal accountNo) throws DataIntegrityException;

    void saveOrUpdate(Object o) throws DataIntegrityException;
}

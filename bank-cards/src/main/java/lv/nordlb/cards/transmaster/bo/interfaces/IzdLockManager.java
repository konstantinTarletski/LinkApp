package lv.nordlb.cards.transmaster.bo.interfaces;

import lv.bank.cards.core.entity.cms.IzdLock;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Local
public interface IzdLockManager {

    String COMP_NAME = "java:comp/env/ejb/IzdLockManager";
    String JNDI_NAME = "java:app/bankCards/IzdLockManagerBean";

    List<IzdLock> findIzdLocksByCard(String card, boolean mode);

    List<IzdLock> findIzdLocksByAccount(BigDecimal accountNo, boolean mode, Date fromDate, Date toDate, Long fromAmount, Long toAmount, String shop, List<String> cardNumbers);
}

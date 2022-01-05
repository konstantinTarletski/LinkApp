package lv.nordlb.cards.transmaster.bo.ejb;

import lv.bank.cards.core.cms.dao.IzdLockDAO;
import lv.bank.cards.core.cms.impl.IzdLockDAOHibernate;
import lv.bank.cards.core.entity.cms.IzdLock;
import lv.nordlb.cards.transmaster.bo.interfaces.IzdLockManager;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Stateless
public class IzdLockManagerBean implements IzdLockManager {

    private final IzdLockDAO izdLockDAO = new IzdLockDAOHibernate();

    @Override
    public List<IzdLock> findIzdLocksByCard(String card, boolean mode) {
        return izdLockDAO.findIzdLocksByCardNumber(card, mode);
    }

    @Override
    public List<IzdLock> findIzdLocksByAccount(BigDecimal accountNo, boolean mode, Date fromDate, Date toDate, Long fromAmount, Long toAmount, String shop, List<String> cardNumbers) {
        return izdLockDAO.findIzdLocksByAccount(accountNo, mode, fromDate, toDate, fromAmount, toAmount, shop, cardNumbers);
    }
}

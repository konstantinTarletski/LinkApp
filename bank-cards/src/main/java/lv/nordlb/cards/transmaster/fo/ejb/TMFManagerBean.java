package lv.nordlb.cards.transmaster.fo.ejb;

import lv.bank.cards.core.entity.rtps.CardsException;
import lv.bank.cards.core.entity.rtps.Regdir;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.entity.rtps.StipLocks;
import lv.bank.cards.core.rtps.dao.CardsExceptionsDAO;
import lv.bank.cards.core.rtps.dao.RegDirDAO;
import lv.bank.cards.core.rtps.dao.StipAccountDAO;
import lv.bank.cards.core.rtps.dao.StipLocksDAO;
import lv.bank.cards.core.rtps.impl.CardsExceptionsDAOHibernate;
import lv.bank.cards.core.rtps.impl.RegDirDAOHibernate;
import lv.bank.cards.core.rtps.impl.StipAccountDAOHibernate;
import lv.bank.cards.core.rtps.impl.StipLocksDAOHibernate;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.nordlb.cards.transmaster.fo.interfaces.TMFManager;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

@Stateless
public class TMFManagerBean implements TMFManager {

    CardsExceptionsDAO cardsExceptionsDAO;
    StipAccountDAO stipAccountDAO;
    StipLocksDAO stipLocksDAO;
    RegDirDAO regDirDAO;

    public TMFManagerBean() {
        cardsExceptionsDAO = new CardsExceptionsDAOHibernate();
        stipAccountDAO = new StipAccountDAOHibernate();
        stipLocksDAO = new StipLocksDAOHibernate();
        regDirDAO = new RegDirDAOHibernate();
    }

    @Override
    public CardsException findInCardsExceptionsList(String cardNumber) throws DataIntegrityException {
        return cardsExceptionsDAO.findByCardNumber(cardNumber);
    }

    @Override
    public List<StipAccount> findStipAccountsByCardNumberAndCentreId(String cardNumber, String centreId) {
        return stipAccountDAO.findByCardAndCentreId(cardNumber, centreId);
    }

    @Override
    public List<StipLocks> findStipLocksByCardNumber(String cardNumber, boolean mode) {
        return stipLocksDAO.findStipLocksByCardNumber(cardNumber, mode);
    }

    @Override
    public List<StipLocks> findStipLocksByAccount(String account, boolean mode, Date fromDate, Date toDate, Long fromAmount, Long toAmount, String shop, List<String> cardNumbers) {
        return stipLocksDAO.findStipLocksByAccount(account, mode, fromDate, toDate, fromAmount, toAmount, shop, cardNumbers);
    }

    @Override
    public List<Regdir> getRegDir() {
        return regDirDAO.GetRegDir();
    }

    @Override
    public Long getLockedAmountByStipAccount(StipAccount sa) {
        if (sa == null) return null;
        Long la = stipLocksDAO.calculateTotalLockedAmount(sa);
        return (la == null ? 0L : la) + (sa.getLockAmountCms() == null ? 0 : sa.getLockAmountCms());
    }

    @Override
    public StipAccount findStipAccountByAccountNoAndCentreId(String accountId, String centreId) {
        return stipAccountDAO.findAccountByAccountNoAndCentreId(accountId, centreId);
    }
}

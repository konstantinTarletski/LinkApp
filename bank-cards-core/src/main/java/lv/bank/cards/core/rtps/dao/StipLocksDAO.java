package lv.bank.cards.core.rtps.dao;

import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.entity.rtps.StipLocks;

import java.util.Date;
import java.util.List;

public interface StipLocksDAO extends DAO {

    List<StipLocks> findStipLocksByCardNumber(String card, boolean mode);

    List<StipLocks> findStipLocksByAccount(String account, boolean mode, Date fromDate, Date toDate, Long fromAmount, Long toAmount, String shop, List<String> cardNumbers);

    Long calculateTotalLockedAmount(StipAccount a);
}

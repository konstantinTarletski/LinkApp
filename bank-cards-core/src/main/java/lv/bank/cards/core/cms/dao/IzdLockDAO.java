package lv.bank.cards.core.cms.dao;

import lv.bank.cards.core.entity.cms.IzdLock;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IzdLockDAO extends DAO {

    List<IzdLock> findIzdLocksByCardNumber(String card, boolean mode);

    List<IzdLock> findIzdLocksByAccount(BigDecimal accountNo, boolean mode, Date fromDate, Date toDate, Long fromAmount, Long toAmount, String shop, List<String> cardNumbers);
}

package lv.bank.cards.core.rtps.dao;

import lv.bank.cards.core.entity.rtps.StipAccount;

import java.util.List;

public interface StipAccountDAO extends DAO {
    List<StipAccount> findByCardAndCentreId(String card, String centreId);

    StipAccount findAccountByAccountNoAndCentreId(String accountId, String centreId);
}

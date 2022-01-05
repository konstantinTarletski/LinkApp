package lv.bank.cards.core.cms.dao;

import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdClient;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDAO extends DAO {

    List<IzdAccount> findByIzdClientAndExternalNo(String clientId, String externalNo);

    IzdAccount findByAccountNo(BigDecimal accountNo);

}

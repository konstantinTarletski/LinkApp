package lv.bank.cards.core.linkApp.dao;

import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.entity.linkApp.PcdCondAccntPK;
import lv.bank.cards.core.entity.linkApp.PcdMerchant;
import lv.bank.cards.core.utils.DataIntegrityException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountsDAO extends DAO {

    String getNewCardAcctId(String externalId) throws DataIntegrityException;

    PcdAccount findByAccountNo(BigDecimal accountNo);

    PcdAccount getAccountByCoreAccountNo(String coreAccountNo, String country);

    List<PcdAccount> getActiveAccountsByCif(String cif, String country);

    PcdMerchant findByRegNr(String regNr);

    PcdAccount getAccountByCardNumber(String cardNumber);

    PcdCondAccnt getCondAccntByComp_Id(PcdCondAccntPK comp_Id);
}

package lv.bank.cards.core.cms.dao;

import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.utils.DataIntegrityException;

public interface ClientDAO extends DAO {

    IzdClient findByCif(String cif) throws DataIntegrityException;

    IzdClient findByCif(String cif, String account, String country) throws DataIntegrityException;

    String findClientNoByCardNo(String cardNo);
}

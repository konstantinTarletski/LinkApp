package lv.bank.cards.core.rtps.dao;

import lv.bank.cards.core.entity.rtps.StipCard;
import lv.bank.cards.core.utils.DataIntegrityException;

public interface CardDAO extends DAO {

    StipCard findByCardNumber(String cardNumber) throws DataIntegrityException;

    StipCard load(String card, String centreId);

}

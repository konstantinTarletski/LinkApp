package lv.bank.cards.core.rtps.dao;

import lv.bank.cards.core.entity.rtps.CardsException;
import lv.bank.cards.core.utils.DataIntegrityException;

public interface CardsExceptionsDAO {
    CardsException findByCardNumber(String cardNumber) throws DataIntegrityException;
}

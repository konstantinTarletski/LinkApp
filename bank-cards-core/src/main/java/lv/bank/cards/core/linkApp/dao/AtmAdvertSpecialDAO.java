package lv.bank.cards.core.linkApp.dao;

import lv.bank.cards.core.entity.linkApp.PcdAtmAdvertSpecial;

public interface AtmAdvertSpecialDAO extends DAO {

    PcdAtmAdvertSpecial findAtmAdvertSpecial(String personalCode, String atmId, boolean question);
}

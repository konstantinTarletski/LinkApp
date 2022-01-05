package lv.bank.cards.core.linkApp.dao;

import lv.bank.cards.core.entity.linkApp.PcdRepLang;

public interface RepLangDAO extends DAO {
    PcdRepLang findByLangCode(String langCode);
}

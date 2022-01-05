package lv.bank.cards.core.linkApp.dao;

import lv.bank.cards.core.entity.linkApp.PcdMerchantPar;

import java.util.List;

public interface MerchantParDAO extends DAO {
    List<PcdMerchantPar> GetDisMerchantPar(String param);
}

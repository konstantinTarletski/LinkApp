package lv.bank.cards.core.linkApp.dao;

import lv.bank.cards.core.entity.linkApp.PcdAtmAdvert;

import java.util.List;

public interface AtmAdvertDAO extends DAO {

    List<PcdAtmAdvert> findTodayShownAds(String personalCode);

    PcdAtmAdvert findAtmAd(String personalCode, String atmId);
}

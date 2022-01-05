package lv.bank.cards.core.cms.dao;

import lv.bank.cards.core.entity.cms.IzdCardTokens;

import java.util.List;

public interface IzdCardTokensDAO extends DAO {

    List<IzdCardTokens> findAppleCardTokens(String card, String deviceId, int tokenStatus, String appleRequestorId);

    List<IzdCardTokens> findGoogleCardTokens(String card, String walletId, int tokenStatus, String googleRequestorId);

}

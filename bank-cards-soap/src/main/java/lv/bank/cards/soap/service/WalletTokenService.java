package lv.bank.cards.soap.service;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.IzdCardTokensDAO;
import lv.bank.cards.core.cms.dto.TokenStatus;
import lv.bank.cards.core.cms.impl.IzdCardTokensDAOHibernate;
import lv.bank.cards.core.entity.cms.IzdCardTokens;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenization;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.utils.VisaTokenServicesUtils;
import lv.bank.cards.soap.service.dto.TokenWalletDo;
import org.apache.commons.lang.StringUtils;

import java.util.List;

@Slf4j
public class WalletTokenService {

    public static final int TOKEN_STATUS = 3;

    public static final String DEVICE_TYPE_ANDROID = "android";
    public static final String DEVICE_TYPE_IOS = "ios";


    protected IzdCardTokensDAO izdCardTokensDAO;
    protected CardsDAO cardsDAO;
    protected CardService cardService;

    public WalletTokenService() {
        izdCardTokensDAO = new IzdCardTokensDAOHibernate();
        cardsDAO = new CardsDAOHibernate();
        cardService = new CardService();
    }

    public TokenWalletDo getWalletToken(String card, String deviceId, String walletId) {

        if (StringUtils.isBlank(walletId) && StringUtils.isBlank(deviceId)) {
            log.info("getWalletToken, walletId and deviceId are null, should be at least one of them");
            return null;
        }

        if (StringUtils.isNotBlank(walletId) && StringUtils.isNotBlank(deviceId)) {
            log.warn("getWalletToken, both walletId and deviceId are not null, but only one of them should be provided. Default is Google");
        }

        boolean eligible = VisaTokenServicesUtils.isBinEligible(card);
        if (eligible) {
            eligible = !cardService.isBlockedForDelivery(card);
        }

        TokenStatus status = TokenStatus.UNDEFINED;
        String refId = "";
        List<IzdCardTokens> cardTokens;

        if (StringUtils.isNotBlank(walletId)) {
            log.info("getWalletToken, searching IzdCardTokens for Google card tokens ");
            cardTokens = izdCardTokensDAO.findGoogleCardTokens(card, walletId, TOKEN_STATUS, LinkAppProperties.getVisaVtsRequestorIdGoogle());
        } else {
            log.info("getWalletToken, searching IzdCardTokens for Apple card tokens ");
            cardTokens = izdCardTokensDAO.findAppleCardTokens(card, deviceId, TOKEN_STATUS, LinkAppProperties.getVisaVtsRequestorIdApple());
        }
        log.info("getWalletToken, cardTokens size = {}", cardTokens.size());

        if (cardTokens.size() >= 1) {
            IzdCardTokens izdCardToken = cardTokens.get(0);
            status = TokenStatus.fromStatus(izdCardToken.getTokenStatus());
            refId = izdCardToken.getTokenRefId();
        } else {

            PcdCardPendingTokenization pcdCardPendingTokenization;
            if (StringUtils.isNotBlank(walletId)) {
                log.info("getWalletToken, searching PcdCardPendingTokenization for Google card tokens ");
                pcdCardPendingTokenization = cardsDAO.getPcdCardPendingTokenizationByWalletAccountId(card, walletId);
            } else {
                log.info("getWalletToken, searching PcdCardPendingTokenization for Apple card tokens ");
                pcdCardPendingTokenization = cardsDAO.getPcdCardPendingTokenizationByKey(card, deviceId);
            }

            if (pcdCardPendingTokenization != null) {
                status = TokenStatus.PENDING;
                refId = pcdCardPendingTokenization.getTokenRefId();
            }
        }

        TokenWalletDo cardToken = new TokenWalletDo();
        cardToken.setId(card);
        cardToken.setTokenEligible(eligible);
        cardToken.setTokenRefId(refId);
        cardToken.setTokenStatus(status.getName());
        return cardToken;
    }


}

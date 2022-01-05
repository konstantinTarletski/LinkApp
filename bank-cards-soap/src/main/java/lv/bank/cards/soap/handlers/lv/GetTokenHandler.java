package lv.bank.cards.soap.handlers.lv;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.WalletTokenService;
import lv.bank.cards.soap.service.dto.TokenWalletDo;
import org.apache.commons.lang.StringUtils;

@Slf4j
public class GetTokenHandler extends SubRequestHandler {

    protected WalletTokenService walletTokenService;

    public GetTokenHandler() {
        super();
        walletTokenService = new WalletTokenService();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String cardNumber = getStringFromNode("/do/card");
        String deviceId = getStringFromNode("/do/device-id");
        String walletId = getStringFromNode("/do/wallet-id");


        if (StringUtils.isBlank(cardNumber)) {
            throw new RequestFormatException("Missing card number.", this);
        }

        if (StringUtils.isBlank(deviceId) && StringUtils.isBlank(walletId)) {
            throw new RequestFormatException("Missing deviceId or walletId.", this);
        }

        if (StringUtils.isNotBlank(deviceId) && StringUtils.isNotBlank(walletId)) {
            throw new RequestFormatException("Either deviceId (iOS) or walletId (Android) should be provided in request, not both.", this);
        }

        ResponseElement response = createElement("token");

        TokenWalletDo token = walletTokenService.getWalletToken(cardNumber, deviceId, walletId);
        if (token != null) {
            response.createElement("eligible", String.valueOf(token.isTokenEligible()));
            response.createElement("status", token.getTokenStatus());
            response.createElement("ref-id", token.getTokenRefId());
        }
    }
}

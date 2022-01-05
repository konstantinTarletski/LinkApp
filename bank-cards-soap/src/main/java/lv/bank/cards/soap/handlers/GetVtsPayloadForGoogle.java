package lv.bank.cards.soap.handlers;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.AESEncrypter;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenization;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenizationPK;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.vendor.api.sonic.rest.dto.AddressDO;
import lv.bank.cards.core.vendor.api.sonic.rest.dto.SonicCardholderDO;
import lv.bank.cards.core.vendor.api.sonic.rest.service.SonicRestService;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GetVtsPayloadForGoogle extends SubRequestHandler {

    protected CardsDAO cardsDAO;
    protected SonicRestService sonic = new SonicRestService();

    public GetVtsPayloadForGoogle() {
        super();
        cardsDAO = new CardsDAOHibernate();
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        log.info("handle");
        final String card = getStringFromNode("/do/card");
        final String bankAppDeviceId = getStringFromNode("/do/bank-app-device-id");
        final String bankAppPushId = getStringFromNode("/do/bank-app-push-id");
        final String deviceType = getStringFromNode("/do/device-type"); //android / ios
        final String walletDeviceId = getStringFromNode("/do/wallet-device-id");
        final String walletAccountId = getStringFromNode("/do/wallet-account-id");

        if (card == null || card.equals("")) {
            throw new RequestProcessingException(ErrorConstants.invalidCardNumber, this);
        }

        if (walletDeviceId == null || walletDeviceId.equals("Please provide wallet device Id")) {
            throw new RequestProcessingException("", this);
        }

        PcdCard pcdCard = cardsDAO.findByCardNumber(card);
        if (pcdCard == null) {
            throw new RequestProcessingException("Card not find", this);
        }

        String jwe;
        SonicCardholderDO cardholderDo;
        AddressDO addressDo;

        try {
            cardholderDo = sonic.getCardholder(pcdCard.getIdCard(), pcdCard.getRegion());

            if (cardholderDo.getErrorCode() != null && !cardholderDo.getErrorCode().isEmpty()) {
                log.warn("handle: getCardholder sonic error");
                throw new RequestProcessingException("Sonic errorCode = " + cardholderDo.getErrorCode() +
                        " Sonic ErrorMessage = " + cardholderDo.getErrorMessage(), this);
            }

            addressDo = cardholderDo.getAddress();
            Map<String, Object> jweContent = getPayloadData(pcdCard, walletDeviceId, walletAccountId, addressDo);
            jwe = createJwe(jweContent, LinkAppProperties.getVisaVtsKeyGoogle(), LinkAppProperties.getVisaVtsSharedSecretGoogle());
        } catch (NoSuchAlgorithmException | JOSEException | IOException e) {
            log.warn("Error, while processing", e);
            throw new RequestProcessingException("Error, while processing = " + e.getMessage(), this);
        }

        //Encoded once more for MIB TODO remove if not needed
        //String encodedBase64 = new String(Base64.getEncoder().encode(jwe.getBytes()));

        createElement("opaque-payment-card", jwe);
        createElement("cardholder-name", pcdCard.getCardName());
        createElement("last-digits", CardUtils.getLast4Digits(pcdCard.getCard()));
        ResponseElement address = createElement("address");
        address.createElement("line1", addressDo.getLine1());
        address.createElement("line2", addressDo.getLine2());
        address.createElement("city", addressDo.getCity());
        address.createElement("area", addressDo.getState());
        address.createElement("country", addressDo.getCountryCode());
        address.createElement("postal-code", addressDo.getPostalCode());
        address.createElement("phone-number", cardholderDo.getPhoneNumber());

        PcdCardPendingTokenization cardPendingTokenization = cardsDAO.getPcdCardPendingTokenizationByKey(card, walletDeviceId);
        if (cardPendingTokenization != null) {
            log.info("handle, pcd_cards_pending_tokenization already exists = {}", cardPendingTokenization.getComp_id());
        } else {
            PcdCardPendingTokenizationPK pk = new PcdCardPendingTokenizationPK();
            pk.setWalletDeviceId(walletDeviceId);
            pk.setCard(card);

            cardPendingTokenization = new PcdCardPendingTokenization();
            cardPendingTokenization.setComp_id(pk);
        }

        cardPendingTokenization.setBankAppDeviceId(bankAppDeviceId);
        cardPendingTokenization.setBankAppPushId(bankAppPushId);
        cardPendingTokenization.setDeviceType(deviceType);
        cardPendingTokenization.setSource("mib");
        cardPendingTokenization.setWalletAccountId(walletAccountId);
        cardPendingTokenization.setRecDate(new Date());

        cardsDAO.saveOrUpdate(cardPendingTokenization);
    }

    protected Map<String, Object> getPayloadData(PcdCard pcdCard, String stableHardwareId, String walletAccountId, AddressDO addressDo) {
        Map<String, Object> jweContent = new HashMap<>();
        jweContent.put("accountNumber", pcdCard.getCard());
        jweContent.put("cvv2", pcdCard.getCvc21());
        jweContent.put("name", pcdCard.getCardName());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pcdCard.getExpiry1());

        Map<String, Object> expirationDate = new HashMap<>();
        expirationDate.put("month", getMonthIn2DigitFormat(pcdCard.getExpiry1()));
        expirationDate.put("year", getYearIn2DigitFormat(pcdCard.getExpiry1()));
        jweContent.put("expirationDate", expirationDate);

        Map<String, Object> provider = new HashMap<>();
        provider.put("clientAppID", "LuminorApp");
        provider.put("clientDeviceID", stableHardwareId);
        provider.put("clientWalletProvider", LinkAppProperties.getVisaVtsRequestorIdGoogle());
        provider.put("clientWalletAccountID", walletAccountId);
        provider.put("intent", "PUSH_PROV_MOBILE");
        provider.put("isIDnV", "true");
        jweContent.put("provider", provider);

        Map<String, Object> billingAddress = new HashMap<>();
        billingAddress.put("line1", addressDo.getLine1());
        billingAddress.put("line2", addressDo.getLine2());
        billingAddress.put("city", addressDo.getCity());
        billingAddress.put("state", addressDo.getState());
        billingAddress.put("postalCode", addressDo.getPostalCode());
        billingAddress.put("country", addressDo.getCountryCode());
        jweContent.put("billingAddress", billingAddress);

        return jweContent;
    }

    protected static String createJwe(Map<String, Object> payloadData, String apiKey, String sharedSecret) throws NoSuchAlgorithmException, JOSEException {

        long currentTime = new Date().getTime() / 1000;
        JWEHeader updatedHeader = new JWEHeader.Builder(JWEAlgorithm.A256GCMKW, EncryptionMethod.A256GCM)
                .keyID(apiKey)
                .type(JOSEObjectType.JOSE)
                .customParam("channelSecurityContext", "SHARED_SECRET")
                .customParam("iat", currentTime)
                .build();
        Payload payload = new Payload(payloadData);
        log.info("createJwe, payload = {}", payload);
        JWEObject jweObject = new JWEObject(updatedHeader, payload);
        byte[] key256 = getDigest(sharedSecret);
        AESEncrypter encrypter = new AESEncrypter(key256);
        jweObject.encrypt(encrypter);
        return jweObject.serialize();
    }

    protected static byte[] getDigest(String value) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(value.getBytes(StandardCharsets.UTF_8));
        return md.digest();
    }

    protected String getYearIn2DigitFormat(Date date) {
        DateFormat df = new SimpleDateFormat("yy");
        return df.format(date);
    }

    protected String getMonthIn2DigitFormat(Date date) {
        DateFormat df = new SimpleDateFormat("MM");
        return df.format(date);
    }

}

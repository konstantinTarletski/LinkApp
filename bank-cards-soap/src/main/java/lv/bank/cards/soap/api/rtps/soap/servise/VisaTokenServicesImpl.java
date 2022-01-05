package lv.bank.cards.soap.api.rtps.soap.servise;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenization;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenizationPK;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.vendor.api.sonic.rest.service.SonicRestService;
import lv.bank.cards.soap.api.rtps.soap.types.Field;
import lv.bank.cards.soap.api.rtps.soap.types.Notify;
import lv.bank.cards.soap.api.rtps.soap.types.NotifyResponse;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.handlers.SonicNotificationHandler;
import lv.bank.cards.soap.service.CardService;
import lv.bank.cards.soap.service.WalletTokenService;
import org.apache.commons.lang.StringUtils;
import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebService
@Stateless
//Need to be renamed to 'BankCardsWS', done in 'standalone.xml'
@WebContext(contextRoot = "BankCardsSoapWS", urlPattern = "/VisaTokenServices")
@HandlerChain(file = "/META-INF/handlers.xml")
@Slf4j
public class VisaTokenServicesImpl implements RtpsNotify {

    public static final String FLD_002 = "FLD_002";
    public static final String FLD_126 = "FLD_126";

    public static final int FLD_126_TAG_NAME_LENGTH = 4;
    public static final int FLD_126_TAG_VALUE_SIZE_LENGTH = 3;

    public static final String FLD_126_TAG_M124 = "M124";
    public static final String FLD_126_TAG_TSID = "TSID";
    public static final String FLD_126_TAG_TCOR = "TCOR";
    public static final String FLD_126_TAG_TOKN = "TOKN";
    public static final String FLD_126_TAG_TWAC = "TWAC";
    public static final String FLD_126_TAG_TRQI = "TRQI";
    public static final String FLD_126_TAG_TREF = "TREF";
    public static final String FLD_126_TAG_TTYP = "TTYP";

    public static final String FLD_126_TAG_M124_TA = "TA";
    public static final String FLD_126_TAG_M124_TC = "TC";

    public static final String FLD_126_TAG_TTYP_SECURE_ELEMENT_APPLE = "02";
    public static final String FLD_126_TAG_TTYP_PSEUDO_ACCOUNT_GOOGLE = "06";

    public static final String SOURCE_RTPS = "rtps";
    public static final String SOURCE_MIB = "mib";

    public static final String SONIC_NOTIFICATION_CODE = "MSG000778";

    protected CardsDAO cardsDAO;
    protected SonicRestService sonicRest;
    protected SonicNotificationHandler sonicNotification;

    public VisaTokenServicesImpl() {
        cardsDAO = new CardsDAOHibernate();
        sonicRest = new SonicRestService();
        sonicNotification = new SonicNotificationHandler();
    }

    @Override
    public NotifyResponse notify(Notify body) {
        log.info("notify BEGIN");
        if (body.getFields() == null || body.getFields().getItem() == null || body.getFields().getItem().isEmpty()) {
            log.info("No items to process");
            throw new RuntimeException("No items to process");
        }

        PcdCard card = getCardNumber(body.getFields().getItem());
        NotifyResponse response = new NotifyResponse();

        try {
            processFLD_126(card, body.getFields().getItem());
            log.info("notify processing finished");
            response.setName("OK");
        } catch (Exception e) {
            log.error("processFLD_126 Error", e);
            response.setName("ERROR");
        }
        return response;
    }

    protected PcdCard getCardNumber(List<Field> fields) {
        for (Field field : fields) {
            if (FLD_002.equals(field.getName())) {
                if (!CardUtils.cardCouldBeValid(field.getValue())) {
                    throw new RuntimeException("Missing value for FLD_002");
                }
                if (!StringUtils.isNumeric(field.getValue())) {
                    throw new RuntimeException("Wrong format for FLD_002");
                }
                return cardsDAO.findByCardNumber(field.getValue());
            }
        }
        return null;
    }

    protected void processFLD_126(PcdCard pcdCard, List<Field> fields) throws IOException, RequestProcessingException {

        for (Field field : fields) {
            if (FLD_126.equals(field.getName())) {
                final String messageIndicator = getTagValue(field.getValue(), FLD_126_TAG_M124);

                if (StringUtils.isBlank(messageIndicator)) {
                    log.info("processFLD_126, messageIndicator is blank");
                    return;
                }

                if (!FLD_126_TAG_M124_TA.equals(messageIndicator) && !FLD_126_TAG_M124_TC.equals(messageIndicator)) {
                    log.info("processFLD_126, messageIndicator '{}' is not for processing", messageIndicator);
                    return;
                }

                log.info("processFLD_126 :messageIndicator = {}, card = {}", messageIndicator, CardUtils.maskCardNumber(pcdCard.getCard()));

                String appleGoogleIndicator = getTagValue(field.getValue(), FLD_126_TAG_TRQI);
                String walletAccountId = getTagValue(field.getValue(), FLD_126_TAG_TWAC);
                String correlationId = getTagValue(field.getValue(), FLD_126_TAG_TCOR);
                String tokenReferenceNumber = getTagValue(field.getValue(), FLD_126_TAG_TREF);

                String deviceType;
                if (LinkAppProperties.getVisaVtsRequestorIdGoogle().equals(appleGoogleIndicator)) {
                    deviceType = WalletTokenService.DEVICE_TYPE_ANDROID;
                } else if (LinkAppProperties.getVisaVtsRequestorIdApple().equals(appleGoogleIndicator)) {
                    deviceType = WalletTokenService.DEVICE_TYPE_IOS;
                } else {
                    log.warn("processFLD_126: unknown appleGoogleIndicator = {}, card = {}", appleGoogleIndicator, CardUtils.maskCardNumber(pcdCard.getCard()));
                    return;
                }

                log.info("processFLD_126: Starting {} message processing", messageIndicator);

                //TA Should come first (1)
                if (FLD_126_TAG_M124_TA.equals(messageIndicator)) {
                    processTaMessage(field, pcdCard, correlationId, walletAccountId, deviceType, tokenReferenceNumber);
                }
                //TC Should come second (always after TA) (2)
                else if (FLD_126_TAG_M124_TC.equals(messageIndicator)) {
                    processTcMessage(field, pcdCard, correlationId, walletAccountId, deviceType, tokenReferenceNumber);
                }
                return;
            }
        }
    }

    protected void processTaMessage(Field field, PcdCard pcdCard, String correlationId, String walletAccountId, String deviceType, String tokenReferenceNumber) {
        String walletDeviceId = getTagValue(field.getValue(), FLD_126_TAG_TSID);

        PcdCardPendingTokenization cardPendingTokenization = cardsDAO.getPcdCardPendingTokenizationByKey(pcdCard.getCard(), walletDeviceId);

        if (cardPendingTokenization == null) {
            PcdCardPendingTokenizationPK pk = new PcdCardPendingTokenizationPK();
            pk.setWalletDeviceId(walletDeviceId);
            pk.setCard(pcdCard.getCard());

            cardPendingTokenization = new PcdCardPendingTokenization();
            cardPendingTokenization.setComp_id(pk);
            cardPendingTokenization.setSource(SOURCE_RTPS);

            log.info("processTaMessage : cardPendingTokenization is null, setting source to {}", SOURCE_RTPS);
        }

        cardPendingTokenization.setDeviceType(deviceType);
        cardPendingTokenization.setWalletAccountId(walletAccountId);
        cardPendingTokenization.setRecDate(new Date());
        cardPendingTokenization.setTokenRefId(tokenReferenceNumber);
        cardPendingTokenization.setCorrId(correlationId);
        cardsDAO.saveOrUpdate(cardPendingTokenization);
    }

    protected void processTcMessage(Field field, PcdCard pcdCard, String correlationId, String walletAccountId, String deviceType, String tokenReferenceNumber)
            throws IOException, RequestProcessingException {
        String tokenPan = getTagValue(field.getValue(), FLD_126_TAG_TOKN);
        String tokenType = getTagValue(field.getValue(), FLD_126_TAG_TTYP);

        PcdCardPendingTokenization cardPendingTokenization = cardsDAO.getPcdCardPendingTokenizationByCorrId(pcdCard.getCard(), correlationId);

        if (cardPendingTokenization == null) {
            log.warn("processTcMessage: pcd_cards_pending_tokenization not find correlationId = {}, card = {}",
                    correlationId, CardUtils.maskCardNumber(pcdCard.getCard()));
            return;
        }

        if (!FLD_126_TAG_TTYP_SECURE_ELEMENT_APPLE.equals(tokenType) && !FLD_126_TAG_TTYP_PSEUDO_ACCOUNT_GOOGLE.equals(tokenType)) {
            log.info("processFLD_126, tokenType '{}' is not a wallet token, will delete pending token entry for card = {}",
                    tokenType, CardUtils.maskCardNumber(pcdCard.getCard()));
            cardsDAO.delete(cardPendingTokenization);
            return;
        }

        cardPendingTokenization.setTokenPan(tokenPan);
        cardPendingTokenization.setDeviceType(deviceType);
        cardPendingTokenization.setWalletAccountId(walletAccountId);
        cardPendingTokenization.setRecDate(new Date());
        cardPendingTokenization.setTokenRefId(tokenReferenceNumber);
        cardsDAO.saveOrUpdate(cardPendingTokenization);

        if (SOURCE_MIB.equals(cardPendingTokenization.getSource()) && StringUtils.isNotBlank(cardPendingTokenization.getBankAppPushId())) {
            log.info("processTcMessage: sendPushNotification for card = {}", CardUtils.maskCardNumber(pcdCard.getCard()));
            sonicRest.sendPushNotification(pcdCard, cardPendingTokenization);
        } else {
            log.info("processTcMessage: sendSonicNotifications for card = {}", CardUtils.maskCardNumber(pcdCard.getCard()));
            sendSonicNotifications(pcdCard, deviceType);
        }
    }

    protected void sendSonicNotifications(PcdCard pcdCard, String deviceType) throws RequestProcessingException {
        List<SonicNotificationHandler.RecipientInfoHolder> recipients = new ArrayList<>();
        SonicNotificationHandler.RecipientInfoHolder holder = new SonicNotificationHandler.RecipientInfoHolder("card", pcdCard.getCard());

        List<SonicNotificationHandler.ParamInfoHolder> params = new ArrayList<>();
        params.add(new SonicNotificationHandler.ParamInfoHolder("%1", pcdCard.getRegion()));
        params.add(new SonicNotificationHandler.ParamInfoHolder("%2", CardUtils.getLast4Digits(pcdCard.getCard())));
        params.add(new SonicNotificationHandler.ParamInfoHolder("%3", getWalletName(deviceType)));

        holder.setParams(params);
        recipients.add(holder);

        sonicNotification.sendSonicNotifications(SONIC_NOTIFICATION_CODE, recipients);
        log.info("sendSonicNotifications: notifications send for card = {}", CardUtils.maskCardNumber(pcdCard.getCard()));
    }

    protected static String getWalletName(String deviceType) {
        if (WalletTokenService.DEVICE_TYPE_IOS.equals(deviceType)) {
            return "Apple Pay";
        } else if (WalletTokenService.DEVICE_TYPE_ANDROID.equals(deviceType)) {
            return "Google Pay";
        }
        return null;
    }

    protected static String getTagValue(String input, String tag) {
        int indexOfTag = input.indexOf(tag);
        if (indexOfTag > -1) {
            String lengthString = input.substring(indexOfTag + FLD_126_TAG_NAME_LENGTH,
                    indexOfTag + FLD_126_TAG_NAME_LENGTH + FLD_126_TAG_VALUE_SIZE_LENGTH);
            int length = Integer.parseInt(lengthString);

            int valueBegin = indexOfTag + FLD_126_TAG_NAME_LENGTH + FLD_126_TAG_VALUE_SIZE_LENGTH;
            return input.substring(valueBegin, Math.min(input.length(), valueBegin + length));
        }
        return null;
    }

}

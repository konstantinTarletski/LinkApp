package lv.bank.cards.soap.handlers.lv;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.IzdCardTokensDAO;
import lv.bank.cards.core.cms.impl.IzdCardTokensDAOHibernate;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dto.CardInfoDTO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.rtps.dao.StipRmsStoplistDAO;
import lv.bank.cards.core.rtps.impl.StipRmsStoplistDAOHibernate;
import lv.bank.cards.core.utils.AccountConditionsUtil;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.core.utils.lv.DeliveryDetailsHelper;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.exceptions.RequestProcessingSoftException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.WalletTokenService;
import lv.bank.cards.soap.service.dto.TokenWalletDo;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
public class CardInfoIB extends SubRequestHandler {

    private static final String DATE_FORMAT_FOR_CARD_INFO = "yyMM";

    protected IzdCardTokensDAO izdCardTokensDAO;
    protected CardsDAO cardsDAO;
    protected StipRmsStoplistDAO stipRmsStoplistDAO;
    protected WalletTokenService walletTokenService;

    public CardInfoIB() {
        super();
        izdCardTokensDAO = new IzdCardTokensDAOHibernate();
        cardsDAO = new CardsDAOHibernate();
        stipRmsStoplistDAO = new StipRmsStoplistDAOHibernate();
        walletTokenService = new WalletTokenService();
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String card = getStringFromNode("/do/card");
        String cif = getStringFromNode("/do/cif");
        String country = getStringFromNode("/do/country");
        String deviceId = getStringFromNode("/do/device-id");
        String walletId = getStringFromNode("/do/wallet-id");

        if (country == null || country.isEmpty()) {
            country = Constants.DEFAULT_COUNTRY_LV;
        }

        if (((card != null) && (cif != null)) || ((card == null) && (cif == null))) {
            throw new RequestFormatException("Specify card or cif tag", this);
        }

        if (StringUtils.isNotBlank(deviceId) && StringUtils.isNotBlank(walletId)) {
            throw new RequestFormatException("Either device-id (iOS) or wallet-id (Android) tag should be provided in request, not both", this);
        }

        List<CardInfoDTO> cards;
        if (cif != null) {
            cards = cardsDAO.getCardInfo(null, cif, country, true);
        } else {
            if (!CardUtils.cardCouldBeValid(card)) {
                throw new RequestProcessingSoftException(ErrorConstants.invalidCardNumber, this);
            }
            cards = cardsDAO.getCardInfo(card, null, null, false);
            if (cards.size() != 1) {
                throw new RequestProcessingSoftException(ErrorConstants.invalidCardNumber, this);
            }
        }

        DateFormat dateFormatCardInfo = new SimpleDateFormat(DATE_FORMAT_FOR_CARD_INFO);

        for (CardInfoDTO cardInfoDTO : cards) {

            log.info("processing card = {}", cardInfoDTO);

            ResponseElement cardInfo = createElement("card-info");
            if (cardInfoDTO != null) {
                cardInfo.createElement("prefix-desc", cardInfoDTO.getPrefixDesc());
                cardInfo.createElement("card", cardInfoDTO.getCard());
                cardInfo.createElement("embossing-name", cardInfoDTO.getEmbossingName());
                cardInfo.createElement("card-name", cardInfoDTO.getCardName());
                cardInfo.createElement("expiry-date", (cardInfoDTO.getExpiryDate() == null) ? "" : dateFormatCardInfo.format(cardInfoDTO.getExpiryDate())).addAttribute("format", DATE_FORMAT_FOR_CARD_INFO);
                cardInfo.createElement("expiry-date2", (cardInfoDTO.getExpiryDate2() == null) ? "" : dateFormatCardInfo.format(cardInfoDTO.getExpiryDate2())).addAttribute("format", DATE_FORMAT_FOR_CARD_INFO);
                cardInfo.createElement("cvc", cardInfoDTO.getCvc());
                cardInfo.createElement("p-cif", cardInfoDTO.getCif());
                cardInfo.createElement("billing-currency", cardInfoDTO.getBillingCurrency());
                cardInfo.createElement("account-number", cardInfoDTO.getAccountNumber());
                cardInfo.createElement("card-group", cardInfoDTO.getGroupc());
                cardInfo.createElement("end-bal", (cardInfoDTO.getEndBal() == null) ? "" : cardInfoDTO.getEndBal().toString());
                cardInfo.createElement("person-code", cardInfoDTO.getClientPersonCode());
                cardInfo.createElement("person-code-card-holder", cardInfoDTO.getPersonCode());
                cardInfo.createElement("card-status1", cardInfoDTO.getCardStatus1());
                PcdCondAccnt accountConditions = cardsDAO.findAccountConditionsByCardNumber(cardInfoDTO.getCard());
                if (accountConditions != null) {
                    BigDecimal creditInterestRate = AccountConditionsUtil.getCreditInterestRate(accountConditions);
                    cardInfo.createElement("deb-intr", String.valueOf(creditInterestRate));
                }
                cardInfo.createElement("design-id", cardInfoDTO.getDesign());
                cardInfo.createElement("galactico", CardUtils.checkGalactico(cardInfoDTO.getCard()));
                PcdCard pc = cardsDAO.findByCardNumber(cardInfoDTO.getCard());
                Integer annFee = cardsDAO.findAnnualFee(pc.getCondSet(), cardInfoDTO.getBillingCurrency(), !pc.getBaseSupp().equals("1"));
                if (annFee != null) {
                    cardInfo.createElement("annual-fee", String.valueOf(annFee));
                }
                cardInfo.createElement("brand", CardUtils.getCardType(cardInfoDTO.getCard()));
                cardInfo.createElement("pin-delivery-status", cardInfoDTO.getUAField2());
                cardInfo.createElement("pin-block", cardInfoDTO.getPinBlock());
                cardInfo.createElement("password", cardInfoDTO.getPassword());
                cardInfo.createElement("application-number", cardInfoDTO.getUField8());
                cardInfo.createElement("auto-renew", "N".equals(cardInfoDTO.getRenew()) ? "0" : "1");
                cardInfo.createElement("contactless", cardInfoDTO.getContactless() == null ? "" : cardInfoDTO.getContactless().toString());
                cardInfo.createElement("language", cardInfoDTO.getLanguage());
                if (cardInfoDTO.getSmsFee() == null) {
                    cardInfo.createElement("sms-fee-conditions");
                } else {
                    cardInfo.createElement("sms-fee-conditions", cardInfoDTO.getSmsFee());
                }

                populateDeliveryDetails(cardInfoDTO, cardInfo);
                TokenWalletDo token = walletTokenService.getWalletToken(cardInfoDTO.getCard(), deviceId, walletId);
                if (token != null) {
                    ResponseElement tokenResponseElement = cardInfo.createElement("token");
                    tokenResponseElement.createElement("eligible", String.valueOf(token.isTokenEligible()));
                    tokenResponseElement.createElement("status", token.getTokenStatus());
                    tokenResponseElement.createElement("ref-id", token.getTokenRefId());
                }
            }
        }
    }

    protected void populateDeliveryDetails(CardInfoDTO cardInfoDTO, ResponseElement cardInfo) {
        DeliveryDetailsHelper details = new DeliveryDetailsHelper(cardInfoDTO.getUBField1());
        ResponseElement detailElement = cardInfo.createElement("delivery-details");
        detailElement.createElement("dlv_address", details.getAddressString());
        detailElement.createElement("dlv_addr_country", details.getCountry());
        detailElement.createElement("dlv_addr_city", details.getRegion());
        detailElement.createElement("dlv_addr_street1", details.getCity());
        detailElement.createElement("dlv_addr_street2", details.getAddress());
        detailElement.createElement("dlv_addr_zip", details.getZipCode());
        detailElement.createElement("dlv_company", details.getAdditionalFields());
        detailElement.createElement("dlv_language", details.getLanguage());
        detailElement.createElement("branch", cardInfoDTO.getStripedUCod10());
    }
}

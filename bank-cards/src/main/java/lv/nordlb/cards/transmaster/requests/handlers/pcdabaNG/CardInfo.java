package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.linkApp.dto.CardInfoDTO;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.utils.lv.DeliveryDetailsHelper;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.exceptions.RequestProcessingSoftException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CardInfo extends SubRequestHandler {

    protected PcdabaNGManager pcdabaNGManager;
    public final String dateFormatForCardInfo = "yyMM";

    public CardInfo() throws RequestPreparationException {
        super();
        try {
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String card = getStringFromNode("/do/card");
        String cif = getStringFromNode("/do/cif");
        String country = getStringFromNode("/do/country");
        if (country == null || country.isEmpty()) {
            country = Constants.DEFAULT_COUNTRY_LV;
        }

        if (((card != null) && (cif != null)) || ((card == null) && (cif == null)))
            throw new RequestFormatException("Specify card or cif tag", this);
        List<CardInfoDTO> cards;

        if (cif != null) {
            cards = pcdabaNGManager.getCardInfoByCif(cif, country, false);
        } else {
            if (!CardUtils.cardCouldBeValid(card))
                throw new RequestProcessingSoftException(ErrorConstants.invalidCardNumber, this);
            cards = new ArrayList<>(1);
            CardInfoDTO cardInfoDTO = pcdabaNGManager.getCardInfo(card);
            if (cardInfoDTO != null) {
                cards.add(cardInfoDTO);
            } else
                throw new RequestProcessingSoftException(ErrorConstants.invalidCardNumber, this);
        }

        DateFormat dateFormatCardInfo = new SimpleDateFormat(this.dateFormatForCardInfo);

        for (CardInfoDTO cardInfoDTO : cards) {
            ResponseElement cardInfo = createElement("card-info");
            if (cardInfoDTO != null) {
                cardInfo.createElement("prefix-desc", cardInfoDTO.getPrefixDesc());
                cardInfo.createElement("card", cardInfoDTO.getCard());
                cardInfo.createElement("embossing-name", cardInfoDTO.getEmbossingName());
                cardInfo.createElement("card-name", cardInfoDTO.getCardName());
                cardInfo.createElement("expiry-date", (cardInfoDTO.getExpiryDate() == null) ? "" : dateFormatCardInfo.format(cardInfoDTO.getExpiryDate())).addAttribute("format", dateFormatForCardInfo);
                cardInfo.createElement("expiry-date2", (cardInfoDTO.getExpiryDate2() == null) ? "" : dateFormatCardInfo.format(cardInfoDTO.getExpiryDate2())).addAttribute("format", dateFormatForCardInfo);
                cardInfo.createElement("cvc", cardInfoDTO.getCvc());
                cardInfo.createElement("p-cif", cardInfoDTO.getCif());
                cardInfo.createElement("billing-currency", cardInfoDTO.getBillingCurrency());
                cardInfo.createElement("account-number", cardInfoDTO.getAccountNumber());
                cardInfo.createElement("card-group", cardInfoDTO.getGroupc());
                cardInfo.createElement("end-bal", (cardInfoDTO.getEndBal() == null) ? "" : cardInfoDTO.getEndBal().toString());
                cardInfo.createElement("card-status1", cardInfoDTO.getCardStatus1());
                cardInfo.createElement("person-code", cardInfoDTO.getPersonCode());
                cardInfo.createElement("client-person-code", cardInfoDTO.getClientPersonCode());
                cardInfo.createElement("card-status1", cardInfoDTO.getCardStatus1());
                cardInfo.createElement("pin-delivery-status", cardInfoDTO.getUAField2());
                cardInfo.createElement("password", cardInfoDTO.getPassword());
                cardInfo.createElement("distrib-mode", cardInfoDTO.getDistribMode());
                ResponseElement distribAddress = cardInfo.createElement("distrib-address");
                distribAddress.createElement("street", cardInfoDTO.getDStreet());
                distribAddress.createElement("city", cardInfoDTO.getDCity());
                distribAddress.createElement("country", cardInfoDTO.getDCountry());
                distribAddress.createElement("post-index", cardInfoDTO.getDPostInd());
                distribAddress.createElement("branch", cardInfoDTO.getStripedDBranch());
                cardInfo.createElement("auto-renew", "N".equals(cardInfoDTO.getRenew()) ? "0" : "1");
                cardInfo.createElement("contactless", cardInfoDTO.getContactless() == null ? "" : cardInfoDTO.getContactless().toString());
                if (cardInfoDTO.getSmsFee() == null)
                    cardInfo.createElement("sms-fee-conditions");
                else
                    cardInfo.createElement("sms-fee-conditions", cardInfoDTO.getSmsFee());
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
    }
}

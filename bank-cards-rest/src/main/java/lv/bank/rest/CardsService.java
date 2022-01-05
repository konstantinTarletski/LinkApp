package lv.bank.rest;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdAccumulator;
import lv.bank.cards.core.entity.linkApp.PcdBranch;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.entity.linkApp.PcdCondAccntPK;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.entity.rtps.StipRmsStoplist;
import lv.bank.cards.core.utils.AccountConditionsUtil;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.utils.lv.DeliveryDetailsHelper;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateCardXML;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateDBWork;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.sonic.rest.dto.SonicCardholderDO;
import lv.bank.cards.core.vendor.api.sonic.rest.service.SonicRestService;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.handlers.SonicNotificationHandler;
import lv.bank.cards.soap.service.CardService;
import lv.bank.cards.soap.service.WalletTokenService;
import lv.bank.cards.soap.service.dto.TokenWalletDo;
import lv.bank.rest.dto.CardAutoRenewalDO;
import lv.bank.rest.dto.CardBalanceDO;
import lv.bank.rest.dto.CardBalanceListDO;
import lv.bank.rest.dto.CardCreditDetailsDO;
import lv.bank.rest.dto.CardCvcDO;
import lv.bank.rest.dto.CardDeliveryDetailsWrapperDO;
import lv.bank.rest.dto.CardDetailsDO;
import lv.bank.rest.dto.CardInfoDO;
import lv.bank.rest.dto.CardLimitsDO;
import lv.bank.rest.dto.CardListDO;
import lv.bank.rest.dto.CardNumberDetailsDO;
import lv.bank.rest.dto.CardStatusDO;
import lv.bank.rest.dto.CardTokenDO;
import lv.bank.rest.dto.ContactlessStatusDO;
import lv.bank.rest.dto.EActivationStatus;
import lv.bank.rest.dto.ECardStatus;
import lv.bank.rest.dto.ECardType;
import lv.bank.rest.dto.ECommerceStatus;
import lv.bank.rest.dto.EContactlessStatus;
import lv.bank.rest.dto.EDeliveryType;
import lv.bank.rest.dto.EProduct;
import lv.bank.rest.dto.ESpecialClientCategory;
import lv.bank.rest.dto.EcommStatusDO;
import lv.bank.rest.dto.VisaTokenServiceOtpDO;
import lv.bank.rest.dto.VisaTokenServicePhoneDO;
import lv.bank.rest.exception.BusinessException;
import lv.bank.rest.exception.JsonErrorCode;
import lv.bank.rest.mapper.ReservationMapper;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;
import lv.nordlb.cards.transmaster.bo.interfaces.CommonManager;
import lv.nordlb.cards.transmaster.fo.interfaces.StipCardManager;
import lv.nordlb.cards.transmaster.fo.interfaces.TMFManager;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lv.bank.cards.core.utils.TextUtils.removeEscapeCharacter;
import static lv.bank.rest.exception.JsonErrorCode.ACCOUNT_NOT_FOUND;
import static lv.bank.rest.exception.JsonErrorCode.BAD_REQUEST;

@Slf4j
@Stateless
public class CardsService {

    private static final String ECOMM_RULE = "FLD_022 %% '......[JSTUVW].*'&&(FLD_041!%'PBANK.*'||!FLD_041)&&(2==2)";
    private static final String ECOMM_ACTION = "108";
    private static final String ECOMM_COMMENT = "E-commerce blocked by LinkApp";

    private static final String ACTIVATE_ACTION = "100";

    private static final String OWNER_BLOCK_RULE = "FLD_041!%'PBANK.*'||!FLD_041";
    private static final String OWNER_BLOCK_ACTION = "100";
    private static final String OWNER_BLOCK_COMMENT = "Owner block from LinkApp";

    static final List<String> RENEW_ALLOWED_STATUSES = Arrays.asList("J", "E", "D", "R", "G");

    public static final int CONTACTLESS_BLOCKED = 0;
    public static final int CONTACTLESS_ACTIVE = 1;
    public static final int CONTACTLESS_PENDING_ACTIVATION = 2;
    public static final int CONTACTLESS_PENDING_BLOCKING = 3;

    public static final String CONTACTLESS_CHIP_TAG = "BF5B";
    public static final String CONTACTLESS_ENABLED = "DF01020000";
    public static final String CONTACTLESS_DISABLED = "DF0102C000";

    public static final String LIMITS_CASH = "Cash";
    public static final String LIMITS_SALES = "Sales";

    private static final int GRACE_PERIOD_INCREMENTER = 30;

    @Inject
    protected PcdabaNGManager pcdabaNGManager;

    @Inject
    protected CMSSoapAPIWrapper cmsSoapWrapper;

    @Inject
    protected TMFManager tmfManager;

    @Inject
    protected StipCardManager stipCardManager;

    @Inject
    protected CardManager cardManager;

    @Inject
    protected CommonManager commonManager;

    protected RTPSCallAPIWrapper rtpsWrapper;
    protected CMSCallAPIWrapper cmsWrapper;
    protected CardService cardService;
    protected WalletTokenService walletTokenService;

    private static final ThreadLocal<DateFormat> CARD_INFO_DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("MM/yy"));
    private static final ThreadLocal<DateFormat> RMS_RULE_DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyMM"));

    public CardsService() {
        rtpsWrapper = new RTPSCallAPIWrapper();
        cmsWrapper = new CMSCallAPIWrapper();
        cardService = new CardService();
        walletTokenService = new WalletTokenService();
    }

    public CardListDO readCustomerCards(String customerId, String country) {
        final CardListDO list = new CardListDO();
        if (!StringUtils.isBlank(customerId)) {
            List<PcdCard> clients = pcdabaNGManager.getClientsCardsByCifOrPersonCode(customerId, country);
            clients.forEach(card -> {
                try {
                    PcdAccount account = getAccount(card.getCard());

                    EActivationStatus activationStatus = cardService.isBlockedForDelivery(card.getCard())
                            ? EActivationStatus.INACTIVE : EActivationStatus.ACTIVE;

                    CardInfoDO cardInfoDO = CardInfoDO.builder()
                            .id(card.getCard())
                            .pan(ReservationMapper.panMask(card.getCard()))
                            .cardStatus(getCardStatus(card))
                            .product(getProduct(card.getCard()))
                            .type(getCardType(card.getCard()))
                            .expirationDate(card.getExpiry1() == null ? null : CARD_INFO_DATE_FORMAT.get().format(card.getExpiry1()))
                            .holderName(card.getCardName())
                            .personalCode(card.getIdCard())
                            .pinStatus(card.getUAField2())
                            .companyName(card.getCmpgName())
                            .coreAccountNo(account.getPcdAccParam().getUfield5())
                            .designId(card.getDesign())
                            .ecom(getEcomCommerceStatus(card))
                            .contactless(EContactlessStatus.fromIndex(card.getContactless()))
                            .activationStatus(activationStatus)
                            .autoRenewal(getAutoRenewal(card))
                            .build();
                    cardInfoDO.setIsCardholder(customerId);

                    PcdBranch branch = pcdabaNGManager.getBranch(card.getUCod10());
                    if (branch != null) {
                        String striped = StringUtils.stripStart(branch.getExternalId(), "0");
                        cardInfoDO.setBranch((StringUtils.isBlank(striped) && !StringUtils.isBlank(branch.getExternalId())) ? "0" : striped);
                    }
                    list.getCards().add(cardInfoDO);
                } catch (BusinessException e) {
                    log.error("readCustomerCards", e);
                }
            });
        }
        return list;
    }

    public CardCreditDetailsDO getCardCreditDetails(String cardNumber) throws BusinessException {
        CardCreditDetailsDO details = new CardCreditDetailsDO();
        PcdCard card = getCard(cardNumber);
        PcdAccount account = getAccount(card.getCard());
        details.setAccountNo(account.getAccountNo());
        details.setCurrency(account.getPcdAccParam().getPcdCurrency().getIsoAlpha());
        details.setCreditLimit(BigDecimal.valueOf(account.getPcdAccParam().getCrd() / Math.pow(10, Double.parseDouble(account.getPcdAccParam().getPcdCurrency().getExp()))));
        CardBalanceListDO balances = getCardBalances(card.getCard());
        if (balances.getCardBalances().size() > 0) {
            details.setAvailableBalance(balances.getCardBalances().get(0).getAvailableBalance());
        } else {
            details.setAvailableBalance(BigDecimal.ZERO);
        }
        BigDecimal usedCredit = details.getAvailableBalance().subtract(details.getCreditLimit());
        if (usedCredit.compareTo(BigDecimal.ZERO) == -1) {
            details.setUsedCredit(usedCredit.abs());
        } else {
            details.setUsedCredit(BigDecimal.ZERO);
        }

        PcdCondAccntPK comp_id = PcdCondAccntPK.builder()
                .bankC(card.getBankC())
                .groupc(card.getGroupc())
                .condSet(account.getPcdAccParam().getCondSet())
                .ccy(account.getPcdAccParam().getPcdCurrency().getIsoAlpha())
                .build();
        PcdCondAccnt condAccnt = pcdabaNGManager.getCondAccntByComp_Id(comp_id);

        int gracePeriodDateOfMonth = getCondAccntByComp_Id(comp_id).getDueDate1();
        if (gracePeriodDateOfMonth > 0) {
            gracePeriodDateOfMonth += GRACE_PERIOD_INCREMENTER;
        }
        details.setGracePeriod(Integer.toString(gracePeriodDateOfMonth));
        details.setCoreAccountNo(account.getPcdAccParam().getUfield5());
        details.setOpeningBalance(BigDecimal.valueOf(account.getBeginBal() / Math.pow(10, Double.parseDouble(account.getPcdAccParam().getPcdCurrency().getExp()))));
        details.setInterestRate(AccountConditionsUtil.getCreditInterestRate(condAccnt));
        return details;
    }

    //pk and cif - customerId
    public CardDetailsDO getCardDetails(String customerId, String cardNumber, String country) throws BusinessException {
        PcdCard card = getCard(cardNumber);
        PcdAccount account = getAccount(cardNumber);

        CardDetailsDO details = CardDetailsDO.builder()
                .id(card.getCard())
                .pan(ReservationMapper.panMask(card.getCard()))
                .cardStatus(getCardStatus(card))
                .product(getProduct(card.getCard()))
                .type(getCardType(card.getCard()))
                .expirationDate(card.getExpiry1() == null ? null : CARD_INFO_DATE_FORMAT.get().format(card.getExpiry1()))
                .holderName(card.getCardName())
                .contactless(EContactlessStatus.fromIndex(card.getContactless()))
                .build();


        ECommerceStatus comerceStatus = getEcomCommerceStatus(card);
        EActivationStatus activationStatus = cardService.isBlockedForDelivery(card.getCard())
                ? EActivationStatus.INACTIVE : EActivationStatus.ACTIVE;


        details.setActivationStatus(activationStatus);
        details.setEcom(comerceStatus);
        details.setDesignId(card.getDesign());
        PcdBranch branch = pcdabaNGManager.getBranch(card.getUCod10());
        if (branch != null) {
            String striped = StringUtils.stripStart(branch.getExternalId(), "0");
            details.setBranch((StringUtils.isBlank(striped) && !StringUtils.isBlank(branch.getExternalId())) ? "0" : striped);
        }
        details.setPinStatus(card.getUAField2());
        details.setPersonalCode(card.getIdCard());
        details.setIsCardholder(customerId);
        details.setCompanyName(card.getCmpgName());
        details.setCoreAccountNo(account.getPcdAccParam().getUfield5());
        details.setAutoRenewal(getAutoRenewal(card));
        if (card.getUCod9() != null) {
            if (card.getUCod9().equals("006")) {
                details.setSpecialClientCategory(ESpecialClientCategory.CHILD);
            } else if (card.getUCod9().equals("007")) {
                details.setSpecialClientCategory(ESpecialClientCategory.TEENAGER);
            }
        }
        return details;
    }

    public CardBalanceListDO getCardBalances(String cardNumber) throws BusinessException {
        PcdCard card = getCard(cardNumber);
        CardBalanceListDO balances = new CardBalanceListDO();

        String centreId = CardUtils.composeCentreIdFromPcdCard(card);
        List<StipAccount> accounts = null;
        try {
            accounts = tmfManager.findStipAccountsByCardNumberAndCentreId(card.getCard(), centreId);
        } catch (DataIntegrityException e) {
            log.warn("Error connecting to RTPS", e);
        }
        if (accounts != null) {
            for (StipAccount acc : accounts) {
                CardBalanceDO balance = new CardBalanceDO();
                balance.setCurrency(acc.getCurrencyCode().getCcyAlpha());
                Long lockedAmount = tmfManager.getLockedAmountByStipAccount(acc);
                if (lockedAmount == null) {
                    lockedAmount = 0L;
                }
                BigDecimal amtLocked = BigDecimal.valueOf(lockedAmount)
                        .divide(BigDecimal.valueOf(Math.pow(10, acc.getCurrencyCode().getExpDot().doubleValue())));
                BigDecimal otb = BigDecimal.valueOf(acc.getInitialAmount())
                        .add(BigDecimal.valueOf(acc.getBonusAmount())).subtract(BigDecimal.valueOf(lockedAmount))
                        .divide(BigDecimal.valueOf(Math.pow(10, acc.getCurrencyCode().getExpDot().doubleValue())));
                balance.setAvailableBalance(otb);
                balance.setReservedAmount(amtLocked);
                balances.getCardBalances().add(balance);
            }
        }
        return balances;
    }

    public CardNumberDetailsDO getCardNumberDetails(String cardNumber) throws BusinessException {
        PcdCard card = getCard(cardNumber);
        return CardNumberDetailsDO.builder()
                .cardNumber(card.getCard())
                .expiry(card.getExpiry1() == null ? null : CARD_INFO_DATE_FORMAT.get().format(card.getExpiry1()))
                .holderName(card.getCardName())
                .cvc(card.getCvc21())
                .build();
    }

    public CardStatusDO getCardStatusDO(String cardNumber) throws BusinessException {
        PcdCard card = getCard(cardNumber);
        CardStatusDO details = new CardStatusDO();
        details.setCardStatus(getCardStatus(card));
        return details;
    }

    public CardStatusDO changeCardStatus(String cardNumber, CardStatusDO status) throws BusinessException {
        PcdCard card = getCard(cardNumber);
        ECardStatus current = getCardStatus(card);

        switch (status.getCardStatus()) {
            case ACTIVE:
                if (!ECardStatus.FROZEN.equals(current)) {
                    throw BusinessException.create(JsonErrorCode.DATA_CONFLICT, "cardStatus",
                            "Only blocked card can be activated");
                }
                if ("1".equals(card.getStatus1())) {
                    throw BusinessException.create(JsonErrorCode.DATA_CONFLICT, "cardStatus", "Card cannot be activated");
                }
                removeCardFromRMS(card, OWNER_BLOCK_RULE, OWNER_BLOCK_ACTION);
                break;
            case FROZEN:
                if (!ECardStatus.ACTIVE.equals(current)) {
                    throw BusinessException.create(JsonErrorCode.DATA_CONFLICT, "cardStatus",
                            "Only active card can be blocked");
                }
                addCardToRMS(card, OWNER_BLOCK_RULE, OWNER_BLOCK_ACTION, OWNER_BLOCK_COMMENT);
                break;
            default:
                throw BusinessException.create(BAD_REQUEST, "cardStatus",
                        "Invalid card status " + status.getCardStatus());
        }

        return CardStatusDO.builder().cardStatus(getCardStatus(card)).build();
    }

    public CardAutoRenewalDO blockOrUnblockCardAutoRenewal(String cardNumber, CardAutoRenewalDO status) throws BusinessException {
        PcdCard card = getCard(cardNumber);
        boolean autoRenewal = getAutoRenewal(card);
        if (!status.isAutoRenewal()) {
            if (RENEW_ALLOWED_STATUSES.contains(card.getRenew())) {
                updateCardRenewStatusInCMS(card, "N");
                card.setRenewOld(card.getRenew());
                card.setRenew("N");
                autoRenewal = getAutoRenewal(card);
            } else {
                throw BusinessException.create(JsonErrorCode.DATA_CONFLICT, "cardRenew", "Change of status is prohibited during card embossing / PIN reminder processing");
            }
        } else {
            String newStatus = card.getRenewOld() == null ? "J" : card.getRenewOld();
            updateCardRenewStatusInCMS(card, newStatus);
            card.setRenew(newStatus);
            autoRenewal = getAutoRenewal(card);
        }
        return CardAutoRenewalDO.builder().autoRenewal(autoRenewal).build();
    }

    private boolean getAutoRenewal(PcdCard card) {
        return !card.getRenew().equals("N");
    }

    public CardDeliveryDetailsWrapperDO getCardDeliveryDetails(String cardNumber) throws BusinessException {
        CardDeliveryDetailsWrapperDO details = new CardDeliveryDetailsWrapperDO();
        PcdCard card = getCard(cardNumber);
        if (card.getUBField1() != null) {
            DeliveryDetailsHelper detailsPcd = new DeliveryDetailsHelper(card.getUBField1());
            boolean escape = true;
            details.getDeliveryDetails().setCountry(removeEscapeCharacter(detailsPcd.getCountry(), escape));
            details.getDeliveryDetails().setCityRegion(removeEscapeCharacter(detailsPcd.getRegion(), escape));
            details.getDeliveryDetails().setVillageDistrict(removeEscapeCharacter(detailsPcd.getCity(), escape));
            details.getDeliveryDetails().setStreet(removeEscapeCharacter(detailsPcd.getAddress(), escape));
            details.getDeliveryDetails().setZip(removeEscapeCharacter(detailsPcd.getZipCode(), escape));
            details.getDeliveryDetails().setLanguage(getLangCodeFromId(removeEscapeCharacter(detailsPcd.getLanguage(), escape)));
            PcdBranch branch = pcdabaNGManager.getBranch(card.getUCod10());
            if (branch != null) {
                String striped = StringUtils.stripStart(branch.getExternalId(), "0");
                details.getDeliveryDetails().setBranch((StringUtils.isBlank(striped) && !StringUtils.isBlank(branch.getExternalId())) ? "0" : striped);
                if (!details.getDeliveryDetails().getBranch().equals("888")) {
                    details.getDeliveryDetails().setDeliveryType(EDeliveryType.BRANCH);
                } else {
                    if (!card.getRegion().equals(detailsPcd.getCountry())) {
                        details.getDeliveryDetails().setDeliveryType(EDeliveryType.ABROAD);
                    } else {
                        details.getDeliveryDetails().setDeliveryType(EDeliveryType.POST);
                    }
                }
            }
        }
        return details;
    }

    public CardDeliveryDetailsWrapperDO updateCardDeliveryDetails(String cardNumber, CardDeliveryDetailsWrapperDO newDetails) throws BusinessException {
        if (!CardUtils.cardCouldBeValid(cardNumber)) {
            throw BusinessException.create(JsonErrorCode.INVALID_CARD_NUMBER, "cardNumber",
                    "Invalid card number");
        }
        if (StringUtils.isBlank(newDetails.getDeliveryDetails().getCountry()) && StringUtils.isBlank(newDetails.getDeliveryDetails().getCityRegion()) &&
                StringUtils.isBlank(newDetails.getDeliveryDetails().getVillageDistrict()) && StringUtils.isBlank(newDetails.getDeliveryDetails().getStreet()) &&
                StringUtils.isBlank(newDetails.getDeliveryDetails().getZip()) && StringUtils.isBlank(newDetails.getDeliveryDetails().getLanguage()) &&
                StringUtils.isBlank(newDetails.getDeliveryDetails().getBranch())) {
            throw BusinessException.create(BAD_REQUEST, "deliveryDetails",
                    "Do not have delivery details");
        }
        if (StringUtils.isBlank(newDetails.getDeliveryDetails().getBranch())) {
            throw BusinessException.create(BAD_REQUEST, "deliveryDetails",
                    "Delivery details branch is mandatory");
        }
        if ("888".equals(newDetails.getDeliveryDetails().getBranch())) {
            List<String> missing = new ArrayList<>();
            if (StringUtils.isBlank(newDetails.getDeliveryDetails().getCountry())) {
                missing.add("country");
            }
            if (StringUtils.isBlank(newDetails.getDeliveryDetails().getCityRegion())) {
                missing.add("region");
            }
            if (StringUtils.isBlank(newDetails.getDeliveryDetails().getStreet())) {
                missing.add("street address");
            }
            if (StringUtils.isBlank(newDetails.getDeliveryDetails().getZip())) {
                missing.add("ZIP code");
            }
            if (!missing.isEmpty()) {
                StringBuilder missingFields = new StringBuilder();
                for (String value : missing) {
                    if (missingFields.length() > 0) {
                        missingFields.append(", ");
                    }
                    missingFields.append(value);
                }
                throw BusinessException.create(BAD_REQUEST, "deliveryDetails",
                        "Missing value for delivery address " + missingFields);
            }
            if (StringUtils.isBlank(newDetails.getDeliveryDetails().getLanguage())) {
                throw BusinessException.create(BAD_REQUEST, "deliveryDetails",
                        "Missing value for delivery address language");
            }
        }
        PcdCard card = getCard(cardNumber);
        DeliveryDetailsHelper details;
        if (card.getUBField1() != null) {
            details = new DeliveryDetailsHelper(card.getUBField1());
            details.setCountry(newDetails.getDeliveryDetails().getCountry());
            details.setRegion(newDetails.getDeliveryDetails().getCityRegion());
            details.setCity(newDetails.getDeliveryDetails().getVillageDistrict());
            details.setAddress(newDetails.getDeliveryDetails().getStreet());
            details.setZipCode(newDetails.getDeliveryDetails().getZip());
            details.setLanguage(getLangIdFromCode(newDetails.getDeliveryDetails().getLanguage()));
        } else {
            details = new DeliveryDetailsHelper(newDetails.getDeliveryDetails().getLanguage(), newDetails.getDeliveryDetails().getCountry(),
                    newDetails.getDeliveryDetails().getCityRegion(), newDetails.getDeliveryDetails().getVillageDistrict(), newDetails.getDeliveryDetails().getStreet(),
                    newDetails.getDeliveryDetails().getZip(), null);
        }
        String branch = "";
        if (!StringUtils.isBlank(newDetails.getDeliveryDetails().getBranch())) {
            newDetails.getDeliveryDetails().setBranch(StringUtils.leftPad(newDetails.getDeliveryDetails().getBranch(), 2, "0"));
            if (commonManager != null) {
                branch = commonManager.getBranchIdByExternalId(newDetails.getDeliveryDetails().getBranch());
                if (branch == null) {
                    throw BusinessException.create(JsonErrorCode.APPLICATION_ERROR, "deliveryDetails",
                            "There is no branch in NORDLB_BRANCHES for " + newDetails.getDeliveryDetails().getBranch());
                }
            }
        }

        String errorMessage = details.checkLanguage();
        if (errorMessage.isEmpty()) errorMessage = details.checkCountry();
        if (errorMessage.isEmpty()) errorMessage = details.checkRegion();
        if (errorMessage.isEmpty()) errorMessage = details.checkCity();
        if (errorMessage.isEmpty()) errorMessage = details.checkAddress();
        if (errorMessage.isEmpty()) errorMessage = details.checkZipCode();
        if (!errorMessage.isEmpty()) {
            throw BusinessException.create(BAD_REQUEST, "deliveryDetails", errorMessage);
        }

        String detailsString = details.getDetails();

        // Update in CMS
        UpdateCardXML updateCardXML = cmsWrapper.new UpdateCardXML(card.getCard(), card.getBankC(), card.getGroupc());
        updateCardXML.setElement("U_BFIELD1", detailsString);
        updateCardXML.setElement("U_COD10", branch);
        UpdateDBWork work = cmsWrapper.new UpdateDBWork();
        work.setInputXML(updateCardXML.getDocument());
        if (cardManager != null) {
            String response = cardManager.doWork(work);
            if (response != null) {
                if (!"success".equals(response)) {
                    throw BusinessException.create(JsonErrorCode.APPLICATION_ERROR, "deliveryDetails",
                            "Cannot update card delivery details. " + response);
                }
            }
        }
        // Update in LinkApp
        card.setUBField1(detailsString);
        card.setUCod10(branch);
        pcdabaNGManager.saveOrUpdate(card);

        // Return from LinkApp
        return getCardDeliveryDetails(cardNumber);
    }

    private String getLangCodeFromId(String langId) {
        switch (langId) {
            case "1":
                return "EN";
            case "2":
                return "LV";
            case "3":
                return "RU";
            case "4":
                return "LT";
            case "8":
                return "ET";
        }
        log.warn("Unsupported langId in database [" + langId + "], will default to [1] (EN)");
        return "EN";
    }

    private String getLangIdFromCode(String langCode) throws BusinessException {
        switch (langCode.toUpperCase()) {
            case "EN":
                return "1";
            case "LV":
                return "2";
            case "RU":
                return "3";
            case "LT":
                return "4";
            case "ET":
                return "8";
            default:
                throw BusinessException.create(BAD_REQUEST, "deliveryDetails", "Unsupported lang code [" + langCode + "]");
        }
    }

    public CardCvcDO getCardCVC(String cardNumber) throws BusinessException {
        PcdCard card = getCard(cardNumber);
        CardCvcDO cvc = new CardCvcDO();
        cvc.setCvc(card.getCvc21());
        return cvc;
    }

    public void activateCard(String cardNumber) throws BusinessException {
        PcdCard card = getCard(cardNumber);

        String rule = "(FLD_041!%'PBANK.*'||!FLD_041)&&(FLD_014=='"
                + (card.getExpiry1() == null ? "" : RMS_RULE_DATE_FORMAT.get().format(card.getExpiry1())) + "')";
        removeCardFromRMS(card, rule, ACTIVATE_ACTION);
    }

    public EcommStatusDO changeEcomm(String cardNumber, EcommStatusDO status) throws BusinessException {
        PcdCard card = getCard(cardNumber);
        ECommerceStatus comerceStatus = getEcomCommerceStatus(card);
        if (comerceStatus.equals(status.getStatus())) {
            throw BusinessException.create(JsonErrorCode.DATA_CONFLICT, "ecomm",
                    "ECOMM status already is " + comerceStatus.getValue());
        }
        if (status.getStatus() == null) {
            throw BusinessException.create(BAD_REQUEST, "ecomm", "Invalid ecomm status");
        }

        switch (status.getStatus()) {
            case ALLOWED:
                // Activate
                removeCardFromRMS(card, ECOMM_RULE, ECOMM_ACTION);
                break;
            case NOT_ALLOWED:
                // Block
                addCardToRMS(card, ECOMM_RULE, ECOMM_ACTION, ECOMM_COMMENT);
                break;
            default:
                throw BusinessException.create(BAD_REQUEST, "ecomm", "Invalid ecomm status");
        }
        return EcommStatusDO.builder().status(getEcomCommerceStatus(card)).build();
    }

    public ContactlessStatusDO changeContactlessStatus(String cardNumber, ContactlessStatusDO status)
            throws BusinessException {
        PcdCard card = getCard(cardNumber);
        if (card.getContactless() != null && (card.getContactless().equals(CONTACTLESS_BLOCKED)
                || card.getContactless().equals(CONTACTLESS_ACTIVE))) {
            if (status.getStatus() == null) {
                throw BusinessException.create(BAD_REQUEST, "contactless", "Allowed values are "
                        + EContactlessStatus.ENABLED.getValue() + " and " + EContactlessStatus.DISABLED.getValue());
            }
            switch (status.getStatus()) {
                case ENABLED:
                    if (!EContactlessStatus.DISABLED.equals(EContactlessStatus.fromIndex(card.getContactless()))) {
                        throw BusinessException.create(JsonErrorCode.DATA_CONFLICT, "contactless",
                                "Only status " + EContactlessStatus.DISABLED.getValue() + " can be enabled");
                    }
                    try {
                        cmsWrapper.setChipTagValue(card.getCard(), CONTACTLESS_CHIP_TAG, CONTACTLESS_ENABLED);
                        card.setContactless(CONTACTLESS_PENDING_ACTIVATION);
                        pcdabaNGManager.saveOrUpdate(card);
                    } catch (CMSCallAPIException e) {
                        throw BusinessException.create(JsonErrorCode.APPLICATION_ERROR, "contactless", e,
                                "Did not manage to change cotactless status");
                    }
                    break;
                case DISABLED:
                    if (!EContactlessStatus.ENABLED.equals(EContactlessStatus.fromIndex(card.getContactless()))) {
                        throw BusinessException.create(JsonErrorCode.DATA_CONFLICT, "contactless",
                                "Only status " + EContactlessStatus.ENABLED.getValue() + " can be disabled");
                    }
                    try {
                        cmsWrapper.setChipTagValue(card.getCard(), CONTACTLESS_CHIP_TAG, CONTACTLESS_DISABLED);
                        card.setContactless(CONTACTLESS_PENDING_BLOCKING);
                        pcdabaNGManager.saveOrUpdate(card);
                    } catch (CMSCallAPIException e) {
                        throw BusinessException.create(JsonErrorCode.APPLICATION_ERROR, "contactless", e,
                                "Did not manage to change cotactless status");
                    }
                    break;
                default:
                    throw BusinessException.create(BAD_REQUEST, "contactless", "Allowed values are "
                            + EContactlessStatus.ENABLED.getValue() + " and " + EContactlessStatus.DISABLED.getValue());
            }
        } else {
            throw BusinessException.create(JsonErrorCode.DATA_CONFLICT, "contactless",
                    "Incorrect current contactless status " + (card.getContactless() == null ? "null"
                            : EContactlessStatus.fromIndex(card.getContactless()).getValue()));
        }
        return ContactlessStatusDO.builder().status(EContactlessStatus.fromIndex(card.getContactless())).build();
    }

    public CardLimitsDO getLimitsByRiskLevel(String riskLevel) {
        return getLimitByRiskLevel(riskLevel);
    }

    public CardLimitsDO getCardLimits(String cardNumber) throws BusinessException {
        PcdCard card = getCard(cardNumber);
        return getLimitByRiskLevel(card.getRiskLevel());
    }

    public CardLimitsDO setCardLimits(String cardNumber, CardLimitsDO limits) throws BusinessException {
        PcdCard card = getCard(cardNumber);
        try {
            cmsSoapWrapper.setRiskLevel(card.getCard(), limits.getId());
        } catch (CMSSoapAPIException e) {
            throw BusinessException.create(JsonErrorCode.APPLICATION_ERROR, "riskLevel",
                    "Cannot update card risk level: " + e.getMessage());
        }
        card.setRiskLevel(limits.getId());
        pcdabaNGManager.saveOrUpdate(card);

        PcdCard cardAfterUpdate = pcdabaNGManager.getCardByCardNumber(cardNumber);
        return getLimitByRiskLevel(cardAfterUpdate.getRiskLevel());
    }

    public VisaTokenServiceOtpDO sendOtp(VisaTokenServiceOtpDO otpDO) throws BusinessException {
        PcdCard pcdCard = getCard(otpDO.getCardNumber());
        try {
            SonicNotificationHandler sonic = new SonicNotificationHandler();
            String notificationCode = "MSG000777";
            List<SonicNotificationHandler.RecipientInfoHolder> recipients = new ArrayList<>();
            SonicNotificationHandler.RecipientInfoHolder holder = new SonicNotificationHandler.RecipientInfoHolder("card", pcdCard.getCard());

            List<SonicNotificationHandler.ParamInfoHolder> params = new ArrayList<>();
            params.add(new SonicNotificationHandler.ParamInfoHolder("%2", pcdCard.getRegion()));
            params.add(new SonicNotificationHandler.ParamInfoHolder("%3", otpDO.getOtp()));

            holder.setParams(params);
            recipients.add(holder);

            sonic.sendSonicNotifications(notificationCode, recipients);

            VisaTokenServiceOtpDO response = new VisaTokenServiceOtpDO();
            response.setOtp(otpDO.getOtp());
            return response;
        } catch (RequestProcessingException e) {
            log.warn("sendSonicNotifications", e);
            throw BusinessException.create(JsonErrorCode.APPLICATION_ERROR, "cardholder", e, e.getMessage());
        }
    }

    public VisaTokenServicePhoneDO getPhoneByCardNumber(String cardNumber) throws BusinessException {
        PcdCard pcdCard = getCard(cardNumber);
        SonicCardholderDO cardHolderDo;
        try {
            cardHolderDo = new SonicRestService().getCardholder(pcdCard.getIdCard(), pcdCard.getRegion());
        } catch (IOException e) {
            log.warn("getPhoneByCardNumber: getCardholder sonic error", e);
            throw BusinessException.create(JsonErrorCode.APPLICATION_ERROR, "cardholder", e, e.getMessage());
        }

        if (cardHolderDo.getErrorCode() != null && !cardHolderDo.getErrorCode().isEmpty()) {
            log.warn("getPhoneByCardNumber: getCardholder sonic error");
            switch (cardHolderDo.getErrorCode()) {
                case "2": {
                    throw BusinessException.create(JsonErrorCode.CUSTOMER_NOT_FOUND, "cardholder",
                            cardHolderDo.getErrorCode(), cardHolderDo.getErrorMessage());
                }
                default: {
                    throw BusinessException.create(JsonErrorCode.BAD_REQUEST, "cardholder",
                            cardHolderDo.getErrorCode(), cardHolderDo.getErrorMessage());
                }
            }
        }

        if (StringUtils.isBlank(cardHolderDo.getPhoneNumber())) {
            throw BusinessException.create(JsonErrorCode.NOT_FOUND, "cardholder","Phone number is not available in CRM");
        }

        VisaTokenServicePhoneDO response = new VisaTokenServicePhoneDO();
        response.setPhone(cardHolderDo.getPhoneNumber());
        return response;
    }

    private CardLimitsDO getLimitByRiskLevel(String riskLevel) {
        // Cash daily
        PcdAccumulator accCash = pcdabaNGManager.getAccumulator(riskLevel, LIMITS_CASH);
        BigDecimal daily = accCash == null || accCash.getAmountDay() == null ? null
                : BigDecimal.valueOf(accCash.getAmountDay()).multiply(new BigDecimal("0.01"));

        // Cash monthly
        BigDecimal monthly = accCash == null || accCash.getAmountWeek() == null ? null
                : BigDecimal.valueOf(accCash.getAmountWeek()).multiply(new BigDecimal("0.01"));

        // Sales daily
        PcdAccumulator accSales = pcdabaNGManager.getAccumulator(riskLevel, LIMITS_SALES);
        BigDecimal sales = accSales == null || accSales.getAmountDay() == null ? null
                : BigDecimal.valueOf(accSales.getAmountDay()).multiply(new BigDecimal("0.01"));

        return CardLimitsDO.builder().cashDaily(daily).cashMonthly(monthly).salesDaily(sales).id(riskLevel).build();
    }

    private PcdCard getCard(String cardNumber) throws BusinessException {
        if (StringUtils.isBlank(cardNumber)) {
            throw BusinessException.create(JsonErrorCode.INVALID_CARD_NUMBER, "cardNumber", "Invalid card number");
        }
        PcdCard card = pcdabaNGManager.getCardByCardNumber(cardNumber);
        if (card == null) {
            throw BusinessException.create(JsonErrorCode.CARD_NOT_FOUND, "cardNumber", "Card not found");
        }
        return card;
    }

    private ECardStatus getCardStatus(PcdCard card) {
        String centreId = CardUtils.composeCentreIdFromPcdCard(card);
        switch (card.getStatus1()) {
            case "0":
                List<StipRmsStoplist> list = stipCardManager.getStipRmsStoplist(card.getCard(), centreId, null);
                if (list != null && !list.isEmpty()) {
                    boolean isBlocked = false;
                    boolean isFrozen = false;
                    for (StipRmsStoplist rms : list) {
                        if (rms.getDescription() != null) {
                            if (rms.getDescription().startsWith("Card blocked till closure")) {
                                return ECardStatus.CLOSED;
                            } else if (rms.getDescription().startsWith("Deliquency block")) {
                                isBlocked = true;
                            } else if (rms.getDescription().startsWith("Card debit operations blocked")) {
                                isBlocked = true;
                            } else if (rms.getDescription().startsWith("Core Banking System dormant block")) {
                                isBlocked = true;
                            } else if (rms.getDescription().startsWith("Card blocked from Osiris")) {
                                isBlocked = true;
                            } else if (rms.getDescription().startsWith("Owner block")) {
                                isFrozen = true;
                            } else if (rms.getDescription().startsWith("Card blocked for delivery")) {
                            } else if (rms.getDescription().startsWith("E-commerce blocked")) {
                            } else {
                                isBlocked = true;
                            }
                        }
                    }
                    if (isBlocked) {
                        return ECardStatus.BLOCKED;
                    } else if (isFrozen) {
                        return ECardStatus.FROZEN;
                    }
                }
                return ECardStatus.ACTIVE;
            case "1":
                return ECardStatus.BLOCKED;
            case "2":
                return ECardStatus.CLOSED;
            default:
                return null;
        }
    }

    private void removeCardFromRMS(PcdCard card, String rule, String action) throws BusinessException {
        try {
            rtpsWrapper.RemoveCardFromRMSStop(CardUtils.composeCentreIdFromPcdCard(card), card.getCard(), rule,
                    null, action, null);
        } catch (RTPSCallAPIException e) {
            throw BusinessException.create(JsonErrorCode.APPLICATION_ERROR, "cardNumber",
                    "Cannot remove card block from RMS");
        }
    }

    private void addCardToRMS(PcdCard card, String rule, String action, String comment) throws BusinessException {
        try {
            rtpsWrapper.AddCardToRMSStop(CardUtils.composeCentreIdFromPcdCard(card), card.getCard(), rule, null,
                    action, comment);
        } catch (RTPSCallAPIException e) {
            throw BusinessException.create(JsonErrorCode.APPLICATION_ERROR, "cardNumber",
                    "Cannot add card block to RMS");
        }
    }

    private EProduct getProduct(String card) {
        return EProduct.fromValue(CardUtils.getCardType(card));
    }

    protected ECardType getCardType(String card) {
        for (String bin : LinkAppProperties.getCreditCardBins()) {
            if (card.startsWith(bin)) {
                if (LinkAppProperties.getBusinessCardBins().contains(bin)) {
                    return ECardType.BUSINESS_CREDIT;
                }
                return ECardType.CREDIT;
            }
        }
        if (LinkAppProperties.getBusinessCardBins().contains(card.substring(0, 6))) {
            return ECardType.BUSINESS_DEBIT;
        }
        return ECardType.DEBIT;
    }

    private ECommerceStatus getEcomCommerceStatus(PcdCard card) {
        ECommerceStatus comerceStatus = ECommerceStatus.ALLOWED;
        String centreId = CardUtils.composeCentreIdFromPcdCard(card);
        List<StipRmsStoplist> list = stipCardManager.getStipRmsStoplist(card.getCard(), centreId, null);
        if (list != null && !list.isEmpty()) {
            for (StipRmsStoplist rms : list) {
                if (rms.getAnswerCode() != null && "108".equals(rms.getAnswerCode().getActionCode())
                        && rms.getDescription() != null && rms.getDescription().startsWith("E-commerce blocked by")) {
                    comerceStatus = ECommerceStatus.NOT_ALLOWED;
                    break;
                }
            }
        }
        return comerceStatus;
    }

    private PcdAccount getAccount(String cardNumber) throws BusinessException {
        if (StringUtils.isBlank(cardNumber)) {
            throw BusinessException.create(JsonErrorCode.INVALID_CARD_NUMBER, "cardNumber", "Invalid card number");
        }
        PcdAccount account = pcdabaNGManager.getAccountByCardNumber(cardNumber);
        if (account == null) {
            throw BusinessException.create(ACCOUNT_NOT_FOUND, "account",
                    "Account for given card number was not found");
        }
        return account;
    }

    private PcdCondAccnt getCondAccntByComp_Id(PcdCondAccntPK comp_Id) throws BusinessException {
        if (StringUtils.isBlank(comp_Id.getBankC()) || StringUtils.isBlank(comp_Id.getGroupc())
                || StringUtils.isBlank(comp_Id.getCondSet())
                || StringUtils.isBlank(comp_Id.getCcy())) {
            throw BusinessException.create(BAD_REQUEST, "comp_Id", "Invalid comp_Id");
        }
        PcdCondAccnt condAccnt = pcdabaNGManager.getCondAccntByComp_Id(comp_Id);
        if (condAccnt == null) {
            throw BusinessException.create(JsonErrorCode.NOT_FOUND, "condAccnt",
                    "Condition account for given comp_Id was not found");
        }
        return condAccnt;
    }

    private void updateCardRenewStatusInCMS(PcdCard card, String status) throws BusinessException {
        UpdateCardXML updateCardXML = cmsWrapper.new UpdateCardXML(card.getCard(), card.getBankC(), card.getGroupc());
        updateCardXML.setElement("RENEW", status);
        UpdateDBWork work = cmsWrapper.new UpdateDBWork();
        work.setInputXML(updateCardXML.getDocument());

        if (cardManager != null) {
            String response = cardManager.doWork(work);
            if (response != null) {
                if (!"success".equals(response)) {
                    throw BusinessException.create(JsonErrorCode.APPLICATION_ERROR, "renewStatus",
                            "Cannot update card renew status. " + response);
                }
            }
        }
    }

    public List<CardTokenDO> readCardsTokens(String customerId, String country, String deviceId, String walletId) {
        List<CardTokenDO> responseObject = new ArrayList<>();
        log.info("readCardsTokens, customerId = {}, country = {}, deviceId = {}", customerId, country, deviceId);
        List<PcdCard> cards = pcdabaNGManager.getClientsCardsByCifOrPersonCode(customerId, country);
        log.info("readCardsTokens cards size = {}", cards.size());
        for (PcdCard card : cards) {
            TokenWalletDo token = walletTokenService.getWalletToken(card.getCard(), deviceId, walletId);
            if (token != null) {
                responseObject.add(CardTokenDO.builder()
                        .tokenRefId(token.getTokenRefId())
                        .tokenEligible(token.isTokenEligible())
                        .tokenStatus(token.getTokenStatus())
                        .id(card.getCard())
                        .build()
                );
            }
        }
        return responseObject;
    }

}

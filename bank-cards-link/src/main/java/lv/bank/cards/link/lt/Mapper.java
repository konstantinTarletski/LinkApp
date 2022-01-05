package lv.bank.cards.link.lt;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.cms.dao.ProductDAO;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdBranch;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdClAcct;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.entity.cms.IzdCompany;
import lv.bank.cards.core.entity.cms.IzdCondCard;
import lv.bank.cards.core.entity.cms.IzdCondCardPK;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.linkApp.dao.AccountsDAO;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dao.ClientsDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.DeliveryDetailsHelperBase;
import lv.bank.cards.core.utils.lt.DeliveryDetailsHelper;
import lv.bank.cards.core.vendor.api.cms.soap.types.ListTypeAddServInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAccountInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAccountInfoAdditional;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAccountInfoBase;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAgreement;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfoEMVData;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfoLogicalCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfoPhysicalCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfoTSMData;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCustomer;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCardRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCustomerRequest;
import lv.bank.cards.link.Constants;
import lv.bank.cards.link.MapperBase;
import lv.bank.cards.link.Order;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static lv.bank.cards.link.Constants.RANGE_ID_PREFIX;

@Slf4j
public class Mapper extends MapperBase {

    public Mapper(CommonDAO commonDAO, CardDAO cardDAO, CardsDAO cardsDAO, ClientDAO clientDAO, ClientsDAO clientsDAO,
                  ProductDAO productDAO, AccountsDAO accountsDAO) {
        super(commonDAO, cardDAO, cardsDAO, clientDAO, clientsDAO, productDAO, accountsDAO);
    }

    public boolean populatePcdClientAndToRowTypeEditCustomerRequest(
            Order order, RowTypeEditCustomerRequest customer, IzdClient izdClient, PcdClient pcdClient) throws DataIntegrityException {
        log.info("orderToRowTypeEditCustomerRequest BEGIN");
        boolean changed = false;

        customer.setBANKC(order.getBankc());
        customer.setCLIENT(izdClient.getComp_id().getClient());

        if (hasChange(order.getClientType(), izdClient.getClType())) {
            customer.setCLTYPE(order.getClientType());
            changed = true;
            if (pcdClient != null) {
                pcdClient.setClType(order.getClientType());
            }
        }
        if (hasChange(order.getClientFirstname(), izdClient.getFNames())) {
            customer.setFNAMES(order.getClientFirstname());
            changed = true;
            if (pcdClient != null) {
                pcdClient.setFirstNames(order.getClientFirstname());
            }
        }
        if (hasChange(order.getOwnerPhone(), izdClient.getRPhone())) {
            customer.setRPHONE(order.getOwnerPhone());
            changed = true;
            if (pcdClient != null) {
                pcdClient.setRPhone(order.getOwnerPhone());
            }
        }
        if (hasChange(order.getClientStreet(), izdClient.getRStreet())) {
            customer.setRSTREET(order.getClientStreet());
            changed = true;
            if (pcdClient != null) {
                pcdClient.setRStreet(order.getClientStreet());
            }
        }
        if (hasChange(order.getClientCity(), izdClient.getRCity())) {
            customer.setRCITY(order.getClientCity());
            changed = true;
            if (pcdClient != null) {
                pcdClient.setRCity(order.getClientCity());
            }
        }
        if (hasChange(order.getClientZip(), izdClient.getRPcode())) {
            customer.setRPCODE(order.getClientZip());
            changed = true;
            if (pcdClient != null) {
                pcdClient.setRPcode(order.getClientZip());
            }
        }
        if (hasChange(order.getClientCountry(), izdClient.getRCntry())) {
            String rcntry = order.getClientCountry();
            if (StringUtils.isNotBlank(rcntry)) {
                String country = commonDAO.getIzdCountryByShortCountryCode(order.getClientCountry()).getCountry();
                if (StringUtils.isNotBlank(country)) {
                    rcntry = country;
                }
            }
            customer.setRCNTRY(rcntry);
            changed = true;
            if (pcdClient != null) {
                pcdClient.setRCntry(rcntry);
            }
        }
        if (hasChange(order.getResident(), izdClient.getResident())) {
            customer.setRESIDENT(order.getResident());
            changed = true;
            if (pcdClient != null) {
                pcdClient.setResident(order.getResident());
            }
        }
        if (hasChange(order.getClientCategory(), izdClient.getClnCat())) {
            customer.setCLNCAT(order.getClientCategory());
            changed = true;
        }

        if (StringUtils.isNotBlank(order.getEmpCompanyRegNr())) {
            IzdCompany thisCompany = commonDAO.findIzdCompanyByRegCodeUR(order.getEmpCompanyRegNr());
            String newEmpCode = thisCompany.getComp_id().getCode();
            if (hasChange(newEmpCode, izdClient.getEmpCode())) {
                customer.setEMPCODE(newEmpCode);
                changed = true;
                if (pcdClient != null) {
                    pcdClient.setEmpCode(newEmpCode);
                }
            }
        } else {
            izdClient.setEmpCode(null);
            changed = true;
        }

        if (Constants.CLIENT_TYPE_CORPORATE.equals(order.getClientType())) {

            customer.setREGNR(order.getClientPersonId());

            if (hasChange(order.getClientLastname(), izdClient.getCmpName())) {
                customer.setCMPNAME(order.getClientLastname());
                changed = true;
                if (pcdClient != null) {
                    pcdClient.setCmpName(order.getClientLastname());
                }
            }

            if (hasChange(order.getCardHolderEmployerName(), izdClient.getEmpName())) {
                customer.setCMPGNAME(order.getCardHolderEmployerName());
                changed = true;
            }

        } else if (Constants.CLIENT_TYPE_PRIVATE.equals(order.getClientType())) {

            if (hasChange(order.getClientLastname(), izdClient.getSurname())) {
                customer.setSURNAME(order.getClientLastname());
                changed = true;
                if (pcdClient != null) {
                    pcdClient.setLastName(order.getClientLastname());
                }
            }
            if (hasChange(order.getClientPersonId(), izdClient.getPersonCode())) {
                customer.setPERSONCODE(order.getClientPersonId());
                changed = true;
                if (pcdClient != null) {
                    pcdClient.setPersonCode(order.getClientPersonId());
                }
            }
        }
        return changed;
    }

    public RowTypeEditCardRequest populatePcdCardAndRowTypeEditCardRequest(
            Order order, PcdCard pcdCard, String condSet, String renew) throws DataIntegrityException {
        log.info("populatePcdCardAndRowTypeEditCardRequest BEGIN");

        RowTypeEditCardRequest card = new RowTypeEditCardRequest();
        card.setCARD(order.getCardNumber());
        card.setBASESUPP(condSet);

        if (StringUtils.isNotBlank(order.getCardName())) {
            card.setCARDNAME(order.getCardName());
            pcdCard.setCardName(order.getCardName());
            card.setMCNAME(order.getCardName());
            pcdCard.setMcName(order.getCardName());
        }
        if (StringUtils.isNotBlank(order.getOwnerPersonId())) {
            card.setIDCARD(order.getOwnerPersonId());
            pcdCard.setIdCard(order.getOwnerPersonId());
        }
        if (StringUtils.isNotBlank(order.getOwnerFirstname())) {
            card.setFNAMES(order.getOwnerFirstname());
        }
        if (StringUtils.isNotBlank(order.getOwnerLastname())) {
            card.setSURNAME(order.getOwnerLastname());
        }
        if (StringUtils.isNotBlank(order.getCardConditionSet())) {
            card.setCONDSET(order.getCardConditionSet());
            pcdCard.setCondSet(order.getCardConditionSet());
        }
        if (StringUtils.isNotBlank(order.getRiskLevel())) {
            card.setRISKLEVEL(order.getRiskLevel());
            pcdCard.setRiskLevel(order.getRiskLevel());
        }
        if (StringUtils.isNotBlank(order.getCardHolderEmployerName())) {
            card.setCMPGNAME(order.getCardHolderEmployerName());
            pcdCard.setCmpgName(order.getCardHolderEmployerName());
        }
        if (StringUtils.isNotBlank(order.getCrdPasswd())) {
            card.setMNAME(order.getCrdPasswd());
            pcdCard.setMName(order.getCrdPasswd());
        }
        if (StringUtils.isNotBlank(order.getDesignId())) {
            card.setUFIELD8(order.getDesignId());
            pcdCard.setUField8(order.getDesignId());
        }

        if (StringUtils.isNotBlank(order.getBranchToDeliverAt())) {
            String branch = getBranchIdByExternalId(order.getBranchToDeliverAt());
            //This is done in processExtraFields
            //card.setUCOD10(branch);
            pcdCard.setUCod10(branch);
        }

        if (!StringUtils.isBlank(order.getCardMaximaFlag())) {
            pcdCard.setMaxima(order.getCardMaximaFlag());
        }

        DeliveryDetailsHelperBase details = orderToDeliveryDetailsHelper(order);
        //This is done in processExtraFields
        //card.setUBFIELD1(details.getDetails());
        pcdCard.setUBField1(details.getDetails());

        if (StringUtils.isNotBlank(order.getCardInsuranceFlag())) {
            //card.setInsuranceFlag(insFlag);
            pcdCard.setInsuranceFlag(getInsuranceFlagValue(order.getCardInsuranceFlag()));
        }

        if (StringUtils.isNotBlank(order.getCardRenewFlag())) {
            if (("n".equalsIgnoreCase(order.getCardRenewFlag())) && (!order.getCardRenewFlag().equalsIgnoreCase(renew))) {
                pcdCard.setRenewOld(pcdCard.getNextRenewOld());
                pcdCard.setRenew("N");
                card.setRENEW("N");
            } else if (("j".equalsIgnoreCase(order.getCardRenewFlag())) && ("n".equalsIgnoreCase(pcdCard.getRenew()))) {
                String renewOld = pcdCard.getRenewOld();
                pcdCard.setRenewOld("N");
                pcdCard.setRenew(renewOld);
                card.setRENEW(renewOld);
            }
        }
        return card;
    }

    public static String getInsuranceFlagValue(String insuranceFlag) {
        String insFlag = null;
        if ("1".equals(insuranceFlag)) {
            insFlag = "Y";
        } else if ("0".equals(insuranceFlag)) {
            insFlag = "N";
        }
        return insFlag;
    }

    public RowTypeCustomer orderToRowTypeCustomer(Order order) throws DataIntegrityException {
        log.info("orderToRowTypeCustomer BEGIN");

        RowTypeCustomer customer = new RowTypeCustomer();
        customer.setCLTYPE(order.getClientType());
        customer.setSTATUS("10");
        customer.setCLNCAT(order.getClientCategory());
        customer.setRESIDENT(order.getResident());
        try {
            customer.setCSINCE(convertYearTo4digit(order.getBankClientSince()));
        } catch (ParseException e) {
            log.warn("orderToRowTypeCustomer, could not parse order.clientSince, value = {}", order.getBankClientSince());
        }
        customer.setFNAMES(order.getClientFirstname());
        customer.setMIDLENAME(order.getCrdPasswd());
        customer.setRPHONE(order.getOwnerPhone());
        customer.setRSTREET(order.getClientStreet());
        customer.setRCITY(order.getClientCity());
        customer.setRPCODE(order.getClientZip());
        customer.setCLIENTB(order.getClientNumberInAbs());

        String rcntry = order.getClientCountry();
        if (StringUtils.isNotBlank(rcntry)) {
            String country = commonDAO.getIzdCountryByShortCountryCode(order.getClientCountry()).getCountry();
            if (StringUtils.isNotBlank(country)) {
                rcntry = country;
            }
        }
        customer.setRCNTRY(rcntry);
        customer.setBDATE(parseDate(order.getBirthdayMask(), order.getOwnerBirthday()));

        if (Constants.CLIENT_TYPE_PRIVATE.equals(order.getClientType())) {
            customer.setSURNAME(order.getClientLastname());
            customer.setPERSONCODE(order.getClientPersonId());
        } else if (Constants.CLIENT_TYPE_CORPORATE.equals(order.getClientType())) {
            customer.setREGNR(order.getClientPersonId());
            customer.setCMPNAME(order.getClientLastname());
            customer.setCMPGNAME(order.getCardHolderEmployerName());
        }

        return customer;
    }

    public RowTypeAccountInfo orderRowTypeAccountInfo(Order order) {
        log.info("orderRowTypeAccountInfo BEGIN");

        RowTypeAccountInfo accountInfo = new RowTypeAccountInfo();

        RowTypeAccountInfoBase baseInfo = new RowTypeAccountInfoBase();
        RowTypeAccountInfoAdditional additionalInfo = new RowTypeAccountInfoAdditional();

        accountInfo.setAdditionalInfo(additionalInfo);
        accountInfo.setBaseInfo(baseInfo);

        baseInfo.setCCY(order.getAccountCcy());
        baseInfo.setUFIELD5(order.getUField5());
        baseInfo.setCARDACCT(order.getCardAccountNoCms());
        baseInfo.setCONDSET(order.getAccountCondSet());

        baseInfo.setADJUSTFLAG("0");
        baseInfo.setSTATCHANGE("1");
        baseInfo.setCRD(BigDecimal.ZERO);
        baseInfo.setMINBAL(BigDecimal.ZERO);
        baseInfo.setNONREDUCEBAL(BigDecimal.ZERO);
        baseInfo.setCYCLE("0");
        baseInfo.setSTATUS("0");
        baseInfo.setCACCNTTYPE("00");
        baseInfo.setLIMINTR(BigDecimal.ZERO);

        return accountInfo;
    }

    public RowTypeCardInfo orderToRowTypeCardInfo(Order order, IzdAccount account) throws DataIntegrityException {
        log.info("orderToRowTypeCardInfo BEGIN");
        RowTypeCardInfo rowTypeCardInfo = new RowTypeCardInfo();

        RowTypeCardInfoLogicalCard logicalCard = new RowTypeCardInfoLogicalCard();
        RowTypeCardInfoPhysicalCard physicalCard = new RowTypeCardInfoPhysicalCard();
        RowTypeCardInfoEMVData emvData = new RowTypeCardInfoEMVData();
        ListTypeAddServInfo addServInfo = new ListTypeAddServInfo();
        RowTypeCardInfoTSMData tsmData = new RowTypeCardInfoTSMData();

        String branch = getBranchIdByExternalId(order.getBranchToDeliverAt());
        log.info("orderToRowTypeCardInfo, orderId = {}, branch = {} ", order.getOrderId(), branch);

        String cardType = getCardType(order);
        log.info("orderToRowTypeCardInfo, orderId = {}, cardType = {} ", order.getOrderId(), cardType);

        rowTypeCardInfo.setLogicalCard(logicalCard);
        rowTypeCardInfo.setPhysicalCard(physicalCard);
        rowTypeCardInfo.setEMVData(emvData);
        rowTypeCardInfo.setAddServInfo(addServInfo);
        rowTypeCardInfo.setTSMData(tsmData);

        logicalCard.setCLIENT(order.getClient());
        logicalCard.setFNAMES(order.getOwnerFirstname());
        logicalCard.setSURNAME(order.getOwnerLastname());
        logicalCard.setMNAME(order.getCrdPasswd());
        logicalCard.setUFIELD7(order.getAuthNotifyDestination());
        logicalCard.setIDCARD(order.getClientPersonId());
        logicalCard.setUCOD9(order.getUCod9());
        logicalCard.setCONDSET(order.getCardConditionSet());
        logicalCard.setRISKLEVEL(order.getRiskLevel());
        logicalCard.setUFIELD8(order.getDesignId());
        logicalCard.setUCOD10(branch);
        logicalCard.setCARDTYPE(cardType);
        if (StringUtils.isNotBlank(order.getCardHolderEmployerName())) {
            logicalCard.setCMPGNAME(order.getCardHolderEmployerName());
        }
        if (order.getAuthLevel() != null) {
            logicalCard.setRANGEID(new BigDecimal(RANGE_ID_PREFIX + order.getAuthLevel()));
        }
        String baseSupp = null;
        if (!StringUtils.isBlank(order.getBaseSupp()) && account != null) {
            acctPoint:
            for (IzdClAcct acct : account.getIzdClAccts()) {
                for (IzdCard card : acct.getIzdCards()) {
                    if (!"2".equals(card.getStatus1()) && card.getBinCode().equals(order.getBin()) && "1".equals(card.getBaseSupp())) {
                        baseSupp = "2"; // 2 stands for "supplementary"
                        break acctPoint;
                    }
                }
            }
        } else {
            baseSupp = "1";
        }
        logicalCard.setBASESUPP(baseSupp);

        physicalCard.setCARDNAME(order.getCardName());
        if (StringUtils.isNotBlank(order.getChipDesignId())) {
            physicalCard.setDESIGNID(new BigDecimal(order.getChipDesignId()));
        }
        if (StringUtils.isNotBlank(order.getMigratedCardNumber())) {
            if (StringUtils.isNotBlank(order.getCardExpiry())) {
                log.info("Migrated card, will set custom expiry date {} that was provided in order", order.getCardExpiry());
                Date cardExpiry = cardExpiryStringToDate(order.getCardExpiry());
                checkCardExpiry(cardExpiry);
                physicalCard.setEXPIRY1(cardExpiry);
            } else {
                log.info("Migrated card, will set random expiry date in range from {} to {} months",
                        Constants.MIGRATED_CARD_MIN_EXPIRY, Constants.MIGRATED_CARD_MAX_EXPIRY);
                Date expiry1 = randomExpiry(Constants.MIGRATED_CARD_MIN_EXPIRY, Constants.MIGRATED_CARD_MAX_EXPIRY);
                physicalCard.setEXPIRY1(expiry1);
            }
        }

        if (StringUtils.isNotBlank(order.getApplicationId())) {
            emvData.setCHIPAPPID(new BigDecimal(order.getApplicationId()));
        }
        return rowTypeCardInfo;
    }

    public RowTypeAgreement orderToRowTypeAgreement(Order order) throws DataIntegrityException {
        log.info("orderToRowTypeAgreement BEGIN");
        RowTypeAgreement agreement = orderToRowTypeAgreementBase(order, getBranchIdByExternalId(order.getBranch()));
        agreement.setCITY(order.getClientCity());
        agreement.setSTREET(order.getClientStreet());
        agreement.setPOSTIND(order.getClientZip());
        agreement.setRISKLEVEL(order.getRiskLevel());
        return agreement;
    }

    protected String getCardType(Order order) {
        IzdCondCardPK izdCondCardPK = new IzdCondCardPK();
        izdCondCardPK.setCondSet(order.getCardConditionSet());
        izdCondCardPK.setBankC(order.getBankc());
        izdCondCardPK.setGroupc(order.getGroupc());
        izdCondCardPK.setCcy(order.getAccountCcy());
        IzdCondCard izdCondCard = cardDAO.getIzdCondCardByID(izdCondCardPK);
        return izdCondCard.getCardType();
    }

    protected static Date cardExpiryStringToDate(String expiry) throws DataIntegrityException {
        String errorMessage = "Incorrect expiry date. Card expiry date should be submitted in format DD.MM.YYYY";
        if (!expiry.matches("([0-9]{2}).([0-9]{2}).([0-9]{4})")) {
            throw new DataIntegrityException(errorMessage);
        }
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(expiry);
        } catch (ParseException e) {
            throw new DataIntegrityException(errorMessage);
        }
    }

    protected static void checkCardExpiry(Date expiryDate) throws DataIntegrityException {
        Calendar expiry = Calendar.getInstance();
        expiry.setTime(expiryDate);

        Calendar maxExpiry = Calendar.getInstance();
        maxExpiry.set(Calendar.DAY_OF_MONTH, 1);
        maxExpiry.add(Calendar.MONTH, Constants.MAX_CARD_EXPIRY_IN_MONTHS);
        int lastDayOfMonth = maxExpiry.getActualMaximum(Calendar.DAY_OF_MONTH);
        maxExpiry.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);

        if (expiry.before(Calendar.getInstance())) {
            throw new DataIntegrityException("Incorrect expiry date. Card cannot be created with a past expiry date.");
        }

        if (expiry.get(Calendar.DAY_OF_MONTH) != expiry.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            throw new DataIntegrityException("Incorrect expiry date. Card should expire on last day of month.");
        }

        if (expiry.after(maxExpiry)) {
            throw new DataIntegrityException("Incorrect expiry date. Card cannot be issued with expiry of more than " +
                    Constants.MAX_CARD_EXPIRY_IN_MONTHS + " months.");
        }
    }

    @Override
    public String getBranchIdByExternalId(String branch) throws DataIntegrityException {
        if (StringUtils.isBlank(branch)) {
            return null;
        }
        final IzdBranch izdBranch = commonDAO.getIzdBranchByRegCodeUR(branch);
        if (izdBranch == null) {
            return null;
        }
        return izdBranch.getComp_id().getBranch();
    }

    @Override
    public DeliveryDetailsHelperBase orderToDeliveryDetailsHelper(Order order) {

        IzdCard izdCard = null;

        if (StringUtils.isNotBlank(order.getCardNumber())) {
            izdCard = (IzdCard) cardDAO.getObject(IzdCard.class, order.getCardNumber());
        } else {
            log.info("orderToDeliveryDetailsHelper, cardNumber is empty");
        }

        final String izdCardBranch = izdCard != null ? izdCard.getUCod10() : null;

        log.info("orderToDeliveryDetailsHelper, action = {} branch = {}, izdCard = {}, izdCardBranch = {}",
                order.getAction(), order.getBranchToDeliverAt(), izdCard, izdCardBranch);

        DeliveryDetailsHelper details = orderToDeliveryDetailsHelperFromOrder(order);

        log.info("orderToDeliveryDetailsHelper, details = {}", details);

        if (StringUtils.isBlank(details.getDetails()) && izdCard != null) {

            String uBField1 = izdCard.getIzdCardsAddFields() == null ? null : izdCard.getIzdCardsAddFields().getUBField1();

            log.info("orderToDeliveryDetailsHelper, izdCard.izdCardsAddFields.uBField1 = {}", uBField1);

            details = new DeliveryDetailsHelper(uBField1);
            log.info("orderToDeliveryDetailsHelper, details from izdCard = {}", details);
        }
        return details;
    }

    protected DeliveryDetailsHelper orderToDeliveryDetailsHelperFromOrder(Order order) {
        log.info("orderToDeliveryDetailsHelperLT BEGIN");
        return new DeliveryDetailsHelper(
                //order.getDlvLanguage(),
                order.getDlvAddrCountry(),
                order.getDlvAddrCity(),
                order.getDlvAddrStreet1(),
                order.getDlvAddrStreet2(),
                order.getDlvAddrZip(),
                order.getDlvCompany(),
                order.getDlvAddrCode());
    }

    public static void populateOrderFromDeliveryDetailsHelper(DeliveryDetailsHelperBase deliveryHelper, Order order) {
        log.info("populateOrderFromDeliveryDetailsHelper BEGIN");
        order.setDlvAddrCountry(deliveryHelper.getCountry());
        order.setDlvAddrCity(deliveryHelper.getRegion());
        order.setDlvAddrStreet1(deliveryHelper.getCity());
        order.setDlvAddrStreet2(deliveryHelper.getAddress());
        order.setDlvAddrZip(deliveryHelper.getZipCode());
        order.setDlvCompany(deliveryHelper.getAdditionalFields());
        order.setDlvAddrCode(deliveryHelper.getAddressCode());
    }

}

package lv.bank.cards.link.lv;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.cms.dao.ProductDAO;
import lv.bank.cards.core.entity.cms.IzdOfferedProduct;
import lv.bank.cards.core.linkApp.dao.AccountsDAO;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dao.ClientsDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.lv.DeliveryDetailsHelper;
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
import lv.bank.cards.link.Constants;
import lv.bank.cards.link.MapperBase;
import lv.bank.cards.link.Order;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
public class Mapper extends MapperBase {

    public Mapper(CommonDAO commonDAO, CardDAO cardDAO, CardsDAO cardsDAO, ClientDAO clientDAO, ClientsDAO clientsDAO,
                  ProductDAO productDAO, AccountsDAO accountsDAO) {
        super(commonDAO, cardDAO, cardsDAO, clientDAO, clientsDAO, productDAO, accountsDAO);
    }

    public RowTypeCustomer orderToRowTypeCustomer(Order order) throws DataIntegrityException {

        log.info("orderToRowTypeCustomer BEGIN");

        RowTypeCustomer customer = new RowTypeCustomer();
        customer.setCLTYPE(order.getClientType());
        customer.setSTATUS("10");
        customer.setCLNCAT(order.getClientCategory());
        customer.setRESIDENT(order.getResident());
        customer.setREGION(order.getCountry());
        customer.setCSINCE(order.getBankClientSince());

        // set afterwards via linkClientToCif as CMS WS do not permit creating 2 identical CIFs,
        // but they can match for LV & EE customers
        // customer.setCLIENTB(order.getClientNumberInAbs());

        if (Constants.CLIENT_TYPE_PRIVATE.equals(order.getClientType())) {
            customer.setFNAMES(order.getClientFirstname());
            customer.setSURNAME(order.getClientLastname());
            customer.setMIDLENAME(order.getCrdPasswd());
            customer.setPERSONCODE(order.getClientPersonId());
            customer.setRPHONE(order.getOwnerPhone());
            customer.setRSTREET(order.getClientStreet());
            customer.setRCITY(order.getClientCity());
            customer.setRPCODE(order.getClientZip());

            String rcntry = order.getClientCountry();
            if (StringUtils.isNotBlank(rcntry)) {
                String country = commonDAO.getIzdCountryByShortCountryCode(order.getClientCountry()).getCountry();
                if (StringUtils.isNotBlank(country)) {
                    rcntry = country;
                }
            }
            customer.setRCNTRY(rcntry);
            customer.setBDATE(parseDate(order.getBirthdayMask(), order.getOwnerBirthday()));

        } else if (Constants.CLIENT_TYPE_CORPORATE.equals(order.getClientType())) {
            if (order.getOwnCompanyName() != null) {
                customer.setCMPNAME(order.getOwnCompanyName());
                customer.setCMPGNAME(order.getOwnCompanyName());
            }
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

        try {
            IzdOfferedProduct product = getCorrespondingProduct(order);
            log.info("orderRowTypeAccountInfo setting cond_set = {}", product.getCondAccnt());
            baseInfo.setCONDSET(product.getCondAccnt());

            if (order.getAccountNoCms() != null) {
                baseInfo.setACCOUNTNO(order.getAccountNoCms());
                baseInfo.setCARDACCT(order.getCardAccountNoCms());
                log.info("orderRowTypeAccountInfo using existing account with account_no = {}, card_acct = {}",
                        order.getAccountNoCms(), order.getCardAccountNoCms());
            } else {
                String cardAccountId = accountsDAO.getNewCardAcctId(order.getAccountNoPlaton());
                log.info("orderRowTypeAccountInfo setting new izd_account.card_acct = {}", cardAccountId);
                baseInfo.setCARDACCT(cardAccountId);
            }
        } catch (DataIntegrityException e) {
            log.error(e.getMessage());
        }

        baseInfo.setCCY(order.getAccountCcy());
        baseInfo.setUFIELD5(order.getAccountNoPlaton());
        baseInfo.setCACCNTTYPE("00");
        baseInfo.setCYCLE("0");
        baseInfo.setSTATUS("0");
        baseInfo.setSTATCHANGE("1");
        baseInfo.setMINBAL(BigDecimal.ZERO);
        baseInfo.setNONREDUCEBAL(BigDecimal.ZERO);
        baseInfo.setCRD(BigDecimal.ZERO);
        baseInfo.setLIMAMNT(BigDecimal.ZERO);

        return accountInfo;
    }

    public RowTypeCardInfo orderToRowTypeCardInfo(Order order) throws DataIntegrityException {
        log.info("orderToRowTypeCardInfo BEGIN");
        RowTypeCardInfo rowTypeCardInfo = new RowTypeCardInfo();

        RowTypeCardInfoLogicalCard logicalCard = new RowTypeCardInfoLogicalCard();
        RowTypeCardInfoPhysicalCard physicalCard = new RowTypeCardInfoPhysicalCard();
        RowTypeCardInfoEMVData emvData = new RowTypeCardInfoEMVData();
        ListTypeAddServInfo addServInfo = new ListTypeAddServInfo();
        RowTypeCardInfoTSMData tsmData = new RowTypeCardInfoTSMData();

        rowTypeCardInfo.setLogicalCard(logicalCard);
        rowTypeCardInfo.setPhysicalCard(physicalCard);
        rowTypeCardInfo.setEMVData(emvData);
        rowTypeCardInfo.setAddServInfo(addServInfo);
        rowTypeCardInfo.setTSMData(tsmData);

        IzdOfferedProduct product = getCorrespondingProduct(order);
        log.info("orderToRowTypeCardInfo, product nr = {} ", product.getComp_id().getCode());
        String branch = getBranchIdByExternalId(order.getBranchToDeliverAt());
        log.info("orderToRowTypeCardInfo, branch = {} ", branch);

        logicalCard.setCLIENT(order.getClient());
        logicalCard.setBASESUPP(order.getBaseSupp());
        logicalCard.setFNAMES(order.getClientFirstname());
        logicalCard.setSURNAME(order.getClientLastname());
        logicalCard.setMNAME(order.getCrdPasswd());
        logicalCard.setUFIELD7(order.getAuthNotifyDestination());
        logicalCard.setIDCARD(order.getClientPersonId());
        logicalCard.setUCOD9(order.getUCod9());
        logicalCard.setUCOD10(branch);
        logicalCard.setUFIELD8(order.getOrderId());
        logicalCard.setCONDSET(product.getCondCard());
        logicalCard.setCARDTYPE(product.getCardType());
        //Setting company name for corporate client
        if (order.getClientType().startsWith(Constants.CLIENT_TYPE_CORPORATE)) {
            logicalCard.setCMPGNAME(order.getOwnerName());
        }
        logicalCard.setBDATE(parseDate(order.getBirthdayMask(), order.getClientBirthday()));

        if (StringUtils.equalsIgnoreCase("true", order.getMinSalaryAccount())) {
            String minBalAccountRiskLevel = getMinimalBalanceAccountRiskLevelByCountry(order.getCountry());
            log.info("orderToRowTypeCardInfo, setting card risk level for min. balance account: {}", minBalAccountRiskLevel);
            logicalCard.setRISKLEVEL(minBalAccountRiskLevel);
        } else if (StringUtils.isNotBlank(order.getRiskLevel())) {
            if (cardDAO.isRiskLevelLinkedToBin(order.getBin(), order.getRiskLevel())) {
                log.info("orderToRowTypeCardInfo, setting card risk level from order {}" + order.getRiskLevel());
                logicalCard.setRISKLEVEL(order.getRiskLevel());
            }
        } else {
            log.info("orderToRowTypeCardInfo, risk level = {}", product.getRiskLevel());
            logicalCard.setRISKLEVEL(product.getRiskLevel());
        }

        physicalCard.setCARDNAME(order.getCardName());

        if (StringUtils.isNotBlank(order.getDesignId())) {
            physicalCard.setDESIGNID(new BigDecimal(order.getDesignId()));
        } else if (product.getChipDesignId() != null) {
            physicalCard.setDESIGNID(new BigDecimal(product.getChipDesignId()));
        }

        if (StringUtils.isNotBlank(order.getMigratedCardNumber())) {
            log.info("Migrated card, will set random expiry date in range from {} to {} months",
                    Constants.MIGRATED_CARD_MIN_EXPIRY, Constants.MIGRATED_CARD_MAX_EXPIRY);
            Date expiry1 = randomExpiry(Constants.MIGRATED_CARD_MIN_EXPIRY, Constants.MIGRATED_CARD_MAX_EXPIRY);
            physicalCard.setEXPIRY1(expiry1);
        }

        if (StringUtils.isNotBlank(order.getApplicationId())) {
            emvData.setCHIPAPPID(new BigDecimal(order.getApplicationId()));
        }

        if (order.getAuthLevel() != null) {
            logicalCard.setAUTHLIMIT(order.getAuthLevel());
        } else if (product.getAuthLevel() != null) {
            logicalCard.setAUTHLIMIT(product.getAuthLevel());
        }

        return rowTypeCardInfo;
    }

    public RowTypeAgreement orderToRowTypeAgreement(Order order) throws DataIntegrityException {

        log.info("orderToRowTypeAgreement BEGIN");

        final String branch = getBranchIdByExternalId(order.getBranch());

        RowTypeAgreement agreement = orderToRowTypeAgreementBase(order, branch);
        agreement.setCITY(order.getStmtCity());
        agreement.setSTREET(order.getStmtStreet());
        agreement.setPOSTIND(order.getStmtZip());

        // existing agreement
        if (order.getAgreementKey() != null && !order.getAgreementKey().isEmpty()) {
            agreement.setAGRENOM(new BigDecimal(order.getAgreementKey()));
        }

        if (StringUtils.equalsIgnoreCase("true", order.getMinSalaryAccount())) {
            String minBalAccountRiskLevel = getMinimalBalanceAccountRiskLevelByCountry(order.getCountry());
            log.info("Setting agreement risk level for min. balance account: {}", minBalAccountRiskLevel);
            agreement.setRISKLEVEL(minBalAccountRiskLevel);
        } else if (StringUtils.isNotBlank(order.getRiskLevel())) {
            String bin = order.getBin();
            String riskLevel = order.getRiskLevel();
            log.info("Setting agreement risk level from order: {}", riskLevel);
            if (cardDAO.isRiskLevelLinkedToBin(bin, riskLevel)) {
                agreement.setRISKLEVEL(riskLevel);
            } else {
                throw new DataIntegrityException("Risk Level [" + riskLevel + "] is not linked to BIN [" + bin + "]");
            }
        } else {
            IzdOfferedProduct product = getCorrespondingProduct(order);
            log.info("Setting agreement risk level from CMS product: " + product.getRiskLevel());
            agreement.setRISKLEVEL(product.getRiskLevel());
        }

        return agreement;
    }

    @Override
    public String getBranchIdByExternalId(String branch) throws DataIntegrityException {
        return commonDAO.getBranchIdByExternalId(branch);
    }

    @Override
    public DeliveryDetailsHelper orderToDeliveryDetailsHelper(Order order) throws DataIntegrityException {
        log.info("orderToDeliveryDetailsHelperLV BEGIN");
        return new DeliveryDetailsHelper(
                getLanguage(order),
                order.getDlvAddrCountry(),
                order.getDlvAddrCity(),
                order.getDlvAddrStreet1(),
                order.getDlvAddrStreet2(),
                order.getDlvAddrZip(),
                order.getDlvCompany());
    }

}

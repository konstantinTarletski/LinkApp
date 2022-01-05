package lv.bank.cards.link.lt;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.AccountDAO;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dao.ClientsDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.ejb.CMSSoapAPIWrapperBean;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAccountInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAgreement;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCustomer;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCardRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCustomerRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeOrderPinEnvelope;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeRenewCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeReplaceCard;
import lv.bank.cards.link.Constants;
import lv.bank.cards.core.utils.DeliveryDetailsHelperBase;
import lv.bank.cards.link.Order;
import lv.bank.cards.link.OrderProcessorBase;
import lv.bank.cards.rtcu.util.BankCardsWSWrapperDelegate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static lv.bank.cards.core.utils.TextUtils.normalizeNameForDiff;
import static lv.bank.cards.core.utils.TextUtils.similarityPercentage;

@Slf4j
public class OrderProcessor extends OrderProcessorBase {

    public OrderProcessor(CMSSoapAPIWrapperBean cmsSoapAPIWrapperBean, CMSCallAPIWrapper cmsCallAPIWrapper,
                          BankCardsWSWrapperDelegate bankCardsWSWrapperDelegate, Mapper mapper,
                          CommonDAO commonDAO, CardDAO cardDAO, CardsDAO cardsDAO, AccountDAO accountDAO,
                          ClientDAO clientDAO, ClientsDAO clientsDAO) {
        super(cmsSoapAPIWrapperBean, cmsCallAPIWrapper, bankCardsWSWrapperDelegate, mapper, commonDAO, cardDAO, cardsDAO,
                accountDAO, clientDAO, clientsDAO);
    }

    public void processOrder(Order order) throws DataIntegrityException, CMSSoapAPIException, CMSCallAPIException {
        log.info("processOrder, BEGIN, order = {}", order);
        processNormalOrder(order);
        processExtraFields(order);
        log.info("processOrder END, orderId = {}, action = {}", order.getOrderId(), order.getAction());
    }

    @Override
    public void informationChange(Order order) throws DataIntegrityException, CMSSoapAPIException, CMSCallAPIException {

        if (Constants.APPLICATION_TYPE_CARD_AUTO_RENEW.equals(order.getApplicationType())) {
            renewCard(order);
        }

        IzdClient izdClient;
        if (StringUtils.isBlank(order.getCardNumber())) {
            izdClient = clientDAO.findByCif(order.getClientNumberInAbs());
        } else {
            IzdCard izdCard = (IzdCard) this.cardDAO.getObject(IzdCard.class, order.getCardNumber());
            izdClient = izdCard.getIzdAgreement().getIzdClient();
        }
        PcdClient pcdClient = clientsDAO.getClient(izdClient.getComp_id().getClient());
        RowTypeEditCustomerRequest customer = new RowTypeEditCustomerRequest();

        if(clientPersonCodeChanged(order, izdClient)){
            updateCardholderPersonCode(izdClient.getPersonCode(), order.getClientPersonId(), order.getCountry(),
                    izdClient.getFNames() + " " + izdClient.getSurname());
        }

        boolean clientRecordHasChanged = getMapper().populatePcdClientAndToRowTypeEditCustomerRequest(order, customer, izdClient, pcdClient);
        if(clientRecordHasChanged){
            clientDAO.saveOrUpdate(izdClient);
            clientsDAO.saveOrUpdate(pcdClient);
            cmsSoapAPIWrapperBean.editCustomer(customer);
        }

        if(StringUtils.isNotBlank(order.getCardNumber())){
            IzdCard izdCard = (IzdCard) this.cardDAO.getObject(IzdCard.class, order.getCardNumber());
            PcdCard pcdCard = cardsDAO.findByCardNumber(order.getCardNumber());

            RowTypeEditCardRequest editCard = getMapper().populatePcdCardAndRowTypeEditCardRequest(
                    order, pcdCard, izdCard.getBaseSupp(), izdCard.getRenew());

            cardsDAO.saveOrUpdate(pcdCard);
            cmsSoapAPIWrapperBean.editCard(editCard);

            if (StringUtils.isNotBlank(order.getCardInsuranceFlag())) {
                cmsCallAPIWrapper.switchInsurance(order.getCardNumber(), order.getBankc(), order.getGroupc(),
                        Mapper.getInsuranceFlagValue(order.getCardInsuranceFlag()));
            }
        }
    }

    @Override
    public void renewCard(Order order) throws CMSSoapAPIException {
        RowTypeRenewCard c = Mapper.orderToRowTypeRenewCard(order);
        RowTypeRenewCard renewCard = cmsSoapAPIWrapperBean.renewCard(c);
        order.setCardNumber(renewCard.getNEWCARD());
    }

    @Override
    public void replaceCard(Order order) throws CMSSoapAPIException {

        //Copy address to order (will be processed by processExtraFields)
        DeliveryDetailsHelperBase details = getMapper().orderToDeliveryDetailsHelper(order);
        Mapper.populateOrderFromDeliveryDetailsHelper(details, order);

        RowTypeReplaceCard c = Mapper.orderToRowTypeReplaceCard(order);
        RowTypeReplaceCard replacedCard = cmsSoapAPIWrapperBean.replaceCard(c);
        order.setCardNumber(replacedCard.getNEWCARD());
    }

    protected String resolveCustomer(Order order) throws DataIntegrityException, CMSSoapAPIException {
        String client = null;
        IzdClient izdClient = clientDAO.findByCif(order.getClientNumberInAbs());
        if(izdClient != null){
            client = izdClient.getComp_id().getClient();
            log.info("resolveCustomer, findByCif clientNumberInAbs = {}, client = {}", order.getClientNumberInAbs(), client);
        }
        if (client == null) {
            RowTypeCustomer customer = getMapper().orderToRowTypeCustomer(order);
            RowTypeCustomer newCustomer = cmsSoapAPIWrapperBean.newCustomer(customer, null);
            client = newCustomer.getCLIENT();
            log.info("resolveCustomer, created new customer = {}", newCustomer.getCLIENT());
        }
        return client;
    }

    @Override
    public void newCard(Order order) throws DataIntegrityException, CMSSoapAPIException, CMSCallAPIException {

        if (StringUtils.isBlank(order.getClient())) {
            order.setClient(resolveCustomer(order));
        }

        IzdAccount account = null;
        if (!StringUtils.isBlank(order.getCardAccountNoCms()) && order.getAccountNoCms() == null) {
            account = findAccountByIzdClientAndExternalNo(order.getClient(), order.getCardAccountNoCms());
            if (account != null) {
                order.setAccountNoCms(account.getAccountNo());
                order.setCardAccountNoCms(account.getCardAcct());
            }
        }

        RowTypeAgreement agreement = getMapper().orderToRowTypeAgreement(order);
        RowTypeCardInfo cardInfo = getMapper().orderToRowTypeCardInfo(order, account);
        RowTypeAccountInfo accountInfo = getMapper().orderRowTypeAccountInfo(order);

        List<RowTypeAccountInfo> accountInfoList = new ArrayList<>();
        accountInfoList.add(accountInfo);

        List<RowTypeCardInfo> cardsListInfo = new ArrayList<>();
        cardsListInfo.add(cardInfo);

        List<RowTypeCardInfo> cardList = cmsSoapAPIWrapperBean.newCard(agreement, accountInfoList, cardsListInfo);
        putNewCardToOrder(order, cardList);

        String insurance = null;
        if ("1".equals(order.getCardInsuranceFlag())) {
            insurance = "Y";
        } else if ("0".equals(order.getCardInsuranceFlag())) {
            insurance = "N";
        }
        cmsCallAPIWrapper.switchInsurance(order.getCardNumber(), order.getBankc(), order.getGroupc(), insurance);
    }

    public static boolean checkAgeOlderThan14(String clientID, String ownerID) {
        if (ownerID == null)
            return false;
        if (StringUtils.equals(clientID, ownerID)) {
            Calendar before14Years = Calendar.getInstance();
            before14Years.add(Calendar.YEAR, -Constants.MIN_AGE_FOR_USING_CAR);
            Calendar birthDate = Calendar.getInstance();
            Date twoDigitYearStart = DateUtils.addYears(new Date(), -100);
            Constants.PERSONAL_CODE_DATE_FORMAT_LT.set2DigitYearStart(twoDigitYearStart);
            try {
                birthDate.setTime(Constants.PERSONAL_CODE_DATE_FORMAT_LT.parse(StringUtils.substring(ownerID, 1, 7)));
            } catch (ParseException e) {
                log.info("Cannot determine age from personal code");
                return false;
            }
            if (before14Years.before(birthDate))
                return false; // Is younger
        }
        return true;
    }

    protected boolean isPINDeliveryBT(Order o) {
        // PIN delivery BT is used only for PIN reminder
        return Boolean.parseBoolean(o.getPinDeliveryBt())
                && o.getApplicationType() != null && o.getApplicationType().startsWith(Constants.PIN_REMINDER_ACTION);
    }

    protected boolean isEPin(Order o, IzdCard izdCard, boolean checkInformationChange) throws DataIntegrityException {
        log.info("isEPin, checkInformationChange = {}",  checkInformationChange);
        if (checkInformationChange && Constants.INFORMATION_CHANGE_ACTION.equals(o.getAction())){
            log.info("isEPin, exit false because checkInformationChange = true, action = {}", o.getAction());
            return false;
        }
        if (!StringUtils.isBlank(o.getBranchToDeliverAt()) && !getDeliveryBranch().equals(o.getBranchToDeliverAt()) && !isPINDeliveryBT(o)) {
            log.info("isEPin, exit false because deliveryBranch = {} applicationType = {}",  o.getBranchToDeliverAt(), o.getApplicationType());
            return false;
        }
        if (!StringUtils.isBlank(o.getClientType()) &&
                !StringUtils.isBlank(o.getClientPersonId()) &&
                !StringUtils.isBlank(o.getOwnerPersonId())) {

            if (!checkAgeOlderThan14(o.getClientPersonId(), o.getOwnerPersonId())) {
                log.info("isEPin, exit false because checkAgeOlderThan14 check");
                return false;
            }
            // Need to generate PIN fields only if new card is created
            log.info("isEPin, checking clientType = {}", o.getClientType());
            return (getDeliveryBranch().equals(o.getBranchToDeliverAt()) || isPINDeliveryBT(o)) &&
                    Constants.CLIENT_TYPE_PRIVATE.equals(o.getClientType()) &&
                    StringUtils.equals(o.getClientPersonId(), o.getOwnerPersonId());
        } else if (!StringUtils.isBlank(o.getCardNumber())) {
            if (izdCard == null) {
                throw new DataIntegrityException("Cannot find card by card number " + o.getCardNumber());
            } else {
                log.info("isEPin, checking izdCard is not null");
                if (!checkAgeOlderThan14(izdCard.getIzdAgreement().getIzdClient().getPersonCode(), izdCard.getIdCard())) {
                    log.info("isEPin, exit false, checkAgeOlderThan14 izdCard.idCard = {}", izdCard.getIdCard());
                    return false;
                }
                log.info("isEPin, check");
                return (lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_080.equals(izdCard.getUCod10()) || isPINDeliveryBT(o)) &&
                        Constants.CLIENT_TYPE_PRIVATE.equals(izdCard.getIzdAgreement().getIzdClient().getClType()) &&
                        StringUtils.equals(izdCard.getIdCard(), izdCard.getIzdAgreement().getIzdClient().getPersonCode());
            }
        } else {
            throw new DataIntegrityException("Missing information for PIN delivery");
        }
    }

    protected boolean isIbePin(Order o, IzdCard izdCard) throws DataIntegrityException {
        if (o.getApplicationType() != null && o.getApplicationType().startsWith(Constants.PIN_REMINDER_BT_ACTION)) {
            log.info("isIbePin, applicationType = {}",  Constants.PIN_REMINDER_BT_ACTION);
            return StringUtils.equals(izdCard.getIdCard(), izdCard.getIzdAgreement().getIzdClient().getPersonCode());
        } else {
            return isEPin(o, izdCard, Constants.INFORMATION_CHANGE_ACTION.equalsIgnoreCase(o.getAction()));
        }
    }

    @Override
    public boolean isNeedToGeneratePin(Order order) throws DataIntegrityException {
        IzdCard izdCard = (IzdCard) cardDAO.getObject(IzdCard.class, order.getCardNumber());
        return isIbePin(order, izdCard);
    }

    @Override
    public String getDefaultCountry() {
        return lv.bank.cards.core.utils.Constants.DEFAULT_COUNTRY_LT;
    }

    @Override
    public String getDeliveryBranch() {
        return lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_9999;
    }

    @Override
    public String getNextPcdPinIDWithAuthenticationCode(String orderId) throws DataIntegrityException {
        return cardsDAO.getNextPcdPinIDWithAuthentificationCode();
    }

    @Override
    public void processExtraFieldsCountrySpecific(CMSCallAPIWrapper.UpdateCardXML updateCardXML, Order order,
                                                  String uAfield1, String uAfield2) throws CMSSoapAPIException {

        if(!Constants.INFORMATION_CHANGE_ACTION.equals(order.getAction()) || isPinReminder(order)
                || Constants.APPLICATION_TYPE_CARD_AUTO_RENEW.equals(order.getApplicationType())){
            updateCardXML.setElement("U_AFIELD3", order.getOrderId() == null ? "" : order.getOrderId());
        }

        if (!StringUtils.isBlank(order.getOwnerPersonId())) {
            updateCardXML.setElement("ID_CARD", order.getOwnerPersonId());
        }
        if (isPinReminder(order)) {
            pinReminder(order);
            PcdCard thisCard = cardsDAO.findByCardNumber(order.getCardNumber());
            thisCard.setUAField1(uAfield1);
            thisCard.setUAField2(uAfield2);
            thisCard.setUField8(order.getOrderId());
            cardsDAO.saveOrUpdate(thisCard);
        }
    }

    public static boolean isPinReminder(Order order) {
        return order.getApplicationType() != null && order.getApplicationType().startsWith(Constants.PIN_REMINDER_ACTION);
    }

    protected RowTypeOrderPinEnvelope pinReminder(Order order) throws CMSSoapAPIException {
        RowTypeOrderPinEnvelope c = Mapper.orderToRowTypeOrderPinEnvelope(order);
        RowTypeOrderPinEnvelope rowTypeOrderPinEnvelope = cmsSoapAPIWrapperBean.orderPinEnvelope(c);
        return rowTypeOrderPinEnvelope;
    }

    public Mapper getMapper() {
        return (Mapper) super.mapper;
    }

    protected boolean clientPersonCodeChanged(Order order, IzdClient izdClient) {
        if (Constants.CLIENT_TYPE_PRIVATE.equals(order.getClientType()) &&
                getMapper().hasChange(order.getClientPersonId(), izdClient.getPersonCode())){
            return true;
        }
        return false;
    }

    public void updateCardholderPersonCode(String oldPersonCode, String newPersonCode, String country, String clientName){
        log.info("Updating cardholder person code, old: {}, new: {}, client name: {}", oldPersonCode, newPersonCode, clientName);
        
        if (StringUtils.isBlank(oldPersonCode)){
            log.info("Previous client person code in CMS was blank. Will not search for cardholders.");
            return;
        }

        log.info("Looking for cards with cardholder person code: {}", oldPersonCode);
        List<PcdCard> pcdCards = cardsDAO.getCardsByCardholderPersonCode(oldPersonCode, country);

        if (pcdCards.isEmpty()){
            log.info("No cards were found");
        }

        for (PcdCard pcdCard : pcdCards) {
            log.info("Processing card: {}", pcdCard.getCard());

            if (pcdCard.getStatus1().equals("2")){
                log.info("Card is closed, Skipping");
                continue;
            }

            int nameSimilarity = similarityPercentage(normalizeNameForDiff(clientName), normalizeNameForDiff(pcdCard.getCardName()));
            log.info("Client name: {} (normalized: {}), cardholder name: {} (normalized: {}), similarity: {}%",
                    clientName, normalizeNameForDiff(clientName), pcdCard.getCardName(), normalizeNameForDiff(pcdCard.getCardName()), nameSimilarity);

            if (nameSimilarity < Constants.NAME_DIFF_THRESHOLD){
                log.info("Client and cardholder names are not similar enough (threshold {}%). " +
                        "This could be a different person. Will not update cardholder person code.", Constants.NAME_DIFF_THRESHOLD);
            }
            else {
                // CMS
                RowTypeEditCardRequest editCardRequest = new RowTypeEditCardRequest();
                editCardRequest.setCARD(pcdCard.getCard());
                editCardRequest.setIDCARD(newPersonCode);
                // LinkApp
                pcdCard.setIdCard(newPersonCode);

                try {
                    cmsSoapAPIWrapperBean.editCard(editCardRequest);
                    cardsDAO.saveOrUpdate(pcdCard);
                    log.info("Changed person code for card {} from {} to {}", pcdCard.getCard(), oldPersonCode, newPersonCode);
                } catch (CMSSoapAPIException e) {
                    log.error("Could not change person code for card {} from {} to {}, pcdCard.getCard(), oldPersonCode, newPersonCode");
                    e.printStackTrace();
                }
            }
        }
    }
}

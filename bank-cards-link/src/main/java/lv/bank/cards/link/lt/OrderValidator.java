package lv.bank.cards.link.lt;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.entity.cms.IzdCountry;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.link.Constants;
import lv.bank.cards.core.utils.DeliveryDetailsHelperBase;
import lv.bank.cards.link.Order;
import lv.bank.cards.link.OrderValidatorBase;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang.StringUtils.defaultIfBlank;

@Slf4j
public class OrderValidator extends OrderValidatorBase {

    public OrderValidator(CommonDAO commonDAO, CardDAO cardDAO, CardsDAO cardsDAO, ClientDAO clientDAO, Mapper mapper) {
        super(commonDAO, cardDAO, cardsDAO, clientDAO, mapper);
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
    public void validateOrder(Order order) throws DataIntegrityException {

        validateAndPrepareOrderBase(order);
        prepareRenewalWithProductChange(order);

        if (Constants.CARD_REPLACE_ACTION.equals(order.getAction())) {
            if ("5".equals(order.getReplacementReason())) {
                order.setAction(Constants.CARD_RENEW_ACTION);
            }
        }

        try {
            IzdCard card = cardDAO.getCardByTrackingNo(order.getOrderId());
            if (card != null && !card.getCard().equals(order.getCardNumber())) {
                throw new DataIntegrityException("Order number is already used");
            }
        } catch (SQLException e) {
            throw new DataIntegrityException(e.getMessage());
        }

        IzdCard izdCard = null;

        if (StringUtils.isNotBlank(order.getCardNumber())) {
            izdCard = (IzdCard) cardDAO.getObject(IzdCard.class, order.getCardNumber());
        } else {
            log.info("validateOrder, cardNumber is empty");
        }

        deliveryDetailsHelperCheck(order, izdCard);

        if (Constants.INFORMATION_CHANGE_ACTION.equals(order.getAction())) {

            IzdClient izdClient;

            if (StringUtils.isBlank(order.getCardNumber())) {
                izdClient = clientDAO.findByCif(order.getClientNumberInAbs());
                if (izdClient == null) {
                    throw new DataIntegrityException("Can't find client [" + order.getClientNumberInAbs() + "] in CMS");
                }
            } else {
                if (izdCard == null) {
                    throw new DataIntegrityException("Can't find card [" + order.getCardNumber() + "] in CMS");
                }
                izdClient = izdCard.getIzdAgreement().getIzdClient();
            }
            if (!izdClient.getClType().equals(order.getClientType())) {
                throw new DataIntegrityException("Can't change client_type from [" + izdClient.getClType() + "] to [" + order.getClientType() + "]");
            }
        }
    }

    protected void deliveryDetailsHelperCheck(Order order, IzdCard izdCard) throws DataIntegrityException {

        Set<String> actionsForCreatingAlternativeDetails = new HashSet<>();
        actionsForCreatingAlternativeDetails.add(Constants.CARD_RENEW_ACTION);
        actionsForCreatingAlternativeDetails.add(Constants.CARD_DUPLICATE_ACTION);
        actionsForCreatingAlternativeDetails.add(Constants.CARD_REPLACE_ACTION);
        actionsForCreatingAlternativeDetails.add(Constants.INFORMATION_CHANGE_ACTION);

        final String izdCardBranch = izdCard != null ? izdCard.getUCod10() : null;

        log.info("deliveryDetailsHelperCheck, action = {} branch = {}, izdCard = {}, izdCardBranch = {}",
                order.getAction(), order.getBranchToDeliverAt(), izdCard, izdCardBranch);

        if (actionsForCreatingAlternativeDetails.contains(order.getAction())
                && (getDeliveryBranch().equals(order.getBranchToDeliverAt()) ||
                (StringUtils.isBlank(order.getBranchToDeliverAt()) && lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_080.equals(izdCardBranch))
        )
        ) {
            DeliveryDetailsHelperBase details = getMapper().orderToDeliveryDetailsHelper(order);
            details.checkData();
        }
    }

    protected void prepareRenewalWithProductChange(Order order) throws DataIntegrityException {
        if (!StringUtils.isBlank(order.getOldCardNumber())) {
            log.info("Adding fields missing from order to process renewal with product change");

            IzdCard oldCard = (IzdCard) cardDAO.getObject(IzdCard.class, order.getOldCardNumber());
            if (oldCard == null){
                throw new DataIntegrityException("Cannot find card " + order.getOldCardNumber());
            }

            IzdClient izdClient = oldCard.getIzdAgreement().getIzdClient();
            IzdCountry izdCountry = (IzdCountry) this.cardDAO.getObject(IzdCountry.class, izdClient.getRCntry());

            // general fields required thorough order processing
            if (StringUtils.isBlank(order.getClient())){
                String client = izdClient.getComp_id().getClient();
                order.setClient(client);
            }

            if (StringUtils.isBlank(order.getCardAccountNoCms())){
                IzdAccount account = oldCard.getIzdClAcct().getIzdAccount();
                order.setAccountNoCms(account.getAccountNo());
                order.setCardAccountNoCms(account.getCardAcct());
            }

            if (StringUtils.isBlank(order.getClientType())){
                order.setClientType(izdClient.getClType());
            }

            if (StringUtils.isBlank(order.getClientPersonId())){
                order.setClientPersonId(izdClient.getPersonCode());
            }

            // fields for agreement creation
            if (StringUtils.isBlank(order.getBranch())){
                order.setBranch(oldCard.getIzdAgreement().getIzdBranch().getRegKodsUr());
            }

            if (StringUtils.isBlank(order.getClientStreet())){
                order.setClientStreet(izdClient.getRStreet());
            }

            if (StringUtils.isBlank(order.getClientCity())){
                order.setClientCity(izdClient.getRCity());
            }

            if (StringUtils.isBlank(order.getClientZip())){
                order.setClientZip(izdClient.getRPcode());
            }

            if (StringUtils.isBlank(order.getClientCountry())){
                order.setClientCountry(izdCountry.getCountryShort());
            }

            if (StringUtils.isBlank(order.getEmail())){
                order.setEmail(oldCard.getIzdAgreement().getEMails());
            }

            if (StringUtils.isBlank(order.getReportLanguage())){
                order.setReportLanguage(defaultIfBlank(oldCard.getIzdAgreement().getRepLang(), Constants.DEFAULT_REP_LANG));
            }

            // fields for card creation
            if (StringUtils.isBlank(order.getBranchToDeliverAt())) {
                order.setBranchToDeliverAt(commonDAO.getExternalBranchId(oldCard.getUCod10()));
            }

            if (StringUtils.isBlank(order.getOwnerPersonId())) {
                order.setOwnerPersonId(oldCard.getIdCard());
            }

            if (StringUtils.isBlank(order.getCrdPasswd())){
                order.setCrdPasswd(oldCard.getMName());
            }

            if (StringUtils.isBlank(order.getAuthNotifyDestination())){
                order.setAuthNotifyDestination(oldCard.getUField7());
            }

            if (StringUtils.isBlank(order.getUCod9())){
                order.setUCod9(oldCard.getUCod9());
            }

            if (StringUtils.isBlank(order.getCardConditionSet())){
                order.setCardConditionSet(oldCard.getCondSet());
            }

            if (StringUtils.isBlank(order.getDesignId())){
                order.setDesignId(oldCard.getUField8());
            }

            if (StringUtils.isBlank(order.getCardHolderEmployerName())){
                order.setCardHolderEmployerName(oldCard.getCmpgName());
            }

            if (StringUtils.isBlank(order.getAccountCcy())){
                order.setAccountCcy(oldCard.getIzdClAcct().getIzdAccount().getIzdAccParam()
                        .getIzdCardGroupCcy().getComp_id().getCcy());
            }

            order.setBaseSupp(oldCard.getBaseSupp());

            // mark old card as "do not renew"
            cardsDAO.setAutomaticRenewFlag(oldCard.getCard(), "N"); // LinkApp
            cardDAO.setAutomaticRenewFlag(oldCard.getCard(), "N"); // CMS

            log.info("Order after adding missing fields = {}", order);
        }
    }

    protected void validatePinReminder(Order order) throws DataIntegrityException {
        if (StringUtils.isEmpty(order.getCardNumber())) {
            throw new DataIntegrityException("Card number must be specified");
        }

        if (StringUtils.isEmpty(order.getOrderId())) {
            throw new DataIntegrityException("Order id must be specified");
        }

        PcdCard thisCard = cardsDAO.findByCardNumber(order.getCardNumber());
        if (thisCard == null) {
            throw new DataIntegrityException("Card with given number couldn't be found (pcd)");
        }

        IzdCard izdCard = (IzdCard) this.cardDAO.getObject(IzdCard.class, order.getCardNumber());
        if (izdCard == null) {
            throw new DataIntegrityException("Card with given number couldn't be found (izd)");
        }

        if (thisCard.getDeliveryBlock() == null) {
            throw new DataIntegrityException("Pin reminder could not be ordered while card is in production");
        }

        if ("7".equals(izdCard.getRenew())) {
            throw new DataIntegrityException("Pin reminder is already ordered");
        }

        Set<String> statuses = new HashSet<>();
        statuses.add("J");
        statuses.add("D");
        statuses.add("R");
        statuses.add("E");
        statuses.add("G");

        if (!statuses.contains(izdCard.getStopCause())) {
            throw new DataIntegrityException("Card stop cause is not allowed: " + izdCard.getStopCause());
        }

        if (izdCard.getIzdCardsPinBlock() == null || izdCard.getIzdCardsPinBlock().getPinBlock() == null) {
            throw new DataIntegrityException("Card missing PIN Block");
        }
    }

    public Mapper getMapper() {
        return (Mapper) super.mapper;
    }


}

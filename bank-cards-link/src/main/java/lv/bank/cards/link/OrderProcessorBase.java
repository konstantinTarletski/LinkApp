package lv.bank.cards.link;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.AccountDAO;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.cms.dto.IzdCondCardDAO;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdClAcct;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dao.ClientsDAO;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.DeliveryDetailsHelperBase;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.ejb.CMSSoapAPIWrapperBean;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCardRequest;
import lv.bank.cards.rtcu.util.BankCardsWSWrapperDelegate;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

@Slf4j
public abstract class OrderProcessorBase {

    protected final Properties prettifyRules = new Properties();
    protected final CommonDAO commonDAO;
    protected final CardDAO cardDAO;
    protected final CardsDAO cardsDAO;
    protected final AccountDAO accountDAO;
    protected final ClientDAO clientDAO;
    protected final ClientsDAO clientsDAO;

    protected final CMSSoapAPIWrapperBean cmsSoapAPIWrapperBean;
    protected final CMSCallAPIWrapper cmsCallAPIWrapper;
    protected final BankCardsWSWrapperDelegate bankCardsWSWrapperDelegate;
    protected final MapperBase mapper;

    public OrderProcessorBase(CMSSoapAPIWrapperBean cmsSoapAPIWrapperBean, CMSCallAPIWrapper cmsCallAPIWrapper,
                              BankCardsWSWrapperDelegate bankCardsWSWrapperDelegate, MapperBase mapper,
                              CommonDAO commonDAO, CardDAO cardDAO, CardsDAO cardsDAO, AccountDAO accountDAO,
                              ClientDAO clientDAO, ClientsDAO clientsDAO) {
        super();
        try (InputStream is = OrderProcessorBase.class.getResourceAsStream("/Prettify.properties")) {
            prettifyRules.load(is);
        } catch (Exception e) {
            log.warn("OrderProcessor, Prettify.properties can not be loaded.", e);
        }
        this.cmsSoapAPIWrapperBean = cmsSoapAPIWrapperBean;
        this.cmsCallAPIWrapper = cmsCallAPIWrapper;
        this.bankCardsWSWrapperDelegate = bankCardsWSWrapperDelegate;
        this.mapper = mapper;
        this.commonDAO = commonDAO;
        this.cardDAO = cardDAO;
        this.cardsDAO = cardsDAO;
        this.accountDAO = accountDAO;
        this.clientDAO = clientDAO;
        this.clientsDAO = clientsDAO;
    }

    protected void processNormalOrder(Order order) throws DataIntegrityException, CMSSoapAPIException, CMSCallAPIException {

        log.info("processAnyOrder incoming order = {}", order);

        if (StringUtils.isNotBlank(order.getPrettyfyCard())) {
            prettifyCMSInformation(order);
        }

        log.info("processAnyOrder, validation completed, result action = {}", order.getAction());

        switch (order.getAction()) {
            case Constants.CARD_RENEW_ACTION: {
                renewCard(order);
            }
            break;
            case Constants.CARD_REPLACE_ACTION: {
                replaceCard(order);
            }
            break;
            case Constants.CARD_CREATE_ACTION: {
                newCard(order);
                addIbanToAccount(order);
            }
            break;
            case Constants.INFORMATION_CHANGE_ACTION: {
                informationChange(order);
            }
            break;
            case Constants.CARD_DUPLICATE_ACTION: {
                throw new DataIntegrityException("processNormalOrder, card duplication not permitted");
            }
            default: {
                throw new DataIntegrityException("processNormalOrder, action = " + order.getAction() + " not supported");
            }
        }

        log.info("processNormalOrder END, order = {}", order);
    }

    public abstract void processOrder(Order order) throws DataIntegrityException, CMSSoapAPIException, CMSCallAPIException;

    public abstract String getDefaultCountry();

    public abstract String getDeliveryBranch();

    public abstract boolean isNeedToGeneratePin(Order order) throws DataIntegrityException;

    public abstract void processExtraFieldsCountrySpecific(CMSCallAPIWrapper.UpdateCardXML updateCardXML, Order order,
                                                           String uAfield1, String uAfield2) throws CMSSoapAPIException;


    public abstract String getNextPcdPinIDWithAuthenticationCode(String orderId) throws DataIntegrityException;

    public abstract void renewCard(Order order) throws DataIntegrityException, CMSSoapAPIException;

    public abstract void replaceCard(Order order) throws DataIntegrityException, CMSSoapAPIException;

    public abstract void newCard(Order order) throws DataIntegrityException, CMSSoapAPIException, CMSCallAPIException;

    public abstract void informationChange(Order order) throws DataIntegrityException, CMSSoapAPIException, CMSCallAPIException;

    protected void putNewCardToOrder(Order order, List<RowTypeCardInfo> cardList) {
        if (cardList != null && cardList.size() == 1) {
            log.info("putNewCardToOrder, card = {}", cardList.get(0).getLogicalCard().getCARD());
            order.setCardNumber(cardList.get(0).getLogicalCard().getCARD());
        } else {
            log.warn("putNewCardToOrder, cmsSoapAPIWrapper.newCard returned more than 1 card !");
        }
    }

    protected void prettifyCMSInformation(Order order) throws CMSSoapAPIException {

        log.info("prettifyCMSInformation, incoming order = {}", order);

        if (prettifyRules.isEmpty()) {
            log.info("prettifyCMSInformation, not rules to prettify, exit");
            return;
        }

        for (Enumeration<Object> e = prettifyRules.keys(); e.hasMoreElements(); ) {
            String ruleName = (String) e.nextElement();
            String rule = (String) prettifyRules.get(ruleName);

            // check rule
            // each rule must be in format |RegExp|Repl
            // instead | you can use anything, but it can be special char in each rule
            String replacement = rule.substring(rule.lastIndexOf(rule.charAt(0)));
            replacement = replacement.substring(1);
            rule = rule.substring(1, rule.length() - replacement.length() - 1);

            log.info("prettifyCMSInformation, rule: {} replacement: {}", rule, replacement);

            if (ruleName.startsWith("cardCond.")) {
                // This one claims to be a card condition set modifier...
                log.info("prettifyCMSInformation, Try to modify conditions");
                IzdCondCardDAO condCard = new IzdCondCardDAO(cardDAO.getIzdCondCardByCard(order.getCardNumber()));
                log.info("prettifyCMSInformation, Card condition set of the current card = {}", condCard.getCondSet());

                if (!condCard.getCondSet().equals(condCard.getCondSet().replaceAll(rule, replacement))) {
                    // Yes, we really will change this card condition
                    log.info("prettifyCMSInformation, Will change it !");

                    String condSet = condCard.getCondSet().replaceAll(rule, replacement);
                    RowTypeEditCardRequest request = mapper.orderToRowTypeEditCardRequest(order, condSet);
                    cmsSoapAPIWrapperBean.editCard(request);
                }
            }
        }
    }

    public void processExtraFields(Order order) throws DataIntegrityException, CMSSoapAPIException {

        log.info("processExtraFields, CardNumber = {}, OrderNumber = {}", CardUtils.maskCardNumber(order.getCardNumber()), order.getOrderId());

        if (StringUtils.isNotBlank(order.getCardNumber())) {

            String branch = mapper.getBranchIdByExternalId(order.getBranchToDeliverAt());
            DeliveryDetailsHelperBase details = mapper.orderToDeliveryDetailsHelper(order);

            log.info("processExtraFields, CardNumber = {}, OrderNumber = {}, Delivery address = {}, branch = {}",
                    CardUtils.maskCardNumber(order.getCardNumber()), order.getOrderId(), details.getDetails(), branch);

            CMSCallAPIWrapper.UpdateCardXML updateCardXML = cmsCallAPIWrapper.new UpdateCardXML(order.getCardNumber(), order.getBankc(), order.getGroupc());

            updateCardXML.setElement("REGION", order.getCountry());
            updateCardXML.setElement("U_BFIELD1", details.getDetails());
            updateCardXML.setElement("U_COD10", branch);

            String uAfield1 = null;
            String uAfield2 = null;

            if (isNeedToGeneratePin(order)) {
                uAfield1 = getNextPcdPinIDWithAuthenticationCode(order.getOrderId());
                uAfield2 = "REQUESTED";
                log.info("processExtraFields, generated PIN ID and authentication = {}", uAfield1);
                updateCardXML.setElement("U_AFIELD1", uAfield1);
                updateCardXML.setElement("U_AFIELD2", uAfield2);
            }

            processExtraFieldsCountrySpecific(updateCardXML, order, uAfield1, uAfield2);

            if (StringUtils.isNotBlank(order.getMigratedCardNumber())) {
                log.info("Migrated card, will save PIN retaining fields");
                PinRetainingHelper retainPin = new PinRetainingHelper(
                        order.getMigratedCardNumber(),
                        order.getMigratedCardPinBlock(),
                        order.getMigratedCardPinKeyId()
                );
                updateCardXML.setElement("U_AFIELD4", retainPin.getData());
            }

            CMSCallAPIWrapper.UpdateDBWork work = cmsCallAPIWrapper.new UpdateDBWork();
            work.setInputXML(updateCardXML.getDocument());
            String response = cardDAO.doWork(work);

            if (!"success".equals(response)) {
                log.error(response);
                throw new DataIntegrityException("Did not update delivery details, response = " + response);
            }
        } else {
            log.info("processExtraFields, no any actions needed, cardNumber is empty");
        }
    }

    public IzdAccount findAccountByIzdClientAndExternalNo(String clientId, String externalNo) throws DataIntegrityException {
        log.info("findAccountByIzdClientAndExternalNo clientId = {}, externalNo = {}", clientId, externalNo);
        List<IzdAccount> accountList = accountDAO.findByIzdClientAndExternalNo(clientId, externalNo);

        if (accountList.isEmpty()) {
            return null;
        } else if (accountList.size() != 1) {
            throw new DataIntegrityException("Can not find exactly 1 IzdAccount by searching criteria");
        }
        return accountList.get(0);
    }

    @SneakyThrows
    public void addIbanToAccount(Order order) {

        if (StringUtils.isBlank(order.getIban())) {
            log.warn("Order is missing IBAN data, will not populate IBAN in CMS");
            return;
        }

        IzdCard izdCard = (IzdCard) cardDAO.getObject(IzdCard.class, order.getCardNumber());

        if (izdCard == null) {
            throw new DataIntegrityException("Could not find card by number: {} " + order.getCardNumber());
        }

        IzdClAcct izdClAcct = izdCard.getIzdClAcct();

        if (izdClAcct == null) {
            throw new DataIntegrityException("Could not find izdClAcct for card: " + order.getCardNumber());
        }

        if (izdClAcct.getIban() == null) {
            izdClAcct.setIban(order.getIban());
            cardDAO.saveOrUpdate(izdClAcct);
            log.info("addIbanToAccount, linked IBAN {} to card account", izdClAcct.getIban());
        }
    }

}

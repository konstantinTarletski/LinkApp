package lv.bank.cards.link.lv;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.AccountDAO;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdClient;
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
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeRenewCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeReplaceCard;
import lv.bank.cards.link.Constants;
import lv.bank.cards.link.Order;
import lv.bank.cards.link.OrderProcessorBase;
import lv.bank.cards.rtcu.util.BankCardsWSWrapperDelegate;
import org.apache.commons.lang.StringUtils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderProcessor extends OrderProcessorBase {

    public OrderProcessor(CMSSoapAPIWrapperBean cmsSoapAPIWrapperBean, CMSCallAPIWrapper cmsCallAPIWrapper,
                          BankCardsWSWrapperDelegate bankCardsWSWrapperDelegate, Mapper mapper,
                          CommonDAO commonDAO, CardDAO cardDAO, CardsDAO cardsDAO, AccountDAO accountDAO,
                          ClientDAO clientDAO, ClientsDAO clientsDAO) {
        super(cmsSoapAPIWrapperBean, cmsCallAPIWrapper, bankCardsWSWrapperDelegate, mapper, commonDAO, cardDAO, cardsDAO,
                accountDAO, clientDAO, clientsDAO);
    }

    @Override
    public void processOrder(Order order) throws DataIntegrityException, CMSSoapAPIException, CMSCallAPIException {
        log.info("processOrder, BEGIN, order = {}", order);
        if (!isRepeatedOrder(order)) {
            processNormalOrder(order);
        }
        processExtraFields(order);
        cardRenaming(order);
        log.info("processOrder END, orderId = {}, action = {}", order.getOrderId(), order.getAction());
    }

    @Override
    public void informationChange(Order order) throws DataIntegrityException {
        throw new DataIntegrityException("informationChange, information change not permitted");
    }

    @Override
    public void renewCard(Order order) throws DataIntegrityException, CMSSoapAPIException {
        // If card is marked as "do not renew", then this flag has to be removed
        // before submitting a renewal request to CMS
        IzdCard izdCard = (IzdCard) cardDAO.getObject(IzdCard.class, order.getCardNumber());
        if ("N".equals(izdCard.getRenew())) {
            linkAppInformationChangeCall(order.getCardNumber());
        }

        RowTypeRenewCard c = Mapper.orderToRowTypeRenewCard(order);
        RowTypeRenewCard renewCard = cmsSoapAPIWrapperBean.renewCard(c);
        order.setCardNumber(renewCard.getNEWCARD());
    }

    @Override
    public void replaceCard(Order order) throws DataIntegrityException, CMSSoapAPIException {
        // Block card for replacement
        if (StringUtils.isNotBlank(order.getAutoBlockCard())) {
            linkAppAddCardToStopListCall(order.getCardNumber(), order.getAutoBlockCard());
        }

        RowTypeReplaceCard c = Mapper.orderToRowTypeReplaceCard(order);
        RowTypeReplaceCard replacedCard = cmsSoapAPIWrapperBean.replaceCard(c);
        order.setCardNumber(replacedCard.getNEWCARD());
    }

    @Override
    public void newCard(Order order) throws DataIntegrityException, CMSSoapAPIException {

        // Block old card if cardReplace action was substituted with cardCreate
        String blockCardNo = order.getBlockCardNo();
        if (blockCardNo != null && blockCardNo.trim().length() == 16) {
            linkAppAddCardToStopListCall(blockCardNo, order.getAutoBlockCard());
        }

        order.setClient(resolveCustomer(order));
        log.info("processAnyOrder, client set to order = {}", order.getClient());

        IzdAccount account = findAccountByIzdClientAndExternalNo(order.getClient(), order.getAccountNoPlaton());

        log.info("processAnyOrder, account for country = {} with cif = {} IzdAccount = {}",
                order.getCountry(), order.getClientNumberInAbs(), account);

        if (account != null) {
            log.info("processAnyOrder, account status = {}", account.getIzdAccParam().getStatus());
            switch (account.getIzdAccParam().getStatus()) {
                case "0": // Do nothing and use this account for card
                    break;
                case "3":
                    log.info("processAnyOrder, account is dormant. Will activate.");
                    linkAppActivateDormantCall(account.getAccountNo().toString(),
                            account.getIzdAccParam().getIzdCardGroupCcy().getComp_id().getCcy());
                    break;
                case "4":
                    log.info("processAnyOrder, account is closed. Will unlink Platon account from it and create a new CMS account.");
                    linkAppUnlinkAccountCall(account.getAccountNo().toString(),
                            account.getIzdAccParam().getUfield5());
                    account = null;
                    break;
                default:
                    log.warn("processAnyOrder, unknown account status {}. Will create a new CMS account.", account.getIzdAccParam().getStatus());
                    account = null;
                    break;
            }
        }

        if (account != null) {
            order.setAccountNoCms(account.getAccountNo());
            order.setCardAccountNoCms(account.getCardAcct());
        }

        RowTypeAgreement agreement = getMapper().orderToRowTypeAgreement(order);
        RowTypeCardInfo cardInfo = getMapper().orderToRowTypeCardInfo(order);
        RowTypeAccountInfo accountInfo = getMapper().orderRowTypeAccountInfo(order);

        List<RowTypeAccountInfo> accountInfoList = new ArrayList<>();
        accountInfoList.add(accountInfo);

        List<RowTypeCardInfo> cardsListInfo = new ArrayList<>();
        cardsListInfo.add(cardInfo);

        List<RowTypeCardInfo> cardList = cmsSoapAPIWrapperBean.newCard(agreement, accountInfoList, cardsListInfo);
        putNewCardToOrder(order, cardList);
    }

    protected String resolveCustomer(Order order) throws DataIntegrityException, CMSSoapAPIException {
        String client;

        IzdClient izdClient = clientDAO.findByCif(
                order.getClientNumberInAbs(), order.getAccountNoPlaton(), order.getCountry());
        log.info("processAnyOrder, current client for country = {} with cif = {} IzdClient = {}",
                order.getCountry(), order.getClientNumberInAbs(), izdClient);

        if (izdClient != null) {
            log.info("Found CMS client {}", izdClient.getComp_id().getClient());
            client = izdClient.getComp_id().getClient();
        } else {
            RowTypeCustomer customer = getMapper().orderToRowTypeCustomer(order);
            RowTypeCustomer newCustomer = cmsSoapAPIWrapperBean.newCustomer(customer, null);
            client = newCustomer.getCLIENT();
            log.info("processAnyOrder, created new customer = {}", newCustomer.getCLIENT());

            // newCustomer WS prevents creating 2 clients with the same CLIENT_B (CIF),
            // however CIFs for customers can collide between LV/EE Platon instances,
            // therefore we need to setup CLIENT_B using CMS DB API after client has been created in CMS.
            linkClientToCif(client, order.getClientNumberInAbs());
        }
        return client;
    }

    protected void cardRenaming(Order order) throws DataIntegrityException {

        if ("true".equalsIgnoreCase(order.getReplaceName())) {
            CMSCallAPIWrapper.UpdateDBWork workClient = cmsCallAPIWrapper.new UpdateDBWork();

            CMSCallAPIWrapper.UpdateCardXML updateCardXML = cmsCallAPIWrapper.new UpdateCardXML(order.getCardNumber(), order.getBankc(), order.getGroupc());
            updateCardXML.setElement("CARD_NAME", order.getCardName());
            updateCardXML.setElement("MC_NAME", cardNameToMagneticTrackFormat(order.getCardName()));
            workClient.setInputXML(updateCardXML.getDocument());
            String response = cardDAO.doWork(workClient);
            if (!"success".equals(response)) {
                log.error(response);
                throw new DataIntegrityException("Did not update client details");
            }

            // update client name only for private customers
            if (order.getClientType().equals(Constants.CLIENT_TYPE_PRIVATE)) {
                String thisClient = clientDAO.findClientNoByCardNo(order.getCardNumber());
                if (thisClient == null) {
                    throw new DataIntegrityException("Cannot find client");
                }
                CMSCallAPIWrapper.UpdateClientXML updateClientXML = cmsCallAPIWrapper.new UpdateClientXML(thisClient, order.getBankc());
                updateClientXML.setElement("F_NAMES", order.getClientFirstname());
                updateClientXML.setElement("SURNAME", order.getClientLastname());

                workClient.setInputXML(updateClientXML.getDocument());
                response = cardDAO.doWork(workClient);
                if (!"success".equals(response)) {
                    log.error(response);
                    throw new DataIntegrityException("Did not update client details");
                }
            }
        }
    }

    @Override
    public String getDefaultCountry() {
        return lv.bank.cards.core.utils.Constants.DEFAULT_COUNTRY_LV;
    }

    @Override
    public String getDeliveryBranch() {
        return lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888;
    }

    @Override
    public boolean isNeedToGeneratePin(Order order){
        IzdCard izdCard = (IzdCard) cardDAO.getObject(IzdCard.class, order.getCardNumber());
        return getDeliveryBranch().equals(order.getBranchToDeliverAt()) &&
                (izdCard.getIzdCardsPinBlock() == null || izdCard.getIzdCardsPinBlock().getPinBlock() == null);
    }

    @Override
    public String getNextPcdPinIDWithAuthenticationCode(String orderId) throws DataIntegrityException {
        return cardsDAO.getNextPcdPinIDWithAuthentificationCode(orderId);
    }

    @Override
    public void processExtraFieldsCountrySpecific(CMSCallAPIWrapper.UpdateCardXML updateCardXML, Order order,
                                                  String uAfield1, String uAfield2) {
        updateCardXML.setElement("U_FIELD8", order.getOrderId() == null ? "" : order.getOrderId());
    }

    public static String cardNameToMagneticTrackFormat(String cardName) {
        if (cardName == null || cardName.trim().equals("")) {
            return "";
        }
        String latin = Normalizer.normalize(cardName, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String name = StringUtils.substringBeforeLast(latin, " ");
        String surname = StringUtils.substringAfterLast(latin, " ");
        return surname + "/" + name;
    }

    public static boolean isRepeatedOrder(Order order) {
        return StringUtils.isNotEmpty(order.getRepeated()) && order.getRepeated().trim().equalsIgnoreCase("true");
    }


    protected void linkClientToCif(String client, String cif) throws DataIntegrityException {
        String result = bankCardsWSWrapperDelegate.rtcungCall("<do what=\"link-client-to-cif\"><client>" + client + "</client><cif>" + cif + "</cif></do>");
        if (!result.contains("<link-client-to-cif>done</link-client-to-cif>")) {
            throw new DataIntegrityException("linkClientToCif, could not link customer " + client + " to Platon CIF " + cif);
        }
        log.info("linkClientToCif, linked customer {} to Platon CIF {}", client, cif);
    }

    protected String linkAppAddCardToStopListCall(String cardNumber, String autoBlockCard) throws DataIntegrityException {
        log.info("linkAppAddCardToStopListCall");
        String result = bankCardsWSWrapperDelegate.rtcungCall("<do what=\"add-card-to-stoplist\"><card>" + cardNumber
                + "</card><description>Auto-blocked for replacement</description><cause>" + autoBlockCard + "</cause></do>");
        if (!result.contains("added")) {
            throw new DataIntegrityException(result);
        }
        return result;
    }

    protected String linkAppInformationChangeCall(String cardNumber) throws DataIntegrityException {
        log.info("linkAppInformationChangeCall");
        String result = bankCardsWSWrapperDelegate.rtcungCall("<do what=\"information-change\"><card pan=\"" + cardNumber + "\"><auto-renew>1</auto-renew></card></do>");
        if (!result.contains("OK")) {
            throw new DataIntegrityException(result);
        }
        return result;
    }

    protected String linkAppActivateDormantCall(String accountNo, String ccy) throws DataIntegrityException {
        log.info("linkAppActivateDormantCall");
        String result = bankCardsWSWrapperDelegate.rtcungCall("<do what=\"activate-dormant\">"
                + "<account>" + accountNo + "</account>"
                + "<comment>Activate account to add new card</comment>"
                + "<ccy>" + ccy + "</ccy>"
                + "</do>");
        if (!result.contains("done")) {
            throw new DataIntegrityException(result);
        }
        return result;
    }

    protected String linkAppUnlinkAccountCall(String cardAccount, String platonAccount) throws DataIntegrityException {
        log.info("linkAppUnlinkAccountCall");
        String result = bankCardsWSWrapperDelegate.rtcungCall("<do what=\"unlink-account\">"
                + "<card-account>" + cardAccount + "</card-account>"
                + "<platon-account>" + platonAccount + "</platon-account>"
                + "</do>");
        if (!result.contains("done")) {
            throw new DataIntegrityException(result);
        }
        return result;
    }


    public Mapper getMapper(){
        return (Mapper) super.mapper;
    }

}

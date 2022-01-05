package lv.bank.cards.link.lt;

import lombok.SneakyThrows;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCondCard;
import lv.bank.cards.core.entity.cms.IzdCondCardPK;
import lv.bank.cards.core.entity.cms.IzdCountry;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.soap.types.ListTypeCustomerCustomInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAgreement;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfoLogicalCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCustomer;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCardRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCustomerRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeRenewCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeReplaceCard;
import lv.bank.cards.link.Constants;
import lv.bank.cards.link.Order;
import lv.bank.cards.link.TestBase;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FlowTest extends TestBase {

    @Before
    @SneakyThrows
    public void initCMSSoapAPIWrapper() {
        super.initCMSSoapAPIWrapper();
        when(commonDAO.getIzdCountryByShortCountryCode(anyString())).thenReturn(new IzdCountry("LT", null, null, null));
        when(commonDAO.getBranchIdByExternalId(eq(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_9999))).thenReturn(EXTERNAL_BRANCH);
        mapper = new Mapper(commonDAO, cardDAO, cardsDAO, clientDAO, clientsDAO, productDAO, accountsDAO);
        orderValidator = new OrderValidator(commonDAO, cardDAO, cardsDAO, clientDAO, getMapper());
        orderProcessor = new OrderProcessor(cmsSoapAPIWrapperBean, cmsCallAPIWrapper, bankCardsWSWrapperDelegate,
                getMapper(), commonDAO, cardDAO, cardsDAO, accountDAO, clientDAO, clientsDAO);
    }

    @Test
    public void checkAgeOlderThan14() {
        // date format in person code yyMMdd
        String clientID = "x000131xxxx";
        String ownerID = null;

        // missing ownerID
        assertFalse(OrderProcessor.checkAgeOlderThan14(clientID, ownerID));

        // client is not cardholder
        ownerID = "x110131xxxx";
        assertTrue(OrderProcessor.checkAgeOlderThan14(clientID, ownerID));

        // older than 14
        ownerID = clientID;
        assertTrue(OrderProcessor.checkAgeOlderThan14(clientID, ownerID));

        // younger than 14
        DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        Calendar birthDate = Calendar.getInstance();
        birthDate.add(Calendar.YEAR, -1);
        clientID = "x" + dateFormat.format(birthDate.getTime()) + "xxxx";
        ownerID = clientID;
        assertFalse(OrderProcessor.checkAgeOlderThan14(clientID, ownerID));

        // check that 2-digit year is interpreted as a past date, e.g. 39 > 1939
        clientID = "x390131xxxx";
        ownerID = clientID;
        assertTrue(OrderProcessor.checkAgeOlderThan14(clientID, ownerID));
    }

    @Test
    @SneakyThrows
    public void testRenewCard() {

        Order order = getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setBranchToDeliverAt("ANY");
        addDeliveryAddress(order);

        IzdCard card = setUpIzdCard(REPLACED_CARD_NUMBER);
        card.setStopCause("A");

        RowTypeRenewCard resultObject = new RowTypeRenewCard();
        resultObject.setNEWCARD(REPLACED_CARD_NUMBER);

        when(cmsSoapAPIWrapperBean.renewCard(any(RowTypeRenewCard.class))).thenReturn(resultObject);
        when(cardDAO.getObject(IzdCard.class, order.getCardNumber())).thenReturn(card);
        when(cardDAO.doWork(any(CMSCallAPIWrapper.UpdateDBWork.class))).thenReturn("success");

        getOrderValidator().validateOrder(order);
        getOrderProcessor().processOrder(order);

        verify(cmsSoapAPIWrapperBean, times(1)).renewCard(any(RowTypeRenewCard.class));
        verify(cardDAO, times(1)).doWork(any(CMSCallAPIWrapper.UpdateDBWork.class));

        assertEquals(REPLACED_CARD_NUMBER, order.getCardNumber());
    }

    @Test
    @SneakyThrows
    public void testReplaceCard() {

        Order order = getValidOrder(Constants.CARD_REPLACE_ACTION);
        order.setBranchToDeliverAt("ANY");
        addDeliveryAddress(order);

        IzdCard card = setUpIzdCard(REPLACED_CARD_NUMBER);
        card.setStopCause("A");

        RowTypeReplaceCard resultObject = new RowTypeReplaceCard();
        resultObject.setNEWCARD(REPLACED_CARD_NUMBER);

        when(cmsSoapAPIWrapperBean.replaceCard(any(RowTypeReplaceCard.class))).thenReturn(resultObject);
        when(cardDAO.getObject(IzdCard.class, order.getCardNumber())).thenReturn(card);
        when(cardDAO.doWork(any(CMSCallAPIWrapper.UpdateDBWork.class))).thenReturn("success");

        getOrderValidator().validateOrder(order);
        getOrderProcessor().processOrder(order);

        verify(cmsSoapAPIWrapperBean, times(1)).replaceCard(any(RowTypeReplaceCard.class));
        verify(cardDAO, times(1)).doWork(any(CMSCallAPIWrapper.UpdateDBWork.class));

        assertEquals(REPLACED_CARD_NUMBER, order.getCardNumber());
    }

    @Test
    @SneakyThrows
    public void testNewCardExistingClient() {

        final String client = "Client";
        final String cardType = "cardType";

        Order order = getValidOrder(Constants.CARD_CREATE_ACTION);
        order.setClientNumberInAbs("ClientNumberInAbs");
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_9999);
        order.setCardInsuranceFlag("1");
        addDeliveryAddress(order);

        IzdCard izdCard = getIzdCardWithClient(client, Constants.CLIENT_TYPE_PRIVATE);

        RowTypeCardInfoLogicalCard rowTypeCardInfoLogicalCard = new RowTypeCardInfoLogicalCard();
        rowTypeCardInfoLogicalCard.setCARD(DEFAULT_CARD_NUMBER);

        RowTypeCardInfo rowTypeCardInfo = new RowTypeCardInfo();
        rowTypeCardInfo.setLogicalCard(rowTypeCardInfoLogicalCard);

        List<RowTypeCardInfo> newCardResponse = new ArrayList<>();
        newCardResponse.add(rowTypeCardInfo);

        IzdCondCard izdCondCard = new IzdCondCard();
        izdCondCard.setCardType(cardType);

        when(clientDAO.findByCif(order.getClientNumberInAbs())).thenReturn(izdCard.getIzdAgreement().getIzdClient());
        when(cardDAO.getObject(IzdCard.class, order.getCardNumber())).thenReturn(izdCard);
        when(cardDAO.doWork(any(CMSCallAPIWrapper.UpdateDBWork.class))).thenReturn("success");
        when(cardDAO.getIzdCondCardByID(any(IzdCondCardPK.class))).thenReturn(izdCondCard);
        when(cmsSoapAPIWrapperBean.newCard(any(RowTypeAgreement.class), any(List.class), any(List.class))).thenReturn(newCardResponse);

        getOrderValidator().validateOrder(order);
        getOrderProcessor().processOrder(order);

        verify(cmsSoapAPIWrapperBean, times(1)).newCard(any(RowTypeAgreement.class), any(List.class), any(List.class));
        verify(cmsCallAPIWrapper, times(1)).switchInsurance(order.getCardNumber(), order.getBankc(), order.getGroupc(), "Y");
        verify(cardDAO, times(1)).getIzdCondCardByID(any(IzdCondCardPK.class));
        verify(cardDAO, times(1)).doWork(any(CMSCallAPIWrapper.UpdateDBWork.class));

        assertEquals(order.getClient(), client);
    }

    @Test
    @SneakyThrows
    public void testNewCardNewClient() {

        final String cardType = "cardType";
        final String client = "Client";

        Order order = getValidOrder(Constants.CARD_CREATE_ACTION);
        order.setClientNumberInAbs("ClientNumberInAbs");
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_9999);
        order.setCardInsuranceFlag("0");
        order.setBankClientSince("x110131xxxx");
        order.setBirthdayMask("DD.MM.YYYY");
        order.setOwnerBirthday("25.03.1977");
        addDeliveryAddress(order);

        IzdCard izdCard = getIzdCardWithClient(client, Constants.CLIENT_TYPE_PRIVATE);

        RowTypeCardInfoLogicalCard rowTypeCardInfoLogicalCard = new RowTypeCardInfoLogicalCard();
        rowTypeCardInfoLogicalCard.setCARD(DEFAULT_CARD_NUMBER);

        RowTypeCardInfo rowTypeCardInfo = new RowTypeCardInfo();
        rowTypeCardInfo.setLogicalCard(rowTypeCardInfoLogicalCard);

        List<RowTypeCardInfo> newCardResponse = new ArrayList<>();
        newCardResponse.add(rowTypeCardInfo);

        RowTypeCustomer newCustomer = new RowTypeCustomer();
        newCustomer.setCLIENT(client);

        IzdCondCard izdCondCard = new IzdCondCard();
        izdCondCard.setCardType(cardType);

        when(cardDAO.getObject(IzdCard.class, order.getCardNumber())).thenReturn(izdCard);
        when(cardDAO.doWork(any(CMSCallAPIWrapper.UpdateDBWork.class))).thenReturn("success");
        when(cardDAO.getIzdCondCardByID(any(IzdCondCardPK.class))).thenReturn(izdCondCard);
        when(cmsSoapAPIWrapperBean.newCustomer(any(RowTypeCustomer.class), any(ListTypeCustomerCustomInfo.class))).thenReturn(newCustomer);
        when(cmsSoapAPIWrapperBean.newCard(any(RowTypeAgreement.class), any(List.class), any(List.class))).thenReturn(newCardResponse);

        getOrderValidator().validateOrder(order);
        getOrderProcessor().processOrder(order);

        verify(cmsSoapAPIWrapperBean, times(1)).newCustomer(any(RowTypeCustomer.class), any(ListTypeCustomerCustomInfo.class));
        verify(cmsSoapAPIWrapperBean, times(1)).newCard(any(RowTypeAgreement.class), any(List.class), any(List.class));
        verify(cmsCallAPIWrapper, times(1)).switchInsurance(order.getCardNumber(), order.getBankc(), order.getGroupc(), "N");
        verify(cardDAO, times(1)).getIzdCondCardByID(any(IzdCondCardPK.class));
        verify(cardDAO, times(1)).doWork(any(CMSCallAPIWrapper.UpdateDBWork.class));

        assertEquals(order.getClient(), client);
    }

    @Test
    @SneakyThrows
    public void testInformationChangeWithRenewCard() {
        final String client = "Client";

        Order order = getValidOrder(Constants.INFORMATION_CHANGE_ACTION);
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_9999);
        addDeliveryAddress(order);
        order.setApplicationType(Constants.APPLICATION_TYPE_CARD_AUTO_RENEW);

        IzdCard izdCard = getIzdCardWithClient(client, Constants.CLIENT_TYPE_PRIVATE);
        PcdCard pcdCard = new PcdCard();

        PcdClient pcdClient = new PcdClient();

        RowTypeRenewCard resultObject = new RowTypeRenewCard();
        resultObject.setNEWCARD(REPLACED_CARD_NUMBER);

        when(cmsSoapAPIWrapperBean.renewCard(any(RowTypeRenewCard.class))).thenReturn(resultObject);
        when(cardDAO.getObject(IzdCard.class, order.getCardNumber())).thenReturn(izdCard);
        when(cardDAO.getObject(IzdCard.class, REPLACED_CARD_NUMBER)).thenReturn(izdCard);
        when(cardsDAO.findByCardNumber(REPLACED_CARD_NUMBER)).thenReturn(pcdCard);
        when(cardDAO.doWork(any(CMSCallAPIWrapper.UpdateDBWork.class))).thenReturn("success");
        when(clientsDAO.getClient(client)).thenReturn(pcdClient);

        getOrderValidator().validateOrder(order);
        getOrderProcessor().processOrder(order);

        assertEquals(order.getCardNumber(), REPLACED_CARD_NUMBER);

        verify(cmsSoapAPIWrapperBean, times(1)).renewCard(any(RowTypeRenewCard.class));
        verify(cmsSoapAPIWrapperBean, times(1)).editCustomer(any(RowTypeEditCustomerRequest.class));
        verify(cmsSoapAPIWrapperBean, times(1)).editCard(any(RowTypeEditCardRequest.class));
        verify(clientsDAO, times(1)).saveOrUpdate(pcdClient);
        verify(cardsDAO, times(1)).saveOrUpdate(pcdCard);
        verify(cardDAO, times(1)).doWork(any(CMSCallAPIWrapper.UpdateDBWork.class));
    }

    public OrderProcessor getOrderProcessor() {
        return (OrderProcessor) super.orderProcessor;
    }

    public OrderValidator getOrderValidator() {
        return (OrderValidator) super.orderValidator;
    }

    public Mapper getMapper() {
        return (Mapper) super.mapper;
    }

    @Override
    public String getCountry() {
        return "LT";
    }

}

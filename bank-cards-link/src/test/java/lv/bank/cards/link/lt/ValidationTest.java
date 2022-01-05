package lv.bank.cards.link.lt;

import lombok.SneakyThrows;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCardsAddFields;
import lv.bank.cards.link.Constants;
import lv.bank.cards.link.Order;
import lv.bank.cards.link.TestBase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class ValidationTest extends TestBase {

    @Before
    @SneakyThrows
    public void initCMSSoapAPIWrapper() {
        super.initCMSSoapAPIWrapper();
        when(commonDAO.getBranchIdByExternalId(eq(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_9999))).thenReturn(EXTERNAL_BRANCH);
        mapper = new Mapper(commonDAO, cardDAO, cardsDAO, clientDAO, clientsDAO, productDAO, accountsDAO);
        orderValidator = new OrderValidator(commonDAO, cardDAO, cardsDAO, clientDAO, getMapper());
    }

    @Test
    @SneakyThrows
    public void testCardReplaceToCardRenew() {
        Order order = getValidOrder(Constants.CARD_REPLACE_ACTION);
        order.setReplacementReason("5");
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_9999);
        addDeliveryAddress(order);

        IzdCard izdCard = new IzdCard();
        when(cardDAO.getObject(IzdCard.class, order.getCardNumber())).thenReturn(izdCard);

        orderValidator.validateOrder(order);

        assertEquals(order.getAction(), Constants.CARD_RENEW_ACTION);
    }

    @Test
    @SneakyThrows
    public void testOrderId() {
        Order order = getValidOrder(Constants.CARD_REPLACE_ACTION);
        addDeliveryAddress(order);

        IzdCard izdCard = new IzdCard();
        izdCard.setCard(DEFAULT_CARD_NUMBER);

        when(cardDAO.getCardByTrackingNo(order.getOrderId())).thenReturn(izdCard);
        when(cardDAO.getObject(IzdCard.class, order.getCardNumber())).thenReturn(izdCard);

        orderValidator.validateOrder(order);
    }

    @Test
    @SneakyThrows
    public void testOrderIdFail() {
        Order order = getValidOrder(Constants.CARD_REPLACE_ACTION);
        addDeliveryAddress(order);

        IzdCard izdCard = new IzdCard();
        izdCard.setCard(REPLACED_CARD_NUMBER);

        when(cardDAO.getCardByTrackingNo(order.getOrderId())).thenReturn(izdCard);
        when(cardDAO.getObject(IzdCard.class, order.getCardNumber())).thenReturn(izdCard);

        testValidationDataIntegrityException(order, "Order number is already used");
    }


    @Test
    @SneakyThrows
    public void testDeliveryBranch() {

        //TEST 1
        Order order = getValidOrder(Constants.CARD_REPLACE_ACTION);
        order.setBranchToDeliverAt(null);

        IzdCardsAddFields izdCardsAddFields = new IzdCardsAddFields();
        izdCardsAddFields.setUBField1(" LVRiga reg.                            Skansktes 12                            Riga     "
                        + "                                                                       LV-1048         DNB  "
                        + "                                             C123                    ");

        IzdCard izdCard = new IzdCard();
        izdCard.setIzdCardsAddFields(izdCardsAddFields);
        izdCard.setUCod10(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_080);
        when(cardDAO.getObject(IzdCard.class, order.getCardNumber())).thenReturn(izdCard);

        orderValidator.validateOrder(order);

        //TEST 2
        izdCardsAddFields.setUBField1(" LVRiga reg.                            Skansktes 12                            Riga     "
                        + "                                                                       LV-1048         DNB  "
                        + "                                                                     ");//C123 removed

        testValidationDataIntegrityException(order, "Missing value for delivery address");

        //TEST 3
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_9999);
        order.setDlvAddrCode("dlvAddrCode");

        orderValidator.validateOrder(order);

        //TEST 4
        order.setDlvAddrCode(null);

        testValidationDataIntegrityException(order, "Missing value for delivery address");

    }

    @Override
    public String getCountry() {
        return "LT";
    }

    public Mapper getMapper() {
        return (Mapper) super.mapper;
    }

}

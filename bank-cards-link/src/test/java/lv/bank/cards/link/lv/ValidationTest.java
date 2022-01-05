package lv.bank.cards.link.lv;

import lombok.SneakyThrows;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCountry;
import lv.bank.cards.link.Constants;
import lv.bank.cards.link.Order;
import lv.bank.cards.link.TestBase;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class ValidationTest extends TestBase {

    @Before
    @SneakyThrows
    public void initCMSSoapAPIWrapper() {
        super.initCMSSoapAPIWrapper();
        when(commonDAO.getIzdCountryByShortCountryCode(anyString())).thenReturn(new IzdCountry("LV", null, null, null));
        when(commonDAO.getBranchIdByExternalId(eq(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888))).thenReturn(EXTERNAL_BRANCH);
        mapper = new Mapper(commonDAO, cardDAO, cardsDAO, clientDAO, clientsDAO, productDAO, accountsDAO);
        orderValidator = new OrderValidator(commonDAO, cardDAO, cardsDAO, clientDAO, getMapper());
    }

    @Test
    @SneakyThrows
    public void checkOrderData() {
        Order order = getValidOrder(Constants.CARD_CREATE_ACTION);
        order.setClientType("2");

        order.setOwnerName("1234567890123456789012345");
        testValidationDataIntegrityException(order, "Own_name field (company name) too long. Should be 24 symbols or shorter.");

        order.setOwnerName("@"); // Not allowed character
        testValidationDataIntegrityException(order, "Company name contains characters that are not allowed: @");

        order.setOwnerName("a"); // Small letters not allowed
        testValidationDataIntegrityException(order, "Company name contains characters that are not allowed: a");

        order.setOwnerName("0123456789ABCDEFGHI");
        orderValidator.validateOrder(order);

        order.setIban("");
        testValidationDataIntegrityException(order, "Order is missing IBAN data");
    }

    @Test
    @SneakyThrows
    public void testMissingAddressValidation() {
        Order order = getValidOrder(Constants.CARD_REPLACE_ACTION);
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888);
        // Missing delivery address
        order.setDlvAddrCity(null);
        order.setDlvAddrCode(null);
        order.setDlvAddrCountry(null);
        order.setDlvAddrStreet1(null);
        order.setDlvAddrStreet2(null);
        order.setDlvAddrZip(null);
        order.setDlvCompany(null);
        testValidationDataIntegrityException(order, "Missing value for delivery address country, region, street address, zip code");
    }

    @Test
    @SneakyThrows
    public void testMissingDlvLanguage() {
        Order order = getValidOrder(Constants.CARD_REPLACE_ACTION);
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888);
        order.setDlvLanguage(null);
        testValidationDataIntegrityException(order, "Missing value for delivery address language");
    }

    @Test
    @SneakyThrows
    public void testMissingDeliveryBranch() {
        Order order = getValidOrder(Constants.CARD_REPLACE_ACTION);
        order.setBranchToDeliverAt("not");

        IzdCard card = setUpIzdCard(DEFAULT_CARD_NUMBER);
        card.setStatus1("0");

        when(commonDAO.getBranchIdByExternalId(eq("not"))).thenReturn(null);

        // Missing mandatory field, delivery branch
        testValidationDataIntegrityException(order, "There is no branch in NORDLB_BRANCHES for not");
    }

    @Test
    @SneakyThrows
    public void testInvalidCardValidityPeriod() {
        Order order = getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setCardValidityPeriod("2");

        getIzdCondCardMock(DEFAULT_CARD_NUMBER, 36);
        IzdCard card = setUpIzdCard(DEFAULT_CARD_NUMBER);
        card.setStatus1("0");

        testValidationDataIntegrityException(order, "Can't renew this card, as card ordered for period 24, but in card condition specified period is 36");
    }

    @Test
    @SneakyThrows
    public void testDefaultCountry() {
        Order order = getValidOrder(Constants.CARD_CREATE_ACTION);
        order.setCountry(null);
        order.setOwnerName("ANY OWNER NAME");

        orderValidator.validateOrder(order);

        assertEquals(order.getCountry(), lv.bank.cards.core.utils.Constants.DEFAULT_COUNTRY_LV);
    }

    @Test
    @SneakyThrows
    public void cardRenew_toCardReplace() {

        Order order = getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888);
        order.setAutoBlockCard("");
        order.setReplaceName("repN");
        order.setCardName("cardN");
        order.setClientFirstname("clientF");
        order.setClientLastname("clientL");
        order.setClientNumberInAbs("cl123");
        order.setReportLanguage("2");
        order.setCardValidityPeriod("3");

        addDeliveryAddress(order);
        order.setDlvLanguage(null); // If no delivery language then use report language

        IzdCard card = setUpIzdCard(DEFAULT_CARD_NUMBER);
        card.setStatus1("2"); // Card is blocked, must create new
        card.setStopCause("A");

        getIzdCondCardMock(DEFAULT_CARD_NUMBER, 36);

        orderValidator.validateOrder(order);

        assertEquals(order.getAction(), Constants.CARD_REPLACE_ACTION);
    }

    @Test
    @SneakyThrows
    public void cardRenew_toCardReplace_renewN() {
        Order order = getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888);
        order.setAutoBlockCard("");
        order.setReplaceName("repN");
        order.setCardName("cardN");
        order.setClientFirstname("clientF");
        order.setClientLastname("clientL");
        order.setClientNumberInAbs("cl123");
        order.setReportLanguage("2");
        order.setCardValidityPeriod("3");
        addDeliveryAddress(order);
        order.setDlvLanguage(null); // If no delivery language then use report language

        IzdCard card = setUpIzdCard(DEFAULT_CARD_NUMBER);
        card.setRenew("N"); // Card is blocked, must create new
        card.setExpiry1(new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000)));
        card.setStopCause("A");

        getIzdCondCardMock(DEFAULT_CARD_NUMBER, 36);

        orderValidator.validateOrder(order);

        assertEquals(order.getAutoBlockCard(), "1");
    }

    @Test
    @SneakyThrows
    public void testMigratedCardFieldValidation() {
        Order order = getValidOrder(Constants.CARD_CREATE_ACTION);
        // Missing PIN retaining fields
        order.setMigratedCardNumber("5123123412341234");
        order.setMigratedCardPinBlock(null);
        order.setMigratedCardPinKeyId(null);
        testValidationDataIntegrityException(order,"Missing value for PIN retaining of migrated card: PIN block, PIN Key ID");
        order.setMigratedCardNumber("5123123412341234");
        order.setMigratedCardPinBlock("cardPinBlock1234");
        order.setMigratedCardPinKeyId(null);
        testValidationDataIntegrityException(order,"Missing value for PIN retaining of migrated card: PIN Key ID");
        order.setMigratedCardNumber("5123123412341234");
        order.setMigratedCardPinBlock(null);
        order.setMigratedCardPinKeyId("06");
        testValidationDataIntegrityException(order,"Missing value for PIN retaining of migrated card: PIN block");
        // Incorrect PIN retaining fields
        order.setMigratedCardNumber("WRONG");
        order.setMigratedCardPinBlock("cardPinBlock1234");
        order.setMigratedCardPinKeyId("06");
        testValidationDataIntegrityException(order,"Incorrect migrated card number: WRONG");
        order.setMigratedCardNumber("5123123412341234");
        order.setMigratedCardPinBlock("WRONG");
        order.setMigratedCardPinKeyId("06");
        testValidationDataIntegrityException(order,"Incorrect size of migrated card PIN block: WRONG");
        order.setMigratedCardNumber("5123123412341234");
        order.setMigratedCardPinBlock("cardPinBlock1234");
        order.setMigratedCardPinKeyId("WRONG");
        testValidationDataIntegrityException(order,"Incorrect migrated card PIN Key ID: WRONG");
    }

    @Override
    public String getCountry() {
        return "LV";
    }

    public Mapper getMapper() {
        return (Mapper) super.mapper;
    }

}

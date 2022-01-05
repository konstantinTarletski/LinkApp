package lv.bank.rest;

import lv.bank.cards.core.entity.linkApp.PcdAccParam;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdAccumulator;
import lv.bank.cards.core.entity.linkApp.PcdBranch;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.entity.linkApp.PcdCondAccntPK;
import lv.bank.cards.core.entity.linkApp.PcdCurrency;
import lv.bank.cards.core.entity.rtps.AnswerCode;
import lv.bank.cards.core.entity.rtps.CurrencyCode;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.entity.rtps.StipRmsStoplist;
import lv.bank.cards.core.linkApp.PcdabaNGConstants;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIWrapper;
import lv.bank.cards.soap.service.CardService;
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
import lv.bank.rest.dto.ContactlessStatusDO;
import lv.bank.rest.dto.ECardStatus;
import lv.bank.rest.dto.ECardType;
import lv.bank.rest.dto.ECommerceStatus;
import lv.bank.rest.dto.EContactlessStatus;
import lv.bank.rest.dto.EProduct;
import lv.bank.rest.dto.ESpecialClientCategory;
import lv.bank.rest.dto.EcommStatusDO;
import lv.bank.rest.exception.BusinessException;
import lv.bank.rest.exception.JsonErrorCode;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.nordlb.cards.transmaster.fo.interfaces.StipCardManager;
import lv.nordlb.cards.transmaster.fo.interfaces.TMFManager;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.NamingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardsServiceTest {

    protected CardsService cardsService;

    @Mock
    protected PcdabaNGManager pcdabaNGManager;

    @Mock
    protected CMSSoapAPIWrapper cmsSoapWrapper;

    @Mock
    protected TMFManager tmfManager;

    @Mock
    protected StipCardManager stipCardManager;

    @Mock
    protected CardService cardService;

    protected final Context context = mock(Context.class);
    protected final SessionFactory sessionFactory = mock(SessionFactory.class);

    private static final List<String> RENEW_DISALLOWED_STATUSES = Arrays.asList("0", "1", "A", "P", "2", "3");

    @Rule
    public MockInitialContextRule mockInitialContextRule = new MockInitialContextRule(context);

    @Before
    public void setUpTest() throws NamingException {
        when(context.lookup(PcdabaNGConstants.HibernateSessionFactory)).thenReturn(sessionFactory);

        cardsService = new CardsService();
        cardsService.cmsWrapper = mock(CMSCallAPIWrapper.class);
        cardsService.rtpsWrapper = mock(RTPSCallAPIWrapper.class);
        cardsService.stipCardManager = stipCardManager;
        cardsService.tmfManager = tmfManager;
        cardsService.cmsSoapWrapper = cmsSoapWrapper;
        cardsService.pcdabaNGManager = pcdabaNGManager;
        cardsService.cardService = cardService;
    }

    @Test
    public void readCustomerCards_Positive() {

        addLinkAppProperty("credit_card_bins", "49217600");
        addLinkAppProperty("business_card_bins", "49217600");

        String client = "M131234";
        String country = "LV";

        List<PcdCard> cards = new ArrayList<>();
        PcdCard card = getCard(1);
        card.setDesign("123");
        card.setUCod10("test123");
        card.setContactless(EContactlessStatus.ENABLED.getIndex());
        card.setRenew("N");

        StipRmsStoplist stopDelivery = new StipRmsStoplist();
        stopDelivery.setAnswerCode(new AnswerCode());
        stopDelivery.getAnswerCode().setActionCode("100");
        stopDelivery.setDescription("Card blocked for delivery by LinkApp");
        StipRmsStoplist stopEcomm = new StipRmsStoplist();
        stopEcomm.setAnswerCode(new AnswerCode());
        stopEcomm.getAnswerCode().setActionCode("108");
        stopEcomm.setDescription("E-commerce blocked by LinkApp");
        when(stipCardManager.getStipRmsStoplist("4921760000000001", "42800202350", null)).thenReturn(Arrays.asList(stopDelivery, stopEcomm));

        cards.add(card);
        cards.add(getCard(2));
        card = getCard(3);
        card.setStatus1("1");
        cards.add(card);

        PcdBranch branch = new PcdBranch();
        branch.setExternalId("0112233");
        when(pcdabaNGManager.getBranch("1122")).thenReturn(branch);

        StipRmsStoplist stop = new StipRmsStoplist();
        stop.setAnswerCode(new AnswerCode());
        stop.getAnswerCode().setActionCode("100");
        stop.setDescription("Owner block from LinkApp");

        PcdAccount account = new PcdAccount();
        PcdAccParam accParam = new PcdAccParam();
        accParam.setUfield5("0123456789");
        account.setPcdAccParam(accParam);


        when(pcdabaNGManager.getClientsCardsByCifOrPersonCode(client, country)).thenReturn(cards);
        when(stipCardManager.getStipRmsStoplist("4921760000000002", "42800202350", null)).thenReturn(Arrays.asList(stop));
        when(pcdabaNGManager.getAccountByCardNumber(anyString())).thenReturn(account);


        CardListDO listDO = cardsService.readCustomerCards(client, country);

        assertEquals(3, listDO.getCards().size());
        CardInfoDO info = listDO.getCards().get(0);
        assertEquals("4921760000000001", info.getId());
        assertEquals("492176******0001", info.getPan());
        assertEquals("Some name1", info.getHolderName());
        assertEquals("10/21", info.getExpirationDate());
        assertEquals(EProduct.VISA, info.getProduct());
        assertEquals("123", info.getDesignId());
        assertEquals(ECardStatus.ACTIVE, info.getCardStatus());
        assertEquals(EContactlessStatus.ENABLED, info.getContactless());
        assertEquals(ECommerceStatus.NOT_ALLOWED, info.getEcom());
        assertEquals("Stark Industries", info.getCompanyName());
        assertEquals("0123456789", info.getCoreAccountNo());
        assertEquals(ECardType.BUSINESS_CREDIT, info.getType());
        assertEquals(ECardStatus.ACTIVE, info.getCardStatus());
        assertEquals(false, info.isAutoRenewal());

        info = listDO.getCards().get(1);
        assertEquals("4921760000000002", info.getId());
        assertEquals("492176******0002", info.getPan());
        assertEquals("Some name2", info.getHolderName());
        assertEquals("10/22", info.getExpirationDate());
        assertEquals("112233", info.getBranch());
        assertEquals(EProduct.VISA, info.getProduct());
        assertEquals(ECardType.BUSINESS_CREDIT, info.getType());
        assertEquals(ECardStatus.FROZEN, info.getCardStatus());
        assertEquals(true, info.isAutoRenewal());

        info = listDO.getCards().get(2);
        assertEquals("4921760000000003", info.getId());
        assertEquals("492176******0003", info.getPan());
        assertEquals("Some name3", info.getHolderName());
        assertEquals("10/23", info.getExpirationDate());
        assertEquals("112233", info.getBranch());
        assertEquals(EProduct.VISA, info.getProduct());
        assertEquals(ECardType.BUSINESS_CREDIT, info.getType());
        assertEquals(ECardStatus.BLOCKED, info.getCardStatus());

        addLinkAppProperty("credit_card_bins", "");
        addLinkAppProperty("business_card_bins", "");

    }

    @Test
    public void getCardDetails_Negative() {
        // Empty card number
        CardDetailsDO detailsDO = null;
        try {
            detailsDO = cardsService.getCardDetails(null, " ", null);
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.INVALID_CARD_NUMBER, e.getErrorCode());
        }
        assertNull(detailsDO);

        // Cannot find card
        try {
            detailsDO = cardsService.getCardDetails(null, "4921760000000003", null);
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.CARD_NOT_FOUND, e.getErrorCode());
        }
        assertNull(detailsDO);
    }

    @Test
    public void getCardDetails_Positive() throws BusinessException {
        PcdCard card = getCard(1);
        card.setContactless(1);
        card.setDesign("1231");
        card.setStatus1("1");
        card.setUAField2("AVAILABLE");
        card.setIdCard("person");
        card.setUCod10("br1");
        card.setCmpgName("Stark Industries");
        card.setRenew("E");

        PcdBranch branch = new PcdBranch();
        branch.setExternalId("012");

        StipRmsStoplist stopOwner = new StipRmsStoplist();
        stopOwner.setAnswerCode(new AnswerCode());
        stopOwner.getAnswerCode().setActionCode("100");
        stopOwner.setDescription("Owner block from LinkApp");
        StipRmsStoplist stopDelivery = new StipRmsStoplist();
        stopDelivery.setAnswerCode(new AnswerCode());
        stopDelivery.getAnswerCode().setActionCode("100");
        stopDelivery.setDescription("Card blocked for delivery by LinkApp");
        StipRmsStoplist stopEcomm = new StipRmsStoplist();
        stopEcomm.setAnswerCode(new AnswerCode());
        stopEcomm.getAnswerCode().setActionCode("108");
        stopEcomm.setDescription("E-commerce blocked by LinkApp");

        getAccount();

        when(stipCardManager.getStipRmsStoplist(card.getCard(), "42800202350", null)).thenReturn(Arrays.asList(stopOwner, stopDelivery, stopEcomm));
        when(pcdabaNGManager.getBranch("br1")).thenReturn(branch);

        CardDetailsDO details = cardsService.getCardDetails(null, card.getCard(), null);

        assertNotNull(details);
        assertEquals("4921760000000001", details.getId());
        assertEquals("492176******0001", details.getPan());
        assertEquals("Some name1", details.getHolderName());
        assertEquals("10/21", details.getExpirationDate());
        assertEquals(EProduct.VISA, details.getProduct());
        assertEquals(ECardType.DEBIT, details.getType());
        assertEquals(ECardStatus.BLOCKED, details.getCardStatus());
        assertEquals(EContactlessStatus.ENABLED, details.getContactless());
        assertEquals(ECommerceStatus.NOT_ALLOWED, details.getEcom());
        assertEquals("1231", details.getDesignId());
        assertEquals("AVAILABLE", details.getPinStatus());
        assertEquals("person", details.getPersonalCode());
        assertEquals("12", details.getBranch());
        assertEquals("Stark Industries", details.getCompanyName());

    }

    @Test
    public void getCardBalances_Positive() throws BusinessException, DataIntegrityException {
        PcdCard card = getCard(1);

        StipAccount accountEUR = new StipAccount();
        accountEUR.setCurrencyCode(new CurrencyCode());
        accountEUR.getCurrencyCode().setCcyAlpha("EUR");
        accountEUR.getCurrencyCode().setExpDot(2);
        accountEUR.setInitialAmount(436742L);
        accountEUR.setBonusAmount(10000L);

        when(tmfManager.findStipAccountsByCardNumberAndCentreId(card.getCard(), "42800202350")).thenReturn(Arrays.asList(accountEUR));
        when(tmfManager.getLockedAmountByStipAccount(accountEUR)).thenReturn(3531L);

        CardBalanceListDO balances = cardsService.getCardBalances(card.getCard());

        assertEquals(1, balances.getCardBalances().size());
        CardBalanceDO balance = balances.getCardBalances().get(0);
        assertEquals("EUR", balance.getCurrency());
        assertEquals(new BigDecimal("35.31"), balance.getReservedAmount());
        assertEquals(new BigDecimal("4432.11"), balance.getAvailableBalance());
    }


    @Test
    public void getCardStatusDO_Positive() throws BusinessException {
        PcdCard card = getCard(1);

        // Active card
        CardStatusDO status = cardsService.getCardStatusDO(card.getCard());
        assertEquals(ECardStatus.ACTIVE, status.getCardStatus());

        // Owner block
        StipRmsStoplist stopOwner = new StipRmsStoplist();
        stopOwner.setAnswerCode(new AnswerCode());
        stopOwner.getAnswerCode().setActionCode("100");
        stopOwner.setDescription("Owner block from LinkApp");
        when(stipCardManager.getStipRmsStoplist(card.getCard(), "42800202350", null)).thenReturn(Arrays.asList(stopOwner));

        status = cardsService.getCardStatusDO(card.getCard());
        assertEquals(ECardStatus.FROZEN, status.getCardStatus());

        // CMS block
        card.setStatus1("1");
        status = cardsService.getCardStatusDO(card.getCard());
        assertEquals(ECardStatus.BLOCKED, status.getCardStatus());

        // CMS closed card
        card.setStatus1("2");
        status = cardsService.getCardStatusDO(card.getCard());
        assertEquals(ECardStatus.CLOSED, status.getCardStatus());

        // RMS pending closure block
        card.setStatus1("0");
        StipRmsStoplist stopPendingClosure = new StipRmsStoplist();
        stopPendingClosure.setAnswerCode(new AnswerCode());
        stopPendingClosure.getAnswerCode().setActionCode("207");
        stopPendingClosure.setDescription("Card blocked till closure by PLATON_USER");
        when(stipCardManager.getStipRmsStoplist(card.getCard(), "42800202350", null)).thenReturn(
                Arrays.asList(stopPendingClosure));

        status = cardsService.getCardStatusDO(card.getCard());
        assertEquals(ECardStatus.CLOSED, status.getCardStatus());

        // multiple RMS blocks
        StipRmsStoplist stopDLQ = new StipRmsStoplist();
        stopDLQ.setAnswerCode(new AnswerCode());
        stopDLQ.getAnswerCode().setActionCode("105");
        stopDLQ.setDescription("Deliquency block. DLQ123456");

        StipRmsStoplist stopEcomm = new StipRmsStoplist();
        stopEcomm.setAnswerCode(new AnswerCode());
        stopEcomm.getAnswerCode().setActionCode("108");
        stopEcomm.setDescription("E-commerce blocked by LinkApp");

        StipRmsStoplist stopDelivery = new StipRmsStoplist();
        stopDelivery.setAnswerCode(new AnswerCode());
        stopDelivery.getAnswerCode().setActionCode("100");
        stopDelivery.setDescription("Card blocked for delivery by LinkApp");

        when(stipCardManager.getStipRmsStoplist(card.getCard(), "42800202350", null)).thenReturn(
                Arrays.asList(stopOwner, stopDLQ, stopPendingClosure, stopDelivery));
        assertEquals(ECardStatus.CLOSED, cardsService.getCardStatusDO(card.getCard()).getCardStatus());

        when(stipCardManager.getStipRmsStoplist(card.getCard(), "42800202350", null)).thenReturn(
                Arrays.asList(stopDLQ, stopOwner));
        assertEquals(ECardStatus.BLOCKED, cardsService.getCardStatusDO(card.getCard()).getCardStatus());

        when(stipCardManager.getStipRmsStoplist(card.getCard(), "42800202350", null)).thenReturn(
                Arrays.asList(stopDelivery, stopEcomm));
        assertEquals(ECardStatus.ACTIVE, cardsService.getCardStatusDO(card.getCard()).getCardStatus());
    }


    @Test
    public void changeCardStatus_Negative() {
        PcdCard card = getCard(1);

        // Not supported status
        CardStatusDO status = null;
        try {
            status = cardsService.changeCardStatus(card.getCard(), CardStatusDO.builder().cardStatus(ECardStatus.CLOSED).build());
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.BAD_REQUEST, e.getErrorCode());
        }
        assertNull(status);

        // Activate already active card
        status = null;
        try {
            status = cardsService.changeCardStatus(card.getCard(), CardStatusDO.builder().cardStatus(ECardStatus.ACTIVE).build());
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.DATA_CONFLICT, e.getErrorCode());
        }
        assertNull(status);

        // Block already blocked card
        status = null;
        card.setStatus1("1");
        try {
            status = cardsService.changeCardStatus(card.getCard(), CardStatusDO.builder().cardStatus(ECardStatus.FROZEN).build());
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.DATA_CONFLICT, e.getErrorCode());
        }
        assertNull(status);
    }

    @Test
    public void changeCardStatus_PositiveActivate() throws BusinessException, RTPSCallAPIException {
        PcdCard card = getCard(1);

        // Owner block
        StipRmsStoplist stop = new StipRmsStoplist();
        stop.setAnswerCode(new AnswerCode());
        stop.getAnswerCode().setActionCode("100");
        stop.setDescription("Owner block from LinkApp");
        when(stipCardManager.getStipRmsStoplist(card.getCard(), "42800202350", null)).thenReturn(Arrays.asList(stop));

        cardsService.changeCardStatus(card.getCard(), CardStatusDO.builder().cardStatus(ECardStatus.ACTIVE).build());

        verify(cardsService.rtpsWrapper).RemoveCardFromRMSStop("42800202350", card.getCard(), "FLD_041!%'PBANK.*'||!FLD_041", null, "100", null);
    }

    @Test
    public void changeCardStatus_PositiveBlock() throws BusinessException, RTPSCallAPIException {
        PcdCard card = getCard(1);

        cardsService.changeCardStatus(card.getCard(), CardStatusDO.builder().cardStatus(ECardStatus.FROZEN).build());

        verify(cardsService.rtpsWrapper).AddCardToRMSStop("42800202350", card.getCard(), "FLD_041!%'PBANK.*'||!FLD_041", null, "100", "Owner block from LinkApp");
    }

    @Test
    public void testCardAutoRenewal_Positive() throws BusinessException {
        PcdCard card = getCard(1);

        for (String renew : CardsService.RENEW_ALLOWED_STATUSES) {
            card.setRenew(renew);
            card.setRenewOld(null);
            CardAutoRenewalDO newStatus = new CardAutoRenewalDO();
            newStatus.setAutoRenewal(false);
            CardAutoRenewalDO status = cardsService.blockOrUnblockCardAutoRenewal(card.getCard(), newStatus);
            assertEquals("N", card.getRenew());
            assertEquals(false, status.isAutoRenewal());
            assertNotNull(card.getRenewOld());
            newStatus.setAutoRenewal(true);
            status = cardsService.blockOrUnblockCardAutoRenewal(card.getCard(), newStatus);
            assertEquals(renew, card.getRenew());
            assertEquals(true, status.isAutoRenewal());
        }
    }

    @Test
    public void testCardAutoRenewal_Negative() {
        PcdCard card = getCard(1);

        for (String renew : RENEW_DISALLOWED_STATUSES) {
            card.setRenew(renew);
            card.setRenewOld(null);
            CardAutoRenewalDO status = null;
            try {
                CardAutoRenewalDO newStatus = new CardAutoRenewalDO();
                status = cardsService.blockOrUnblockCardAutoRenewal(card.getCard(), newStatus);
            } catch (BusinessException e) {
                assertEquals(JsonErrorCode.DATA_CONFLICT, e.getErrorCode());
                assertEquals("Change of status is prohibited during card embossing / PIN reminder processing", e.getMessage());
            }
            assertNull(status);
            assertEquals(renew, card.getRenew());
            assertNull(card.getRenewOld());
        }
    }

    @Test
    public void getChildTeen_Positive() throws BusinessException {
        getAccount();
        PcdCard card = getCard(1);
        card.setRenew("E");

        card.setUCod9("006");
        CardDetailsDO details = cardsService.getCardDetails(null, card.getCard(), null);
        assertEquals(ESpecialClientCategory.CHILD, details.getSpecialClientCategory());

        card.setUCod9("007");
        details = cardsService.getCardDetails(null, card.getCard(), null);
        assertEquals(ESpecialClientCategory.TEENAGER, details.getSpecialClientCategory());
    }

    @Test
    public void getChildTeen_Negative() throws BusinessException {
        getAccount();
        PcdCard card = getCard(1);
        card.setRenew("E");

        card.setUCod9(null);
        CardDetailsDO details = cardsService.getCardDetails(null, card.getCard(), null);
        assertNull(details.getSpecialClientCategory());

        card.setUCod9("XXX");
        details = cardsService.getCardDetails(null, card.getCard(), null);
        assertNull(details.getSpecialClientCategory());
    }

    @Test
    public void getCardNumberDetailsDO_Positive() throws BusinessException {
        PcdCard card = getCard(1);

        CardNumberDetailsDO details = cardsService.getCardNumberDetails(card.getCard());
        assertEquals("4921760000000001", details.getCardNumber());
        assertEquals("Some name1", details.getHolderName());
        assertEquals("10/21", details.getExpiry());
        assertNull(details.getCvc());
    }

    @Test
    public void getCardNumberDetailsDO_Negative() {
        try {
            cardsService.getCardNumberDetails("1111111111111111");
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.CARD_NOT_FOUND, e.getErrorCode());
            assertEquals("Card not found", e.getMessage());
        }
    }

    @Test
    public void getCardCreditDetailsDO_Positive() throws BusinessException {
        PcdCard card = getCard(1);
        getAccount();
        getCondAccnt(1, BigDecimal.valueOf(18.00), BigDecimal.valueOf(0.00));
        CardCreditDetailsDO details = cardsService.getCardCreditDetails(card.getCard());
        assertEquals("0123456789", details.getCoreAccountNo());
        assertEquals("EUR", details.getCurrency());
        assertEquals("31", details.getGracePeriod());
        assertEquals(new BigDecimal("712207"), details.getAccountNo());
        assertEquals(new BigDecimal("0"), details.getAvailableBalance());
        assertEquals(new BigDecimal("150.0"), details.getCreditLimit());
        assertEquals(new BigDecimal("20.0"), details.getOpeningBalance());
        assertEquals(new BigDecimal("150.0"), details.getUsedCredit());
        assertEquals(new BigDecimal("18.0"), details.getInterestRate());
        getCondAccnt(0, BigDecimal.valueOf(18.00), BigDecimal.valueOf(0.00));
        CardCreditDetailsDO detailsForCondSetWithZeroInGracePeriod = cardsService.getCardCreditDetails(card.getCard());
        assertEquals("0", detailsForCondSetWithZeroInGracePeriod.getGracePeriod());
        getCondAccnt(0, BigDecimal.valueOf(0.00), BigDecimal.valueOf(12.00));
        CardCreditDetailsDO detailsForCondSetWithInterestFromOverdueField = cardsService.getCardCreditDetails(card.getCard());
        assertEquals(new BigDecimal("12.0"), detailsForCondSetWithInterestFromOverdueField.getInterestRate());
    }

    @Test
    public void getCardCreditDetailsDO_Negative() {
        try {
            cardsService.getCardCreditDetails("1111111111111111");
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.CARD_NOT_FOUND, e.getErrorCode());
            assertEquals("Card not found", e.getMessage());
        }
    }

    @Test
    public void getCardDeliveryDetailsDO_Positive() throws BusinessException {
        PcdCard card = getCard(1);
        card.setUBField1("2LVSalaspils novads                     Salaspils                               Skolas -12                                        LV-2121                                                           ");

        CardDeliveryDetailsWrapperDO details = cardsService.getCardDeliveryDetails(card.getCard());
        assertEquals("LV", details.getDeliveryDetails().getCountry());
        assertEquals("Salaspils novads", details.getDeliveryDetails().getCityRegion());
        assertEquals("Salaspils", details.getDeliveryDetails().getVillageDistrict());
        assertEquals("Skolas -12", details.getDeliveryDetails().getStreet());
        assertEquals("LV-2121", details.getDeliveryDetails().getZip());
        assertEquals("LV", details.getDeliveryDetails().getLanguage());
    }

    @Test
    public void getCardDeliveryDetailsDO_Negative() {
        try {
            cardsService.getCardDeliveryDetails("1111111111111111");
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.CARD_NOT_FOUND, e.getErrorCode());
            assertEquals("Card not found", e.getMessage());
        }
    }

    @Test
    public void updateCardDeliveryDetailsDO_Positive() throws BusinessException {
        PcdCard card = getCard(1);
        card.setUBField1("2LVSalaspils novads                     Salaspils                               Skolas -12                                        LV-2121                                                           ");

        CardDeliveryDetailsWrapperDO details = cardsService.getCardDeliveryDetails(card.getCard());
        details.getDeliveryDetails().setCountry("LV");
        details.getDeliveryDetails().setCityRegion("Kocēnu novads");
        details.getDeliveryDetails().setVillageDistrict("Bērzaines pagasts");
        details.getDeliveryDetails().setStreet("'KUČIERI'");
        details.getDeliveryDetails().setZip("LV4208");
        details.getDeliveryDetails().setLanguage("RU");
        details.getDeliveryDetails().setBranch("888");
        CardDeliveryDetailsWrapperDO updates = cardsService.updateCardDeliveryDetails(card.getCard(), details);
        assertEquals("LV", updates.getDeliveryDetails().getCountry());
        assertEquals("Kocēnu novads", updates.getDeliveryDetails().getCityRegion());
        assertEquals("Bērzaines pagasts", updates.getDeliveryDetails().getVillageDistrict());
        assertEquals("'KUČIERI'", updates.getDeliveryDetails().getStreet());
        assertEquals("LV4208", updates.getDeliveryDetails().getZip());
        assertEquals("RU", updates.getDeliveryDetails().getLanguage());

        // Unsupported language code in database, default to EN
        card.setUBField1("9LVSalaspils novads                     Salaspils                               Skolas -12                                        LV-2121                                                           ");
        CardDeliveryDetailsWrapperDO details2 = cardsService.getCardDeliveryDetails(card.getCard());
        assertEquals("EN", details2.getDeliveryDetails().getLanguage());
    }

    @Test
    public void updateCardDeliveryDetailsDO_Negative() throws BusinessException {
        PcdCard card = getCard(1);
        card.setUBField1("2LVSalaspils novads                     Salaspils                               Skolas -12                                        LV-2121                                                           ");
        CardDeliveryDetailsWrapperDO details = cardsService.getCardDeliveryDetails(card.getCard());
        details.getDeliveryDetails().setBranch("888");

        // Country value too long
        details.getDeliveryDetails().setCountry("EST");
        try {
            cardsService.updateCardDeliveryDetails(card.getCard(), details);
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.BAD_REQUEST, e.getErrorCode());
            assertEquals("Country value [EST] exceeds max length (2)", e.getMessage());
        }
        details.getDeliveryDetails().setCountry("LV");

        // Region value too long
        details.getDeliveryDetails().setCityRegion("Pfaffenschlag bei Waidhofen an der Thaya");
        try {
            cardsService.updateCardDeliveryDetails(card.getCard(), details);
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.BAD_REQUEST, e.getErrorCode());
            assertEquals("Region value [Pfaffenschlag bei Waidhofen an der Thaya] exceeds max length (37)", e.getMessage());
        }
        details.getDeliveryDetails().setCityRegion("Salaspils novads");

        // City value too long
        details.getDeliveryDetails().setVillageDistrict("Saint-Germain-de-Tallevende-la-Lande-Vaumont");
        try {
            cardsService.updateCardDeliveryDetails(card.getCard(), details);
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.BAD_REQUEST, e.getErrorCode());
            assertEquals("City value [Saint-Germain-de-Tallevende-la-Lande-Vaumont] exceeds max length (40)", e.getMessage());
        }
        details.getDeliveryDetails().setVillageDistrict("Salaspils");

        // Address value too long
        details.getDeliveryDetails().setStreet("Bischöflich-Geistlicher-Rat-Josef-Zinnbauer-Straße 2");
        try {
            cardsService.updateCardDeliveryDetails(card.getCard(), details);
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.BAD_REQUEST, e.getErrorCode());
            assertEquals("Address value [Bischöflich-Geistlicher-Rat-Josef-Zinnbauer-Straße 2] exceeds max length (50)", e.getMessage());
        }
        details.getDeliveryDetails().setStreet("Skolas -12");

        // ZipCode value too long
        details.getDeliveryDetails().setZip("4188979265 Tehran");
        try {
            cardsService.updateCardDeliveryDetails(card.getCard(), details);
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.BAD_REQUEST, e.getErrorCode());
            assertEquals("ZipCode value [4188979265 Tehran] exceeds max length (16)", e.getMessage());
        }
        details.getDeliveryDetails().setZip("LV-2121");

        // Unsupported language code in request
        details.getDeliveryDetails().setLanguage("EST");
        try {
            cardsService.updateCardDeliveryDetails(card.getCard(), details);
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.BAD_REQUEST, e.getErrorCode());
            assertEquals("Unsupported lang code [EST]", e.getMessage());
        }
        details.getDeliveryDetails().setLanguage("LV");
    }

    @Test
    public void getCardCVC_Positive() throws BusinessException {
        PcdCard card = getCard(1);
        card.setCvc21("332");

        CardCvcDO cvc = cardsService.getCardCVC(card.getCard());
        assertEquals("332", cvc.getCvc());
    }

    @Test
    public void activateCard_Positive() throws BusinessException, RTPSCallAPIException {
        PcdCard card = getCard(1);

        cardsService.activateCard(card.getCard());
        verify(cardsService.rtpsWrapper).RemoveCardFromRMSStop("42800202350", card.getCard(), "(FLD_041!%'PBANK.*'||!FLD_041)&&(FLD_014=='2110')", null, "100", null);
    }

    @Test
    public void changeEcomm_Negative() {
        PcdCard card = getCard(1);

        EcommStatusDO status = null;
        try {
            status = cardsService.changeEcomm(card.getCard(), EcommStatusDO.builder().status(ECommerceStatus.ALLOWED).build());
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.DATA_CONFLICT, e.getErrorCode());
        }
        assertNull(status);

        try {
            status = cardsService.changeEcomm(card.getCard(), new EcommStatusDO());
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.BAD_REQUEST, e.getErrorCode());
        }
        assertNull(status);
    }

    @Test
    public void changeEcomm_PositiveAllow() throws BusinessException, RTPSCallAPIException {
        PcdCard card = getCard(1);
        StipRmsStoplist stopEcomm = new StipRmsStoplist();
        stopEcomm.setAnswerCode(new AnswerCode());
        stopEcomm.getAnswerCode().setActionCode("108");
        stopEcomm.setDescription("E-commerce blocked by LinkApp");

        when(stipCardManager.getStipRmsStoplist(card.getCard(), "42800202350", null)).thenReturn(Arrays.asList(stopEcomm));

        cardsService.changeEcomm(card.getCard(), EcommStatusDO.builder().status(ECommerceStatus.ALLOWED).build());

        verify(cardsService.rtpsWrapper).RemoveCardFromRMSStop("42800202350", card.getCard(), "FLD_022 %% '......[JSTUVW].*'&&(FLD_041!%'PBANK.*'||!FLD_041)&&(2==2)", null, "108", null);
    }

    @Test
    public void changeEcomm_PositiveNotAllow() throws BusinessException, RTPSCallAPIException {
        PcdCard card = getCard(1);

        cardsService.changeEcomm(card.getCard(), EcommStatusDO.builder().status(ECommerceStatus.NOT_ALLOWED).build());

        verify(cardsService.rtpsWrapper).AddCardToRMSStop("42800202350", card.getCard(), "FLD_022 %% '......[JSTUVW].*'&&(FLD_041!%'PBANK.*'||!FLD_041)&&(2==2)", null, "108", "E-commerce blocked by LinkApp");
    }

    @Test
    public void changeContactlessStatus_Negative() {
        PcdCard card = getCard(1);
        card.setContactless(2);

        // Incorrect current status
        ContactlessStatusDO status = null;
        try {
            status = cardsService.changeContactlessStatus(card.getCard(), ContactlessStatusDO.builder()
                    .status(EContactlessStatus.DISABLED).build());
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.DATA_CONFLICT, e.getErrorCode());
            assertEquals("Incorrect current contactless status pendingActivation", e.getMessage());
        }
        assertNull(status);

        // Cannot set null status
        card.setContactless(1);
        try {
            status = cardsService.changeContactlessStatus(card.getCard(), new ContactlessStatusDO());
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.BAD_REQUEST, e.getErrorCode());
            assertEquals("Allowed values are enabled and disabled", e.getMessage());
        }
        assertNull(status);

        // Cannot set pending statuses
        try {
            status = cardsService.changeContactlessStatus(card.getCard(), ContactlessStatusDO.builder()
                    .status(EContactlessStatus.PENDING_ACTIVATION).build());
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.BAD_REQUEST, e.getErrorCode());
            assertEquals("Allowed values are enabled and disabled", e.getMessage());
        }
        assertNull(status);

        // Cannot set enable already active card
        try {
            status = cardsService.changeContactlessStatus(card.getCard(), ContactlessStatusDO.builder()
                    .status(EContactlessStatus.ENABLED).build());
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.DATA_CONFLICT, e.getErrorCode());
            assertEquals("Only status disabled can be enabled", e.getMessage());
        }
        assertNull(status);

        // Cannot set disable already disabled card
        card.setContactless(0);
        try {
            status = cardsService.changeContactlessStatus(card.getCard(), ContactlessStatusDO.builder()
                    .status(EContactlessStatus.DISABLED).build());
        } catch (BusinessException e) {
            assertEquals(JsonErrorCode.DATA_CONFLICT, e.getErrorCode());
            assertEquals("Only status enabled can be disabled", e.getMessage());
        }
        assertNull(status);
    }

    @Test
    public void changeContactlessStatus_PositiveDisable() throws BusinessException, CMSCallAPIException {
        PcdCard card = getCard(1);
        card.setContactless(1);

        cardsService.changeContactlessStatus(card.getCard(), ContactlessStatusDO.builder().status(EContactlessStatus.DISABLED).build());

        verify(cardsService.cmsWrapper).setChipTagValue(card.getCard(), CardsService.CONTACTLESS_CHIP_TAG, CardsService.CONTACTLESS_DISABLED);
        assertEquals(3, card.getContactless().intValue());
    }

    @Test
    public void changeContactlessStatus_PositiveEnable() throws BusinessException, CMSCallAPIException {
        PcdCard card = getCard(1);
        card.setContactless(0);

        cardsService.changeContactlessStatus(card.getCard(), ContactlessStatusDO.builder().status(EContactlessStatus.ENABLED).build());

        verify(cardsService.cmsWrapper).setChipTagValue(card.getCard(), CardsService.CONTACTLESS_CHIP_TAG, CardsService.CONTACTLESS_ENABLED);
        assertEquals(2, card.getContactless().intValue());
    }

    @Test
    public void getLimits_Positive() throws BusinessException {
        PcdCard card = getCard(1);
        card.setRiskLevel("AC");

        PcdAccumulator cash = new PcdAccumulator();
        cash.setAmountDay(50000L);
        cash.setAmountWeek(150000L);

        PcdAccumulator sales = new PcdAccumulator();
        sales.setAmountDay(80000L);

        when(pcdabaNGManager.getAccumulator("AC", CardsService.LIMITS_CASH)).thenReturn(cash);
        when(pcdabaNGManager.getAccumulator("AC", CardsService.LIMITS_SALES)).thenReturn(sales);

        CardLimitsDO limits = cardsService.getCardLimits(card.getCard());

        assertEquals("AC", limits.getId());
        assertEquals(500, limits.getCashDaily().intValue());
        assertEquals(1500, limits.getCashMonthly().intValue());
        assertEquals(800, limits.getSalesDaily().intValue());
    }

    @Test
    public void setCardLimits_Positive() throws BusinessException, CMSSoapAPIException {
        PcdCard cardBeforeChange = getCard(1);
        cardBeforeChange.setRiskLevel("AC");

        PcdCard cardAfterChange = getCard(1);
        cardAfterChange.setRiskLevel("AL");

        CardLimitsDO limits = CardLimitsDO.builder().id("AL").build();

        PcdAccumulator cash = new PcdAccumulator();
        cash.setAmountDay(50000L);
        cash.setAmountWeek(150000L);

        PcdAccumulator sales = new PcdAccumulator();
        sales.setAmountDay(80000L);

        when(pcdabaNGManager.getCardByCardNumber(cardAfterChange.getCard())).thenReturn(cardAfterChange);
        when(pcdabaNGManager.getAccumulator("AL", CardsService.LIMITS_CASH)).thenReturn(cash);
        when(pcdabaNGManager.getAccumulator("AL", CardsService.LIMITS_SALES)).thenReturn(sales);

        CardLimitsDO newLimits = cardsService.setCardLimits(cardBeforeChange.getCard(), limits);

        assertEquals("AL", newLimits.getId());
        assertEquals(500, newLimits.getCashDaily().intValue());
        assertEquals(1500, newLimits.getCashMonthly().intValue());
        assertEquals(800, newLimits.getSalesDaily().intValue());

        verify(cardsService.cmsSoapWrapper).setRiskLevel(cardAfterChange.getCard(), limits.getId());
    }

    @Test
    public void setCardLimits_NegativeCMSError() throws CMSSoapAPIException {
        PcdCard card = getCard(1);
        card.setRiskLevel("AC");
        CardLimitsDO limits = CardLimitsDO.builder().id("AL").build();

        doThrow(new CMSSoapAPIException("Error")).when(cardsService.cmsSoapWrapper).setRiskLevel(card.getCard(), limits.getId());

        boolean hadError = false;
        try {
            cardsService.setCardLimits(card.getCard(), limits);
        } catch (BusinessException e) {
            hadError = true;
            assertEquals(JsonErrorCode.APPLICATION_ERROR, e.getErrorCode());
            assertEquals("Cannot update card risk level: Error", e.getMessage());
        }
        assertTrue(hadError);
    }

    private PcdCard getCard(int suffix) {
        PcdCard card = new PcdCard();
        card.setBankC("23");
        card.setGroupc("50");
        card.setCard("492176000000000" + suffix);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020 + suffix, 9, 15);
        card.setExpiry1(calendar.getTime());
        card.setStatus1("0");
        card.setCardName("Some name" + suffix);
        card.setCmpgName("Stark Industries");
        card.setUCod10("1122");
        card.setRenew("J");
        when(pcdabaNGManager.getCardByCardNumber(card.getCard())).thenReturn(card);

        return card;
    }

    private PcdAccount getAccount() {
        PcdAccount account = new PcdAccount();
        PcdAccParam accParam = new PcdAccParam();
        accParam.setUfield5("0123456789");
        PcdCurrency pcdCurrency = new PcdCurrency();
        pcdCurrency.setIsoAlpha("EUR");
        pcdCurrency.setExp("2");
        accParam.setPcdCurrency(pcdCurrency);
        accParam.setCrd(15000);
        accParam.setCondSet("102");
        account.setPcdAccParam(accParam);
        account.setAccountNo(new BigDecimal(712207));
        account.setBeginBal(2000);
        PcdClient client = new PcdClient();
        client.setPersonCode("39008100094");
        account.setPcdClient(client);
        when(pcdabaNGManager.getAccountByCardNumber(anyString())).thenReturn(account);
        return account;
    }

    private PcdCondAccnt getCondAccnt(int gracePeriodDayOfMonth, BigDecimal debitInterestPurchaseBase,
                                      BigDecimal debitInterestPurchaseOverdueBase) {
        PcdCondAccnt contAccnt = new PcdCondAccnt();
        contAccnt.setDueDate1(gracePeriodDayOfMonth);
        contAccnt.setDebIntrBaseP(debitInterestPurchaseBase);
        contAccnt.setDebIntrOverPB(debitInterestPurchaseOverdueBase);
        PcdCondAccntPK comp_id = new PcdCondAccntPK("23", "50", "102", "EUR");
        when(pcdabaNGManager.getCondAccntByComp_Id(comp_id)).thenReturn(contAccnt);
        return contAccnt;
    }

    @Test
    public void checkCardHolder() {
        String client = "M1312346";
        String country = "LV";

        List<PcdCard> cards = new ArrayList<>();
        cards.add(getCard(1));
        cards.add(getCard(2));


        PcdAccount account = new PcdAccount();
        PcdAccParam accParam = new PcdAccParam();
        accParam.setUfield5("0123456789");
        account.setPcdAccParam(accParam);

        when(pcdabaNGManager.getClientsCardsByCifOrPersonCode(client, country)).thenReturn(cards);
        when(pcdabaNGManager.getAccountByCardNumber(anyString())).thenReturn(account);

        String customerId = "11111-22223";

        CardListDO listDO = cardsService.readCustomerCards(client, country);
        CardInfoDO CardInfo0 = listDO.getCards().get(0).builder().personalCode("11111-22222").build().setIsCardholder(customerId);
        assertFalse(CardInfo0.isCardholder());
        CardInfoDO CardInfo1 = listDO.getCards().get(1).builder().personalCode("11111-22223").build().setIsCardholder(customerId);
        assertTrue(CardInfo1.isCardholder());
    }

    public static void addLinkAppProperty(String name, String value) {
        try {
            Field prop = LinkAppProperties.class.getDeclaredField("PROPERTIES");
            prop.setAccessible(true);
            Properties props = (Properties) prop.get(null);
            if (props == null) {
                prop.set(null, new Properties());
            }
            props = (Properties) prop.get(LinkAppProperties.class);
            props.put(name, value);

            Field reread = LinkAppProperties.class.getDeclaredField("REREAD");
            reread.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(reread, reread.getModifiers() & ~Modifier.FINAL);
            reread.set(null, new AtomicBoolean(false));

        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

package lv.bank.cards.link.lv;

import lombok.SneakyThrows;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.cms.dao.ProductDAO;
import lv.bank.cards.core.entity.cms.IzdCountry;
import lv.bank.cards.core.entity.cms.IzdOfferedProduct;
import lv.bank.cards.core.entity.cms.IzdOfferedProductPK;
import lv.bank.cards.core.linkApp.dao.AccountsDAO;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dao.ClientsDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAccountInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAccountInfoBase;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAgreement;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfoEMVData;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfoLogicalCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfoPhysicalCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCustomer;
import lv.bank.cards.link.Constants;
import lv.bank.cards.link.Order;
import lv.bank.cards.link.TestBase;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapperTest {

    protected final CommonDAO commonDAO = mock(CommonDAO.class);
    protected final CardDAO cardDAO = mock(CardDAO.class);
    protected final CardsDAO cardsDAO = mock(CardsDAO.class);
    protected final ProductDAO productDAO = mock(ProductDAO.class);
    protected final AccountsDAO accountsDAO = mock(AccountsDAO.class);
    protected final ClientDAO clientDAO = mock(ClientDAO.class);
    protected final ClientsDAO clientsDAO = mock(ClientsDAO.class);

    protected final Context context = mock(Context.class);
    protected Mapper mapper;

    @Before
    @SneakyThrows
    public void initCMSSoapAPIWrapper() {
        mapper = new Mapper(commonDAO, cardDAO, cardsDAO, clientDAO, clientsDAO, productDAO, accountsDAO);
    }

    @Test
    @SneakyThrows
    public void testOrderToRowTypeCustomerPrivate() {
        Order order = TestBase.getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setClientType(Constants.CLIENT_TYPE_PRIVATE);
        order.setBirthdayMask("DD.MM.YYYY");
        order.setOwnerBirthday("25.03.1977");
        order.setClientNumberInAbs("ClientNumberInAbs");
        order.setResident("Resident");
        order.setCountry("Country");
        order.setBankClientSince("BankClientSince");
        order.setClientFirstname("ClientFirstname");
        order.setClientLastname("ClientLastname");
        order.setCrdPasswd("CrdPasswd");
        order.setClientPersonId("ClientPersonId");
        order.setClientStreet("ClientStreet");
        order.setClientCity("ClientCity");
        order.setClientZip("ClientZip");

        final String country = "EE";
        IzdCountry izdCountry = new IzdCountry();
        izdCountry.setCountry(country);

        Date ownerBirthday = new SimpleDateFormat(order.getBirthdayMask()).parse(order.getOwnerBirthday());

        when(commonDAO.getIzdCountryByShortCountryCode(order.getClientCountry())).thenReturn(izdCountry);

        RowTypeCustomer expectedCustomer = mapper.orderToRowTypeCustomer(order);

        assertEquals(order.getClientType(), expectedCustomer.getCLTYPE());
        assertEquals(order.getResident(), expectedCustomer.getRESIDENT());
        assertEquals(order.getCountry(), expectedCustomer.getREGION());
        assertEquals(order.getBankClientSince(), expectedCustomer.getCSINCE());
        assertEquals("10", expectedCustomer.getSTATUS());
        assertEquals(order.getClientFirstname(), expectedCustomer.getFNAMES());
        assertEquals(order.getClientLastname(), expectedCustomer.getSURNAME());
        assertEquals(order.getCrdPasswd(), expectedCustomer.getMIDLENAME());
        assertEquals(order.getClientPersonId(), expectedCustomer.getPERSONCODE());
        assertEquals(order.getClientStreet(), expectedCustomer.getRSTREET());
        assertEquals(order.getClientCity(), expectedCustomer.getRCITY());
        assertEquals(order.getClientZip(), expectedCustomer.getRPCODE());
        assertEquals(country, expectedCustomer.getRCNTRY());
        assertEquals(ownerBirthday, expectedCustomer.getBDATE());
    }

    @Test
    @SneakyThrows
    public void testOrderToRowTypeCustomerCorporate() {
        Order order = TestBase.getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setClientType(Constants.CLIENT_TYPE_CORPORATE);
        order.setResident("Resident");
        order.setCountry("Country");
        order.setBankClientSince("BankClientSince");
        order.setOwnCompanyName("OwnCompanyName");

        RowTypeCustomer expectedCustomer = mapper.orderToRowTypeCustomer(order);

        assertEquals(order.getClientType(), expectedCustomer.getCLTYPE());
        assertEquals(order.getResident(), expectedCustomer.getRESIDENT());
        assertEquals(order.getCountry(), expectedCustomer.getREGION());
        assertEquals(order.getBankClientSince(), expectedCustomer.getCSINCE());
        assertEquals("10", expectedCustomer.getSTATUS());
        assertEquals(order.getOwnCompanyName(), expectedCustomer.getCMPNAME());
        assertEquals(order.getOwnCompanyName(), expectedCustomer.getCMPGNAME());
    }

    @Test
    @SneakyThrows
    public void testOrderToRowTypeAgreementMinSalaryAccount() {

        //TEST 1

        final String branch = "123";
        final String agreementKey = "333";

        Order order = TestBase.getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setBin("Bin");
        order.setClient("Client");
        order.setEmail("Email");
        order.setReportLanguage("ReportLanguage");
        order.setFillPlaceNg("FillPlaceNg");
        order.setRepDistributionMode("RepDistributionMode");
        order.setStmtCity("StmtCity");
        order.setStmtStreet("StmtStreet");
        order.setStmtZip("StmtZip");
        order.setBranch(branch);
        order.setMinSalaryAccount("true");
        order.setAgreementKey(agreementKey);

        final String country = "EE";
        IzdCountry izdCountry = new IzdCountry();
        izdCountry.setCountry(country);

        final String productCode = "ABC";
        IzdOfferedProductPK izdOfferedProductPK = new IzdOfferedProductPK();
        izdOfferedProductPK.setCode(productCode);

        IzdOfferedProduct izdOfferedProduct = new IzdOfferedProduct();
        izdOfferedProduct.setComp_id(izdOfferedProductPK);

        when(commonDAO.getIzdCountryByShortCountryCode(order.getClientCountry())).thenReturn(izdCountry);
        when(commonDAO.getBranchIdByExternalId(branch)).thenReturn(branch);
        when(productDAO.findAvailableProducts(any())).thenReturn(Arrays.asList(izdOfferedProduct));

        RowTypeAgreement expectedAgreement = mapper.orderToRowTypeAgreement(order);

        assertEquals(order.getBin(), expectedAgreement.getBINCOD());
        assertEquals(order.getClient(), expectedAgreement.getCLIENT());
        assertEquals(order.getBankc(), expectedAgreement.getBANKCODE());
        assertEquals(order.getEmail(), expectedAgreement.getEMAILS());
        assertEquals(order.getReportLanguage(), expectedAgreement.getREPLANG());
        assertEquals(order.getFillPlaceNg(), expectedAgreement.getUCOD4());
        assertEquals(order.getRepDistributionMode(), expectedAgreement.getDISTRIBMODE());
        assertEquals(order.getStmtCity(), expectedAgreement.getCITY());
        assertEquals(order.getStmtStreet(), expectedAgreement.getSTREET());
        assertEquals(order.getStmtZip(), expectedAgreement.getPOSTIND());
        assertEquals("10", expectedAgreement.getSTATUS());
        assertNotNull(expectedAgreement.getENROLLED());
        assertEquals(Constants.DEFAULT_PRODUCT, expectedAgreement.getPRODUCT());
        assertEquals(branch, expectedAgreement.getBRANCH());
        assertEquals(country, expectedAgreement.getCOUNTRY());
        assertEquals(new BigDecimal(order.getAgreementKey()), expectedAgreement.getAGRENOM());
        assertEquals(Constants.MIN_BAL_ACCOUNT_RISK_LEVEL_LV, expectedAgreement.getRISKLEVEL());

        //TEST 2

        final String riskLevel = "555";
        order.setRiskLevel(riskLevel);
        order.setMinSalaryAccount("not true");

        when(cardDAO.isRiskLevelLinkedToBin(order.getBin(), order.getRiskLevel())).thenReturn(true);

        expectedAgreement = mapper.orderToRowTypeAgreement(order);

        assertEquals(riskLevel, expectedAgreement.getRISKLEVEL());

        //TEST 3

        final String productRiskLevel = "124";
        order.setRiskLevel(null);
        order.setMinSalaryAccount("not true");
        izdOfferedProduct.setRiskLevel(productRiskLevel);

        expectedAgreement = mapper.orderToRowTypeAgreement(order);

        assertEquals(productRiskLevel, expectedAgreement.getRISKLEVEL());
    }


    @Test
    @SneakyThrows
    public void testOrderToRowTypeCardInfo() {

        //TEST 1

        final String branch = "123";
        final String riskLevel = "88888";
        final String birthdayMask = "dd.MM.YYYY";
        final String clientBirthday = "31.08.2018";
        final Date clientBirthdayDate = new SimpleDateFormat(birthdayMask).parse(clientBirthday);

        Order order = TestBase.getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setClient("Client");
        order.setBaseSupp("BaseSupp");
        order.setClientFirstname("ClientFirstname");
        order.setClientLastname("ClientLastname");
        order.setCrdPasswd("CrdPasswd");
        order.setAuthNotifyDestination("AuthNotifyDestination");
        order.setClientPersonId("ClientPersonId");
        order.setUCod9("UCod9");
        order.setOwnerName("OwnerName");
        order.setBranchToDeliverAt(branch);
        order.setClientType(Constants.CLIENT_TYPE_CORPORATE);
        order.setBirthdayMask(birthdayMask);
        order.setClientBirthday(clientBirthday);
        order.setMinSalaryAccount("true");
        order.setDesignId("300");
        order.setMigratedCardNumber("Something !!!");
        order.setApplicationId("400");

        final String productCode = "ABC";
        IzdOfferedProductPK izdOfferedProductPK = new IzdOfferedProductPK();
        izdOfferedProductPK.setCode(productCode);

        final IzdOfferedProduct izdOfferedProduct = new IzdOfferedProduct();
        izdOfferedProduct.setComp_id(izdOfferedProductPK);
        izdOfferedProduct.setRiskLevel(riskLevel);
        izdOfferedProduct.setAuthLevel("0");

        when(commonDAO.getBranchIdByExternalId(branch)).thenReturn(branch);
        when(productDAO.findAvailableProducts(any())).thenReturn(Arrays.asList(izdOfferedProduct));

        RowTypeCardInfo expectedCardInfo = mapper.orderToRowTypeCardInfo(order);

        RowTypeCardInfoLogicalCard logicalCard = expectedCardInfo.getLogicalCard();
        RowTypeCardInfoPhysicalCard physicalCard = expectedCardInfo.getPhysicalCard();
        RowTypeCardInfoEMVData emvData = expectedCardInfo.getEMVData();

        assertEquals(order.getClient(), logicalCard.getCLIENT());
        assertEquals(order.getBaseSupp(), logicalCard.getBASESUPP());
        assertEquals(order.getClientFirstname(), logicalCard.getFNAMES());
        assertEquals(order.getClientLastname(), logicalCard.getSURNAME());
        assertEquals(order.getCrdPasswd(), logicalCard.getMNAME());
        assertEquals(order.getAuthNotifyDestination(), logicalCard.getUFIELD7());
        assertEquals(order.getClientPersonId(), logicalCard.getIDCARD());
        assertEquals(order.getUCod9(), logicalCard.getUCOD9());
        assertEquals(order.getOrderId(), logicalCard.getUFIELD8());
        assertEquals(order.getOwnerName(), logicalCard.getCMPGNAME());
        assertEquals(branch, logicalCard.getUCOD10());
        assertEquals(izdOfferedProduct.getCondCard(), logicalCard.getCONDSET());
        assertEquals(izdOfferedProduct.getCardType(), logicalCard.getCARDTYPE());
        assertEquals(clientBirthdayDate, logicalCard.getBDATE());
        assertEquals(Constants.MIN_BAL_ACCOUNT_RISK_LEVEL_LV, logicalCard.getRISKLEVEL());
        assertEquals(order.getCardName(), physicalCard.getCARDNAME());
        assertEquals(new BigDecimal(order.getDesignId()), physicalCard.getDESIGNID());
        assertNotNull(physicalCard.getEXPIRY1());
        assertEquals(new BigDecimal(order.getApplicationId()), emvData.getCHIPAPPID());
        assertEquals("0", logicalCard.getAUTHLIMIT());

        //TEST 2

        order.setMinSalaryAccount("false");
        order.setRiskLevel("Some level");
        order.setAuthLevel("1");

        when(cardDAO.isRiskLevelLinkedToBin(order.getBin(), order.getRiskLevel())).thenReturn(true);

        expectedCardInfo = mapper.orderToRowTypeCardInfo(order);

        logicalCard = expectedCardInfo.getLogicalCard();

        assertEquals(order.getRiskLevel(), logicalCard.getRISKLEVEL());
        assertEquals(order.getAuthLevel(), logicalCard.getAUTHLIMIT());

        //TEST 3

        order.setMinSalaryAccount("false");
        order.setRiskLevel(null);

        expectedCardInfo = mapper.orderToRowTypeCardInfo(order);
        logicalCard = expectedCardInfo.getLogicalCard();
        assertEquals(riskLevel, logicalCard.getRISKLEVEL());

        //TEST 4

        order.setDesignId(null);
        izdOfferedProduct.setChipDesignId(999L);

        expectedCardInfo = mapper.orderToRowTypeCardInfo(order);
        physicalCard = expectedCardInfo.getPhysicalCard();
        assertTrue(Long.valueOf("999") == physicalCard.getDESIGNID().longValue());

    }

    @Test
    @SneakyThrows
    public void testOrderRowTypeAccountInfo() {
        final String productCode = "ABC";
        final String condAccnt = "condAccnt";
        final String cardAccountNoCms = "cardAccountNoCms";
        final BigDecimal accountNoCms = new BigDecimal(555);

        Order order = TestBase.getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setAccountNoCms(accountNoCms);
        order.setCardAccountNoCms(cardAccountNoCms);
        order.setAccountCcy("AccountCcy");
        order.setAccountNoPlaton("AccountNoPlaton");

        IzdOfferedProductPK izdOfferedProductPK = new IzdOfferedProductPK();
        izdOfferedProductPK.setCode(productCode);

        final IzdOfferedProduct izdOfferedProduct = new IzdOfferedProduct();
        izdOfferedProduct.setComp_id(izdOfferedProductPK);
        izdOfferedProduct.setCondAccnt(condAccnt);

        when(productDAO.findAvailableProducts(any())).thenReturn(Arrays.asList(izdOfferedProduct));

        RowTypeAccountInfo accountInfo = mapper.orderRowTypeAccountInfo(order);

        RowTypeAccountInfoBase baseInfo = accountInfo.getBaseInfo();

        assertEquals(condAccnt, baseInfo.getCONDSET());
        assertEquals(accountNoCms, baseInfo.getACCOUNTNO());
        assertEquals(order.getCardAccountNoCms(), baseInfo.getCARDACCT());

        assertEquals(order.getAccountCcy(), baseInfo.getCCY());
        assertEquals(order.getAccountNoPlaton(), baseInfo.getUFIELD5());
        assertEquals("00", baseInfo.getCACCNTTYPE());
        assertEquals("0", baseInfo.getCYCLE());
        assertEquals("0", baseInfo.getSTATUS());
        assertEquals("1", baseInfo.getSTATCHANGE());
        assertEquals(BigDecimal.ZERO, baseInfo.getMINBAL());
        assertEquals(BigDecimal.ZERO, baseInfo.getNONREDUCEBAL());
        assertEquals(BigDecimal.ZERO, baseInfo.getCRD());
        assertEquals(BigDecimal.ZERO, baseInfo.getLIMAMNT());
    }

    @Test
    public void randomExpiryTest() throws DataIntegrityException {
        Date today = new Date();
        try {
            mapper.randomExpiry(-1, 0);
        } catch (DataIntegrityException e) {
            assertEquals(e.getMessage(), "minMonths value has to be greater then 0, was: -1");
        }

        try {
            mapper.randomExpiry(1, 0);
        } catch (DataIntegrityException e) {
            assertEquals(e.getMessage(), "maxMonths value has to be greater then minMonths, was minMonths: 1, maxMonths : 0");
        }

        try {
            mapper.randomExpiry(1, 37);
        } catch (DataIntegrityException e) {
            assertEquals(e.getMessage(), "maxMonths value cannot exceed 36, was : 37");
        }

        assertTrue((TestBase.getMonthsBetween(today, mapper.randomExpiry(24, 36)) > 23));
        assertTrue((TestBase.getMonthsBetween(today, mapper.randomExpiry(24, 36)) < 37));
        assertEquals(TestBase.getMonthsBetween(today, mapper.randomExpiry(24, 24)), 24);
    }

}

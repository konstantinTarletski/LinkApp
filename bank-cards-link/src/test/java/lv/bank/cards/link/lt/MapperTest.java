package lv.bank.cards.link.lt;

import lombok.SneakyThrows;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.cms.dao.ProductDAO;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdBranch;
import lv.bank.cards.core.entity.cms.IzdBranchPK;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdClAcct;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.entity.cms.IzdClientPK;
import lv.bank.cards.core.entity.cms.IzdCompany;
import lv.bank.cards.core.entity.cms.IzdCompanyPK;
import lv.bank.cards.core.entity.cms.IzdCondCard;
import lv.bank.cards.core.entity.cms.IzdCondCardPK;
import lv.bank.cards.core.entity.cms.IzdCountry;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.linkApp.dao.AccountsDAO;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dao.ClientsDAO;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAccountInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAccountInfoBase;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAgreement;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfoEMVData;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfoLogicalCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfoPhysicalCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCustomer;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCardRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCustomerRequest;
import lv.bank.cards.link.Constants;
import lv.bank.cards.link.Order;
import lv.bank.cards.link.TestBase;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static lv.bank.cards.link.TestBase.BANKC;
import static lv.bank.cards.link.TestBase.DEFAULT_CLIENT_COUNTRY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    public void checkAgeOlderThan14() {
        assertEquals("1999", mapper.convertYearTo4digit("1199"));
        assertEquals("2011", mapper.convertYearTo4digit("1111"));
    }

    @Test
    @SneakyThrows
    public void testPopulatePcdClientAndToRowTypeEditCustomerRequest() {

        //TEST 1

        final String empCompanyRegNr = "empCompanyRegNr";
        final String izdCompanyCode = "izdCompanyCode";
        final String client = "client";
        final String countryLT3Letters = "LTU";

        Order order = TestBase.getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setClientType(Constants.CLIENT_TYPE_CORPORATE);
        order.setClientFirstname("ClientFirstname");
        order.setOwnerPhone("OwnerPhone");
        order.setClientStreet("ClientStreet");
        order.setClientCity("ClientCit");
        order.setClientZip("ClientZip");
        order.setClientCountry("ClientCountry");
        order.setResident("Resident");
        order.setClientCategory("ClientCategory");
        order.setEmpCompanyRegNr(empCompanyRegNr);
        order.setClientLastname("ClientLastname");
        order.setCardHolderEmployerName("CardHolderEmployerName");

        IzdClientPK izdClientPK = new IzdClientPK();
        izdClientPK.setClient(client);

        IzdClient izdClient = new IzdClient();
        izdClient.setComp_id(izdClientPK);

        PcdClient pcdClient = new PcdClient();

        IzdCompanyPK izdCompanyPK = new IzdCompanyPK();
        izdCompanyPK.setCode(izdCompanyCode);

        IzdCompany izdCompany = new IzdCompany();
        izdCompany.setComp_id(izdCompanyPK);

        IzdCountry izdCountry = new IzdCountry();
        izdCountry.setCountry(countryLT3Letters);
        izdCountry.setCountryShort(lv.bank.cards.core.utils.Constants.DEFAULT_COUNTRY_LT);

        RowTypeEditCustomerRequest customer = new RowTypeEditCustomerRequest();

        when(commonDAO.findIzdCompanyByRegCodeUR(order.getEmpCompanyRegNr())).thenReturn(izdCompany);
        when(commonDAO.getIzdCountryByShortCountryCode(order.getClientCountry())).thenReturn(izdCountry);

        boolean changed = mapper.populatePcdClientAndToRowTypeEditCustomerRequest(order, customer, izdClient, pcdClient);

        assertTrue(changed);
        assertEquals(customer.getBANKC(), BANKC);
        assertEquals(customer.getCLIENT(), client);
        assertEquals(order.getClientType(), customer.getCLTYPE());
        assertEquals(order.getClientFirstname(), customer.getFNAMES());
        assertEquals(order.getOwnerPhone(), customer.getRPHONE());
        assertEquals(order.getClientStreet(), customer.getRSTREET());
        assertEquals(order.getClientCity(), customer.getRCITY());
        assertEquals(order.getClientZip(), customer.getRPCODE());
        assertEquals(countryLT3Letters, customer.getRCNTRY());
        assertEquals(order.getResident(), customer.getRESIDENT());
        assertEquals(order.getClientCategory(), customer.getCLNCAT());
        assertEquals(izdCompanyCode, customer.getEMPCODE());
        assertEquals(order.getClientLastname(), customer.getCMPNAME());
        assertEquals(order.getCardHolderEmployerName(), customer.getCMPGNAME());
        assertEquals(order.getClientPersonId(), customer.getREGNR());

        assertEquals(order.getClientFirstname(), pcdClient.getFirstNames());
        assertEquals(order.getOwnerPhone(), pcdClient.getRPhone());
        assertEquals(order.getClientStreet(), pcdClient.getRStreet());
        assertEquals(order.getClientCity(), pcdClient.getRCity());
        assertEquals(order.getClientZip(), pcdClient.getRPcode());
        assertEquals(countryLT3Letters, pcdClient.getRCntry());
        assertEquals(order.getResident(), pcdClient.getResident());
        assertEquals(izdCompanyCode, pcdClient.getEmpCode());
        assertEquals(order.getClientLastname(), pcdClient.getCmpName());

        //TEST 2

        customer = new RowTypeEditCustomerRequest();

        order.setClientType(Constants.CLIENT_TYPE_PRIVATE);

        changed = mapper.populatePcdClientAndToRowTypeEditCustomerRequest(order, customer, izdClient, pcdClient);

        assertTrue(changed);
        assertEquals(order.getClientLastname(), customer.getSURNAME());
        assertEquals(order.getClientPersonId(), customer.getPERSONCODE());

        assertEquals(order.getClientLastname(), pcdClient.getLastName());
        assertEquals(order.getClientPersonId(), pcdClient.getPersonCode());
    }

    @Test
    @SneakyThrows
    public void testOrderToRowTypeEditCardRequestLTInformationChange() {

        //TEST 1

        final String condSet = "condSet";
        final String renew = "renew";
        final String renewOld = "RenewOld";

        Order order = TestBase.getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setOwnerPersonId("OwnerPersonId");
        order.setOwnerLastname("sOwnerLastname");
        order.setCardConditionSet("CardConditionSet");
        order.setRiskLevel("RiskLevel");
        order.setCardHolderEmployerName("CardHolderEmployerName");
        order.setCrdPasswd("CrdPasswd");
        order.setDesignId("DesignId");
        order.setBranchToDeliverAt(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_9999);
        order.setCardInsuranceFlag("1");
        order.setCardRenewFlag("n");

        PcdCard pcdCard = new PcdCard();
        pcdCard.setRenew("A");
        pcdCard.setRenewOld(renewOld);

        when(commonDAO.getIzdBranchByRegCodeUR(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_9999))
                .thenReturn(getIzdBranch(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_9999));

        RowTypeEditCardRequest editCard = mapper.populatePcdCardAndRowTypeEditCardRequest(
                order, pcdCard, condSet, renew);

        assertEquals(TestBase.DEFAULT_CARD_NUMBER, editCard.getCARD());
        assertEquals(condSet, editCard.getBASESUPP());
        assertEquals(TestBase.DEFAULT_CARD_NAME, editCard.getCARDNAME());
        assertEquals(TestBase.DEFAULT_CARD_NAME, editCard.getMCNAME());
        assertEquals(order.getOwnerPersonId(), editCard.getIDCARD());
        assertEquals(order.getOwnerFirstname(), editCard.getFNAMES());
        assertEquals(order.getOwnerLastname(), editCard.getSURNAME());
        assertEquals(order.getCardConditionSet(), editCard.getCONDSET());
        assertEquals(order.getRiskLevel(), editCard.getRISKLEVEL());
        assertEquals(order.getCardHolderEmployerName(), editCard.getCMPGNAME());
        assertEquals(order.getCrdPasswd(), editCard.getMNAME());
        assertEquals(order.getDesignId(), editCard.getUFIELD8());
        //assertEquals(Constants.DELIVERY_BRANCH_LV, editCard.getUCOD10());
        //assertNotNull(editCard.getUBFIELD1());
        assertEquals("N", editCard.getRENEW());

        assertEquals(TestBase.DEFAULT_CARD_NAME, pcdCard.getCardName());
        assertEquals(TestBase.DEFAULT_CARD_NAME, pcdCard.getMcName());
        assertEquals(order.getOwnerPersonId(), pcdCard.getIdCard());
        assertEquals(order.getCardConditionSet(), pcdCard.getCondSet());
        assertEquals(order.getRiskLevel(), pcdCard.getRiskLevel());
        assertEquals(order.getCardHolderEmployerName(), pcdCard.getCmpgName());
        assertEquals(order.getCrdPasswd(), pcdCard.getMName());
        assertEquals(order.getDesignId(), pcdCard.getUField8());
        assertEquals(lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LT_9999, pcdCard.getUCod10());
        assertNotNull(pcdCard.getUBField1());
        assertEquals("Y", pcdCard.getInsuranceFlag());
        assertEquals("E", pcdCard.getRenewOld());
        assertEquals("N", pcdCard.getRenew());

        //TEST 2

        pcdCard = new PcdCard();
        pcdCard.setRenew("n");
        pcdCard.setRenewOld(renewOld);

        order.setCardInsuranceFlag("0");
        order.setCardRenewFlag("j");

        editCard = mapper.populatePcdCardAndRowTypeEditCardRequest(order, pcdCard, condSet, renew);

        assertEquals("N", pcdCard.getInsuranceFlag());
        assertEquals("N", pcdCard.getRenewOld());
        assertEquals(renewOld, pcdCard.getRenew());

        assertEquals(renewOld, editCard.getRENEW());
    }

    @Test
    @SneakyThrows
    public void testOrderToRowTypeCustomer() {
        final String clientCategory = "ClientCategory";

        Order order = TestBase.getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setClientType(Constants.CLIENT_TYPE_CORPORATE);
        order.setClientCategory(clientCategory);
        order.setResident("Resident");
        order.setBankClientSince("1101");
        order.setBirthdayMask("DD.MM.YYYY");
        order.setOwnerBirthday("25.03.1977");
        order.setClientFirstname("ClientFirstname");
        order.setCrdPasswd("CrdPasswd");
        order.setOwnerPhone("OwnerPhone");
        order.setClientStreet("ClientStreet");
        order.setClientCity("ClientCity");
        order.setClientZip("ClientZip");
        order.setClientLastname("ClientLastname");
        order.setCardHolderEmployerName("CardHolderEmployerName");

        Date ownerBirthday = new SimpleDateFormat(order.getBirthdayMask()).parse(order.getOwnerBirthday());

        final String country = "EE";
        IzdCountry izdCountry = new IzdCountry();
        izdCountry.setCountry(country);

        when(commonDAO.getIzdCountryByShortCountryCode(DEFAULT_CLIENT_COUNTRY)).thenReturn(izdCountry);

        RowTypeCustomer customer = mapper.orderToRowTypeCustomer(order);

        assertEquals(order.getClientType(), customer.getCLTYPE());
        assertEquals("10", customer.getSTATUS());
        assertEquals(order.getClientCategory(), customer.getCLNCAT());
        assertEquals(order.getResident(), customer.getRESIDENT());
        assertEquals("2001", customer.getCSINCE());
        assertEquals(order.getClientFirstname(), customer.getFNAMES());
        assertEquals(order.getCrdPasswd(), customer.getMIDLENAME());
        assertEquals(order.getOwnerPhone(), customer.getRPHONE());
        assertEquals(order.getClientStreet(), customer.getRSTREET());
        assertEquals(order.getClientCity(), customer.getRCITY());
        assertEquals(order.getClientZip(), customer.getRPCODE());
        assertEquals(country, customer.getRCNTRY());
        assertEquals(ownerBirthday, customer.getBDATE());
        assertEquals(order.getClientLastname(), customer.getCMPNAME());
        assertEquals(order.getCardHolderEmployerName(), customer.getCMPGNAME());
        assertEquals(order.getClientPersonId(), customer.getREGNR());

        //TEST 2

        order.setClientType(Constants.CLIENT_TYPE_PRIVATE);

        customer = mapper.orderToRowTypeCustomer(order);

        assertEquals(order.getClientLastname(), customer.getSURNAME());
        assertEquals(order.getClientPersonId(), customer.getPERSONCODE());
    }

    @Test
    @SneakyThrows
    public void testOrderRowTypeAccountInfo() {
        Order order = TestBase.getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setAccountCcy("AccountCcy");
        order.setUField5("UField5");
        order.setCardAccountNoCms("cardAccountNoCms");
        order.setAccountCondSet("AccountCondSet");

        RowTypeAccountInfo accountInfo = mapper.orderRowTypeAccountInfo(order);

        RowTypeAccountInfoBase baseInfo = accountInfo.getBaseInfo();

        assertEquals(order.getAccountCcy(), baseInfo.getCCY());
        assertEquals(order.getUField5(), baseInfo.getUFIELD5());
        assertEquals(order.getAccountCondSet(), baseInfo.getCONDSET());

        assertEquals("0", baseInfo.getADJUSTFLAG());
        assertEquals("1", baseInfo.getSTATCHANGE());
        assertEquals(BigDecimal.ZERO, baseInfo.getCRD());
        assertEquals(BigDecimal.ZERO, baseInfo.getMINBAL());
        assertEquals(BigDecimal.ZERO, baseInfo.getNONREDUCEBAL());
        assertEquals("0", baseInfo.getCYCLE());
        assertEquals("0", baseInfo.getSTATUS());
        assertEquals("00", baseInfo.getCACCNTTYPE());
        assertEquals(BigDecimal.ZERO, baseInfo.getLIMINTR());
    }

    @Test
    @SneakyThrows
    public void testOrderToRowTypeCardInfo() {

        //TEST 1

        final String branch = "123";
        final String cardType = "cardType";
        final Calendar cardExpiryCalendar = Calendar.getInstance();
        cardExpiryCalendar.add(Calendar.YEAR, 1);
        cardExpiryCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
        cardExpiryCalendar.set(Calendar.DATE, 31);
        final SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        final String cardExpiry = outputDateFormat.format(cardExpiryCalendar.getTime());

        Order order = TestBase.getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setClient("Client");
        order.setOwnerFirstname("OwnerFirstname");
        order.setOwnerLastname("OwnerLastname");
        order.setCrdPasswd("CrdPasswd");
        order.setAuthNotifyDestination("AuthNotifyDestination");
        order.setClientPersonId("ClientPersonId");
        order.setUCod9("UCod9");
        order.setCardConditionSet("CardConditionSet");
        order.setRiskLevel("RiskLevel");
        order.setDesignId("tDesignId");
        order.setBranchToDeliverAt(branch);
        order.setCardHolderEmployerName("CardHolderEmployerName");
        order.setAuthLevel("222");
        order.setBaseSupp(null);
        order.setChipDesignId("333");
        order.setMigratedCardNumber("MigratedCardNumber");
        order.setCardExpiry(cardExpiry);
        order.setApplicationId("444");

        IzdAccount account = null;

        IzdCondCard izdCondCard = new IzdCondCard();
        izdCondCard.setCardType(cardType);

        when(commonDAO.getIzdBranchByRegCodeUR(branch)).thenReturn(getIzdBranch(branch));
        when(cardDAO.getIzdCondCardByID(any(IzdCondCardPK.class))).thenReturn(izdCondCard);

        RowTypeCardInfo expectedCardInfo = mapper.orderToRowTypeCardInfo(order, account);

        RowTypeCardInfoLogicalCard logicalCard = expectedCardInfo.getLogicalCard();
        RowTypeCardInfoPhysicalCard physicalCard = expectedCardInfo.getPhysicalCard();
        RowTypeCardInfoEMVData emvData = expectedCardInfo.getEMVData();

        verify(cardDAO, times(1)).getIzdCondCardByID(any(IzdCondCardPK.class));

        assertEquals(order.getClient(), logicalCard.getCLIENT());
        assertEquals(order.getOwnerFirstname(), logicalCard.getFNAMES());
        assertEquals(order.getOwnerLastname(), logicalCard.getSURNAME());
        assertEquals(order.getCrdPasswd(), logicalCard.getMNAME());
        assertEquals(order.getAuthNotifyDestination(), logicalCard.getUFIELD7());
        assertEquals(order.getClientPersonId(), logicalCard.getIDCARD());
        assertEquals(order.getUCod9(), logicalCard.getUCOD9());
        assertEquals(order.getCardConditionSet(), logicalCard.getCONDSET());
        assertEquals(order.getRiskLevel(), logicalCard.getRISKLEVEL());
        assertEquals(order.getDesignId(), logicalCard.getUFIELD8());
        assertEquals(branch, logicalCard.getUCOD10());
        assertEquals(cardType, logicalCard.getCARDTYPE());
        assertEquals(order.getCardHolderEmployerName(), logicalCard.getCMPGNAME());
        assertEquals(new BigDecimal(Constants.RANGE_ID_PREFIX + order.getAuthLevel()), logicalCard.getRANGEID());
        assertEquals("1", logicalCard.getBASESUPP());

        assertEquals(order.getCardName(), physicalCard.getCARDNAME());
        assertEquals(new BigDecimal(order.getChipDesignId()), physicalCard.getDESIGNID());
        assertEquals(outputDateFormat.parse(cardExpiry), physicalCard.getEXPIRY1());

        assertEquals(new BigDecimal(order.getApplicationId()), emvData.getCHIPAPPID());

        //TEST 2

        order.setBaseSupp("2222");
        order.setBin("444");

        IzdCard izdCard = new IzdCard();
        izdCard.setStatus1("not 2");
        izdCard.setBaseSupp("1");
        izdCard.setBinCode(order.getBin());

        Set<IzdCard> izdCards = new HashSet<>();
        izdCards.add(izdCard);

        IzdClAcct izdClAcct = new IzdClAcct();
        izdClAcct.setIzdCards(izdCards);

        Set<IzdClAcct> izdClAccts = new HashSet<>();
        izdClAccts.add(izdClAcct);

        account = new IzdAccount();
        account.setIzdClAccts(izdClAccts);

        expectedCardInfo = mapper.orderToRowTypeCardInfo(order, account);
        logicalCard = expectedCardInfo.getLogicalCard();
        physicalCard = expectedCardInfo.getPhysicalCard();
        emvData = expectedCardInfo.getEMVData();

        assertEquals("2", logicalCard.getBASESUPP());
    }

    @Test
    @SneakyThrows
    public void testOrderToRowTypeAgreement() {
        final String branch = "123";

        Order order = TestBase.getValidOrder(Constants.CARD_RENEW_ACTION);
        order.setBin("Bin");
        order.setClient("Client");
        order.setEmail("Email");
        order.setReportLanguage("ReportLanguage");
        order.setRepDistributionMode("RepDistributionMode");
        order.setFillPlaceNg("FillPlaceNg");
        order.setClientCity("ClientCity");
        order.setClientStreet("ClientStreet");
        order.setClientZip("ClientZip");
        order.setBranch(branch);
        order.setRiskLevel("RiskLevel");

        final String country = "EE";
        IzdCountry izdCountry = new IzdCountry();
        izdCountry.setCountry(country);

        when(commonDAO.getIzdCountryByShortCountryCode(order.getClientCountry())).thenReturn(izdCountry);
        when(commonDAO.getIzdBranchByRegCodeUR(branch)).thenReturn(getIzdBranch(branch));

        RowTypeAgreement expectedAgreement = mapper.orderToRowTypeAgreement(order);

        assertEquals(order.getBin(), expectedAgreement.getBINCOD());
        assertEquals(order.getClient(), expectedAgreement.getCLIENT());
        assertEquals(order.getBankc(), expectedAgreement.getBANKCODE());
        assertEquals(order.getEmail(), expectedAgreement.getEMAILS());
        assertEquals(order.getReportLanguage(), expectedAgreement.getREPLANG());
        assertEquals(order.getRepDistributionMode(), expectedAgreement.getDISTRIBMODE());
        assertEquals(order.getFillPlaceNg(), expectedAgreement.getUCOD4());
        assertEquals(order.getClientCity(), expectedAgreement.getCITY());
        assertEquals(order.getClientStreet(), expectedAgreement.getSTREET());
        assertEquals(order.getClientZip(), expectedAgreement.getPOSTIND());
        assertEquals(Constants.DEFAULT_PRODUCT, expectedAgreement.getPRODUCT());
        assertEquals(country, expectedAgreement.getCOUNTRY());
        assertEquals(branch, expectedAgreement.getBRANCH());
        assertEquals(order.getRiskLevel(), expectedAgreement.getRISKLEVEL());
    }

    protected IzdBranch getIzdBranch(String branchCode) {
        IzdBranchPK izdBranchPK = new IzdBranchPK();
        izdBranchPK.setBranch(branchCode);

        IzdBranch izdBranch = new IzdBranch();
        izdBranch.setComp_id(izdBranchPK);
        return izdBranch;
    }

}

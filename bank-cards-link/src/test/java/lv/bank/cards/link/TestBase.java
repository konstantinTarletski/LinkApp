package lv.bank.cards.link;

import lombok.SneakyThrows;
import lv.bank.cards.core.cms.dao.AccountDAO;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.cms.dao.ProductDAO;
import lv.bank.cards.core.entity.cms.IzdAgreement;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdClAcct;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.entity.cms.IzdClientPK;
import lv.bank.cards.core.entity.cms.IzdCondCard;
import lv.bank.cards.core.entity.cms.IzdCondCardPK;
import lv.bank.cards.core.linkApp.dao.AccountsDAO;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dao.ClientsDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.soap.ejb.CMSSoapAPIWrapperBean;
import lv.bank.cards.core.vendor.api.cms.soap.service.IssuingPort;
import lv.bank.cards.core.vendor.api.cms.soap.service.IssuingService;
import lv.bank.cards.rtcu.util.BankCardsWSWrapperDelegate;
import lv.bank.cards.rtcu.util.BankCardsWSWrapperService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import javax.naming.Context;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class TestBase {

    public static final String U_AFIELD1_GENERATED_VALUE = "authCode";

    public static final String DEFAULT_CARD_NUMBER = "4775730000000001";
    public static final String REPLACED_CARD_NUMBER = "4775730000000002";
    public static final String DEFAULT_CARD_NAME = "Any Name";
    public static final String DEFAULT_DLV_LANGUAGE = "1";
    public static final String DEFAULT_CLIENT_COUNTRY = "LV";
    public static final String EXTERNAL_BRANCH = "E888";
    public static final String TODAY = new SimpleDateFormat("yyyyMMdd").format(new Date());

    public static final String USERNAME = "abc";
    public static final String PASSWORD_CODED = "-331b07b3c5df1bad5c22449d87106f42008fba5553e14ea0";
    public static final String PASSWORD = "superSecretPassword";
    public static final String CMS_SERVICE_URL = "https://any.url.yes";
    public static final String BANKC = "23";
    public static final String GROUPC = "50";

    public static final String CCY = "EUR";

    protected final Context context = mock(Context.class);
    protected final IssuingService service = mock(IssuingService.class);
    protected final IssuingPort issuingPort = mock(IssuingPort.class);
    protected final CMSSoapAPIWrapperBean cmsSoapAPIWrapperBean = mock(CMSSoapAPIWrapperBean.class);

    protected OrderValidatorBase orderValidator;
    protected OrderProcessorBase orderProcessor;

    protected final CommonDAO commonDAO = mock(CommonDAO.class);
    protected final ProductDAO productDAO = mock(ProductDAO.class);
    protected final ClientDAO clientDAO = mock(ClientDAO.class);
    protected final ClientsDAO clientsDAO = mock(ClientsDAO.class);
    protected final CardDAO cardDAO = mock(CardDAO.class);
    protected final CardsDAO cardsDAO = mock(CardsDAO.class);
    protected final AccountDAO accountDAO = mock(AccountDAO.class);
    protected final AccountsDAO accountsDAO = mock(AccountsDAO.class);
    protected final CMSCallAPIWrapper cmsCallAPIWrapper = mock(CMSCallAPIWrapper.class);
    protected final BankCardsWSWrapperService bankCardsWSWrapperService = mock(BankCardsWSWrapperService.class);
    protected final BankCardsWSWrapperDelegate bankCardsWSWrapperDelegate = mock(BankCardsWSWrapperDelegate.class);

    protected MapperBase mapper;

    @SneakyThrows
    public void initCMSSoapAPIWrapper() {
        addLinkAppProperty(LinkAppProperties.CMS_SOAP_WSDL_URL, CMS_SERVICE_URL);
        addLinkAppProperty(LinkAppProperties.CMS_SOAP_USERNAME, USERNAME);
        addLinkAppProperty(LinkAppProperties.CMS_BANK_CODE, BANKC);
        addLinkAppProperty(LinkAppProperties.CMS_GROUP_CODE, GROUPC);
        addLinkAppProperty(LinkAppProperties.CMS_SOAP_PASSWORD, PASSWORD_CODED);
        doReturn(issuingPort).when(service).getIssuing(USERNAME, PASSWORD, CMS_SERVICE_URL);

        doReturn(bankCardsWSWrapperDelegate).when(bankCardsWSWrapperService).getBankCardsWSWrapperPort();
        when(cardsDAO.getNextPcdPinIDWithAuthentificationCode(anyString())).thenReturn(U_AFIELD1_GENERATED_VALUE);
        when(commonDAO.getBranchIdByExternalId(anyString())).thenReturn("B12");
    }

    @Test
    public void pinRetainingHelperTest() {
        PinRetainingHelper helper = new PinRetainingHelper("5123123412341234", "cardPinBlock1234", "06");
        assertEquals("5123123412341234   cardPinBlock1234                06 ", helper.getData());
        assertEquals("5123123412341234", helper.getCardNumber());
        assertEquals("cardPinBlock1234", helper.getPinBlock());
        assertEquals("06", helper.getPinKeyId());
    }

    @Test
    public void getMinimalBalanceAccountRiskLevelByCountryTest() {
        assertEquals("IL", mapper.getMinimalBalanceAccountRiskLevelByCountry("LV"));
        assertEquals("MSC", mapper.getMinimalBalanceAccountRiskLevelByCountry("EE"));
        assertEquals("IL", mapper.getMinimalBalanceAccountRiskLevelByCountry(null));
    }

    public static long getMonthsBetween(Date date1, Date date2) {
        LocalDate d1 = date1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate d2 = date2.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return ChronoUnit.MONTHS.between(d1, d2);
    }

    public static Order getValidOrder(String action) {
        Order order = new Order();
        order.setCardNumber(DEFAULT_CARD_NUMBER);
        order.setOrderId("123");
        order.setBankc(BANKC);
        order.setGroupc(GROUPC);
        order.setAction(action);
        order.setClientType(Constants.CLIENT_TYPE_PRIVATE);
        order.setApplicationId("100");
        order.setCardName(DEFAULT_CARD_NAME);
        order.setDlvLanguage(DEFAULT_DLV_LANGUAGE);
        order.setClientCountry(DEFAULT_CLIENT_COUNTRY);
        order.setIban("IBAN");
        return order;
    }

    protected static void addDeliveryAddress(Order o) {
        o.setDlvAddrCity("Riga reg.");
        o.setDlvAddrCode("C123");
        o.setDlvAddrCountry("LV");
        o.setDlvAddrStreet1("Skansktes 12");
        o.setDlvAddrStreet2("Riga");
        o.setDlvAddrZip("LV-1048");
        o.setDlvLanguage("1");
        o.setDlvCompany("DNB");
    }

    protected IzdCard setUpIzdCard(String cardNumber) {
        IzdCard card = new IzdCard();
        card.setCard(cardNumber);
        IzdClAcct izdClAcct = new IzdClAcct();
        card.setIzdClAcct(izdClAcct);
        when(cardDAO.getObject(IzdCard.class, cardNumber)).thenReturn(card);
        return card;
    }

    protected void testValidationDataIntegrityException(Order o, String message) {
        String errorMessage = null;
        try {
            orderValidator.validateOrder(o);
        } catch (DataIntegrityException e) {
            errorMessage = e.getMessage();
        }
        assertEquals(message, errorMessage);
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

    public String getCarAutoRenewRequestXml(String cardNumber) {
        return "<do what=\"information-change\"><card pan=\"" + cardNumber + "\"><auto-renew>1</auto-renew></card></do>";
    }

    public String getCarAutoRenewResponseXml(String cardNumber) {
        return "OK";
    }

    public String getCardStopListResponseXml(String cardNumber) {
        return "<done><add-card-to-stoplist><added>" + cardNumber + "</added></add-card-to-stoplist></done>";
    }

    public String getCardStopListRequestXml(String cardNumber, String autoBlockCard) {
        return "<do what=\"add-card-to-stoplist\"><card>" + cardNumber + "</card><description>Auto-blocked for replacement</description><cause>"
                + autoBlockCard + "</cause></do>";
    }

    public String getCardUpdateXml(String operation, String card, String region, String uAfield1, String uAfield2, String cardName,
                                   String mcName, String uCod10, String uBfield1, String uField8) {
        StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Changes_request><document><version/>"
                + "<DOC_NAME>card</DOC_NAME><OPERATION>" + operation + "</OPERATION><EXTERNAL_ID>1</EXTERNAL_ID>"
                + "<details><CARD>" + card + "</CARD><BANK_C>" + BANKC + "</BANK_C><GROUPC>" + GROUPC + "</GROUPC>");

        if (StringUtils.isNotBlank(region)) {
            result.append("<REGION>" + region + "</REGION>");
        } else {
            result.append("<REGION>" + getCountry() + "</REGION>");
        }
        if (StringUtils.isNotBlank(uBfield1)) {
            result.append("<U_BFIELD1>" + uBfield1 + "</U_BFIELD1>");
        }
        if (StringUtils.isNotBlank(uCod10)) {
            result.append("<U_COD10>" + uCod10 + "</U_COD10>");
        }
        if (StringUtils.isNotBlank(uAfield1)) {
            result.append("<U_AFIELD1>" + uAfield1 + "</U_AFIELD1>");
        }
        if (StringUtils.isNotBlank(uAfield2)) {
            result.append("<U_AFIELD2>" + uAfield2 + "</U_AFIELD2>");
        }
        if (StringUtils.isNotBlank(cardName)) {
            result.append("<CARD_NAME>" + cardName + "</CARD_NAME>");
        }
        if (StringUtils.isNotBlank(mcName)) {
            result.append("<MC_NAME>" + mcName + "</MC_NAME>");
        }
        if (StringUtils.isNotBlank(uField8)) {
            result.append("<U_FIELD8>" + uField8 + "</U_FIELD8>");
        }
        result.append("</details></document></Changes_request>");
        return result.toString();
    }

    protected IzdCondCard getIzdCondCardMock(String cardNumber, int cardValidity) {
        IzdCondCard cond = new IzdCondCard();
        cond.setCardValidity(cardValidity);
        IzdCondCardPK izdCondCardPK = new IzdCondCardPK();
        cond.setComp_id(izdCondCardPK);
        izdCondCardPK.setBankC(BANKC);
        izdCondCardPK.setGroupc(GROUPC);
        izdCondCardPK.setCcy(CCY);
        izdCondCardPK.setCondSet("ANY");
        when(cardDAO.getIzdCondCardByCard(cardNumber)).thenReturn(cond);
        return cond;
    }

    protected IzdCard getIzdCardWithClient(String client, String type){
        IzdClientPK izdClientPK = new IzdClientPK();
        izdClientPK.setClient(client);

        IzdClient izdClient = new IzdClient();
        izdClient.setComp_id(izdClientPK);
        izdClient.setClType(type);

        IzdClAcct izdClAcct = new IzdClAcct();

        IzdAgreement izdAgreement = new IzdAgreement();
        izdAgreement.setIzdClient(izdClient);

        IzdCard izdCard = new IzdCard();
        izdCard.setIzdClAcct(izdClAcct);
        izdCard.setIzdAgreement(izdAgreement);
        return izdCard;
    }

    public abstract String getCountry();

}

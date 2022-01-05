package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dto.CardInfoDTO;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.soap.service.IssuingPort;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIWrapper;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.nordlb.cards.MockInitialContextRule;
import lv.nordlb.cards.SubRequestJUnit;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;
import lv.nordlb.cards.transmaster.bo.interfaces.ClientManager;
import lv.nordlb.cards.transmaster.bo.interfaces.IzdLockManager;
import lv.nordlb.cards.transmaster.fo.interfaces.TMFManager;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Rule;

import javax.naming.Context;
import javax.naming.NamingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class is for common methods and test data which are used in many JUnit handler test
 */
public class JUnitHandlerTestBase {

    public static final String CMS_BANK_CODE = "23";
    public static final String CMS_GROUP_CODE = "50";

    protected final Context context = mock(Context.class);
    protected final IssuingPort issuingPort = mock(IssuingPort.class);
    protected final CMSSoapAPIWrapper cmsSoapAPIWrapper = mock(CMSSoapAPIWrapper.class);
    protected final CardsDAO cardsDAO = mock(CardsDAO.class);

    @Rule
    public MockInitialContextRule mockInitialContextRule = new MockInitialContextRule(context);

    protected PcdabaNGManager pcdabaNGManager = mock(PcdabaNGManager.class);
    protected IzdLockManager izdLockManager = mock(IzdLockManager.class);
    protected TMFManager tmfManager = mock(TMFManager.class);
    protected CardManager cardManager = mock(CardManager.class);
    protected ClientManager clientManager = mock(ClientManager.class);
    protected RTPSCallAPIWrapper rtpsCallApiWraper = mock(RTPSCallAPIWrapper.class);

    @Before
    public void initSystemSettings() throws NamingException {
        when(context.lookup(PcdabaNGManager.JNDI_NAME)).thenReturn(pcdabaNGManager);
        when(context.lookup(IzdLockManager.JNDI_NAME)).thenReturn(izdLockManager);
        when(context.lookup(TMFManager.JNDI_NAME)).thenReturn(tmfManager);
        when(context.lookup(CardManager.JNDI_NAME)).thenReturn(cardManager);
        when(context.lookup(ClientManager.JNDI_NAME)).thenReturn(clientManager);
    }

    /**
     * Create Sub Request with given what attribute value and add card and cif elements to root element
     */
    protected SubRequest getRequestWithCard(String what, String card, String cif) throws RequestPreparationException, RequestFormatException {
        SubRequest request = getSubrequest(what);
        addElementOnRootElement(request, "card", card);
        addElementOnRootElement(request, "cif", cif);
        return request;
    }

    /**
     * Create sub request with given what attribute value
     */
    protected SubRequest getSubrequest(String what) throws RequestPreparationException, RequestFormatException {
        Element req = DocumentHelper.createElement("do");
        req.addAttribute("what", what);
        return new SubRequestJUnit(req);
    }

    /**
     * Create sub request with given what attribute value and other attributes
     */
    protected SubRequest getSubrequest(String what, Map<String, String> attributes) throws RequestPreparationException, RequestFormatException {
        Element req = DocumentHelper.createElement("do");
        req.addAttribute("what", what);
        for (Entry<String, String> entry : attributes.entrySet()) {
            req.addAttribute(entry.getKey(), entry.getValue());
        }
        return new SubRequestJUnit(req);
    }

    /**
     * Create sub request with given what attribute value and date format
     */
    protected SubRequest getSubrequest(String what, String dateFormat) throws RequestPreparationException, RequestFormatException {
        Element req = DocumentHelper.createElement("do");
        req.addAttribute("what", what);
        req.addAttribute("dateFormat", dateFormat);
        return new SubRequestJUnit(req);
    }

    /**
     * Add XML element to request root element with given value
     */
    protected Element addElementOnRootElement(SubRequest request, String tag, String value) {
        if (request != null && tag != null && value != null) {
            Element root = request.getReq().getRootElement();
            Element element = DocumentHelper.createElement(tag);
            element.setText(value);
            root.add(element);
            return element;
        }
        return null;
    }

    /**
     * Add XML element to given element with given value
     */
    protected Element addElement(Element rootElement, String tag, String value) {
        if (rootElement != null && tag != null && value != null) {
            Element element = DocumentHelper.createElement(tag);
            element.setText(value);
            rootElement.add(element);
            return element;
        }
        return null;
    }

    /**
     * Add XML Attribute to request root element with given value
     */
    protected void addAttributeOnRootElement(SubRequest request, String attributeName, String value) {
        if (request != null && attributeName != null && value != null) {
            Element root = request.getReq().getRootElement();
            Attribute attribute = DocumentHelper.createAttribute(root, attributeName, value);
            root.add(attribute);
        }
    }

    /**
     * Add XML Attribute to given element with given value
     */
    protected void addAttributeOnElement(Element element, String attributeName, String value) {
        if (element != null && attributeName != null && value != null) {
            Attribute attribute = DocumentHelper.createAttribute(element, attributeName, value);
            element.add(attribute);
        }
    }

    @SuppressWarnings("deprecation")
    /** Create CardInfoDTO object for tests */
    protected CardInfoDTO getCardInfo(String cardNumber, String suffix) {

        CardInfoDTO cardInfo = new CardInfoDTO();
        cardInfo.setAccountNumber("33051502" + suffix);
        cardInfo.setBillingCurrency("EUR");
        cardInfo.setPrefixDesc("VISA Dynamic" + suffix);
        cardInfo.setCard(cardNumber);
        cardInfo.setEmbossingName("SOME ONE" + suffix);
        cardInfo.setCardName("SOME ONE C" + suffix);
        cardInfo.setExpiryDate(new Date(2018, 2, 1));
        cardInfo.setExpiryDate2(new Date(2018, 3, 1));
        cardInfo.setCvc("123" + suffix);
        cardInfo.setCif("T22893" + suffix);
        cardInfo.setGroupc("50");
        cardInfo.setEndBal(0L);
        cardInfo.setClientPersonCode("321312-4321" + suffix);
        cardInfo.setPersonCode("123123-1234" + suffix);
        cardInfo.setCardStatus1("0");
        cardInfo.setDesign("477573_20140801" + suffix);
        cardInfo.setUAField2("REQUESTED" + suffix);
        cardInfo.setPinBlock("AVAILABLE" + suffix);
        cardInfo.setPassword("Pass" + suffix);
        cardInfo.setDistribMode("Bankā" + suffix);
        cardInfo.setDStreet("Skankstes 12" + suffix);
        cardInfo.setDCity("Riga" + suffix);
        cardInfo.setDCountry("LV" + suffix);
        cardInfo.setDPostInd("LV-1012" + suffix);
        cardInfo.setDBranch("32" + suffix);
        cardInfo.setUBField1("2LVRīga                                 Rīga                                    Visvalža 3a-8                                     LV-1050                                                           ");
        cardInfo.setUCod10("31" + suffix);
        PcdCard card = new PcdCard();
        card.setCondSet("N01");
        card.setBaseSupp("1");

        PcdCondAccnt accountConditions = PcdCondAccnt.builder().debIntrOverPB(new BigDecimal("2.2")).build();
        when(pcdabaNGManager.getAccountConditionsByCardNumber(eq(cardNumber))).thenReturn(accountConditions);
        when(pcdabaNGManager.getCardByCardNumber(eq(cardNumber))).thenReturn(card);
        when(pcdabaNGManager.getAnnualFee(eq("N01"), eq("EUR"), eq(false))).thenReturn(600);
        return cardInfo;
    }

    /**
     * Call handle method and expect request format exception with given error message
     */
    protected void checkRequestFormatException(SubRequestHandler handler, SubRequest request, String errorMessage)
            throws RequestProcessingException {
        boolean hadException = false;
        try {
            handler.handle(request);
        } catch (RequestFormatException e) {
            assertEquals(errorMessage, e.getMessage());
            hadException = true;
        }
        assertTrue(hadException);
    }

    /**
     * Call handle method and expect request processing exception with given error message
     */
    protected void checkRequestProcessingException(SubRequestHandler handler, SubRequest request, String errorMessage)
            throws RequestFormatException {
        boolean hadException = false;
        try {
            handler.handle(request);
        } catch (RequestProcessingException e) {
            assertEquals(errorMessage, e.getMessage());
            hadException = true;
        }
        assertTrue(hadException);
    }

    /**
     * Check if exists LinkApp properties and add new variable
     */
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

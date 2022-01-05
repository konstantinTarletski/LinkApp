package lv.bank.cards.soap.handlers;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenization;
import lv.bank.cards.core.vendor.api.sonic.rest.dto.AddressDO;
import lv.bank.cards.core.vendor.api.sonic.rest.dto.SonicCardholderDO;
import lv.bank.cards.core.vendor.api.sonic.rest.service.SonicRestService;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.dom4j.Element;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GetVtsPayloadForGoogleTest extends JUnitTestBase {

    private GetVtsPayloadForGoogle handler;
    private SonicRestService sonic = mock(SonicRestService.class);

    private static final String CARD = "4921750000001234";
    private static final String BANK_APP_DEVICE_ID = "0jw2euzsx4576w7x01mubxns";
    private static final String BANK_APP_PUSH_ID = "y7mutz1xff1osa8f83e5p3h9";
    private static final String DEVICE_TYPE = "ANDROID";
    private static final String WALLET_DEVICE_ID = "ed6abb56323ba656521ac476";
    private static final String WALLET_ACCOUNT_ID = "TMDeMYsP5CaNkOf990byXH0m";
    private static final String VISA_VTS_KEY_GOOGLE = "-392e11fe3f77d8a441e1691f935684eb7472a0d35171eb6ed92783b440f495fa68d5c28cc125ea94e159efdad9549e4cf66025633c93b691";
    private static final String VISA_VTS_SHARED_SECRET_KEY = "6f69f9af256ad2212b373e2cfc77e4292538dce05c37999bce145b79df4f8959387a09c1d1c2f8f7df8592078de921bc";

    @Before
    public void setUpTest() {
        handler = new GetVtsPayloadForGoogle();
        handler.cardsDAO = cardsDAO;
        handler.sonic = sonic;
    }

    @Test(expected = RequestProcessingException.class)
    public void handleWithoutCard() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest subrequest = getSubrequest("get-vts-payload-for-google");
        addElementOnRootElement(subrequest, "card", "");
        addElementOnRootElement(subrequest, "bank-app-device-id", BANK_APP_DEVICE_ID);
        addElementOnRootElement(subrequest, "bank-app-push-id", BANK_APP_PUSH_ID);
        addElementOnRootElement(subrequest, "device-type", DEVICE_TYPE);
        addElementOnRootElement(subrequest, "wallet-device-id", WALLET_DEVICE_ID);
        addElementOnRootElement(subrequest, "wallet-account-id", WALLET_ACCOUNT_ID);

        handler.handle(subrequest);

    }

    @Test(expected = RequestProcessingException.class)
    public void handleWithoutWalletDeviceId() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest subrequest = getSubrequest("get-vts-payload-for-google");
        addElementOnRootElement(subrequest, "card", CARD);
        addElementOnRootElement(subrequest, "bank-app-device-id", BANK_APP_DEVICE_ID);
        addElementOnRootElement(subrequest, "bank-app-push-id", BANK_APP_PUSH_ID);
        addElementOnRootElement(subrequest, "device-type", DEVICE_TYPE);
        addElementOnRootElement(subrequest, "wallet-account-id", WALLET_ACCOUNT_ID);

        handler.handle(subrequest);
    }

    @Test(expected = RequestProcessingException.class)
    public void handleWithWrongCard() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest subrequest = getSubrequest("get-vts-payload-for-google");
        addElementOnRootElement(subrequest, "card", CARD);
        addElementOnRootElement(subrequest, "bank-app-device-id", BANK_APP_DEVICE_ID);
        addElementOnRootElement(subrequest, "bank-app-push-id", BANK_APP_PUSH_ID);
        addElementOnRootElement(subrequest, "device-type", DEVICE_TYPE);
        addElementOnRootElement(subrequest, "wallet-account-id", WALLET_ACCOUNT_ID);
        when(cardsDAO.findByCardNumber(CARD)).thenReturn(null);

        handler.handle(subrequest);
    }

    @Test(expected = RequestProcessingException.class)
    public void handleWithCardHolderDoError() throws RequestPreparationException, RequestFormatException, RequestProcessingException, IOException {
        SubRequest subrequest = getSubrequest("get-vts-payload-for-google");
        addElementOnRootElement(subrequest, "card", CARD);
        addElementOnRootElement(subrequest, "bank-app-device-id", BANK_APP_DEVICE_ID);
        addElementOnRootElement(subrequest, "bank-app-push-id", BANK_APP_PUSH_ID);
        addElementOnRootElement(subrequest, "device-type", DEVICE_TYPE);
        addElementOnRootElement(subrequest, "wallet-account-id", WALLET_ACCOUNT_ID);
        when(sonic.getCardholder(anyString(), anyString())).thenReturn(getSonicCardHolderDoWithError());

        handler.handle(subrequest);
    }


    @Test
    public void handle() throws RequestPreparationException, RequestFormatException, RequestProcessingException, IOException {
        SubRequest subrequest = getSubrequest("get-vts-payload-for-google");
        addElementOnRootElement(subrequest, "card", CARD);
        addElementOnRootElement(subrequest, "bank-app-device-id", BANK_APP_DEVICE_ID);
        addElementOnRootElement(subrequest, "bank-app-push-id", BANK_APP_PUSH_ID);
        addElementOnRootElement(subrequest, "device-type", DEVICE_TYPE);
        addElementOnRootElement(subrequest, "wallet-device-id", WALLET_DEVICE_ID);
        addElementOnRootElement(subrequest, "wallet-account-id", WALLET_ACCOUNT_ID);

        when(cardsDAO.findByCardNumber(anyString())).thenReturn(getPcdCard());
        when(sonic.getCardholder(anyString(), anyString())).thenReturn(getSonicCardHolderDo());
        addLinkAppProperty("visa_vts_key_google", VISA_VTS_KEY_GOOGLE);
        addLinkAppProperty("visa_vts_shared_secret_google", VISA_VTS_SHARED_SECRET_KEY);
        handler.handle(subrequest);

        Assert.assertNotNull(handler.compileResponse().getElement().element("opaque-payment-card").getText());
        Assert.assertEquals(handler.compileResponse().getElement().element("cardholder-name").getText(), "Test cardname");
        Assert.assertEquals(handler.compileResponse().getElement().element("last-digits").getText(), "1234");
        Element address = handler.compileResponse().getElement().element("address");
        Assert.assertEquals(address.element("line1").getText(), "Line 1");
        Assert.assertEquals(address.element("line2").getText(), "Line 2");
        Assert.assertEquals(address.element("city").getText(), "Test city");
        Assert.assertEquals(address.element("area").getText(), "Test state");
        Assert.assertEquals(address.element("country").getText(), "Test country code");
        Assert.assertEquals(address.element("postal-code").getText(), "Test postal code");
        Assert.assertEquals(address.element("phone-number").getText(), "078233112");
        verify(cardsDAO, times(1)).getPcdCardPendingTokenizationByKey(CARD, WALLET_DEVICE_ID);
        verify(cardsDAO, times(1)).saveOrUpdate(any(PcdCardPendingTokenization.class));
    }

    private SonicCardholderDO getSonicCardHolderDo() {
        SonicCardholderDO sonicCardholderDO = new SonicCardholderDO();
        AddressDO addressDO = new AddressDO();
        addressDO.setCity("Test city");
        addressDO.setCountryCode("Test country code");
        addressDO.setLine1("Line 1");
        addressDO.setLine2("Line 2");
        addressDO.setLine3("Line 3");
        addressDO.setState("Test state");
        addressDO.setPostalCode("Test postal code");
        sonicCardholderDO.setAddress(addressDO);
        sonicCardholderDO.setPhoneNumber("078233112");
        return sonicCardholderDO;
    }

    private SonicCardholderDO getSonicCardHolderDoWithError() {
        SonicCardholderDO sonicCardholderDO = new SonicCardholderDO();
        sonicCardholderDO.setErrorCode("Sonic error");
        return sonicCardholderDO;
    }

    private PcdCard getPcdCard() {
        PcdCard pcdCard = new PcdCard();
        pcdCard.setCard(CARD);
        pcdCard.setRegion("USA");
        pcdCard.setCvc21("121");
        pcdCard.setCardName("Test cardname");
        pcdCard.setExpiry1(new GregorianCalendar(2025, Calendar.MAY, 5).getGregorianChange());
        pcdCard.setExpiry2(new GregorianCalendar(2025, Calendar.NOVEMBER, 10).getGregorianChange());
        return pcdCard;
    }
}
package lv.bank.cards.soap.handlers.lv;

import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.service.WalletTokenService;
import lv.bank.cards.soap.service.dto.TokenWalletDo;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetTokenHandlerTest extends JUnitTestBase {

    public static final String DEFAULT_CARD_NUMBER = "4775730000000001";

    protected GetTokenHandler handler;
    protected WalletTokenService walletTokenService = mock(WalletTokenService.class);

    @Before
    public void setUpTest() throws RequestPreparationException, NamingException {
        handler = new GetTokenHandler();
        handler.walletTokenService = walletTokenService;
    }

    @Test
    public void handleSuccessDeviceId() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest request = getSubrequest("get-token");
        addElementOnRootElement(request, "card", DEFAULT_CARD_NUMBER);
        addElementOnRootElement(request, "device-id", "device-id");

        TokenWalletDo token = new TokenWalletDo();
        token.setTokenEligible(true);
        token.setTokenRefId("tokenRefId");
        token.setTokenStatus("tokenStatus");
        token.setId("id");

        when(walletTokenService.getWalletToken(DEFAULT_CARD_NUMBER, "device-id", null)).thenReturn(token);

        handler.handle(request);

        assertEquals("true", handler.compileResponse().getElement().element("token").element("eligible").getText());
        assertEquals("tokenRefId", handler.compileResponse().getElement().element("token").element("ref-id").getText());
        assertEquals("tokenStatus", handler.compileResponse().getElement().element("token").element("status").getText());
    }

    @Test
    public void handleSuccessWalletId() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest request = getSubrequest("get-token");
        addElementOnRootElement(request, "card", DEFAULT_CARD_NUMBER);
        addElementOnRootElement(request, "wallet-id", "wallet-id");

        TokenWalletDo token = new TokenWalletDo();
        token.setTokenEligible(true);
        token.setTokenRefId("tokenRefId");
        token.setTokenStatus("tokenStatus");
        token.setId("id");

        when(walletTokenService.getWalletToken(DEFAULT_CARD_NUMBER, null, "wallet-id")).thenReturn(token);

        handler.handle(request);

        assertEquals("true", handler.compileResponse().getElement().element("token").element("eligible").getText());
        assertEquals("tokenRefId", handler.compileResponse().getElement().element("token").element("ref-id").getText());
        assertEquals("tokenStatus", handler.compileResponse().getElement().element("token").element("status").getText());
    }

    @Test
    public void handleEitherDeviceIdOrWalletIdError() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest request = getSubrequest("get-token");
        addElementOnRootElement(request, "card", DEFAULT_CARD_NUMBER);
        addElementOnRootElement(request, "device-id", "device-id");
        addElementOnRootElement(request, "wallet-id", "wallet-id");

        checkRequestFormatException(handler, request, "Either deviceId (iOS) or walletId (Android) should be provided in request, not both.");
    }

    @Test
    public void handleEmptyCardError() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest request = getSubrequest("get-token");
        addElementOnRootElement(request, "device-id", "device-id");
        addElementOnRootElement(request, "wallet-id", "wallet-id");

        checkRequestFormatException(handler, request, "Missing card number.");
    }

    @Test
    public void handleMissingDeviceIdOrWalletIdError() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest request = getSubrequest("get-token");
        addElementOnRootElement(request, "card", DEFAULT_CARD_NUMBER);

        checkRequestFormatException(handler, request, "Missing deviceId or walletId.");
    }

}

package lv.bank.cards.soap.handlers;

import com.nimbusds.jose.shaded.json.JSONObject;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenization;
import lv.bank.cards.soap.JUnitTestBase;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.security.ec.ECPrivateKeyImpl;
import sun.security.ec.ECPublicKeyImpl;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GetVtsPayloadForAppleTest extends JUnitTestBase {

    public static final String BASE_64_CERT = "-----BEGIN CERTIFICATE-----\n" +
            "MIIEEjCCA7igAwIBAgIIEccnFAKsD+UwCgYIKoZIzj0EAwIwgYExOzA5BgNVBAMMMlRlc3QgQXBwb\n" +
            "GUgV29ybGR3aWRlIERldmVsb3BlcnMgUmVsYXRpb25zIENBIC0gRUNDMSAwHgYDVQQLDBdDZXJ0aW\n" +
            "ZpY2F0aW9uIEF1dGhvcml0eTETMBEGA1UECgwKQXBwbGUgSW5jLjELMAkGA1UEBhMCVVMwHhcNMTk\n" +
            "wODA3MjA1NzA5WhcNMjEwNzEzMDI1ODAwWjBtMTYwNAYDVQQDDC1lY2MtY3J5cHRvLXNlcnZpY2Vz\n" +
            "LWVuY2lwaGVybWVudF9VQzYtSW5NZW1vcnkxETAPBgNVBAsMCEFwcGxlUGF5MRMwEQYDVQQKDApBc\n" +
            "HBsZSBJbmMuMQswCQYDVQQGEwJVUzBZMBMGByqGSM49AgEGCCqGSM49AwEHA0IABC4+XM9rmrBL56\n" +
            "IvP6zP3nPIfocVU5SjSBVAiolsoYo3TaxmmvO/\n" +
            "YiD8hjdn9K9HUHxbwiH8ShmHTa85tAdOPrijggIrMIICJzAMBgNVHRMBAf8EAjAAMB8GA1UdIwQYM\n" +
            "BaAFNbW1Vrl//3CfDTDQ969aHZcNqm+ME8GCCsGAQUFBwEBBEMwQTA/\n" +
            "BggrBgEFBQcwAYYzaHR0cDovL29jc3AtdWF0LmNvcnAuYXBwbGUuY29tL29jc3AwNC10ZXN0d3dkc\n" +
            "mNhZWNjMIIBHQYDVR0gBIIBFDCCARAwggEMBgkqhkiG92NkBQEwgf4wgcMGCCsGAQUFBwICMIG2DI\n" +
            "GzUmVsaWFuY2Ugb24gdGhpcyBjZXJ0aWZpY2F0ZSBieSBhbnkgcGFydHkgYXNzdW1lcyBhY2NlcHR\n" +
            "hbmNlIG9mIHRoZSB0aGVuIGFwcGxpY2FibGUgc3RhbmRhcmQgdGVybXMgYW5kIGNvbmRpdGlvbnMg\n" +
            "b2YgdXNlLCBjZXJ0aWZpY2F0ZSBwb2xpY3kgYW5kIGNlcnRpZmljYXRpb24gcHJhY3RpY2Ugc3Rhd\n" +
            "GVtZW50cy4wNgYIKwYBBQUHAgEWKmh0dHA6Ly93d3cuYXBwbGUuY29tL2NlcnRpZmljYXRlYXV0aG\n" +
            "9yaXR5LzBBBgNVHR8EOjA4MDagNKAyhjBodHRwOi8vY3JsLXVhdC5jb3JwLmFwcGxlLmNvbS9hcHB\n" +
            "sZXd3ZHJjYWVjYy5jcmwwHQYDVR0OBBYEFK0uo8t+NMLt7kNoTicRH8xJMznQMA4GA1UdDwEB/\n" +
            "wQEAwIDKDASBgkqhkiG92NkBicBAf8EAgUAMAoGCCqGSM49BAMCA0gAMEUCIQCKEXnIsY2PZqMF2x\n" +
            "HKehKgp/ZywZ/9/TZ+AnpOA6mI/AIgTI94NSaIn7DLd47QTK760WILDOr0EdOHiExJMZwYp7c=\n" +
            "-----END CERTIFICATE-----";

    private static final String PRIVATE_KEY_HEX = "3041020100301306072A8648CE3D020106082A8648CE3D03010704273025020101042042F083488FF1481B052C94E462FCDF1259C336DCB65090466C9DD27BBD829FB4";
    private static final String PUBLIC_KEY_HEX = "3059301306072A8648CE3D020106082A8648CE3D03010703420004E0E5905B80045623DEE88CCF1A35E70AFAF0CA92F039D64A8AF20979C331157273FB782F02E6967E0D9B7383DCD613F01C312CD2EBBBF577D91D272D5EBF94F6";
    public static final String SHARED_SECRET = "AE6FDAFF440C21CF67B6BA5B424EC9F523513BD7AE9D470236C63B5780530FC3";
    public static final String KDF_INPUT = "00000001AE6FDAFF440C21CF67B6BA5B424EC9F523513BD7AE9D470236C63B5780530FC30D69642D6165733235362D47434D4170706C6504E0E5905B80045623DEE88CCF1A35E70AFAF0CA92F039D64A8AF20979C331157273FB782F02E6967E0D9B7383DCD613F01C312CD2EBBBF577D91D272D5EBF94F6";
    public static final String CARD = "4921750000001234";
    public static final String BANK_APP_DEVICE_ID = "0jw2euzsx4576w7x01mubxns";
    private static final String BANK_APP_PUSH_ID = "y7mutz1xff1osa8f83e5p3h9";
    private static final String DEVICE_TYPE = "APPLE";
    private static final String WALLET_DEVICE_ID = "ed6abb56323ba656521ac476";
    private static final String VISA_VTS_KEY_APPLE = "-322471515d6e8846ec9e1bc0c681e350322471515d6e8846ec9e1bc0c681e35207a6df87216de44";
    private static final String NONCE = "nonce";
    private static final String VISA_VTS_REQUESTOR_ID_APPLE = "40010030273";
    private GetVtsPayloadForApple subRequestHandler;

    @Before
    public void setUp() throws Exception {
        this.subRequestHandler = new GetVtsPayloadForApple();
        subRequestHandler.cardsDAO = cardsDAO;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testHandle() {
    }

    @Test
    public void handle() throws RequestFormatException, RequestPreparationException, RequestProcessingException, CertificateException, InvalidKeyException, NoSuchAlgorithmException {
        SubRequest subRequest = getSubrequest("get-vts-for-apple");
        addElementOnRootElement(subRequest, "card", CARD);
        addElementOnRootElement(subRequest, "bank-app-device-id", BANK_APP_DEVICE_ID);
        addElementOnRootElement(subRequest, "bank-app-push-id", BANK_APP_PUSH_ID);
        addElementOnRootElement(subRequest, "device-type", DEVICE_TYPE);
        addElementOnRootElement(subRequest, "wallet-device-id", WALLET_DEVICE_ID);
        addElementOnRootElement(subRequest, "nonce", NONCE);
        addElementOnRootElement(subRequest, "certificates", BASE_64_CERT);

        when(cardsDAO.findByCardNumber(anyString())).thenReturn(getPcdCard());
        addLinkAppProperty("visa_vts_key_apple", VISA_VTS_KEY_APPLE);
        addLinkAppProperty("visa_vts_requestor_id_apple", VISA_VTS_REQUESTOR_ID_APPLE);
        subRequestHandler.handle(subRequest);

        verify(cardsDAO, times(1)).getPcdCardPendingTokenizationByKey(CARD, WALLET_DEVICE_ID);
        verify(cardsDAO, times(1)).saveOrUpdate(any(PcdCardPendingTokenization.class));
        Assert.assertNotNull(subRequestHandler.compileResponse().getElement().element("ephemeral-public-key").getText());
        Assert.assertNotNull(subRequestHandler.compileResponse().getElement().element("encrypted-pass-data").getText());
        Assert.assertNotNull(subRequestHandler.compileResponse().getElement().element("activation-data").getText());
    }

    @Test(expected = RequestProcessingException.class)
    public void handleWithoutCard() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest subrequest = getSubrequest("get-vts-payload-for-apple");
        addElementOnRootElement(subrequest, "card", "");
        addElementOnRootElement(subrequest, "bank-app-device-id", BANK_APP_DEVICE_ID);
        addElementOnRootElement(subrequest, "bank-app-push-id", BANK_APP_PUSH_ID);
        addElementOnRootElement(subrequest, "device-type", DEVICE_TYPE);
        addElementOnRootElement(subrequest, "wallet-device-id", WALLET_DEVICE_ID);

        subRequestHandler.handle(subrequest);

    }

    @Test(expected = RequestFormatException.class)
    public void handleWithoutNonce() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest subrequest = getSubrequest("get-vts-payload-for-apple");
        addElementOnRootElement(subrequest, "card", CARD);
        addElementOnRootElement(subrequest, "bank-app-device-id", BANK_APP_DEVICE_ID);
        addElementOnRootElement(subrequest, "bank-app-push-id", BANK_APP_PUSH_ID);
        addElementOnRootElement(subrequest, "device-type", DEVICE_TYPE);
        addElementOnRootElement(subrequest, "wallet-device-id", WALLET_DEVICE_ID);
        addElementOnRootElement(subrequest, "nonce", "");

        subRequestHandler.handle(subrequest);

    }

    @Test(expected = RequestProcessingException.class)
    public void handleWithoutPcdCard() throws RequestPreparationException, RequestFormatException, RequestProcessingException {
        SubRequest subrequest = getSubrequest("get-vts-payload-for-apple");
        addElementOnRootElement(subrequest, "card", CARD);
        addElementOnRootElement(subrequest, "bank-app-device-id", BANK_APP_DEVICE_ID);
        addElementOnRootElement(subrequest, "bank-app-push-id", BANK_APP_PUSH_ID);
        addElementOnRootElement(subrequest, "device-type", DEVICE_TYPE);
        addElementOnRootElement(subrequest, "wallet-device-id", WALLET_DEVICE_ID);
        addElementOnRootElement(subrequest, "nonce", NONCE);
        when(cardsDAO.findByCardNumber(anyString())).thenReturn(null);

        subRequestHandler.handle(subrequest);

    }

    @Test
    public void testGenerateEphemeralKeyPair() throws RequestProcessingException, NoSuchAlgorithmException, InvalidKeyException {
        KeyPair keyPair = this.subRequestHandler.generateEphemeralKeyPair();

        ECPrivateKeyImpl aPrivate = (ECPrivateKeyImpl) keyPair.getPrivate();
        assertEquals("EC", aPrivate.getAlgorithm());
        assertEquals("PKCS#8", aPrivate.getFormat());

        ECPublicKeyImpl aPublic = (ECPublicKeyImpl) keyPair.getPublic();

        assertEquals("EC", aPublic.getAlgorithm());
        assertEquals("X.509", aPublic.getFormat());

        System.out.println(DatatypeConverter.printHexBinary(aPublic.getEncodedPublicValue()));
        System.out.println(DatatypeConverter.printHexBinary(aPublic.getEncodedInternal()));
        System.out.println(DatatypeConverter.printHexBinary(aPublic.getEncoded()));
    }

    @Test
    public void getKeyAgreementTest() throws NoSuchAlgorithmException, CertificateException, InvalidKeyException, IOException {


        ECPrivateKeyImpl ecPrivateKey = new ECPrivateKeyImpl(DatatypeConverter.parseHexBinary(PRIVATE_KEY_HEX));
        ECPublicKeyImpl ecPublicKey = new ECPublicKeyImpl(DatatypeConverter.parseHexBinary(PUBLIC_KEY_HEX));

        X509Certificate x509Certificate = this.subRequestHandler.parseApplePublicCertificate(BASE_64_CERT);
        ECPublicKeyImpl applePublicKey = (ECPublicKeyImpl) x509Certificate.getPublicKey();

        KeyAgreement keyAgreement = this.subRequestHandler.getKeyAgreement(ecPrivateKey, applePublicKey);

        assertEquals(32, keyAgreement.generateSecret().length);
        assertEquals(SHARED_SECRET, DatatypeConverter.printHexBinary(keyAgreement.generateSecret()));

    }


    @Test
    public void parseApplePublicCertificateTest() throws CertificateException {

        X509Certificate x509Certificate = this.subRequestHandler.parseApplePublicCertificate(BASE_64_CERT);
        PublicKey applePublicKey = x509Certificate.getPublicKey();

        assertEquals("EC", applePublicKey.getAlgorithm());
        assertEquals("X.509", applePublicKey.getFormat());
        assertTrue(applePublicKey.toString().contains("parameters: secp256r1 [NIST P-256, X9.62 prime256v1]"));
        assertTrue(DatatypeConverter.printHexBinary(applePublicKey.getEncoded()).contains("042E3E5CCF6B9AB04BE7A22F3FACCFDE73C87E87155394A34815408A896CA18A374DAC669AF3BF6220FC863767F4AF47507C5BC221FC4A19874DAF39B4074E3EB8"));

    }

    @Test
    public void getKDFInputTest() throws InvalidKeyException, CertificateException, NoSuchAlgorithmException {
        ECPrivateKeyImpl ecPrivateKey = new ECPrivateKeyImpl(DatatypeConverter.parseHexBinary(PRIVATE_KEY_HEX));
        ECPublicKeyImpl ecPublicKey = new ECPublicKeyImpl(DatatypeConverter.parseHexBinary(PUBLIC_KEY_HEX));

        X509Certificate x509Certificate = this.subRequestHandler.parseApplePublicCertificate(BASE_64_CERT);
        ECPublicKeyImpl applePublicKey = (ECPublicKeyImpl) x509Certificate.getPublicKey();

        KeyAgreement keyAgreement = this.subRequestHandler.getKeyAgreement(ecPrivateKey, applePublicKey);

        byte[] kdfInput = this.subRequestHandler.getKDFInput(keyAgreement, ecPublicKey);

        assertEquals(120, kdfInput.length);
        assertEquals(KDF_INPUT, DatatypeConverter.printHexBinary(kdfInput));

    }

    @Test
    public void getAESKeyTest() throws NoSuchAlgorithmException {
        byte[] aesKey = this.subRequestHandler.getAESKey(KDF_INPUT.getBytes(StandardCharsets.UTF_8));
        assertEquals(32, aesKey.length);
        assertEquals("CCE67E89C1E65E6DEA96CF9940F77C48157B0E001F2AB44482ABED1270DEDECC", DatatypeConverter.printHexBinary(aesKey));

    }

    @Test
    public void getAesGcmEncryptionTest() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        byte[] aesKey = this.subRequestHandler.getAESKey(KDF_INPUT.getBytes(StandardCharsets.UTF_8));
        Map<String, Object> payload = new HashMap<>();
        payload.put("primaryAccountNumber", "pcdCard.getCard()"); //number
        payload.put("expiration", new SimpleDateFormat("MM/yy").format(new Date(1634121896419L))); //string in format "11/18"
        payload.put("name", "pcdCard.getCardName()"); //String
        payload.put("nonce", "nonce"); //String
        payload.put("nonceSignature", "nonceSignature"); //String

        String jsonString = JSONObject.toJSONString(payload);

        byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
        byte[] aesGcmEncryption = this.subRequestHandler.getAesGcmEncryption(bytes, aesKey);
        assertEquals(bytes.length + 16, aesGcmEncryption.length);
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
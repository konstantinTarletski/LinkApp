package lv.bank.cards.soap.handlers;

import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenization;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenizationPK;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import org.apache.commons.lang.StringUtils;
import sun.security.ec.ECPrivateKeyImpl;
import sun.security.ec.ECPublicKeyImpl;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GetVtsPayloadForApple extends SubRequestHandler {
    public static final String DO_CARD = "/do/card";
    public static final String DO_BANK_APP_DEVICE_ID = "/do/bank-app-device-id";
    public static final String DO_BANK_APP_PUSH_ID = "/do/bank-app-push-id";
    public static final String DO_DEVICE_TYPE = "/do/device-type";
    public static final String DO_CERTIFICATES = "/do/certificates";
    public static final String DO_NONCE = "/do/nonce";
    public static final String DO_NONCE_SIGNATURE = "/do/nonce-signature";
    public static final String DO_WALLET_DEVICE_ID = "/do/wallet-device-id";
    public static final String ERROR_SPECIFY_NONCE = "Specify nonce";
    public static final int GCM_IV_LENGTH = 12;
    public static final String TYPE = "MBPAC";
    public static final int VERSION = 1;
    public static final String KEY_SCHEME = "FK";
    public static final String ALGORITHM = "TDEA";
    protected CardsDAO cardsDAO;

    public GetVtsPayloadForApple() {
        super();
        cardsDAO = new CardsDAOHibernate();
    }

    @Override
    public void handle(SubRequest request) throws RequestFormatException, RequestProcessingException {
        super.handle(request);
        log.info("handle");

        final String card = getStringFromNode(DO_CARD);
        final String bankAppDeviceId = getStringFromNode(DO_BANK_APP_DEVICE_ID);
        final String bankAppPushId = getStringFromNode(DO_BANK_APP_PUSH_ID);
        final String deviceType = getStringFromNode(DO_DEVICE_TYPE); //android / ios
        final String certificates = getStringFromNode(DO_CERTIFICATES);
        final String nonce = getStringFromNode(DO_NONCE);
        final String nonceSignature = getStringFromNode(DO_NONCE_SIGNATURE);
        final String walletDeviceId = getStringFromNode(DO_WALLET_DEVICE_ID);

        if (StringUtils.isBlank(card)) {
            throw new RequestProcessingException(ErrorConstants.invalidCardNumber, this);
        }

        if (StringUtils.isBlank(nonce)) {
            throw new RequestFormatException(ERROR_SPECIFY_NONCE, this);
        }

        PcdCard pcdCard = cardsDAO.findByCardNumber(card);
        if (pcdCard == null) {
            throw new RequestProcessingException("Card not find", this);
        }


        Map<String, Object> payload = new HashMap<>();
        payload.put("primaryAccountNumber" , pcdCard.getCard()); //number
        payload.put("expiration" , new SimpleDateFormat("MM/yy").format(pcdCard.getExpiry1())); //string in format "11/18"
        payload.put("name" , pcdCard.getCardName()); //String
        payload.put("nonce" , nonce); //String
        payload.put("nonceSignature" , nonceSignature); //String


        byte[] encryptedPayloadBytes = null;
        ECPublicKeyImpl ephemeralPublicKey;
        String activationData;
        byte[] activationDataBytes;
        try {
            KeyPair ephemeralKeyPair = generateEphemeralKeyPair();
            ephemeralPublicKey = (ECPublicKeyImpl) ephemeralKeyPair.getPublic();
            ECPrivateKeyImpl ephemeralPrivateKey = (ECPrivateKeyImpl) ephemeralKeyPair.getPrivate();

            X509Certificate applePublicCertificate = parseApplePublicCertificate(certificates);
            ECPublicKeyImpl applePublicKey = getApplePublicKey(applePublicCertificate);

            encryptedPayloadBytes = encryptPayload(JSONObject.toJSONString(payload), ephemeralPrivateKey, applePublicKey);

            String encryptedInformation = LinkAppProperties.getVisaVtsKeyApple();
            String keySetIdentifier = LinkAppProperties.getVisaVtsRequestorIdApple();

            activationData = String.format("%s-%s-%s-%s.1--%s-%s", TYPE, VERSION, KEY_SCHEME, keySetIdentifier, ALGORITHM, encryptedInformation);
            activationDataBytes = activationData.getBytes();

        } catch (CertificateException | NoSuchAlgorithmException e) {
            throw new RequestProcessingException ("Error while parsing certificate", e);
        }

        String encryptedPayloadBase64 = Base64.getMimeEncoder().encodeToString(encryptedPayloadBytes);
        String ephemeralPublicKeyBase64 = Base64.getMimeEncoder().encodeToString(ephemeralPublicKey.getEncodedPublicValue());
        String encryptedActivationData = Base64.getMimeEncoder().encodeToString(activationDataBytes);

        createElement("ephemeral-public-key", ephemeralPublicKeyBase64);
        createElement("encrypted-pass-data", encryptedPayloadBase64);
        createElement("activation-data", encryptedActivationData);

        PcdCardPendingTokenization cardPendingTokenization = cardsDAO.getPcdCardPendingTokenizationByKey(card, walletDeviceId);
        if (cardPendingTokenization != null) {
            log.info("handle, pcd_cards_pending_tokenization already exists = {}", cardPendingTokenization.getComp_id());
        } else {
            PcdCardPendingTokenizationPK pk = new PcdCardPendingTokenizationPK();
            pk.setWalletDeviceId(walletDeviceId);
            pk.setCard(card);

            cardPendingTokenization = new PcdCardPendingTokenization();
            cardPendingTokenization.setComp_id(pk);
        }

        cardPendingTokenization.setBankAppDeviceId(bankAppDeviceId);
        cardPendingTokenization.setBankAppPushId(bankAppPushId);
        cardPendingTokenization.setDeviceType(deviceType);
        cardPendingTokenization.setSource("mib");
        cardPendingTokenization.setRecDate(new Date());

        cardsDAO.saveOrUpdate(cardPendingTokenization);
    }

    protected byte [] encryptPayload (String payload, ECPrivateKeyImpl ephemeralPrivateKey, ECPublicKeyImpl applePublicKey) throws RequestProcessingException {
        byte [] encryptedPayload;
        KeyAgreement keyAgreement = null;
        try {
            keyAgreement = getKeyAgreement(ephemeralPrivateKey, applePublicKey);
            byte[] kdfInput = getKDFInput(keyAgreement, applePublicKey);
            byte[] aesKey = getAESKey(kdfInput);
            encryptedPayload = getAesGcmEncryption(payload.getBytes(StandardCharsets.UTF_8), aesKey);
        } catch (Exception e) {
            throw new RequestProcessingException ("Error while processing request", e);
        }

        return encryptedPayload;
    }

    protected X509Certificate parseApplePublicCertificate (String base64ApplePubCert) throws CertificateException {
        base64ApplePubCert = base64ApplePubCert
                .replace("-----BEGIN CERTIFICATE-----\n", "")
                .replace("-----END CERTIFICATE-----", "");
        byte[] decoded = Base64.getMimeDecoder().decode(base64ApplePubCert);
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate x509Certificate = null;
        try (InputStream in = new ByteArrayInputStream(decoded)) {
            x509Certificate = (X509Certificate) certFactory.generateCertificate(in);
        } catch (IOException e) {
            throw new CertificateException ();
        }
        return x509Certificate;
    }

    protected ECPublicKeyImpl getApplePublicKey (X509Certificate appleCertificate) {
        return (ECPublicKeyImpl)appleCertificate.getPublicKey();
    }

    protected KeyPair generateEphemeralKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(256);
        return keyPairGenerator.generateKeyPair();
    }

    protected KeyAgreement getKeyAgreement (PrivateKey privateKey, PublicKey publicKey) throws InvalidKeyException, NoSuchAlgorithmException {
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true);
        return keyAgreement;
    }

    protected byte [] getKDFInput (KeyAgreement keyAgreement, ECPublicKeyImpl generatedPublicKey) {
        byte[] sharedSecret = keyAgreement.generateSecret();
        byte[] ephemeralPublicKey = generatedPublicKey.getEncodedPublicValue();
        String counterHex = "00000001";
        String sharedSecretHex = DatatypeConverter.printHexBinary(sharedSecret);
        String algorithmIDLengthHex = "0D";
        String algorithmIDHex = "69642D6165733235362D47434D"; //"id-aes256-GCM"
        String partyUInfoHex = "4170706C65"; //"Apple"
        String ephemeralPublicKeyHex = DatatypeConverter.printHexBinary(ephemeralPublicKey);

        return DatatypeConverter.parseHexBinary(counterHex
                + sharedSecretHex
                + algorithmIDLengthHex
                + algorithmIDHex
                + partyUInfoHex
                + ephemeralPublicKeyHex);
    }

    protected byte [] getAESKey (byte [] kdfInput) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(kdfInput);
    }

    protected byte [] getAesGcmEncryption (byte[] payload, byte [] aesKey) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        byte[] IV = new byte[GCM_IV_LENGTH];
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, IV);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
        return cipher.doFinal(payload);
    }


}

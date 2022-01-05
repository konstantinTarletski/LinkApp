package lv.bank.cards.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static java.nio.charset.Charset.defaultCharset;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LinkAppProperties {

    public static Properties PROPERTIES;

    public static final String AUTHENTIFICATION_CODE_KEY = "authentification_code_key";
    public static final String ATM_ADD_TO_EMAIL_KEY = "atm_ad_to_email";
    public static final String ATM_ADD_FROM_EMAIL_KEY = "atm_ad_from_email";
    public static final String ATM_ADD_REPLAY_EMAIL_KEY = "sonic_host";
    public static final String IP_WHITE_LIST = "ip_address_white_list";
    public static final String IP_BLACK_LIST = "ip_address_black_list";
    public static final String CREDIT_CARD_BINS = "credit_card_bins";
    public static final String BUSINESS_CARD_BINS = "business_card_bins";

    public static final String CMS_SOAP_WSDL_URL = "cms_soap_wsdl_url";
    public static final String CMS_SOAP_USERNAME = "cms_soap_username";
    public static final String CMS_SOAP_PASSWORD = "cms_soap_password";

    public static final String CMS_BANK_CODE = "cms_bank_code";
    public static final String CMS_GROUP_CODE = "cms_group_code";

    public static final String SONIC_HOST_KEY = "sonic_host";
    public static final String SONIC_REST_PORT_KEY = "sonic_port_rest";
    public static final String SONIC_PORT_SOAP_KEY = "sonic_port_soap";
    public static final String SONIC_TIMEOUT_KEY = "sonic_timeout";

    public static final String LINK_APP_HOST_KEY = "link_app_host";
    public static final String BANK_CARDS_WS_WRAPPER_PORT_WSDL_KEY = "bank_cards_ws_wrapper_port_wsdl";

    public static final String STATISTICS_GROUP_PREFIX = "statistics_group_";

    public static final String VISA_VTS_KEY_GOOGLE = "visa_vts_key_google";
    public static final String VISA_VTS_SHARED_SECRET_GOOGLE = "visa_vts_shared_secret_google";
    public static final String VISA_VTS_KEY_APPLE = "visa_vts_key_apple";

    public static final String VISA_TOKEN_SERVICE_BINS_KEY = "visa_token_service_bins";

    public static final String VISA_VTS_REQUESTOR_ID_APPLE_KEY = "visa_vts_requestor_id_apple";
    public static final String VISA_VTS_REQUESTOR_ID_GOOGLE_KEY = "visa_vts_requestor_id_google";

    public static final String PUSH_NOTIFICATION_TOKEN_PROVISIONING_TEMPLATE_ID_KEY = "push_notification_token_provisioning_template_id";
    public static final String PUSH_NOTIFICATION_TOKEN_PROVISIONING_PROJECT_ID_KEY = "push_notification_token_provisioning_project_id";

    public static final String HANDOFF_LOCATION_KEY = "handoff_location";
    public static final String HANDOFF_LOCATION_TOKENS_KEY = "handoff_location_tokens";

    public static final AtomicBoolean REREAD = new AtomicBoolean(false);
    public static final AtomicBoolean REREAD_GROUPS = new AtomicBoolean(false);

    /**
     * Value representing date when Link MAster transformation file was initialized, set to null to initialize again
     */
    public static final AtomicReference<Calendar> TRANSFORMER_INIT_DATE = new AtomicReference<>(null);

    /**
     * Value representing date when Crypting 3DES key was initialized, set to null to initialize again
     */
    public static final AtomicReference<Calendar> CRYPTING_KEY_INIT_DATE = new AtomicReference<>(null);

    static {
        PROPERTIES = readPropertiesFile();
    }

    private static Properties readPropertiesFile() {
        Properties prop = new Properties();
        String serverPath = System.getProperty("jboss.server.config.dir");
        if (serverPath != null) {
            String confPath = serverPath + File.separator + "linkApp.properties";
            File file = new File(confPath);
            if (file.exists()) {
                log.info("Reading linkApp.properties file from {}", confPath);
                try {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        prop.load(fis);
                    }
                    REREAD.set(false);
                    REREAD_GROUPS.set(true);
                } catch (IOException e) {
                    log.error("Could not read linkApp.properties file in configuration folder");
                }
            } else {
                log.error("Did not find linkApp.properties file in configuration folder = {}", confPath);
            }
        } else {
            log.info("Skipping reading linkApp.properties because 'jboss.server.config.dir' variable is null");
        }
        return prop;
    }

    public static String getLinkAppProperties(String key) {
        if (REREAD.get())
            PROPERTIES = readPropertiesFile();
        return StringUtils.trimToNull(PROPERTIES.getProperty(key));
    }

    public static String getAuthentificationCode() {
        return getLinkAppProperties(AUTHENTIFICATION_CODE_KEY);
    }

    public static String getSonicHost() {
        return getLinkAppProperties(SONIC_HOST_KEY);
    }

    public static String getSonicPortSoap() {
        return getLinkAppProperties(SONIC_PORT_SOAP_KEY);
    }

    public static String getSonicRestPort() {
        return getLinkAppProperties(SONIC_REST_PORT_KEY);
    }

    public static String getSonicTimeout() {
        return getLinkAppProperties(SONIC_TIMEOUT_KEY);
    }

    public static String getAtmAdToEmail() {
        return getLinkAppProperties(ATM_ADD_TO_EMAIL_KEY);
    }

    public static String getAtmAdFromEmail() {
        return getLinkAppProperties(ATM_ADD_FROM_EMAIL_KEY);
    }

    public static String getAtmAdReplayEmail() {
        return getLinkAppProperties(ATM_ADD_REPLAY_EMAIL_KEY);
    }

    /**
     * Get list of IP address which are acceptable
     */
    public static String getIPAddressWhiteList() {
        return getLinkAppProperties(IP_WHITE_LIST);
    }

    public static List<String> getCreditCardBins() {
        String list = getLinkAppProperties(CREDIT_CARD_BINS);
        return StringUtils.isEmpty(list) ? new ArrayList<>() : Arrays.asList(list.split(","));
    }

    public static List<String> getBusinessCardBins() {
        String list = getLinkAppProperties(BUSINESS_CARD_BINS);
        return StringUtils.isEmpty(list) ? new ArrayList<>() : Arrays.asList(list.split(","));
    }

    /**
     * Get list of IP address which are denied
     */
    public static String getIPAddressBlackList() {
        return getLinkAppProperties(IP_BLACK_LIST);
    }

    public static String getCmsSoapUsername() {
        return getLinkAppProperties(CMS_SOAP_USERNAME);
    }

    public static String getCmsBankCode() {
        return getLinkAppProperties(CMS_BANK_CODE);
    }

    public static String getCmsGroupCode() {
        return getLinkAppProperties(CMS_GROUP_CODE);
    }

    public static String getCmsSoapWsdlUrl() {
        return getLinkAppProperties(CMS_SOAP_WSDL_URL);
    }

    public static String getLinkAppHost() {
        return getLinkAppProperties(LINK_APP_HOST_KEY);
    }

    public static String getBankCardsWsWrapperPortWsdl() {
        return getLinkAppProperties(BANK_CARDS_WS_WRAPPER_PORT_WSDL_KEY);
    }


    public static String getCmsSoapPassword() {
        try {
            return decodePassword(getLinkAppProperties(CMS_SOAP_PASSWORD));
        } catch (Exception e) {
            log.error("Error while decoding cms_soap_password from linkApp.properties file in configuration folder");
        }
        return null;
    }

    public static String getVisaVtsKeyGoogle() {
        try {
            return decodePassword(getLinkAppProperties(VISA_VTS_KEY_GOOGLE));
        } catch (Exception e) {
            log.error("Error while decoding visa_key from linkApp.properties file in configuration folder");
        }
        return null;
    }

    public static String getVisaVtsSharedSecretGoogle() {
        try {
            return decodePassword(getLinkAppProperties(VISA_VTS_SHARED_SECRET_GOOGLE));
        } catch (Exception e) {
            log.error("Error while decoding visa_shared_secret from linkApp.properties file in configuration folder");
        }
        return null;
    }

    public static String getVisaVtsKeyApple() {
        try {
            return decodePassword(getLinkAppProperties(VISA_VTS_KEY_APPLE));
        } catch (Exception e) {
            log.error("Error while decoding visa_shared_secret from linkApp.properties file in configuration folder");
        }
        return null;
    }

    public static String getVisaTokenServiceBins() {
        return getLinkAppProperties(VISA_TOKEN_SERVICE_BINS_KEY);
    }

    public static String getVisaVtsRequestorIdApple() {
        return getLinkAppProperties(VISA_VTS_REQUESTOR_ID_APPLE_KEY);
    }

    public static String getVisaVtsRequestorIdGoogle() {
        return getLinkAppProperties(VISA_VTS_REQUESTOR_ID_GOOGLE_KEY);
    }

    public static String getPushNotificationTokenProvisioningTemplateId() {
        return getLinkAppProperties(PUSH_NOTIFICATION_TOKEN_PROVISIONING_TEMPLATE_ID_KEY);
    }

    public static String getPushNotificationTokenProvisioningProjectId() {
        return getLinkAppProperties(PUSH_NOTIFICATION_TOKEN_PROVISIONING_PROJECT_ID_KEY);
    }

    public static void resetInitializingValues() {
        REREAD.set(true);
        CRYPTING_KEY_INIT_DATE.set(null);
        TRANSFORMER_INIT_DATE.set(null);
    }

    public static String getHandoffLocation() {
        return getLinkAppProperties(HANDOFF_LOCATION_KEY);
    }

    public static String getHandoffLocationTokens() {
        return getLinkAppProperties(HANDOFF_LOCATION_TOKENS_KEY);
    }

    public static String decodePassword(String secret) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] kbytes = "jaas is the way".getBytes(defaultCharset());
        SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");

        BigInteger n = new BigInteger(secret, 16);
        byte[] encoding = n.toByteArray();

        //SECURITY-344: fix leading zeros
        if (encoding.length % 8 != 0) {
            int length = encoding.length;
            int newLength = ((length / 8) + 1) * 8;
            int pad = newLength - length; //number of leading zeros
            byte[] old = encoding;
            encoding = new byte[newLength];
            for (int i = old.length - 1; i >= 0; i--) {
                encoding[i + pad] = old[i];
            }
            //SECURITY-563: handle negative numbers
            if (n.signum() == -1) {
                for (int i = 0; i < newLength - length; i++) {
                    encoding[i] = (byte) -1;
                }
            }
        }

        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decode = cipher.doFinal(encoding);
        return new String(decode, defaultCharset());
    }
}

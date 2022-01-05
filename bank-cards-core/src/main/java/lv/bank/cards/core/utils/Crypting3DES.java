package lv.bank.cards.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Calendar;

import static java.nio.charset.Charset.defaultCharset;

@Slf4j
public class Crypting3DES {

    private static final String ALGORITHM_NAME = "DESede";
    private static final String ALGORITHM_MODE = "DESede/CBC/NoPadding";

    private static Cipher encryptCipher;
    private static Cipher decryptCipher;
    private static IvParameterSpec iv;

    private static final String[] LETTER_BASE = new String[]{"A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z"};

    private static final String[] SYMBOL_BASE = new String[]{"A", "B", "C", "D", "E", "F",
            "G", "H", /*"I", "J",*/ "K", "L", "M", "N",/* "O",*/ "P", /*"Q",*/ "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z", /*"1",*/ "2", "3", "4", "5", "6", "7", "8", "9"/*, "0"*/};


    /**
     * Encrypt given string and return in HEX Decimal form
     */
    public static String encrypting(String value) {
        initCrypter();
        try {
            byte[] valueBytes = value.getBytes(defaultCharset());
            int length = byteArrayLength(valueBytes.length);
            byte[] dataToEncrypt = Arrays.copyOf(valueBytes, length);
            byte[] encryptedData;
            encryptedData = encryptCipher.doFinal(dataToEncrypt);
            return DatatypeConverter.printHexBinary(encryptedData);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            return null;
        }
    }

    /**
     * Get byte array length for crypting without padding
     */
    private static int byteArrayLength(int l) {
        if (l < 9) {
            return 8;
        } else {
            int times = l / 8;
            int add = l % 8;
            return times * 8 + (add == 0 ? 0 : 8);
        }
    }

    /**
     * Decrypt given HEX decimal string and return in plain text
     */
    public static String decrypting(String code) {
        initCrypter();
        try {
            byte[] hex = DatatypeConverter.parseHexBinary(code);
            byte[] textDecrypted = decryptCipher.doFinal(hex);
            return new String(textDecrypted, defaultCharset()).replaceAll("\\x00", "");
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            return null;
        }
    }

    /**
     * Initialize Crypter with new key every 30 min. Take key from linkApp.properties file
     */
    private static void initCrypter() {
        Calendar min30Ago = Calendar.getInstance();
        min30Ago.add(Calendar.MINUTE, -30);
        if (LinkAppProperties.CRYPTING_KEY_INIT_DATE.get() == null || LinkAppProperties.CRYPTING_KEY_INIT_DATE.get().before(min30Ago)) {
            LinkAppProperties.CRYPTING_KEY_INIT_DATE.set(Calendar.getInstance());

            String sharedKey = LinkAppProperties.getAuthentificationCode();
            if (sharedKey == null) {
                log.error("Did not find shared key '" + LinkAppProperties.AUTHENTIFICATION_CODE_KEY + "'");
            } else if (sharedKey.length() == 32) {
                sharedKey = sharedKey + sharedKey.substring(0, 16);
            } else if (sharedKey.length() != 48) {
                log.error("Wrong length of shared key '" + LinkAppProperties.AUTHENTIFICATION_CODE_KEY + "'. Should be 32 or 48 symbols");
            }
            try {
                iv = new IvParameterSpec(new byte[8]);
                byte[] keyBytes = DatatypeConverter.parseHexBinary(sharedKey);
                SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM_NAME);
                SecretKey key = factory.generateSecret(new DESedeKeySpec(keyBytes));
                encryptCipher = Cipher.getInstance(ALGORITHM_MODE);
                encryptCipher.init(Cipher.ENCRYPT_MODE, key, iv);
                decryptCipher = Cipher.getInstance(ALGORITHM_MODE);
                decryptCipher.init(Cipher.DECRYPT_MODE, key, iv);
            } catch (NoSuchAlgorithmException | InvalidKeyException
                    | InvalidKeySpecException | NoSuchPaddingException |
                    InvalidAlgorithmParameterException e) {
                throw new RuntimeException("Could not initialize Crypter");
            }
        }
    }

    public static String getToken(long number) throws DataIntegrityException {
        int charBase = LETTER_BASE.length;
        long number2 = number;
        number = number * 7;
        StringBuilder sb = new StringBuilder();
        if (number == 0) {
            sb.append(LETTER_BASE[0]);
        }
        while (number > 0) {
            int reminder = (int) (number % charBase);
            sb.append(LETTER_BASE[reminder]);
            number = number / charBase;
        }
        String token = StringUtils.rightPad(sb.toString(), 5, LETTER_BASE[0]);
        token = StringUtils.substring(token, 0, 3) + LETTER_BASE[getChecksum(number2, 15)] + StringUtils.substring(token, 3);
        if (6 != token.length()) {
            throw new DataIntegrityException("Token lenght isn not 6");
        }
        return token;
    }

    private static int getChecksum(long number, int base) {
        int factor = 2;
        int sum = 0;
        while (number > 0) {
            int integer = (int) (number % base) * factor;
            if (integer < base)
                sum += integer;
            else
                sum += integer - base + 1;
            factor = 3 - factor;
            number = number / base;
        }
        return (base - (sum % base)) % base;
    }

    public static String getRandomString(int length) {
        String string = "";
        for (int i = 0; i < length; i++) {
            int number = new Double(SYMBOL_BASE.length * Math.random() * 0.999999).intValue();
            string += SYMBOL_BASE[number];
        }
        return string;
    }
}

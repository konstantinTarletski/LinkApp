package lv.bank.cards.link;

import java.math.BigInteger;
import java.text.SimpleDateFormat;

public class Constants {

    public static final String CARD_REPLACE_ACTION = "cardReplace";
    public static final String CARD_RENEW_ACTION = "cardRenew";
    public static final String CARD_DUPLICATE_ACTION = "cardDuplicate";
    public static final String CARD_CREATE_ACTION = "cardCreate";
    public static final String PIN_REMINDER_ACTION = "PINReminder";
    public static final String PIN_REMINDER_BT_ACTION = "PINReminder(BT)";
    public static final String INFORMATION_CHANGE_ACTION = "informationChange";

    public final static String CLIENT_TYPE_PRIVATE = "1";
    public final static String CLIENT_TYPE_CORPORATE = "2";

    public final static String MIN_BAL_ACCOUNT_RISK_LEVEL_LV = "IL";
    public final static String MIN_BAL_ACCOUNT_RISK_LEVEL_EE = "MSC";

    public final static int MIGRATED_CARD_MIN_EXPIRY = 24;
    public final static int MIGRATED_CARD_MAX_EXPIRY = 36;

    public static final int MAX_CARD_EXPIRY_IN_MONTHS = 36;

    public static final SimpleDateFormat PERSONAL_CODE_DATE_FORMAT_LT = new SimpleDateFormat("yyMMdd");

    public static final int MIN_AGE_FOR_USING_CAR = 14;

    public final static String APPLICATION_TYPE_CARD_AUTO_RENEW = "AutoRenewal";

    public static final BigInteger WITH_CHARGE = BigInteger.ONE;
    public static final BigInteger WITHOUT_CHARGE = BigInteger.ZERO;

    public final static String DEFAULT_PRODUCT = "0";
    public final static String DEFAULT_REP_LANG = "1";

    public final static String RANGE_ID_PREFIX = "1";

    // similarity threshold of client and cardholder names for automatic
    // cardholder person code change when person code is updated on client level
    public static final int NAME_DIFF_THRESHOLD = 90;
}

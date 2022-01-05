package lv.bank.rest.mapper;

import lv.bank.cards.core.entity.cms.IzdLock;
import lv.bank.cards.core.entity.rtps.StipLocks;
import lv.bank.rest.dto.ReservationDO;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static lv.bank.rest.dto.ETransactionStatus.AUTHORIZED;
import static org.apache.commons.lang.StringUtils.substring;
import static org.apache.commons.lang.StringUtils.substringAfter;
import static org.apache.commons.lang.StringUtils.substringBefore;
import static org.apache.commons.lang.StringUtils.trimToNull;

public class ReservationMapper {

    private static final ThreadLocal<DateFormat> DATE_TIME_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));

    public static ReservationDO toReservationDO(IzdLock lock) {
        ReservationDO ret = ReservationDO.builder()
                .id(lock.getFld038())
                .date(lock.getRequestDate() == null ? null : DATE_TIME_FORMAT.get().format(lock.getRequestDate()))
                .pan(panMask(lock.getFld002()))
                .cardId(lock.getFld002())
                .originAmount(amount(lock.getLockingSign() == 0, lock.getFld004(), lock.getIzdCcyFld049().getExp()))
                .originCurrency(lock.getFld049())
                .billingAmount(amount(lock.getLockingSign() == 0, lock.getFld006(), lock.getIzdCcyFld051().getExp()))
                .billingCurrency(lock.getFld051())
                .transactionStatus(AUTHORIZED)
                .shopName(shopName(lock.getFld043()))
                .shopAddress(shopAddress(lock.getFld043()))
                .shopCountry(shopCountry(lock.getFld043())).build();

        if (ret != null) {
            ret.calculateRate();
        }

        return ret;
    }

    private static BigDecimal amount(boolean negative, Long amount, String exp) {
        return BigDecimal.valueOf((negative ? -1 : 1) * amount).divide(BigDecimal.valueOf(Math.pow(10, Double.parseDouble(exp))));
    }

    private static BigDecimal amount(boolean negative, Long amount, Integer exp) {
        return BigDecimal.valueOf((negative ? -1 : 1) * amount).divide(BigDecimal.valueOf(Math.pow(10, Double.valueOf(exp))));
    }

    private static String shopName(String merchant) {
        return merchant == null ? null : substringBefore(merchant, ">");
    }

    private static String shopAddress(String merchant) {
        return merchant == null ? null : trimToNull(substringAfter(substring(merchant, 0, merchant.length() - 2), ">"));
    }

    private static String shopCountry(String merchant) {
        return merchant == null ? null : substring(merchant, merchant.length() - 2);
    }

    public static String panMask(String cardNumber) {
        if (StringUtils.isBlank(cardNumber)) {
            return null;
        }
        cardNumber = trimToNull(cardNumber);

        final String left = substring(cardNumber, 0, 6);
        final String center = StringUtils.leftPad("", cardNumber.length() - 10, "*");
        final String right = substring(cardNumber, cardNumber.length() - 4);
        return left + center + right;
    }

    public static ReservationDO toReservationDO(StipLocks lock) {
        ReservationDO ret = lock == null || lock.getStipLocksMatch() == null ? null : ReservationDO.builder()
                .id(lock.getStipLocksMatch().getFld038())
                .date(lock.getRequestDate() == null ? null : DATE_TIME_FORMAT.get().format(lock.getRequestDate()))
                .pan(panMask(lock.getStipLocksMatch().getFld002()))
                .cardId(lock.getStipLocksMatch().getFld002())
                .originAmount(amount(Math.abs(lock.getAmount()) == lock.getAmount(),
                        lock.getStipLocksMatch().getFld004(),
                        lock.getStipLocksMatch().getCurrencyCodeByFld049().getExpDot()))
                .originCurrency(lock.getStipLocksMatch().getCurrencyCodeByFld049().getCcyAlpha())
                .billingAmount(amount(Math.abs(lock.getAmount()) == lock.getAmount(),
                        lock.getStipLocksMatch().getFld006(),
                        lock.getStipLocksMatch().getCurrencyCodeByFld051().getExpDot()))
                .billingCurrency(lock.getStipLocksMatch().getCurrencyCodeByFld051().getCcyAlpha())
                .transactionStatus(AUTHORIZED)
                .shopName(shopName(lock.getStipLocksMatch().getFld043()))
                .shopAddress(shopAddress(lock.getStipLocksMatch().getFld043()))
                .shopCountry(shopCountry(lock.getStipLocksMatch().getFld043()))
                .build();

        if (ret != null) {
            ret.calculateRate();
        }
        return ret;
    }
}


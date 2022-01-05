package lv.bank.rest.service;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.cms.IzdLock;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.rtps.StipLocks;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.rest.dto.ReservationListDO;
import lv.bank.rest.exception.BusinessException;
import lv.bank.rest.mapper.ReservationMapper;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.nordlb.cards.transmaster.bo.interfaces.IzdLockManager;
import lv.nordlb.cards.transmaster.fo.interfaces.TMFManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lv.bank.rest.exception.JsonErrorCode.ACCOUNT_NOT_FOUND;
import static lv.bank.rest.exception.JsonErrorCode.BAD_REQUEST;
import static org.apache.commons.lang.StringUtils.isBlank;

@Slf4j
@Stateless
public class ReservationService {

    private static final ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    @Inject
    private PcdabaNGManager pcdabaNGManager;

    @Inject
    private IzdLockManager izdLockManager;

    @Inject
    private TMFManager tmfManager;

    public ReservationListDO getReservations(String accountNo, String country, String fromDateString, String toDateString,
                                             BigDecimal fromAmount, BigDecimal toAmount, String shopName,
                                             List<String> cardNumbers) throws BusinessException {
        PcdAccount account = pcdabaNGManager.getAccountByCoreAccountNo(accountNo, country);
        if (account == null) {
            throw BusinessException.create(ACCOUNT_NOT_FOUND, "accountNo", "Account with given account number is not found");
        }
        final Date fromDate = isBlank(fromDateString) ? null : parseFromDate(fromDateString);
        final Date toDate = isBlank(toDateString) ? null : parseToDate(toDateString);
        final Long fromA = fromAmount == null ? null : fromAmount.multiply(BigDecimal.valueOf(100L)).longValue();
        final Long toA = toAmount == null ? null : toAmount.multiply(BigDecimal.valueOf(100L)).longValue();

        final ReservationListDO reservation = new ReservationListDO();

        final List<IzdLock> locksFromCms = izdLockManager.findIzdLocksByAccount(account.getAccountNo(), false, fromDate, toDate, fromA, toA, shopName, cardNumbers);
        final Set<Long> usedRowIdsFromCms = new HashSet<>();

        locksFromCms.forEach(lock -> {
            if (!cmsLockMustBeHiddenFromClient(lock)) {
                usedRowIdsFromCms.add(lock.getRowNumb());
                reservation.getReservations().add(ReservationMapper.toReservationDO(lock));
            }
        });

        final String accountNoString = account.getAccountNo().toPlainString();
        final List<StipLocks> locksFromRtps = tmfManager.findStipLocksByAccount(accountNoString, false, fromDate, toDate, fromA, toA, shopName, cardNumbers);

        locksFromRtps.stream()
                .filter(s -> !rtpsLockMustBeHiddenFromClient(s))
                .filter(s -> !usedRowIdsFromCms.contains(s.getRowNumb()))
                .forEach(s -> reservation.getReservations().add(ReservationMapper.toReservationDO(s)));

        return reservation;
    }

    private Date parseFromDate(String fromDate) throws BusinessException {
        try {
            Date date = DATE_FORMAT.get().parse(fromDate);
            LocalDateTime localDateTime = dateToLocalDateTime(date);
            LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
            return localDateTimeToDate(startOfDay);
        } catch (ParseException e) {
            log.error("parseFromDate", e);
            throw BusinessException.create(BAD_REQUEST, "fromDate", "Invalid from date format");
        }
    }

    private Date parseToDate(String toDate) throws BusinessException {
        try {
            Date date = DATE_FORMAT.get().parse(toDate);
            LocalDateTime localDateTime = dateToLocalDateTime(date);
            LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
            return localDateTimeToDate(endOfDay);
        } catch (ParseException e) {
            log.error("parseToDate", e);
            throw BusinessException.create(BAD_REQUEST, "toDate", "Invalid to date format");
        }
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private boolean cmsLockMustBeHiddenFromClient(IzdLock lock) {
        return CardUtils.isDepositInOutsourcedAtm(lock.getProcCode(), lock.getFld026(), lock.getFld032());
    }

    private boolean rtpsLockMustBeHiddenFromClient(StipLocks lock) {
        return CardUtils.isDepositInOutsourcedAtm(lock.getStipLocksMatch().getFld003(), lock.getStipLocksMatch().getFld026(),
                lock.getStipLocksMatch().getFld032());
    }
}

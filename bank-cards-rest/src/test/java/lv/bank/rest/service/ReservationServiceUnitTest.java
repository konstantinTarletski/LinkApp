package lv.bank.rest.service;

import lv.bank.rest.dto.ReservationDO;
import lv.bank.rest.exception.BusinessException;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.bank.cards.core.entity.cms.IzdCcyTable;
import lv.bank.cards.core.entity.cms.IzdLock;
import lv.nordlb.cards.transmaster.bo.interfaces.IzdLockManager;
import lv.bank.cards.core.entity.rtps.CurrencyCode;
import lv.bank.cards.core.entity.rtps.StipLocks;
import lv.bank.cards.core.entity.rtps.StipLocksMatch;
import lv.nordlb.cards.transmaster.fo.interfaces.TMFManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static jersey.repackaged.com.google.common.collect.Lists.newArrayList;
import static lv.bank.rest.dto.ETransactionStatus.AUTHORIZED;
import static lv.bank.rest.exception.JsonErrorCode.ACCOUNT_NOT_FOUND;
import static lv.bank.rest.exception.JsonErrorCode.BAD_REQUEST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceUnitTest {

    private static final PcdAccount ACCOUNT = new PcdAccount();
    private static final String ACCOUNT_NO = "123456789";
    private static final String COUNTRY_CODE = "EE";
    private static final String TRANSACTION_ID = "TRANSACTION_ID";
    private static final String ORIGIN_CURRENCY = "EUR";
    private static final String ORIGIN_CURRENCY_ISO = "978";
    private static final String BILLING_CURRENCY = "USD";
    private static final String BILLING_CURRENCY_ISO = "840";
    private static final String SHOP_NAME_ADDRESS_COUNTRY = "SHOP_NAME> SHOP_ADDRESS EE";
    private static final String CARD_NUMBER = "111122223334444";
    private static final String CARD_NUMBER_MASKED = "111122*****4444";

    static {
        ACCOUNT.setAccountNo(new BigDecimal(ACCOUNT_NO));
    }

    @Mock
    private PcdabaNGManager pcdabaNGManager;

    @Mock
    private IzdLockManager izdLockManager;

    @Mock
    private TMFManager tmfManager;

    @InjectMocks
    private ReservationService reservationService;

    @Before
    public void before() {
        when(pcdabaNGManager.getAccountByCoreAccountNo(ACCOUNT_NO, COUNTRY_CODE)).thenReturn(ACCOUNT);
    }

    @Test
    public void testAccountNotFoundException() {
        when(pcdabaNGManager.getAccountByCoreAccountNo(ACCOUNT_NO, COUNTRY_CODE)).thenReturn(null);
        try {
            reservationService.getReservations(ACCOUNT_NO, COUNTRY_CODE, null, null, null, null, null, null);
            fail("Exception expected!");
        } catch (BusinessException e) {
            assertThat(e.getErrorCode(), is(ACCOUNT_NOT_FOUND));
            assertThat(e.getTarget(), is("accountNo"));
            assertThat(e.getMessage(), is("Account with given account number is not found"));
        }
    }

    @Test
    public void testInvalidFormatForFromDate() {
        String fromDate = "invalidFormat";
        try {
            reservationService.getReservations(ACCOUNT_NO, COUNTRY_CODE, fromDate, null, null, null, null, null);
            fail("Exception expected!");
        } catch (BusinessException e) {
            assertThat(e.getErrorCode(), is(BAD_REQUEST));
            assertThat(e.getTarget(), is("fromDate"));
            assertThat(e.getMessage(), is("Invalid from date format"));
        }
    }

    @Test
    public void testInvalidFormatForToDate() {
        String toDate = "invalidFormat";
        try {
            reservationService.getReservations(ACCOUNT_NO, COUNTRY_CODE, null, toDate, null, null, null, null);
            fail("Exception expected!");
        } catch (BusinessException e) {
            assertThat(e.getErrorCode(), is(BAD_REQUEST));
            assertThat(e.getTarget(), is("toDate"));
            assertThat(e.getMessage(), is("Invalid to date format"));
        }
    }

    @Test
    public void testVerifyArguments() throws BusinessException {
        // given
        final String fromDate = "2000-12-31";
        final String toDate = "2001-12-31";
        final BigDecimal fromAmount = new BigDecimal("2.25");
        final BigDecimal toAmount = new BigDecimal("3.25");
        final String shopName = "SHOP_NAME";
        final List<String> cardNumbers = asList("CARD_NO_1", "CARD_NO_2");

        // when
        reservationService.getReservations(ACCOUNT_NO, COUNTRY_CODE, fromDate, toDate, fromAmount, toAmount, shopName, cardNumbers);

        // then
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final long expectedFromAmount = 225L;
        final long expectedToAmount = 325L;

        ArgumentCaptor<Date> fromDateCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Date> toDateCaptor = ArgumentCaptor.forClass(Date.class);

        verify(pcdabaNGManager).getAccountByCoreAccountNo(ACCOUNT_NO, COUNTRY_CODE);
        verify(izdLockManager).findIzdLocksByAccount(eq(ACCOUNT.getAccountNo()), eq(false), fromDateCaptor.capture(), toDateCaptor.capture(), eq(expectedFromAmount), eq(expectedToAmount), eq(shopName), eq(cardNumbers));
        verify(tmfManager).findStipLocksByAccount(eq(ACCOUNT_NO), eq(false), fromDateCaptor.capture(), toDateCaptor.capture(), eq(expectedFromAmount), eq(expectedToAmount), eq(shopName), eq(cardNumbers));

        assertThat(dateFormat.format(fromDateCaptor.getAllValues().get(0)), is(fromDate + " 00:00:00"));
        assertThat(dateFormat.format(fromDateCaptor.getAllValues().get(1)), is(fromDate + " 00:00:00"));
        assertThat(dateFormat.format(toDateCaptor.getAllValues().get(0)), is(toDate + " 23:59:59"));
        assertThat(dateFormat.format(toDateCaptor.getAllValues().get(1)), is(toDate + " 23:59:59"));
    }

    @Test
    public void testReturnLockFromCms() throws BusinessException {
        // given
        final IzdLock cmsLock = buildCmsLock(1L, TRANSACTION_ID);
        when(izdLockManager.findIzdLocksByAccount(any(), eq(false), any(), any(), any(), any(), any(), any())).thenReturn(newArrayList(cmsLock));

        // when
        final List<ReservationDO> actual = reservationService.getReservations(ACCOUNT_NO, COUNTRY_CODE, null, null, null, null, null, null).getReservations();

        // then
        assertThat(actual, hasSize(1));

        final ReservationDO actualReservation = actual.get(0);
        assertThat(actualReservation.getId(), is("TRANSACTION_ID"));
        assertThat(actualReservation.getDate(), is(notNullValue()));
        assertThat(actualReservation.getPan(), is(CARD_NUMBER_MASKED));
        assertThat(actualReservation.getCardId(), is(CARD_NUMBER));
        assertThat(actualReservation.getOriginAmount().doubleValue(), is(1.0));
        assertThat(actualReservation.getOriginCurrency(), is("EUR"));
        assertThat(actualReservation.getBillingAmount().doubleValue(), is(10.0));
        assertThat(actualReservation.getBillingCurrency(), is("USD"));
        assertThat(actualReservation.getRate(), is(new BigDecimal("0.1000000000")));
        assertThat(actualReservation.getTransactionStatus(), is(AUTHORIZED));
        assertThat(actualReservation.getShopName(), is("SHOP_NAME"));
        assertThat(actualReservation.getShopAddress(), is("SHOP_ADDRESS"));
        assertThat(actualReservation.getShopCountry(), is("EE"));
    }

    @Test
    public void testReturnLockFromRtps() throws BusinessException {
        // given
        final StipLocks rtpsLock = buildRtpsLock(1L, TRANSACTION_ID);
        when(tmfManager.findStipLocksByAccount(any(), eq(false), any(), any(), any(), any(), any(), any())).thenReturn(newArrayList(rtpsLock));

        // when
        final List<ReservationDO> actual = reservationService.getReservations(ACCOUNT_NO, COUNTRY_CODE, null, null, null, null, null, null).getReservations();

        // then
        assertThat(actual, hasSize(1));

        final ReservationDO actualReservation = actual.get(0);
        assertThat(actualReservation.getId(), is(TRANSACTION_ID));
        assertThat(actualReservation.getDate(), is(notNullValue()));
        assertThat(actualReservation.getPan(), is("111122*****4444"));
        assertThat(actualReservation.getCardId(), is(CARD_NUMBER));
        assertThat(actualReservation.getOriginAmount().doubleValue(), is(-1.0));
        assertThat(actualReservation.getOriginCurrency(), is(ORIGIN_CURRENCY));
        assertThat(actualReservation.getBillingAmount().doubleValue(), is(-10.0));
        assertThat(actualReservation.getBillingCurrency(), is(BILLING_CURRENCY));
        assertThat(actualReservation.getRate(), is(new BigDecimal("0.1000000000")));
        assertThat(actualReservation.getTransactionStatus(), is(AUTHORIZED));
        assertThat(actualReservation.getShopName(), is("SHOP_NAME"));
        assertThat(actualReservation.getShopAddress(), is("SHOP_ADDRESS"));
        assertThat(actualReservation.getShopCountry(), is("EE"));
    }

    @Test
    public void testReturnUniqueReservationsByRowNumber() throws BusinessException {
        // given
        final IzdLock cmsLock1 = buildCmsLock(1L, "TRANSACTION_ID_1");
        final IzdLock cmsLock2 = buildCmsLock(2L, "TRANSACTION_ID_2");
        when(izdLockManager.findIzdLocksByAccount(any(), eq(false), any(), any(), any(), any(), any(), any())).thenReturn(newArrayList(cmsLock1, cmsLock2));

        final StipLocks rtpsLock1 = buildRtpsLock(1L, "TRANSACTION_ID_1");
        final StipLocks rtpsLock2 = buildRtpsLock(99L, "TRANSACTION_ID_99");
        when(tmfManager.findStipLocksByAccount(any(), eq(false), any(), any(), any(), any(), any(), any())).thenReturn(newArrayList(rtpsLock1, rtpsLock2));

        // when
        final List<ReservationDO> actual = reservationService.getReservations(ACCOUNT_NO, COUNTRY_CODE, null, null, null, null, null, null).getReservations();

        // then
        assertThat(actual, hasSize(3));
        assertThat(actual.get(0).getId(), is("TRANSACTION_ID_1"));
        assertThat(actual.get(1).getId(), is("TRANSACTION_ID_2"));
        assertThat(actual.get(2).getId(), is("TRANSACTION_ID_99"));
    }

    @Test
    public void testHideOutsourcedAtmDepositLockFromCms() throws BusinessException {
        // given
        final IzdLock cmsLockToShow = buildCmsLock(1L, "LOCK_TO_SHOW");
        final IzdLock cmsLockToHide = buildCmsLock(2L, "LOCK_TO_HIDE");
        cmsLockToHide.setProcCode("21"); // deposit
        cmsLockToHide.setFld026("6011"); // ATM
        cmsLockToHide.setFld032("416352"); // LHV bank ATM

        when(izdLockManager.findIzdLocksByAccount(any(), eq(false), any(), any(), any(), any(), any(), any()))
                .thenReturn(newArrayList(cmsLockToShow, cmsLockToHide));

        // when
        final List<ReservationDO> actual = reservationService.getReservations(ACCOUNT_NO, COUNTRY_CODE, null, null, null, null, null, null).getReservations();

        // then
        assertThat(actual, hasSize(1));
        assertThat(actual.get(0).getId(), is("LOCK_TO_SHOW"));
    }

    @Test
    public void testHideOutsourcedAtmDepositLockFromRtps() throws BusinessException {
        // given
        final IzdLock cmsLockToShow = buildCmsLock(1L, "LOCK_TO_SHOW");

        when(izdLockManager.findIzdLocksByAccount(any(), eq(false), any(), any(), any(), any(), any(), any()))
                .thenReturn(newArrayList(cmsLockToShow));

        final StipLocks rtpsLockToShow = buildRtpsLock(1L, "LOCK_TO_SHOW");
        final StipLocks rtpsLockToHide = buildRtpsLock(2L, "LOCK_TO_HIDE");
        rtpsLockToHide.getStipLocksMatch().setFld003("21"); // deposit
        rtpsLockToHide.getStipLocksMatch().setFld026("6011"); // ATM
        rtpsLockToHide.getStipLocksMatch().setFld032("416352"); // LHV bank ATM

        when(tmfManager.findStipLocksByAccount(any(), eq(false), any(), any(), any(), any(), any(), any()))
                .thenReturn(newArrayList(rtpsLockToShow, rtpsLockToHide));

        // when
        final List<ReservationDO> actual = reservationService.getReservations(ACCOUNT_NO, COUNTRY_CODE, null, null, null, null, null, null).getReservations();

        // then
        assertThat(actual, hasSize(1));
        assertThat(actual.get(0).getId(), is("LOCK_TO_SHOW"));
    }

    private IzdLock buildCmsLock(long rowNumber, String transactionId) {
        final IzdLock cmsLock = new IzdLock();
        cmsLock.setRowNumb(rowNumber);
        cmsLock.setRequestDate(new Date());
        cmsLock.setLockingSign(1);
        cmsLock.setIzdCcyFld049(new IzdCcyTable());
        cmsLock.getIzdCcyFld049().setExp("2");
        cmsLock.getIzdCcyFld049().setCcyCode(ORIGIN_CURRENCY_ISO);
        cmsLock.setIzdCcyFld051(new IzdCcyTable());
        cmsLock.getIzdCcyFld051().setExp("2");
        cmsLock.getIzdCcyFld051().setCcyCode(BILLING_CURRENCY_ISO);
        cmsLock.setFld002(CARD_NUMBER);
        cmsLock.setFld004(100L);
        cmsLock.setFld006(1000L);
        cmsLock.setFld043(SHOP_NAME_ADDRESS_COUNTRY);
        cmsLock.setFld049(ORIGIN_CURRENCY);
        cmsLock.setFld051(BILLING_CURRENCY);
        cmsLock.setFld038(transactionId);
        return cmsLock;
    }

    private StipLocks buildRtpsLock(long rowNumber, String transactionId) {
        final StipLocks rtpsLock = new StipLocks();
        rtpsLock.setRowNumb(rowNumber);
        rtpsLock.setRequestDate(new Date());
        rtpsLock.setAmount(303L);
        rtpsLock.setStipLocksMatch(new StipLocksMatch());
        rtpsLock.getStipLocksMatch().setCurrencyCodeByFld049(new CurrencyCode());
        rtpsLock.getStipLocksMatch().getCurrencyCodeByFld049().setExpDot(2);
        rtpsLock.getStipLocksMatch().getCurrencyCodeByFld049().setCcyAlpha(ORIGIN_CURRENCY);
        rtpsLock.getStipLocksMatch().setCurrencyCodeByFld051(new CurrencyCode());
        rtpsLock.getStipLocksMatch().getCurrencyCodeByFld051().setExpDot(2);
        rtpsLock.getStipLocksMatch().getCurrencyCodeByFld051().setCcyAlpha(BILLING_CURRENCY);
        rtpsLock.getStipLocksMatch().setFld002(CARD_NUMBER);
        rtpsLock.getStipLocksMatch().setFld004(100L);
        rtpsLock.getStipLocksMatch().setFld006(1000L);
        rtpsLock.getStipLocksMatch().setFld043(SHOP_NAME_ADDRESS_COUNTRY);
        rtpsLock.getStipLocksMatch().setFld049(ORIGIN_CURRENCY_ISO);
        rtpsLock.getStipLocksMatch().setFld051(BILLING_CURRENCY_ISO);
        rtpsLock.getStipLocksMatch().setFld038(transactionId);
        return rtpsLock;
    }
}

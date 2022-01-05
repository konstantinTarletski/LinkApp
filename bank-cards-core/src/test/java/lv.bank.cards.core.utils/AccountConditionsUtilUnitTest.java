package lv.bank.cards.core.utils;

import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountConditionsUtilUnitTest {

    private static final BigDecimal INTEREST_RATE_1 = new BigDecimal("1.23");
    private static final BigDecimal INTEREST_RATE_2 = new BigDecimal("4.56");

    @Test
    public void testReturnBaseInterestRate() {
        // given
        PcdCondAccnt account = PcdCondAccnt.builder().debIntrBaseP(INTEREST_RATE_1).debIntrOverPB(INTEREST_RATE_2).build();
        // when
        BigDecimal actual = AccountConditionsUtil.getCreditInterestRate(account);
        // then
        assertThat(actual, is(INTEREST_RATE_1));
    }

    @Test
    public void testReturnOverdueBaseForMissingBase() {
        // given
        PcdCondAccnt account = PcdCondAccnt.builder().debIntrBaseP(null).debIntrOverPB(INTEREST_RATE_1).build();
        // when
        BigDecimal actual = AccountConditionsUtil.getCreditInterestRate(account);
        // then
        assertThat(actual, is(INTEREST_RATE_1));
    }

    @Test
    public void testReturnOverdueBaseForNegativeBase() {
        // given
        PcdCondAccnt account = PcdCondAccnt.builder().debIntrBaseP(INTEREST_RATE_1.negate()).debIntrOverPB(INTEREST_RATE_2).build();
        // when
        BigDecimal actual = AccountConditionsUtil.getCreditInterestRate(account);
        // then
        assertThat(actual, is(INTEREST_RATE_2));
    }
}

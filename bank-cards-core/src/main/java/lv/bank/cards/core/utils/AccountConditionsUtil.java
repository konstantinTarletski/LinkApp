package lv.bank.cards.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountConditionsUtil {

    public static BigDecimal getCreditInterestRate(PcdCondAccnt account) {
        BigDecimal debitInterestPurchaseBase = account.getDebIntrBaseP();
        BigDecimal debitInterestPurchaseOverdueBase = account.getDebIntrOverPB();

        if (debitInterestPurchaseBase != null && debitInterestPurchaseBase.compareTo(BigDecimal.ZERO) > 0) {
            return debitInterestPurchaseBase;
        }
        return debitInterestPurchaseOverdueBase;
    }
}

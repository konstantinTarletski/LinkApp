package lv.bank.cards.soap.service;

import lombok.RequiredArgsConstructor;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.rtps.dao.StipLocksDAO;
import lv.bank.cards.core.rtps.impl.StipLocksDAOHibernate;
import lv.bank.cards.soap.service.dto.OtbDo;

@RequiredArgsConstructor
public class OtbService {

    protected final StipLocksDAO stipLocksDAO;

    public OtbDo calculateOtb(StipAccount acc) {

        if (acc != null) {

            OtbDo otbDo = new OtbDo();

            Long lockedAmount = getLockedAmountByStipAccount(acc);
            if (lockedAmount == null) {
                lockedAmount = 0L;
            }
            otbDo.setAmtLocked(String.format("%.2f", lockedAmount.doubleValue() / Math.pow(10, acc.getCurrencyCode().getExpDot().doubleValue())));
            otbDo.setOtb(String.format("%.2f",
                    (acc.getInitialAmount().doubleValue() + acc.getBonusAmount().doubleValue() - lockedAmount.doubleValue())
                            /
                            Math.pow(10, acc.getCurrencyCode().getExpDot().doubleValue()))
            );
            otbDo.setAmtInitial(String.format("%.2f", acc.getInitialAmount().doubleValue() / Math.pow(10, acc.getCurrencyCode().getExpDot().doubleValue())));
            otbDo.setAmtBonus(String.format("%.2f", acc.getBonusAmount().doubleValue() / Math.pow(10, acc.getCurrencyCode().getExpDot().doubleValue())));
            otbDo.setAmtCrd(String.format("%.2f", acc.getCreditLimit().doubleValue() / Math.pow(10, acc.getCurrencyCode().getExpDot().doubleValue())));
            return otbDo;
        }
        return null;
    }

    protected Long getLockedAmountByStipAccount(StipAccount sa) {
        if (sa == null) return null;
        Long la = stipLocksDAO.calculateTotalLockedAmount(sa);
        return (la == null ? 0L : la) + (sa.getLockAmountCms() == null ? 0 : sa.getLockAmountCms());
    }

}

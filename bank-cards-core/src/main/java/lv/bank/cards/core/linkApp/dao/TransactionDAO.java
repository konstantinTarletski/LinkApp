package lv.bank.cards.core.linkApp.dao;

import lv.bank.cards.core.entity.linkApp.PcdSlip;

import java.util.Date;
import java.util.List;

public interface TransactionDAO extends DAO {
    List<PcdSlip> findTransactionDetailsByDate(String card, Date from, Date to);
}

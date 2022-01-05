package lv.bank.cards.core.linkApp.dao;

public interface CcyConvDAO extends DAO {
    double findConversionRate(String from, String to);
}

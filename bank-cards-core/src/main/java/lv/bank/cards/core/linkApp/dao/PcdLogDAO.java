package lv.bank.cards.core.linkApp.dao;

public interface PcdLogDAO {
    Long write(String source, String operation, String operator, String text, String result);
}

package lv.bank.cards.core.linkApp.dao;

public interface DAO {

    Object save(Object object);

    Object update(Object object);

    Object saveOrUpdate(Object object);

    Object delete(Object object);

}

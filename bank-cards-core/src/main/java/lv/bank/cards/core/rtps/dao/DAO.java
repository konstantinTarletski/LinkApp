package lv.bank.cards.core.rtps.dao;

import java.io.Serializable;

public interface DAO {

    Object getObject(Class<?> clazz, Serializable id);

    void saveObject(Object o);

    void updateObject(Object o);

    void removeObject(Class<?> clazz, Serializable id);
}

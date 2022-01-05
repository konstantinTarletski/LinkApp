package lv.bank.cards.core.cms.dao;

import org.hibernate.jdbc.ReturningWork;

import java.io.Serializable;

public interface DAO {

    Object getObject(Class<?> clazz, Serializable id);

    void saveOrUpdate(Object o);

    void removeObject(Class<?> clazz, Serializable id);

    String doWork(ReturningWork<String> work);

}

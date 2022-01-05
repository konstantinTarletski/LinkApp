
package lv.bank.cards.core.cms.impl;

import lv.bank.cards.core.cms.BOConstants;
import lv.bank.cards.core.cms.dao.DAO;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;

public class BaseDAOHibernate implements DAO {

    protected static final SessionFactory sf;

    static {
        try {
            Context ctx = new InitialContext();
            sf = (SessionFactory) ctx.lookup(BOConstants.HibernateSessionFactory);
        } catch (NamingException ex) {
            throw new RuntimeException("Exception building SessionFactory: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void saveOrUpdate(Object o) {
        sf.getCurrentSession().saveOrUpdate(o);
    }

    @Override
    public Object getObject(Class<?> clazz, Serializable id) {
        return sf.getCurrentSession().get(clazz, id);
    }

    @Override
    public void removeObject(Class<?> clazz, Serializable id) {
        sf.getCurrentSession().delete(getObject(clazz, id));
    }

    @Override
    public String doWork(ReturningWork<String> work) {
        return sf.getCurrentSession().doReturningWork(work);
    }
}

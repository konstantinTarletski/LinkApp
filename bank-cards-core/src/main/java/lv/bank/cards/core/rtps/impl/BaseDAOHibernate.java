
package lv.bank.cards.core.rtps.impl;

import lv.bank.cards.core.rtps.FOConstants;
import lv.bank.cards.core.rtps.dao.DAO;
import org.hibernate.SessionFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;

public class BaseDAOHibernate implements DAO {
    protected static final SessionFactory sf;

    static {
        try {
            Context ctx = new InitialContext();
            sf = (SessionFactory) ctx.lookup(FOConstants.HibernateSessionFactory);
        } catch (NamingException ex) {
            throw new RuntimeException("Exception building SessionFactory: " + ex.getMessage(), ex);
        }
    }

    public void saveObject(Object o) {
        sf.getCurrentSession().saveOrUpdate(o);
    }

    public Object getObject(Class<?> clazz, Serializable id) {
        return sf.getCurrentSession().get(clazz, id);
    }

    public void removeObject(Class<?> clazz, Serializable id) {
        sf.getCurrentSession().delete(getObject(clazz, id));
    }

    public void updateObject(Object o) {
        sf.getCurrentSession().update(o);
        sf.getCurrentSession().flush();

    }
}

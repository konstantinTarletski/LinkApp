
package lv.bank.cards.core.linkApp.impl;

import lv.bank.cards.core.linkApp.PcdabaNGConstants;
import lv.bank.cards.core.linkApp.dao.DAO;
import org.hibernate.SessionFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class BaseDAOHibernate implements DAO {

    protected static final SessionFactory sf;

    static {
        try {
            Context ctx = new InitialContext();
            sf = (SessionFactory) ctx.lookup(PcdabaNGConstants.HibernateSessionFactory);
        } catch (NamingException ex) {
            throw new RuntimeException("Exception building SessionFactory: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Object save(Object object) {
        sf.getCurrentSession().save(object);
        sf.getCurrentSession().flush();
        return object;
    }

    @Override
    public Object update(Object object) {
        sf.getCurrentSession().update(object);
        sf.getCurrentSession().flush();
        return object;
    }

    @Override
    public Object saveOrUpdate(Object object) {
        sf.getCurrentSession().saveOrUpdate(object);
        sf.getCurrentSession().flush();
        return object;
    }

    @Override
    public Object delete(Object object) {
        sf.getCurrentSession().delete(object);
        sf.getCurrentSession().flush();
        return object;
    }

}

package lv.bank.cards.core.linkApp.impl;

import java.util.List;

import lv.bank.cards.core.entity.linkApp.PcdClient;

import lv.bank.cards.core.linkApp.dao.ClientsDAO;
import org.hibernate.criterion.Restrictions;

public class ClientsDAOHibernate extends BaseDAOHibernate implements ClientsDAO {

    public PcdClient findClientByCif(String cif) {
        List<?> l = sf.getCurrentSession().createCriteria(PcdClient.class).add(Restrictions.eq("clientB", cif)).list();
        if(l.size() == 1) {
            return (PcdClient)l.get(0);
        } else return null;
    }

    public PcdClient getClient(String id) {
        List<?> l = sf.getCurrentSession().createCriteria(PcdClient.class).add(Restrictions.eq("comp_id.client", id)).list();
        if(l.size() == 1) {
            return (PcdClient)l.get(0);
        } else return null;
    }
}

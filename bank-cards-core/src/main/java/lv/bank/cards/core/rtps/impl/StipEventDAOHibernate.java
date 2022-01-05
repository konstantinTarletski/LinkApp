package lv.bank.cards.core.rtps.impl;

import lv.bank.cards.core.rtps.dao.StipEventDAO;

import java.util.List;

import lv.bank.cards.core.entity.rtps.StipEventsN;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class StipEventDAOHibernate extends BaseDAOHibernate implements StipEventDAO {

    @SuppressWarnings("unchecked")
    public List<StipEventsN> findStipEventNs(StipEventsN mask) {
        Criteria crit = sf.getCurrentSession().createCriteria(StipEventsN.class);

        if (mask.getFld002() != null) crit.add(Restrictions.eq("fld002",mask.getFld002()));
        if (mask.getLockingFlag() != null) crit.add(Restrictions.eq("lockingFlag",mask.getLockingFlag()));

        return crit.list();
    }

}

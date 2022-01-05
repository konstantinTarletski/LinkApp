package lv.bank.cards.core.linkApp.impl;

import lv.bank.cards.core.linkApp.dao.AccumulatorsDAO;
import lv.bank.cards.core.entity.linkApp.PcdAccumulator;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class AccumulatorsDAOHibernate extends BaseDAOHibernate implements AccumulatorsDAO {

    @Override
    public PcdAccumulator getAccumulator(String param, String description) {
        Criteria criteria = sf.getCurrentSession().createCriteria(PcdAccumulator.class);
        criteria.add(Restrictions.eq("paramGrp", param));
        criteria.add(Restrictions.eq("description", description));
        return (PcdAccumulator) criteria.uniqueResult();
    }

}

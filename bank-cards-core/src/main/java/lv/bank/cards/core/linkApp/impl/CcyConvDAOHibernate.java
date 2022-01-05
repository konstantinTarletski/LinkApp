package lv.bank.cards.core.linkApp.impl;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.linkApp.dao.CcyConvDAO;
import lv.bank.cards.core.entity.linkApp.PcdCcyConv;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Slf4j
public class CcyConvDAOHibernate extends BaseDAOHibernate implements CcyConvDAO {

    @Override
    public double findConversionRate(String from, String to) {
        Criteria crit = sf.getCurrentSession().createCriteria(PcdCcyConv.class)
                .add(Restrictions.eq("id.curency", from))
                .add(Restrictions.eq("id.procCcy", to));
        return ((PcdCcyConv) crit.list().iterator().next()).getConvRate();
    }
}

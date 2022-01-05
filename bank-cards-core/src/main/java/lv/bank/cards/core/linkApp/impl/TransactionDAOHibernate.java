package lv.bank.cards.core.linkApp.impl;

import lv.bank.cards.core.linkApp.dao.TransactionDAO;
import lv.bank.cards.core.entity.linkApp.PcdSlip;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

public class TransactionDAOHibernate extends BaseDAOHibernate implements TransactionDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<PcdSlip> findTransactionDetailsByDate(String card, Date from, Date to) {
        List<PcdSlip> res;
        if (card != null) {
            Criteria crit = sf.getCurrentSession().createCriteria(PcdSlip.class).add(Restrictions.eq("card", card));
            if (from != null) {
                crit.add(Restrictions.ge("tranDateTime", from));
            }
            if (to != null) {
                crit.add(Restrictions.le("tranDateTime", to));
            }
            res = crit.list();
        } else {
            res = sf.getCurrentSession().createCriteria(PcdSlip.class).
                    add(Restrictions.ge("tranDateTime", from)).
                    add(Restrictions.le("tranDateTime", to)).list();
        }
        return res;
    }

}

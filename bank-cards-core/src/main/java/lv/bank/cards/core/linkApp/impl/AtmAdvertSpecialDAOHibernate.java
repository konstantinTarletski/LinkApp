package lv.bank.cards.core.linkApp.impl;

import lv.bank.cards.core.linkApp.dao.AtmAdvertSpecialDAO;
import lv.bank.cards.core.entity.linkApp.PcdAtmAdvertSpecial;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class AtmAdvertSpecialDAOHibernate extends BaseDAOHibernate implements AtmAdvertSpecialDAO {

    @Override
    public PcdAtmAdvertSpecial findAtmAdvertSpecial(String personalCode, String atmId, boolean question) {
        Criteria criteria = sf.getCurrentSession().createCriteria(PcdAtmAdvertSpecial.class)
                .add(Restrictions.eq("idCard", personalCode))
                .add(Restrictions.isNull("answer"))
                .add(Restrictions.disjunction()
                        .add(Restrictions.eq("termId", "*"))
                        .add(Restrictions.like("termId", "%" + atmId + "%")));
        if (question)
            criteria.add(Restrictions.isNull("recDate"));
        List<?> adds = criteria.list();
        if (adds.isEmpty())
            return null;
        else
            return (PcdAtmAdvertSpecial) adds.get(0);
    }
}

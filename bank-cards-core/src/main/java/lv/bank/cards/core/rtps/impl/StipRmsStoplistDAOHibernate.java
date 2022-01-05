package lv.bank.cards.core.rtps.impl;

import lv.bank.cards.core.entity.rtps.StipRmsStoplist;
import lv.bank.cards.core.rtps.dao.StipRmsStoplistDAO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class StipRmsStoplistDAOHibernate extends BaseDAOHibernate implements StipRmsStoplistDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<StipRmsStoplist> getStipRmsStoplist(String card, String centreId, Long priority) {
        if (StringUtils.isBlank(card) || StringUtils.isBlank(centreId))
            return new ArrayList<StipRmsStoplist>();
        Criteria query = sf.getCurrentSession()
                .createCriteria(StipRmsStoplist.class)
                .add(Restrictions.eq("compId.cardNumber", card))
                .add(Restrictions.eq("compId.centreId", centreId));
        if (priority != null)
            query.add(Restrictions.eq("compId.priority", priority));
        return (List<StipRmsStoplist>) query.list();
    }

}

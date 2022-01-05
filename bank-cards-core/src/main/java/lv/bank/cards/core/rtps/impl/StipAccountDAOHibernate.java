package lv.bank.cards.core.rtps.impl;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.rtps.dao.StipAccountDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

@Slf4j
public class StipAccountDAOHibernate extends BaseDAOHibernate implements StipAccountDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<StipAccount> findByCardAndCentreId(String card, String centreId) {

        if (log.isDebugEnabled())
            log.debug("Searching for accounts linked to card = {} and centreId = {}", card, centreId);
        Session s = sf.getCurrentSession();//currentSession(FOConstants.HibernateSessionFactory);
        if (log.isDebugEnabled())
            log.debug("Session retreived" + s);
        Criteria crit = s.createCriteria(StipAccount.class)
                .createCriteria("stipRestracnts").createCriteria("stipCard").add(Restrictions.eq("comp_id.cardNumber", card)).add(Restrictions.eq("comp_id.centreId", centreId));
        return (List<StipAccount>) crit.list();
    }

    @Override
    public StipAccount findAccountByAccountNoAndCentreId(String accountId, String centreId) {
        return (StipAccount) sf.getCurrentSession().createCriteria(StipAccount.class)
                .add(Restrictions.eq("comp_id.accountId", accountId))
                .add(Restrictions.eq("comp_id.centreId", centreId))
                .uniqueResult();
    }
}

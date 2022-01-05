package lv.bank.cards.core.cms.impl;

import lv.bank.cards.core.cms.dao.IzdCardTokensDAO;
import lv.bank.cards.core.entity.cms.IzdCardTokens;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Arrays;
import java.util.List;

public class IzdCardTokensDAOHibernate extends BaseDAOHibernate implements IzdCardTokensDAO {

    @Override
    public List<IzdCardTokens> findAppleCardTokens(String card, String deviceId, int tokenStatus, String appleRequestorId) {
        Criteria criteria = sf.getCurrentSession().createCriteria(IzdCardTokens.class)
                .add(Restrictions.eq("card", card))
                .add(Restrictions.eq("secureId", deviceId))
                .add(Restrictions.lt("tokenStatus", tokenStatus))
                .add(Restrictions.eq("requestorId", appleRequestorId))
                .add(Restrictions.in("tokenType", Arrays.asList("02","06")))
                .addOrder(Order.desc("createDate"));

        return criteria.list();
    }

    @Override
    public List<IzdCardTokens> findGoogleCardTokens(String card, String walletId, int tokenStatus, String googleRequestorId) {
        Criteria criteria = sf.getCurrentSession().createCriteria(IzdCardTokens.class)
                .add(Restrictions.eq("card", card))
                .add(Restrictions.eq("secureId", walletId))
                .add(Restrictions.lt("tokenStatus", tokenStatus))
                .add(Restrictions.eq("requestorId", googleRequestorId))
                .add(Restrictions.in("tokenType", Arrays.asList("02","06")))
                .addOrder(Order.desc("createDate"));
        return criteria.list();
    }

}

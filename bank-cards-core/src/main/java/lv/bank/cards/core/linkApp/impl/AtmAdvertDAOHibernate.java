package lv.bank.cards.core.linkApp.impl;

import lv.bank.cards.core.linkApp.dao.AtmAdvertDAO;
import lv.bank.cards.core.entity.linkApp.PcdAtmAdvert;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AtmAdvertDAOHibernate extends BaseDAOHibernate implements AtmAdvertDAO {

    private static final Long MIL_SEC_24 = 86400000L;

    @SuppressWarnings("unchecked")
    @Override
    public List<PcdAtmAdvert> findTodayShownAds(String personalCode) {
        Date fromDate = new Date(Calendar.getInstance().getTimeInMillis() - MIL_SEC_24);
        return sf.getCurrentSession().createCriteria(PcdAtmAdvert.class)
                .add(Restrictions.eq("atmAdvertPk.idCard", personalCode))
                .add(Restrictions.ge("atmAdvertPk.recDate", fromDate))
                .list();
    }

    @Override
    public PcdAtmAdvert findAtmAd(String personalCode, String atmId) {
        Date fromDate = new Date(Calendar.getInstance().getTimeInMillis() - (2 * MIL_SEC_24));
        List<?> adds = sf.getCurrentSession().createCriteria(PcdAtmAdvert.class)
                .add(Restrictions.eq("atmAdvertPk.idCard", personalCode))
                .add(Restrictions.eq("termId", atmId))
                .add(Restrictions.ge("atmAdvertPk.recDate", fromDate))
                .add(Restrictions.isNull("answer"))
                .addOrder(Order.desc("atmAdvertPk.recDate"))
                .list();
        if (adds.isEmpty())
            return null;
        else
            return (PcdAtmAdvert) adds.get(0);
    }
}

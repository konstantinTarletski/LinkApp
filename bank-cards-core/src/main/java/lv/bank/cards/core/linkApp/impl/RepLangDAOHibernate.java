package lv.bank.cards.core.linkApp.impl;

import lv.bank.cards.core.linkApp.dao.RepLangDAO;
import lv.bank.cards.core.entity.linkApp.PcdRepLang;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class RepLangDAOHibernate extends BaseDAOHibernate implements RepLangDAO {

    @SuppressWarnings("unchecked")
    @Override
    public PcdRepLang findByLangCode(String langCode) {
        List<PcdRepLang> list = sf.getCurrentSession().createCriteria(PcdRepLang.class).add(
                Restrictions.eq("comp_id.lanCode", langCode)).list();
        if (!list.isEmpty())
            return list.get(0);
        return null;
    }
}

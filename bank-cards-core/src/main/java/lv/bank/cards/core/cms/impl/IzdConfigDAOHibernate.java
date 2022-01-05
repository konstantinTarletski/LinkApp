package lv.bank.cards.core.cms.impl;

import lv.bank.cards.core.cms.dao.IzdConfigDAO;
import lv.bank.cards.core.entity.cms.IzdConfig;

import java.util.List;

public class IzdConfigDAOHibernate extends BaseDAOHibernate implements IzdConfigDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<IzdConfig> GetIzdConfig() {
        return (List<IzdConfig>) sf.getCurrentSession()
                .createQuery("FROM IzdConfig WHERE id.groupc = '50' and id.bankC= '23' ORDER BY id.groupc, id.bankC, id.paramNumb ASC").list();
    }

}

	
	

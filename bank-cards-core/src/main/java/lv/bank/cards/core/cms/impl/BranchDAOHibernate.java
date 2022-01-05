package lv.bank.cards.core.cms.impl;

import lv.bank.cards.core.cms.dao.BranchDAO;
import lv.bank.cards.core.entity.cms.IzdBranch;

import java.util.List;

public class BranchDAOHibernate extends BaseDAOHibernate implements BranchDAO {

    @SuppressWarnings("unchecked")
    @Override
    public IzdBranch findByExternalId(String externalID) {
        List<IzdBranch> l = sf.getCurrentSession().createQuery("select nbranch.izdBranch from NordlbBranch as nbranch where nbranch.externalId=:externalId")
                .setString("externalId", externalID).list();
        if (!l.isEmpty())
            return l.get(0);
        return null;
    }

}

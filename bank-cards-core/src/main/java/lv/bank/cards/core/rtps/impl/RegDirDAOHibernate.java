package lv.bank.cards.core.rtps.impl;

import lv.bank.cards.core.entity.rtps.Regdir;
import lv.bank.cards.core.rtps.dao.RegDirDAO;

import java.util.List;

public class RegDirDAOHibernate extends BaseDAOHibernate implements RegDirDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<Regdir> GetRegDir() {
        List<Regdir> sregdir = sf.getCurrentSession().createQuery("FROM Regdir ORDER BY regId ASC").list();
        return sregdir;
    }

}

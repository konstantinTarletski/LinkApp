package lv.bank.cards.core.linkApp.impl;

import lv.bank.cards.core.linkApp.dao.MerchantParDAO;
import lv.bank.cards.core.entity.linkApp.PcdMerchantPar;

import java.util.List;

public class MerchantParDAOHibernate extends BaseDAOHibernate implements MerchantParDAO {

    @Override
    public List<PcdMerchantPar> GetDisMerchantPar(String param) {
        @SuppressWarnings("unchecked")
        List<PcdMerchantPar> smerhpar = sf.getCurrentSession()
                .createQuery("FROM PcdMerchantPar WHERE Chr1 = :b").setParameter("b", param).list();
        return smerhpar;
    }

}

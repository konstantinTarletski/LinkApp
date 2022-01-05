package lv.nordlb.cards.pcdabaNG.ejb;

import lv.bank.cards.core.entity.linkApp.PcdMerchantPar;
import lv.bank.cards.core.linkApp.dao.MerchantParDAO;
import lv.bank.cards.core.linkApp.impl.MerchantParDAOHibernate;
import lv.nordlb.cards.pcdabaNG.interfaces.MerchantManager;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class MerchantManagerBean implements MerchantManager {

    protected final MerchantParDAO merchantParDAO;

    public MerchantManagerBean() {
        merchantParDAO = new MerchantParDAOHibernate();
    }

    @Override
    public List<PcdMerchantPar> GetDisMerchantPar(String param) {
        return merchantParDAO.GetDisMerchantPar(param);
    }

}

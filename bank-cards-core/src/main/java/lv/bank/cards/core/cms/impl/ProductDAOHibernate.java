package lv.bank.cards.core.cms.impl;

import lv.bank.cards.core.cms.dao.ProductDAO;
import lv.bank.cards.core.cms.dto.IzdOfferedProductDTO;
import lv.bank.cards.core.entity.cms.IzdOfferedProduct;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ProductDAOHibernate extends BaseDAOHibernate implements ProductDAO {


    @SuppressWarnings("unchecked")
    @Override
    public List<IzdOfferedProduct> findAvailableProducts(IzdOfferedProductDTO mask) {
        /* bankc,groupc,bin,[clnCat],clType,[company],repDistribution,ccy; */

        Criteria cr = sf.getCurrentSession().createCriteria(IzdOfferedProduct.class);
        if (mask.getBankC() != null)
            cr.add(Restrictions.eq("comp_id.bankC", mask.getBankC()));
        if (mask.getGroupc() != null)
            cr.add(Restrictions.eq("comp_id.groupc", mask.getGroupc()));
        if (mask.getBin() != null)
            cr.add(Restrictions.eq("bin", mask.getBin()));
        if (mask.getClType() != null)
            cr.add(Restrictions.eq("clType", mask.getClType()));
        if (mask.getCardType() != null)
            cr.add(Restrictions.eq("cardType", mask.getCardType()));
        if (mask.getRepDistribution() != null)
            cr.add(Restrictions.eq("repDistribution", mask.getRepDistribution()));
        if (mask.getCcy() != null)
            cr.createCriteria("izdValidProductCcies").add(Restrictions.eq("comp_id.ccy", mask.getCcy()));
        if (mask.getClnCat() != null)
            cr.add(Restrictions.eq("clnCat", mask.getClnCat()));
        else
            cr.add(Restrictions.isNull("clnCat"));

        if (mask.getCompany() != null)
            cr.add(Restrictions.eq("company", mask.getCompany()));
        else
            cr.add(Restrictions.isNull("company"));

        if (mask.getAuthLevel() != null)
            cr.add(Restrictions.eq("authLevel", mask.getAuthLevel()));

        return (List<IzdOfferedProduct>) cr.list();
    }

}

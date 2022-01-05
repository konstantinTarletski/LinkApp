package lv.bank.cards.core.linkApp.impl;

import lv.bank.cards.core.linkApp.dao.PPCardsDAO;
import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class PPCardsDAOHibernate extends BaseDAOHibernate implements PPCardsDAO {

    @SuppressWarnings("unchecked")
    @Override
    public PcdPpCard findPcdPPCardByCreditCard(String card) {
        List<PcdPpCard> it = sf.getCurrentSession().createCriteria(PcdPpCard.class).add(Restrictions.eq("pcdCard.card", card)).list();
        PcdPpCard pc = null;
        for (PcdPpCard tmp : it) {
            if (pc == null || tmp.getRegDate().after(pc.getRegDate())) {
                pc = tmp;
            }
        }
        return pc;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PcdPpCard findPcdPPCardByCardNumber(String card) {
        List<PcdPpCard> ls = sf.getCurrentSession().createCriteria(PcdPpCard.class).add(Restrictions.eq("cardNumber", Integer.valueOf(card.substring(10, 17)))).list();
        if (ls.size() > 0) {
            return ls.get(0);
        } else return null;
    }
}

package lv.bank.cards.core.rtps.impl;

import lv.bank.cards.core.entity.rtps.StipCard;
import lv.bank.cards.core.entity.rtps.StipCardPK;
import lv.bank.cards.core.rtps.dao.CardDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CardDAOHibernate extends BaseDAOHibernate implements CardDAO {

    @SuppressWarnings("unchecked")
    @Override
    public StipCard findByCardNumber(String cardNumber) throws DataIntegrityException {
        List<StipCard> c = sf.getCurrentSession().createCriteria(StipCard.class)
                .add(Restrictions.eq("comp_id.cardNumber", cardNumber)).list();
        if (c.size() > 1) throw new DataIntegrityException("Too many cards found");
        else if (c.size() == 0) throw new DataIntegrityException("No cards with this number");
        else return c.get(0);
    }

    @Override
    public StipCard load(String card, String centreId) {
        StipCardPK pk = new StipCardPK();
        pk.setCardNumber(card);
        pk.setCentreId(centreId);
        return (StipCard) sf.getCurrentSession().get(StipCard.class, pk);
    }

}

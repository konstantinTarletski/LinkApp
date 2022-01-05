package lv.bank.cards.core.rtps.impl;

import lv.bank.cards.core.entity.rtps.CardsException;
import lv.bank.cards.core.rtps.dao.CardsExceptionsDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CardsExceptionsDAOHibernate extends BaseDAOHibernate implements CardsExceptionsDAO {

    @Override
    public CardsException findByCardNumber(String cardNumber) throws DataIntegrityException {
        @SuppressWarnings("unchecked")
        List<CardsException> c = sf.getCurrentSession().createCriteria(CardsException.class)
                .add(Restrictions.eq("cardNumber", cardNumber)).list();

        if (c.size() == 0)
            return null;
        if (c.size() == 1)
            return c.get(0);
        else
            throw new DataIntegrityException("Found " + c.size() + " records in CardsExceptions. Looks strange");
    }

}

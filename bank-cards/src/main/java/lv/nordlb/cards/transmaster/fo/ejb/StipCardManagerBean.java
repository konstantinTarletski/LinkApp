package lv.nordlb.cards.transmaster.fo.ejb;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.rtps.StipCard;
import lv.bank.cards.core.entity.rtps.StipRmsStoplist;
import lv.bank.cards.core.rtps.dao.CardDAO;
import lv.bank.cards.core.rtps.dao.CardsExceptionsDAO;
import lv.bank.cards.core.rtps.dao.StipRmsStoplistDAO;
import lv.bank.cards.core.rtps.impl.CardDAOHibernate;
import lv.bank.cards.core.rtps.impl.CardsExceptionsDAOHibernate;
import lv.bank.cards.core.rtps.impl.StipRmsStoplistDAOHibernate;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.nordlb.cards.transmaster.fo.interfaces.StipCardManager;

import javax.ejb.Stateless;
import java.util.List;

@Slf4j
@Stateless
public class StipCardManagerBean implements StipCardManager {

    CardDAO cardDAO = null;
    CardsExceptionsDAO cardsExceptionsDAO = null;
    StipRmsStoplistDAO stipRmsStoplistDAO = null;

    public StipCardManagerBean() {
        cardDAO = new CardDAOHibernate();
        cardsExceptionsDAO = new CardsExceptionsDAOHibernate();
        stipRmsStoplistDAO = new StipRmsStoplistDAOHibernate();
    }

    /**
     * Initialize class for JUnit tests
     */
    protected StipCardManagerBean(boolean test) {
    }

    public StipCard findStipCard(String cardNumber) {
        try {
            return cardDAO.findByCardNumber(cardNumber);
        } catch (DataIntegrityException e) {
            log.info(e.getMessage());
        }
        return null;
    }

    public StipCard loadStipCard(String cardNumber, String centreId) {
        return cardDAO.load(cardNumber, centreId);
    }

    @Override
    public List<StipRmsStoplist> getStipRmsStoplist(String card, String centreId, Long priority) {
        return stipRmsStoplistDAO.getStipRmsStoplist(card, centreId, priority);
    }

}

package lv.nordlb.cards.transmaster.fo.ejb;

import lv.bank.cards.core.rtps.dao.CardDAO;
import lv.bank.cards.core.rtps.dao.CardsExceptionsDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StipCardManagerBeanTest {

    private CardDAO cardDAO = mock(CardDAO.class);
    private CardsExceptionsDAO cardsExceptionsDAO = mock(CardsExceptionsDAO.class);

    private StipCardManagerBean manager = new StipCardManagerBean(true);

    @Before
    public void initTest() {
        manager.cardDAO = cardDAO;
        manager.cardsExceptionsDAO = cardsExceptionsDAO;
    }

    @Test
    public void findStipCard() throws DataIntegrityException {
        manager.findStipCard("card");
        verify(cardDAO).findByCardNumber("card");
    }

    @Test
    public void loadStipCard() {
        manager.loadStipCard("card", "centre");
        verify(cardDAO).load("card", "centre");
    }

}

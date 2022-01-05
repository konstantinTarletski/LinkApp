package lv.bank.cards.soap.handlers;

import lv.bank.cards.core.entity.rtps.CardsException;
import lv.bank.cards.core.entity.rtps.StipCard;
import lv.bank.cards.core.entity.rtps.StipStoplist;
import lv.bank.cards.core.rtps.dao.CardDAO;
import lv.bank.cards.core.rtps.dao.CardsExceptionsDAO;
import lv.bank.cards.core.rtps.impl.CardDAOHibernate;
import lv.bank.cards.core.rtps.impl.CardsExceptionsDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.exceptions.RequestProcessingSoftException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;

import java.util.Iterator;

public class CardStatus extends SubRequestHandler {

    protected CardsExceptionsDAO cardsExceptionsDAO;
    protected CardDAO cardDAO;
    protected CardService helper;

    public CardStatus() {
        super();
        helper = new CardService();
        cardsExceptionsDAO = new CardsExceptionsDAOHibernate();
        cardDAO = new CardDAOHibernate();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String card = getStringFromNode("/do/card");
        if (!CardUtils.cardCouldBeValid(card))
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);

        StipCard thisCard = null;
        StipStoplist stipStoplist = null;
        CardsException cardException;
        String centreId = helper.getCentreIdByCard(card);
        if (centreId != null) thisCard = cardDAO.load(card, centreId);

        if (thisCard == null)
            throw new RequestProcessingSoftException(ErrorConstants.cantFindCard, this);

        Iterator<?> i = thisCard.getStipStoplist().iterator();
        if (i.hasNext()) stipStoplist = (StipStoplist) i.next();


        try {
            cardException = cardsExceptionsDAO.findByCardNumber(card);
        } catch (DataIntegrityException e) {
            throw new RequestProcessingException(e.getMessage(), this);
        }

        ResponseElement cardElement = createElement("card-status");

        cardElement.createElement("card", card);
        cardElement.createElement("stip-card-status", thisCard.getAnswerCodeByStatCode1().getActionCode());

        ResponseElement work = cardElement.createElement("stip-stoplist");

        if (stipStoplist != null) {
            work.createElement("answer-code", stipStoplist.getAnswerCode().getActionCode());
            work.createElement("description", stipStoplist.getDescription());
        }

        work = cardElement.createElement("card-exception");

        if (cardException != null) {
            work.createElement("answer-code", cardException.getAnswerCode().getActionCode());
            work.createElement("description", cardException.getDescription());
        }
    }
}

package lv.bank.cards.soap.handlers;

import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.impl.CardDAOHibernate;
import lv.bank.cards.core.entity.cms.IzdStoplist;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;

public class CardStatusBO extends SubRequestHandler {

    protected CardDAO cardDAO;
    protected CardsDAO cardsDAO;

    public CardStatusBO() {
        super();
        cardDAO = new CardDAOHibernate();
        cardsDAO = new CardsDAOHibernate();
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String card = getStringFromNode("/do/card");

        if (!CardUtils.cardCouldBeValid(card)){
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);
        }

        PcdCard thisCard = cardsDAO.findByCardNumber(card);

        if (thisCard == null){
            throw new RequestProcessingException(ErrorConstants.cantFindCard, this);
        }

        IzdStoplist stopList = (IzdStoplist) cardDAO.getObject(IzdStoplist.class, card);

        ResponseElement cardElement = createElement("card-status-bo");

        cardElement.createElement("card", card);
        cardElement.createElement("card-status-1", thisCard.getStatus1());
        cardElement.createElement("card-status-2", thisCard.getStatus2());
        ResponseElement work = cardElement.createElement("stoplist");

        if (stopList != null) {
            work.createElement("answer-code", stopList.getIzdStopCause().getStatusCode());
            work.createElement("cause", stopList.getIzdStopCause().getComp_id().getCause());
            work.createElement("description", stopList.getText());
        }
    }
}

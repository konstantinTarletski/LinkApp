package lv.bank.cards.soap.handlers;

import lombok.Setter;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.exceptions.RequestProcessingSoftException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

/**
 * @author dobicinaitis
 */
public class CardInfoAcs extends SubRequestHandler {
    @Setter
    private CardsDAO cardsDAO;

    public CardInfoAcs() throws RequestPreparationException {
        super();
        cardsDAO = new CardsDAOHibernate();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String card = getStringFromNode("/do/card");

        if (card == null) {
            throw new RequestFormatException("Specify card tag", this);
        }

        if (!CardUtils.cardCouldBeValid(card)) {
            throw new RequestProcessingSoftException(ErrorConstants.invalidCardNumber, this);
        }

        PcdCard pcdCard = cardsDAO.findByCardNumber(card);

        ResponseElement cardInfo = createElement("card-info-acs");

        if (pcdCard != null) {
            cardInfo.createElement("person-code-card-holder", pcdCard.getIdCard());
            cardInfo.createElement("country", pcdCard.getRegion());
            cardInfo.createElement("product-name", cardsDAO.getProductNameByCard(pcdCard.getCard()));
        }
    }
}


package lv.bank.cards.soap.handlers;

import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

import java.util.List;

public class CardData extends SubRequestHandler {

    protected CardsDAO cardsDAO;

    public CardData() {
        super();
        cardsDAO = new CardsDAOHibernate();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        List<String> cards = getStringListFromNode("/do/card");
        List<String> accounts = getStringListFromNode("/do/account");

        if (cards.size() == 0 && accounts.size() == 0)
            throw new RequestFormatException("Specify either source either account tags", this);
        List<Object[]> result = cardsDAO.findCardData(cards, accounts);

        ResponseElement response = createElement("card-data");
        for (Object tuple : result) {
            if (tuple instanceof Object[]) {
                Object[] row = (Object[]) tuple;
                ResponseElement record = response.createElement("record");
                record.createElement("account", (String) row[1]);
                record.createElement("card", (String) row[2]);
                record.createElement("expiry", (String) row[3]);
                record.createElement("cvc2", (String) row[4]);
            }
        }
    }
}

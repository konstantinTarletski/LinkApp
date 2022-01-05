package lv.bank.cards.soap.handlers.lv;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import org.apache.commons.lang.StringUtils;

public class SetChipTagHandler extends SubRequestHandler {

    protected CardsDAO cardsDAO;
    protected CMSCallAPIWrapper wrap;

    public SetChipTagHandler() {
        super();
        cardsDAO = new CardsDAOHibernate();
        wrap = new CMSCallAPIWrapper();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String cardString = getStringFromNode("/do/card");
        String chip = getStringFromNode("/do/chip-tag");
        String value = getStringFromNode("/do/value");

        if (StringUtils.isBlank(cardString)) {
            throw new RequestFormatException("Missing card tag");
        }
        if (StringUtils.isBlank(chip)) {
            throw new RequestFormatException("Missing chip tag");
        }
        if (StringUtils.isBlank(value)) {
            throw new RequestFormatException("Missing value tag");
        }

        PcdCard card = cardsDAO.findByCardNumber(cardString);

        if (card == null) {
            throw new RequestFormatException("Cannot find card " + cardString);
        }

        try {
            wrap.setChipTagValue(card.getCard(), chip, value);
        } catch (CMSCallAPIException e) {
            throw new RequestProcessingException(e.getMessage(), this);
        }

        createElement("set-chip-tag-value", "done");
    }
}

package lv.bank.cards.soap.handlers.lt;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIWrapper;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.exceptions.RequestProcessingRTPSAPIExtendedException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ActivateCard extends SubRequestHandler {

    protected RTPSCallAPIWrapper mainWrapper;
    protected CardsDAO cardsDAO;
    protected CardService helper;

    public ActivateCard() {
        super();
        mainWrapper = new RTPSCallAPIWrapper();
        helper = new CardService();
        cardsDAO = new CardsDAOHibernate();
    }

    /**
     * WARNING !!! If something changes here probably must change also in
     * {@link lv.nordlb.cards.transmaster.requests.handlers.rtps.RemoveCardFromStoplist#RemoveCardFromStoplist()}
     */
    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String centre_id = getStringFromNode("/do/centre-id");
        String card_number = getStringFromNode("/do/card");
        String rule_expr = getStringFromNode("/do/rule-expr");
        String priority = getStringFromNode("/do/priority");
        String action_code = getStringFromNode("/do/action-code");
        String description = getStringFromNode("/do/description");
        String operator = getAttributeByName("/do/@operator");


        ResponseElement activateCard = createElement("activate-card");
        if (!CardUtils.cardCouldBeValid(card_number))
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);

        if (centre_id == null) centre_id = helper.getCentreIdByCard(card_number);
        PcdCard thisCard = cardsDAO.findByCardNumber(card_number);

        if (thisCard == null || centre_id == null) {
            throw new RequestFormatException(ErrorConstants.cantFindCard, this);
        }
        //Write ExpiryDate into RuleExpr (kinda ugly)
        DateFormat formatter = new SimpleDateFormat("yyMM");
        rule_expr = rule_expr.replace("FLD_014==''", "FLD_014=='" + formatter.format(thisCard.getExpiry2() == null ? thisCard.getExpiry1() : thisCard.getExpiry2()) + "'");

        Integer parsedPriority;
        if (priority != null) {
            parsedPriority = Integer.valueOf(priority);
            if (parsedPriority.intValue() >= 0) throw new RequestProcessingException("Incorrect priority value", this);
        } else {
            parsedPriority = null;
        }
        try {
            thisCard.setDeliveryBlock("6");
            thisCard.setIssuedBy(operator);
            cardsDAO.saveOrUpdate(thisCard);
            mainWrapper.RemoveCardFromRMSStop(centre_id, card_number, rule_expr, parsedPriority, action_code, description);
            activateCard.createElement("activated", card_number);
        } catch (RTPSCallAPIException e) {
            throw new RequestProcessingRTPSAPIExtendedException(e, this);
        }
    }
}



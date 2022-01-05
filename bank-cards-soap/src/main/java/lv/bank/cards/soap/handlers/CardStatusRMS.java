package lv.bank.cards.soap.handlers;


import lv.bank.cards.core.entity.rtps.StipRmsStoplist;
import lv.bank.cards.core.rtps.dao.StipRmsStoplistDAO;
import lv.bank.cards.core.rtps.impl.StipRmsStoplistDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.exceptions.RequestProcessingSoftException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;

import java.util.List;

public class CardStatusRMS extends SubRequestHandler {

    protected CardService helper;
    protected StipRmsStoplistDAO stipRmsStoplistDAO;

    public CardStatusRMS() {
        super();
        helper = new CardService();
        stipRmsStoplistDAO = new StipRmsStoplistDAOHibernate();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String card = getStringFromNode("/do/card");
        if (!CardUtils.cardCouldBeValid(card))
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);

        String centreId = helper.getCentreIdByCard(card);
        if (centreId == null)
            throw new RequestProcessingSoftException(ErrorConstants.cantFindCard, this);

        List<StipRmsStoplist> list = stipRmsStoplistDAO.getStipRmsStoplist(card, centreId, null);

        ResponseElement cardElement = createElement("card-status-rms");
        cardElement.createElement("card", card);

        for (StipRmsStoplist rms : list) {
            ResponseElement entry = cardElement.createElement("entry");
            entry.createElement("centre-id", rms.getCompId().getCentreId());
            entry.createElement("card", rms.getCompId().getCardNumber());
            if (rms.getEffectiveDate() != null)
                entry.createElement("effective-date", r.getDateFormat().format(rms.getEffectiveDate()));
            else entry.createElement("effective-date");
            if (rms.getUpdateDate() != null)
                entry.createElement("update-date", r.getDateFormat().format(rms.getUpdateDate()));
            else entry.createElement("update-date");
            if (rms.getPurgeDate() != null)
                entry.createElement("purge-date", r.getDateFormat().format(rms.getPurgeDate()));
            else entry.createElement("purge-date");
            entry.createElement("action-code", rms.getAnswerCode() != null ? rms.getAnswerCode().getActionCode() : "");
            entry.createElement("description", rms.getDescription());
            entry.createElement("rule-expression", rms.getRuleExpr());
            entry.createElement("priority", rms.getCompId().getPriority().toString());
        }
    }
}

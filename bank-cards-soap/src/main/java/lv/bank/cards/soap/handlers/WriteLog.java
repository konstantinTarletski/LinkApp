package lv.bank.cards.soap.handlers;

import lv.bank.cards.core.linkApp.dao.PcdLogDAO;
import lv.bank.cards.core.linkApp.impl.PcdLogDAOHibernate;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;


public class WriteLog extends SubRequestHandler {

    protected PcdLogDAO pcdLogDAO;

    public WriteLog() {
        super();
        pcdLogDAO = new PcdLogDAOHibernate();
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String source = getStringFromNode("/do/source");
        String operation = getStringFromNode("/do/operation");
        String operator = getStringFromNode("/do/operator");
        String text = getStringFromNode("/do/text");
        String result = getStringFromNode("/do/result");

        if (source == null)
            throw new RequestFormatException("Specify source tag", this);
        if (operation == null)
            throw new RequestFormatException("Specify operation tag", this);
        if (operator == null)
            throw new RequestFormatException("Specify operator tag", this);
        if (result == null)
            throw new RequestFormatException("Specify result tag", this);

        Long newLogId = pcdLogDAO.write(source, operation, operator, text, result);
        ResponseElement response = createElement("write-log");
        response.createElement("log-id", newLogId.toString());
    }
}

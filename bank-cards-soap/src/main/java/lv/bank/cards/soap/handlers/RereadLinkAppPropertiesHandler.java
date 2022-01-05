package lv.bank.cards.soap.handlers;

import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;


public class RereadLinkAppPropertiesHandler extends SubRequestHandler {

    public RereadLinkAppPropertiesHandler() {
        super();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        LinkAppProperties.resetInitializingValues();
    }
}

package lv.bank.cards.soap.exceptions;

import lv.bank.cards.soap.requests.SubRequestHandler;

public class RequestPreparationException extends ResponseHoldingException {

    private static final long serialVersionUID = 1L;

    public RequestPreparationException(Throwable arg0, SubRequestHandler caller) {
        super(arg0, caller);
    }

    public RequestPreparationException(String arg0, SubRequestHandler caller) {
        super(arg0, caller);
    }

    public RequestPreparationException() {
        super();
    }

    public RequestPreparationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public RequestPreparationException(String arg0) {
        super(arg0);
    }

    public RequestPreparationException(Throwable arg0) {
        super(arg0);
    }

}

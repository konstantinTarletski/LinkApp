package lv.bank.cards.soap.exceptions;

import lv.bank.cards.soap.requests.SubRequestHandler;

public class RequestProcessingException extends ResponseHoldingException {

    private static final long serialVersionUID = 1L;

    public RequestProcessingException(Throwable arg0, SubRequestHandler caller) {
        super(arg0, caller);
    }

    public RequestProcessingException() {
        super();
    }

    public RequestProcessingException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public RequestProcessingException(String arg0) {
        super(arg0);
    }

    public RequestProcessingException(String arg0, SubRequestHandler caller) {
        super(arg0, caller);
    }

    public RequestProcessingException(Throwable arg0) {
        super(arg0);
    }

}

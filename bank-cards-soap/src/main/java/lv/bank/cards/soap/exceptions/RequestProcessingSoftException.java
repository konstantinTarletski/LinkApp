package lv.bank.cards.soap.exceptions;

import lv.bank.cards.soap.requests.SubRequestHandler;

public class RequestProcessingSoftException extends RequestProcessingException {

    private static final long serialVersionUID = 1L;

    public RequestProcessingSoftException(Throwable arg0, SubRequestHandler caller) {
        super(arg0, caller);
    }

    public RequestProcessingSoftException() {
        super();
    }

    public RequestProcessingSoftException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public RequestProcessingSoftException(String arg0) {
        super(arg0);
    }

    public RequestProcessingSoftException(String arg0, SubRequestHandler caller) {
        super(arg0, caller);
    }

    public RequestProcessingSoftException(Throwable arg0) {
        super(arg0);
    }

}

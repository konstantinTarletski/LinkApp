package lv.bank.cards.core.vendor.api.cms.db;

public class CallAPIWrapperException extends Exception {

    private static final long serialVersionUID = 1L;

    public CallAPIWrapperException() {
        super();
    }

    public CallAPIWrapperException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public CallAPIWrapperException(Throwable arg0) {
        super(arg0);
    }

    public CallAPIWrapperException(String message) {
        super(message);
    }

}

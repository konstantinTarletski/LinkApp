package lv.bank.cards.auth;

public class AuthorisationException extends Exception {

    private static final long serialVersionUID = -6687057802271679223L;

    public AuthorisationException() {
        super();
    }

    public AuthorisationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public AuthorisationException(String arg0) {
        super(arg0);
    }

    public AuthorisationException(Throwable arg0) {
        super(arg0);
    }

}

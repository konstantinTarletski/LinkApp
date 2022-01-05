package lv.bank.cards.core.vendor.api.rtps.db.types;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TAuthItemException extends Exception {

    private static final long serialVersionUID = 1L;

    public TAuthItemException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public TAuthItemException(Throwable arg0) {
        super(arg0);
    }

    public TAuthItemException(String message) {
    }

}

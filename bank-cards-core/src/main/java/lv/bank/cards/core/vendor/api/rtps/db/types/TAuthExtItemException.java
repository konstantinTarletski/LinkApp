package lv.bank.cards.core.vendor.api.rtps.db.types;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TAuthExtItemException extends Exception {

    private static final long serialVersionUID = 1L;

    public TAuthExtItemException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public TAuthExtItemException(Throwable arg0) {
        super(arg0);
    }

    public TAuthExtItemException(String message) {
    }

}

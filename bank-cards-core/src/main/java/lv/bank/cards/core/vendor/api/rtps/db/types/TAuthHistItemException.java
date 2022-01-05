package lv.bank.cards.core.vendor.api.rtps.db.types;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TAuthHistItemException extends Exception {

    private static final long serialVersionUID = 1L;

    public TAuthHistItemException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public TAuthHistItemException(Throwable arg0) {
        super(arg0);
    }

    public TAuthHistItemException(String message) {
    }

}

package lv.bank.cards.core.vendor.api.rtps.db.types;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TRMSStopListItemException extends Exception {

    private static final long serialVersionUID = 1L;

    public TRMSStopListItemException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public TRMSStopListItemException(Throwable arg0) {
        super(arg0);
    }

    public TRMSStopListItemException(String message) {
        super(message);
    }

}

package lv.bank.cards.core.vendor.api.cms.db;

import lombok.NoArgsConstructor;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.vendor.api.cms.db.types.TLastError;

@NoArgsConstructor
public class CMSCallAPIException extends CallAPIWrapperException {

    private static final long serialVersionUID = 1L;

    private TLastError lastErr = new TLastError();

    public CMSCallAPIException(TLastError lastErr) {

        super("Error \n " +
                "Error code: " + lastErr.getErrorCode() + "\n" +
                "Error cause: " + CardUtils.maskCardNumber(lastErr.getErrorCause()) + "\n" +
                "Action: " + lastErr.getAction());
        this.lastErr = lastErr;
    }

    public CMSCallAPIException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public CMSCallAPIException(Throwable arg0) {
        super(arg0);
    }

    public CMSCallAPIException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return this.lastErr.getErrorCode();
    }

    public String getErrorCause() {
        return CardUtils.maskCardNumber(this.lastErr.getErrorCause());
    }

    public String getAction() {
        return this.lastErr.getAction();
    }

}

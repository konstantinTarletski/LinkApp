package lv.nordlb.cards.transmaster.requests.handlers.rtps;

import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequestHandler;

public class RequestProcessingRTPSAPIExtendedException extends RequestProcessingException {

    private static final long serialVersionUID = 1L;
    private RTPSCallAPIException e = null;

    public ResponseElement compileResponseElement() {
        ResponseElement retData = super.compileResponseElement();

        if (e != null) {
            ResponseElement excep = getExceptionElement();
            excep.createElement("errorcode", Integer.toString(e.getErrorCode()));
            excep.createElement("oraerrcode", Integer.toString(e.getOraErrCode()));

            if (e.getErrorText() != null) {
                excep.createElement("errortext", e.getErrorText());
            } else {
                excep.createElement("errortext", "null");
            }
        }
        return retData;
    }

    public RequestProcessingRTPSAPIExtendedException(Throwable arg0, SubRequestHandler caller) {
        super(arg0, caller);

        if (arg0 instanceof RTPSCallAPIException)
            e = (RTPSCallAPIException) arg0;
    }

}

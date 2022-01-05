package lv.bank.cards.soap.exceptions;

import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequestHandler;


public class RequestProcessingRTPSAPIExtendedException extends RequestProcessingException {

    private static final long serialVersionUID = 1L;
    protected RTPSCallAPIException e = null;

    @Override
    public ResponseElement compileResponseElement() {
        ResponseElement retData = super.compileResponseElement();

        if (e != null) {
            ResponseElement excep = getExceptionElement();
            if (Integer.toString(e.getErrorCode()) != null) {
                excep.createElement("errorcode", Integer.toString(e.getErrorCode()));
            } else excep.createElement("errorcode", "0");
            if (Integer.toString(e.getOraErrCode()) != null) {
                excep.createElement("oraerrcode", Integer.toString(e.getOraErrCode()));
            } else excep.createElement("oraerrcode", "0");
            if (e.getErrorText() != null) {
                excep.createElement("errortext", e.getErrorText());
            } else excep.createElement("errortext", "null");
        }
        return retData;
    }

    public RequestProcessingRTPSAPIExtendedException(Throwable arg0, SubRequestHandler caller) {
        super(arg0, caller);

        System.out.println("RequestProcessingRTPSAPIExtendedException(" + arg0.getClass().getName());

        if (arg0 instanceof RTPSCallAPIException)
            e = (RTPSCallAPIException) arg0;
    }

}

package lv.bank.cards.soap.exceptions;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequestHandler;

@Slf4j
public class RequestProcessingCMSAPIExtendedException extends RequestProcessingException {

	private static final long serialVersionUID = 1L;
	private CMSCallAPIException e = null;

	@Override
	public ResponseElement compileResponseElement() {

		ResponseElement retData = super.compileResponseElement();
		if (e != null) {
			ResponseElement excep = getExceptionElement();
			if (e.getErrorCode()!=null){
				excep.createElement("errorcode", e.getErrorCode());
			} else excep.createElement("errorcode", "0");
			if (e.getErrorCause()!=null){
				excep.createElement("errorcause", e.getErrorCause());
			} else excep.createElement("oraerrcode", "0");
			if (e.getAction()!=null){
				excep.createElement("action", e.getAction());
			} else excep.createElement("errortext", "null");
		}
		return retData;
	}

	public RequestProcessingCMSAPIExtendedException(Throwable arg0, SubRequestHandler caller) {
		super(arg0, caller);
		log.info("RequestProcessingCMSAPIExtendedException("+arg0.getClass().getName());
		
		if (arg0 instanceof CMSCallAPIException){
			e=(CMSCallAPIException) arg0;
		}
	}

}

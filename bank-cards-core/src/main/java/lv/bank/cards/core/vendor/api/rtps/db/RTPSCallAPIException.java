package lv.bank.cards.core.vendor.api.rtps.db;

import lv.bank.cards.core.vendor.api.cms.db.CallAPIWrapperException;
import lv.bank.cards.core.vendor.api.rtps.db.types.TLastError;

public class RTPSCallAPIException extends CallAPIWrapperException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TLastError lastErr = new TLastError();
	
	public int getErrorCode(){
		return this.lastErr.getErrorCode();
	}
	
	public int getOraErrCode(){
		return this.lastErr.getOraErrCode();
	}
	
	public String getErrorText(){
		return maskCardNumber(this.lastErr.getErrorText());
	}
	
	public RTPSCallAPIException() {
		super();
	}
	
	public RTPSCallAPIException(TLastError lastErr) {
		super("Error \n " +
			"Error code: " + lastErr.getErrorCode() + "\n"+
			"OraError code: " + lastErr.getOraErrCode() + "\n"+
			"Error text: " + maskCardNumber(lastErr.getErrorText()));
		this.lastErr=lastErr;
	}

	public RTPSCallAPIException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RTPSCallAPIException(Throwable arg0) {
		super(arg0);
	}

	public RTPSCallAPIException(String message) {
	}

	public static String maskCardNumber(String text){
		return text.replaceAll("([^\\d]|^)(\\d{6})\\d{6}(\\d{4})([^\\d]|$)", "$1$2******$3$4");
	}
}

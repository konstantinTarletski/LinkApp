package lv.bank.cards.soap.exceptions;

import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.requests.SubResponse;

public abstract class ResponseHoldingException extends Exception {

	private static final long serialVersionUID = 1L;

	public static interface constants {
		public static final String exception = "exception";
		public static final String text = "text";
		public static final String type = "type";
	}
	protected SubRequestHandler caller = null;
	protected ResponseElement responseRoot = null;
	protected ResponseElement exceptionHolder = null;
	
	protected ResponseElement getExceptionElement() {
		if (exceptionHolder != null) return exceptionHolder;
		
		SubResponse sr = null;
		if ((caller != null) && ((sr = caller.compileResponse())!= null))
			responseRoot=sr;
		else
			responseRoot = new ResponseElement(false);
		return responseRoot.createElement(constants.exception);
	}
	public ResponseElement compileResponseElement() {
		exceptionHolder = getExceptionElement();
		exceptionHolder.createElement(constants.text, getMessage());
		exceptionHolder.createElement(constants.type, this.getClass().getSimpleName());
		return responseRoot;
	}
	
	public ResponseHoldingException() {
		super();
	}

	public ResponseHoldingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	public ResponseHoldingException(String arg0, SubRequestHandler caller) {
		super(arg0);
		this.caller=caller;
	}
	public ResponseHoldingException(Throwable arg0, SubRequestHandler caller) {
		super(arg0);
		this.caller=caller;
	}

	public ResponseHoldingException(String arg0) {
		super(arg0);
	}

	public ResponseHoldingException(Throwable arg0) {
		super(arg0);
	}
}

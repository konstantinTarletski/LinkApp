package lv.bank.cards.soap.exceptions;

import lv.bank.cards.soap.requests.SubRequestHandler;

public class RequestFormatException extends ResponseHoldingException {

	private static final long serialVersionUID = 1L;

	public RequestFormatException(String arg0, SubRequestHandler caller) {
		super(arg0, caller);
	}

	public RequestFormatException() {
		super();
	}

	public RequestFormatException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RequestFormatException(String arg0) {
		super(arg0);
	}

	public RequestFormatException(Throwable arg0) {
		super(arg0);
	}

}

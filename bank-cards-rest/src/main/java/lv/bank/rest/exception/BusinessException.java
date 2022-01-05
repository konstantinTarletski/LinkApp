package lv.bank.rest.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private JsonErrorCode errorCode;
	
	private String target;

	private BusinessException(JsonErrorCode errorCode, String message, String target) {
		super(message);
		this.errorCode = errorCode;
		this.target = target;
	}
	
	private BusinessException(JsonErrorCode errorCode, String message, String target, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.target = target;
	}
	
	public static BusinessException create(JsonErrorCode errorCode, String target, String message) {
		return new BusinessException(errorCode, message, target);
	}
	
	public static BusinessException create(JsonErrorCode errorCode, String target, String message, Object ... arguments) {
		return new BusinessException(errorCode, String.format(message, arguments), target);
	}
	
	public static BusinessException create(JsonErrorCode errorCode, String target, Throwable cause, String message) {
		return new BusinessException(errorCode, message, target, cause);
	}
	
	public static BusinessException create(JsonErrorCode errorCode, String target, Throwable cause, String message, Object ... arguments) {
		return new BusinessException(errorCode, String.format(message, arguments), target, cause);
	}

	public JsonErrorCode getErrorCode() {
		return errorCode;
	}

	public String getTarget() {
		return target;
	}
}

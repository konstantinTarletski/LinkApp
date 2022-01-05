package lv.bank.rest.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JsonErrorCode {

	BAD_REQUEST(400),
	INVALID_CARD_NUMBER(400),
	FORBIDDEN(403),
	NOT_FOUND(404),
	CARD_NOT_FOUND(404),
	ACCOUNT_NOT_FOUND(404),
	CARDS_NOT_FOUND(404),
	CUSTOMER_NOT_FOUND(404),
	DATA_CONFLICT(409),
	APPLICATION_ERROR(500);

	@Getter
	private final int statusCode;
	
	public String getCode() {
		return name().toLowerCase();
	}
}

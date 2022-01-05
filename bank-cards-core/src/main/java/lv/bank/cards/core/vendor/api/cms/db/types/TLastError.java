package lv.bank.cards.core.vendor.api.cms.db.types;

import lombok.Data;

@Data
public class TLastError {

    private String errorCode;
	private String errorCause;
	private String action;
	
}

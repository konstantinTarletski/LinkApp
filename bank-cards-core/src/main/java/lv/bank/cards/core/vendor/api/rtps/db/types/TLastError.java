package lv.bank.cards.core.vendor.api.rtps.db.types;

import lombok.Data;

@Data
public class TLastError {

    private int errorCode;
    private int oraErrCode;
    private String errorText;

}

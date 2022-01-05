package lv.bank.cards.core.vendor.api.cms.soap;

import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.vendor.api.cms.db.CallAPIWrapperException;

public class CMSSoapAPIException extends CallAPIWrapperException {

    public CMSSoapAPIException(String message) {
        super(CardUtils.maskCardNumber(message));
    }

}

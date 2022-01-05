package lv.nordlb.cards.transmaster.requests.handlers.cms;

import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;

public class ActivateDormantAccountHandler extends SubRequestHandler {

    protected CardManager cardManager;
    protected CMSSoapAPIWrapper soapAPIWrapper;

    public ActivateDormantAccountHandler() throws RequestPreparationException {
        super();
        try {
            cardManager = (CardManager) new InitialContext().lookup(CardManager.JNDI_NAME);
            soapAPIWrapper = (CMSSoapAPIWrapper) new InitialContext().lookup(CMSSoapAPIWrapper.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String account_no = getStringFromNode("/do/account");
        String comment = getStringFromNode("/do/comment");
        String ccy = getStringFromNode("/do/ccy");

        IzdAccount thisAccount = cardManager.getIzdAccountByAccountNr(new BigDecimal(account_no));
        if (thisAccount == null)
            throw new RequestProcessingException(ErrorConstants.invalidAccNr, this);

        if (!ccy.equals(thisAccount.getIzdAccParam().getIzdCardGroupCcy().getComp_id().getCcy().trim()))
            throw new RequestProcessingException(ErrorConstants.invalidCCY, this);

        int AccountStatus = Integer.parseInt(thisAccount.getIzdAccParam().getStatus());
        if (AccountStatus != 3)
            throw new RequestProcessingException("Can activate only dormant account", this);

        try {
            soapAPIWrapper.activateAccount(thisAccount.getCardAcct(), ccy, comment);
        } catch (CMSSoapAPIException e) {
            throw new RequestProcessingException(e.getMessage(), this);
        }

        createElement("activate-dormant", "done");
    }
}

package lv.nordlb.cards.transmaster.requests.handlers.cms;

import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;

public class ExecuteTransaction extends SubRequestHandler {

    protected CardManager cardManager;
    protected CMSSoapAPIWrapper soapAPIWrapper;

    public ExecuteTransaction() throws RequestPreparationException {
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
        String accountNo = getStringFromNode("/do/card-account");
        String amount = getStringFromNode("/do/amount");
        String tranCcy = getStringFromNode("/do/currency");
        String tranType = getStringFromNode("/do/tr-type");
        String dealDesc = getStringFromNode("/do/description");

        IzdAccount thisAccount = cardManager.getIzdAccountByAccountNr(BigDecimal.valueOf(Long.parseLong(accountNo)));
        if (thisAccount == null)
            throw new RequestProcessingException(ErrorConstants.invalidAccNr, this);

        if (!tranCcy.equals(thisAccount.getIzdAccParam().getIzdCardGroupCcy().getComp_id().getCcy().trim()))
            throw new RequestProcessingException(ErrorConstants.invalidCCY, this);

        if (tranType == null) {
            throw new RequestProcessingException(ErrorConstants.noTransactonType, this);
        }

        int AccountStatus = Integer.parseInt(thisAccount.getIzdAccParam().getStatus());
        if (AccountStatus != 0) {
            throw new RequestProcessingException(ErrorConstants.inactiveAccount, this);
        }

        BigDecimal internalNo;
        try {
            internalNo = soapAPIWrapper.executeTransaction("3", accountNo, tranType, tranCcy, amount, "", dealDesc);
        } catch (CMSSoapAPIException e) {
            throw new RequestProcessingException(e.getMessage(), this);
        }

        ResponseElement response = createElement("internal-no", internalNo.toBigInteger().toString());
    }
}

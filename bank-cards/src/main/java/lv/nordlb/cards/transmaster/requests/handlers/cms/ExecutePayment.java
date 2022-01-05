/*
 * Created on 2005.10.6
 */
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

public class ExecutePayment extends SubRequestHandler {

    protected CardManager cardManager;
    protected CMSSoapAPIWrapper soapAPIWrapper;

    public ExecutePayment() throws RequestPreparationException {
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
        String accountNo = getStringFromNode("/do/account");
        String amount = getStringFromNode("/do/amt");
        String dealDesc = getStringFromNode("/do/description");
        String tranCcy = getStringFromNode("/do/ccy");
        String checkAmount = getStringFromNode("/do/chkamt");
        String tranType = getStringFromNode("/do/trtype");
        String slipNr = getStringFromNode("/do/slip");

        IzdAccount thisAccount = cardManager.getIzdAccountByAccountNr(BigDecimal.valueOf(Long.parseLong(accountNo)));
        if (thisAccount == null)
            throw new RequestProcessingException(ErrorConstants.invalidAccNr, this);

        if (!tranCcy.equals(thisAccount.getIzdAccParam().getIzdCardGroupCcy().getComp_id().getCcy().trim()))
            throw new RequestProcessingException(ErrorConstants.invalidCCY, this);

        if (checkAmount != null) {
            if (!(Long.parseLong(amount) == thisAccount.getEndBal()))
                throw new RequestProcessingException(ErrorConstants.invalidBalance, this);
        }
        if (tranType == null) {
            throw new RequestProcessingException(ErrorConstants.noTransactonType, this);
        }

        try {
            soapAPIWrapper.executeTransaction("0", accountNo, tranType, tranCcy, amount, slipNr, dealDesc);
        } catch (CMSSoapAPIException e) {
            throw new RequestProcessingException(e.getMessage(), this);
        }

        ResponseElement response = createElement("job");
        response.createElement("Executed", "1");
    }
}

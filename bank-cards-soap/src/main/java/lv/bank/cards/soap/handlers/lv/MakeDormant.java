package lv.bank.cards.soap.handlers.lv;

import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.impl.CardDAOHibernate;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;

public class MakeDormant extends SubRequestHandler {

    protected CardDAO cardDAO;
    protected CMSSoapAPIWrapper soapAPIWrapper;

    public MakeDormant() throws RequestPreparationException {
        super();
        cardDAO = new CardDAOHibernate();
        try {
            soapAPIWrapper = (CMSSoapAPIWrapper) new InitialContext().lookup(CMSSoapAPIWrapper.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String account_no = getStringFromNode("/do/account");
        String desc = getStringFromNode("/do/description");
        String ccy = getStringFromNode("/do/ccy");
        String feeCalculationMode = getStringFromNode("/do/interest");

        IzdAccount thisAccount = cardDAO.findIzdAccountByAcctNr(new BigDecimal(account_no));
        if (thisAccount == null)
            throw new RequestProcessingException(ErrorConstants.invalidAccNr, this);

        if (!ccy.equals(thisAccount.getIzdAccParam().getIzdCardGroupCcy().getComp_id().getCcy().trim()))
            throw new RequestProcessingException(ErrorConstants.invalidCCY, this);

        int AccountStatus = Integer.parseInt(thisAccount.getIzdAccParam().getStatus());
        if (AccountStatus != 0)
            throw new RequestProcessingException("Account [" + account_no + "] is already made dormant or closed", this);

        try {
            soapAPIWrapper.makeAccountDormant(thisAccount.getCardAcct(), ccy, desc, feeCalculationMode);
        } catch (CMSSoapAPIException e) {
            throw new RequestProcessingException(e.getMessage(), this);
        }

        createElement("make-dormant", "done");
    }
}

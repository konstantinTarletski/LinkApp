package lv.nordlb.cards.transmaster.requests.handlers.cms;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.linkApp.PcdAccParam;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;

@Slf4j
public class AccountCreditLimit extends SubRequestHandler {

    protected final CMSSoapAPIWrapper soapAPIWrapper;
    protected final PcdabaNGManager pcdabaNGManager;

    public AccountCreditLimit() throws RequestPreparationException {
        super();
        try {
            soapAPIWrapper = (CMSSoapAPIWrapper) new InitialContext().lookup(CMSSoapAPIWrapper.JNDI_NAME);
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    /* (non-Javadoc)
     * @see lv.bank.cards.soap.requests.SubRequestHandler#handle(lv.bank.cards.soap.requests.SubRequest)
     */
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String accountNoS = getStringFromNode("/do/account_no");
        BigDecimal accountNo = new BigDecimal(accountNoS);

        PcdAccount thisPcdAccount = pcdabaNGManager.getAccountByAccountNo(accountNo);
        if (thisPcdAccount == null) {
            throw new RequestProcessingException("Can't find account [" + accountNo + "]", this);
        }

        String newCrdS = getStringFromNode("/do/credit_limit");
        if (newCrdS != null) {
            Long newCrd = Long.parseLong(newCrdS);
            PcdAccParam pcdAccParam = thisPcdAccount.getPcdAccParam();

            try {
                log.info("handle editAccount");
                soapAPIWrapper.editAccount(thisPcdAccount.getAccountNo(), Integer.parseInt(newCrdS));
            } catch (CMSSoapAPIException e) {
                throw new RequestProcessingException(e.getMessage(), this);
            }

            pcdAccParam.setCrd(newCrd);
            pcdabaNGManager.saveOrUpdate(pcdAccParam);
        }
        //get credit limit
        createElement("account_no", accountNoS);
        createElement("credit_limit", String.valueOf(thisPcdAccount.getPcdAccParam().getCrd()));
        createElement("ccy", thisPcdAccount.getPcdAccParam().getPcdCurrency().getIsoAlpha());

    }
}

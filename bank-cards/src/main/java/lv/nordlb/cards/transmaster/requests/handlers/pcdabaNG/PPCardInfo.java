package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PPCardInfo extends SubRequestHandler {
    private PcdabaNGManager pcdabaNGManager = null;
    Logger log = Logger.getLogger(PPCardInfo.class);

    public PPCardInfo() throws RequestPreparationException {
        super();
        try {
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        PcdPpCard result = null;
        String cardNr = getStringFromNode("/do/card");
        String ppCardNr = getStringFromNode("/do/pp-card");
        if (StringUtils.isBlank(cardNr) && StringUtils.isBlank(ppCardNr))
            throw new RequestFormatException("Specify card number", this);

        if (!StringUtils.isBlank(cardNr))
            result = pcdabaNGManager.getPPCardInfoByCreditCard(cardNr);
        if (!StringUtils.isBlank(ppCardNr) && result == null) {
            result = pcdabaNGManager.getPPCardInfoByCardNumber(ppCardNr);
            if (!ppCardNr.equals(result.getFullCardNr()))
                result = null;
        }
        if (result == null) throw
                new RequestFormatException(ErrorConstants.cantFindCard, this);
        String accountNr = null;
        if (result.getPcdCard().getPcdAccounts() != null && !result.getPcdCard().getPcdAccounts().isEmpty()) {
            PcdAccount acc = result.getPcdCard().getPcdAccounts().iterator().next();
            accountNr = acc.getPcdAccParam().getUfield5();
        }

        ResponseElement response = createElement("get-pp-info");
        response.createElement("card", (result.getFullCardNr()));
        response.createElement("status", (result.getStatus()).toPlainString());
        if (accountNr != null) {
            response.createElement("cbs-account-no", accountNr);
        }
        if ((result.getStatus()).toPlainString().equals("0")) {
            response.createElement("operator", result.getOperator());
            response.createElement("comments", result.getComment());
        }
        //	    		manager.writeLog("pp-cards", "block-pp", operator, comment , "SUCCESSFUL");
    }

}


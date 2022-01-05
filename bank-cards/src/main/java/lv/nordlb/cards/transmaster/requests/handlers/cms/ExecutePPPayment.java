package lv.nordlb.cards.transmaster.requests.handlers.cms;

import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.bank.cards.core.linkApp.dto.CardInfoDTO;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.soap.ErrorConstants;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

public class ExecutePPPayment extends SubRequestHandler {

    protected final PcdabaNGManager pcdabaNGManager;
    protected final CMSSoapAPIWrapper soapAPIWrapper;

    public ExecutePPPayment() throws RequestPreparationException {
        super();
        try {
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
            soapAPIWrapper = (CMSSoapAPIWrapper) new InitialContext().lookup(CMSSoapAPIWrapper.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String priorityPassCardNr = getStringFromNode("/do/ppcard");
        String amount = getStringFromNode("/do/amt");
        String dealDesc = getStringFromNode("/do/description");
        String slipNr = getStringFromNode("/do/slip");
        CardInfoDTO cardInfoDTO;
        PcdPpCard priorityPassCard;
        priorityPassCard = pcdabaNGManager.getPPCardInfoByCardNumber(priorityPassCardNr);
        if (priorityPassCard == null) {
            throw new RequestProcessingException("No such Priority Pass card!", this);
        }
        if (priorityPassCard.getStatus() == BigDecimal.ZERO) {
            if (priorityPassCard.getExpiryDate() != null) {
                GregorianCalendar now = new GregorianCalendar();
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(priorityPassCard.getExpiryDate());
                cal.add(GregorianCalendar.MONTH, 1);
                cal.set(GregorianCalendar.DAY_OF_MONTH, cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
                if (now.after(cal)) {
                    throw new RequestProcessingException("PP is blocked!", this);
                }
            } else {
                throw new RequestProcessingException("PP is blocked!", this);
            }
        }
        cardInfoDTO = pcdabaNGManager.getCardInfo(priorityPassCard.getPcdCard().getCard());
        if (cardInfoDTO == null)
            throw new RequestProcessingException(ErrorConstants.cantFindCard, this);
        if (!"EUR".equals(cardInfoDTO.getBillingCurrency().trim())) {
            double rate = 0;
            rate = pcdabaNGManager.getConvRate("EUR", cardInfoDTO.getBillingCurrency().trim());
            amount = String.valueOf(Math.round(Double.valueOf(amount) * rate));
        }

        String accountNo = (priorityPassCard.getPcdCard().getPcdAccounts().iterator().next()).getAccountNo().toPlainString();
        String ccy = cardInfoDTO.getBillingCurrency().trim();

        if (priorityPassCard.checkHash(priorityPassCardNr.substring(17))) {
            try {
                soapAPIWrapper.executeTransaction("0", accountNo, "52B", ccy, amount, slipNr, dealDesc);
            } catch (CMSSoapAPIException e) {
                throw new RequestProcessingException(e.getMessage(), this);
            }
        } else throw new RequestProcessingException("Card Hash Error!", this);

        ResponseElement response = createElement("job");
        response.createElement("Executed", "1");

    }
}

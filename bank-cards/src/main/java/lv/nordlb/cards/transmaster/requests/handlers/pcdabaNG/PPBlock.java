package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.Date;

public class PPBlock extends SubRequestHandler {
    private PcdabaNGManager pcdabaNGManager = null;

    /**
     * @throws RequestPreparationException
     */
    public PPBlock() throws RequestPreparationException {
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
        PcdPpCard result;
        String cardNr = getStringFromNode("/do/ppcard");
        String comment = getStringFromNode("/do/comment");
        String operator = getStringFromNode("/do/operator");
        String reason = getStringFromNode("/do/reason");
        if (cardNr == null || cardNr.equals("")) throw new RequestFormatException("Specify PP card number", this);

        result = pcdabaNGManager.getPPCardInfoByCardNumber(cardNr);
        if (result == null) throw
                new RequestFormatException(ErrorConstants.cantFindCard, this);
        result.setCtime(new Date());
        result.setOperator(operator);
        result.setStatus(BigDecimal.ZERO);
        if (reason != null) {
            if (reason.equals("lost")) {
                result.setEmailStatus(BigDecimal.ONE);
                comment = "Cause: Lost Comment: " + comment;
            } else if (reason.equals("stolen")) {
                result.setEmailStatus(BigDecimal.valueOf(2));
                comment = "Cause: Stolen Comment: " + comment;
            } else if (reason.equals("cancelled")) {
                result.setEmailStatus(BigDecimal.valueOf(3));
                comment = "Cause: Cancelled Comment: " + comment;
            } else throw new RequestProcessingException("Invalid reason!", this);
        } else {
            comment = "Comment: " + comment;
        }

        result.setComment(comment);
        pcdabaNGManager.saveOrUpdate(result);
        ResponseElement response = createElement("block-pp");
        response.createElement("result", "Card blocked");
        pcdabaNGManager.writeLog("pp-cards", "block-pp", operator, "Card: " + cardNr + " Cause: " + comment, "SUCCESSFUL");
    }

}


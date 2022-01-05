package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.Crypting3DES;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import org.apache.commons.lang.StringUtils;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Jānis Saldābols
 */
public class RetrievePinCodes extends SubRequestHandler {

    private PcdabaNGManager pcdabaNGManager = null;

    public RetrievePinCodes() throws RequestPreparationException {
        super();
        try {
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String card = getStringFromNode("/do/card");
        if (!CardUtils.cardCouldBeValid(card))
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);

        PcdCard thisCard = pcdabaNGManager.getCardByCardNumber(card);
        if (thisCard == null)
            throw new RequestProcessingException(ErrorConstants.cantFindCard + " (pcd)", this);

        if (StringUtils.isBlank(thisCard.getUAField1())) {
            throw new RequestProcessingException("Card do not have PIN ID (pcd)", this);
        }

        String pinField = thisCard.getUAField1();

        String pinId = StringUtils.trimToEmpty(StringUtils.substring(pinField, 0, 10));
        String pinAuthentification = StringUtils.trimToEmpty(StringUtils.substring(pinField, 20, 36));
        String decrypted = Crypting3DES.decrypting(pinAuthentification);
        if (decrypted == null || decrypted.length() != 4) {
            throw new RequestProcessingException("Could not decrypt autentification code", this);
        }
        ResponseElement cardElement = createElement("check-cvc-retrieve-pin-codes");
        cardElement.createElement("card", card);
        cardElement.createElement("pin-id-code", pinId);
        cardElement.createElement("pin-auth-code", decrypted);

    }

}

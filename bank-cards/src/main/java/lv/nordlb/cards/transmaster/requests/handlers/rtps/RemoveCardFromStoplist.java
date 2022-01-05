package lv.nordlb.cards.transmaster.requests.handlers.rtps;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.exceptions.RequestProcessingSoftException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.nordlb.cards.transmaster.requests.handlers.cms.RequestProcessingCMSAPIExtendedException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RemoveCardFromStoplist extends SubRequestHandler {

    private CMSSoapAPIWrapper soapAPIWrapper;
    private PcdabaNGManager pcdabaNGManager;

    public RemoveCardFromStoplist() throws RequestPreparationException {
        super();
        try {
            soapAPIWrapper = (CMSSoapAPIWrapper) new InitialContext().lookup(CMSSoapAPIWrapper.JNDI_NAME);
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String card_number = getStringFromNode("/do/card");
        String description = getStringFromNode("/do/description");

        ResponseElement removeCardFromStopList = createElement("remove-card-from-stoplist");
        if (!CardUtils.cardCouldBeValid(card_number))
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);

        PcdCard thisCard = pcdabaNGManager.getCardByCardNumber(card_number);
        String centre_id = CardUtils.composeCentreIdFromPcdCard(thisCard);

        if (thisCard == null || centre_id == null) {
            throw new RequestFormatException(ErrorConstants.cantFindCard, this);
        }
        //If the card is hard-blocked (cannot be unblocked), throw a soft exception
        if ("2".equals(thisCard.getStatus1())) {
            throw new RequestProcessingSoftException(ErrorConstants.cardIsHardBlocked);
        }
        try {
            thisCard.setStatus1("0");
            thisCard.setStatus2("0");
            thisCard.setStopCause("0");
            pcdabaNGManager.saveOrUpdate(thisCard);
            soapAPIWrapper.removeCardFromStop(card_number, description);
        } catch (CMSSoapAPIException e) {
            throw new RequestProcessingCMSAPIExtendedException(e, this);
        }
        removeCardFromStopList.createElement("removed", card_number);
    }
}



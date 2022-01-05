package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCardsAddFields;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;
import org.apache.commons.lang.StringUtils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jānis Saldābols
 */
public class SetPinDeliveryStatusHandler extends SubRequestHandler {

    private static final List<String> STATUSES = Arrays.asList("AVAILABLE", "REQUESTED", "DELETED", "DELIVERED");
    private PcdabaNGManager pcdabaNGManager = null;
    private CardManager cardManager = null;

    public SetPinDeliveryStatusHandler() throws RequestPreparationException {
        super();
        try {
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
            cardManager = (CardManager) new InitialContext().lookup(CardManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String card = getStringFromNode("/do/card");
        String status = StringUtils.upperCase(getStringFromNode("/do/pin-delivery-status"));

        updateCardPinDeliveryStatus(card, status, false);

        ResponseElement cardElement = createElement("set-pin-delivery-status");
        cardElement.createElement("card", card);
        cardElement.createElement("pin-delivery-status", status);
    }

    public void updateCardPinDeliveryStatus(String card, String status, boolean skipError) throws RequestFormatException, RequestProcessingException {
        if (!CardUtils.cardCouldBeValid(card))
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);

        if (StringUtils.isBlank(status))
            throw new RequestFormatException("Specify pin-delivery-status tag", this);

        if (!STATUSES.contains(status)) {
            throw new RequestFormatException("Status is not in list 'AVAILABLE', 'REQUESTED', 'DELETED', 'DELIVERED'", this);
        }

        PcdCard thisCard = pcdabaNGManager.getCardByCardNumber(card);
        if (thisCard == null)
            throw new RequestProcessingException(ErrorConstants.cantFindCard + " (pcd)", this);

        IzdCard izdCard = cardManager.getIzdCardByCardNumber(card);
        if (izdCard == null)
            throw new RequestProcessingException(ErrorConstants.cantFindCard + " (izd)", this);

        // Update information
        if (izdCard.getIzdCardsAddFields() == null) {
            if (skipError)
                return;
            else
                throw new RequestProcessingException("Card is not present in IZD_CARDS_ADD_FIELDS table", this);
        }
        izdCard.getIzdCardsAddFields().setUAField2(status);
        thisCard.setUAField2(status);

        // If status is deleted then remove PIN ID
        if ("DELETED".equals(StringUtils.upperCase(status)) ||
                "DELIVERED".equals(StringUtils.upperCase(status))) {
            // If status DELETED or DELIVERED we need to remove PIN ID
            if (izdCard.getIzdCardsAddFields() == null) {
                izdCard.setIzdCardsAddFields(new IzdCardsAddFields());
            }
            izdCard.getIzdCardsAddFields().setUAField1(null);
            thisCard.setUAField1(null);
        }

        // Update in CMS
        try {
            cardManager.saveOrUpdate(izdCard.getIzdCardsAddFields());
        } catch (DataIntegrityException e) {
            throw new RequestProcessingException("Cannot update card info (izd)", this);
        }

        // Update in LinkApp
        pcdabaNGManager.saveOrUpdate(thisCard);
    }
}

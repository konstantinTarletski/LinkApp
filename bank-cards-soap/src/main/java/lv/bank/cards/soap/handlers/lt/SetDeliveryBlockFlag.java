package lv.bank.cards.soap.handlers.lt;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import org.apache.commons.lang.StringUtils;

public class SetDeliveryBlockFlag extends SubRequestHandler {

    protected CardsDAO cardsDAO;

    public SetDeliveryBlockFlag() {
        super();
        cardsDAO = new CardsDAOHibernate();
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String card = getStringFromNode("/do/card");
        String deliveryBlock = getStringFromNode("/do/delivery-block");
        String trackingNo = getStringFromNode("/do/tracking-no");

        ResponseElement reponseElement = createElement("set-delivery-block-flag");

        if (StringUtils.isBlank(card) && StringUtils.isBlank(trackingNo))
            throw new RequestFormatException("Card or tracking number must be specified", this);

        if (deliveryBlock == null
                || (!deliveryBlock.equals("5") && !deliveryBlock.equals("6")
                && !deliveryBlock.equals("8") && !deliveryBlock.equals("auto"))) {
            throw new RequestFormatException("delivery-block tag must contain 5, 6, 8 or auto", this);
        }

        if (!CardUtils.cardCouldBeValid(card) && StringUtils.isBlank(trackingNo))
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);

        PcdCard thisCard;
        if (StringUtils.isEmpty(card)) {
            try {
                thisCard = cardsDAO.getCardByTrackingNoLT(trackingNo);
            } catch (DataIntegrityException e) {
                throw new RequestProcessingException(e.getMessage(), this);
            }
        } else {
            thisCard = cardsDAO.findByCardNumber(card);
        }

        if (thisCard == null) {
            throw new RequestFormatException(ErrorConstants.cantFindCard, this);
        }
        card = thisCard.getCard();

        if ("auto".equalsIgnoreCase(deliveryBlock) && thisCard.getDeliveryBlock() == null) {
            deliveryBlock = Constants.DELIVERY_BRANCH_LT_080.equals(thisCard.getUCod10()) ? "8" : "5";
        }

        if (!"auto".equalsIgnoreCase(deliveryBlock)) {
            thisCard.setDeliveryBlock(deliveryBlock);
        }

        cardsDAO.saveOrUpdate(thisCard);
        reponseElement.createElement("card", card);
        reponseElement.createElement("delivery-block", thisCard.getDeliveryBlock());
    }
}

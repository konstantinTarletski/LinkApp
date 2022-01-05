package lv.bank.cards.soap.handlers;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.impl.CardDAOHibernate;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingCMSAPIExtendedException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.exceptions.RequestProcessingSoftException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;

@Slf4j
public class AddCardToStoplist extends SubRequestHandler {

    protected CMSSoapAPIWrapper soapAPIWrapper;
    protected CardDAO cardDAO;
    protected CardService cardHelper;

    public AddCardToStoplist() throws RequestPreparationException {
        super();
        try {
            soapAPIWrapper = (CMSSoapAPIWrapper) new InitialContext().lookup(CMSSoapAPIWrapper.JNDI_NAME);
            cardDAO = new CardDAOHibernate();
            cardHelper = new CardService();
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String card_number = getStringFromNode("/do/card");
        String action_code = getStringFromNode("/do/action-code");
        String cause = getStringFromNode("/do/cause");
        String description = getStringFromNode("/do/description");

        ResponseElement addCardToStopList = createElement("add-card-to-stoplist");

        if (!CardUtils.cardCouldBeValid(card_number)) {
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);
        }

        PcdCard pcdCard = cardHelper.getPcdCard(card_number);

        if (pcdCard == null) {
            throw new RequestFormatException(ErrorConstants.cantFindCard, this);
        }

        // If the card is already hard-blocked, throw a soft exception
        if ("2".equals(pcdCard.getStatus1())) {
            throw new RequestProcessingSoftException(ErrorConstants.cardIsHardBlocked);
        }

        if (action_code == null) {
            try {
                action_code = cardHelper.getActionCodeByCauseBankC(cause, pcdCard.getBankC());
            } catch (DataIntegrityException e) {
                throw new RequestProcessingException(e.getMessage(), this);
            }
        } else {
            try {
                cause = cardHelper.getCauseByActionCodeBankC(action_code, pcdCard.getBankC());
            } catch (DataIntegrityException e) {
                throw new RequestProcessingException(e.getMessage(), this);
            }
        }

        //Replace all new lines with spaces
        if (description != null) {
            description = description.replaceAll("(\\r|\\n|\\r\\n)+", " ");
        }

        try {
            if (pcdCard.getExpiry1().before(new Date())) {
                IzdCard izdCard = (IzdCard) cardDAO.getObject(IzdCard.class, card_number);
                izdCard.setStatus1(action_code.substring(0, 1));
                izdCard.setStatus2(action_code.substring(0, 1));
                izdCard.setStopCause(cause);
                cardDAO.saveOrUpdate(izdCard);
                log.info("Card is expired, setting status in CMS manually");
            } else {

                if ("1".equals(pcdCard.getStatus1()) && action_code.startsWith("2")) {
                    soapAPIWrapper.removeCardFromStop(card_number, "Unblocking to apply hard block");
                }
                soapAPIWrapper.addCardToStop(card_number, cause, description);
            }

            pcdCard.setStatus1(action_code.substring(0, 1));
            pcdCard.setStatus2(action_code.substring(0, 1));
            pcdCard.setStopCause(cause);
            cardHelper.saveOrUpdate(pcdCard);
        } catch (CMSSoapAPIException e) {
            throw new RequestProcessingCMSAPIExtendedException(e, this);
        }
        addCardToStopList.createElement("added", card_number);
    }
}

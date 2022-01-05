package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.OrderPinEnvelopeWork;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateCardXML;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateDBWork;
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

/**
 * @author Jānis Saldābols
 */
public class OrderPinReminderHandler extends SubRequestHandler {
    private PcdabaNGManager pcdabaNGManager = null;
    private CardManager cardManager = null;
    private CMSCallAPIWrapper wrap;

    public OrderPinReminderHandler() throws RequestPreparationException {
        super();
        try {
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
            cardManager = (CardManager) new InitialContext().lookup(CardManager.JNDI_NAME);
            wrap = new CMSCallAPIWrapper();
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String card = getStringFromNode("/do/card");
        String orderId = getStringFromNode("/do/order-id");
        boolean check = "TRUE".equals(StringUtils.upperCase(StringUtils.trimToEmpty(getStringFromNode("/do/check"))));
        if (!CardUtils.cardCouldBeValid(card))
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);

        if (StringUtils.isEmpty(orderId) && !check)
            throw new RequestFormatException("Order id must be specified", this);

        PcdCard thisCard = pcdabaNGManager.getCardByCardNumber(card);
        if (thisCard == null)
            throw new RequestProcessingException(ErrorConstants.cantFindCard + " (pcd)", this);

        IzdCard izdCard = cardManager.getIzdCardByCardNumber(card);
        if (izdCard == null)
            throw new RequestProcessingException(ErrorConstants.cantFindCard + " (izd)", this);

        if ("2".equals(izdCard.getStatus1())) {
            throw new RequestProcessingException("Card is hard blocked", this);
        }

        if ("7".equals(izdCard.getRenew()) || "REQUESTED".equals(thisCard.getUAField2())) {
            throw new RequestProcessingException("PIN reminder is already ordered", this);
        }

        if ("AVAILABLE".equals(thisCard.getUAField2())) {
            throw new RequestProcessingException("PIN is already available to be received", this);
        }

        if (!"J".equals(izdCard.getRenew()) && !"D".equals(izdCard.getRenew()) &&
                !"R".equals(izdCard.getRenew()) && !"E".equals(izdCard.getRenew()) &&
                !"G".equals(izdCard.getRenew())) {
            throw new RequestProcessingException("Card status " + izdCard.getRenew() + " is not in (J,D,R,E,G) (izd)", this);
        }

        if (izdCard.getIzdCardsPinBlocks() == null || izdCard.getIzdCardsPinBlock() == null || izdCard.getIzdCardsPinBlock().getPinBlock() == null) {
            throw new RequestProcessingException("Card missing PIN Block", this);
        }

        if (check) {
            ResponseElement cardElement = createElement("order-pin-reminder");
            cardElement.createElement("check", "success");
        } else {

            String pinField = null;
            try {
                pinField = pcdabaNGManager.getNextPcdPinIDWithAuthentificationCode(orderId);
            } catch (DataIntegrityException e) {
                throw new RequestProcessingException("Cannot generate PIN ID", this);
            }


            // Update in CMS
            UpdateCardXML updateCardXML = wrap.new UpdateCardXML(card,
                    thisCard.getBankC(), thisCard.getGroupc());
            updateCardXML.setElement("U_AFIELD2", "REQUESTED");
            updateCardXML.setElement("U_AFIELD1", pinField);
            updateCardXML.setElement("U_FIELD8", orderId);
            UpdateDBWork updateWork = wrap.new UpdateDBWork();
            updateWork.setInputXML(updateCardXML.getDocument());
            String response = cardManager.doWork(updateWork);
            if (!"success".equals(response)) {
                throw new RequestProcessingException(StringUtils.substring(
                        StringUtils.substringBetween(response, "<ERROR_DESC>",
                                "</ERROR_DESC>"), 0, 200), this);
            }

            // Update in LinkApp
            thisCard.setUAField2("REQUESTED");
            thisCard.setUAField1(pinField);
            thisCard.setUField8(orderId);
            pcdabaNGManager.saveOrUpdate(thisCard);

            // Run OrderPinEnvelope function
            OrderPinEnvelopeWork work = wrap.new OrderPinEnvelopeWork();
            work.setParams(thisCard.getBankC(), thisCard.getGroupc(), card);
            response = cardManager.doWork(work);
            if (!"success".equals(response)) {
                throw new RequestProcessingException(
                        StringUtils.substring(response, 0, 200), this);
            }

            ResponseElement cardElement = createElement("order-pin-reminder");
            cardElement.addText("success");
        }
    }

}

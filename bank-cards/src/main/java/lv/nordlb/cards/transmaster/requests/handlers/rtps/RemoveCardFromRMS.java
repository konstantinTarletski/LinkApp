/*
 * Created on 2005.10.6
 */
package lv.nordlb.cards.transmaster.requests.handlers.rtps;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIWrapper;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;
import lv.nordlb.cards.transmaster.interfaces.RTCU;
import lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG.SetPinDeliveryStatusHandler;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.naming.InitialContext;
import javax.naming.NamingException;

@Slf4j
public class RemoveCardFromRMS extends SubRequestHandler {

    private static final String TEMPLATE = "deliveryTimeBlock";

    protected RTPSCallAPIWrapper mainWrapper;
    protected RTCU rtcu;
    protected CardService helper;


    public RemoveCardFromRMS() throws RequestPreparationException {
        super();
        mainWrapper = new RTPSCallAPIWrapper();
        helper = new CardService();
        try {
            rtcu = (RTCU) new InitialContext().lookup(RTCU.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String centre_id = getStringFromNode("/do/centre-id");
        String card_number = getStringFromNode("/do/card");
        String rule_expr = getStringFromNode("/do/rule-expr");
        String priority = getStringFromNode("/do/priority");
        String action_code = getStringFromNode("/do/action-code");
        String description = getStringFromNode("/do/description");
        String repeated = getStringFromNode("/do/repeated");

        ResponseElement removeCardFromRMS = createElement("remove-card-from-rms");
        if (!CardUtils.cardCouldBeValid(card_number))
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);

        if (centre_id == null) {
            centre_id = helper.getCentreIdByCard(card_number);
            if (centre_id == null)
                throw new RequestProcessingException(ErrorConstants.cantFindCard, this);
        }
        Integer parsedPriority;
        if (priority != null) {
            parsedPriority = Integer.valueOf(priority);
            if (parsedPriority.intValue() >= 0)
                throw new RequestProcessingException("Incorrect priority value", this);
        } else {
            parsedPriority = null;
        }
        try {
            try {
                mainWrapper.RemoveCardFromRMSStop(centre_id, card_number,
                        rule_expr, parsedPriority, action_code, description);
            } catch (RTPSCallAPIException e) {
                if (e.getMessage().contains("PLS-00201") && StringUtils.isBlank(repeated)) { // try again if there is Oracle error about missing function
                    log.info("Retry RTPS remove card call");
                    try {
                        Document doc = r.getReq();
                        Element element = DocumentHelper.createElement("repeated");
                        element.setText("true");
                        doc.getRootElement().add(element);
                        String result = rtcu.RTCUNGCall(doc.asXML());
                        if (!result.contains("removed")) {
                            throw e;
                        }
                    } catch (DataIntegrityException e1) {
                        throw e;
                    }
                } else {
                    throw e;
                }
            }
            removeCardFromRMS.createElement("removed", card_number.toString());

        } catch (RTPSCallAPIException e) {
            throw new RequestProcessingRTPSAPIExtendedException(e, this);
        }

        String type = getStringFromNode("/do/@template");
        if (TEMPLATE.equals(type)) {
            try {
                SetPinDeliveryStatusHandler setPinStatus = new SetPinDeliveryStatusHandler();
                setPinStatus.updateCardPinDeliveryStatus(card_number, "DELIVERED", true);
                removeCardFromRMS.createElement("pin-delivery-status", "DELIVERED");
            } catch (RequestPreparationException e) {
                throw new RequestProcessingException("Cannot update PIN Delivery status", this);
            }
        }
    }
}



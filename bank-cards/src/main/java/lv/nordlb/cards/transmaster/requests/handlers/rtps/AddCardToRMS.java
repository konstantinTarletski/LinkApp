package lv.nordlb.cards.transmaster.requests.handlers.rtps;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.rtps.StipRmsStoplist;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIWrapper;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.exceptions.RequestProcessingSoftException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;
import lv.nordlb.cards.transmaster.fo.interfaces.StipCardManager;
import lv.nordlb.cards.transmaster.interfaces.RTCU;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

@Slf4j
public class AddCardToRMS extends SubRequestHandler {

    protected RTPSCallAPIWrapper mainWrapper;
    protected StipCardManager stipCardManager;
    protected RTCU rtcu;
    protected CardService helper;

    public AddCardToRMS() throws RequestPreparationException {
        super();
        mainWrapper = new RTPSCallAPIWrapper();
        helper = new CardService();
        try {
            stipCardManager = (StipCardManager) new InitialContext().lookup(StipCardManager.JNDI_NAME);
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

        ResponseElement addCardToRMS = createElement("add-card-to-rms");

        if (!CardUtils.cardCouldBeValid(card_number))
            throw new RequestProcessingSoftException(ErrorConstants.invalidCardNumber, this);

        if (rule_expr == null)
            throw new RequestFormatException("rule-expr tag value is mandatory", this);

        if (centre_id == null) {
            centre_id = helper.getCentreIdByCard(card_number);
            if (centre_id == null) {
                throw new RequestProcessingSoftException(ErrorConstants.cantFindCard, this);
            }
        }
        Long parsedPriority;
        if (priority != null) {
            parsedPriority = Long.valueOf(priority);
            if (parsedPriority.intValue() >= 0) {
                throw new RequestFormatException("Incorrect priority value", this);
            }
        } else {
            parsedPriority = null;
        }

        //Replace all new lines with spaces
        if (description != null) {
            description = description.replaceAll("(\\r|\\n|\\r\\n)+", " ");
        }

        try {
            List<StipRmsStoplist> list = stipCardManager.getStipRmsStoplist(card_number, centre_id, parsedPriority);
            boolean inRMSStopList = false;
            for (StipRmsStoplist tmp : list) {
                if (StringUtils.equals(tmp.getRuleExpr(), rule_expr) &&
                        tmp.getAnswerCode() != null && StringUtils.equals(tmp.getAnswerCode().getActionCode(), action_code) &&
                        StringUtils.equals(tmp.getDescription(), description)) inRMSStopList = true;
            }
            if (!inRMSStopList) {
                try {
                    mainWrapper.AddCardToRMSStop(centre_id, card_number, rule_expr, parsedPriority, action_code, description);
                } catch (RTPSCallAPIException e) {
                    if (e.getMessage().contains("PLS-00201") && StringUtils.isBlank(repeated)) { // try again if there is Oracle error about missing function
                        log.info("Retry RTPS add card call");
                        try {
                            Document doc = r.getReq();
                            Element element = DocumentHelper.createElement("repeated");
                            element.setText("true");
                            doc.getRootElement().add(element);
                            String result = rtcu.RTCUNGCall(doc.asXML());
                            if (!result.contains("added")) {
                                throw e;
                            }
                        } catch (DataIntegrityException e1) {
                            throw e;
                        }
                    } else {
                        throw e;
                    }
                }
            }
            addCardToRMS.createElement("added", card_number.toString());
        } catch (RTPSCallAPIException e) {
            throw new RequestProcessingRTPSAPIExtendedException(e, this);
        }
    }
}



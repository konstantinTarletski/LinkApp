package lv.bank.cards.soap.handlers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.utils.XmlUtils;
import lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.service.LAPPIBSMSNotifPrcWSPortType;
import lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.service.LAPPIBSMSNotifPrcWSService;
import lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types.AddrT;
import lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types.NotificationQueryBodyLVT;
import lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types.NotificationT;
import lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types.ParamListT;
import lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types.ParamT;
import lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types.QueryT;
import lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types.RecipientListT;
import lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.types.RecipientT;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultElement;

import javax.xml.ws.WebServiceException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SonicNotificationHandler extends SubRequestHandler {

    public SonicNotificationHandler() {
        super();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String notificationCode = getStringFromNode("/do/notification-code");

        if (StringUtils.isBlank(notificationCode))
            throw new RequestFormatException("Missing notification code", this);

        List<RecipientInfoHolder> recipients = new ArrayList<>();

        XPath xpathSelector = DocumentHelper.createXPath("/do/recipient");
        @SuppressWarnings("unchecked")
        List<Element> elements = xpathSelector.selectNodes(r.getReq());
        for (Element e : elements) {
            Document rec = DocumentHelper.createDocument(e.createCopy());
            String addressType = getStringFromNode("/recipient/address-type", rec);
            String address = getStringFromNode("/recipient/address", rec);
            if (StringUtils.isBlank(address) || StringUtils.isBlank(addressType)) {
                throw new RequestFormatException("Address or address type, address=" + address + " address type=" + addressType, this);
            }
            RecipientInfoHolder recipient = new RecipientInfoHolder(addressType, address);
            XPath paramSelector = DocumentHelper.createXPath("/recipient/param");
            @SuppressWarnings("unchecked")
            List<DefaultElement> params = paramSelector.selectNodes(rec);
            int i = 1;
            for (DefaultElement p : params) {
                String code = XmlUtils.getAttributeValue(DocumentHelper.createDocument(p.createCopy()), "/param/@code");
                if (StringUtils.isBlank(code))
                    code = "%" + i;
                recipient.getParams().add(new ParamInfoHolder(code, p.getText()));
                i++;
            }
            recipients.add(recipient);
        }
        if (recipients.isEmpty())
            throw new RequestFormatException("Missing recipients", this);

        sendSonicNotifications(notificationCode, recipients);
        createElement("sonic-notification").addText("Done");
    }

    /**
     * Send notifications to Sonic
     */
    public void sendSonicNotifications(String notificationCode, List<RecipientInfoHolder> recipients) throws RequestProcessingException {

        LAPPIBSMSNotifPrcWSService service = new LAPPIBSMSNotifPrcWSService();
        LAPPIBSMSNotifPrcWSPortType port = service.getLAPPIBSMSNotifPrcWSPort();

        try {
            QueryT query = new QueryT();
            NotificationQueryBodyLVT body = new NotificationQueryBodyLVT();
            NotificationT notification = new NotificationT();
            notification.setNotifCode(notificationCode);
            body.setNotification(notification);
            RecipientListT recipientList = new RecipientListT();
            for (RecipientInfoHolder r : recipients) {
                RecipientT rec = new RecipientT();
                AddrT addr = new AddrT();
                addr.setType(r.getAddressType());
                addr.setValue(r.getAddress());
                rec.setAddr(addr);
                if (!r.getParams().isEmpty()) {
                    ParamListT pList = new ParamListT();
                    for (ParamInfoHolder p : r.getParams()) {
                        ParamT param = new ParamT();
                        param.setCode(p.getParamType());
                        param.setValue(p.getParam());
                        pList.getParam().add(param);
                    }
                    rec.getParamList().add(pList);
                }
                recipientList.getRecipient().add(rec);
            }
            body.setRecipientList(recipientList);
            query.setQueryBody(body);
            port.lappIBSMSNotifPrcWS(query);
        } catch (WebServiceException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                log.warn("Timeout from Sonic", e);
            } else {
                log.warn("Cannot send notification", e);
            }
            throw new RequestProcessingException("Cannot send notification", this);
        }
    }

    @Data
    public static class RecipientInfoHolder {
        private final String addressType;
        private final String address;
        private List<ParamInfoHolder> params = new ArrayList<>();
    }

    @Data
    public static class ParamInfoHolder {
        private final String paramType;
        private final String param;
    }
}

package lv.bank.rest.service;

import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.rest.exception.BusinessException;
import lv.bank.rest.exception.JsonErrorCode;
import lv.bank.cards.soap.handlers.SonicNotificationHandler;
import org.dom4j.Element;

public class SonicNotificationService {

    public static void SendSonicNotification(Element element) throws BusinessException {
        try {
            SonicNotificationHandler sonic = new SonicNotificationHandler();
            sonic.handle(new SubRequest(element));
        } catch (Exception e) {
            throw BusinessException.create(JsonErrorCode.APPLICATION_ERROR, "notification",
                    "Error notification processing: " + e.getMessage());
        }
    }
}

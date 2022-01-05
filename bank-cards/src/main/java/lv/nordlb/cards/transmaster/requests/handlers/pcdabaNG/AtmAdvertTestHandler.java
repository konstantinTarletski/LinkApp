package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.soap.api.atm.soap.servise.ServiceProviderPortImpl;
import lv.bank.cards.soap.api.atm.soap.types.Detail;
import lv.bank.cards.soap.api.atm.soap.types.DetailArray;
import lv.bank.cards.soap.api.atm.soap.types.GetList;
import lv.bank.cards.soap.api.atm.soap.types.PaymentServerFault;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import org.apache.commons.lang.StringUtils;

public class AtmAdvertTestHandler extends SubRequestHandler {

    public AtmAdvertTestHandler() {
        super();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String card = getStringFromNode("/do/card");
        String answer = getStringFromNode("/do/answer");
        String terminal = getStringFromNode("/do/terminal");

        if (StringUtils.isBlank(terminal))
            terminal = "t1";

        String typeString = StringUtils.isBlank(answer) ? "QUESTION" : "ANSWER";

        ServiceProviderPortImpl service = new ServiceProviderPortImpl();
        GetList body = new GetList();
        body.setObjectID(1);
        DetailArray details = new DetailArray();
        Detail d = new Detail();
        d.setName("card");
        d.setValue(card);
        details.getItem().add(d);
        Detail term = new Detail();
        term.setName("terminal_id");
        term.setValue(terminal);
        details.getItem().add(term);
        Detail type = new Detail();
        type.setName("op_type");
        type.setValue(typeString);
        details.getItem().add(type);
        Detail replay = new Detail();
        replay.setName("adv_answer");
        replay.setValue(answer);
        details.getItem().add(replay);
        body.setDetails(details);
        try {
            service.getList(body);
            createElement("error").addText("error");
        } catch (PaymentServerFault e) {
            createElement("ad-id").addText(e.getFaultInfo().getError());
        }
    }

}

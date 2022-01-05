package lv.bank.cards.soap.handlers.lv;

import lv.bank.cards.core.cms.dao.IzdConfigDAO;
import lv.bank.cards.core.cms.impl.IzdConfigDAOHibernate;
import lv.bank.cards.core.entity.cms.IzdConfig;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

import java.util.List;

public class IssConfig extends SubRequestHandler {

    protected IzdConfigDAO izdConfigDAO;

    public IssConfig() {
        super();
        izdConfigDAO = new IzdConfigDAOHibernate();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        List<IzdConfig> sregdir = izdConfigDAO.GetIzdConfig();

        for (IzdConfig izdcnfg : sregdir) {
            ResponseElement element = createElement("element");
            element.createElement("BankC").addText(izdcnfg.getId().getBankC());
            element.createElement("GroupC").addText(izdcnfg.getId().getGroupc());
            element.createElement("ParamNum").addText(izdcnfg.getId().getParamNumb().toString());
            element.createElement("Value").addText(String.valueOf(izdcnfg.getParamValue()));
            element.createElement("Name").addText(izdcnfg.getParamName());
            element.createElement("Comment").addText(izdcnfg.getParamComent());
            element.createElement("UserID").addText(String.valueOf(izdcnfg.getUsrid()));
            element.createElement("Ctime").addText(r.getDateFormat().format(izdcnfg.getCtime()));
        }
    }
}


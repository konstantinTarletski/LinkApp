package lv.bank.cards.soap.handlers.lv;

import lv.bank.cards.core.entity.rtps.Regdir;
import lv.bank.cards.core.rtps.dao.RegDirDAO;
import lv.bank.cards.core.rtps.impl.RegDirDAOHibernate;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

import java.util.List;

public class RtpsConfig extends SubRequestHandler {

    protected RegDirDAO regDirDAO;

    public RtpsConfig() {
        super();
        regDirDAO = new RegDirDAOHibernate();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        List<Regdir> sregdir = regDirDAO.GetRegDir();

        for (Regdir regdirtable : sregdir) {
            ResponseElement element = createElement("element");

            element.createElement("RegDir").addText(regdirtable.getRegId().toString());
            element.createElement("RegName").addText(regdirtable.getRegName());
            element.createElement("RegTypeId").addText(regdirtable.getRegTypeId());
            element.createElement("RegReadOnly").addText(regdirtable.getReadOnly());
            if (regdirtable.getRegValueTypeId() != null)
                element.createElement("RegValueTypeId").addText(regdirtable.getRegValueTypeId());
            else element.createElement("RegValueTypeId").addText("");
            if (regdirtable.getRegValue() != null)
                element.createElement("RegValue").addText(regdirtable.getRegValue());
            else element.createElement("RegValue").addText("");
        }
    }
}

package lv.bank.cards.auth.RTPS;

import lv.bank.cards.auth.AuthorisationException;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class PosISOAuthorization {
    static Logger log = Logger.getLogger(PosISOAuthorization.class);
    ISOMsg request = new ISOMsg();
    ISOMsg response = new ISOMsg();
    String successActionCode = "%%%";

    public void setRequestField(int fieldNo, Object value) throws AuthorisationException {
        try {
            request.set(new ISOField(fieldNo, value.toString()));
        } catch (ISOException e) {
            log.error(e);
            throw new AuthorisationException(e);
        }
    }

    public synchronized ISOMsg authorize() throws AuthorisationException {
        PosISOCom c;
        try {
            c = new PosISOCom();
            Date startDate = new Date();

            if (!request.hasField(12))
                request.set(new ISOField(12, new SimpleDateFormat("yyMMddHHmmss").format(startDate)));

            for (int r = 1; r <= 128; r++) {
                if (request.hasField(r))
                    log.debug("TO RTPS  : fld_" + r + "='" + request.getValue(r) + "'");
            }

            response = c.singleAuth(request);

            for (int r = 1; r <= 128; r++) {
                if (response.hasField(r))
                    log.debug("FROM RTPS: fld_" + r + "='" + response.getValue(r) + "'");
            }
            return response;

        } catch (AuthorisationException e) {
            log.error(e);
            throw e;
        } catch (ISOException e) {
            log.error(e);
            throw new AuthorisationException(e);
        }

    }

    public ISOMsg getRequest() {
        return request;
    }

    public ISOMsg getResponse() {
        return response;
    }

    public boolean isSucessfullyCompleted() {
        if ((response != null) && (response.hasField(39))) {
            return successActionCode.compareTo(response.getValue(39).toString()) == 0 ? true : false;
        }
        return false;
    }

}

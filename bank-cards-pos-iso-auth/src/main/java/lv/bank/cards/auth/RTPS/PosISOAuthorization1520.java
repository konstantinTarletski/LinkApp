package lv.bank.cards.auth.RTPS;

import lv.bank.cards.auth.AuthorisationException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PosISOAuthorization1520 extends PosISOAuthorization {

    {
        successActionCode = "500";
    }

    public PosISOAuthorization1520() throws AuthorisationException {
        try {
            request.setPackager(new PosISOPackager());
            request.setMTI("1520"); // MTI for reconciliations
//			request.set(new ISOField(3, "000000"));	// processing code
            request.set(new ISOField(24, "500"));
            request.set(new ISOField(25, "1508"));
            request.set(new ISOField(28, new SimpleDateFormat("yyMMdd").format(new Date()))); // Reconciliation date
        } catch (ISOException e) {
            log.error(e);
            throw new AuthorisationException(e);
        }
    }

}

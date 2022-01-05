package lv.bank.cards.auth.RTPS;

import lv.bank.cards.auth.AuthorisationException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PosISOAuthorization1420 extends PosISOAuthorization {

    {
        successActionCode = "400";
    }

    public PosISOAuthorization1420() throws AuthorisationException {
        try {
            request.setPackager(new PosISOPackager());
            request.setMTI("1420"); // MTI for reversals
            request.set(new ISOField(3, "000000"));    // processing code
            request.set(new ISOField(22, "XXXXXXXXXXXX")); // Point code
            request.set(new ISOField(24, "400")); // Function code; 400 - Full reversal
            request.set(new ISOField(25, "4000")); // Message reason code 4000 - customer cancellation
            request.set(new ISOField(28, new SimpleDateFormat("yyMMdd").format(new Date()))); // Reconciliation date
        } catch (ISOException e) {
            log.error(e);
            throw new AuthorisationException(e);
        }
    }

}

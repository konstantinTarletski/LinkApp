package lv.bank.cards.auth.RTPS;

import lv.bank.cards.auth.AuthorisationException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PosISOAuthorization1200 extends PosISOAuthorization {
    {
        successActionCode = "000";
    }

    public PosISOAuthorization1200() throws AuthorisationException {
        try {
            request.setPackager(new PosISOPackager());
            request.setMTI("1200"); // MTI for purchases
            request.set(new ISOField(3, "000000"));    // processing code
            request.set(new ISOField(22, "XXXXXXXXXXXX")); // Point code
            request.set(new ISOField(24, "200")); // Function code;
            request.set(new ISOField(25, "1508")); // reason code: online forced by terminal
            request.set(new ISOField(28, new SimpleDateFormat("yyMMdd").format(new Date()))); // Reconciliation date
        } catch (ISOException e) {
            log.error(e);
            throw new AuthorisationException(e);
        }
    }

    public PosISOAuthorization1200(Long amount, String card, String expiry) throws AuthorisationException {
        this();
        try {
            request.set(new ISOField(2, card)); // card number
            request.set(new ISOField(4, amount.toString())); // original amount
            request.set(new ISOField(14, expiry)); // Expiration date
        } catch (ISOException e) {
            log.error(e);
            throw new AuthorisationException(e);
        }
    }


}

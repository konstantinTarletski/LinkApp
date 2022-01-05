/*
 * Created on Oct 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package lv.bank.cards.auth.RTPS;

import lv.bank.cards.auth.AuthorisationException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;

/**
 * @author ays
 * <p>
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PosISOCom {
    PosISOChannel ch = null;
    String host = null;
    int port = 0;
    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(PosISOCom.class.getName());

    public PosISOCom() throws AuthorisationException {
        super();
        try {
            host = (String) new InitialContext().lookup("java:global/LinkApp/BankCardsPosISOAuth/authServerAddr");
            port = (Integer) new InitialContext().lookup("java:global/LinkApp/BankCardsPosISOAuth/authServerPort");
        } catch (NamingException e) {
            log.error("Can't lookup authorisation server address for BankCardsPosISOAuth");
            e.printStackTrace();
        }
    }

    public ISOMsg singleAuth(ISOMsg m) {

        log.debug("Calling PosISOChannel(" + host + "," + port + "," + m.getPackager() + ")");
        ch = new PosISOChannel(host, port, m.getPackager());
        try {
            ISOMsg m1 = null;

            ch.connect();
            /*** Sending msg ***/
            ch.send(m);
            /*** Getting msg ***/
            m1 = ch.receive();

            ch.endOfSession();

            //m1.dump(S y s t e m.out,"Received :");
            return m1;
        } catch (IOException e1) {
            ch.endOfSession();
            e1.printStackTrace();
            try {
                m.set(new ISOField(39, "900"));
                m.set(new ISOField(44, e1.getMessage()));
                return m;
            } catch (ISOException e) {
                return null;
            }

        } catch (ISOException e) {
            ch.endOfSession();
            e.printStackTrace();
            try {
                m.set(new ISOField(39, "900"));
                m.set(new ISOField(44, e.getMessage()));
                return m;
            } catch (ISOException e1) {
                return null;
            }
        }
    }
}

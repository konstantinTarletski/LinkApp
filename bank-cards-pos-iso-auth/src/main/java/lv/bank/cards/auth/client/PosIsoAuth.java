package lv.bank.cards.auth.client;

import lv.bank.cards.auth.AuthorisationException;
import lv.bank.cards.auth.interfaces.RTPSAuth;
import org.jpos.iso.ISOMsg;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PosIsoAuth {
    RTPSAuth rtpsAuth;
    private ISOMsg result = null;

    public PosIsoAuth() throws NamingException {
        rtpsAuth = (RTPSAuth) new InitialContext().lookup(RTPSAuth.JNDI_NAME);
    }

    public String getField(int n) {
        if (result == null) return null;
        return result.hasField(n) ? (String) result.getValue(n) : null;
    }

    private String testSimplePurchase() throws AuthorisationException {
        return rtpsAuth.simplePurchase("sms", "4652281998644768", "1");
    }

    private String testSimpleReversal() throws AuthorisationException {
        return rtpsAuth.simpleReversal("sms", 47000013);
    }

    private String testSimpleBDC() throws AuthorisationException {
        return rtpsAuth.simpleBDC("sms");
    }

    public static void main(String[] args) {
        try {
            PosIsoAuth test1 = new PosIsoAuth();
            // test1.testBalance();
            // test1.testReversal();
            // test1.testAuth();
            System.out.println(test1.testSimpleBDC());

            //<answer><trxn-id>41063713</trxn-id><result>failed</result><action-code>501</action-code></answer>
            //<answer><trxn-id>41065982</trxn-id><result>failed</result><action-code>501</action-code></answer>

            System.out.println(test1.testSimpleReversal());
            System.out.println(test1.testSimplePurchase());
        } catch (AuthorisationException | NamingException e) {
            e.printStackTrace();
        }
    }
}

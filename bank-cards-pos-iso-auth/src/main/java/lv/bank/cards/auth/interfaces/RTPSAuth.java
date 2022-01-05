package lv.bank.cards.auth.interfaces;

import lv.bank.cards.auth.AuthorisationException;

import javax.ejb.Local;
import javax.jws.WebService;

@Local
@WebService
public interface RTPSAuth {
    public static final String JNDI_NAME = "java:app/bankCardsPosISOAuth/RTPSAuthBean!lv.bank.cards.auth.interfaces.RTPSAuth";

    public String simpleReversal(String source, long originalId)
            throws AuthorisationException;

    public String simpleBDC(String source)
            throws AuthorisationException;

    public String simplePurchase(String source, String card, String amount)
            throws AuthorisationException;

    public String balanceInquiry(String source, String card)
            throws AuthorisationException;
}

/*
 * Created on Oct 8, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package lv.bank.cards.auth.client;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RTPSAuthHelper {

    public static ISOMsg prepare1200(String fld3, String fld4, String fld22, String fld24, String fld25) throws ISOException {
        ISOMsg m = new ISOMsg();
//		"<iso>" +
//		"<fld000>1200</fld000>" +
//		"<fld003>" + fld3 + "</fld003>" +   //Processing code
//		"<fld004>" + fld4 + "</fld004>" +   //Amount
//		"<fld022>" + fld22 + "</fld022>" +  //POINT code
//		"<fld024>" + fld24 + "</fld024>" +  //Function code
//		"<fld025>" + fld25 + "</fld025>" +  //Message reason code
//		"</iso>";
        m.set(new ISOField(3, fld3));
        m.set(new ISOField(4, fld4));
        m.set(new ISOField(22, fld22));
        m.set(new ISOField(24, fld24));
        m.set(new ISOField(25, fld25));
        return m;
    }

    public static ISOMsg prepareBalanceEnquiry(String card, String expiry) throws ISOException {
        ISOMsg m = prepare1200("310000", "0", "XXXXXXXXXXXX", "108", "1508");
        //  procCode,amt,pointcode,         function, message reason
        m.set(new ISOField(2, card)); //Card
        m.set(new ISOField(14, expiry)); //expiry
        return m;
    }
//
//	public static ISOMsg prepareAuthRequest(String card, String expiry, int amount) throws ISOException {
//			ISOMsg m =  prepare1200("000000", new Integer(amount).toString(), "XXXXXXXXXXXX", "200", "1508");
//												   //  procCode,amt,pointcode,         function, message reason
//			m.set (new ISOField (2, card)); //Card
//			m.set (new ISOField (14, expiry)); //expiry
//			return m;
//	}


}

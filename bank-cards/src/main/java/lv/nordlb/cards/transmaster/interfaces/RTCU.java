package lv.nordlb.cards.transmaster.interfaces;

import lv.bank.cards.core.utils.DataIntegrityException;

import javax.ejb.Local;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(targetNamespace = "http://util.rtcu.cards.bank.lv/")
@SOAPBinding(style=Style.RPC)
@Local
public interface RTCU {

	String COMP_NAME="java:comp/env/ejb/RTCU";
	String JNDI_NAME="java:app/bankCards/RTCUBean!lv.nordlb.cards.transmaster.interfaces.RTCU";

	String RTCUNGCall( String cmd ) throws DataIntegrityException;

   String QueryCall( String cmd ) throws DataIntegrityException;
}

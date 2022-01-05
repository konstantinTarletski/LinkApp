package lv.bank.cards.soap.api.atm.soap.servise;

import lv.bank.cards.soap.api.atm.soap.types.AutoPayment;
import lv.bank.cards.soap.api.atm.soap.types.AutoPaymentResponse;
import lv.bank.cards.soap.api.atm.soap.types.CancelRequest;
import lv.bank.cards.soap.api.atm.soap.types.CancelRequestResponse;
import lv.bank.cards.soap.api.atm.soap.types.GetPayment;
import lv.bank.cards.soap.api.atm.soap.types.GetPaymentResponse;
import lv.bank.cards.soap.api.atm.soap.types.Payment;
import lv.bank.cards.soap.api.atm.soap.types.PaymentResponse;
import lv.bank.cards.soap.api.atm.soap.types.PaymentServerFault;
import lv.bank.cards.soap.api.atm.soap.types.Request;
import lv.bank.cards.soap.api.atm.soap.types.RequestResponse;
import lv.bank.cards.soap.api.atm.soap.types.ReturnPayment;
import lv.bank.cards.soap.api.atm.soap.types.ReturnPaymentResponse;
import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebService;

/**
 * This Web Service is generated from specific WSDL file to unsure that ATM can use it.
 * We cannot change settings in ATM and that is reason why we generated Web Service from specific WSDL which is in ATM documentation.
 * Currently, none of methods are implemented
 *
 * @author saldabols
 */
@WebService(targetNamespace = "urn:PaymentServer", serviceName = "PaymentServerService", portName = "PaymentServerPort", wsdlLocation = "/META-INF/PaymentServerService.wsdl")
@Stateless
@WebContext(contextRoot = "BankCardsSoapWS", urlPattern = "/PaymentServerPort")
@HandlerChain(file = "/META-INF/handlers.xml")
public class PaymentServerPortImpl implements PaymentServerPort {

    public RequestResponse request(Request body) throws PaymentServerFault {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public AutoPaymentResponse autoPayment(AutoPayment body)
            throws PaymentServerFault {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public PaymentResponse payment(Payment body) throws PaymentServerFault {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public CancelRequestResponse cancelRequest(CancelRequest body)
            throws PaymentServerFault {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public ReturnPaymentResponse returnPayment(ReturnPayment body)
            throws PaymentServerFault {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public GetPaymentResponse getPayment(GetPayment body)
            throws PaymentServerFault {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}

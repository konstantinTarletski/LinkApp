package lv.bank.cards.soap.api.atm.soap.servise;

import lv.bank.cards.soap.api.atm.soap.types.AutoPayment;
import lv.bank.cards.soap.api.atm.soap.types.AutoPaymentResponse;
import lv.bank.cards.soap.api.atm.soap.types.CancelRequest;
import lv.bank.cards.soap.api.atm.soap.types.CancelRequestResponse;
import lv.bank.cards.soap.api.atm.soap.types.GetPayment;
import lv.bank.cards.soap.api.atm.soap.types.GetPaymentResponse;
import lv.bank.cards.soap.api.atm.soap.types.ObjectFactory;
import lv.bank.cards.soap.api.atm.soap.types.Payment;
import lv.bank.cards.soap.api.atm.soap.types.PaymentResponse;
import lv.bank.cards.soap.api.atm.soap.types.PaymentServerFault;
import lv.bank.cards.soap.api.atm.soap.types.Request;
import lv.bank.cards.soap.api.atm.soap.types.RequestResponse;
import lv.bank.cards.soap.api.atm.soap.types.ReturnPayment;
import lv.bank.cards.soap.api.atm.soap.types.ReturnPaymentResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.4.2
 * 2021-11-04T13:59:23.577+02:00
 * Generated source version: 3.4.2
 *
 */
@WebService(targetNamespace = "urn:PaymentServer", name = "PaymentServerPort")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface PaymentServerPort {

    @WebMethod(operationName = "Request", action = "urn:PaymentServer")
    @WebResult(name = "RequestResponse", targetNamespace = "urn:PaymentServer", partName = "body")
    public RequestResponse request(

        @WebParam(partName = "body", name = "Request", targetNamespace = "urn:PaymentServer")
                Request body
    ) throws PaymentServerFault;

    @WebMethod(operationName = "CancelRequest", action = "urn:PaymentServer")
    @WebResult(name = "CancelRequestResponse", targetNamespace = "urn:PaymentServer", partName = "body")
    public CancelRequestResponse cancelRequest(

        @WebParam(partName = "body", name = "CancelRequest", targetNamespace = "urn:PaymentServer")
                CancelRequest body
    ) throws PaymentServerFault;

    @WebMethod(operationName = "ReturnPayment", action = "urn:PaymentServer")
    @WebResult(name = "ReturnPaymentResponse", targetNamespace = "urn:PaymentServer", partName = "body")
    public ReturnPaymentResponse returnPayment(

        @WebParam(partName = "body", name = "ReturnPayment", targetNamespace = "urn:PaymentServer")
                ReturnPayment body
    ) throws PaymentServerFault;

    @WebMethod(operationName = "GetPayment", action = "urn:PaymentServer")
    @WebResult(name = "GetPaymentResponse", targetNamespace = "urn:PaymentServer", partName = "body")
    public GetPaymentResponse getPayment(

        @WebParam(partName = "body", name = "GetPayment", targetNamespace = "urn:PaymentServer")
                GetPayment body
    ) throws PaymentServerFault;

    @WebMethod(operationName = "AutoPayment", action = "urn:PaymentServer")
    @WebResult(name = "AutoPaymentResponse", targetNamespace = "urn:PaymentServer", partName = "body")
    public AutoPaymentResponse autoPayment(

        @WebParam(partName = "body", name = "AutoPayment", targetNamespace = "urn:PaymentServer")
                AutoPayment body
    ) throws PaymentServerFault;

    @WebMethod(operationName = "Payment", action = "urn:PaymentServer")
    @WebResult(name = "PaymentResponse", targetNamespace = "urn:PaymentServer", partName = "body")
    public PaymentResponse payment(

        @WebParam(partName = "body", name = "Payment", targetNamespace = "urn:PaymentServer")
                Payment body
    ) throws PaymentServerFault;
}

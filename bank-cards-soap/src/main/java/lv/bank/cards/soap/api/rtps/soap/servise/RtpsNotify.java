package lv.bank.cards.soap.api.rtps.soap.servise;

import lv.bank.cards.soap.api.rtps.soap.types.Notify;
import lv.bank.cards.soap.api.rtps.soap.types.NotifyResponse;
import lv.bank.cards.soap.api.rtps.soap.types.ObjectFactory;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface RtpsNotify {

    @WebResult(name = "notifyResponse", targetNamespace = "urn:SoapOut", partName = "body")
    NotifyResponse notify(

            @WebParam(partName = "body", name = "notify", targetNamespace = "urn:SoapOut")
                    Notify body
    );

}

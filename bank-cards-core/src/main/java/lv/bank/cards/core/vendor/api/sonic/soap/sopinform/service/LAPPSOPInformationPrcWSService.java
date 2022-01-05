package lv.bank.cards.core.vendor.api.sonic.soap.sopinform.service;

import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.utils.LogXmlHandler;
import lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.service.LAPPIBSMSNotifPrcWSService;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.handler.Handler;
import java.util.List;
import java.util.Map;

@WebServiceClient(name = "LAPP_SOPInformationPrcWSService",
        targetNamespace = "urn:Processes_BASE/processes/linkapp/sopinformation/LAPP_SOPInformationPrcWS")
public class LAPPSOPInformationPrcWSService extends Service {

    public final static QName SERVICE = new QName("urn:Processes_BASE/processes/linkapp/sopinformation/LAPP_SOPInformationPrcWS", "LAPP_SOPInformationPrcWSService");
    public final static QName LAPPSOPInformationPrcWSPort = new QName("urn:Processes_BASE/processes/linkapp/sopinformation/LAPP_SOPInformationPrcWS", "LAPP_SOPInformationPrcWSPort");
    public final static String WSDL_LOCAL_PATH = "sopinform.wsdl";

    public LAPPSOPInformationPrcWSService() {
        super(LAPPSOPInformationPrcWSService.class.getClassLoader().getResource(WSDL_LOCAL_PATH), SERVICE);
    }

    @WebEndpoint(name = "LAPP_SOPInformationPrcWSPort")
    public LAPPSOPInformationPrcWSPortType getLAPPSOPInformationPrcWSPort() {
        LAPPSOPInformationPrcWSPortType port = super.getPort(LAPPSOPInformationPrcWSPort, LAPPSOPInformationPrcWSPortType.class);

        String sonicHost = LinkAppProperties.getSonicHost();
        String sonicSoapPort = LinkAppProperties.getSonicPortSoap();
        String serviceUrl = sonicHost + ":" + sonicSoapPort + "/services/lapp/sopinform?wsdl";
        BindingProvider bindingProvider = ((BindingProvider) port);

        Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceUrl);

        //Should not use standard timeouts, because user should have small timout for not to wait long
        requestContext.put("javax.xml.ws.client.connectionTimeout", 2000); // Timeout in millis = 3 s
        requestContext.put("javax.xml.ws.client.receiveTimeout", 3000); // Timeout in millis = 2 s
        //requestContext.put("javax.xml.ws.client.connectionTimeout", Integer.parseInt(LinkAppProperties.getSonicTimeout()));
        //requestContext.put("javax.xml.ws.client.receiveTimeout", Integer.parseInt(LinkAppProperties.getSonicTimeout()));

        List<Handler> handlerList = bindingProvider.getBinding().getHandlerChain();
        handlerList.add(new LogXmlHandler());
        bindingProvider.getBinding().setHandlerChain(handlerList);
        return port;
    }


}

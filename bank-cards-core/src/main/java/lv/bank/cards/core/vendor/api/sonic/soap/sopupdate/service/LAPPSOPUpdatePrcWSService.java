package lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.service;

import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.utils.LogXmlHandler;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.handler.Handler;
import java.util.List;
import java.util.Map;

@WebServiceClient(name = "LAPP_SOPUpdatePrcWSService",
        targetNamespace = "urn:Processes_BASE/processes/linkapp/sopupdate/LAPP_SOPUpdatePrcWS")
public class LAPPSOPUpdatePrcWSService extends Service {

    public final static QName SERVICE = new QName("urn:Processes_BASE/processes/linkapp/sopupdate/LAPP_SOPUpdatePrcWS", "LAPP_SOPUpdatePrcWSService");
    public final static QName LAPPSOPUpdatePrcWSPort = new QName("urn:Processes_BASE/processes/linkapp/sopupdate/LAPP_SOPUpdatePrcWS", "LAPP_SOPUpdatePrcWSPort");
    public final static String WSDL_LOCAL_PATH = "sopupdate.wsdl";

    public LAPPSOPUpdatePrcWSService() {
        super(LAPPSOPUpdatePrcWSService.class.getClassLoader().getResource(WSDL_LOCAL_PATH), SERVICE);
    }

    @WebEndpoint(name = "LAPP_SOPUpdatePrcWSPort")
    public LAPPSOPUpdatePrcWSPortType getLAPPSOPUpdatePrcWSPort() {
        LAPPSOPUpdatePrcWSPortType port = super.getPort(LAPPSOPUpdatePrcWSPort, LAPPSOPUpdatePrcWSPortType.class);

        String sonicHost = LinkAppProperties.getSonicHost();
        String sonicSoapPort = LinkAppProperties.getSonicPortSoap();
        String serviceUrl = sonicHost + ":" + sonicSoapPort + "/services/lapp/sopupdate?wsdl";
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

package lv.bank.cards.core.vendor.api.sonic.soap.ibsmsnotif.service;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@WebServiceClient(name = "LAPP_IBSMSNotifPrcWSService", targetNamespace = "urn:Processes_BASE/processes/linkapp/notif/LAPP_IBSMSNotifPrcWS")
public class LAPPIBSMSNotifPrcWSService extends Service {

    public final static QName SERVICE = new QName("urn:Processes_BASE/processes/linkapp/notif/LAPP_IBSMSNotifPrcWS", "LAPP_IBSMSNotifPrcWSService");
    public final static QName LAPPIBSMSNotifPrcWSPort = new QName("urn:Processes_BASE/processes/linkapp/notif/LAPP_IBSMSNotifPrcWS", "LAPP_IBSMSNotifPrcWSPort");
    public final static String WSDL_LOCAL_PATH = "ibsmsnotif.wsdl";

    public LAPPIBSMSNotifPrcWSService() {
        super(LAPPIBSMSNotifPrcWSService.class.getClassLoader().getResource(WSDL_LOCAL_PATH), SERVICE);
    }

    @WebEndpoint(name = "LAPP_IBSMSNotifPrcWSPort")
    public LAPPIBSMSNotifPrcWSPortType getLAPPIBSMSNotifPrcWSPort() {
        LAPPIBSMSNotifPrcWSPortType port = super.getPort(LAPPIBSMSNotifPrcWSPort, LAPPIBSMSNotifPrcWSPortType.class);

        String sonicHost = LinkAppProperties.getSonicHost();
        String sonicSoapPort = LinkAppProperties.getSonicPortSoap();
        String serviceUrl = sonicHost + ":" + sonicSoapPort + "/services/lapp/ibsmsnotif?wsdl";
        BindingProvider bindingProvider = ((BindingProvider) port);

        Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceUrl);
        requestContext.put("javax.xml.ws.client.connectionTimeout", Integer.parseInt(LinkAppProperties.getSonicTimeout()));
        requestContext.put("javax.xml.ws.client.receiveTimeout", Integer.parseInt(LinkAppProperties.getSonicTimeout()));

        List<Handler> handlerList = bindingProvider.getBinding().getHandlerChain();
        handlerList.add(new LogXmlHandler());
        bindingProvider.getBinding().setHandlerChain(handlerList);
        return port;
    }

}

package lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.service;

import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.utils.LogXmlHandler;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.SOPInformationQueryHeaderLVT;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.SystemNameT;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.TodoCountryListT;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.handler.Handler;
import java.util.List;
import java.util.Map;

@WebServiceClient(name = "LAPP_CashInDNBNordeaPrcWSService",
        targetNamespace = "urn:Processes_BASE/processes/linkapp/cashindnbnordea/LAPP_CashInDNBNordeaPrcWS")
public class LAPPCashInDNBNordeaPrcWSService extends Service {

    public final static QName SERVICE = new QName("urn:Processes_BASE/processes/linkapp/cashindnbnordea/LAPP_CashInDNBNordeaPrcWS", "LAPP_CashInDNBNordeaPrcWSService");
    public final static QName LAPPCashInDNBNordeaPrcWSPort = new QName("urn:Processes_BASE/processes/linkapp/cashindnbnordea/LAPP_CashInDNBNordeaPrcWS", "LAPP_CashInDNBNordeaPrcWSPort");
    public final static String WSDL_LOCAL_PATH = "cashindnbnordea.wsdl";

    public LAPPCashInDNBNordeaPrcWSService() {
        super(LAPPCashInDNBNordeaPrcWSService.class.getClassLoader().getResource(WSDL_LOCAL_PATH), SERVICE);
    }

    @WebEndpoint(name = "LAPP_CashInDNBNordeaPrcWSPort")
    public LAPPCashInDNBNordeaPrcWSPortType getLAPPCashInDNBNordeaPrcWSPort() {
        //return super.getPort(LAPPCashInDNBNordeaPrcWSPort, LAPPCashInDNBNordeaPrcWSPortType.class);
        LAPPCashInDNBNordeaPrcWSPortType port = super.getPort(LAPPCashInDNBNordeaPrcWSPort, LAPPCashInDNBNordeaPrcWSPortType.class);

        String sonicHost = LinkAppProperties.getSonicHost();
        String sonicSoapPort = LinkAppProperties.getSonicPortSoap();
        String serviceUrl = sonicHost + ":" + sonicSoapPort + "/services/lapp/cashindnbnordea?wsdl";
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

    public static SOPInformationQueryHeaderLVT getHeaderLV(TodoCountryListT country) {
        SOPInformationQueryHeaderLVT header = new SOPInformationQueryHeaderLVT();
        header.setQueryName("CashInDNBNordea");
        header.setLanguage("LV");
        SystemNameT system = new SystemNameT();
        system.setCountry(country);
        system.setValue("0");
        header.setSystemName(system);
        return header;
    }

}

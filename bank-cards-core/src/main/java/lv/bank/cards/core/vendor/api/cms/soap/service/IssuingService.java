package lv.bank.cards.core.vendor.api.cms.soap.service;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.utils.LogXmlHandler;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.handler.Handler;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

@Slf4j
@WebServiceClient(name = "IssuingService", targetNamespace = "urn:IssuingWS")
public class IssuingService extends Service {

    public final static QName SERVICE = new QName("urn:IssuingWS", "IssuingService");
    public final static QName Issuing = new QName("urn:IssuingWS", "Issuing");
    public final static String WSDL_LOCAL_PATH = "Issuing.wsdl";

    static {
        try {
            disableSslVerification();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
            //log.warn("Can not disable SSL verification");
        }
    }

    public IssuingService() {
        super(IssuingService.class.getClassLoader().getResource(WSDL_LOCAL_PATH), SERVICE);
    }

    @WebEndpoint(name = "Issuing")
    public IssuingPort getIssuing(String username, String password, String serviceUrl) {
        IssuingPort port = super.getPort(Issuing, IssuingPort.class);

        BindingProvider bindingProvider = ((BindingProvider) port);

        Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(BindingProvider.USERNAME_PROPERTY, username);
        requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceUrl);

        List<Handler> handlerList = bindingProvider.getBinding().getHandlerChain();
        handlerList.add(new LogXmlHandler());
        bindingProvider.getBinding().setHandlerChain(handlerList);
        return port;
    }

    //TODO where to put ? !!!!
    protected static void disableSslVerification() throws NoSuchAlgorithmException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}

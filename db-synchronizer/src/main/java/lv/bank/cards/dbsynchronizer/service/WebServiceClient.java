package lv.bank.cards.dbsynchronizer.service;

import lv.bank.cards.rtcu.util.BankCardsWSWrapperDelegate;
import lv.bank.cards.rtcu.util.BankCardsWSWrapperService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

//@Slf4j
public class WebServiceClient {

    private static final Logger log = Logger.getLogger(WebServiceClient.class);
    private URL url;
    private BankCardsWSWrapperDelegate wrapper;

    public WebServiceClient(String wsdlUrl) {
        URL baseUrl = WebServiceClient.class.getResource(".");
        try {
            url = new URL(baseUrl, wsdlUrl);
            log.info(url.toString());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("There is no webservice at : " + wsdlUrl);
        }
        BankCardsWSWrapperService service = new BankCardsWSWrapperService(url);
        this.wrapper = service.getBankCardsWSWrapperPort();
    }

    public void addCardToRMS(String card, String expiry, String centreId) {
        String request =
                "<do what=\"add-card-to-rms\" template=\"deliveryTimeBlock\" operator=\"LinkApp\">" +
                        "<card>" + card + "</card>" +
                        "<expiry>" + expiry + "</expiry>" +
                        "<centre-id>" + centreId + "</centre-id>" +
                        "</do>";
        String response = wrapper.rtcungCall(request);
        log.info(response);
    }

    public void addCardToRMS(String card, String expiry) {
        String request =
                "<do what=\"add-card-to-rms\" template=\"deliveryTimeBlock\" operator=\"LinkApp\">" +
                        "<card>" + card + "</card>" +
                        "<expiry>" + expiry + "</expiry>" +
                        "</do>";
        String response = wrapper.rtcungCall(request);
        log.info(response);
    }

    public void addElectronicCommerceBlock(String card) {
        String request = "<do what=\"add-card-to-rms\" template=\"electronicCommerceBlock\" "
                + "operator=\"LinkApp\"><card>" + card + "</card></do>";
        String response = wrapper.rtcungCall(request);
        log.info(response);
    }

}

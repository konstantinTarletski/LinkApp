package lv.bank.cards.soap.api.rtps.soap.servise;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.service.LAPPCashInDNBNordeaPrcWSPortType;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.service.LAPPCashInDNBNordeaPrcWSService;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.CashInDNBNordeaQueryBodyLVT;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.QueryT;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.SourceT;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.TodoCountryListT;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.TypeT;
import lv.bank.cards.soap.api.rtps.soap.types.Field;
import lv.bank.cards.soap.api.rtps.soap.types.Notify;
import lv.bank.cards.soap.api.rtps.soap.types.NotifyResponse;
import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@WebService
@Stateless
//Need to be renamed to 'BankCardsWS', done in 'standalone.xml'
@WebContext(contextRoot = "BankCardsSoapWS", urlPattern = "/RtpsSoapNotify")
@HandlerChain(file = "/META-INF/handlers.xml")
@Slf4j
public class RtpsSoapNotifyImpl implements RtpsNotify {

    private static final String FLD_002 = "FLD_002";
    private static final String FLD_004 = "FLD_004";
    private static final String FLD_006 = "FLD_006";
    private static final String FLD_007 = "FLD_007";
    private static final String FLD_011 = "FLD_011";
    private static final String FLD_037 = "FLD_037";
    private static final String FLD_038 = "FLD_038";
    private static final String FLD_041 = "FLD_041";
    private static final String FLD_043 = "FLD_043";
    private static final String FLD_049 = "FLD_049";
    private static final String FLD_051 = "FLD_051";
    private static final String MSG_TYPE_IN = "MSG_TYPE_IN";

    protected CardsDAO cardsDAO;
    protected LAPPCashInDNBNordeaPrcWSService service;
    protected LAPPCashInDNBNordeaPrcWSPortType port;

    private static final Map<String, SetCashInBodyValue> MAPPING;

    static {
        Map<String, SetCashInBodyValue> map = new HashMap<>();
        map.put(FLD_002, new SetCashInBodyValue.SetFLD_002());
        map.put(FLD_004, new SetCashInBodyValue.SetFLD_004());
        map.put(FLD_006, new SetCashInBodyValue.SetFLD_006());
        map.put(FLD_007, new SetCashInBodyValue.SetFLD_007());
        map.put(FLD_011, new SetCashInBodyValue.SetFLD_011());
        map.put(FLD_037, new SetCashInBodyValue.SetFLD_037());
        map.put(FLD_038, new SetCashInBodyValue.SetFLD_038());
        map.put(FLD_041, new SetCashInBodyValue.SetFLD_041());
        map.put(FLD_043, new SetCashInBodyValue.SetFLD_043());
        map.put(FLD_049, new SetCashInBodyValue.SetFLD_049());
        map.put(FLD_051, new SetCashInBodyValue.SetFLD_051());
        map.put(MSG_TYPE_IN, new SetCashInBodyValue.SetType());
        MAPPING = Collections.unmodifiableMap(map);
    }

    public RtpsSoapNotifyImpl() {
        cardsDAO = new CardsDAOHibernate();
        service = new LAPPCashInDNBNordeaPrcWSService();
        port = service.getLAPPCashInDNBNordeaPrcWSPort();
    }

    @Override
    public NotifyResponse notify(Notify body) {
        log.info("notify BEGIN");

        if (body.getFields() == null || body.getFields().getItem() == null || body.getFields().getItem().isEmpty()) {
            log.info("No items to process");
            throw new RuntimeException("Missing cash in information");
        }

        CashInDNBNordeaQueryBodyLVT cashInBody = new CashInDNBNordeaQueryBodyLVT();
        cashInBody.setTYPE(TypeT.T);
        cashInBody.setSOURCE(SourceT.N);

        TodoCountryListT cardCountry = null;

        for (Field field : body.getFields().getItem()) {
            if (MAPPING.containsKey(field.getName())) {
                MAPPING.get(field.getName()).setValue(cardsDAO, cashInBody, field.getValue());
                if (FLD_002.equals(field.getName())) {
                    PcdCard card = cardsDAO.findByCardNumber(field.getValue());
                    if (card != null && card.getPcdAccounts() != null && !card.getPcdAccounts().isEmpty()) {
                        cashInBody.setACCOUNT(card.getPcdAccounts().iterator().next().getPcdAccParam().getUfield5());
                        cardCountry = TodoCountryListT.valueOf(card.getRegion());
                    }
                }
            }
        }

        try {
            QueryT query = new QueryT();
            query.setQueryBody(cashInBody);
            query.setQueryHeader(LAPPCashInDNBNordeaPrcWSService.getHeaderLV(cardCountry));
            log.info("calling Sonic lappCashInDNBNordeaPrcWS cardCountry = {}", cardCountry);
            long time = System.currentTimeMillis();
            port.lappCashInDNBNordeaPrcWS(query);

            log.info("Sonic done in ms " + (System.currentTimeMillis() - time));

        } catch (Exception e) {
            log.error("notify", e);
            try {
                SOAPFactory factory = SOAPFactory.newInstance();
                SOAPFault fault = factory.createFault();
                fault.setFaultString(e.getMessage());
                throw new SOAPFaultException(fault);
            } catch (SOAPException e1) {
                throw new RuntimeException(e.getMessage());
            }
        }

        NotifyResponse response = new NotifyResponse();
        response.setName("OK");
        return response;
    }
}


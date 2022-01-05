package lv.bank.cards.soap.api.atm.soap.servise;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.service.LAPPCashInDNBNordeaPrcWSPortType;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.service.LAPPCashInDNBNordeaPrcWSService;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.CashInDNBNordeaQueryBodyLVT;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.QueryT;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.SourceT;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.TodoCountryListT;
import lv.bank.cards.core.vendor.api.sonic.soap.cashindnbnordea.types.TypeT;
import lv.bank.cards.soap.api.atm.soap.types.Block;
import lv.bank.cards.soap.api.atm.soap.types.BlockResponse;
import lv.bank.cards.soap.api.atm.soap.types.Detail;
import lv.bank.cards.soap.api.atm.soap.types.Get;
import lv.bank.cards.soap.api.atm.soap.types.GetClientID;
import lv.bank.cards.soap.api.atm.soap.types.GetClientIDResponse;
import lv.bank.cards.soap.api.atm.soap.types.GetList;
import lv.bank.cards.soap.api.atm.soap.types.GetListResponse;
import lv.bank.cards.soap.api.atm.soap.types.GetResponse;
import lv.bank.cards.soap.api.atm.soap.types.PaymentServerException;
import lv.bank.cards.soap.api.atm.soap.types.PaymentServerFault;
import lv.bank.cards.soap.api.atm.soap.types.Return;
import lv.bank.cards.soap.api.atm.soap.types.ReturnResponse;
import lv.bank.cards.soap.api.atm.soap.types.Unblock;
import lv.bank.cards.soap.api.atm.soap.types.UnblockResponse;
import lv.bank.cards.soap.api.atm.soap.types.WriteOff;
import lv.bank.cards.soap.api.atm.soap.types.WriteOffResponse;
import lv.bank.cards.soap.api.rtps.soap.servise.SetCashInBodyValue;
import lv.bank.cards.soap.service.AtmAdvertService;
import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This Web Service is generated from specific WSDL file to unsure that ATM can use it.
 * We cannot change settings in ATM and that is reason why we generated Web Service from specific WSDL which is in ATM documentation.
 * For now only getList and writeOff methods are implemented.
 *
 * @author saldabols
 */
@WebService(targetNamespace = "urn:PaymentServer", serviceName = "ServiceProviderService", portName = "ServiceProviderPort", wsdlLocation = "/META-INF/PaymentServerService.wsdl")
@Stateless
@WebContext(contextRoot = "BankCardsSoapWS", urlPattern = "/ServiceProviderPort")
@HandlerChain(file = "/META-INF/handlers.xml")
@Slf4j
public class ServiceProviderPortImpl implements ServiceProviderPort {

    private static final String FLD_002 = "pan2";
    private static final String FLD_004 = "amount";
    //	private static final String FLD_006 = "amount";
    private static final String FLD_007 = "auth_time";
    private static final String FLD_011 = "auth_stan";
    private static final String FLD_037 = "auth_ref_number";
    private static final String FLD_038 = "auth_appr_code";
    private static final String FLD_041 = "terminal_id";
    private static final String FLD_043 = "merchant_name";
    private static final String FLD_049 = "ccy_code";
    private static final String FLD_051 = "cardholder_ccy_code";

    private static final Map<String, SetCashInBodyValue> MAPPING;

    static {
        Map<String, SetCashInBodyValue> map = new HashMap<>();
        map.put(FLD_002, new SetCashInBodyValue.SetFLD_002());
        map.put(FLD_004, new SetCashInBodyValue.SetFLD_004_and_006());
        //map.put(FLD_006, new SetCashInBodyValue.SetFLD_006());
        map.put(FLD_007, new SetCashInBodyValue.SetFLD_007());
        map.put(FLD_011, new SetCashInBodyValue.SetFLD_011());
        map.put(FLD_037, new SetCashInBodyValue.SetFLD_037());
        map.put(FLD_038, new SetCashInBodyValue.SetFLD_038());
        map.put(FLD_041, new SetCashInBodyValue.SetFLD_041());
        map.put(FLD_043, new SetCashInBodyValue.SetFLD_043());
        map.put(FLD_049, new SetCashInBodyValue.SetFLD_049());
        map.put(FLD_051, new SetCashInBodyValue.SetFLD_051());
        MAPPING = Collections.unmodifiableMap(map);
    }

    protected CardsDAO cardsDAO;
    protected LAPPCashInDNBNordeaPrcWSService service;
    protected LAPPCashInDNBNordeaPrcWSPortType port;
    protected AtmAdvertService atmAdvertService;

    public ServiceProviderPortImpl() {
        cardsDAO = new CardsDAOHibernate();
        service = new LAPPCashInDNBNordeaPrcWSService();
        port = service.getLAPPCashInDNBNordeaPrcWSPort();
        atmAdvertService = new AtmAdvertService();
    }

    @Override
    public GetClientIDResponse getClientID(GetClientID body)
            throws PaymentServerFault {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Because of ATM settings limitation this method is used to get advertisement ID.
     * Which is provided as error code
     */
    @Override
    public GetListResponse getList(GetList body) throws PaymentServerFault {
        String card = null;
        String terminal = null;
        String answer = null;
        String type = null;
        for (Detail detail : body.getDetails().getItem()) {
            if ("card".equals(detail.getName())) {
                card = detail.getValue();
            } else if ("terminal_id".equals(detail.getName())) {
                terminal = detail.getValue();
            } else if ("adv_answer".equals(detail.getName())) {
                answer = detail.getValue();
            } else if ("op_type".equals(detail.getName())) {
                type = detail.getValue();
            }
        }
        log.info("ATM -> card:" + card + " terminal:" + terminal + " answer:" + answer + " type:" + type);
        String adId = "0";
        try {
            adId = atmAdvertService.handle(card, terminal, answer, type);
        } catch (Exception e) {
            log.warn("atmAdvertService.handle error = ", e);
        }
        log.info("ATM <- advertisment:" + adId);
        PaymentServerException faultInfo = new PaymentServerException();
        faultInfo.setError(adId);
        faultInfo.setProvider("LinkApp");
        faultInfo.setDescription("Show atm advertisment");
        throw new PaymentServerFault("Show adS", faultInfo);
    }

    @Override
    public ReturnResponse _return(Return body) throws PaymentServerFault {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public GetResponse get(Get body) throws PaymentServerFault {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public BlockResponse block(Block body) throws PaymentServerFault {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public UnblockResponse unblock(Unblock body) throws PaymentServerFault {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public WriteOffResponse writeOff(WriteOff body) throws PaymentServerFault {
        if (body != null && body.getDetails() != null) {
            CashInDNBNordeaQueryBodyLVT cashInBody = new CashInDNBNordeaQueryBodyLVT();
            cashInBody.setTYPE(TypeT.T);
            cashInBody.setSOURCE(SourceT.D);
            for (Detail detail : body.getDetails().getItem()) {
                if (MAPPING.containsKey(detail.getName())) {
                    MAPPING.get(detail.getName()).setValue(cardsDAO, cashInBody, detail.getValue());
                }
            }
            if (cashInBody.getFLD002() != null) {
                QueryT query = new QueryT();
                query.setQueryBody(cashInBody);
                query.setQueryHeader(LAPPCashInDNBNordeaPrcWSService.getHeaderLV(TodoCountryListT.LV));
                long time = System.currentTimeMillis();
                port.lappCashInDNBNordeaPrcWS(query);

                log.info("Sonic done in ms " + (System.currentTimeMillis() - time));
            }
        }
        return new WriteOffResponse();
    }

}

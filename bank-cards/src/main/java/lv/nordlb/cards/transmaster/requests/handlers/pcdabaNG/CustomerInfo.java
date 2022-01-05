package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.entity.rtps.StipRmsStoplist;
import lv.bank.cards.core.rtps.dao.StipLocksDAO;
import lv.bank.cards.core.rtps.impl.StipLocksDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.soap.service.dto.OtbDo;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.nordlb.cards.transmaster.fo.interfaces.StipCardManager;
import lv.nordlb.cards.transmaster.fo.interfaces.TMFManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.OtbService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

public class CustomerInfo extends SubRequestHandler {

    protected PcdabaNGManager pcdabaNGManager = null;
    protected TMFManager tmfManager = null;
    protected StipCardManager stipCardManager = null;
    protected StipLocksDAO stipLocksDAO;
    protected OtbService otbService;

    public static final String DATE_FORMAT_FOR_CARD_INFO = "MM.yyyy";

    public CustomerInfo() throws RequestPreparationException {
        super();
        stipLocksDAO = new StipLocksDAOHibernate();
        otbService = new OtbService(stipLocksDAO);
        try {
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
            tmfManager = (TMFManager) new InitialContext().lookup(TMFManager.JNDI_NAME);
            stipCardManager = (StipCardManager) new InitialContext().lookup(StipCardManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        DateFormat dateFormatCardInfo = new SimpleDateFormat(this.DATE_FORMAT_FOR_CARD_INFO);

        String id = getStringFromNode("/do/id");
        if (id == null) throw new RequestFormatException("Specify customer identifier", this);

        String country = getStringFromNode("/do/country");
        if (country == null || country.isEmpty()) {
            country = Constants.DEFAULT_COUNTRY_LV;
        }

        List<PcdClient> clnts = pcdabaNGManager.getClientInfo(id, country);
        //Iterator<PcdAccount> accs = clnt.getPcdAccounts().iterator();
        ResponseElement custInfo = createElement("customer-info");
        if (clnts == null || clnts.isEmpty()) {// throw new RequestProcessingSoftException("Specified client not found",this);
            //No data to process return empty element
            return;
        }
        custInfo.createElement("customer-id", id);
        ResponseElement accts = custInfo.createElement("accounts-list");
        boolean posShowed = false;
        for (PcdClient clnt : clnts) {
            if (clnt.getPcdAccounts().size() > 0) {
                for (PcdAccount acc : clnt.getPcdAccounts()) {
                    boolean accShowed = false;
                    ResponseElement accInfo = null;
                    ResponseElement cardsList = null;
                    if (acc.getPcdCards().size() > 0) {
                        for (PcdCard card : acc.getPcdCards()) {
                            if (!card.getStatus1().equals("2")) {

                                OtbDo otbDo = null;
                                if (!accShowed) {

                                    StipAccount stAcc = null;

                                    try {
                                        List<StipAccount> stAccList = tmfManager.findStipAccountsByCardNumberAndCentreId(card.getCard(),
                                                CardUtils.composeCentreIdFromPcdCard(card));
                                        if (stAccList.size() > 0) {
                                            stAcc = stAccList.get(0);
                                        }
                                    } catch (DataIntegrityException e) {
                                        e.printStackTrace();
                                    }

                                    accInfo = accts.createElement("account");
                                    accInfo.createElement("account-nr", acc.getPcdAccParam().getUfield5());
                                    if (stAcc != null) {
                                        otbDo = otbService.calculateOtb(stAcc);
                                        accInfo.createElement("balance-available", otbDo.getOtb());
                                    }

                                    accInfo.createElement("currency", acc.getPcdAccParam().getPcdCurrency().getIsoAlpha());
                                    cardsList = accInfo.createElement("cards-list");
                                    accShowed = true;
                                }

                                ResponseElement cardInfo = cardsList.createElement("card");
                                cardInfo.createElement("card-number", card.getCard());
                                cardInfo.createElement("status", card.getStatus1() == null ? "" : card.getStatus1());
                                cardInfo.createElement("brand", CardUtils.getCardType(card.getCard()));

                                List<StipRmsStoplist> rmsList = stipCardManager.getStipRmsStoplist(card.getCard(), CardUtils.composeCentreIdFromPcdCard(card), null);
                                if (rmsList.size() > 0 || card.getStatus1().equals("1")) {
                                    String blockType = "softBlock";
                                    ResponseElement blocks = cardInfo.createElement("blocks");
                                    for (StipRmsStoplist stop : rmsList) {
                                        String desc = stop.getDescription();
                                        if (desc.substring(0, 22).equals("Owner block from iNORD"))
                                            blockType = "ibBlock";
                                    }
                                    blocks.createElement("block-info", blockType);
                                }

                                cardInfo.createElement("valid-thru", dateFormatCardInfo.format(card.getExpiry1())).addAttribute("format", this.DATE_FORMAT_FOR_CARD_INFO);
                                Integer annFee = pcdabaNGManager.getAnnualFee(card.getCondSet(), acc.getPcdAccParam().getPcdCurrency().getIsoAlpha(), card.getBaseSupp().equals("1") ? false : true);
                                Integer crdFee = pcdabaNGManager.getCardFee(card.getCondSet(), acc.getPcdAccParam().getPcdCurrency().getIsoAlpha(), card.getBaseSupp().equals("1") ? false : true);
                                cardInfo.createElement("next-payment-type", annFee > 0 ? "yearly" : (crdFee > 0 ? "monthly" : ""));
                                if (annFee > 0 || crdFee > 0) {
                                    GregorianCalendar now = new GregorianCalendar();
                                    GregorianCalendar pday = new GregorianCalendar();
                                    pday.setTime((card.getRenewDate() != null ? card.getRenewDate() : card.getRecDate())); // If the card has not been renewed yet (renew_date is null), start the count from its registration date (rec_date)

                                    while (pday.before(now)) {
                                        pday.add(annFee > 0 ? GregorianCalendar.YEAR : GregorianCalendar.MONTH, 1);
                                    }
                                    cardInfo.createElement("next-payment-date", new SimpleDateFormat("yyyy-MM-dd").format(pday.getTime())).addAttribute("format", "yyyy-MM-dd");
                                }
                                if (otbDo != null) {
                                    cardInfo.createElement("balance-available", otbDo.getOtb());
                                    cardInfo.createElement("currency", acc.getPcdAccParam().getPcdCurrency().getIsoAlpha());
                                }

                            }
                        }
                    }
                }
            }

            if (!posShowed) {
                if (clnt.getRegNr() != null) {
                    if (pcdabaNGManager.getMerchantByRegNr(clnt.getRegNr()) != null) {
                        custInfo.createElement("cust-pos").createElement("pos-agreement", "1");
                    } else custInfo.createElement("cust-pos").createElement("pos-agreement", "0");
                } else custInfo.createElement("cust-pos").createElement("pos-agreement", "0");
                posShowed = true;
            }
        }
    }

}

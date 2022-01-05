package lv.bank.cards.soap.service;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.linkApp.PcdAtmAdvert;
import lv.bank.cards.core.entity.linkApp.PcdAtmAdvertPk;
import lv.bank.cards.core.entity.linkApp.PcdAtmAdvertSpecial;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.linkApp.dao.AtmAdvertDAO;
import lv.bank.cards.core.linkApp.dao.AtmAdvertSpecialDAO;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.AtmAdvertDAOHibernate;
import lv.bank.cards.core.linkApp.impl.AtmAdvertSpecialDAOHibernate;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.core.vendor.api.sonic.soap.sopinform.service.LAPPSOPInformationPrcWSPortType;
import lv.bank.cards.core.vendor.api.sonic.soap.sopinform.service.LAPPSOPInformationPrcWSService;
import lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.ClientIDUniqueClientIDQueryCriteriaT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.QueryT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.ResultT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.SOPInformationQueryBodyLVT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.SOPInformationQueryHeaderLVT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.SOPStatusListT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.SOPT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.SystemNameT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.TodoCountryListT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.service.LAPPSOPUpdatePrcWSPortType;
import lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.service.LAPPSOPUpdatePrcWSService;
import lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types.SOPInformationUpdateQueryBodyLVT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types.SOPInformationUpdateQueryHeaderLVT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types.StringWithCodeT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types.StringWithTextCodeT;
import lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types.SystemNameLVT;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import org.apache.commons.lang.StringUtils;

import javax.xml.ws.WebServiceException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AtmAdvertService {

    public static final String DEFAULT_VALUE = "0";
    public static final String QUESTION_TYPE = "QUESTION";
    public static final String ANSWER_TYPE = "ANSWER";
    private static final String LEGAL_PERSON = "J";
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("dd-MM-yyyy HH:mm"));
    private static final Map<String, Integer> ANSWER_MAP;

    static {
        Map<String, Integer> map = new HashMap<>();
        map.put("YES", 1);
        map.put("NO", 2);
        map.put("LATER", 3);
        ANSWER_MAP = Collections.unmodifiableMap(map);
    }

    protected CardsDAO cardsDAO;
    protected AtmAdvertSpecialDAO atmAdvertSpecialDAO;
    protected AtmAdvertDAO atmAdvertDAO;
    protected LAPPSOPInformationPrcWSService service;
    protected LAPPSOPInformationPrcWSPortType port;

    public AtmAdvertService() {
        super();
        cardsDAO = new CardsDAOHibernate();
        atmAdvertSpecialDAO = new AtmAdvertSpecialDAOHibernate();
        atmAdvertDAO = new AtmAdvertDAOHibernate();
        service = new LAPPSOPInformationPrcWSService();
        port = service.getLAPPSOPInformationPrcWSPort();
    }

    public String handle(String card, String terminal, String answer, String type)
            throws RequestFormatException, RequestProcessingException {

        if (!CardUtils.cardCouldBeValid(card)) { // No valid card
            log.warn("Not valid card number:" + card);
            return DEFAULT_VALUE;
        }

        if (StringUtils.isBlank(terminal)) {
            log.error("Missing terminal ID, card number:" + card);
            return DEFAULT_VALUE;
        }

        // Remove time restrictions
//		int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//		if(dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY){ // Weekend 
//			LOGGER.info("Don't show ad because weekend");
//			return DEFAULT_VALUE;
//		}
//		
//		int hour  = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//		if(hour < 9 || hour > 17){
//			LOGGER.info("Don't show ad because not working hours");
//			return DEFAULT_VALUE;
//		}


        PcdCard thisCard = cardsDAO.findByCardNumber(card);
        if (thisCard == null) { // Cannot find card
            log.error("Cannot find card:" + card);
            return DEFAULT_VALUE;
        }

        if (LEGAL_PERSON.equals(StringUtils.upperCase(StringUtils.substring(thisCard.getCondSet(), 0, 1)))) { // legal person
            log.info("Legal person card:" + card + " - no ads");
            return DEFAULT_VALUE;
        }

        if (thisCard.getIdCard() == null) { // Don't have personal code
            log.error("Card without personal code, card:" + card);
            return DEFAULT_VALUE;
        }

        if (!Constants.DEFAULT_COUNTRY_LV.equals(thisCard.getRegion())) {
            log.info("Show ATM ad only for LV cards");
            return DEFAULT_VALUE;
        }

        PcdAtmAdvertSpecial adSpecial = atmAdvertSpecialDAO.findAtmAdvertSpecial(thisCard.getIdCard(), terminal, QUESTION_TYPE.equals(type));
        if (adSpecial != null) { // Found special advertisement and will show this
            log.info("Found special advertisment " + adSpecial.getAdvertId());
            if (QUESTION_TYPE.equals(type)) {
                adSpecial.setRecDate(Calendar.getInstance().getTime());
                atmAdvertSpecialDAO.saveOrUpdate(adSpecial);
                return adSpecial.getAdvertId();
            } else if (ANSWER_TYPE.equals(type)) {
                adSpecial.setAnswer(answer);
                atmAdvertSpecialDAO.saveOrUpdate(adSpecial);
                return DEFAULT_VALUE;
            }
        }

        if (QUESTION_TYPE.equals(type)) {
            List<PcdAtmAdvert> shownAds = atmAdvertDAO.findTodayShownAds(thisCard.getIdCard());
            if (!shownAds.isEmpty()) {
                log.info("We already showed ad in last 24h");
                return DEFAULT_VALUE;
            }

            log.info("ATM get ad for: " + thisCard.getIdCard());
            Map<String, String> adsMap = getATMAdvertsFromSonic(thisCard.getIdCard());
            if (adsMap == null || adsMap.isEmpty()) {
                log.info("Don't have ads for card: " + card);
                return DEFAULT_VALUE;
            }

            List<String> ads = new ArrayList<>(adsMap.keySet());

            int adIndex = new Double((ads.size() - 0.001) * Math.random()).intValue();
            String ad = ads.get(adIndex);
            PcdAtmAdvert atmAd = new PcdAtmAdvert(
                    new PcdAtmAdvertPk(thisCard.getIdCard(), ad, Calendar.getInstance().getTime()),
                    terminal, null, adsMap.get(ad));
            atmAdvertDAO.saveOrUpdate(atmAd);
            return ad;
        } else if (ANSWER_TYPE.equals(type)) {
            log.info("ATM write answer");
            PcdAtmAdvert ad = atmAdvertDAO.findAtmAd(thisCard.getIdCard(), terminal);
            if (ad == null) {
                log.error("Cannot find record for answer, client: " + thisCard.getIdCard() + " terminal: " + terminal);
            } else {
                ad.setAnswer(answer);
                String result = sendResult(answer, thisCard.getIdCard(), ad.getAtmAdvertPk().getAdvertId(),
                        terminal, ad.getAtmAdvertPk().getRecDate(), ad.getMessage());
                log.info("Response sent to sales: answer - " + answer + " personal code - " + thisCard.getIdCard()
                        + " ad ID - " + ad.getAtmAdvertPk().getAdvertId() + " ATM - " + terminal
                        + " date - " + DATE_FORMAT.get().format(ad.getAtmAdvertPk().getRecDate())
                        + " email - " + ad.getMessage());
                ad.setMessage(ad.getMessage() + ";" + result);
                atmAdvertDAO.saveOrUpdate(ad);
            }
        }

        return DEFAULT_VALUE;
    }

    /**
     * Get list of ATM ad IDs to show for given client
     */
    protected Map<String, String> getATMAdvertsFromSonic(String personalCode) {

        Map<String, String> map = new HashMap<>();

        try {
            QueryT query = new QueryT();
            SOPInformationQueryHeaderLVT header = new SOPInformationQueryHeaderLVT();
            header.setQueryName("SOPInformation");
            SystemNameT systemName = new SystemNameT();
            systemName.setCountry(TodoCountryListT.LV);
            systemName.setValue("0");
            header.setSystemName(systemName);
            header.setXMLVersion("1.0");
            header.setLanguage("LV");
            query.setQueryHeader(header);
            SOPInformationQueryBodyLVT body = new SOPInformationQueryBodyLVT();
            ClientIDUniqueClientIDQueryCriteriaT criteria = new ClientIDUniqueClientIDQueryCriteriaT();
            criteria.setType("ClientID");
            criteria.setValue(personalCode);
            body.setCriteria(criteria);
            SOPStatusListT list = new SOPStatusListT();
            lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.StringWithTextCodeT status =
                    new lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.StringWithTextCodeT();
            status.setCode("1");
            status.setValue("11111");
            list.getSOPStatus().add(status);
            body.setSOPStatusList(list);
            lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.StringWithTextCodeT status2
                    = new lv.bank.cards.core.vendor.api.sonic.soap.sopinform.types.StringWithTextCodeT();
            status2.setCode("3");
            status2.setValue("33333");
            list.getSOPStatus().add(status2);
            body.setSOPStatusList(list);
            query.setQueryBody(body);
            long t = System.currentTimeMillis();
            ResultT result = port.lappSOPInformationPrcWS(query);
            log.info("Get ATM from Sonic in ms " + (System.currentTimeMillis() - t));
            for (SOPT sopt : result.getResultBody().getSOPList().getSOP()) {
                map.put(sopt.getProduct().getCode(), sopt.getEmailOfProductResponsible() + ";" + sopt.getProduct().getValue());
            }
        } catch (WebServiceException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                log.info("Timeout from Sonic");
            } else {
                log.info("Cannot get ATM ads", e);
            }
        }
        return map;
    }

    /**
     * Send response to sales with ATM ad answer information
     */
    protected String sendResult(String answer, String personalCode, String adNumber, String atm, Date date, String emailAndName) {

        LAPPSOPUpdatePrcWSService service = new LAPPSOPUpdatePrcWSService();
        LAPPSOPUpdatePrcWSPortType port = service.getLAPPSOPUpdatePrcWSPort();

        try {

            lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types.QueryT query =
                    new lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types.QueryT();
            SOPInformationUpdateQueryHeaderLVT header = new SOPInformationUpdateQueryHeaderLVT();
            header.setQueryName("SOPInformationUpdate");

            SystemNameLVT systemNameLVT = new SystemNameLVT();
            systemNameLVT.setCountry(lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types.TodoCountryListT.LV);
            systemNameLVT.setValue("0");
            header.setSystemName(systemNameLVT);
            header.setXMLVersion("1.0");
            header.setLanguage("LV");
            query.setQueryHeader(header);
            SOPInformationUpdateQueryBodyLVT body = new SOPInformationUpdateQueryBodyLVT();
            lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types.ClientIDUniqueClientIDQueryCriteriaT criteria =
                    new lv.bank.cards.core.vendor.api.sonic.soap.sopupdate.types.ClientIDUniqueClientIDQueryCriteriaT();
            criteria.setType("ClientID");
            criteria.setValue(personalCode);
            body.setCriteria(criteria);
            body.setChangeBy("LinkApp");
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(new Date());
            body.setChangeDate(new Date());
            StringWithTextCodeT product = new StringWithTextCodeT();
            product.setCode(adNumber);
            product.setValue(StringUtils.substringAfter(emailAndName, ";"));
            body.setProductCode(product);
            StringWithCodeT answerT = new StringWithCodeT();
            answerT.setCode(getAnswerCode(answer));
            answerT.setValue(answer);
            body.setCustomerAnswer(answerT);
            body.setEmailOfProductResponsible(StringUtils.substringBefore(emailAndName, ";"));
            query.setQueryBody(body);
            long t = System.currentTimeMillis();
            port.lappSOPUpdatePrcWS(query);
            log.info("Send ATM response email, Sonic in ms " + (System.currentTimeMillis() - t));
            return "Done";
        } catch (WebServiceException e) {
            if (e.getCause() instanceof SocketTimeoutException)
                log.error("Timeout from Sonic");
            else
                log.error("Cannot send ATM ad email", e);
            return "Cannot update ATM ad status";
        }
    }

    private static int getAnswerCode(String answer) {
        Integer code = ANSWER_MAP.get(answer);
        if (code == null)
            return 1; // Default value
        return code.intValue();
    }
}

package lv.bank.cards.link;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.cms.dao.ProductDAO;
import lv.bank.cards.core.cms.dto.IzdOfferedProductDTO;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCompany;
import lv.bank.cards.core.entity.cms.IzdOfferedProduct;
import lv.bank.cards.core.linkApp.dao.AccountsDAO;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dao.ClientsDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.DeliveryDetailsHelperBase;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAgreement;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCardRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeOrderPinEnvelope;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeRenewCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeReplaceCard;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import static org.apache.commons.lang.StringUtils.defaultIfBlank;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public abstract class MapperBase {

    protected final CommonDAO commonDAO;
    protected final CardDAO cardDAO;
    protected final CardsDAO cardsDAO;
    protected final ClientDAO clientDAO;
    protected final ClientsDAO clientsDAO;
    protected final ProductDAO productDAO;
    protected final AccountsDAO accountsDAO;

    protected IzdOfferedProduct getCorrespondingProduct(Order order) throws DataIntegrityException {

        // If product code passed from external source, we just use it. No choosing is necessary, forget about it!
        log.info("getCorrespondingProduct BEGIN");
        //		we should decode branch using BO nordlb_branches table
        IzdOfferedProductDTO mask = new IzdOfferedProductDTO();

        mask.setBankC(order.getBankc());
        mask.setGroupc(order.getGroupc());
        mask.setCcy(order.getAccountCcy());
        mask.setClType(order.getClientType());
        mask.setBin(order.getBin());
        mask.setClnCat(order.getClientCategory());
        mask.setRepDistribution(order.getRepDistributionMode());
        mask.setCardType(order.getCardType());
        mask.setAuthLevel(order.getAuthLevel());
        IzdOfferedProduct product = null;

        // <company stuff>
        if (order.getEmpCompanyRegNr() != null && !order.getEmpCompanyRegNr().isEmpty()) {
            IzdCompany thisCompany = commonDAO.findIzdCompanyByRegCodeUR(order.getEmpCompanyRegNr());
            log.info("getCorrespondingProduct setting company = {}", thisCompany);
            mask.setCompany(thisCompany.getComp_id().getCode());
            log.info("Getting product with setup: {}", mask);
            product = findExactlyOneProduct(mask);
        }
        // </company stuff>

        if (product == null) {
            // There are no product for this company or company not defined for this client
            // trying to find general product for everybody
            mask.setCompany(null);
            log.info("Getting product with setup: {}", mask);
            product = findExactlyOneProduct(mask);
        }

        if (product != null) {
            log.info("getCorrespondingProduct setting productId = {}", product.getComp_id().getCode());
            order.setProductId(product.getComp_id().getCode());
            return product;
        }
        throw new DataIntegrityException("Can't get corresponding product - " + mask);
    }

    public IzdOfferedProduct findExactlyOneProduct(IzdOfferedProductDTO mask) throws DataIntegrityException {
        List<IzdOfferedProduct> products = productDAO.findAvailableProducts(mask);
        if (products == null || products.size() != 1) {
            throw new DataIntegrityException("Can not find exactly 1 product by searching criteria");
        }
        return products.get(0);
    }

    public RowTypeEditCardRequest orderToRowTypeEditCardRequest(Order order, String condSet) {
        log.info("orderToRowTypeEditCardRequest BEGIN");
        IzdCard izdCard = (IzdCard) cardDAO.getObject(IzdCard.class, order.getCardNumber());
        RowTypeEditCardRequest card = new RowTypeEditCardRequest();
        card.setCARDNAME(order.getCardName());
        card.setBASESUPP(condSet);
        card.setCONDSET(izdCard.getBaseSupp());
        card.setCARD(order.getCardNumber());
        return card;
    }

    public static RowTypeReplaceCard orderToRowTypeReplaceCard(Order order) {
        log.info("orderToRowTypeReplaceCard BEGIN");
        RowTypeReplaceCard r = new RowTypeReplaceCard();
        r.setCARD(order.getCardNumber());
        r.setCHIPID(new BigDecimal(order.getApplicationId()));
        r.setOFFCONDSET(order.getOfflineConditionSet());

        Set<String> reasons = new HashSet<>();
        reasons.add("4");
        reasons.add("7");
        reasons.add("10");

        BigInteger charge = reasons.contains(order.getReplacementReason()) ? Constants.WITH_CHARGE : Constants.WITHOUT_CHARGE;
        r.setNOCHARGE(charge);
        return r;
    }

    public static RowTypeRenewCard orderToRowTypeRenewCard(Order order) {
        log.info("orderToRowTypeRenewCard BEGIN");
        RowTypeRenewCard r = new RowTypeRenewCard();
        r.setCARD(order.getCardNumber());
        r.setCHIPID(new BigDecimal(order.getApplicationId()));
        BigInteger charge = Constants.APPLICATION_TYPE_CARD_AUTO_RENEW.equals(order.getApplicationType())
                ? Constants.WITH_CHARGE
                : Constants.WITHOUT_CHARGE;
        r.setNOCHARGE(charge);
        return r;
    }

    public static RowTypeOrderPinEnvelope orderToRowTypeOrderPinEnvelope(Order order) {
        log.info("orderToRowTypeOrderPinEnvelope BEGIN");
        RowTypeOrderPinEnvelope r = new RowTypeOrderPinEnvelope();
        r.setCARD(order.getCardNumber());
        //r.setNOCHARGE(BigInteger.ZERO);
        return r;
    }

    public RowTypeAgreement orderToRowTypeAgreementBase(Order order, String branch) throws DataIntegrityException {

        final String country = commonDAO.getIzdCountryByShortCountryCode(order.getClientCountry()).getCountry();

        log.info("orderToRowTypeAgreementBase, orderBranch = {}, targetBranch = {}, clientCountry = {}, targetCountry = {}",
                order.getBranch(), branch, order.getClientCountry(), country);

        RowTypeAgreement agreement = new RowTypeAgreement();
        agreement.setBINCOD(order.getBin());
        agreement.setCLIENT(order.getClient());
        agreement.setBANKCODE(order.getBankc());
        agreement.setEMAILS(order.getEmail());
        agreement.setREPLANG(defaultIfBlank(order.getReportLanguage(), Constants.DEFAULT_REP_LANG));
        agreement.setUCOD4(order.getFillPlaceNg());
        agreement.setDISTRIBMODE(order.getRepDistributionMode());
        agreement.setCOUNTRY(country);
        agreement.setBRANCH(branch);
        agreement.setPRODUCT(Constants.DEFAULT_PRODUCT);
        agreement.setSTATUS("10");
        agreement.setENROLLED(new Date());
        return agreement;
    }

    public static String getMinimalBalanceAccountRiskLevelByCountry(String countryCode) {
        if ("EE".equals(countryCode)) {
            return Constants.MIN_BAL_ACCOUNT_RISK_LEVEL_EE;
        }
        return Constants.MIN_BAL_ACCOUNT_RISK_LEVEL_LV;
    }

    protected static String getLanguage(Order order) throws DataIntegrityException {
        String language = order.getDlvLanguage();
        if (StringUtils.isBlank(language))
            language = order.getReportLanguage();
        if (StringUtils.isBlank(language))
            throw new DataIntegrityException("Missing value for delivery address language");
        return language;
    }

    /**
     * @param monthAndYear MMyy
     * @return year YYYY
     */
    public static String convertYearTo4digit(String monthAndYear) throws ParseException {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("MMyy");
        Date twoDigitYearStart = DateUtils.addYears(new Date(), -100);
        inputDateFormat.set2DigitYearStart(twoDigitYearStart);
        Date d = inputDateFormat.parse(monthAndYear);
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy");
        return outputDateFormat.format(d);
    }

    public static Date parseDate(String dateMask, String date){
        try {
            if(StringUtils.isNotBlank(dateMask) && StringUtils.isNotBlank(date)){
                return new SimpleDateFormat(dateMask).parse(date);
            }
        } catch (ParseException e) {
            log.warn("parseDate, error while parsing DATE, value = {}, mask = {}", date, dateMask, e);
        }
        return null;
    }

    public boolean hasChange(String orderField, String oldValue){
        //orderField "null" means -- no change
        return StringUtils.isNotBlank(orderField) && !StringUtils.equals(orderField, oldValue);
    }

    public abstract DeliveryDetailsHelperBase orderToDeliveryDetailsHelper(Order order) throws DataIntegrityException;

    public abstract String getBranchIdByExternalId(String branch) throws DataIntegrityException;


    public static Date randomExpiry(int minMonths, int maxMonths) throws DataIntegrityException {
        if (minMonths < 1) {
            throw new DataIntegrityException("minMonths value has to be greater then 0, was: " + minMonths);
        }
        if (maxMonths < minMonths) {
            throw new DataIntegrityException("maxMonths value has to be greater then minMonths, was minMonths: "
                    + minMonths + ", maxMonths : " + maxMonths);
        }
        if (maxMonths > Constants.MAX_CARD_EXPIRY_IN_MONTHS) {
            throw new DataIntegrityException("maxMonths value cannot exceed " + Constants.MAX_CARD_EXPIRY_IN_MONTHS +
                    ", was : " + maxMonths);
        }
        // init expiry date object
        Calendar expiry = Calendar.getInstance();
        expiry.set(Calendar.DAY_OF_MONTH, 1);
        // add random month count to expiry date
        Random random = new Random();
        int randomMonthCount = random.nextInt((maxMonths - minMonths) + 1) + minMonths;
        expiry.add(Calendar.MONTH, randomMonthCount);
        // set expiry date to last day of month
        int lastDayOfMonth = expiry.getActualMaximum(Calendar.DAY_OF_MONTH);
        expiry.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
        expiry.set(Calendar.HOUR_OF_DAY, 0);
        expiry.set(Calendar.MINUTE, 0);
        expiry.set(Calendar.SECOND, 0);
        expiry.set(Calendar.MILLISECOND, 0);

        return expiry.getTime();
    }
}

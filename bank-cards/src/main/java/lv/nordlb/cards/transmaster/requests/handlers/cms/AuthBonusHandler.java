package lv.nordlb.cards.transmaster.requests.handlers.cms;

import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateAccountXML;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateDBWork;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIException;
import lv.bank.cards.core.vendor.api.rtps.db.RTPSCallAPIWrapper;
import lv.nordlb.cards.transmaster.bo.interfaces.AccountManager;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import org.apache.commons.lang.StringUtils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isBlank;

public class AuthBonusHandler extends SubRequestHandler {

    protected static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy.MM.dd"));
    protected static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#.00");
    private static final List<String> ACTION_LIST = Arrays.asList("SET", "ADD");
    private static final String ACTION_ADD = "ADD";

    private static final long MIN_AMOUNT = -99999999999L;
    private static final long MAX_AMOUNT = 0L;

    private CMSCallAPIWrapper wrap;
    private RTPSCallAPIWrapper rtpsCallApi;

    private AccountManager accountManager;
    private CardManager cardManager;

    void setWrap(CMSCallAPIWrapper wrap) {
        this.wrap = wrap;
    }

    void setRtpsCallApi(RTPSCallAPIWrapper rtpsCallApi) {
        this.rtpsCallApi = rtpsCallApi;
    }

    public AuthBonusHandler() throws RequestPreparationException {
        super();
        wrap = new CMSCallAPIWrapper();
        rtpsCallApi = new RTPSCallAPIWrapper();
        try {
            accountManager = (AccountManager) new InitialContext().lookup(AccountManager.JNDI_NAME);
            cardManager = (CardManager) new InitialContext().lookup(CardManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String account = getStringFromNode("/do/account");
        String amountString = getStringFromNode("/do/amount");
        String expiry = getStringFromNode("/do/expiry");
        String action = getStringFromNode("/do/action");

        if (isBlank(account)) {
            throw new RequestFormatException("Not specified account tag", this);
        }

        if (isBlank(amountString)) {
            throw new RequestFormatException("Not specified amount tag", this);
        }

        if (isBlank(expiry)) {
            throw new RequestFormatException("Not specified expiry tag", this);
        }

        if (isBlank(action)) {
            throw new RequestFormatException("Not specified action tag", this);
        }

        BigDecimal accountNumber = null;
        try {
            accountNumber = new BigDecimal(account);
        } catch (NumberFormatException e) {
            throw new RequestFormatException("Wrong account number", this);
        }

        IzdAccount izdAccount = null;
        try {
            izdAccount = accountManager.findAccountByAccountNo(accountNumber);
        } catch (DataIntegrityException e) {
            throw new RequestFormatException("Cannot find account", this);
        }

        if (izdAccount == null) {
            throw new RequestFormatException("Cannot find account", this);
        }


        Double amountDec = null;
        Long amount = null;
        try {
            amountDec = Double.parseDouble(amountString);
            String formatAmount = NUMBER_FORMAT.format(amountDec);
            formatAmount = formatAmount.replaceAll("\\.", "").replaceAll(",", "");
            amount = Long.parseLong(formatAmount);
        } catch (NumberFormatException e) {
            throw new RequestFormatException("Wrong amount format", this);
        }

        if (amount < MIN_AMOUNT) {
            throw new RequestFormatException("Wrong amount", this);
        }
        BigDecimal checkAmount = new BigDecimal(amountString).multiply(new BigDecimal(100));
        checkAmount = checkAmount.setScale(2, RoundingMode.HALF_UP);
        if (amount != checkAmount.doubleValue()) {
            throw new RequestFormatException("Wrong amount format", this);
        }

        Date expiryDate = null;
        try {
            expiryDate = DATE_FORMAT.get().parse(expiry);
        } catch (ParseException e) {
            throw new RequestFormatException("Wrong expirey format", this);
        }

        if (expiryDate == null) {
            throw new RequestFormatException("Wrong expirey format", this);
        }

        if (expiryDate.before(new Date())) {
            throw new RequestFormatException("Expiry must be in future", this);
        }

        if (!ACTION_LIST.contains(StringUtils.upperCase(action))) {
            throw new RequestFormatException("Wrong action", this);
        }

        if (ACTION_ADD.equalsIgnoreCase(action) &&
                izdAccount.getIzdAccParam().getAuthBonus() != null) {
            amount += izdAccount.getIzdAccParam().getAuthBonus();
        }
        if (amount > MAX_AMOUNT || amount < MIN_AMOUNT) {
            throw new RequestFormatException("Wrong amount after adding", this);
        }

        if (ACTION_ADD.equalsIgnoreCase(action) &&
                izdAccount.getIzdAccParam().getAbExpirity() != null &&
                izdAccount.getIzdAccParam().getAbExpirity().after(expiryDate)) {
            expiryDate = izdAccount.getIzdAccParam().getAbExpirity();
        }

        UpdateAccountXML updateAccountXML = wrap.new UpdateAccountXML(izdAccount.getAccountNo().toString(),
                izdAccount.getBankC(), izdAccount.getGroupC());
        updateAccountXML.setElement("AUTH_BONUS", Long.toString(amount));
        updateAccountXML.setElementWithoutEscape("AB_EXPIRITY", "to_date('" + DATE_FORMAT.get().format(expiryDate) + "','yyyy.mm.dd')");

        UpdateDBWork work = wrap.new UpdateDBWork();
        work.setInputXML(updateAccountXML.getDocument());
        String response = cardManager.doWork(work);
        if (!"success".equals(response)) {
            throw new RequestProcessingException(StringUtils.substring(
                    StringUtils.substringBetween(response, "<ERROR_DESC>",
                            "</ERROR_DESC>"), 0, 200), this);
        }

        try {
            rtpsCallApi.setAccBonusAmount(CardUtils.composeCentreId(izdAccount.getBankC(), izdAccount.getGroupC()),
                    izdAccount.getAccountNo().toString(), amount);
        } catch (RTPSCallAPIException e) {
            throw new RequestProcessingException(StringUtils.substring(e.getErrorText(), 0, 200), this);
        }

        createElement("auth-bonus", "done");
    }

}

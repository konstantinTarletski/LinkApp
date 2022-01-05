package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.cms.IzdLock;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.entity.linkApp.PcdCondAccntPK;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.entity.rtps.StipLocks;
import lv.bank.cards.core.rtps.dao.StipLocksDAO;
import lv.bank.cards.core.rtps.impl.StipLocksDAOHibernate;
import lv.bank.cards.core.utils.AccountConditionsUtil;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.Constants;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.exceptions.RequestProcessingSoftException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.OtbService;
import lv.bank.cards.soap.service.dto.OtbDo;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.nordlb.cards.transmaster.bo.interfaces.IzdLockManager;
import lv.nordlb.cards.transmaster.fo.interfaces.TMFManager;
import org.apache.commons.lang.StringUtils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class GetCardAccountBalances extends SubRequestHandler {

    protected final PcdabaNGManager pcdabaNGManager;
    protected final TMFManager tmfManager;
    protected final IzdLockManager izdLockManager;
    protected final StipLocksDAO stipLocksDAO;
    protected OtbService otbService;

    public GetCardAccountBalances() throws RequestPreparationException {
        super();
        stipLocksDAO = new StipLocksDAOHibernate();
        otbService = new OtbService(stipLocksDAO);
        try {
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
            tmfManager = (TMFManager) new InitialContext().lookup(TMFManager.JNDI_NAME);
            izdLockManager = (IzdLockManager) new InitialContext().lookup(IzdLockManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);

        String cif = getStringFromNode("/do/cif");
        String coreAccountNo = getStringFromNode("/do/core-account");
        String country = getStringFromNode("/do/country");

        if (StringUtils.isBlank(cif) && StringUtils.isBlank(coreAccountNo)) {
            throw new RequestFormatException("Specify cif or core-account", this);
        }

        if (StringUtils.isNotBlank(cif) && StringUtils.isNotBlank(coreAccountNo)) {
            throw new RequestFormatException("Specify either cif or core-account", this);
        }

        if (StringUtils.isBlank(country)) {
            throw new RequestFormatException("Specify country code", this);
        }

        ResponseElement response = createElement("accounts");
        List<PcdAccount> accounts = null;

        if (cif != null) {
            accounts = pcdabaNGManager.getActiveAccountsByCif(cif, country);
        } else {
            accounts = Arrays.asList(pcdabaNGManager.getAccountByCoreAccountNo(coreAccountNo, country));
        }

        if (accounts != null && !accounts.isEmpty()) {
            String centreId = CardUtils.composeCentreId(Constants.CMS_BANK_CODE_23, Constants.CMS_GROUP_CODE_50);
            for (PcdAccount pcdAccount : accounts) {
                if (pcdAccount != null) {
                    String coreAccount = pcdAccount.getPcdAccParam().getUfield5();
                    String cardAccount = pcdAccount.getAccountNo().toString();
                    log.info("Processing account: " + coreAccount + " (" + cardAccount + ")");

                    if (!pcdAccount.getPcdAccParam().getStatus().equals("0")) {
                        log.info("Account " + coreAccount + " (" + cardAccount + ") is not active and will be " +
                                "excluded from response");
                        continue;
                    }

                    StipAccount stipAccount = tmfManager.findStipAccountByAccountNoAndCentreId(cardAccount, centreId);

                    if (stipAccount == null) {
                        log.warn("Account " + coreAccount + " (" + cardAccount + ") is absent from RTPS database, " +
                                "thus it will be excluded from response");
                    } else {
                        OtbDo oh = otbService.calculateOtb(stipAccount);
                        String creditUsed = getCreditUsed(oh.getOtb(), oh.getAmtCrd());
                        String creditAvailable = getCreditAvailable(oh.getAmtCrd(), creditUsed);
                        String accountCurrency = stipAccount.getCurrencyCode().getCcyAlpha();
                        String interestRate = getCreditInterestRate(pcdAccount.getPcdAccParam().getCondSet(), accountCurrency);
                        String amountLockedForClient = getAmountLockedForClient(pcdAccount.getAccountNo());

                        ResponseElement responseAccount = response.createElement("account");
                        responseAccount.createElement("core-account", coreAccount);
                        responseAccount.createElement("card-account", cardAccount);
                        responseAccount.createElement("currency-alpha", accountCurrency);
                        responseAccount.createElement("credit-granted", oh.getAmtCrd());
                        responseAccount.createElement("credit-used", creditUsed);
                        responseAccount.createElement("credit-available", creditAvailable);
                        responseAccount.createElement("interest-rate", interestRate);
                        responseAccount.createElement("amount-initial", oh.getAmtInitial());
                        responseAccount.createElement("amount-locked", oh.getAmtLocked());
                        responseAccount.createElement("amount-locked-client", amountLockedForClient);
                        responseAccount.createElement("amount-bonus", oh.getAmtBonus());
                        responseAccount.createElement("amount-available", oh.getOtb());
                    }
                }
            }
        }
    }

    private String getCreditInterestRate(String conditionSet, String currency) throws RequestProcessingSoftException {
        PcdCondAccntPK conditionSetPK = new PcdCondAccntPK(LinkAppProperties.getCmsBankCode(),
                LinkAppProperties.getCmsGroupCode(), conditionSet, currency);
        PcdCondAccnt accountConditions = pcdabaNGManager.getCondAccntByComp_Id(conditionSetPK);

        if (accountConditions == null) {
            throw new RequestProcessingSoftException(ErrorConstants.invalidComp_Id, this);
        }
        return String.format("%.0f", AccountConditionsUtil.getCreditInterestRate(accountConditions));
    }

    private String getCreditUsed(String otb, String credit) {
        BigDecimal availableBalance = new BigDecimal(otb);
        BigDecimal creditGranted = new BigDecimal(credit);

        if (availableBalance.compareTo(creditGranted) >= 0) {
            return "0.00";
        }
        return String.format("%.2f", creditGranted.subtract(availableBalance));
    }

    private String getCreditAvailable(String credit, String used) {
        BigDecimal creditGranted = new BigDecimal(credit);
        BigDecimal creditUsed = new BigDecimal(used);

        return String.format("%.2f", creditGranted.subtract(creditUsed));
    }

    private String getAmountLockedForClient(BigDecimal accountNo) {
        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);
        final Set<Long> usedRowIdsFromCms = new HashSet<>();

        final List<IzdLock> locksFromCms = izdLockManager.findIzdLocksByAccount(accountNo, false, null, null, null, null, null, null);
        locksFromCms.forEach(lock -> {
            if (!CardUtils.isDepositInOutsourcedAtm(lock.getProcCode(), lock.getFld026(), lock.getFld032())) {
                usedRowIdsFromCms.add(lock.getRowNumb());
                total.updateAndGet(x -> x.add(getLockAmount(lock.getLockingSign() == 0, lock.getFld006(),
                        lock.getIzdCcyFld051().getExp())));
            }
        });

        final List<StipLocks> locksFromRtps = tmfManager.findStipLocksByAccount(accountNo.toPlainString(), false, null, null, null, null, null, null);
        locksFromRtps.stream()
                .filter(lock -> !CardUtils.isDepositInOutsourcedAtm(lock.getStipLocksMatch().getFld003(),
                        lock.getStipLocksMatch().getFld026(), lock.getStipLocksMatch().getFld032()))
                .filter(lock -> !usedRowIdsFromCms.contains(lock.getRowNumb()))
                .forEach(lock -> total.updateAndGet(x -> x.add(getLockAmount(Math.abs(lock.getAmount()) == lock.getAmount(),
                        lock.getStipLocksMatch().getFld006(),
                        lock.getStipLocksMatch().getCurrencyCodeByFld051().getExpDot()))));

        return String.format("%.2f", total.get());
    }

    private BigDecimal getLockAmount(boolean positive, Long amount, String exp) {
        return BigDecimal.valueOf((positive ? 1 : -1) * amount).divide(BigDecimal.valueOf(Math.pow(10, Double.parseDouble(exp))));
    }

    private BigDecimal getLockAmount(boolean positive, Long amount, Integer exp) {
        return BigDecimal.valueOf((positive ? 1 : -1) * amount).divide(BigDecimal.valueOf(Math.pow(10, Double.valueOf(exp))));
    }
}

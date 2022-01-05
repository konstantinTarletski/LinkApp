package lv.nordlb.cards.pcdabaNG.ejb;

import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdAccumulator;
import lv.bank.cards.core.entity.linkApp.PcdAtmAdvert;
import lv.bank.cards.core.entity.linkApp.PcdAtmAdvertSpecial;
import lv.bank.cards.core.entity.linkApp.PcdAuthBatch;
import lv.bank.cards.core.entity.linkApp.PcdAuthPosIsoHostMessage;
import lv.bank.cards.core.entity.linkApp.PcdAuthSource;
import lv.bank.cards.core.entity.linkApp.PcdBranch;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.entity.linkApp.PcdCondAccntPK;
import lv.bank.cards.core.entity.linkApp.PcdCurrency;
import lv.bank.cards.core.entity.linkApp.PcdMerchant;
import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.bank.cards.core.entity.linkApp.PcdRepLang;
import lv.bank.cards.core.entity.linkApp.PcdSlip;
import lv.bank.cards.core.entity.linkApp.PcdStopCause;
import lv.bank.cards.core.linkApp.dao.AccountsDAO;
import lv.bank.cards.core.linkApp.dao.AccumulatorsDAO;
import lv.bank.cards.core.linkApp.dao.AtmAdvertDAO;
import lv.bank.cards.core.linkApp.dao.AtmAdvertSpecialDAO;
import lv.bank.cards.core.linkApp.dao.AuthPosIsoHostMsgDAO;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dao.CcyConvDAO;
import lv.bank.cards.core.linkApp.dao.PPCardsDAO;
import lv.bank.cards.core.linkApp.dao.PcdLogDAO;
import lv.bank.cards.core.linkApp.dao.RepLangDAO;
import lv.bank.cards.core.linkApp.dao.TransactionDAO;
import lv.bank.cards.core.linkApp.dto.BDCCountAndAmount;
import lv.bank.cards.core.linkApp.dto.CardInfoDTO;
import lv.bank.cards.core.linkApp.impl.AccountsDAOHibernate;
import lv.bank.cards.core.linkApp.impl.AccumulatorsDAOHibernate;
import lv.bank.cards.core.linkApp.impl.AtmAdvertDAOHibernate;
import lv.bank.cards.core.linkApp.impl.AtmAdvertSpecialDAOHibernate;
import lv.bank.cards.core.linkApp.impl.AuthPosIsoHostMsgDAOHibernate;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.linkApp.impl.CcyConvDAOHibernate;
import lv.bank.cards.core.linkApp.impl.PPCardsDAOHibernate;
import lv.bank.cards.core.linkApp.impl.PcdLogDAOHibernate;
import lv.bank.cards.core.linkApp.impl.RepLangDAOHibernate;
import lv.bank.cards.core.linkApp.impl.TransactionDAOHibernate;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import org.hibernate.SQLQuery;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Stateless
public class PcdabaNGManagerBean implements PcdabaNGManager {

    protected final CardsDAO cardsDAO;
    protected final AccountsDAO accountsDAO;
    protected final PcdLogDAO pcdLogDAO;
    protected final RepLangDAO repLangDAO;
    protected final AuthPosIsoHostMsgDAO authPosIsoHostMsgDAO;
    protected final TransactionDAO transactionDAO;
    protected final PPCardsDAO pcdPPCardsDAO;
    protected final CcyConvDAO ccyConvDAO;
    protected final AtmAdvertDAO atmAdvertDAO;
    protected final AtmAdvertSpecialDAO atmAdvertSpecialDAO;
    protected final AccumulatorsDAO accumulatorsDAO;

    public PcdabaNGManagerBean() {
        cardsDAO = new CardsDAOHibernate();
        accountsDAO = new AccountsDAOHibernate();
        pcdLogDAO = new PcdLogDAOHibernate();
        repLangDAO = new RepLangDAOHibernate();
        authPosIsoHostMsgDAO = new AuthPosIsoHostMsgDAOHibernate();
        transactionDAO = new TransactionDAOHibernate();
        pcdPPCardsDAO = new PPCardsDAOHibernate();
        ccyConvDAO = new CcyConvDAOHibernate();
        atmAdvertDAO = new AtmAdvertDAOHibernate();
        atmAdvertSpecialDAO = new AtmAdvertSpecialDAOHibernate();
        accumulatorsDAO = new AccumulatorsDAOHibernate();
    }

    @Override
    public String getNewCardAcctId(String externalId) throws DataIntegrityException {
        return accountsDAO.getNewCardAcctId(externalId);
    }

    @Override
    public PcdAccount getAccountByAccountNo(BigDecimal accountNo) {
        return accountsDAO.findByAccountNo(accountNo);
    }

    @Override
    public PcdAccount getAccountByCoreAccountNo(String coreAccountNo, String country) {
        return accountsDAO.getAccountByCoreAccountNo(coreAccountNo, country);
    }

    @Override
    public List<PcdAccount> getActiveAccountsByCif(String cif, String country) {
        return accountsDAO.getActiveAccountsByCif(cif, country);
    }

    @Override
    public PcdCard getCardByCardNumber(String cardNumber) {
        return cardsDAO.findByCardNumber(cardNumber);
    }

    @Override
    public PcdRepLang getRepLangByLangCode(String langCode) {
        return repLangDAO.findByLangCode(langCode);
    }

    @Override
    public List<String> getCardsByPersonalCode(String personalCode, String country) {
        return cardsDAO.getCardsByPersonalCode(personalCode, country);
    }

    @Override
    public List<PcdStopCause> getStopCauseByActionCodeBankC(String action_code, String bank_c) {
        return cardsDAO.findStopCauseByActionCodeBankC(action_code, bank_c);
    }

    @Override
    public List<PcdStopCause> getActionCodeByStopCauseBankC(String cause, String bank_c) {
        return cardsDAO.findActionCodeByStopCauseBankC(cause, bank_c);
    }

    @Override
    public Object saveOrUpdate(Object object) {
        cardsDAO.saveOrUpdate(object);
        return object;
    }

    @Override
    public Long writeLog(String source, String operation, String operator, String text, String result) {
        return pcdLogDAO.write(source, operation, operator, text, result);
    }

    @Override
    public List<Object[]> getCardData(List<String> cards, List<String> accounts) {
        return cardsDAO.findCardData(cards, accounts);
    }

    @Override
    public HashMap<String, BDCCountAndAmount> getReconsiled(PcdAuthBatch batch) {
        return authPosIsoHostMsgDAO.getReconsiled(batch);
    }

    @Override
    public PcdAuthBatch getNewBatchOpened(/*Date watermark, */String terminal, PcdAuthSource source) {
        return authPosIsoHostMsgDAO.getNewBatchOpened(/*watermark,*/ terminal, source);
    }

    @Override
    public PcdAuthPosIsoHostMessage getAuthPosIsoMessageById(Long id) {
        return authPosIsoHostMsgDAO.findPcdAuthPosIsoHostMessageById(id);
    }

    @Override
    public PcdAuthSource getAuthSourceById(Long id) {
        return authPosIsoHostMsgDAO.findPcdAuthSourceById(id);
    }

    @Override
    public PcdAuthSource getAuthSourceByName(String name) {
        return authPosIsoHostMsgDAO.findPcdAuthSourceByName(name);
    }

    @Override
    public List<CardInfoDTO> getCardInfoByCif(String cif, String country, boolean mode) {
        return cardsDAO.getCardInfo(null, cif, country, mode);
    }

    @Override
    public CardInfoDTO getCardInfo(String card) {
        List<CardInfoDTO> cardInfo = cardsDAO.getCardInfo(card, null, null, false);
        if (!cardInfo.isEmpty()) {
            return cardInfo.get(0);
        } else return null;
    }

    @Override
    public List<PcdSlip> getTransactionDetails(String card, Date from, Date to) {
        return transactionDAO.findTransactionDetailsByDate(card, from, to);
    }

    @Override
    public SQLQuery getCardDataHandoff(String datetime, String country) {
        return cardsDAO.findCardsDataHandoff(datetime, country);
    }

    @Override
    public SQLQuery getCardDataHandoffForLDWH(String datetime, String country) {
        return cardsDAO.findCardsDataHandoffForLDWH(datetime, country);
    }

    @Override
    public SQLQuery getBinDataHandoff(String datetime) {
        return cardsDAO.findBinDataHandoff(datetime);
    }

    @Override
    public SQLQuery getCondAccntDataHandoff(String datetime) {
        return cardsDAO.findCondAccntDataHandoff(datetime);
    }

    @Override
    public SQLQuery getCondCardDataHandoff(String datetime) {
        return cardsDAO.findCondCardDataHandoff(datetime);
    }

    @Override
    public SQLQuery getServicesFeeDataHandoff(String datetime) {
        return cardsDAO.findServicesFeeDataHandoff(datetime);
    }

    @Override
    public SQLQuery getStopCausesDataHandoff(String datetime) {
        return cardsDAO.getStopCausesDataHandoff(datetime);
    }

    @Override
    public PcdPpCard getPPCardInfoByCreditCard(String card) {
        return pcdPPCardsDAO.findPcdPPCardByCreditCard(card);
    }

    @Override
    public PcdPpCard getPPCardInfoByCardNumber(String card) {
        return pcdPPCardsDAO.findPcdPPCardByCardNumber(card);
    }

    @Override
    public double getConvRate(String from, String to) {
        return ccyConvDAO.findConversionRate(from, to);
    }

    @Override
    public PcdCondAccnt getAccountConditionsByCardNumber(String cardNumber) {
        return cardsDAO.findAccountConditionsByCardNumber(cardNumber);
    }

    @Override
    public Integer getAnnualFee(String set, String ccy, boolean which) {
        return cardsDAO.findAnnualFee(set, ccy, which);
    }

    @Override
    public Integer getCardFee(String set, String ccy, boolean which) {
        return cardsDAO.findCardFee(set, ccy, which);
    }

    @Override
    public List<PcdClient> getClientInfo(String id, String country) {
        return cardsDAO.findClientInfo(id, country);
    }

    @Override
    public List<PcdCard> getClientsCardsByCifOrPersonCode(String customerId, String country) {
        return cardsDAO.getClientsCardsByCifOrPersonCode(customerId, country);
    }

    @Override
    public PcdMerchant getMerchantByRegNr(String regNr) {
        return accountsDAO.findByRegNr(regNr);
    }

    @Override
    public String getNextPcdPinIDWithAuthentificationCode(String orderId) throws DataIntegrityException {
        return cardsDAO.getNextPcdPinIDWithAuthentificationCode(orderId);
    }

    @Override
    public List<PcdAtmAdvert> findShownAtmAds(String personalCode) {
        return atmAdvertDAO.findTodayShownAds(personalCode);
    }

    @Override
    public PcdAtmAdvert findAtmAd(String personalCode, String atmId) {
        return atmAdvertDAO.findAtmAd(personalCode, atmId);
    }

    @Override
    public PcdAtmAdvertSpecial findAtmAdvertSpecial(String personalCode, String atmId, boolean question) {
        return atmAdvertSpecialDAO.findAtmAdvertSpecial(personalCode, atmId, question);
    }

    @Override
    public PcdCard getCardForTest() {
        return cardsDAO.getCardForTest();
    }

    @Override
    public PcdCurrency getCurrencyByIsoNum(String isoNum) {
        return cardsDAO.getCurrencyByIsoNum(isoNum);
    }

    @Override
    public PcdAccumulator getAccumulator(String param, String description) {
        return accumulatorsDAO.getAccumulator(param, description);
    }

    @Override
    public PcdBranch getBranch(String branch) {
        return cardsDAO.getBranch(branch);
    }

    @Override
    public String getProductNameByCard(String cardNumber) {
        return cardsDAO.getProductNameByCard(cardNumber);
    }

    @Override
    public List<PcdClient> getClientsFromCIF(String customerId) {
        return cardsDAO.getClientsFromCIF(customerId);
    }

    @Override
    public PcdAccount getAccountByCardNumber(String cardNumber) {
        return accountsDAO.getAccountByCardNumber(cardNumber);
    }

    @Override
    public PcdCondAccnt getCondAccntByComp_Id(PcdCondAccntPK comp_Id) {
        return accountsDAO.getCondAccntByComp_Id(comp_Id);
    }

}

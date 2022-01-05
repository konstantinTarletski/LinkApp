package lv.nordlb.cards.pcdabaNG.interfaces;

import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdAccumulator;
import lv.bank.cards.core.entity.linkApp.PcdAtmAdvert;
import lv.bank.cards.core.entity.linkApp.PcdAtmAdvertSpecial;
import lv.bank.cards.core.entity.linkApp.PcdAuthBatch;
import lv.bank.cards.core.entity.linkApp.PcdAuthPosIsoHostMessage;
import lv.bank.cards.core.entity.linkApp.PcdAuthSource;
import lv.bank.cards.core.entity.linkApp.PcdBranch;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenization;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.entity.linkApp.PcdCondAccntPK;
import lv.bank.cards.core.entity.linkApp.PcdCurrency;
import lv.bank.cards.core.entity.linkApp.PcdMerchant;
import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.bank.cards.core.entity.linkApp.PcdRepLang;
import lv.bank.cards.core.entity.linkApp.PcdSlip;
import lv.bank.cards.core.entity.linkApp.PcdStopCause;
import lv.bank.cards.core.linkApp.dto.BDCCountAndAmount;
import lv.bank.cards.core.linkApp.dto.CardInfoDTO;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.hibernate.SQLQuery;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Local
public interface PcdabaNGManager {

    String JNDI_NAME = "java:app/bankCards/PcdabaNGManagerBean";

    String getNewCardAcctId(String externalId) throws DataIntegrityException;

    PcdAccount getAccountByAccountNo(BigDecimal accountNo);

    PcdAccount getAccountByCoreAccountNo(String coreAccountNo, String country);

    List<PcdAccount> getActiveAccountsByCif(String cif, String country);

    PcdCard getCardByCardNumber(String cardNumber);

    PcdRepLang getRepLangByLangCode(String langCode);

    List<String> getCardsByPersonalCode(String personalCode, String country);

    List<PcdStopCause> getStopCauseByActionCodeBankC(String action_code, String bank_c);

    List<PcdStopCause> getActionCodeByStopCauseBankC(String cause, String bank_c);

    Object saveOrUpdate(Object object);

    Long writeLog(String source, String operation, String operator, String text, String result);

    List<Object[]> getCardData(List<String> cards, List<String> accounts);

    HashMap<String, BDCCountAndAmount> getReconsiled(PcdAuthBatch batch);

    PcdAuthBatch getNewBatchOpened(String terminal, PcdAuthSource source);

    PcdAuthPosIsoHostMessage getAuthPosIsoMessageById(Long id);

    PcdAuthSource getAuthSourceById(Long id);

    PcdAuthSource getAuthSourceByName(String name);

    List<CardInfoDTO> getCardInfoByCif(String cif, String country, boolean mode);

    CardInfoDTO getCardInfo(String card);

    List<PcdSlip> getTransactionDetails(String card, Date from, Date to);

    SQLQuery getCardDataHandoff(String datetime, String country);

    SQLQuery getCardDataHandoffForLDWH(String datetime, String country);

    SQLQuery getBinDataHandoff(String datetime);

    SQLQuery getCondAccntDataHandoff(String datetime);

    SQLQuery getCondCardDataHandoff(String datetime);

    SQLQuery getServicesFeeDataHandoff(String datetime);

    SQLQuery getStopCausesDataHandoff(String datetime);

    PcdPpCard getPPCardInfoByCreditCard(String card);

    PcdPpCard getPPCardInfoByCardNumber(String card);

    double getConvRate(String from, String to);

    PcdCondAccnt getAccountConditionsByCardNumber(String cardNumber);

    Integer getAnnualFee(String set, String ccy, boolean which);

    Integer getCardFee(String set, String ccy, boolean which);

    List<PcdClient> getClientInfo(String id, String country);

    List<PcdCard> getClientsCardsByCifOrPersonCode(String customerId, String country);

    PcdMerchant getMerchantByRegNr(String regNr);

    String getNextPcdPinIDWithAuthentificationCode(String orderId) throws DataIntegrityException;

    List<PcdAtmAdvert> findShownAtmAds(String personalCode);

    PcdAtmAdvert findAtmAd(String personalCode, String atmId);

    PcdAtmAdvertSpecial findAtmAdvertSpecial(String personalCode, String atmId, boolean question);

    PcdCard getCardForTest();

    PcdCurrency getCurrencyByIsoNum(String isoNum);

    PcdAccumulator getAccumulator(String param, String description);

    PcdBranch getBranch(String branch);

    String getProductNameByCard(String cardNumber);

    List<PcdClient> getClientsFromCIF(String customerId);

    PcdAccount getAccountByCardNumber(String cardNumber);

    PcdCondAccnt getCondAccntByComp_Id(PcdCondAccntPK comp_Id);

}

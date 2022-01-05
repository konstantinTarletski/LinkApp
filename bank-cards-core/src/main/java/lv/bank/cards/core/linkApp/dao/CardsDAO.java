package lv.bank.cards.core.linkApp.dao;

import lv.bank.cards.core.entity.linkApp.PcdBranch;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenization;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.entity.linkApp.PcdCurrency;
import lv.bank.cards.core.entity.linkApp.PcdStopCause;
import lv.bank.cards.core.linkApp.dto.CardInfoDTO;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.hibernate.SQLQuery;

import java.math.BigInteger;
import java.util.List;

public interface CardsDAO extends DAO {

    PcdCard findByCardNumber(String card);

    List<PcdStopCause> findStopCauseByActionCodeBankC(String action_code, String bank_c);

    List<PcdStopCause> findActionCodeByStopCauseBankC(String cause, String bank_c);

    List<Object[]> findCardData(List<String> cards, List<String> accounts);

    List<CardInfoDTO> getCardInfo(String card, String cif, String country, boolean mode);

    List<CardInfoDTO> getCardInfoLT(String card, String cif);

    SQLQuery findBinDataHandoff(String datetime);

    SQLQuery findCardsDataHandoff(String datetime, String country);

    SQLQuery findCardsDataHandoffForLDWH(String datetime, String country);

    SQLQuery findCondCardDataHandoff(String datetime);

    SQLQuery findCondAccntDataHandoff(String datetime);

    SQLQuery findServicesFeeDataHandoff(String datetime);

    SQLQuery getStopCausesDataHandoff(String datetime);

    List<String> getCardsByPersonalCode(String personalCode, String country);

    List<String> getCardsByPersonalCode(String personalCode);

    PcdCondAccnt findAccountConditionsByCardNumber(String card);

    Integer findAnnualFee(String set, String ccy, boolean which);

    Integer findCardFee(String set, String ccy, boolean which);

    List<PcdClient> findClientInfo(String id, String country);

    List<PcdCard> getClientsCardsByCifOrPersonCode(String customerId, String country);

    String getNextPcdPinIDWithAuthentificationCode(String orderId) throws DataIntegrityException;

    String getNextPcdPinIDWithAuthentificationCode() throws DataIntegrityException;

    PcdCard getCardForTest();

    PcdCurrency getCurrencyByIsoNum(String isoNum);

    PcdBranch getBranch(String branch);

    String getProductNameByCard(String cardNumber);

    List<PcdClient> getClientsFromCIF(String customerId);

    PcdCard getCardByTrackingNoLT(String trackingNo) throws DataIntegrityException;

    List<Object> findAccountsWithoutCards(String datetime);

    List<Object[]> getCardsOverviewForCLient(String client, String accountNumber, String cardStatus, String deliveryBlock,
                                             String cardtype, String holderName, String lastDigits, int fromRecord,
                                             int numberOfRecords);

    Long getCardsOverviewForCLientTotal(String client, String accountNumber, String cardStatus, String deliveryBlock,
                                        String cardtype, String holderName, String lastDigits);

    Object[] getCardDetailsByCard(String cardNumber);

    SQLQuery findCardDataHandoffLT(String datetime);

    PcdCard findByInOrderNumber(BigInteger inOrderNumber) throws DataIntegrityException;

    String getCifByCardNumber(String card);

    void setAutomaticRenewFlag(String card, String renewFlag);

    void setRenewOld(String card, String renewOld);

    List<PcdCard> getCardsByCardholderPersonCode(String personCode, String country);

    PcdCardPendingTokenization getPcdCardPendingTokenizationByWalletAccountId(String cardNumber, String walletAccountId);

    PcdCardPendingTokenization getPcdCardPendingTokenizationByCorrId(String cardNumber, String corrId);

    PcdCardPendingTokenization getPcdCardPendingTokenizationByKey(String cardNumber, String walletDeviceId);
}

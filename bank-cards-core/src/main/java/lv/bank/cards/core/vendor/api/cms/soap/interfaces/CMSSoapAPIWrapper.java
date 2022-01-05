package lv.bank.cards.core.vendor.api.cms.soap.interfaces;

import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.types.ListTypeCustomerCustomInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAccountInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAgreement;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCustomer;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditAgreementRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCardRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCustomerRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeOrderPinEnvelope;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeRenewCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeReplaceCard;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.List;

@Local
public interface CMSSoapAPIWrapper {

    String JNDI_NAME = "java:app/bankCardsCore/CMSSoapAPIWrapperBean";

    void setRiskLevel(String card, String riskLevel) throws CMSSoapAPIException;

    void editAccount(BigDecimal accountNo, Integer newCredit) throws CMSSoapAPIException;

    void addCardToStop(String card, String cause, String text) throws CMSSoapAPIException;

    void removeCardFromStop(String card, String text) throws CMSSoapAPIException;

    void makeAccountDormant(String cardAcct, String ccy, String comment, String feeCalculationMode) throws CMSSoapAPIException;

    void activateAccount(String cardAcct, String ccy, String comment) throws CMSSoapAPIException;

    BigDecimal executeTransaction(String paymentMode, String accountNo, String tranType, String tranCcy,
                                  String amount, String slipNr, String dealDesc) throws CMSSoapAPIException;

    BigDecimal cancelTransaction(BigDecimal internalNo, BigDecimal accountNo, String tranType, String tranCcy, BigDecimal amount) throws CMSSoapAPIException;

    RowTypeCustomer newCustomer(RowTypeCustomer customerInfo, ListTypeCustomerCustomInfo customListInfo) throws CMSSoapAPIException;

    List<RowTypeCardInfo> newAgreement(RowTypeAgreement agreementInfo, List<RowTypeAccountInfo> accountsListInfo,
                                       List<RowTypeCardInfo> cardsListInfo) throws CMSSoapAPIException;

    void editAgreement(RowTypeEditAgreementRequest agreementInfo) throws CMSSoapAPIException;

    List<RowTypeCardInfo> newCard(RowTypeAgreement agreementInfo, List<RowTypeAccountInfo> accountsListInfo,
                                  List<RowTypeCardInfo> cardsListInfo) throws CMSSoapAPIException;

    void editCard(RowTypeEditCardRequest parameters) throws CMSSoapAPIException;

    void editCustomer(RowTypeEditCustomerRequest parameters) throws CMSSoapAPIException;

    RowTypeOrderPinEnvelope orderPinEnvelope(RowTypeOrderPinEnvelope parameters) throws CMSSoapAPIException;

    RowTypeRenewCard renewCard(RowTypeRenewCard parameters) throws CMSSoapAPIException;

    RowTypeReplaceCard replaceCard(RowTypeReplaceCard parameters) throws CMSSoapAPIException;

    String getVersion();
}

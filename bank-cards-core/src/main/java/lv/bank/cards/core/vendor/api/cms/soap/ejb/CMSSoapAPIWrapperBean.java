package lv.bank.cards.core.vendor.api.cms.soap.ejb;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.soap.service.IssuingPort;
import lv.bank.cards.core.vendor.api.cms.soap.service.IssuingService;
import lv.bank.cards.core.vendor.api.cms.soap.types.ListTypeAccountInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.ListTypeCardInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.ListTypeCustomerCustomInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.OperationConnectionInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.OperationResponseInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAccountInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeActivateAccountRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAddCardToStopListRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeAgreement;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCardInfo;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeCustomer;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditAccountRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditAgreementRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCardRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditCustomerRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeExecTransactionRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeExecTransactionResponse;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeMakeAccountDormantRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeOrderPinEnvelope;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeRemoveCardFromStopRequest;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeRenewCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeReplaceCard;
import lv.bank.cards.core.vendor.api.cms.soap.types.VersionInfo;

import javax.ejb.Stateless;
import javax.xml.ws.Holder;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Stateless
public class CMSSoapAPIWrapperBean implements CMSSoapAPIWrapper {

    public static final BigDecimal EXECUTION_TYPE_CANCEL_TRANSACTION = BigDecimal.valueOf(12);

    protected final String cmsSoapWsdlUrl;
    protected final String cmsSoapUsername;
    protected final String cmsSoapPassword;
    protected final String bankC;
    protected final String groupC;
    protected final IssuingService issuingService;
    protected final IssuingPort issuingPort;

    public CMSSoapAPIWrapperBean() {
        this.cmsSoapWsdlUrl = LinkAppProperties.getCmsSoapWsdlUrl();
        this.cmsSoapUsername = LinkAppProperties.getCmsSoapUsername();
        this.cmsSoapPassword = LinkAppProperties.getCmsSoapPassword();
        this.bankC = LinkAppProperties.getCmsBankCode();
        this.groupC = LinkAppProperties.getCmsGroupCode();
        this.issuingService = new IssuingService();
        this.issuingPort = issuingService.getIssuing(cmsSoapUsername, cmsSoapPassword, cmsSoapWsdlUrl);
        log.info("CMSSoapAPIWrapper cmsSoapUsername = {}, cmsSoapWsdlUrl = {}", cmsSoapUsername, cmsSoapWsdlUrl);
    }

    @Override
    public void setRiskLevel(String card, String riskLevel) throws CMSSoapAPIException {
        log.info("setRiskLevel BEGIN");

        RowTypeEditCardRequest parameters = new RowTypeEditCardRequest();
        parameters.setCARD(card);
        parameters.setRISKLEVEL(riskLevel);

        long begin = System.currentTimeMillis();
        OperationResponseInfo response = issuingPort.editCard(getConnectionInfo(), parameters);
        processOperationResponse(response, begin);
    }

    @Override
    public void editAccount(BigDecimal accountNo, Integer newCredit) throws CMSSoapAPIException {
        log.info("editAccount BEGIN");

        RowTypeEditAccountRequest param = new RowTypeEditAccountRequest();
        param.setCRD(BigDecimal.valueOf(newCredit));
        param.setACCOUNTNO(accountNo);

        long begin = System.currentTimeMillis();
        OperationResponseInfo response = issuingPort.editAccount(getConnectionInfo(), param);
        processOperationResponse(response, begin);
    }

    @Override
    public void addCardToStop(String card, String cause, String text) throws CMSSoapAPIException {
        log.info("addCardToStop BEGIN");

        RowTypeAddCardToStopListRequest param = new RowTypeAddCardToStopListRequest();
        param.setBANKC(bankC);
        param.setGROUPC(groupC);
        param.setCARD(card);
        param.setSTOPCAUSE(cause);
        param.setTEXT(text);

        long begin = System.currentTimeMillis();
        OperationResponseInfo response = issuingPort.addCardToStop(getConnectionInfo(), param);
        processOperationResponse(response, begin);
    }

    @Override
    public void removeCardFromStop(String card, String text) throws CMSSoapAPIException {
        log.info("removeCardFromStop BEGIN");

        RowTypeRemoveCardFromStopRequest param = new RowTypeRemoveCardFromStopRequest();
        param.setBANKC(bankC);
        param.setGROUPC(groupC);
        param.setCARD(card);
        param.setTEXT(text);

        long begin = System.currentTimeMillis();
        OperationResponseInfo response = issuingPort.removeCardFromStop(getConnectionInfo(), param);
        processOperationResponse(response, begin);
    }

    @Override
    public void makeAccountDormant(String cardAcct, String ccy, String comment, String feeCalculationMode) throws CMSSoapAPIException {
        log.info("removeCardFromStop BEGIN");

        RowTypeMakeAccountDormantRequest param = new RowTypeMakeAccountDormantRequest();
        param.setCARDACCT(cardAcct);
        param.setCCY(ccy);
        param.setCOMMENT(comment);
        param.setFEECALC(feeCalculationMode);

        long begin = System.currentTimeMillis();
        OperationResponseInfo response = issuingPort.makeAccountDormant(getConnectionInfo(), param);
        processOperationResponse(response, begin);
    }

    @Override
    public void activateAccount(String cardAcct, String ccy, String comment) throws CMSSoapAPIException {
        log.info("activateAccount BEGIN");

        RowTypeActivateAccountRequest param = new RowTypeActivateAccountRequest();
        param.setCARDACCT(cardAcct);
        param.setCCY(ccy);
        param.setCOMMENT(comment);

        long begin = System.currentTimeMillis();
        OperationResponseInfo response = issuingPort.activateAccount(getConnectionInfo(), param);
        processOperationResponse(response, begin);
    }

    @Override
    public BigDecimal executeTransaction(String paymentMode, String accountNo, String tranType, String tranCcy,
                                         String amount, String slipNr, String dealDesc) throws CMSSoapAPIException {
        log.info("executeTransaction BEGIN");

        RowTypeExecTransactionRequest param = new RowTypeExecTransactionRequest();
        param.setBANKC(bankC);
        param.setGROUPC(groupC);
        param.setPAYMENTMODE(paymentMode);
        param.setACCOUNTNO(new BigDecimal(accountNo));
        param.setTRANTYPE(tranType);
        param.setTRANCCY(tranCcy);
        param.setTRANAMNT(new BigDecimal(amount));
        param.setSLIPNR(slipNr);
        param.setDEALDESC(dealDesc);

        Holder<OperationResponseInfo> responseInfo = new Holder<>();
        Holder<RowTypeExecTransactionResponse> details = new Holder<>();

        long begin = System.currentTimeMillis();
        issuingPort.executeTransaction(getConnectionInfo(), param, responseInfo, details);
        processOperationResponse(responseInfo.value, begin);

        log.info("CMS internal row number: {}", details.value.getINTERNALNO());
        return details.value.getINTERNALNO();
    }

    @Override
    public BigDecimal cancelTransaction(BigDecimal internalNo, BigDecimal accountNo, String tranType, String tranCcy, BigDecimal amount) throws CMSSoapAPIException {
        log.info("cancelTransaction BEGIN");

        RowTypeExecTransactionRequest param = new RowTypeExecTransactionRequest();
        param.setBANKC(bankC);
        param.setGROUPC(groupC);
        param.setINTERNALNO(internalNo);
        param.setEXECUTIONTYPE(EXECUTION_TYPE_CANCEL_TRANSACTION);
        param.setACCOUNTNO(accountNo);
        param.setTRANTYPE(tranType);
        param.setTRANCCY(tranCcy);
        param.setTRANAMNT(amount);

        Holder<OperationResponseInfo> responseInfo = new Holder<>();
        Holder<RowTypeExecTransactionResponse> details = new Holder<>();

        long begin = System.currentTimeMillis();
        issuingPort.executeTransaction(getConnectionInfo(), param, responseInfo, details);
        processOperationResponse(responseInfo.value, begin);

        log.info("CMS internal row number: {}", details.value.getINTERNALNO());
        return details.value.getINTERNALNO();
    }

    @Override
    public RowTypeCustomer newCustomer(RowTypeCustomer customerInfo, ListTypeCustomerCustomInfo customListInfo) throws CMSSoapAPIException {
        log.info("newCustomer BEGIN customerInfo = {} customListInfo = {}", customerInfo, customListInfo);

        Holder<RowTypeCustomer> rowTypeCustomerHolder = new Holder(customerInfo);

        if (customListInfo == null) {
            customListInfo = new ListTypeCustomerCustomInfo();
        }
        Holder<ListTypeCustomerCustomInfo> customListInfoHolder = new Holder(customListInfo);

        long begin = System.currentTimeMillis();
        OperationResponseInfo response = issuingPort.newCustomer(getConnectionInfo(), rowTypeCustomerHolder, customListInfoHolder);
        processOperationResponse(response, begin);
        return rowTypeCustomerHolder.value;
    }

    @Override
    public List<RowTypeCardInfo> newAgreement(RowTypeAgreement agreementInfo, List<RowTypeAccountInfo> accountsListInfo,
                                              List<RowTypeCardInfo> cardsListInfo) throws CMSSoapAPIException {
        log.info("newAgreement BEGIN");

        Holder<RowTypeAgreement> rowTypeAgreementHolder = new Holder(agreementInfo);

        ListTypeAccountInfo listTypeAccountInfo = new ListTypeAccountInfo();
        listTypeAccountInfo.setRow(accountsListInfo);
        Holder<ListTypeAccountInfo> listTypeAccountInfoHolder = new Holder(listTypeAccountInfo);

        ListTypeCardInfo listTypeCardInfo = new ListTypeCardInfo();
        listTypeCardInfo.setRow(cardsListInfo);
        Holder<ListTypeCardInfo> listTypeCardInfoHolder = new Holder(listTypeCardInfo);

        long begin = System.currentTimeMillis();
        OperationResponseInfo response = issuingPort.newAgreement(
                getConnectionInfo(),
                rowTypeAgreementHolder,
                listTypeAccountInfoHolder,
                listTypeCardInfoHolder);
        processOperationResponse(response, begin);
        return listTypeCardInfoHolder.value.getRow();
    }

    @Override
    public void editAgreement(RowTypeEditAgreementRequest agreementInfo) throws CMSSoapAPIException {

        long begin = System.currentTimeMillis();
        OperationResponseInfo response = issuingPort.editAgreement(getConnectionInfo(),agreementInfo);
        processOperationResponse(response, begin);
    }

    @Override
    public List<RowTypeCardInfo> newCard(RowTypeAgreement agreementInfo, List<RowTypeAccountInfo> accountsListInfo,
                                         List<RowTypeCardInfo> cardsListInfo) throws CMSSoapAPIException {
        log.info("newCard BEGIN");

        Holder<RowTypeAgreement> rowTypeAgreementHolder = new Holder(agreementInfo);

        ListTypeAccountInfo listTypeAccountInfo = new ListTypeAccountInfo();
        listTypeAccountInfo.setRow(accountsListInfo);
        Holder<ListTypeAccountInfo> listTypeAccountInfoHolder = new Holder(listTypeAccountInfo);

        ListTypeCardInfo listTypeCardInfo = new ListTypeCardInfo();
        listTypeCardInfo.setRow(cardsListInfo);
        Holder<ListTypeCardInfo> listTypeCardInfoHolder = new Holder(listTypeCardInfo);

        long begin = System.currentTimeMillis();
        OperationResponseInfo response = issuingPort.newCard(
                getConnectionInfo(),
                rowTypeAgreementHolder,
                listTypeAccountInfoHolder,
                listTypeCardInfoHolder);
        processOperationResponse(response, begin);
        return listTypeCardInfoHolder.value.getRow();
    }

    @Override
    public void editCard(RowTypeEditCardRequest parameters) throws CMSSoapAPIException {
        log.info("editCard BEGIN");
        long begin = System.currentTimeMillis();
        OperationResponseInfo response = issuingPort.editCard(getConnectionInfo(), parameters);
        processOperationResponse(response, begin);
    }

    @Override
    public void editCustomer(RowTypeEditCustomerRequest parameters) throws CMSSoapAPIException {
        log.info("editCustomer BEGIN");
        long begin = System.currentTimeMillis();
        OperationResponseInfo response = issuingPort.editCustomer(getConnectionInfo(), parameters);
        processOperationResponse(response, begin);
    }

    @Override
    public RowTypeOrderPinEnvelope orderPinEnvelope(RowTypeOrderPinEnvelope parameters) throws CMSSoapAPIException {
        log.info("orderPinEnvelope BEGIN");

        Holder<OperationResponseInfo> responseInfo = new Holder<>();
        Holder<RowTypeOrderPinEnvelope> details = new Holder<>();

        long begin = System.currentTimeMillis();
        issuingPort.orderPinEnvelope(getConnectionInfo(), parameters, responseInfo, details);
        processOperationResponse(responseInfo.value, begin);
        return details.value;
    }

    @Override
    public RowTypeRenewCard renewCard(RowTypeRenewCard parameters) throws CMSSoapAPIException {
        log.info("renewCard BEGIN");

        Holder<OperationResponseInfo> responseInfo = new Holder<>();
        Holder<RowTypeRenewCard> details = new Holder<>();

        long begin = System.currentTimeMillis();
        issuingPort.renewCard(getConnectionInfo(), parameters, responseInfo, details);
        processOperationResponse(responseInfo.value, begin);
        return details.value;
    }


    @Override
    public RowTypeReplaceCard replaceCard(RowTypeReplaceCard parameters) throws CMSSoapAPIException {
        log.info("replaceCard BEGIN");

        Holder<OperationResponseInfo> responseInfo = new Holder<>();
        Holder<RowTypeReplaceCard> details = new Holder<>();

        long begin = System.currentTimeMillis();
        issuingPort.replaceCard(getConnectionInfo(), parameters, responseInfo, details);
        processOperationResponse(responseInfo.value, begin);
        return details.value;
    }

    @Override
    public String getVersion() {
        long begin = System.currentTimeMillis();
        VersionInfo version = issuingPort.getVersion();
        long total = System.currentTimeMillis() - begin;
        log.info("getVersion version = " + version.getVersion());
        log.info("CMS response in {} ms", total);
        return version.getVersion();
    }

    protected void processOperationResponse(OperationResponseInfo responseInfo, long begin) throws CMSSoapAPIException {
        long total = System.currentTimeMillis() - begin;

        log.info("processOperationResponse, response code = {}", responseInfo.getResponseCode());
        log.info("processOperationResponse, CMS response in {} ms", total);

        if (responseInfo.getResponseCode().intValue() != 0) {
            throw new CMSSoapAPIException(responseInfo.getErrorDescription());
        }
    }

    protected OperationConnectionInfo getConnectionInfo() {
        OperationConnectionInfo info = new OperationConnectionInfo();
        info.setBANKC(bankC);
        info.setGROUPC(groupC);
        return info;
    }

}

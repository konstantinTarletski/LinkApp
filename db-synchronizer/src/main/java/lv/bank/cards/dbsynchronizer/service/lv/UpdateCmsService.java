package lv.bank.cards.dbsynchronizer.service.lv;

import lv.bank.cards.core.entity.cms.DnbIzdAccntChng;
import lv.bank.cards.core.entity.cms.IzdAccParam;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdAgreement;
import lv.bank.cards.core.entity.cms.IzdBinTable;
import lv.bank.cards.core.entity.cms.IzdBranch;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCardGroup;
import lv.bank.cards.core.entity.cms.IzdCardType;
import lv.bank.cards.core.entity.cms.IzdCardsAddFields;
import lv.bank.cards.core.entity.cms.IzdCardsPinBlocks;
import lv.bank.cards.core.entity.cms.IzdCcyConvEx;
import lv.bank.cards.core.entity.cms.IzdCcyConvExPK;
import lv.bank.cards.core.entity.cms.IzdCcyTable;
import lv.bank.cards.core.entity.cms.IzdClAcct;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.entity.cms.IzdCompany;
import lv.bank.cards.core.entity.cms.IzdCondAccnt;
import lv.bank.cards.core.entity.cms.IzdCondAccntPK;
import lv.bank.cards.core.entity.cms.IzdCondCard;
import lv.bank.cards.core.entity.cms.IzdDbOwner;
import lv.bank.cards.core.entity.cms.IzdRepDistribut;
import lv.bank.cards.core.entity.cms.IzdRepLang;
import lv.bank.cards.core.entity.cms.IzdServicesFee;
import lv.bank.cards.core.entity.cms.IzdServicesFeePK;
import lv.bank.cards.core.entity.cms.IzdStopCause;
import lv.bank.cards.core.entity.cms.MerchantPar;
import lv.bank.cards.core.entity.cms.MerchantsView;
import lv.bank.cards.core.entity.linkApp.PcdAccParam;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdAgreement;
import lv.bank.cards.core.entity.linkApp.PcdBin;
import lv.bank.cards.core.entity.linkApp.PcdBranch;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardGroup;
import lv.bank.cards.core.entity.linkApp.PcdCardType;
import lv.bank.cards.core.entity.linkApp.PcdCcyConv;
import lv.bank.cards.core.entity.linkApp.PcdCcyConvPK;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.entity.linkApp.PcdCompany;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.entity.linkApp.PcdCondAccntPK;
import lv.bank.cards.core.entity.linkApp.PcdCondCard;
import lv.bank.cards.core.entity.linkApp.PcdCurrency;
import lv.bank.cards.core.entity.linkApp.PcdDbOwner;
import lv.bank.cards.core.entity.linkApp.PcdLog;
import lv.bank.cards.core.entity.linkApp.PcdMerchant;
import lv.bank.cards.core.entity.linkApp.PcdMerchantPar;
import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.bank.cards.core.entity.linkApp.PcdProcIds;
import lv.bank.cards.core.entity.linkApp.PcdRepDistribut;
import lv.bank.cards.core.entity.linkApp.PcdRepLang;
import lv.bank.cards.core.entity.linkApp.PcdServicesFee;
import lv.bank.cards.core.entity.linkApp.PcdServicesFeePK;
import lv.bank.cards.core.entity.linkApp.PcdSlip;
import lv.bank.cards.core.entity.linkApp.PcdStopCause;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.dbsynchronizer.config.ApplicationProperties;
import lv.bank.cards.dbsynchronizer.dao.CmsDao;
import lv.bank.cards.dbsynchronizer.dao.LinkAppDao;
import lv.bank.cards.dbsynchronizer.jpa.pcd.PcdLastUpdatedRepository;
import lv.bank.cards.dbsynchronizer.service.UpdateCmsServiceBase;
import lv.bank.cards.dbsynchronizer.utils.AbstractSpecialConverter;
import lv.bank.cards.dbsynchronizer.utils.AbstractSpecialUpdateHandler;
import lv.bank.cards.dbsynchronizer.utils.Converter;
import lv.bank.cards.dbsynchronizer.utils.DataAndStatelessSessionHolder;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.log4j.Logger;
import org.hibernate.ScrollableResults;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LV_PROFILE;

@Profile(COUNTRY_LV_PROFILE)
@Service
public class UpdateCmsService extends UpdateCmsServiceBase {

    private static final Logger log = Logger.getLogger(UpdateCmsService.class);

    public final static Map<String, String> CARD_NAMES;

    static {
        Map<String, String> names = new HashMap<>();
        names.put("4999628", "Visa Platinum");
        names.put("4999629", "Visa Gold");
        names.put("492176", "Visa Business");
        names.put("489994", "Luminor Visa Infinite");
        names.put("408883", "Luminor Visa Infinite");
        CARD_NAMES = Collections.unmodifiableMap(names);
    }

    public UpdateCmsService(CmsDao cmsDao, LinkAppDao linkAppDao,
                            ApplicationProperties appProperties,
                            PcdLastUpdatedRepository pcdLastUpdatedRepository) {
        super(cmsDao, linkAppDao, appProperties, pcdLastUpdatedRepository);
        super.handlers = getAvailableHandlers();

        Converter.defConverter(new DateConverter());
        Converter.defConverter(new IzdBranchToPcdBranchConverter());
        Converter.defConverter(new IzdAccountToPcdAccountConverter());
        Converter.defConverter(new IzdCardToPcdCardConverter());
        Converter.defConverter(new IzdCondAccntToPcdCondAccntConverter());
        Converter.defConverter(new IzdServicesFeeToPcdServicesFeeConverter());
        Converter.defConverter(new IzdCcyConvExToPcdCcyConvConverter());
        Converter.defConverter(new MerchantParToPcdMerchantParConverter());
        Converter.defConverter(new MerchantsViewToPcdMerchantConverter());
    }

    protected void update(Date prevUpdWaterMark, Date curWaterMark) throws NoSuchMethodException {

        update(IzdDbOwner.class, PcdDbOwner.class, PcdDbOwner.class.getMethod("getBankC"), prevUpdWaterMark, curWaterMark);
        update(IzdStopCause.class, PcdStopCause.class, PcdStopCause.class.getMethod("getComp_id"), prevUpdWaterMark, curWaterMark);
        update(IzdCardType.class, PcdCardType.class, PcdCardType.class.getMethod("getComp_id"), prevUpdWaterMark, curWaterMark);
        update(IzdBinTable.class, PcdBin.class, PcdBin.class.getMethod("getComp_id"), prevUpdWaterMark, curWaterMark);
        update(IzdRepLang.class, PcdRepLang.class, PcdRepLang.class.getMethod("getComp_id"), prevUpdWaterMark, curWaterMark);
        update(IzdRepDistribut.class, PcdRepDistribut.class, PcdRepDistribut.class.getMethod("getComp_id"), prevUpdWaterMark, curWaterMark);
        update(IzdClient.class, PcdClient.class, PcdClient.class.getMethod("getComp_id"), prevUpdWaterMark, curWaterMark);
        update(IzdCardGroup.class, PcdCardGroup.class, PcdCardGroup.class.getMethod("getComp_id"), prevUpdWaterMark, curWaterMark);
        update(IzdBranch.class, PcdBranch.class, PcdBranch.class.getMethod("getComp_id"), prevUpdWaterMark, curWaterMark);
        update(IzdCompany.class, PcdCompany.class, PcdCompany.class.getMethod("getComp_id"), prevUpdWaterMark, curWaterMark);
        update(IzdCcyTable.class, PcdCurrency.class, PcdCurrency.class.getMethod("getIsoAlpha"), prevUpdWaterMark, curWaterMark);
        update(IzdAgreement.class, PcdAgreement.class, PcdAgreement.class.getMethod("getAgreement"), prevUpdWaterMark, curWaterMark);
        update(IzdAccount.class, PcdAccount.class, PcdAccount.class.getMethod("getAccountNo"), prevUpdWaterMark, curWaterMark);
        update(IzdAccParam.class, PcdAccParam.class, PcdAccParam.class.getMethod("getAccountNo"), prevUpdWaterMark, curWaterMark);
        update(IzdCondCard.class, PcdCondCard.class, PcdCondCard.class.getMethod("getComp_id"), prevUpdWaterMark, curWaterMark);
        update(IzdCard.class, PcdCard.class, PcdCard.class.getMethod("getCard"), prevUpdWaterMark, curWaterMark);
        update(DnbIzdAccntChng.class, PcdProcIds.class, PcdProcIds.class.getMethod("getComp_id"), null, null);
        update(IzdServicesFee.class, PcdServicesFee.class, PcdServicesFee.class.getMethod("getComp_id"), prevUpdWaterMark, curWaterMark);
        update(IzdCondAccnt.class, PcdCondAccnt.class, PcdCondAccnt.class.getMethod("getComp_id"), prevUpdWaterMark, curWaterMark);
        update(IzdCcyConvEx.class, PcdCcyConv.class, PcdCcyConv.class.getMethod("getId"), prevUpdWaterMark, curWaterMark);
    }

    @Override
    protected Map<String, AbstractSpecialUpdateHandler> getAvailableHandlers() {
        Map<String, AbstractSpecialUpdateHandler> updMan = new HashMap<>();

        // This will delete just created records from PcdCardAccounts
        updMan.put(PcdCard.class.getName(), new PcdCardsUpdateHandler());
        updMan.put(PcdAccount.class.getName(), new PcdAccountsUpdateHandler());
        updMan.put(PcdClient.class.getName(), new PcdClientUpdateHandler());
        updMan.put(PcdAccParam.class.getName(), new PcdAccParamUpdateHandler());
        updMan.put(PcdProcIds.class.getName(), new PcdProcIdsUpdateHandler());
        updMan.put(PcdCcyConv.class.getName(), new PcdCcyConvUpdateHandler());
        updMan.put(PcdBin.class.getName(), new PcdBinUpdateHandler());
        return updMan;
    }

    @Override
    protected void blockPPCards() {
        List<PcdCard> list = linkAppDao.getPcdCardListByStatus1andPpCardsStatus("2", BigDecimal.ZERO);
        for (PcdCard card : list) {
            blockPP(card, false);
        }
    }

    public class IzdCardToPcdCardConverter extends AbstractSpecialConverter {
        public Serializable convert(Object s) {
            PcdCard o = (PcdCard) super.convert(s);
            IzdCard i = (IzdCard) (s);

            o.setGroupc(i.getIzdCardType().getComp_id().getGroupc());
            o.setBankC(i.getIzdCardType().getComp_id().getBankC());
            o.setAgreement(i.getIzdAgreement().getAgreNom());

            PcdAccount acc = new PcdAccount();
            acc.setAccountNo(i.getIzdClAcct().getIzdAccount().getAccountNo());
            Set<PcdAccount> pcdAccounts = new HashSet<>();
            pcdAccounts.add(acc);
            log.info("Added account: " + acc.getAccountNo().toString());
            o.setPcdAccounts(pcdAccounts);

            //Reload object, to get session opened
            IzdCardsAddFields fields = cmsDao.getIzdCardsAddFieldsById(i.getCard());
            if (fields != null) {
                o.setUAField1(fields.getUAField1());
                o.setUAField2(fields.getUAField2());
                o.setUAField3(fields.getUAField3());
                o.setUAField4(fields.getUAField4());
                o.setUAField5(fields.getUAField5());
                o.setUAField6(fields.getUAField6());
                o.setUAField7(fields.getUAField7());
                o.setUBField1(fields.getUBField1());
                o.setUACode11(fields.getUACode11());
                o.setUACode12(fields.getUACode12());
            }
            IzdCardsPinBlocks pinBlock = cmsDao.getIzdCardsPinBlocksByCard(i.getCard());
            if (pinBlock != null && pinBlock.getPinBlock() != null) {
                o.setPinBlock("AVAILABLE");
            }
            // Set ctime because update can be initialized by izdCardsAddFields table
            o.setCtime(new Date());
            return o;
        }

        public Class<?> getSourceClass() {
            return IzdCard.class;
        }

        public Class<?> getDestinationClass() {
            return PcdCard.class;
        }

        public IzdCardToPcdCardConverter() {
            super();
            ConvertUtils.register(new BigDecimalConverter(null), java.math.BigDecimal.class);
        }
    }

    public static class MerchantsViewToPcdMerchantConverter extends AbstractSpecialConverter {
        public Class<?> getSourceClass() {
            return MerchantsView.class;
        }

        public Class<?> getDestinationClass() {
            return PcdMerchant.class;
        }
    }

    public static class MerchantParToPcdMerchantParConverter extends AbstractSpecialConverter {
        public Class<?> getSourceClass() {
            return MerchantPar.class;
        }

        public Class<?> getDestinationClass() {
            return PcdMerchantPar.class;
        }
    }

    public static class IzdCcyConvExToPcdCcyConvConverter extends AbstractSpecialConverter {
        public static class IzdCcyConvExPKToPcdCcyConvPK implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null) return null;
                if (arg1 instanceof IzdCcyConvExPK) {
                    PcdCcyConvPK pcdCcyConvPK = new PcdCcyConvPK();
                    pcdCcyConvPK.setBankC(((IzdCcyConvExPK) arg1).getBankC());
                    pcdCcyConvPK.setBuyOrSell(((IzdCcyConvExPK) arg1).getBuyOrSell());
                    pcdCcyConvPK.setCurency(((IzdCcyConvExPK) arg1).getCurency());
                    pcdCcyConvPK.setDateExp(((IzdCcyConvExPK) arg1).getDateExp());
                    pcdCcyConvPK.setFromReconcil(((IzdCcyConvExPK) arg1).getFromReconcil());
                    pcdCcyConvPK.setProcCcy(((IzdCcyConvExPK) arg1).getProcCcy());
                    return pcdCcyConvPK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdServicesFeePK");
                }
            }
        }

        public IzdCcyConvExToPcdCcyConvConverter() {
            super();
            ConvertUtils.register(new IzdCcyConvExPKToPcdCcyConvPK(), PcdCcyConvPK.class);
        }

        public Class<?> getSourceClass() {
            return IzdCcyConvEx.class;
        }

        public Class<?> getDestinationClass() {
            return PcdCcyConv.class;
        }
    }

    public static class IzdServicesFeeToPcdServicesFeeConverter extends AbstractSpecialConverter {
        public static class IzdServicesFeePKToPcdServicesFeePK implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null) return null;
                if (arg1 instanceof IzdServicesFeePK) {
                    PcdServicesFeePK pcdServicesFeePK = new PcdServicesFeePK();
                    pcdServicesFeePK.setServiceType(((IzdServicesFeePK) arg1).getServiceType());
                    pcdServicesFeePK.setAcntCcy(((IzdServicesFeePK) arg1).getAcntCcy());
                    pcdServicesFeePK.setCommGrp(((IzdServicesFeePK) arg1).getCommGrp());
                    pcdServicesFeePK.setBankC(((IzdServicesFeePK) arg1).getBankC());
                    pcdServicesFeePK.setEventArea(((IzdServicesFeePK) arg1).getEventArea());
                    return pcdServicesFeePK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdServicesFeePK");
                }
            }
        }

        public IzdServicesFeeToPcdServicesFeeConverter() {
            super();
            ConvertUtils.register(new IzdServicesFeePKToPcdServicesFeePK(), PcdServicesFeePK.class);
        }

        public Class<?> getSourceClass() {
            return IzdServicesFee.class;
        }

        public Class<?> getDestinationClass() {
            return PcdServicesFee.class;
        }
    }

    public static class IzdCondAccntToPcdCondAccntConverter extends AbstractSpecialConverter {
        public static class IzdCondAccntPKToPcdCondAccntPK implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null) return null;
                if (arg1 instanceof IzdCondAccntPK) {
                    PcdCondAccntPK pcdCondAccntPK = new PcdCondAccntPK();
                    pcdCondAccntPK.setBankC(((IzdCondAccntPK) arg1).getBankC());
                    pcdCondAccntPK.setGroupc(((IzdCondAccntPK) arg1).getGroupc());
                    pcdCondAccntPK.setCondSet(((IzdCondAccntPK) arg1).getCondSet());
                    pcdCondAccntPK.setCcy(((IzdCondAccntPK) arg1).getCcy());
                    return pcdCondAccntPK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdCondAccntPK");
                }
            }
        }

        public IzdCondAccntToPcdCondAccntConverter() {
            super();
            ConvertUtils.register(new IzdCondAccntPKToPcdCondAccntPK(), PcdCondAccntPK.class);
        }

        public Class<?> getSourceClass() {
            return IzdCondAccnt.class;
        }

        public Class<?> getDestinationClass() {
            return PcdCondAccnt.class;
        }
    }

    public class IzdAccountToPcdAccountConverter extends AbstractSpecialConverter {
        public Serializable convert(Object s) {
            PcdAccount o = (PcdAccount) super.convert(s);
            IzdAccount i = (IzdAccount) (s);

            //TODO ask why like this, delete if new implementation is ok ?
            //o.setBankC(i.getIzdAccParam().getIzdCardGroupCcy().getComp_id().getBankC());
            o.setBankC(i.getBankC());

            Set<IzdClAcct> clAccts = cmsDao.getIzdClAcctSetById(i.getAccountNo());
            List<PcdCard> pcdCards = new ArrayList<>();

            for (IzdClAcct clAcct : clAccts) {
                Set<IzdCard> cards = cmsDao.getIzdCardSetByClAcct(clAcct.getTabKey());
                for (IzdCard izdCard : cards) {
                    PcdCard pcdCard = new PcdCard();
                    pcdCard.setCard(izdCard.getCard());
                    pcdCards.add(pcdCard);
                    log.info("Added card: " + pcdCard.getCard());
                }
                if (clAcct.getIzdAccount().getAccountNo().equals(o.getAccountNo())) {
                    o.setIban(clAcct.getIban());
                    log.info("Added IBAN: " + clAcct.getIban());
                }
            }
            o.setPcdCards(pcdCards);
            return o;
        }

        public Class<?> getSourceClass() {
            return IzdAccount.class;
        }

        public Class<?> getDestinationClass() {
            return PcdAccount.class;
        }

        public IzdAccountToPcdAccountConverter() {
            super();
            ConvertUtils.register(new BigDecimalConverter(null), java.math.BigDecimal.class);
        }
    }

    public void blockPP(PcdCard pcdCard, boolean renew) {
        if (pcdCard.getPcdPpCards() != null && !pcdCard.getPcdPpCards().isEmpty()) {
            for (PcdPpCard pcdPPCard : pcdCard.getPcdPpCards()) {
                if (!pcdPPCard.getStatus().equals(BigDecimal.ZERO)) {
                    pcdPPCard.setCtime(new Date());
                    pcdPPCard.setStatus(BigDecimal.ZERO);
                    pcdPPCard.setEmailStatus(BigDecimal.valueOf(3));
                    if (renew) {
                        pcdPPCard.setExpiryDate(new Date());
                    }
                    PcdLog newLog = new PcdLog();
                    newLog.setSource("pp-cards");
                    newLog.setOperation("block-pp");
                    newLog.setOperator("");
                    if (renew) {
                        newLog.setOperation("renew-pp");
                        newLog.setText("Card: " + pcdPPCard.getFullCardNr() + " Cause: Renew of Visa card: " + (pcdCard.getCard()));
                    } else {
                        newLog.setOperation("block-pp");
                        newLog.setText("Card: " + pcdPPCard.getFullCardNr() + " Cause: Blocking of Visa card: " + (pcdCard.getCard()));
                    }
                    newLog.setResult("SUCCESSFUL");
                    newLog.setRecDate(new Date());
                    log.info("PP block " + pcdPPCard.getPcdCard().getCard());
                    linkAppDao.savePcdLog(newLog);
                    linkAppDao.savePcdPpCard(pcdPPCard);
                }
            }
        }
    }

    public class PcdProcIdsUpdateHandler extends AbstractSpecialUpdateHandler {

        private void trySynchronizeSlips(long proc_id, String bank_c, String groupc) {

            DataAndStatelessSessionHolder<ScrollableResults> data = cmsDao.getIzdSlip(bank_c, proc_id, groupc);

            int updatedSlips = 0;
//			int slipsToUpdate=0;
            while (data.getData().next()) {
                Object[] row = data.getData().get();
                PcdSlip newSlip = new PcdSlip();
                newSlip.setMerchant(row[0] != null ? (String) row[0] : "");
                newSlip.setSettlCmi(row[1] != null ? (String) row[1] : "");
                newSlip.setSendCmi(row[2] != null ? (String) row[2] : "");
                newSlip.setRecCmi(row[3] != null ? (String) row[3] : "");
                newSlip.setCard(row[4] != null ? (String) row[4] : "");
                newSlip.setExpDate(row[5] != null ? (Date) row[5] : null);
                newSlip.setTranAmt(row[6] != null ? ((BigDecimal) row[6]).longValue() : 0);
                newSlip.setCcyExp(row[7] != null ? (String) row[7] : "");
                newSlip.setTranCcy(row[8] != null ? (String) row[8] : "");
                newSlip.setTranDateTime(row[9] != null ? (Date) row[9] : null);
                newSlip.setTranType(row[10] != null ? (String) row[10] : "");
                newSlip.setAprCode(row[11] != null ? (String) row[11] : "");
                newSlip.setStan(row[12] != null ? (String) row[12] : "");
                newSlip.setFee(row[13] != null ? ((BigDecimal) row[13]).longValue() : 0);
                newSlip.setAbvrName(row[14] != null ? (String) row[14] : "");
                newSlip.setCity(row[15] != null ? (String) row[15] : "");
                newSlip.setCountry(row[16] != null ? (String) row[16] : "");
                newSlip.setTerminal(row[17] != null ? (String) row[17] : "");
                newSlip.setRecDate(row[18] != null ? (Date) row[18] : null);
                newSlip.setAccntCcy(row[19] != null ? (String) row[19] : "");
                newSlip.setAccntAmt(row[20] != null ? ((BigDecimal) row[20]).longValue() : 0);
                newSlip.setAmount(row[21] != null ? ((BigDecimal) row[21]).longValue() : 0);
                newSlip.setPostDate(row[22] != null ? (Date) row[22] : null);
                newSlip.setErrCode(row[23] != null ? (String) row[23] : "");
                newSlip.setAcqrefNr(row[24] != null ? (String) row[24] : "");
                newSlip.setTermId(row[25] != null ? (String) row[25] : "");
                newSlip.setAccountNo(row[26] != null ? (BigDecimal) row[26] : null);
                newSlip.setProcId(row[27] != null ? ((BigDecimal) row[27]).longValue() : 0);
                newSlip.setBankC(row[28] != null ? (String) row[28] : "");
                newSlip.setCtime(row[29] != null ? (Date) row[29] : null);
                newSlip.setGroupc(row[30] != null ? (String) row[30] : "");
                newSlip.setDebCred(row[31] != null ? (BigDecimal) row[31] : null);
                newSlip.setTrCode(row[32] != null ? (String) row[32] : "");
                newSlip.setTrFee(row[33] != null ? ((BigDecimal) row[33]).longValue() : 0);
                newSlip.setSbAmount(row[34] != null ? ((BigDecimal) row[34]).longValue() : 0);
                newSlip.setBranch(row[35] != null ? (String) row[35] : "");
                newSlip.setCardAcct(row[36] != null ? (String) row[36] : "");
                linkAppDao.saveOrUpdatePcdSlip(newSlip);
                updatedSlips++;
            }

            data.getStatelessSession().close();
            log.info(updatedSlips + " slips updated");
        }

        private void clearPcdSlip(PcdProcIds procId) {
            linkAppDao.deletePcdSlipByProcIdbankCgroupc(procId.getComp_id().getProcId(), procId.getComp_id().getBankC(), procId.getComp_id().getGroupc());
            log.info("Clearing records from PcdSlip for bankC='" + procId.getComp_id().getBankC() + "', groupc='" + procId.getComp_id().getGroupc() +
                    "', procId='" + procId.getComp_id().getProcId() + "': [linkAppDao.deletePcdSlipByProcIdbankCgroupc]");
        }

        public void beforeInsertFilter(Object o) {
            this.clearPcdSlip((PcdProcIds) o);
            this.trySynchronizeSlips(((PcdProcIds) o).getComp_id().getProcId(),
                    ((PcdProcIds) o).getComp_id().getBankC(),
                    ((PcdProcIds) o).getComp_id().getGroupc());
        }

        public void beforeUpdateFilter(Object oldO, Object newO) {
            this.clearPcdSlip((PcdProcIds) newO);
            this.trySynchronizeSlips(((PcdProcIds) newO).getComp_id().getProcId(),
                    ((PcdProcIds) newO).getComp_id().getBankC(),
                    ((PcdProcIds) newO).getComp_id().getGroupc());
        }
    }


    public class PcdCcyConvUpdateHandler extends AbstractSpecialUpdateHandler {
        //Removing old courses
        @SuppressWarnings("unchecked")
        public void beforeInsertFilter(Object o) {
            PcdCcyConv newCcyConv = (PcdCcyConv) o;
            PcdCcyConv oldCcyConv = null;
            Iterator<PcdCcyConv> it = linkAppDao.getPcdCcyConv(newCcyConv.getId().getBankC(),
                    newCcyConv.getId().getCurency(),
                    newCcyConv.getId().getProcCcy(),
                    newCcyConv.getId().getFromReconcil(),
                    newCcyConv.getId().getBuyOrSell());
            if (it.hasNext()) {
                oldCcyConv = it.next();
            }
            if (oldCcyConv == null) {
                log.info("No old currency course record");
            } else {
                log.info("Deleting previous record");
                linkAppDao.deletePcdCcyConv(oldCcyConv);
            }
        }
    }

    public static class PcdBinUpdateHandler extends AbstractSpecialUpdateHandler {
        public void beforeUpdateFilter(Object oldO, Object newO) {
            ((PcdBin) newO).setSpecialCardName(((PcdBin) oldO).getSpecialCardName());
        }
    }

    public class PcdCardsUpdateHandler extends AbstractSpecialUpdateHandler {

        private void createNewPP(PcdCard pcdCard) {
            if (pcdCard.getPcdPpCards() == null)
                pcdCard.setPcdPpCards(new ArrayList<>());
            Iterator<PcdPpCard> it = pcdCard.getPcdPpCards().iterator();
            PcdPpCard pcdPPCard = null;
            if (it.hasNext()) {
                pcdPPCard = it.next();
            }
            while (it.hasNext()) {
                PcdPpCard tmp = it.next();
                if (tmp.getRegDate().after(pcdPPCard.getRegDate())) {
                    pcdPPCard = tmp;
                }
            }

            if (pcdPPCard != null) {
                if (!pcdPPCard.getStatus().equals(BigDecimal.valueOf(2))) {
                    pcdPPCard = new PcdPpCard();
                    pcdPPCard.setStatus(BigDecimal.valueOf(2));
                    pcdPPCard.setEmailStatus(BigDecimal.ZERO);
                    pcdPPCard.setPcdCard(pcdCard);
                    pcdPPCard.setCtime(new Date());
                    linkAppDao.savePcdPpCard(pcdPPCard);
                    pcdCard.getPcdPpCards().add(pcdPPCard);
                    log.info("New ppCard " + pcdPPCard.getCardNumber());
                } else {
                    log.info("There is an embossing card, no need to create a new pp.");
                }
            } else {
                pcdPPCard = new PcdPpCard();
                pcdPPCard.setStatus(BigDecimal.valueOf(2));
                pcdPPCard.setEmailStatus(BigDecimal.ZERO);
                pcdPPCard.setPcdCard(pcdCard);
                pcdPPCard.setCtime(new Date());
                linkAppDao.savePcdPpCard(pcdPPCard);
                pcdCard.getPcdPpCards().add(pcdPPCard);
                log.info("New ppCard " + pcdPPCard.getCardNumber());
            }

        }

        public void beforeUpdateFilter(Object oldObj, Object newObj) {
/**
 * <PriorityPass>
 */
            if (PcdPpCard.isPriorityPassEligible(((PcdCard) newObj).getCard())) {
                oldObj = linkAppDao.getPcdCardById(((PcdCard) oldObj).getCard());
                if (((PcdCard) oldObj).getPcdPpCards() != null)
                    ((PcdCard) oldObj).getPcdPpCards().size(); // Init collection
                linkAppDao.evictPcdCard(((PcdCard) oldObj));
                if (!((PcdCard) newObj).getStatus1().equals("2")) {
                    if (((PcdCard) newObj).getExpiry2() != null) {
                        if (((PcdCard) oldObj).getExpiry2() == null ||
                                (((PcdCard) oldObj).getExpiry2().compareTo(((PcdCard) newObj).getExpiry2()) != 0)) {
                            log.info("Prolonging " + getCardName(((PcdCard) newObj).getCard()) + "! Blocking old PP card");
                            blockPP((PcdCard) oldObj, true);
                            if (((PcdCard) newObj).getCard().substring(0, 6).equals("492176")
                                    && ((PcdCard) newObj).getCardType().equals("01")) {
                                log.info("New PP card will not be created for employee business card");
                            } else {
                                log.info("Creating new PP card");
                                createNewPP((PcdCard) oldObj);
                            }
                        }
                    }
                } else if (!((PcdCard) oldObj).getStatus1().equals("2")) {
                    log.info("Visa card is blocked! Blocking PP");
                    blockPP((PcdCard) oldObj, false);
                }
            }

/**
 * </PriorityPass>
 */
            Set<String> renewToExclude = new HashSet<>();
            renewToExclude.add("7");
            renewToExclude.add("G");

            // Delivery-block renewed cards
            if (((PcdCard) newObj).getRenewDate() != null && ((PcdCard) oldObj).getRenewDate() != null
                    && !((PcdCard) newObj).getRenewDate().equals(((PcdCard) oldObj).getRenewDate())) {
                if (newObj instanceof PcdCard) {
                    PcdCard card = (PcdCard) newObj;

                    //Don't block hard blocked cards and cards whose renew flag changed due to PIN reminders
                    if (!card.getStatus1().equals("2") && !renewToExclude.contains(card.getRenew())) {
                        log.info("Card renewal: adding to RMS stoplist");

                        DateFormat formatter = new SimpleDateFormat("yyMM");
                        wsClient.addCardToRMS(card.getCard(), formatter.format(card.getExpiry2() == null ? card.getExpiry1() : card.getExpiry2()),
                                CardUtils.composeCentreIdFromPcdCard(card));
                    }
                }
            }

/**
 * <CardDesign>
 */
            if (((PcdCard) oldObj).getDesign() != null) {
                if (((PcdCard) newObj).getRenewDate() != null) {
                    if (((PcdCard) oldObj).getRenewDate() != null) {
                        if (!((PcdCard) newObj).getRenewDate().equals(((PcdCard) oldObj).getRenewDate())) {

                            ((PcdCard) newObj).setDesign(linkAppDao.getDesign((PcdCard) newObj));
                        } else {
                            ((PcdCard) newObj).setDesign(((PcdCard) oldObj).getDesign());
                        }
                    } else {
                        ((PcdCard) newObj).setDesign(linkAppDao.getDesign((PcdCard) newObj));
                    }
                }
            } else {
                ((PcdCard) newObj).setDesign(linkAppDao.getDesign((PcdCard) newObj));
            }
/**
 * </CardDesign>
 */
            // Renew status
            PcdCard saved = (PcdCard) oldObj;
            PcdCard updated = (PcdCard) newObj;
            updated.setRenewOld(saved.getRenewOld()); // Set same status for renew old
            if (updated.getRenew() == null || !updated.getRenew().equals(saved.getRenew())) { // Update renew old status only if renew status is changed
                log.info("renewOld card " + saved.getCard() + " change from " + saved.getRenewOld() + " to " + saved.getNextRenewOld() + " and renew " + updated.getRenew());
                updated.setRenewOld(saved.getNextRenewOld());
            }

            // Contactless status
            BigDecimal contactless = cmsDao.getContactlessByCard(saved.getCard());
            if ((contactless != null && !contactless.equals(saved.getContactless())) ||
                    (contactless == null && saved.getContactless() != null)) {
                log.info("Change contactless from " + saved.getContactless() + " to " + contactless + " for " + saved.getCard());
                updated.setContactless(contactless == null ? null : contactless.intValue());
            }
        }

        public void beforeInsertFilter(Object o) {
            PcdCard card = (PcdCard) o;
            linkAppDao.deletePcdCardsAccountByCard(card.getCard());
            log.info("Trying to prepare pcdCardsAccount [linkAppDao.deletePcdCardsAccountByCard], card = " + card.getCard());
            if (appProperties.isAddCardToStopOnUpdate()/* && (!acct.getCardAcct().startsWith("L"))*/) { // Co-ex
                if (!card.getStatus1().equals("2")) { // Don't block hard blocked card
                    if (card.getUAField4() != null) {
                        log.info("New migrated card: skipping delivery block setup and applying E-commerce block");
                        wsClient.addElectronicCommerceBlock(card.getCard());
                    } else {
                        log.info("New card: adding to RMS stoplist");
                        DateFormat formatter = new SimpleDateFormat("yyMM");
                        wsClient.addCardToRMS(card.getCard(), formatter.format(card.getExpiry2() == null ? card.getExpiry1() : card.getExpiry2()),
                                CardUtils.composeCentreIdFromPcdCard(card));
                    }
                }
            }
        }

        public void afterInsertFilter(Object o) {
            PcdCard tmpCard = (PcdCard) o;
            //PriorityPass
            if (PcdPpCard.isPriorityPassEligible(tmpCard.getCard())) {
                if (tmpCard.getCard().substring(0, 6).equals("492176") && tmpCard.getCardType().equals("01")) {
                    log.info("New PP card will not be created for employee business card");
                } else {
                    log.info("New " + getCardName(tmpCard.getCard()) + "! Creating new PP card");
                    PcdPpCard pcdPPCard = new PcdPpCard();
                    pcdPPCard.setStatus(BigDecimal.valueOf(2));
                    pcdPPCard.setEmailStatus(BigDecimal.ZERO);
                    pcdPPCard.setPcdCard((PcdCard) o);
                    pcdPPCard.setCtime(new Date());
                    linkAppDao.savePcdPpCard(pcdPPCard);
                }
            }
            tmpCard.setDesign(linkAppDao.getDesign(tmpCard));

            // Contactless status
            BigDecimal contactless = cmsDao.getContactlessByCard(tmpCard.getCard());
            linkAppDao.updatePcdCardContactless(tmpCard.getCard(), contactless == null ? null : contactless.intValue());
        }
    }

    public static class DateConverter extends AbstractSpecialConverter {
        public static class NullableDateConverter implements org.apache.commons.beanutils.Converter {

            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null) return null;
                if (arg1 instanceof Date) {
                    Date date = new Date();
                    date.setTime(((Date) arg1).getTime());
                    return date;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to Date");
                }
            }
        }

        public DateConverter() {
            super();
            ConvertUtils.register(new NullableDateConverter(), Date.class);
        }

        public Class<?> getSourceClass() {
            return Date.class;
        }

        public Class<?> getDestinationClass() {
            return Date.class;
        }
    }

    public static String getCardName(String card) {
        if (CARD_NAMES.containsKey(card.substring(0, 7))) {
            return CARD_NAMES.get(card.substring(0, 7));
        } else if (CARD_NAMES.containsKey(card.substring(0, 6))) {
            return CARD_NAMES.get(card.substring(0, 6));
        } else {
            return "";
        }
    }

}

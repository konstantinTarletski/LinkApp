package lv.bank.cards.dbsynchronizer.service;

import lv.bank.cards.core.entity.cms.DnbIzdAccntChng;
import lv.bank.cards.core.entity.cms.DnbIzdAccntChngPK;
import lv.bank.cards.core.entity.cms.IzdAccParam;
import lv.bank.cards.core.entity.cms.IzdAgreement;
import lv.bank.cards.core.entity.cms.IzdBinTable;
import lv.bank.cards.core.entity.cms.IzdBinTablePK;
import lv.bank.cards.core.entity.cms.IzdBranch;
import lv.bank.cards.core.entity.cms.IzdBranchPK;
import lv.bank.cards.core.entity.cms.IzdCardGroup;
import lv.bank.cards.core.entity.cms.IzdCardGroupPK;
import lv.bank.cards.core.entity.cms.IzdCardType;
import lv.bank.cards.core.entity.cms.IzdCardTypePK;
import lv.bank.cards.core.entity.cms.IzdCcyTable;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.entity.cms.IzdClientPK;
import lv.bank.cards.core.entity.cms.IzdCompany;
import lv.bank.cards.core.entity.cms.IzdCompanyPK;
import lv.bank.cards.core.entity.cms.IzdCondCard;
import lv.bank.cards.core.entity.cms.IzdCondCardPK;
import lv.bank.cards.core.entity.cms.IzdDbOwner;
import lv.bank.cards.core.entity.cms.IzdRepDistribut;
import lv.bank.cards.core.entity.cms.IzdRepDistributPK;
import lv.bank.cards.core.entity.cms.IzdRepLang;
import lv.bank.cards.core.entity.cms.IzdRepLangPK;
import lv.bank.cards.core.entity.cms.IzdStopCause;
import lv.bank.cards.core.entity.cms.IzdStopCausePK;
import lv.bank.cards.core.entity.cms.NordlbBranch;
import lv.bank.cards.core.entity.linkApp.PcdAccParam;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdAgreement;
import lv.bank.cards.core.entity.linkApp.PcdBin;
import lv.bank.cards.core.entity.linkApp.PcdBinPK;
import lv.bank.cards.core.entity.linkApp.PcdBranch;
import lv.bank.cards.core.entity.linkApp.PcdBranchPK;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardGroup;
import lv.bank.cards.core.entity.linkApp.PcdCardGroupPK;
import lv.bank.cards.core.entity.linkApp.PcdCardType;
import lv.bank.cards.core.entity.linkApp.PcdCardTypePK;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.entity.linkApp.PcdClientPK;
import lv.bank.cards.core.entity.linkApp.PcdCompany;
import lv.bank.cards.core.entity.linkApp.PcdCompanyPK;
import lv.bank.cards.core.entity.linkApp.PcdCondCard;
import lv.bank.cards.core.entity.linkApp.PcdCondCardPK;
import lv.bank.cards.core.entity.linkApp.PcdCurrency;
import lv.bank.cards.core.entity.linkApp.PcdDbOwner;
import lv.bank.cards.core.entity.linkApp.PcdLastUpdated;
import lv.bank.cards.core.entity.linkApp.PcdProcIds;
import lv.bank.cards.core.entity.linkApp.PcdProcIdsPK;
import lv.bank.cards.core.entity.linkApp.PcdRepDistribut;
import lv.bank.cards.core.entity.linkApp.PcdRepDistributPK;
import lv.bank.cards.core.entity.linkApp.PcdRepLang;
import lv.bank.cards.core.entity.linkApp.PcdRepLangPK;
import lv.bank.cards.core.entity.linkApp.PcdStopCause;
import lv.bank.cards.core.entity.linkApp.PcdStopCausePK;
import lv.bank.cards.dbsynchronizer.config.ApplicationProperties;
import lv.bank.cards.dbsynchronizer.dao.CmsDao;
import lv.bank.cards.dbsynchronizer.dao.LinkAppDao;
import lv.bank.cards.dbsynchronizer.jpa.pcd.PcdLastUpdatedRepository;
import lv.bank.cards.dbsynchronizer.utils.AbstractSpecialConverter;
import lv.bank.cards.dbsynchronizer.utils.AbstractSpecialUpdateHandler;
import lv.bank.cards.dbsynchronizer.utils.Converter;
import lv.bank.cards.dbsynchronizer.utils.DataAndStatelessSessionHolder;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.hibernate.ScrollableResults;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class UpdateCmsServiceBase {

    private static final Logger log = Logger.getLogger(UpdateCmsServiceBase.class);

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    protected final CmsDao cmsDao;
    protected final LinkAppDao linkAppDao;
    protected final ApplicationProperties appProperties;
    protected final PcdLastUpdatedRepository pcdLastUpdatedRepository;
    protected Map<String, AbstractSpecialUpdateHandler> handlers;
    protected WebServiceClient wsClient;

    public UpdateCmsServiceBase(CmsDao cmsDao,
                                LinkAppDao linkAppDao,
                                ApplicationProperties appProperties,
                                PcdLastUpdatedRepository pcdLastUpdatedRepository) {
        this.cmsDao = cmsDao;
        this.linkAppDao = linkAppDao;
        this.appProperties = appProperties;
        this.pcdLastUpdatedRepository = pcdLastUpdatedRepository;
        wsClient = new WebServiceClient(appProperties.getWsdlLocation());

        Converter.defConverter(new IzdCompanyToPcdCompanyConverter());
        Converter.defConverter(new IzdStopCauseToPcdStopCauseConverter());
        Converter.defConverter(new IzdDbOwnerToPcdDbOwnerConverter());
        Converter.defConverter(new IzdClientToPcdClientConverter());
        Converter.defConverter(new IzdCardGroupToPcdCardGroupConverter());
        Converter.defConverter(new IzdCcyTableToPcdCurrencyConverter());
        Converter.defConverter(new IzdBinTableToPcdBinConverter());
        Converter.defConverter(new IzdRepLangToPcdRepLangConverter());
        Converter.defConverter(new IzdCondCardToPcdCondCardConverter());
        Converter.defConverter(new IzdAgreementToPcdAgreementConverter());
        Converter.defConverter(new IzdAccParamToPcdAccParamConverter());
        Converter.defConverter(new IzdCardTypeToPcdCardTypeConverter());
        Converter.defConverter(new IzdRepDistributToPcdRepDistributConverter());
        Converter.defConverter(new DnbIzdAccntChngToPcdProcIdsConverter());
        // This is necessary to get null's for Integer.class
        ConvertUtils.register(new BrokenNullConverter(), Integer.class);
        // This is necessary to get null's for BigInteger.class
        ConvertUtils.register(new BrokenNullBigIntegerConverter(), BigInteger.class);
    }

    public void updateDb(Date prevUpdWaterMark, Date curWaterMark) throws NoSuchMethodException {
        long startTime = System.currentTimeMillis();
        log.info("updateDb CMS START");
        cmsDao.enableFilter();
        update(prevUpdWaterMark, curWaterMark);
        updateContactlessStatus();
        blockPPCards();
        long totalTime = (System.currentTimeMillis() - startTime)/1000;
        log.info("updateDb CMS - END, time of execution is = " + totalTime + " seconds");
    }

    protected abstract Map<String, AbstractSpecialUpdateHandler> getAvailableHandlers();

    protected abstract void update(Date prevUpdWaterMark, Date curWaterMark) throws NoSuchMethodException;

    protected abstract void blockPPCards();

    protected void update(Class<?> s, Class<?> d, Method getIdMethod, Date lastUpdate, Date currentLevel) {
        log.info("PROCESSING update from " + s.getSimpleName() + " to " + d.getSimpleName());
        DataAndStatelessSessionHolder<ScrollableResults> result = cmsDao.findDataToUpdate(s, d, lastUpdate, currentLevel);
        linkAppDao.processUpdate(result.getData(), d, getIdMethod, getAvailableHandlers());
        if (result.getStatelessSession() != null){
            result.getStatelessSession().close();
        }
    }

    public void updateContactlessStatus() {
        Date u = pcdLastUpdatedRepository
                .findById(appProperties.getNordlbBankc())
                .map(PcdLastUpdated::getCdate)
                .orElse(null);

        Date ctime = cmsDao.getIzdShadowCtrlCtime();
        if (ctime.after(u)) {
            List<PcdCard> cards = linkAppDao.getPcdCardsByContactless(Arrays.asList(2, 3));
            for (PcdCard card : cards) {
                BigDecimal contactless = cmsDao.getContactlessByCard(card.getCard());
                if ((contactless != null && !contactless.equals(card.getContactless())) ||
                        (contactless == null && card.getContactless() != null)) {
                    log.info("Change contactless from " + card.getContactless() + " to " + contactless + " for " + card.getCard());
                    Integer newContactless = contactless == null ? null : contactless.intValue();
                    linkAppDao.updatePcdCard(card.getCard(), newContactless, new Date());
                }
            }
        }
    }

    public static class BrokenNullBigIntegerConverter implements org.apache.commons.beanutils.Converter {
        @SuppressWarnings({"rawtypes", "unchecked"})
        public Object convert(Class arg0, Object arg1) {
            if (arg1 == null)
                return null;
            return (BigInteger) arg1;
        }
    }

    public static class BrokenNullConverter implements org.apache.commons.beanutils.Converter {
        @SuppressWarnings({"rawtypes", "unchecked"})
        public Object convert(Class arg0, Object arg1) {
            if (arg1 == null)
                return null;
            return (Integer) arg1;
        }
    }

    public static class DnbIzdAccntChngToPcdProcIdsConverter extends AbstractSpecialConverter {
        static class DnbIzdAccntChngPKToPcdProcIdsPKConverter implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null) return null;
                if (arg1 instanceof DnbIzdAccntChngPK) {
                    PcdProcIdsPK pcdProcIdsPK = new PcdProcIdsPK();
                    pcdProcIdsPK.setBankC(((DnbIzdAccntChngPK) arg1).getBankC());
                    pcdProcIdsPK.setGroupc(((DnbIzdAccntChngPK) arg1).getGroupc());
                    pcdProcIdsPK.setProcId(((DnbIzdAccntChngPK) arg1).getProcId());
                    return pcdProcIdsPK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdProcIdsPK");
                }
            }
        }

        public DnbIzdAccntChngToPcdProcIdsConverter() {
            super();
            ConvertUtils.register(new DnbIzdAccntChngPKToPcdProcIdsPKConverter(), PcdProcIdsPK.class);
        }

        public Class<?> getSourceClass() {
            return DnbIzdAccntChng.class;
        }

        public Class<?> getDestinationClass() {
            return PcdProcIds.class;
        }
    }

    public static class IzdRepDistributToPcdRepDistributConverter extends AbstractSpecialConverter {

        static class IzdRepDistributPKToPcdRepDistributPKConverter implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null)
                    return null;
                if (arg1 instanceof IzdRepDistributPK) {
                    PcdRepDistributPK pcdRepDistributPK = new PcdRepDistributPK();
                    pcdRepDistributPK.setBankC(((IzdRepDistributPK) arg1).getBankC());
                    pcdRepDistributPK.setCode(((IzdRepDistributPK) arg1).getCode());
                    return pcdRepDistributPK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdRepDistributPK");
                }
            }
        }

        public IzdRepDistributToPcdRepDistributConverter() {
            super();
            ConvertUtils.register(new IzdRepDistributPKToPcdRepDistributPKConverter(), PcdRepDistributPK.class);
        }

        public Class<?> getSourceClass() {
            return IzdRepDistribut.class;
        }

        public Class<?> getDestinationClass() {
            return PcdRepDistribut.class;
        }
    }

    public static class IzdCardTypeToPcdCardTypeConverter extends AbstractSpecialConverter {

        static class IzdCardTypePKToPcdCardTypePKConverter implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null)
                    return null;
                if (arg1 instanceof IzdCardTypePK) {
                    PcdCardTypePK pcdCardTypePK = new PcdCardTypePK();
                    pcdCardTypePK.setBankC(((IzdCardTypePK) arg1).getBankC());
                    pcdCardTypePK.setGroupc(((IzdCardTypePK) arg1).getGroupc());
                    pcdCardTypePK.setCType(((IzdCardTypePK) arg1).getCType());
                    return pcdCardTypePK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdCardTypePK");
                }
            }
        }

        public IzdCardTypeToPcdCardTypeConverter() {
            super();
            ConvertUtils.register(new IzdCardTypePKToPcdCardTypePKConverter(), PcdCardTypePK.class);
        }

        public Class<?> getSourceClass() {
            return IzdCardType.class;
        }

        public Class<?> getDestinationClass() {
            return PcdCardType.class;
        }
    }

    public static class IzdAccParamToPcdAccParamConverter extends AbstractSpecialConverter {

        public Serializable convert(Object s) {
            PcdAccParam o = (PcdAccParam) super.convert(s);
            IzdAccParam i = (IzdAccParam) (s);

            log.debug("IzdAccParamToPcdAccParamConverter.convert, PcdAccParam = {}" + o);
            log.debug("IzdAccParamToPcdAccParamConverter.convert, IzdAccParam = {}" + i);

            o.setPcdAccount(new PcdAccount());
            o.getPcdAccount().setAccountNo(i.getAccountNo());
            o.setPcdCurrency(new PcdCurrency());
            o.getPcdCurrency().setIsoAlpha(i.getIzdCardGroupCcy().getComp_id().getCcy());

            return o;
        }

        public Class<?> getSourceClass() {
            return IzdAccParam.class;
        }

        public Class<?> getDestinationClass() {
            return PcdAccParam.class;
        }
    }

    public static class IzdAgreementToPcdAgreementConverter extends AbstractSpecialConverter {
        public Serializable convert(Object s) {
            PcdAgreement o = (PcdAgreement) super.convert(s);
            IzdAgreement i = (IzdAgreement) (s);

            log.debug("IzdAgreementToPcdAgreementConverter.convert, PcdAgreement = {}" + o);
            log.debug("IzdAgreementToPcdAgreementConverter.convert, IzdAgreement = {}" + i);

            o.setAgreement(i.getAgreNom());
            o.setBranch(i.getIzdBranch().getComp_id().getBranch());
            o.setClient(i.getIzdClient().getComp_id().getClient());
            o.setBankC(i.getIzdClient().getComp_id().getBankC());
            o.setCity(i.getCity());
            o.setStreet(i.getStreet());
            o.setCountry(i.getCountry());

            return o;
        }

        public Class<?> getSourceClass() {
            return IzdAgreement.class;
        }

        public Class<?> getDestinationClass() {
            return PcdAgreement.class;
        }
    }

    public static class IzdCondCardToPcdCondCardConverter extends AbstractSpecialConverter {

        public Class<?> getSourceClass() {
            return IzdCondCard.class;
        }

        public Class<?> getDestinationClass() {
            return PcdCondCard.class;
        }

        static class IzdCondCardPKToPcdCondCardPKConverter implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null) return null;
                if (arg1 instanceof IzdCondCardPK) {
                    PcdCondCardPK pcdCondCardPK = new PcdCondCardPK();
                    pcdCondCardPK.setBankC(((IzdCondCardPK) arg1).getBankC());
                    pcdCondCardPK.setCcy(((IzdCondCardPK) arg1).getCcy());
                    pcdCondCardPK.setCondSet(((IzdCondCardPK) arg1).getCondSet());
                    pcdCondCardPK.setGroupc(((IzdCondCardPK) arg1).getGroupc());
                    return pcdCondCardPK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdCondCardPK");
                }
            }
        }

        public IzdCondCardToPcdCondCardConverter() {
            super();
            ConvertUtils.register(new IzdCondCardPKToPcdCondCardPKConverter(), PcdCondCardPK.class);
        }
    }

    public static class IzdRepLangToPcdRepLangConverter extends AbstractSpecialConverter {

        static class IzdRepLangPKToPcdRepLangPKConverter implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null)
                    return null;
                if (arg1 instanceof IzdRepLangPK) {
                    PcdRepLangPK pcdRepLangPK = new PcdRepLangPK();
                    pcdRepLangPK.setBankC(((IzdRepLangPK) arg1).getBankC());
                    pcdRepLangPK.setLanCode(((IzdRepLangPK) arg1).getLanCode());
                    return pcdRepLangPK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdRepLangPK");
                }
            }
        }

        public IzdRepLangToPcdRepLangConverter() {
            super();
            ConvertUtils.register(new IzdRepLangPKToPcdRepLangPKConverter(), PcdRepLangPK.class);
        }

        public Class<?> getSourceClass() {
            return IzdRepLang.class;
        }

        public Class<?> getDestinationClass() {
            return PcdRepLang.class;
        }
    }

    public static class IzdBinTableToPcdBinConverter extends AbstractSpecialConverter {

        static class IzdBinTablePKToPcdBinTablePKConverter implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null) return null;
                if (arg1 instanceof IzdBinTablePK) {
                    PcdBinPK pcdBinPK = new PcdBinPK();
                    pcdBinPK.setBankC(((IzdBinTablePK) arg1).getBankC());
                    pcdBinPK.setGroupc(((IzdBinTablePK) arg1).getCardGroupCode());
                    pcdBinPK.setBinCode(((IzdBinTablePK) arg1).getBinCode());
                    return pcdBinPK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdBinPK");
                }
            }
        }

        public IzdBinTableToPcdBinConverter() {
            super();
            ConvertUtils.register(new IzdBinTablePKToPcdBinTablePKConverter(), PcdBinPK.class);
        }

        public Class<?> getSourceClass() {
            return IzdBinTable.class;
        }

        public Class<?> getDestinationClass() {
            return PcdBin.class;
        }
    }

    public static class IzdCcyTableToPcdCurrencyConverter extends AbstractSpecialConverter {

        public Serializable convert(Object s) {
            PcdCurrency o = (PcdCurrency) super.convert(s);
            IzdCcyTable i = (IzdCcyTable) s;
            o.setIsoAlpha(i.getCcy());
            o.setIsoNum(i.getCcyCode());
            return o;
        }

        public Class<?> getSourceClass() {
            return IzdCcyTable.class;
        }

        public Class<?> getDestinationClass() {
            return PcdCurrency.class;
        }

    }

    public static class IzdCardGroupToPcdCardGroupConverter extends AbstractSpecialConverter {
        public static class IzdCardGroupPKToPcdCardGroupPK implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null)
                    return null;
                if (arg1 instanceof IzdCardGroupPK) {
                    PcdCardGroupPK pcdCardGroupPK = new PcdCardGroupPK();
                    pcdCardGroupPK.setBankC(((IzdCardGroupPK) arg1).getBankC());
                    pcdCardGroupPK.setGroupc(((IzdCardGroupPK) arg1).getGroupc());
                    return pcdCardGroupPK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdClientPK");
                }
            }
        }

        public IzdCardGroupToPcdCardGroupConverter() {
            super();
            ConvertUtils.register(new IzdCardGroupPKToPcdCardGroupPK(), PcdCardGroupPK.class);
        }

        public Class<?> getSourceClass() {
            return IzdCardGroup.class;
        }

        public Class<?> getDestinationClass() {
            return PcdCardGroup.class;
        }
    }

    public static class IzdClientToPcdClientConverter extends AbstractSpecialConverter {
        public class IzdClientPKToPcdClientPK implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null)
                    return null;
                if (arg1 instanceof IzdClientPK) {
                    PcdClientPK pcdClient = new PcdClientPK();
                    pcdClient.setBankC(((IzdClientPK) arg1).getBankC());
                    pcdClient.setClient(((IzdClientPK) arg1).getClient());
                    return pcdClient;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdClientPK");
                }
            }
        }

        public IzdClientToPcdClientConverter() {
            super();
            ConvertUtils.register(new IzdClientPKToPcdClientPK(), PcdClientPK.class);
        }

        public Serializable convert(Object s) {
            Object tmpO = super.convert(s);
            log.info("Received object:" + tmpO.getClass());
            PcdClient o = (PcdClient) tmpO;
            IzdClient i = (IzdClient) s;
            o.setFirstNames(i.getFNames());
            o.setLastName(i.getSurname());
            return o;
        }

        public Class<?> getSourceClass() {
            return IzdClient.class;
        }

        public Class<?> getDestinationClass() {
            return PcdClient.class;
        }
    }

    public static class IzdDbOwnerToPcdDbOwnerConverter extends AbstractSpecialConverter {
        public Class<?> getSourceClass() {
            return IzdDbOwner.class;
        }

        public Class<?> getDestinationClass() {
            return PcdDbOwner.class;
        }
    }

    public static class IzdStopCauseToPcdStopCauseConverter extends AbstractSpecialConverter {
        public Class<?> getSourceClass() {
            return IzdStopCause.class;
        }

        public Class<?> getDestinationClass() {
            return PcdStopCause.class;
        }

        public static class IzdStopCausePKToPcdStopCausePK implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null)
                    return null;
                if (arg1 instanceof IzdStopCausePK) {
                    PcdStopCausePK pcdStopCausePK = new PcdStopCausePK();
                    pcdStopCausePK.setBankC(((IzdStopCausePK) arg1).getBankC());
                    pcdStopCausePK.setCause(((IzdStopCausePK) arg1).getCause());
                    return pcdStopCausePK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdStopCausePK");
                }
            }
        }

        public IzdStopCauseToPcdStopCauseConverter() {
            super();
            ConvertUtils.register(new IzdStopCausePKToPcdStopCausePK(), PcdStopCausePK.class);
        }
    }

    public static class IzdCompanyToPcdCompanyConverter extends AbstractSpecialConverter {

        static class IzdCompanyPKToPcdCompanyPKConverter implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null)
                    return null;
                if (arg1 instanceof IzdCompanyPK) {
                    PcdCompanyPK pcdCompanyPK = new PcdCompanyPK();
                    pcdCompanyPK.setBankC(((IzdCompanyPK) arg1).getBankC());
                    pcdCompanyPK.setCode(((IzdCompanyPK) arg1).getCode());
                    return pcdCompanyPK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdCompanyPK");
                }
            }
        }

        public IzdCompanyToPcdCompanyConverter() {
            super();
            ConvertUtils.register(new IzdCompanyPKToPcdCompanyPKConverter(), PcdCompanyPK.class);
        }

        public Class<?> getSourceClass() {
            return IzdCompany.class;
        }

        public Class<?> getDestinationClass() {
            return PcdCompany.class;
        }
    }

    public class PcdAccountsUpdateHandler extends AbstractSpecialUpdateHandler {

        public void beforeInsertFilter(Object o) {
            linkAppDao.deletePcdCardsAccountByAccount(((PcdAccount) o).getAccountNo());
            log.info("Trying to prepare pcdCardsAccount [linkAppDao.deletePcdCardsAccountByAccount], accountNo = " + ((PcdAccount) o).getAccountNo());
        }
    }

    public static class PcdClientUpdateHandler extends AbstractSpecialUpdateHandler {

        public void beforeUpdateFilter(Object oldO, Object newO) {
            PcdClient saved = (PcdClient) oldO;
            PcdClient updated = (PcdClient) newO;

            if (objectWasChanged(saved.getClientB(), updated.getClientB())) {
                log.info("clientB changed");
            }
        }
    }

    public static class PcdAccParamUpdateHandler extends AbstractSpecialUpdateHandler {

        public void beforeUpdateFilter(Object oldO, Object newO) {
            PcdAccParam saved = (PcdAccParam) oldO;
            PcdAccParam updated = (PcdAccParam) newO;

            if (objectWasChanged(saved.getUfield5(), updated.getUfield5())) {
                log.info("AccountNoB changed");
            }
            if (objectWasChanged(Long.toString(saved.getCrd()), Long.toString(updated.getCrd()))) {
                log.info("Crd changed");
            }
            if (objectWasChanged(saved.getStatus(), updated.getStatus())) {
                log.info("Status changed");
            }
        }
    }

    public class IzdBranchToPcdBranchConverter extends AbstractSpecialConverter {

        public Serializable convert(Object s) {
            PcdBranch o = (PcdBranch) super.convert(s);
            IzdBranch i = (IzdBranch) (s);
            NordlbBranch nb = cmsDao.getIzdNordlbBranchByCard(i.getComp_id().getBankCode(), i.getComp_id().getBranch());
            if (nb != null) {
                o.setExternalId(nb.getExternalId());
            }
            return o;
        }

        class IzdBranchPKToPcdBranchPKConverter implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null) return null;
                if (arg1 instanceof IzdBranchPK) {
                    PcdBranchPK pcdBranchPK = new PcdBranchPK();
                    pcdBranchPK.setBankC(((IzdBranchPK) arg1).getBankCode());
                    pcdBranchPK.setBranch(((IzdBranchPK) arg1).getBranch());
                    return pcdBranchPK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdBranchPK");
                }
            }
        }

        public IzdBranchToPcdBranchConverter() {
            super();
            ConvertUtils.register(new IzdBranchPKToPcdBranchPKConverter(), PcdBranchPK.class);
        }

        public Class<?> getSourceClass() {
            return IzdBranch.class;
        }

        public Class<?> getDestinationClass() {
            return PcdBranch.class;
        }
    }

    protected static String dateToString(Date date) {
        return date == null ? "null" : DATE_FORMAT.format(date);
    }

    public static boolean objectWasChanged(Object o, Object n) {
        log.info("COMPARING [" + o + "] and [" + n + "]");
        if ((o != null) && (!o.equals(n))) {
            return true;
        } else if ((n != null) && (!n.equals(o))) {
            return true;
        }
        return false;
    }

    public static boolean objectWasChanged(Date o, Date n) {
        log.info("COMPARING DATE [" + dateToString(o) + "] and [" + dateToString(n) + "]");

        if ((o == null && n != null) ||
                (o != null && n == null))
            return true;
        else if (o != null && n != null) {
            Calendar co = Calendar.getInstance();
            co.setTime(o);
            Calendar cn = Calendar.getInstance();
            cn.setTime(n);
            return co.get(Calendar.YEAR) != cn.get(Calendar.YEAR) ||
                    co.get(Calendar.MONTH) != cn.get(Calendar.MONTH) ||
                    co.get(Calendar.DAY_OF_MONTH) != cn.get(Calendar.DAY_OF_MONTH);
        }
        return false;
    }

}

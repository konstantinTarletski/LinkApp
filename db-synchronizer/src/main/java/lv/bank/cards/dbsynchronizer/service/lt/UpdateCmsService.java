package lv.bank.cards.dbsynchronizer.service.lt;

import lv.bank.cards.core.entity.cms.DnbIzdAccntChng;
import lv.bank.cards.core.entity.cms.IzdAccParam;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdAgreement;
import lv.bank.cards.core.entity.cms.IzdBinCardDesigns;
import lv.bank.cards.core.entity.cms.IzdBinCardDesignsPK;
import lv.bank.cards.core.entity.cms.IzdBinTable;
import lv.bank.cards.core.entity.cms.IzdBranch;
import lv.bank.cards.core.entity.cms.IzdBranchPK;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCardDesigns;
import lv.bank.cards.core.entity.cms.IzdCardDesignsPK;
import lv.bank.cards.core.entity.cms.IzdCardGroup;
import lv.bank.cards.core.entity.cms.IzdCardType;
import lv.bank.cards.core.entity.cms.IzdCardsAddFields;
import lv.bank.cards.core.entity.cms.IzdCardsPinBlocks;
import lv.bank.cards.core.entity.cms.IzdCcyTable;
import lv.bank.cards.core.entity.cms.IzdClAcct;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.entity.cms.IzdCompany;
import lv.bank.cards.core.entity.cms.IzdCondCard;
import lv.bank.cards.core.entity.cms.IzdDbOwner;
import lv.bank.cards.core.entity.cms.IzdRepDistribut;
import lv.bank.cards.core.entity.cms.IzdRepLang;
import lv.bank.cards.core.entity.cms.IzdStopCause;
import lv.bank.cards.core.entity.linkApp.PcdAccParam;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdAgreement;
import lv.bank.cards.core.entity.linkApp.PcdBin;
import lv.bank.cards.core.entity.linkApp.PcdBinCardDesigns;
import lv.bank.cards.core.entity.linkApp.PcdBinCardDesignsPK;
import lv.bank.cards.core.entity.linkApp.PcdBranch;
import lv.bank.cards.core.entity.linkApp.PcdBranchPK;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardDesigns;
import lv.bank.cards.core.entity.linkApp.PcdCardDesignsPK;
import lv.bank.cards.core.entity.linkApp.PcdCardGroup;
import lv.bank.cards.core.entity.linkApp.PcdCardType;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.entity.linkApp.PcdCompany;
import lv.bank.cards.core.entity.linkApp.PcdCondCard;
import lv.bank.cards.core.entity.linkApp.PcdCurrency;
import lv.bank.cards.core.entity.linkApp.PcdDbOwner;
import lv.bank.cards.core.entity.linkApp.PcdProcIds;
import lv.bank.cards.core.entity.linkApp.PcdRepDistribut;
import lv.bank.cards.core.entity.linkApp.PcdRepLang;
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
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LT_PROFILE;

@Profile(COUNTRY_LT_PROFILE)
@Service
public class UpdateCmsService extends UpdateCmsServiceBase {

    private static final Logger log = Logger.getLogger(UpdateCmsService.class);

    public UpdateCmsService(CmsDao cmsDao, LinkAppDao linkAppDao,
                            ApplicationProperties appProperties,
                            PcdLastUpdatedRepository pcdLastUpdatedRepository) {
        super(cmsDao, linkAppDao, appProperties, pcdLastUpdatedRepository);
        super.handlers = getAvailableHandlers();
        Converter.defConverter(new IzdBranchToPcdBranchConverter());
        Converter.defConverter(new IzdAccountToPcdAccountConverter());
        Converter.defConverter(new IzdCardToPcdCardConverter());
        Converter.defConverter(new IzdCardDesignsToPcdCardDesignsConverter());
        Converter.defConverter(new IzdBinCardDesignsToPcdBinCardDesignsConverter());
        // This is necessary to get null's for attributes
        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
    }

    protected void update(Date prevUpdWaterMark, Date curWaterMark) throws NoSuchMethodException {

        update(IzdDbOwner.class, PcdDbOwner.class, PcdDbOwner.class.getMethod("getBankC"),
                prevUpdWaterMark, curWaterMark);
        update(IzdCardDesigns.class, PcdCardDesigns.class, PcdCardDesigns.class.getMethod("getComp_id"),
                prevUpdWaterMark, curWaterMark);
        update(IzdStopCause.class, PcdStopCause.class, PcdStopCause.class.getMethod("getComp_id"),
                prevUpdWaterMark, curWaterMark);
        update(IzdCardType.class, PcdCardType.class, PcdCardType.class.getMethod("getComp_id"),
                prevUpdWaterMark, curWaterMark);
        update(IzdCardGroup.class, PcdCardGroup.class, PcdCardGroup.class.getMethod("getComp_id"),
                prevUpdWaterMark, curWaterMark);
        update(IzdBinTable.class, PcdBin.class, PcdBin.class.getMethod("getComp_id"),
                prevUpdWaterMark, curWaterMark);
        update(IzdBinCardDesigns.class, PcdBinCardDesigns.class, PcdBinCardDesigns.class.getMethod("getComp_id"),
                prevUpdWaterMark, curWaterMark);
        update(IzdRepLang.class, PcdRepLang.class, PcdRepLang.class.getMethod("getComp_id"),
                prevUpdWaterMark, curWaterMark);
        update(IzdRepDistribut.class, PcdRepDistribut.class, PcdRepDistribut.class.getMethod("getComp_id"),
                prevUpdWaterMark, curWaterMark);
        update(IzdClient.class, PcdClient.class, PcdClient.class.getMethod("getComp_id"),
                prevUpdWaterMark, curWaterMark);
        update(IzdBranch.class, PcdBranch.class, PcdBranch.class.getMethod("getComp_id"),
                prevUpdWaterMark, curWaterMark);
        update(IzdCompany.class, PcdCompany.class, PcdCompany.class.getMethod("getComp_id"),
                prevUpdWaterMark, curWaterMark);
        update(IzdCcyTable.class, PcdCurrency.class, PcdCurrency.class.getMethod("getIsoAlpha"),
                prevUpdWaterMark, curWaterMark);
        update(IzdAgreement.class, PcdAgreement.class, PcdAgreement.class.getMethod("getAgreement"),
                prevUpdWaterMark, curWaterMark);
        update(IzdAccount.class, PcdAccount.class, PcdAccount.class.getMethod("getAccountNo"), prevUpdWaterMark, curWaterMark);
        update(IzdAccParam.class, PcdAccParam.class, PcdAccParam.class.getMethod("getAccountNo"),
                prevUpdWaterMark, curWaterMark);
        update(IzdCondCard.class, PcdCondCard.class, PcdCondCard.class.getMethod("getComp_id"),
                prevUpdWaterMark, curWaterMark);
        update(IzdCard.class, PcdCard.class, PcdCard.class.getMethod("getCard"),
                prevUpdWaterMark, curWaterMark);
        update(DnbIzdAccntChng.class, PcdProcIds.class, PcdProcIds.class.getMethod("getComp_id"),
                null, null);
    }

    @Override
    protected void blockPPCards() {
        log.info("Nothing to do in blockPPCards in LT");
    }

    @Override
    protected Map<String, AbstractSpecialUpdateHandler> getAvailableHandlers() {
        Map<String, AbstractSpecialUpdateHandler> handlers = new HashMap<>();
        // This will delete just created records from PcdCardAccounts
        handlers.put(PcdCard.class.getName(), new PcdCardsUpdateHandler());
        handlers.put(PcdAccount.class.getName(), new PcdAccountsUpdateHandler());
        handlers.put(PcdClient.class.getName(), new PcdClientUpdateHandler());
        handlers.put(PcdAccParam.class.getName(), new PcdAccParamUpdateHandler());
        return handlers;
    }

    public static class IzdBinCardDesignsToPcdBinCardDesignsConverter extends AbstractSpecialConverter {

        public IzdBinCardDesignsToPcdBinCardDesignsConverter() {
            super();
            ConvertUtils.register(new IzdBinCardDesignsPKToPcdBinCardDesignsPK(), PcdBinCardDesignsPK.class);
        }

        public Class<?> getSourceClass() {
            return IzdBinCardDesigns.class;
        }

        public Class<?> getDestinationClass() {
            return PcdBinCardDesigns.class;
        }

        public class IzdBinCardDesignsPKToPcdBinCardDesignsPK implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null)
                    return null;
                if (arg1 instanceof IzdBinCardDesignsPK) {
                    PcdBinCardDesignsPK pcdBinCardDesignsPK = new PcdBinCardDesignsPK();
                    pcdBinCardDesignsPK.setBinCode(((IzdBinCardDesignsPK) arg1).getBinCode());
                    pcdBinCardDesignsPK.setDesignId(((IzdBinCardDesignsPK) arg1).getDesignId());
                    pcdBinCardDesignsPK.setGroupc(((IzdBinCardDesignsPK) arg1).getGroupc());
                    pcdBinCardDesignsPK.setBankC(((IzdBinCardDesignsPK) arg1).getBankC());
                    return pcdBinCardDesignsPK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdCardDesignsPK");
                }
            }
        }

    }

    public static class IzdCardDesignsToPcdCardDesignsConverter extends AbstractSpecialConverter {

        public IzdCardDesignsToPcdCardDesignsConverter() {
            super();
            ConvertUtils.register(new IzdCardDesignsPKToPcdCardDesignsPK(), PcdCardDesignsPK.class);
        }

        public Class<?> getSourceClass() {
            return IzdCardDesigns.class;
        }

        public Class<?> getDestinationClass() {
            return PcdCardDesigns.class;
        }

        public static class IzdCardDesignsPKToPcdCardDesignsPK implements org.apache.commons.beanutils.Converter {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null)
                    return null;
                if (arg1 instanceof IzdCardDesignsPK) {
                    PcdCardDesignsPK pcdCardDesignsPK = new PcdCardDesignsPK();
                    pcdCardDesignsPK.setDesignId(((IzdCardDesignsPK) arg1).getDesignId());
                    pcdCardDesignsPK.setGroupc(((IzdCardDesignsPK) arg1).getGroupc());
                    pcdCardDesignsPK.setBankC(((IzdCardDesignsPK) arg1).getBankC());
                    return pcdCardDesignsPK;
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdCardDesignsPK");
                }
            }
        }

    }

    public class IzdCardToPcdCardConverter extends AbstractSpecialConverter {

        public IzdCardToPcdCardConverter() {
            super();
            ConvertUtils.register(new BigDecimalConverter(null), java.math.BigDecimal.class);
        }

        public Serializable convert(Object s) {
            PcdCard o = (PcdCard) super.convert(s);
            IzdCard i = (IzdCard) (s);

            log.debug("IzdCardToPcdCardConverter.convert, PcdCard = {}" + o);
            log.debug("IzdCardToPcdCardConverter.convert, IzdCard = {}" + i);

            if (i.getUCod10() == null) {
                o.setUCod10(i.getIzdAgreement().getIzdBranch().getComp_id().getBranch());
            }
            o.setGroupc(i.getIzdCardType().getComp_id().getGroupc());
            o.setBankC(i.getIzdCardType().getComp_id().getBankC());
            o.setAgreement(i.getIzdAgreement().getAgreNom());

            PcdAccount acc = new PcdAccount();
            acc.setAccountNo(i.getIzdClAcct().getIzdAccount().getAccountNo());
            acc.setCardAcct(i.getIzdClAcct().getIzdAccount().getCardAcct());
            Set<PcdAccount> pcdAccounts = new HashSet<>();
            pcdAccounts.add(acc);
            o.setPcdAccounts(pcdAccounts);

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
            o.setCtime(new Date());
            return o;
        }

        public Class<?> getSourceClass() {
            return IzdCard.class;
        }

        public Class<?> getDestinationClass() {
            return PcdCard.class;
        }
    }

    public class IzdAccountToPcdAccountConverter extends AbstractSpecialConverter {

        public Serializable convert(Object s) {
            PcdAccount o = (PcdAccount) super.convert(s);
            IzdAccount i = (IzdAccount) (s);

            log.debug("IzdAccountToPcdAccountConverter.convert, PcdAccount = {}" + o);
            log.debug("IzdAccountToPcdAccountConverter.convert, IzdAccount = {}" + i);

            List<PcdAccount> l = linkAppDao.getPcdAccountListByAccountNo(i.getAccountNo());
            if (l.size() == 1) {
                PcdAccount tmp = l.iterator().next();
                o.setIban(tmp.getIban());
            }

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
    }

    public class PcdCardsUpdateHandler extends AbstractSpecialUpdateHandler {

        DateFormat formatter = new SimpleDateFormat("yyMM");

        public void beforeInsertFilter(Object o) {
            linkAppDao.deletePcdCardsAccountByCard(((PcdCard) o).getCard());
            log.info("Trying to prepare pcdCardsAccount [linkAppDao.deletePcdCardsAccountByCard], card = " + ((PcdCard) o).getCard());
            if (((PcdCard) o).getUAField4() != null) {
                log.info("New migrated card: skipping delivery block setup");
                log.info("Setting delivery_block flag to final");
                ((PcdCard) o).setDeliveryBlock("6");
            } else {
                log.info("Setting delivery_block flag");
                // For new card delivery block is null
                ((PcdCard) o).setDeliveryBlock(null);

                log.info("New card: setting up delivery block");
                wsClient.addCardToRMS(((PcdCard) o).getCard(),
                        formatter.format(((PcdCard) o).getExpiry2() == null ? ((PcdCard) o)
                                .getExpiry1() : ((PcdCard) o).getExpiry2()),
                        CardUtils.composeCentreIdFromPcdCard((PcdCard) o));
            }
            log.info("New card: setting up E-commerce block");
            wsClient.addElectronicCommerceBlock(((PcdCard) o).getCard());
        }

        public void beforeUpdateFilter(Object oldO, Object newO) {
            PcdCard saved = (PcdCard) oldO;
            PcdCard updated = (PcdCard) newO;
            log.debug("Transferring PCD-only fields (attributes) to updated card... ");
            updated.setDeliveryBlock(saved.getDeliveryBlock());
            updated.setIssuedBy(saved.getIssuedBy());
            updated.setIssueDate(saved.getIssueDate());
            updated.setOperator(saved.getOperator());
            updated.setCreationBranch(saved.getCreationBranch());
            updated.setMaxima(saved.getMaxima());
            updated.setMediator(saved.getMediator());
            if (updated.getRenew() == null || !updated.getRenew().equals(saved.getRenew())) { // Update renew old status only if renew status is changed
                log.info("renewOld card " + saved.getCard() + " change from " + saved.getRenewOld() + " to " + saved.getNextRenewOld() + " and renew " + updated.getRenew());
                updated.setRenewOld(saved.getNextRenewOld());
            }

            if (objectWasChanged(saved.getExpiry2(), updated.getExpiry2())
                    && updated.getExpiry2() != null
                    && !updated.getExpiry1().equals(updated.getExpiry2())) {
                log.info("Expiry changed: set delivery_block flag and add to RMS stoplist");

                // Set delivery block to null
                updated.setDeliveryBlock(null);

                log.info("Expiry changed: add to RMS stoplist");
                wsClient.addCardToRMS(updated.getCard(), formatter.format(updated.getExpiry2()),
                        CardUtils.composeCentreIdFromPcdCard(updated));
            }
            // Contactless status
            BigDecimal contactless = cmsDao.getContactlessByCard(saved.getCard());
            if ((contactless != null && !contactless.equals(saved.getContactless())) ||
                    (contactless == null && saved.getContactless() != null)) {
                log.info("Change contactless from " + saved.getContactless() + " to " + contactless + " for " + saved.getCard());
                updated.setContactless(contactless == null ? null : contactless.intValue());
            }
        }

        @Override
        public void afterInsertFilter(Object s) {
            PcdCard tmpCard = (PcdCard) s;
            // Contactless status
            BigDecimal contactless = cmsDao.getContactlessByCard(tmpCard.getCard());
            linkAppDao.updatePcdCardContactless(tmpCard.getCard(), contactless == null ? null : contactless.intValue());
        }
    }

}

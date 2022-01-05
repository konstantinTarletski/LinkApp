package lv.bank.cards.dbsynchronizer.service.lv;

import lv.bank.cards.dbsynchronizer.config.ApplicationProperties;
import lv.bank.cards.dbsynchronizer.config.lv.ApplicationPropertiesLV;
import lv.bank.cards.dbsynchronizer.dao.LinkAppDao;
import lv.bank.cards.dbsynchronizer.service.CleanDbServiceBase;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LV_PROFILE;

@Profile(COUNTRY_LV_PROFILE)
@Service
public class CleanDbService extends CleanDbServiceBase {

    private static final Logger log = Logger.getLogger(CleanDbServiceBase.class);

    protected final ApplicationPropertiesLV applicationPropertiesLV;

    public CleanDbService(LinkAppDao linkAppDao,
                          ApplicationProperties applicationProperties,
                          ApplicationPropertiesLV applicationPropertiesLV) {
        super(linkAppDao, applicationProperties);
        this.applicationPropertiesLV = applicationPropertiesLV;
    }

    @Override
    protected void cleanCountrySpecificDB() {
        long startTime = System.currentTimeMillis();
        log.info("cleanCountrySpecificDB - START");
        log.info("cleanCountrySpecificDB - cleanPcdAuthBatches");
        linkAppDao.cleanPcdAuthBatches(applicationPropertiesLV.getPcdAuthBatchesCleanPeriodDays());
        log.info("cleanCountrySpecificDB - cleanPcdAuthPosIsoHostMessages");
        linkAppDao.cleanPcdAuthPosIsoHostMessages(applicationPropertiesLV.getPcdAuthPosIsoHostMessagesCleanPeriodDays());
        log.info("cleanCountrySpecificDB - cleanPcdAtmAdverts");
        linkAppDao.cleanPcdAtmAdverts(applicationPropertiesLV.getPcdAtmAdvertsCleanPeriodDays());
        log.info("cleanCountrySpecificDB - cleanSmsReconciliation");
        linkAppDao.cleanSmsReconciliation(applicationPropertiesLV.getSmsReconciliationCleanPeriodDays());
        long totalTime = (System.currentTimeMillis() - startTime)/1000;
        log.info("cleanCountrySpecificDB - END, time of execution is = " + totalTime + " seconds");
    }

}

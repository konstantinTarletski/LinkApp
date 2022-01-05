package lv.bank.cards.dbsynchronizer.service.lt;

import lv.bank.cards.dbsynchronizer.config.ApplicationProperties;
import lv.bank.cards.dbsynchronizer.config.lt.ApplicationPropertiesLT;
import lv.bank.cards.dbsynchronizer.dao.LinkAppDao;
import lv.bank.cards.dbsynchronizer.service.CleanDbServiceBase;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LT_PROFILE;

@Profile(COUNTRY_LT_PROFILE)
@Service
public class CleanDbService extends CleanDbServiceBase {

    private static final Logger log = Logger.getLogger(CleanDbServiceBase.class);

    protected final ApplicationPropertiesLT applicationPropertiesLT;

    public CleanDbService(LinkAppDao linkAppDao,
                          ApplicationProperties applicationProperties,
                          ApplicationPropertiesLT applicationPropertiesLT) {
        super(linkAppDao, applicationProperties);
        this.applicationPropertiesLT = applicationPropertiesLT;
    }

    @Override
    protected void cleanCountrySpecificDB() {
        long startTime = System.currentTimeMillis();
        log.info("cleanCountrySpecificDB - START");
        log.info("cleanCountrySpecificDB - NOTHING TO CLEAN SPECIAL FOR LT");
        long totalTime = (System.currentTimeMillis() - startTime)/1000;
        log.info("cleanCountrySpecificDB - END, time of execution is = " + totalTime + " seconds");
    }
}

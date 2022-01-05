package lv.bank.cards.dbsynchronizer.service;

import lombok.RequiredArgsConstructor;
import lv.bank.cards.dbsynchronizer.config.ApplicationProperties;
import lv.bank.cards.dbsynchronizer.dao.LinkAppDao;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public abstract class CleanDbServiceBase {

    private static final Logger log = Logger.getLogger(CleanDbServiceBase.class);

    protected final LinkAppDao linkAppDao;
    protected final ApplicationProperties applicationProperties;

    protected abstract void cleanCountrySpecificDB();

    public void cleanDB(){
        cleanCountrySpecificDB();
        long startTime = System.currentTimeMillis();
        log.info("cleanDB - START");
        log.info("cleanDB - cleanPcdCardsPendingTokenization");
        linkAppDao.cleanPcdCardsPendingTokenization(applicationProperties.getPcdCardspendingTokenizationTokenCleanPeriodDays());
        log.info("cleanDB - cleanPcdCardsPendingTokenizationByTokenRefId");
        linkAppDao.cleanPcdCardsPendingTokenizationByTokenRefId(
                applicationProperties.getPcdCardspendingTokenizationTokenRefIdCleanPeriodMinutes()
        );
        long totalTime = (System.currentTimeMillis() - startTime)/1000;
        log.info("cleanDB - END, time of execution is = " + totalTime + " seconds");
    }

}

package lv.bank.cards.dbsynchronizer.service;

import lombok.RequiredArgsConstructor;
import lv.bank.cards.core.entity.linkApp.PcdLastUpdated;
import lv.bank.cards.dbsynchronizer.config.ApplicationProperties;
import lv.bank.cards.dbsynchronizer.dao.CmsDao;
import lv.bank.cards.dbsynchronizer.dao.LinkAppDao;
import lv.bank.cards.dbsynchronizer.jpa.cms.DnbIzdAccntChngRepository;
import lv.bank.cards.dbsynchronizer.jpa.pcd.PcdLastUpdatedRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UpdateService {

    private static final Logger log = Logger.getLogger(UpdateService.class);

    protected final UpdateCmsServiceBase updateCmsService;
    protected final UpdateRtpsService updateRtpsService;
    protected final CmsDao cmsDao;
    protected final LinkAppDao linkAppDao;
    protected final ApplicationProperties appProperties;
    protected final DnbIzdAccntChngRepository dnbIzdAccntChngRepository;
    protected final PcdLastUpdatedRepository pcdLastUpdatedRepository;

    public void updateAllDb() throws NoSuchMethodException {
        long startTime = System.currentTimeMillis();
        log.info("updateAllDb START");

        Date prevUpdWaterMark = pcdLastUpdatedRepository
                .findById(appProperties.getNordlbBankc())
                .map(PcdLastUpdated::getCdate)
                .orElse(null);

        log.info("Last water mark was " + prevUpdWaterMark);

        Date curWaterMark = cmsDao.getCurrentWaterMark();
        log.info("Current water mark is " + curWaterMark);

        if (!curWaterMark.after(prevUpdWaterMark)) {
            log.warn("WARNING: Very long unfinished transaction detected");
        }

        dnbIzdAccntChngRepository.markNewProcIdsFromBO();

        updateCmsService.updateDb(prevUpdWaterMark, curWaterMark);
        updateRtpsService.updateDb(prevUpdWaterMark, curWaterMark);

        pcdLastUpdatedRepository.setLastUpdateTime(appProperties.getNordlbBankc(), curWaterMark);

        dnbIzdAccntChngRepository.deleteMarkedProcIdsFromBO();

        long totalTime = (System.currentTimeMillis() - startTime)/1000;
        log.info("updateAllDb - END, time of execution is = " + totalTime + " seconds");
        log.info("OK");
    }

}

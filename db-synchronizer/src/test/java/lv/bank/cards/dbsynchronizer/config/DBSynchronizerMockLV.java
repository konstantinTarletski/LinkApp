package lv.bank.cards.dbsynchronizer.config;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.dbsynchronizer.dao.CmsDao;
import lv.bank.cards.dbsynchronizer.jpa.cms.DnbIzdAccntChngRepository;
import lv.bank.cards.dbsynchronizer.dao.LinkAppDao;
import lv.bank.cards.dbsynchronizer.jpa.pcd.PcdLastUpdatedRepository;
import lv.bank.cards.dbsynchronizer.service.CleanDbServiceBase;
import lv.bank.cards.dbsynchronizer.service.CommandLineRunnerService;
import lv.bank.cards.dbsynchronizer.service.UpdateCmsServiceBase;
import lv.bank.cards.dbsynchronizer.service.UpdateRtpsService;
import lv.bank.cards.dbsynchronizer.service.UpdateService;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LV_TEST_PROFILE;

@Slf4j
@Primary
@Service
@Profile(COUNTRY_LV_TEST_PROFILE)
public class DBSynchronizerMockLV extends CommandLineRunnerService {


    public DBSynchronizerMockLV(CleanDbServiceBase cleanDbService,
                                UpdateService updateService) {
        super(cleanDbService, updateService);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("DBSynchronizerMockLV started");
    }


}

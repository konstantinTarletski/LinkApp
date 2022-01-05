package lv.bank.cards.dbsynchronizer.config;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.dbsynchronizer.service.CleanDbServiceBase;
import lv.bank.cards.dbsynchronizer.service.CommandLineRunnerService;
import lv.bank.cards.dbsynchronizer.service.UpdateService;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LT_TEST_PROFILE;

@Slf4j
@Primary
@Service
@Profile(COUNTRY_LT_TEST_PROFILE)
public class DBSynchronizerMockLT extends CommandLineRunnerService {


    public DBSynchronizerMockLT(CleanDbServiceBase cleanDbService,
                                UpdateService updateService) {
        super(cleanDbService, updateService);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("DBSynchronizerMockLT Started");
        //NOT RUN default flow
    }

}

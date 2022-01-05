package lv.bank.cards.dbsynchronizer.service;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandLineRunnerService implements CommandLineRunner {

    private static final Logger log = Logger.getLogger(UpdateService.class);

    protected final CleanDbServiceBase cleanDbService;
    protected final UpdateService updateService;


    @Override
    public void run(String... args) throws Exception {
        log.info("run - application START");
        updateService.updateAllDb();
        cleanDbService.cleanDB();
        log.info("run - application END");
    }

}

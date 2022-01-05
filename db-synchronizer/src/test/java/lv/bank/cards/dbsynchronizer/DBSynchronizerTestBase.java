package lv.bank.cards.dbsynchronizer;

import lv.bank.cards.dbsynchronizer.service.UpdateService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DBSynchronizerTestBase {

    @Autowired
    protected SessionFactory cmsSessionFactory;

    @Autowired
    protected SessionFactory pcdSessionFactory;

    @Autowired
    protected SessionFactory rtpsSessionFactory;

    @Autowired
    protected UpdateService updateService;

    @Autowired
    protected CustomQueriesBase customQueries;

}

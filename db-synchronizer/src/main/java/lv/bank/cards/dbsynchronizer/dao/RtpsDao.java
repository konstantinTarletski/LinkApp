package lv.bank.cards.dbsynchronizer.dao;

import lv.bank.cards.dbsynchronizer.CustomQueriesBase;
import lv.bank.cards.dbsynchronizer.config.ApplicationProperties;
import lv.bank.cards.dbsynchronizer.utils.DataAndStatelessSessionHolder;
import org.hibernate.ScrollableResults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional("rtpsHibernateTransactionManager")
@Repository
public class RtpsDao extends SourceDaoBase {

    protected final LocalSessionFactoryBean rtpsSessionFactory;

    public RtpsDao(@Qualifier("rtpsSessionFactory") LocalSessionFactoryBean rtpsSessionFactory, ApplicationProperties appProperties, CustomQueriesBase customQueries) {
        super(rtpsSessionFactory, appProperties, customQueries);
        this.rtpsSessionFactory = rtpsSessionFactory;
    }

    public DataAndStatelessSessionHolder<ScrollableResults> findDataToUpdate(Class<?> s, Class<?> d, Date lastUpdate, Date currentLevel) {
        return super.findDataToUpdate(s, d, lastUpdate, currentLevel);
    }


}

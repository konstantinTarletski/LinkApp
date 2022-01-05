package lv.bank.cards.dbsynchronizer.dao;

import lombok.RequiredArgsConstructor;
import lv.bank.cards.core.entity.rtps.StipParamList;
import lv.bank.cards.core.entity.rtps.StipParamN;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.dbsynchronizer.CustomQueriesBase;
import lv.bank.cards.dbsynchronizer.config.ApplicationProperties;
import lv.bank.cards.dbsynchronizer.utils.DataAndStatelessSessionHolder;
import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.Date;

@RequiredArgsConstructor
public abstract class SourceDaoBase {

    private static Logger log = Logger.getLogger(SourceDaoBase.class);

    protected final LocalSessionFactoryBean sessionFactory;
    protected final ApplicationProperties appProperties;
    protected final CustomQueriesBase customQueries;

    public Session getSession() {
        return sessionFactory.getObject().getCurrentSession();
    }

    public DataAndStatelessSessionHolder<ScrollableResults> findDataToUpdate(Class<?> s, Class<?> d, Date lastUpdate, Date currentLevel) {

        String centreId = CardUtils.composeCentreId(appProperties.getBankc(), appProperties.getGroupc());
        StatelessSession statelessSession = sessionFactory.getObject().openStatelessSession();

        Query q;

        if (customQueries.getCustomQueries().containsKey(s.getName())) {
            q = statelessSession.createQuery(customQueries.getCustomQueries().get(s.getName()));
        } else {
            String slqName = "get.updated.items." + s.getName() + ".to." + d.getName();
            q = statelessSession.getNamedQuery(slqName);
        }

        if (s.getName().equalsIgnoreCase(StipParamN.class.getName()) ||
                s.getName().equalsIgnoreCase(StipParamList.class.getName())) {
            q.setString("centreId", centreId);
        }

        if (lastUpdate != null) {
            q.setTimestamp("lastWaterMark", lastUpdate);
            if (currentLevel != null) {
                q.setTimestamp("currentWaterMark", currentLevel);
            }
        }
        DataAndStatelessSessionHolder<ScrollableResults> data = new DataAndStatelessSessionHolder<>();
        data.setData(q.scroll(ScrollMode.FORWARD_ONLY));
        data.setStatelessSession(statelessSession);
        return data;
    }

}

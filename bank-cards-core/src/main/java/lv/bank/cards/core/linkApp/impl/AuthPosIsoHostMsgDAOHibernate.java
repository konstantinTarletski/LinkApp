/*
 * Created on 2006.5.4
 */
package lv.bank.cards.core.linkApp.impl;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.linkApp.dao.AuthPosIsoHostMsgDAO;
import lv.bank.cards.core.linkApp.dto.BDCCountAndAmount;
import lv.bank.cards.core.entity.linkApp.PcdAuthBatch;
import lv.bank.cards.core.entity.linkApp.PcdAuthPosIsoHostMessage;
import lv.bank.cards.core.entity.linkApp.PcdAuthSource;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class AuthPosIsoHostMsgDAOHibernate extends BaseDAOHibernate implements AuthPosIsoHostMsgDAO {

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, BDCCountAndAmount> getReconsiled(PcdAuthBatch batch) {
        HashMap<String, BDCCountAndAmount> retData = new HashMap<>();
        log.info("Asked to calculate bdc tuples for " + batch.getTerminalId() + " [" + batch.getBatchId() + "]");
        Query q = sf.getCurrentSession()
                .createQuery("select mti,count(*),sum(fld004) from PcdAuthPosIsoHostMessage where pcdAuthBatch=:batch group by mti");
        // only non reconsiled messages which were approved by RTPS and dated below watermark and for particular terminal

        q.setEntity("batch", batch);
        Iterator<?> i = q.iterate();

        while (i.hasNext()) {
            Object[] tuple = (Object[]) i.next();
            BDCCountAndAmount data = new BDCCountAndAmount();
            data.setCount((Long) tuple[1]);
            data.setSum((Long) tuple[2]);
            retData.put((String) tuple[0], data);
            log.info("BDC tuple: " + (String) tuple[0] + "=" + (Long) tuple[1] + "[sum:" + (Long) tuple[2] + "]");
            if (log.isDebugEnabled()) {
                Query qList = sf.getCurrentSession()
                        .createQuery("from PcdAuthPosIsoHostMessage where pcdAuthBatch=:batch and mti=:mti");
                // only non reconsiled messages which were approved by RTPS and dated below watermark and for particular terminal
                qList.setEntity("batch", batch);
                qList.setString("mti", (String) tuple[0]);
                for (Iterator<PcdAuthPosIsoHostMessage> iList = qList.iterate(); iList.hasNext(); ) {
                    PcdAuthPosIsoHostMessage msg = iList.next();
                    log.debug("Auth from this tuple :" + msg.getId());

                }

            }
//			else
//				log.info("Debug is not enabled");
        }
        return retData;
    }

    public PcdAuthBatch getNewBatchOpened(/*Date watermark, */String terminal, PcdAuthSource source) {
        log.info("Will create new batch");
        PcdAuthBatch batch = new PcdAuthBatch();
        batch.setPcdAuthSource(source);
        batch.setTerminalId(terminal);
        saveBatch(batch);
        log.info("Batch opened with id=[" + batch.getBatchId() + "]");
        Query q = sf.getCurrentSession()
                .createQuery("UPDATE PcdAuthPosIsoHostMessage set pcdAuthBatch=:batch where pcdAuthBatch is null and fld039 in ('000','400') and fld041=:terminal"/* and datetime < :watermark"*/);
        q.setEntity("batch", batch);
        /*q.setTimestamp("watermark", watermark);*/
        q.setString("terminal", terminal);
        int i = q.executeUpdate();
        log.info("There are " + i + " records in batch " + batch.getBatchId());
        return batch;
    }

    public PcdAuthBatch saveBatch(PcdAuthBatch batch) {
        sf.getCurrentSession().save(batch);
        sf.getCurrentSession().flush();
        return batch;
    }

    public PcdAuthBatch updateBatch(PcdAuthBatch batch) {
        sf.getCurrentSession().update(batch);
        sf.getCurrentSession().flush();
        return null;
    }

    public PcdAuthPosIsoHostMessage findPcdAuthPosIsoHostMessageById(long id) {
        return (PcdAuthPosIsoHostMessage) sf.getCurrentSession().get(PcdAuthPosIsoHostMessage.class, id);
    }

    public PcdAuthSource findPcdAuthSourceById(long id) {
        return (PcdAuthSource) sf.getCurrentSession().load(PcdAuthSource.class, id);
    }

    @SuppressWarnings("unchecked")
    public PcdAuthSource findPcdAuthSourceByName(String name) {
        Criteria c = sf.getCurrentSession().createCriteria(PcdAuthSource.class).add(Restrictions.eq("this.source", name));
        List<PcdAuthSource> l = c.setCacheable(true).setCacheRegion("SourceBySource").list();
        if (!l.isEmpty()) {
            return l.get(0);
        }
        return null;
    }

}

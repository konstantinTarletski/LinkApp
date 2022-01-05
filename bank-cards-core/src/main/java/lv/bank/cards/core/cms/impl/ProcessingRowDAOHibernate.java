package lv.bank.cards.core.cms.impl;

import lv.bank.cards.core.cms.dao.ProcessingRowDAO;
import lv.bank.cards.core.entity.cms.IzdBatches;
import lv.bank.cards.core.entity.cms.IzdPreProcessingRow;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ProcessingRowDAOHibernate extends BaseDAOHibernate implements ProcessingRowDAO {

    @Override
    public IzdPreProcessingRow findBySlip(String slip) {
        Iterator<?> it = sf.getCurrentSession().createCriteria(IzdPreProcessingRow.class).add(Restrictions.eq("slipNr", slip)).list().iterator();
        if (it.hasNext()) return (IzdPreProcessingRow) it.next();
        else return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IzdPreProcessingRow> findByCard(String card) {
        Date lastUpd = ((IzdBatches) sf.getCurrentSession().createCriteria(IzdBatches.class).addOrder(Order.desc("endTime")).setMaxResults(1).list().get(0)).getEndTime();
//		Date lastUpd = (Date) sf.getCurrentSession().createSQLQuery("select max(izd_batches.end_time) from izd_batches").list().iterator().next();
        return (List<IzdPreProcessingRow>) sf.getCurrentSession().createCriteria(IzdPreProcessingRow.class)
                .add(Restrictions.eq("card", card))
                .add(Restrictions.in("trType", Arrays.asList("325", "326", "32A", "32B", "32D", "32H", "32S", "32T")))
                .add(Restrictions.lt("regDate", lastUpd))
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IzdPreProcessingRow> getByAccountLimit(int lim) {
        Date lastUpd = ((IzdBatches) sf.getCurrentSession().createCriteria(IzdBatches.class).addOrder(Order.desc("endTime")).setMaxResults(1).list().get(0)).getEndTime();
//		Date lastUpd = (Date) sf.getCurrentSession().createSQLQuery("select max(izd_batches.end_time) from izd_batches").list().iterator().next();
        return (List<IzdPreProcessingRow>) sf.getCurrentSession().createCriteria(IzdPreProcessingRow.class)
                .add(Restrictions.in("trType", Arrays.asList("325", "326", "32A", "32B", "32D", "32H", "32S", "32T")))
                .add(Restrictions.lt("regDate", lastUpd))
                .setMaxResults(lim)
                .setProjection(Projections.projectionList().add(Projections.distinct(Projections.property("accountNo")))).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IzdPreProcessingRow> getNFeesFromPreProcessingRow(int lim) {

        StringBuilder queryString = new StringBuilder("SELECT pp FROM IzdPreProcessingRow pp ");
        queryString.append("JOIN pp.card c ");
        queryString.append("JOIN c.izdAgreement a ");
        queryString.append("JOIN a.izdBranch b ");
        queryString.append("WHERE pp.trType in ('325','326','32A','32B','32D','32H','32S','32T', '32c', '32b') AND "
                + "b.comp_id.branch != '002' AND pp.executionType <> 12 ORDER BY pp.internalNo ");

        Query query = sf.getCurrentSession().createQuery(queryString.toString());
        query.setMaxResults(lim);

        return (List<IzdPreProcessingRow>) query.list();
    }

    @Override
    public IzdPreProcessingRow getIzdPreProcessingRowByInternalNo(BigDecimal internalNo) {
        return (IzdPreProcessingRow) sf.getCurrentSession().get(IzdPreProcessingRow.class, internalNo);
    }
}

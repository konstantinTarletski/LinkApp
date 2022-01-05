package lv.bank.cards.dbsynchronizer.dao;

import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCcyConv;
import lv.bank.cards.core.entity.linkApp.PcdDesign;
import lv.bank.cards.core.entity.linkApp.PcdLog;
import lv.bank.cards.core.entity.linkApp.PcdPpCard;
import lv.bank.cards.core.entity.linkApp.PcdSlip;
import lv.bank.cards.dbsynchronizer.BusinessException;
import lv.bank.cards.dbsynchronizer.config.ApplicationProperties;
import lv.bank.cards.dbsynchronizer.utils.AbstractSpecialUpdateHandler;
import lv.bank.cards.dbsynchronizer.utils.Converter;
import lv.bank.cards.dbsynchronizer.utils.MonitorRiskLevel;
import org.apache.log4j.Logger;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
@Transactional("pcdHibernateTransactionManager")
public class LinkAppDao {

    private static final Logger log = Logger.getLogger(LinkAppDao.class);

    @Qualifier("pcdSessionFactory")
    @Autowired
    protected LocalSessionFactoryBean pcdSessionFactory;
    @Autowired
    protected ApplicationProperties appProperties;

    public Session getSession() {
        return pcdSessionFactory.getObject().getCurrentSession();
    }


    public List<PcdCard> getPcdCardsByContactless(List<Integer> contactlesListlist) {
        List<PcdCard> cards = getSession().createCriteria(PcdCard.class).add(Restrictions.in("contactless", contactlesListlist)).list();
        return cards;
    }

    public List<PcdAccount> getPcdAccountListByAccountNo(BigDecimal accountNo) {
        Session session = getSession();
        List<PcdAccount> l = session.createCriteria(PcdAccount.class)
                .add(Restrictions.eq("accountNo", accountNo)).list();
        return l;
    }

    public void deletePcdCardsAccountByCard(String card) {
        Session session = getSession();
        session.createQuery("delete from PcdCardsAccount where card=:card")
                .setString("card", card)
                .executeUpdate();
    }

    public PcdCard getPcdCardById(String card) {
        Session session = getSession();
        return session.get(PcdCard.class, card);
    }

    public void evictPcdCard(PcdCard card) {
        Session session = getSession();
        session.evict(card);
    }

    public void updatePcdCard(String card, Integer contactless, Date ctime) {
        Session session = getSession();
        PcdCard pcdCard = session.get(PcdCard.class, card);
        pcdCard.setContactless(contactless == null ? null : contactless.intValue());
        pcdCard.setCtime(ctime);
        session.update(card);
    }

    public void savePcdLog(PcdLog pcdLog) {
        Session session = getSession();
        session.save(pcdLog);
    }

    public void savePcdPpCard(PcdPpCard pcdPpCard) {
        Session session = getSession();
        session.save(pcdPpCard);
    }

    public void updatePcdCardContactless(String card, Integer contactless) {
        Session session = getSession();
        PcdCard pcdCard = session.get(PcdCard.class, card);
        pcdCard.setContactless(contactless);
        session.update(pcdCard);
    }

    public void deletePcdCcyConv(PcdCcyConv pcdCcyConv) {
        Session session = getSession();
        session.delete(pcdCcyConv);
    }

    public void saveOrUpdatePcdSlip(PcdSlip pcdSlip) {
        Session session = getSession();
        session.saveOrUpdate(pcdSlip);
    }

    public List<PcdCard> getPcdCardListByStatus1andPpCardsStatus(String status1, BigDecimal ppCardsStatus){
        Session session = getSession();
        List<PcdCard> list = session.createCriteria(PcdCard.class)
                .createAlias("pcdPpCards", "ppCards")
                .add(Restrictions.eq("status1", status1))
                .add(Restrictions.not(Restrictions.eq("ppCards.status", ppCardsStatus)))
                .list();
        return list;
    }

    public Iterator<PcdCcyConv> getPcdCcyConv(String bankC, String curency, String procCcy, String fromReconcil, String buyOrSell) {
        Session session = getSession();
        Iterator<PcdCcyConv> it = session.createCriteria(PcdCcyConv.class)
                .add(Restrictions.eq("id.bankC", bankC))
                .add(Restrictions.eq("id.curency", curency))
                .add(Restrictions.eq("id.procCcy", procCcy))
                .add(Restrictions.eq("id.fromReconcil", fromReconcil))
                .add(Restrictions.eq("id.buyOrSell", buyOrSell))
                .list().iterator();
        return it;
    }

    public void deletePcdSlipByProcIdbankCgroupc(long procId, String bankC, String groupc) {
        Session session = getSession();
        session.createQuery("delete PcdSlip slip where slip.procId=:procId and slip.bankC=:bankC and slip.groupc=:groupc")
                .setLong("procId", procId)
                .setString("bankC", bankC)
                .setString("groupc", groupc)
                .executeUpdate();
    }

    public void deletePcdCardsAccountByAccount(BigDecimal account) {
        Session session = getSession();
        session.createQuery("delete from PcdCardsAccount where account_No=:account")
                .setBigDecimal("account", account)
                .executeUpdate();
    }

    public Long countPcdAccumulatorTypeByDescription(String description) {
        Long count = (Long) getSession().createQuery("Select count(t) from PcdAccumulatorType t where t.description = :descr").setParameter("descr", description).uniqueResult();
        return count;
    }

    public List<MonitorRiskLevel> getMonitorRiskLevelList() {
        List<MonitorRiskLevel> list = getSession().createSQLQuery("select " +
                        "    at.description as description " +
                        "   ,a.param_grp as param_grp " +
                        "from " +
                        "    pcd_accumulator_types at " +
                        "   ,(select distinct param_grp from pcd_accumulators) a " +
                        "where " +
                        "    at.mandatory = 'y' " +
                        "and length(a.param_grp) = 3 " +
                        "and not exists (select 1 from pcd_accumulators a2 where a2.param_grp = a.param_grp and a2.description = at.description)")
                .addScalar("description")
                .addScalar("param_grp")
                .setResultTransformer(Transformers.aliasToBean(MonitorRiskLevel.class)).list();
        return list;
    }

    public void processUpdate(ScrollableResults i, Class<?> d, Method getIdMethod, Map<String, AbstractSpecialUpdateHandler> availableHandlers) throws BusinessException {

        Session session = getSession();

        while (i.next()) {

            Object obj = i.get(0);

            if (Converter.isConverterAvailble(obj.getClass(), d)) {

                Object res = Converter.convert(obj, d);

                try {

                    log.debug("Got object to update:" + getIdMethod.invoke(res));

                    Object tmpO;
                    if ((tmpO = session.get(d, (Serializable) getIdMethod.invoke(res))) != null) {
                        session.evict(tmpO);
                        execBeforeUpdateFilter(tmpO, res, availableHandlers);
                        log.info("Updating " + res.getClass().getSimpleName() + " " + getIdMethod.invoke(res));
                        session.update(res);
                    } else {
                        log.info("112: Creating " + res.getClass().getSimpleName() + " " + getIdMethod.invoke(res));
                        execBeforeInsertFilters(res, availableHandlers);
                        session.save(res);
                        execAfterInsertFilters(res, availableHandlers);
                    }
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                    log.error("Error while getting PK of row", e);
                    throw new BusinessException("Error while getting PK of row", e);
                }

            } else {
                log.error("Don't know how to work with " + obj.getClass().getName());
                throw new BusinessException("Don't know how to work with " + obj.getClass().getName());
            }
        }
    }

    protected void execBeforeUpdateFilter(Object o, Object newObj, Map<String, AbstractSpecialUpdateHandler> availableHandlers) {
        log.debug("Notifying update filter");
        availableHandlers
                .entrySet()
                .stream()
                .filter(e -> e.getKey().equalsIgnoreCase(newObj.getClass().getName()))
                .findAny().ifPresent(item ->
                        item.getValue().beforeUpdateFilter(o, newObj)
                );
    }

    protected void execBeforeInsertFilters(Object o, Map<String, AbstractSpecialUpdateHandler> availableHandlers) {
        availableHandlers
                .entrySet()
                .stream()
                .filter(e -> e.getKey().equalsIgnoreCase(o.getClass().getName()))
                .findAny().ifPresent(item ->
                        item.getValue().beforeInsertFilter(o)
                );
    }

    protected void execAfterInsertFilters(Object o, Map<String, AbstractSpecialUpdateHandler> availableHandlers) {
        log.debug("execAfterInsertFilters()");
        availableHandlers
                .entrySet()
                .stream()
                .filter(e -> e.getKey().equalsIgnoreCase(o.getClass().getName()))
                .findAny().ifPresent(item ->
                        item.getValue().afterInsertFilter(o)
                );
    }

    public String getDesign(PcdCard card) {
        String notablePart6 = card.getCard().substring(0, 6);
        String notablePart7 = card.getCard().substring(0, 7);
        Date renew = card.getRenewDate();
        if (renew == null) {
            renew = card.getRecDate();
        }

        log.info("Search design for " + card.getCard());

        Session session = getSession();

        Collection<Object> designs = session.createCriteria(PcdDesign.class)
                .add(Restrictions.eq("cardType", card.getCard()))
                .add(Restrictions.eq("active", Boolean.TRUE))
                .addOrder(Order.desc("date")).list();

        if (designs == null || designs.isEmpty()) {
            log.info("Search design for " + notablePart7);
            designs = session.createCriteria(PcdDesign.class)
                    .add(Restrictions.eq("cardType", notablePart7))
                    .add(Restrictions.eq("active", Boolean.TRUE))
                    .addOrder(Order.desc("date")).list();
        }

        if (designs == null || designs.isEmpty()) {
            log.info("Search design for " + notablePart6);
            designs = session.createCriteria(PcdDesign.class)
                    .add(Restrictions.eq("cardType", notablePart6))
                    .add(Restrictions.eq("active", Boolean.TRUE))
                    .addOrder(Order.desc("date")).list();
        }

        if (designs == null || designs.isEmpty()) {
            return null;
        } else {
            for (Object d : designs) {
                PcdDesign design = (PcdDesign) d;
                if (renew.after(design.getDate())) {
                    log.info("Found design " + design.getDesign());
                    return design.getDesign();
                }
            }
        }

        log.info("Did not find design for " + notablePart6);
        return null;
    }

    public void cleanPcdAuthBatches(int days) {
        String sql = "delete from pcd_auth_batches b where trunc(b.datetime) < sysdate - " + days;
        getSession().createSQLQuery(sql).executeUpdate();
    }

    public void cleanPcdAuthPosIsoHostMessages(int days) {
        String sql = "delete from pcd_auth_pos_iso_host_messages a where trunc(a.datetime) < sysdate - " + days;
        getSession().createSQLQuery(sql).executeUpdate();
    }

    public void cleanSmsReconciliation(int days) {
        String sql = "delete from sms_reconciliation r where r.last_date < sysdate - " + days;
        getSession().createSQLQuery(sql).executeUpdate();
    }

    public void cleanPcdAtmAdverts(int days) {
        String sql = "delete from pcd_atm_adverts a where a.rec_date < sysdate - " + days;
        getSession().createSQLQuery(sql).executeUpdate();
    }

    public void cleanPcdCardsPendingTokenization(int days) {
        String sql = "delete from pcd_cards_pending_tokenization a where a.rec_date < sysdate - " + 3;
        getSession().createSQLQuery(sql).executeUpdate();
    }

    public void cleanPcdCardsPendingTokenizationByTokenRefId (int minutes) {
        String sql = "delete from pcd_cards_pending_tokenization a where a.token_ref_id is null and a.rec_date < sysdate - interval '" + minutes + "' minute";
        getSession().createSQLQuery(sql).executeUpdate();
    }

}

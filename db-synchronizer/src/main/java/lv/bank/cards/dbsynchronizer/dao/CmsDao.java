package lv.bank.cards.dbsynchronizer.dao;

import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdBranch;
import lv.bank.cards.core.entity.cms.IzdBranchPK;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCardsAddFields;
import lv.bank.cards.core.entity.cms.IzdCardsPinBlocks;
import lv.bank.cards.core.entity.cms.IzdCardsPinBlocksPK;
import lv.bank.cards.core.entity.cms.IzdClAcct;
import lv.bank.cards.core.entity.cms.NordlbBranch;
import lv.bank.cards.dbsynchronizer.CustomQueriesBase;
import lv.bank.cards.dbsynchronizer.config.ApplicationProperties;
import lv.bank.cards.dbsynchronizer.utils.ContactlessSqlQuery;
import lv.bank.cards.dbsynchronizer.utils.DataAndStatelessSessionHolder;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(value = "cmsHibernateTransactionManager")
public class CmsDao extends SourceDaoBase {

    private static final Logger log = Logger.getLogger(CmsDao.class);


    public CmsDao(@Qualifier("cmsSessionFactory") LocalSessionFactoryBean cmsSessionFactory,
                  ApplicationProperties appProperties,
                  CustomQueriesBase customQueries
    ) {
        super(cmsSessionFactory, appProperties, customQueries);
    }

    public void enableFilter() {
        getSession().enableFilter("bankFilter").setParameter("bankC", appProperties.getBankc());
    }

    public DataAndStatelessSessionHolder<ScrollableResults> findDataToUpdate(Class<?> s, Class<?> d, Date lastUpdate, Date currentLevel) {
        return super.findDataToUpdate(s, d, lastUpdate, currentLevel);
    }

    public Date getIzdShadowCtrlCtime() {
        return (Date) getSession().createSQLQuery("select ctime from izd_shadow_ctrl").list().get(0);
    }

    public BigDecimal getContactlessByCard(String card) {
        BigDecimal contactless = (BigDecimal) getSession().createSQLQuery(
                ContactlessSqlQuery.getContaclessQuery(card)).list().get(0);
        return contactless;
    }

    public IzdCardsAddFields getIzdCardsAddFieldsById(String card) {
        return getSession().get(IzdCardsAddFields.class, card);
    }

    public Set<IzdClAcct> getIzdClAcctSetById(BigDecimal accountNo) {
        Set<IzdClAcct> set = new HashSet<>();
        set.addAll(getSession().get(IzdAccount.class, accountNo).getIzdClAccts());
        return set;
    }

    public Set<IzdCard> getIzdCardSetByClAcct(BigDecimal tabKey) {
        Set<IzdCard> set = new HashSet<>();
        set.addAll(getSession().get(IzdClAcct.class, tabKey).getIzdCards());
        return set;
    }

    public IzdCardsPinBlocks getIzdCardsPinBlocksByCard(String card) {
        IzdCard izdCard = getSession().get(IzdCard.class, card);
        return izdCard.getIzdCardsPinBlock();
    }

    public NordlbBranch getIzdNordlbBranchByCard(String bankCode, String branch) {
        IzdBranchPK pk = new IzdBranchPK();
        pk.setBranch(branch);
        pk.setBankCode(bankCode);
        IzdBranch izdBranch = getSession().get(IzdBranch.class, pk);
        if (izdBranch.getNordlbBranches() != null && !izdBranch.getNordlbBranches().isEmpty()) {
            return izdBranch.getNordlbBranches().iterator().next();
        }
        return null;
    }

    public DataAndStatelessSessionHolder<ScrollableResults> getIzdSlip(String bankC, long procId, String groupC) {
        StatelessSession statelessSession = sessionFactory.getObject().openStatelessSession();
        String sql =
                "select " +
                        "merchant," +
                        "settl_cmi," +
                        "send_cmi," +
                        "rec_cmi," +
                        "card," +
                        "exp_date," +
                        "tran_amt," +
                        "ccy_exp," +
                        "tran_ccy," +
                        "tran_date_time," +
                        "tran_type," +
                        "apr_code," +
                        "stan," +
                        "fee," +
                        "abvr_name," +
                        "city," +
                        "country," +
                        "terminal," +
                        "rec_date," +
                        "accnt_ccy," +
                        "accnt_amt," +
                        "amount," +
                        "post_date," +
                        "err_code," +
                        "acqref_nr," +
                        "term_id," +
                        "account_no," +
                        "proc_id," +
                        "bank_c," +
                        "ctime," +
                        "groupc," +
                        "deb_cred," +
                        "tr_code," +
                        "tr_fee, " +
                        "sb_amount," +
                        "branch," +
                        "card_acct " +
                        "from " +
                        "izd_slip " +
                        "where " +
                        "bank_c=" + bankC +
                        " and proc_id=" + procId +
                        " and groupc=" + groupC;
        SQLQuery sqlQuery = statelessSession.createSQLQuery(sql);
        sqlQuery.setFetchSize(500);

        DataAndStatelessSessionHolder<ScrollableResults> data = new DataAndStatelessSessionHolder<>();
        data.setData(sqlQuery.scroll(ScrollMode.FORWARD_ONLY));
        data.setStatelessSession(statelessSession);
        return data;
    }

    public Date getCurrentWaterMark() {
        Date waterMark;
        try {
            List<Date> transactions = getSession().createSQLQuery(
                            "SELECT nvl(min(tr.start_date),sysdate)-(1/8640) as syncdate FROM v$transaction tr, v$locked_object lo, "
                                    + "all_objects ob WHERE tr.xidsqn=lo.xidsqn and lo.object_id = ob.object_id and ob.owner='" + appProperties.getSchemaName() + "' and "
                                    + "ob.object_name in ('IZD_DB_OWNERS', 'IZD_CARD_DESIGNS', 'IZD_STOP_CAUSES', 'IZD_CARD_TYPE', 'IZD_CARD_GROUPS', "
                                    + "'IZD_BIN_TABLE', 'IZD_BIN_CARD_DESIGNS', 'IZD_REP_LANG', 'IZD_REP_DISTRIBUT', 'IZD_CLIENTS', 'IZD_BRANCHES', "
                                    + "'IZD_COMPANIES', 'IZD_CCY_TABLE', 'IZD_AGREEMENT', 'IZD_ACCOUNTS', 'IZD_ACC_PARAM', 'IZD_COND_CARD', "
                                    + "'IZD_CARDS', 'DNB_IZD_ACCNT_CHNG', 'IZD_COND_ACCNT' )")
                    .list();
            waterMark = transactions.get(0);
        } catch (HibernateException e) {
            log.error("Error getting currentWaterMark", e);
            throw new RuntimeException("Error during getting currentWaterMark", e);
        }
        return waterMark;
    }

}

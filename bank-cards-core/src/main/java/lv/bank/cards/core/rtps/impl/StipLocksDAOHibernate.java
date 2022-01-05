package lv.bank.cards.core.rtps.impl;

import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.entity.rtps.StipLocks;
import lv.bank.cards.core.rtps.dao.StipLocksDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class StipLocksDAOHibernate extends BaseDAOHibernate implements StipLocksDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<StipLocks> findStipLocksByCardNumber(String card, boolean mode) {
        Criteria criteria = sf.getCurrentSession().createCriteria(StipLocks.class).addOrder(Order.desc("requestDate"));
        if (mode) {
            criteria.createCriteria("stipLocksMatch").add(Restrictions.eq("fld002", card));
        } else {
            criteria.createCriteria("stipLocksMatch", "m")
                    .add(Restrictions.eq("fld002", card))
                    .add(Restrictions.sqlRestriction("not (substr(nvl(FLD_041,'x'),1,5) = 'PBANK' and exists (select child_row from RTPSADMIN.stip_locks_match where centre_id='42800202350' and child_row=m1_.row_numb and fld_002=m1_.fld_002) and m1_.msg_type in ('1400','1401','1420','1421'))"))
                    .add(Restrictions.sqlRestriction("not (substr(nvl(FLD_041,'x'),1,5) = 'PBANK' and exists (select row_numb  from RTPSADMIN.stip_locks_match where centre_id='42800202350' and row_numb=m1_.child_row and fld_002=m1_.fld_002))"));
        }
        return (List<StipLocks>) criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StipLocks> findStipLocksByAccount(String account, boolean mode, Date fromDate, Date toDate, Long fromAmount, Long toAmount, String shop, List<String> cardNumbers) {
        Criteria criteria = sf.getCurrentSession().createCriteria(StipLocks.class).addOrder(Order.desc("requestDate"));
        criteria.add(Restrictions.eq("stipAccount.comp_id.accountId", account));

        criteria.createCriteria("stipLocksMatch", "m");
        if (!mode) {
            criteria.add(Restrictions.sqlRestriction("not (substr(nvl(FLD_041,'x'),1,5) = 'PBANK' and not(substr(nvl(FLD_041,'x'),1,8) = 'PBANKSMS'))"));
        }

        if (fromDate != null) {
            criteria.add(Restrictions.ge("requestDate", fromDate));
        }

        if (fromAmount != null) {
            criteria.add(Restrictions.ge("m.fld006", fromAmount));
        }
        if (toAmount != null) {
            criteria.add(Restrictions.le("m.fld006", toAmount));
        }
        if (shop != null) {
            criteria.add(Restrictions.like("m.fld043", "%" + shop + "%"));
        }
        if (cardNumbers != null) {
            if (mode) {
                criteria.add(Restrictions.in("m.fld002", cardNumbers));
            } else {
                criteria.add(Restrictions.in("m.fld002", cardNumbers))
                        .add(Restrictions.sqlRestriction("not (substr(nvl(FLD_041,'x'),1,5) = 'PBANK' and exists (select child_row from RTPSADMIN.stip_locks_match where centre_id='42800202350' and child_row=m1_.row_numb and fld_002=m1_.fld_002) and m1_.msg_type in ('1400','1401','1420','1421'))"))
                        .add(Restrictions.sqlRestriction("not (substr(nvl(FLD_041,'x'),1,5) = 'PBANK' and exists (select row_numb  from RTPSADMIN.stip_locks_match where centre_id='42800202350' and row_numb=m1_.child_row and fld_002=m1_.fld_002))"));
            }
        }
        return (List<StipLocks>) criteria.list();
    }

    @Override
    public Long calculateTotalLockedAmount(StipAccount sa) {
        @SuppressWarnings("rawtypes")
        Iterator i = sf.getCurrentSession().createQuery("select sum(this.amount) from StipLocks as this where this.stipAccount.comp_id.centreId=:centreId and this.stipAccount.comp_id.accountId=:accountId")
                .setString("centreId", sa.getComp_id().getCentreId())
                .setString("accountId", sa.getComp_id().getAccountId())
                //.setEntity("sa", sa)
                .list().iterator();
        if (i.hasNext()) {
            //Object [] row = (Object[]) i.next();
            return (Long) i.next();
        }
        return 0L;
    }

}

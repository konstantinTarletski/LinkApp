package lv.bank.cards.core.cms.impl;

import lv.bank.cards.core.cms.dao.IzdLockDAO;
import lv.bank.cards.core.entity.cms.IzdLock;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
public class IzdLockDAOHibernate extends BaseDAOHibernate implements IzdLockDAO {

    private static final String SCHEMA_NAME = "ISSUING_LV";

    @Override
    public List<IzdLock> findIzdLocksByCardNumber(String card, boolean mode) {
        Criteria criteria = sf.getCurrentSession().createCriteria(IzdLock.class).addOrder(Order.desc("requestDate"))
                .add(Restrictions.eq("lockingFlag", 1));

        if (mode) {
            criteria.add(Restrictions.eq("fld002", card));
        } else {
            criteria.add(Restrictions.eq("fld002", card))
                    .add(Restrictions.sqlRestriction("not (substr(nvl(FLD_041,'x'),1,5) = 'PBANK' and exists (select child_row from " + SCHEMA_NAME + ".izd_locks where bank_c={alias}.bank_c and groupc={alias}.groupc and child_row={alias}.row_numb and fld_002={alias}.fld_002) and {alias}.msg_type in ('1400','1401','1420','1421'))"))
                    .add(Restrictions.sqlRestriction("not (substr(nvl(FLD_041,'x'),1,5) = 'PBANK' and exists (select row_numb  from " + SCHEMA_NAME + ".izd_locks where bank_c={alias}.bank_c and groupc={alias}.groupc and row_numb={alias}.child_row and fld_002={alias}.fld_002))"));
        }
        return (List<IzdLock>) criteria.list();
    }

    @Override
    public List<IzdLock> findIzdLocksByAccount(BigDecimal accountNo, boolean mode, Date fromDate, Date toDate, Long fromAmount, Long toAmount, String shop, List<String> cardNumbers) {
        Criteria criteria = sf.getCurrentSession().createCriteria(IzdLock.class).addOrder(Order.desc("requestDate"))
                .add(Restrictions.eq("accountNo", accountNo))
                .add(Restrictions.eq("lockingFlag", 1));

        if (!mode) {
            criteria.add(Restrictions.sqlRestriction("not (substr(nvl(FLD_041,'x'),1,5) = 'PBANK' and not(substr(nvl(FLD_041,'x'),1,8) = 'PBANKSMS'))"));
        }

        if (fromDate != null) {
            criteria.add(Restrictions.ge("requestDate", fromDate));
        }
        if (toDate != null) {
            criteria.add(Restrictions.le("requestDate", toDate));
        }
        if (fromAmount != null) {
            criteria.add(Restrictions.ge("fld006", fromAmount));
        }
        if (toAmount != null) {
            criteria.add(Restrictions.le("fld006", toAmount));
        }
        if (shop != null) {
            criteria.add(Restrictions.like("fld043", "%" + shop + "%"));
        }
        if (cardNumbers != null && !cardNumbers.isEmpty()) {
            if (mode) {
                criteria.add(Restrictions.in("fld002", cardNumbers));
            } else {
                criteria.add(Restrictions.in("m.fld002", cardNumbers))
                        .add(Restrictions.sqlRestriction("not (substr(nvl(FLD_041,'x'),1,5) = 'PBANK' and exists (select child_row from " + SCHEMA_NAME + ".izd_locks where bank_c={alias}.bank_c and groupc={alias}.groupc and child_row={alias}.row_numb and fld_002={alias}.fld_002) and {alias}.msg_type in ('1400','1401','1420','1421'))"))
                        .add(Restrictions.sqlRestriction("not (substr(nvl(FLD_041,'x'),1,5) = 'PBANK' and exists (select row_numb  from " + SCHEMA_NAME + ".izd_locks where bank_c={alias}.bank_c and groupc={alias}.groupc and row_numb={alias}.child_row and fld_002={alias}.fld_002))"));
            }
        }
        return (List<IzdLock>) criteria.list();
    }
}

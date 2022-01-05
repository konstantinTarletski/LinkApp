package lv.bank.cards.core.linkApp.impl;

import lv.bank.cards.core.linkApp.dao.AccountsDAO;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.entity.linkApp.PcdCondAccntPK;
import lv.bank.cards.core.entity.linkApp.PcdMerchant;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountsDAOHibernate extends BaseDAOHibernate implements AccountsDAO {

    @Override
    public PcdAccount findByAccountNo(BigDecimal accountNo) {
        return (PcdAccount) sf.getCurrentSession().get(PcdAccount.class, accountNo);
    }

    @Override
    public PcdAccount getAccountByCoreAccountNo(String coreAccountNo, String country) {
        return (PcdAccount) sf.getCurrentSession().createCriteria(PcdAccount.class, "a")
                .createAlias("a.pcdAccParam", "p").createAlias("a.pcdClient", "c")
                .add(Restrictions.and(Restrictions.eq("p.ufield5", coreAccountNo), Restrictions.eq("c.region", country))).uniqueResult();
    }

    @Override
    public List<PcdAccount> getActiveAccountsByCif(String cif, String country) {
        return (List<PcdAccount>) sf.getCurrentSession().createCriteria(PcdAccount.class, "a")
                .createAlias("a.pcdAccParam", "p").createAlias("a.pcdClient", "c")
                .add(Restrictions.and(
                        Restrictions.isNotNull("p.ufield5"),
                        Restrictions.eq("p.status", "0"),
                        Restrictions.eq("c.clientB", cif),
                        Restrictions.eq("c.region", country)
                )).list();
    }

    @Override
    public String getNewCardAcctId(String externalId) throws DataIntegrityException { // Get a new UID to put into IZD_ACCOUNTS.CARD_ACCT during account creation
        SessionImpl session = (SessionImpl) sf.getCurrentSession();
        Connection connection = session.connection();
        try (PreparedStatement ps = connection.prepareStatement("SELECT PCD_CARD_ACCT_SEQ.nextval as nextval from dual")) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal id = rs.getBigDecimal("nextval");
                    return id.toString();
                } else {
                    throw new DataIntegrityException("Can't get nextval from sequence PCD_CARD_ACCT_SEQ in PCD");
                }
            }
        } catch (SQLException e) {
            throw new DataIntegrityException("Nextval from sequence PCD_CARD_ACCT_SEQ (Generated value for IZD_ACCOUNTS.CARD_ACCT) is null. Check if this sequence is OK");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public PcdMerchant findByRegNr(String regNr) {
        List<PcdMerchant> ls = sf.getCurrentSession().createCriteria(PcdMerchant.class).add(Restrictions.eq("regNr", regNr)).list();
        if (ls.size() > 0) return ls.get(0);
        else return null;
    }

    @Override
    public PcdAccount getAccountByCardNumber(String cardNumber) {
        return (PcdAccount) sf.getCurrentSession().createCriteria(PcdAccount.class, "a")
                .createAlias("a.pcdCards", "c")
                .add(Restrictions.eq("c.card", cardNumber)).uniqueResult();
    }

    @Override
    public PcdCondAccnt getCondAccntByComp_Id(PcdCondAccntPK comp_Id) {
        return (PcdCondAccnt) sf.getCurrentSession().createCriteria(PcdCondAccnt.class, "a")
                .add(Restrictions.and(
                        Restrictions.eq("a.comp_id.bankC", comp_Id.getBankC()),
                        Restrictions.eq("a.comp_id.groupc", comp_Id.getGroupc()),
                        Restrictions.eq("a.comp_id.condSet", comp_Id.getCondSet()),
                        Restrictions.eq("a.comp_id.ccy", comp_Id.getCcy())
                )).uniqueResult();
    }
}

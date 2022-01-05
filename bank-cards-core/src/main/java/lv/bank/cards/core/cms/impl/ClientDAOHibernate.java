package lv.bank.cards.core.cms.impl;

import lombok.NoArgsConstructor;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.entity.cms.IzdClAcct;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

@NoArgsConstructor
public class ClientDAOHibernate extends BaseDAOHibernate implements ClientDAO {

    @SuppressWarnings("unchecked")
    @Override
    public IzdClient findByCif(String cif, String account, String country) {
        if (account != null) {
            StringBuilder queryString = new StringBuilder("SELECT C FROM IzdClient c, IzdAccount a ");
            queryString.append("JOIN a.izdClAccts cl ");
            queryString.append("WHERE c.clientB = :client AND c.comp_id.client = cl.client AND c.region = :country");
            queryString.append(" AND a.izdAccParam.ufield5 = :account ORDER BY c.comp_id.client DESC ");

            Query query = sf.getCurrentSession().createQuery(queryString.toString());
            query.setParameter("client", cif);
            query.setParameter("account", account);
            query.setParameter("country", country);

            List<IzdClient> found = query.list();
            if (found != null && found.size() > 0) {
                return found.get(0);
            }
        }

        List<IzdClient> data = sf.getCurrentSession().createCriteria(IzdClient.class)
                .add(Restrictions.and(Restrictions.eq("clientB", cif), Restrictions.eq("region", country)))
                .addOrder(Order.desc("comp_id.client")).list();

        if (data.size() > 0)
            return data.get(0);
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<IzdClient> find(IzdClient mask) {
        Criteria crit = sf.getCurrentSession()
                .createCriteria(IzdClient.class);
        if (mask.getPersonCode() != null) crit.add(Restrictions.eq("personCode", mask.getPersonCode()));
        return crit.list();
    }

    public IzdClient findByCif(String cif) throws DataIntegrityException {
        List<?> data = sf.getCurrentSession().createCriteria(IzdClient.class)
                .add(Restrictions.eq("clientB", cif)).list();

        if (data.size() == 1)
            return (IzdClient) data.get(0);
        else if (data.size() > 1) {
            throw new DataIntegrityException("There are "+data.size()+" IzdClients with cif =["+cif+"]");
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String findClientNoByCardNo(String cardNo) {
        List<IzdClAcct> data = sf.getCurrentSession().createCriteria(IzdClAcct.class)
                .createAlias("izdCards", "card")
                .add(Restrictions.eq("card.card", cardNo)).list();
        if (data != null && data.size() > 0) {
            return data.get(0).getClient();
        }
        return null;
    }

}

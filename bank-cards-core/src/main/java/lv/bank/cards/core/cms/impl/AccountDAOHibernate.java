package lv.bank.cards.core.cms.impl;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.AccountDAO;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.utils.LinkAppProperties;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class AccountDAOHibernate extends BaseDAOHibernate implements AccountDAO {

    @Override
    public List<IzdAccount> findByIzdClientAndExternalNo(String clientId, String externalNo) {
        if("23".equals(LinkAppProperties.getCmsBankCode())){
            log.debug("findByIzdClientAndExternalNo, ufield5 = {} client = {}", externalNo, clientId);
            Criteria cr = sf.getCurrentSession().createCriteria(IzdAccount.class);
            cr.createCriteria("izdAccParam").add(Restrictions.eq("ufield5", externalNo));
            cr.createCriteria("izdClAccts").add(Restrictions.eq("client", clientId));
            return (List<IzdAccount>) cr.list();
        }else{
            log.debug("findByIzdClientAndExternalNo, cardAcct = {} client = {}", externalNo, clientId);
            return sf.getCurrentSession().getNamedQuery("getAccountByClientAndExternalId")
                    .setString("externalId", externalNo).setString("client", clientId).list();
        }
    }

    @Override
    public IzdAccount findByAccountNo(BigDecimal accountNo) {
        return (IzdAccount) sf.getCurrentSession().get(IzdAccount.class, accountNo);
    }

}

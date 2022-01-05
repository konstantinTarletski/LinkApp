package lv.bank.cards.core.cms.impl;

import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdAgreement;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCondCard;
import lv.bank.cards.core.entity.cms.IzdCondCardPK;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class CardDAOHibernate extends BaseDAOHibernate implements CardDAO {

    @Override
    public IzdCondCard getIzdCondCardByID(IzdCondCardPK id) {
        return (IzdCondCard) sf.getCurrentSession().get(IzdCondCard.class, id);
    }

    @Override
    public IzdCondCard getIzdCondCardByCard(String card) {

        IzdCard izdCard = (IzdCard) sf.getCurrentSession().get(IzdCard.class, card);
        IzdCondCardPK condCardPK = new IzdCondCardPK();

        condCardPK.setCondSet(izdCard.getCondSet());
        condCardPK.setGroupc(izdCard.getIzdCardType().getComp_id().getGroupc());
        condCardPK.setCcy(izdCard.getIzdClAcct().getIzdCardGroupCcy().getComp_id().getCcy());
        condCardPK.setBankC(izdCard.getIzdCardType().getComp_id().getBankC());

        return (IzdCondCard) sf.getCurrentSession().get(IzdCondCard.class, condCardPK);
    }

    @SuppressWarnings("unchecked")
    public List<IzdCard> getIzdCardsByIzdAgreement(IzdAgreement agreement) {
        return sf.getCurrentSession().createCriteria(IzdCard.class)
                .add(Restrictions.eq("izdAgreement", agreement)).list();
    }

    public void setAutomaticRenewFlag(String card, String renewFlag) {
        IzdCard izdCard = (IzdCard)sf.getCurrentSession().load(IzdCard.class,card);
        izdCard.setRenew(renewFlag);
        sf.getCurrentSession().saveOrUpdate(izdCard);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IzdCard getIzdCardByRenewCard(String renewCard) throws SQLException {
        Criteria cr = sf.getCurrentSession().createCriteria(IzdCard.class);
        cr.add(Restrictions.eq("renewedCard", renewCard));
        List<IzdCard> cards = cr.list();
        if (cards.size() == 1) {
            return cards.get(0);
        } else if (cards.size() > 1) {
            throw new SQLException("Found multiple cards by renew card:" + renewCard);
        }
        return null;
    }

    @Override
    public IzdCard getCardByTrackingNo(String trackingNo) throws SQLException {
        Iterator<?> i = sf.getCurrentSession().createCriteria(IzdCard.class)
                .createCriteria("izdCardsAddFields").add(Restrictions.eq("uAField3", trackingNo)).list().iterator();
        if (i.hasNext()){
            IzdCard card = (IzdCard) i.next();
            if(i.hasNext())
                throw new SQLException("Found multiple cards by tracking number = " + trackingNo);
            return card;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IzdCard getIzdCardByOrderId(String orderId) throws SQLException {
        Criteria cr = sf.getCurrentSession().createCriteria(IzdCard.class);
        cr.add(Restrictions.eq("UField8", orderId));
        List<IzdCard> cards = cr.list();
        if (cards.size() == 1) {
            return cards.get(0);
        } else if (cards.size() > 1) {
            throw new SQLException("Found multiple cards by order ID:" + orderId);
        }
        return null;
    }

    @Override
    public IzdAccount findIzdAccountByAcctNr(BigDecimal valueOf) {
        return (IzdAccount) sf.getCurrentSession().get(IzdAccount.class, valueOf);
    }

    @Override
    public boolean isRiskLevelLinkedToBin(String bin, String riskLevel) {
        String query = "select 1 from izd_risktab where bin = ? and risk_level = ?";
        Iterator<?> it = sf.getCurrentSession().createSQLQuery(query)
                .setString(0, bin).setString(1, riskLevel).list().iterator();
        if (it.hasNext()) {
            return true;
        }
        return false;
    }
}

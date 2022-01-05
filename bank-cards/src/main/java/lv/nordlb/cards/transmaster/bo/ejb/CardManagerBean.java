package lv.nordlb.cards.transmaster.bo.ejb;

import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.IzdCardTokensDAO;
import lv.bank.cards.core.cms.dao.ProcessingRowDAO;
import lv.bank.cards.core.cms.dto.IzdCondCardDAO;
import lv.bank.cards.core.cms.impl.CardDAOHibernate;
import lv.bank.cards.core.cms.impl.IzdCardTokensDAOHibernate;
import lv.bank.cards.core.cms.impl.ProcessingRowDAOHibernate;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCardTokens;
import lv.bank.cards.core.entity.cms.IzdPreProcessingRow;
import lv.bank.cards.core.entity.cms.IzdStoplist;
import lv.bank.cards.core.entity.cms.IzdTrtypeName;
import lv.bank.cards.core.entity.cms.IzdTrtypeNameId;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;
import org.hibernate.jdbc.ReturningWork;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class CardManagerBean implements CardManager {

    protected final CardDAO cardDAO;
    protected final ProcessingRowDAO processingRowDAO;
    protected final IzdCardTokensDAO izdCardTokensDAO;

    public CardManagerBean() {
        cardDAO = new CardDAOHibernate();
        processingRowDAO = new ProcessingRowDAOHibernate();
        izdCardTokensDAO = new IzdCardTokensDAOHibernate();
    }

    @Override
    public IzdStoplist getIzdStoplistByCardNumber(String cardNumber) {
        return (IzdStoplist) cardDAO.getObject(IzdStoplist.class, cardNumber);
    }

    @Override
    public IzdCard getIzdCardByCardNumber(String cardNumber) {
        return (IzdCard) cardDAO.getObject(IzdCard.class, cardNumber);
    }

    @Override
    public IzdCard findByRenewCardNumber(String renewCardNumber) throws SQLException {
        return cardDAO.getIzdCardByRenewCard(renewCardNumber);
    }

    @Override
    public IzdCard findByOrderId(String orderId) throws SQLException {
        return cardDAO.getIzdCardByOrderId(orderId);
    }

    @Override
    public IzdCondCardDAO getIzdCondCardByCardNumber(String cardNumber) {
        return new IzdCondCardDAO(cardDAO.getIzdCondCardByCard(cardNumber));
    }

    @Override
    public void saveOrUpdate(Object o) throws DataIntegrityException {
        cardDAO.saveOrUpdate(o);
    }

    @Override
    public IzdAccount getIzdAccountByAccountNr(BigDecimal valueOf) {
        return cardDAO.findIzdAccountByAcctNr(valueOf);
    }

    @Override
    public List<IzdPreProcessingRow> getIzdPreProcessingRowsByCard(String card) {
        return processingRowDAO.findByCard(card);
    }

    @Override
    public List<IzdPreProcessingRow> getIzdPreProcessingRowsByAccLimit(int lim) {
        return processingRowDAO.getByAccountLimit(lim);
    }

    @Override
    public IzdTrtypeName getIzdTrTypeNameByType(IzdTrtypeNameId type) {
        return (IzdTrtypeName) processingRowDAO.getObject(IzdTrtypeName.class, type);
    }

    @Override
    public List<IzdPreProcessingRow> getIzdPreProcessingRowsComissionsLimit(int lim) {
        return processingRowDAO.getNFeesFromPreProcessingRow(lim);
    }

    @Override
    public IzdPreProcessingRow getIzdPreProcessingRowByInternalNo(BigDecimal internalNo) {
        return processingRowDAO.getIzdPreProcessingRowByInternalNo(internalNo);
    }

    @Override
    public String doWork(ReturningWork<String> work) {
        return cardDAO.doWork(work);
    }

    @Override
    public boolean isRiskLevelLinkedToBin(String bin, String riskLevel) {
        return cardDAO.isRiskLevelLinkedToBin(bin, riskLevel);
    }

}

package lv.nordlb.cards.transmaster.bo.interfaces;

import lv.bank.cards.core.cms.dto.IzdCondCardDAO;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdPreProcessingRow;
import lv.bank.cards.core.entity.cms.IzdStoplist;
import lv.bank.cards.core.entity.cms.IzdTrtypeName;
import lv.bank.cards.core.entity.cms.IzdTrtypeNameId;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.hibernate.jdbc.ReturningWork;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Local
public interface CardManager {

    String COMP_NAME = "java:comp/env/ejb/CardManager";
    String JNDI_NAME = "java:app/bankCards/CardManagerBean";

    IzdCard findByRenewCardNumber(String renewCardNumber) throws SQLException;

    IzdCard findByOrderId(String orderId) throws SQLException;

    IzdCondCardDAO getIzdCondCardByCardNumber(String cardNumber);

    IzdStoplist getIzdStoplistByCardNumber(String cardNumber) throws DataIntegrityException;

    IzdCard getIzdCardByCardNumber(String cardNumber);

    void saveOrUpdate(Object o) throws DataIntegrityException;

    IzdAccount getIzdAccountByAccountNr(BigDecimal valueOf);

    List<IzdPreProcessingRow> getIzdPreProcessingRowsByCard(String card);

    List<IzdPreProcessingRow> getIzdPreProcessingRowsByAccLimit(int lim);

    List<IzdPreProcessingRow> getIzdPreProcessingRowsComissionsLimit(int lim);

    IzdPreProcessingRow getIzdPreProcessingRowByInternalNo(BigDecimal internalNo);

    IzdTrtypeName getIzdTrTypeNameByType(IzdTrtypeNameId type);

    boolean isRiskLevelLinkedToBin(String bin, String riskLevel);

    /**
     * Execute hibernate work in current session
     */
    String doWork(ReturningWork<String> work);
}

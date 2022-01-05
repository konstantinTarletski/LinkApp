package lv.bank.cards.core.cms.dao;

import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdAgreement;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCondCard;
import lv.bank.cards.core.entity.cms.IzdCondCardPK;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CardDAO extends DAO {

    IzdCondCard getIzdCondCardByID(IzdCondCardPK id);

    void setAutomaticRenewFlag(String card, String renewFlag);

    List<IzdCard> getIzdCardsByIzdAgreement(IzdAgreement agreement);

    IzdCard getCardByTrackingNo(String trackingNo) throws SQLException;

    IzdCondCard getIzdCondCardByCard(String card);

    IzdCard getIzdCardByRenewCard(String card) throws SQLException;

    IzdCard getIzdCardByOrderId(String orderId) throws SQLException;

    IzdAccount findIzdAccountByAcctNr(BigDecimal valueOf);

    boolean isRiskLevelLinkedToBin(String bin, String riskLevel);
}

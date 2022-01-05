package lv.bank.cards.core.cms.dao;

import lv.bank.cards.core.entity.cms.IzdPreProcessingRow;

import java.math.BigDecimal;
import java.util.List;

public interface ProcessingRowDAO extends DAO {

    IzdPreProcessingRow findBySlip(String slip);

    List<IzdPreProcessingRow> findByCard(String card);

    List<IzdPreProcessingRow> getByAccountLimit(int lim);

    List<IzdPreProcessingRow> getNFeesFromPreProcessingRow(int lim);

    IzdPreProcessingRow getIzdPreProcessingRowByInternalNo(BigDecimal internalNo);

}

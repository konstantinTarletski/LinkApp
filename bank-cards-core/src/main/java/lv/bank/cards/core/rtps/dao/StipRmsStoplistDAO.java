package lv.bank.cards.core.rtps.dao;

import lv.bank.cards.core.entity.rtps.StipRmsStoplist;

import java.util.List;

public interface StipRmsStoplistDAO extends DAO {

    List<StipRmsStoplist> getStipRmsStoplist(String card, String centreId, Long priority);

}

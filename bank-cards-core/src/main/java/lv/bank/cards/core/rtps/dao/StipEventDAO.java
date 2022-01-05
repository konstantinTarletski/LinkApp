package lv.bank.cards.core.rtps.dao;

import lv.bank.cards.core.entity.rtps.StipEventsN;

import java.util.List;

public interface StipEventDAO extends DAO {

    List<StipEventsN> findStipEventNs(StipEventsN mask);

}

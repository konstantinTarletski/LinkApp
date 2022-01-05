package lv.bank.cards.core.linkApp.dao;

import lv.bank.cards.core.entity.linkApp.PcdAccumulator;

public interface AccumulatorsDAO extends DAO {

    PcdAccumulator getAccumulator(String param, String description);

}

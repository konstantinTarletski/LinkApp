package lv.bank.cards.core.cms.dao;

import lv.bank.cards.core.entity.cms.IzdConfig;

import java.util.List;

public interface IzdConfigDAO extends DAO {

    List<IzdConfig> GetIzdConfig();

}

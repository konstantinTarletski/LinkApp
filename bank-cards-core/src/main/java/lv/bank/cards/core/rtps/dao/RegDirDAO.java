package lv.bank.cards.core.rtps.dao;

import lv.bank.cards.core.entity.rtps.Regdir;

import java.util.List;

public interface RegDirDAO extends DAO {

    List<Regdir> GetRegDir();

}

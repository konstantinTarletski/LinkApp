package lv.bank.cards.core.cms.dao;

import lv.bank.cards.core.entity.cms.IzdBranch;

public interface BranchDAO extends DAO {

    IzdBranch findByExternalId(String externalID);

}

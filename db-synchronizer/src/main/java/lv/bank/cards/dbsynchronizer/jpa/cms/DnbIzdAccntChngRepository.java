package lv.bank.cards.dbsynchronizer.jpa.cms;

import lv.bank.cards.core.entity.cms.DnbIzdAccntChng;
import lv.bank.cards.core.entity.cms.DnbIzdAccntChngPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DnbIzdAccntChngRepository extends CrudRepository<DnbIzdAccntChng, DnbIzdAccntChngPK> {

    @Transactional
    @Modifying
    @Query("update DnbIzdAccntChng accntChng set accntChng.status='1' where accntChng.status='0'")
    void markNewProcIdsFromBO();

    @Transactional
    @Modifying
    @Query("delete from DnbIzdAccntChng accntChng where accntChng.status='1'")
    void deleteMarkedProcIdsFromBO();

}

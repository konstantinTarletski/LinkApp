package lv.bank.cards.dbsynchronizer.jpa.pcd;

import lv.bank.cards.core.entity.linkApp.PcdLastUpdated;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface PcdLastUpdatedRepository extends CrudRepository<PcdLastUpdated, String> {

    @Transactional
    @Modifying
    @Query("update PcdLastUpdated c set c.cdate = :cdate WHERE c.code = :nordlbBankc")
    void setLastUpdateTime(@Param("nordlbBankc") String id, @Param("cdate") Date cdate);

}

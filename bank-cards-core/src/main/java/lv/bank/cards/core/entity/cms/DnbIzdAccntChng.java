package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dnb_izd_accnt_chng")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.DnbIzdAccntChng.to.lv.bank.cards.core.entity.linkApp.PcdProcIds",
        query = "Select chng FROM DnbIzdAccntChng chng where chng.status='1'")
public class DnbIzdAccntChng implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DnbIzdAccntChngPK comp_id;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "status", length = 1, nullable = false)
    private String status;

}

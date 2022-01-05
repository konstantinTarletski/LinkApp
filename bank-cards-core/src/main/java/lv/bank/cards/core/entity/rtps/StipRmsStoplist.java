package lv.bank.cards.core.entity.rtps;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "RTPSADMIN.stip_rms_stoplist")
public class StipRmsStoplist implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StipRmsStoplistPK compId;

    @Column(name = "effective_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name = "purge_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purgeDate;

    @ManyToOne
    @JoinColumn(name = "action_code", nullable = false)
    private AnswerCode answerCode;

    @Column(name = "rule_expr", length = 1000)
    private String ruleExpr;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "rec_id", length = 256)
    private String recId;

    @Column(name = "step_count", precision = 3, scale = 0)
    private Integer stepCout;

    @Column(name = "deleted", precision = 3, scale = 0)
    private Integer deleted;

}

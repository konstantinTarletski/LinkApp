package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "pcd_risk_levels")
public class PcdRiskLevel implements Serializable {

    private static final long serialVersionUID = 1690142993314839557L;

    @EmbeddedId
    private PcdRiskLevelPK id;

    @Column(name = "effective_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name = "purge_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purgeDate;

    @Column(name = "mcc_restr", length = 1)
    private String mccRestr;

    @Column(name = "limits_flag", length = 1)
    private String limitsFlag;

    @Column(name = "accum_flag", length = 1)
    private String accumFlag;

    @Column(name = "accum_ccy", length = 3)
    private String accumCcy;

    @Column(name = "abbreviature", length = 20)
    private String abbreviature;

    @Column(name = "description", length = 255)
    private String description;

}

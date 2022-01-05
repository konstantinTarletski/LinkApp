package lv.bank.cards.core.entity.rtps;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Data
@Entity
@Table(name = "RTPSADMIN.stip_params_n")
@NamedNativeQuery(name = "get.updated.items.lv.bank.cards.core.entity.rtps.StipParamN.to.lv.bank.cards.core.entity.linkApp.PcdRiskLevel",
        query = "select * from RTPSADMIN.stip_params_n p " +
                "where centre_id = :centreId and exists ( " +
                "              select 1 from RTPSADMIN.stip_params_n_jn " +
                "              where centre_id = :centreId " +
                "              and jn_datetime between :lastWaterMark and :currentWaterMark " +
                "             )",
        resultClass = StipParamN.class)
public class StipParamN implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StipParamNPK id;

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

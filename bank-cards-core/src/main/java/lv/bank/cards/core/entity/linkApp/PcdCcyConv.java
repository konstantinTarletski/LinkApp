package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@Entity
@Table(name = "pcd_ccy_conv")
public class PcdCcyConv implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdCcyConvPK id;

    @Column(name = "conv_rate", precision = 17, scale = 9)
    private Double convRate;

    @Column(name = "usrid", length = 6)
    private String usrid;

    @Column(name = "ctime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "conv_rate2", precision = 17, scale = 9)
    private Double convRate2;

    @Column(name = "conv_rate_abs", precision = 16, scale = 9)
    private Double convRateAbs;

    @Column(name = "from_reconcile", length = 2)
    private String fromReconcile;

}

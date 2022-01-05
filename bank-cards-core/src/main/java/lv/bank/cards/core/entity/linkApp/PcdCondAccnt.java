package lv.bank.cards.core.entity.linkApp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pcd_cond_accnt")
public class PcdCondAccnt implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdCondAccntPK comp_id;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Column(name = "olimp_intr", precision = 6)
    private Double olimpIntr;

    @Column(name = "late_fee", precision = 10)
    private Long lateFee;

    @Column(name = "stm_fee", precision = 10)
    private Long stmFee;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "due_date1", precision = 2, nullable = false)
    private int dueDate1;

    @Column(name = "deb_intr_base_p", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrBaseP;

    @Column(name = "deb_intr_base_c", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrBaseC;

    @Column(name = "deb_intr_base_o", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrBaseO;

    @Column(name = "deb_intr_base_f", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrBaseF;

    @Column(name = "deb_intr_over_p_b", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrOverPB;

    @Column(name = "deb_intr_over_p_r", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrOverPR;

    @Column(name = "deb_intr_over_c_b", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrOverCB;

    @Column(name = "deb_intr_over_c_r", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrOverCR;

    @Column(name = "deb_intr_over_o_b", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrOverOB;

    @Column(name = "deb_intr_over_o_r", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrOverOR;

    @Column(name = "deb_intr_over_f_b", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrOverFB;

    @Column(name = "deb_intr_over_f_r", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrOverFR;

    @Column(name = "deb_intr_pnty_p_b", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrPntyPB;

    @Column(name = "deb_intr_pnty_p_r", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrPntyPR;

    @Column(name = "deb_intr_pnty_c_b", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrPntyCB;

    @Column(name = "deb_intr_pnty_c_r", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrPntyCR;

    @Column(name = "deb_intr_pnty_o_b", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrPntyOB;

    @Column(name = "deb_intr_pnty_o_r", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrPntyOR;

    @Column(name = "deb_intr_pnty_f_b", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrPntyFB;

    @Column(name = "deb_intr_pnty_f_r", precision = 6, scale = 2, nullable = false)
    private BigDecimal debIntrPntyFR;

    @Column(name = "rev_algo_switch", precision = 1, nullable = false)
    private int revAlgoSwitch;

    @Column(name = "rev_intr_p", precision = 6, scale = 2, nullable = false)
    private BigDecimal revIntrP;

    @Column(name = "rev_intr_c", precision = 6, scale = 2, nullable = false)
    private BigDecimal revIntrC;

    @Column(name = "rev_intr_o", precision = 6, scale = 2, nullable = false)
    private BigDecimal revIntrO;

    @Column(name = "rev_intr_f", precision = 6, scale = 2, nullable = false)
    private BigDecimal revIntrF;
}

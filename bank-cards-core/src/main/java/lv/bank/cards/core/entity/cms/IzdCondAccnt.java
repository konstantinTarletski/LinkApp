package lv.bank.cards.core.entity.cms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "izd_cond_accnt")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdCondAccnt.to.lv.bank.cards.core.entity.linkApp.PcdCondAccnt",
        query = "select c from IzdCondAccnt c where c.ctime between :lastWaterMark and :currentWaterMark")
public class IzdCondAccnt implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdCondAccntPK comp_id;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Column(name = "deb_intr")
    private BigDecimal debIntr;

    @Column(name = "olimp_intr")
    private BigDecimal olimpIntr;

    @Column(name = "cred_intr")
    private BigDecimal credIntr;

    @Column(name = "min_bear")
    private BigDecimal minBear;

    @Column(name = "late_intr")
    private BigDecimal lateIntr;

    @Column(name = "late_intr1")
    private BigDecimal lateIntr1;

    @Column(name = "late_fee")
    private Long lateFee;

    @Column(name = "acct_fee_m")
    private Long acctFeeM;

    @Column(name = "acct_fee_a")
    private Long acctFeeA;

    @Column(name = "stm_fee")
    private Long stmFee;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "rev_intr")
    private BigDecimal revIntr;

    @Column(name = "mrev_fee")
    private Long mrevFee;

    @Column(name = "grace_per", length = 3)
    private String gracePer;

    @Column(name = "n_reduce_intr", nullable = false)
    private BigDecimal NReduceIntr;

    @Column(name = "n_reduce_penalty")
    private BigDecimal NReducePenalty;

    @Column(name = "to_nac_ccy_intr")
    private BigDecimal toNacCcyIntr;

    @Column(name = "from_nac_ccy_intr")
    private BigDecimal fromNacCcyIntr;

    @Column(name = "stm_fee_grace_per", nullable = false)
    private int stmFeeGracePer;

    @Column(name = "paym_tarif", length = 3)
    private String paymTarif;

    @Column(name = "grace_per_m", length = 1, nullable = false)
    private String gracePerM;

    @Column(name = "grace_per_cur")
    private Integer gracePerCur;

    @Column(name = "grace_per_next")
    private Integer gracePerNext;

    @Column(name = "cash_purch_fee_l", nullable = false)
    private BigDecimal cashPurchFeeL;

    @Column(name = "cash_purch_fee_i", nullable = false)
    private BigDecimal cashPurchFeeI;

    @Column(name = "dep_intr")
    private BigDecimal depIntr;

    @Column(name = "dep_open_int")
    private Long depOpenInt;

    @Column(name = "dep_amount")
    private Long depAmount;

    @Column(name = "dep_cap_per", length = 1)
    private String depCapPer;

    @Column(name = "dep_cap_type", length = 1)
    private String depCapType;

    @Column(name = "dep_exp_f", length = 1)
    private String depExpF;

    @Column(name = "dep_close_f", length = 1)
    private String depCloseF;

    @Column(name = "dep_link_f", length = 1)
    private String depLinkF;

    @Column(name = "dep_int_close")
    private BigDecimal depIntClose;

    @Column(name = "dep_int_pnlty")
    private Long depIntPnlty;

    @Column(name = "dep_min_amt")
    private Long depMinAmt;

    @Column(name = "dep_min_pct")
    private BigDecimal depMinPct;

    @Column(name = "dep_int_tf", length = 1)
    private String depIntTf;

    @Column(name = "dep_int_tp", length = 2)
    private String depIntTp;

    @Column(name = "cond_f", length = 1)
    private String condF;

    @Column(name = "conv_markup", nullable = false)
    private BigDecimal convMarkup;

    @Column(name = "deb_intr_base_p", nullable = false)
    private BigDecimal debIntrBaseP;

    @Column(name = "deb_intr_base_c", nullable = false)
    private BigDecimal debIntrBaseC;

    @Column(name = "deb_intr_base_o", nullable = false)
    private BigDecimal debIntrBaseO;

    @Column(name = "deb_intr_base_f", nullable = false)
    private BigDecimal debIntrBaseF;

    @Column(name = "deb_intr_over_p_b", nullable = false)
    private BigDecimal debIntrOverPB;

    @Column(name = "deb_intr_over_p_r", nullable = false)
    private BigDecimal debIntrOverPR;

    @Column(name = "deb_intr_over_c_b", nullable = false)
    private BigDecimal debIntrOverCB;

    @Column(name = "deb_intr_over_c_r", nullable = false)
    private BigDecimal debIntrOverCR;

    @Column(name = "deb_intr_over_o_b", nullable = false)
    private BigDecimal debIntrOverOB;

    @Column(name = "deb_intr_over_o_r", nullable = false)
    private BigDecimal debIntrOverOR;

    @Column(name = "deb_intr_over_f_b", nullable = false)
    private BigDecimal debIntrOverFB;

    @Column(name = "deb_intr_over_f_r", nullable = false)
    private BigDecimal debIntrOverFR;

    @Column(name = "deb_intr_pnty_p_b", nullable = false)
    private BigDecimal debIntrPntyPB;

    @Column(name = "deb_intr_pnty_p_r", nullable = false)
    private BigDecimal debIntrPntyPR;

    @Column(name = "deb_intr_pnty_c_b", nullable = false)
    private BigDecimal debIntrPntyCB;

    @Column(name = "deb_intr_pnty_c_r", nullable = false)
    private BigDecimal debIntrPntyCR;

    @Column(name = "deb_intr_pnty_o_b", nullable = false)
    private BigDecimal debIntrPntyOB;

    @Column(name = "deb_intr_pnty_o_r", nullable = false)
    private BigDecimal debIntrPntyOR;

    @Column(name = "deb_intr_pnty_f_b", nullable = false)
    private BigDecimal debIntrPntyFB;

    @Column(name = "deb_intr_pnty_f_r", nullable = false)
    private BigDecimal debIntrPntyFR;

    @Column(name = "intr_deb_intr_base_p", nullable = false)
    private BigDecimal intrDebIntrBaseP;

    @Column(name = "intr_deb_intr_base_c", nullable = false)
    private BigDecimal intrDebIntrBaseC;

    @Column(name = "intr_deb_intr_base_o", nullable = false)
    private BigDecimal intrDebIntrBaseO;

    @Column(name = "intr_deb_intr_base_f", nullable = false)
    private BigDecimal intrDebIntrBaseF;

    @Column(name = "intr_deb_intr_over_p_b", nullable = false)
    private BigDecimal intrDebIntrOverPB;

    @Column(name = "intr_deb_intr_over_p_r", nullable = false)
    private BigDecimal intrDebIntrOverPR;

    @Column(name = "intr_deb_intr_over_c_b", nullable = false)
    private BigDecimal intrDebIntrOverCB;

    @Column(name = "intr_deb_intr_over_c_r", nullable = false)
    private BigDecimal intrDebIntrOverCR;

    @Column(name = "intr_deb_intr_over_o_b", nullable = false)
    private BigDecimal intrDebIntrOverOB;

    @Column(name = "intr_deb_intr_over_o_r", nullable = false)
    private BigDecimal intrDebIntrOverOR;

    @Column(name = "intr_deb_intr_over_f_b", nullable = false)
    private BigDecimal intrDebIntrOverFB;

    @Column(name = "intr_deb_intr_over_f_r", nullable = false)
    private BigDecimal intrDebIntrOverFR;

    @Column(name = "intr_deb_intr_pnty_p_b", nullable = false)
    private BigDecimal intrDebIntrPntyPB;

    @Column(name = "intr_deb_intr_pnty_p_r", nullable = false)
    private BigDecimal intrDebIntrPntyPR;

    @Column(name = "intr_deb_intr_pnty_c_b", nullable = false)
    private BigDecimal intrDebIntrPntyCB;

    @Column(name = "intr_deb_intr_pnty_c_r", nullable = false)
    private BigDecimal intrDebIntrPntyCR;

    @Column(name = "intr_deb_intr_pnty_o_b", nullable = false)
    private BigDecimal intrDebIntrPntyOB;

    @Column(name = "intr_deb_intr_pnty_o_r", nullable = false)
    private BigDecimal intrDebIntrPntyOR;

    @Column(name = "intr_deb_intr_pnty_f_b", nullable = false)
    private BigDecimal intrDebIntrPntyFB;

    @Column(name = "intr_deb_intr_pnty_f_r", nullable = false)
    private BigDecimal intrDebIntrPntyFR;

    @Column(name = "rev_algo_switch", nullable = false)
    private int revAlgoSwitch;

    @Column(name = "rev_overlimit_payoff_mode", nullable = false)
    private int revOverlimitPayoffMode;

    @Column(name = "rev_intr_p", nullable = false)
    private BigDecimal revIntrP;

    @Column(name = "rev_intr_c", nullable = false)
    private BigDecimal revIntrC;

    @Column(name = "rev_intr_o", nullable = false)
    private BigDecimal revIntrO;

    @Column(name = "rev_intr_f", nullable = false)
    private BigDecimal revIntrF;

    @Column(name = "intr_rev_intr_p", nullable = false)
    private BigDecimal intrRevIntrP;

    @Column(name = "intr_rev_intr_c", nullable = false)
    private BigDecimal intrRevIntrC;

    @Column(name = "intr_rev_intr_o", nullable = false)
    private BigDecimal intrRevIntrO;

    @Column(name = "intr_rev_intr_f", nullable = false)
    private BigDecimal intrRevIntrF;

    @Column(name = "intr_mrev_fee", nullable = false)
    private long intrMrevFee;

    @Column(name = "intr_olimp_intr", nullable = false)
    private BigDecimal intrOlimpIntr;

    @Column(name = "intr_acct_fee_m", nullable = false)
    private long intrAcctFeeM;

    @Column(name = "intr_acct_fee_a", nullable = false)
    private long intrAcctFeeA;

    @Column(name = "intr_stm_fee", nullable = false)
    private long intrStmFee;

    @Column(name = "intr_late_fee", nullable = false)
    private long intrLateFee;

    @Column(name = "intr_cred_intr", nullable = false)
    private BigDecimal intrCredIntr;

    @Column(name = "intr_min_bear", nullable = false)
    private BigDecimal intrMinBear;

    @Column(name = "intr_n_reduce_intr", nullable = false)
    private BigDecimal intrNReduceIntr;

    @Column(name = "intr_n_reduce_penalty", nullable = false)
    private BigDecimal intrNReducePenalty;

    @Column(name = "intr_latefee_bonus_m", nullable = false)
    private int intrLatefeeBonusM;

    @Column(name = "intr_latefee_disc_int", nullable = false)
    private BigDecimal intrLatefeeDiscInt;

    @Column(name = "due_date_cfg", nullable = false)
    private int dueDateCfg;

    @Column(name = "due_date1", nullable = false)
    private int dueDate1;

    @Column(name = "due_date2", nullable = false)
    private int dueDate2;

    @Column(name = "overdue_period", nullable = false)
    private int overduePeriod;

    @Column(name = "intr_period", nullable = false)
    private int intrPeriod;

    @Column(name = "reminder_fee", nullable = false)
    private long reminderFee;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ccy", insertable = false, updatable = false, referencedColumnName = "ccy"),
            @JoinColumn(name = "groupc", insertable = false, updatable = false, referencedColumnName = "groupc"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private IzdCardGroupCcy izdCardGroupCcy;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdCondAccnt", fetch = FetchType.LAZY)
    private Set<IzdAccParam> izdAccParams;

}

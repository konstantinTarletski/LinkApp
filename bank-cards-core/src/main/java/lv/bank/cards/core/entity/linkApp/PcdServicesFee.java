package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "pcd_services_fee")
public class PcdServicesFee implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdServicesFeePK comp_id;

    /**
     * Minimal fee
     */
    @Column(name = "min_fee", precision = 10, scale = 0)
    private Long minFee;

    /**
     * Contant fee alwayas added
     */
    @Column(name = "const_fee", precision = 10, scale = 0)
    private Long constFee;

    /**
     * Procent for account amount
     */
    @Column(name = "percent_fee", precision = 6)
    private Double percentFee;

    /**
     * Max allowed fee
     */
    @Column(name = "max_fee", precision = 10, scale = 0)
    private Long maxFee;

    /**
     * Debet kredit flag D - debit C- credit
     */
    @Column(name = "debit_credit", length = 1, nullable = false)
    private String debitCredit;

    /**
     * Card group
     */
    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    /**
     * Condition set code
     */
    @Column(name = "cond_set", length = 3, nullable = false)
    private String condSet;

    /**
     * Condition set code source A - izd_cond_acnt,C-izd_cond_card
     */
    @Column(name = "cond_set_source", length = 1, nullable = false)
    private String condSetSource;

    /**
     * Last change time
     */
    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    /**
     * User id
     */
    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "rec_date_when_added")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recDateWhenAdded;

    @Column(name = "xt_set_id", precision = 22, scale = 0)
    private BigDecimal xtSetId;

}

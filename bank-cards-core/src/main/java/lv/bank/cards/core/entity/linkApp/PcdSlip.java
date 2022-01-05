package lv.bank.cards.core.entity.linkApp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "pcd_slip")
public class PcdSlip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "slipSeq")
    @SequenceGenerator(name = "slipSeq", sequenceName = "PCD_SLIP_SEQ", allocationSize = 1)
    @Column(name = "slip_id")
    private Long slipId;

    @Column(name = "merchant", length = 15)
    private String merchant;

    @Column(name = "settl_cmi", length = 8)
    private String settlCmi;

    @Column(name = "send_cmi", length = 8)
    private String sendCmi;

    @Column(name = "rec_cmi", length = 8)
    private String recCmi;

    @Column(name = "card", length = 19)
    private String card;

    @Column(name = "exp_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expDate;

    @Column(name = "sb_amount")
    private Long sbAmount;

    @Column(name = "branch", length = 3)
    private String branch;

    @Column(name = "card_acct", length = 20)
    private String cardAcct;

    @Column(name = "tran_amt")
    private Long tranAmt;

    @Column(name = "ccy_exp", length = 1)
    private String ccyExp;

    @Column(name = "tran_ccy", length = 3)
    private String tranCcy;

    @Column(name = "tran_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranDateTime;

    @Column(name = "tran_type", length = 3)
    private String tranType;

    @Column(name = "apr_code", length = 6)
    private String aprCode;

    @Column(name = "stan", length = 12)
    private String stan;

    @Column(name = "fee")
    private Long fee;

    @Column(name = "abvr_name", length = 27)
    private String abvrName;

    @Column(name = "city", length = 20)
    private String city;

    @Column(name = "country", length = 3)
    private String country;

    @Column(name = "terminal", length = 1)
    private String terminal;

    @Column(name = "rec_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recDate;

    @Column(name = "accnt_ccy", length = 3)
    private String accntCcy;

    @Column(name = "accnt_amt", length = 12)
    private long accntAmt;

    @Column(name = "amount")
    private long amount;

    @Column(name = "post_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;

    @Column(name = "err_code", length = 2)
    private String errCode;

    @Column(name = "acqref_nr", length = 23)
    private String acqrefNr;

    @Column(name = "term_id", length = 8)
    private String termId;

    @Column(name = "account_no")
    private BigDecimal accountNo;

    @Column(name = "proc_id")
    private long procId;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    @Column(name = "deb_cred", length = 22)
    private BigDecimal debCred;

    @Column(name = "tr_code", length = 3)
    private String trCode;

    @Column(name = "tr_fee")
    private Long trFee;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "account_no", insertable = false, updatable = false)
    private PcdAccount pcdAccount;

}

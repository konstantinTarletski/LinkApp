package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_acc_param")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
public class IzdAccParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "account_no", nullable = false)
    private BigDecimal accountNo;

    @Column(name = "card_acct", length = 20, nullable = false)
    private String cardAcct;

    @Column(name = "cycle", length = 1)
    private String cycle;

    @Column(name = "min_bal", nullable = false)
    private long minBal;

    @Column(name = "crd", length = 12, nullable = false)
    private long crd;

    @Column(name = "status", length = 1, nullable = false)
    private String status;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "ufield_5", length = 20)
    private String ufield5;

    @Column(name = "stop_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopDate;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "cond_set", length = 3)
    private String condSet;

    @Column(name = "auth_bonus")
    private Long authBonus;

    @Column(name = "ab_expirity")
    @Temporal(TemporalType.TIMESTAMP)
    private Date abExpirity;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "account_no", insertable = false, updatable = false, nullable = false)
    private IzdAccount izdAccount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ccy", updatable = false, insertable = false, referencedColumnName = "ccy"),
            @JoinColumn(name = "bank_c", updatable = false, insertable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "groupc", updatable = false, insertable = false, referencedColumnName = "groupc")
    })
    private IzdCardGroupCcy izdCardGroupCcy;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "cond_set", updatable = false, insertable = false, referencedColumnName = "cond_set"),
            @JoinColumn(name = "ccy", updatable = false, insertable = false, referencedColumnName = "ccy"),
            @JoinColumn(name = "bank_c", updatable = false, insertable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "groupc", updatable = false, insertable = false, referencedColumnName = "groupc")
    })
    private IzdCondAccnt izdCondAccnt;

}

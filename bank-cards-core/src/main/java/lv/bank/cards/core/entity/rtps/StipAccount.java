package lv.bank.cards.core.entity.rtps;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "RTPSADMIN.stip_accounts")
public class StipAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StipAccountPK comp_id;

    @Column(name = "effective_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name = "purge_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purgeDate;

    @Column(name = "initial_amount", precision = 16, scale = 0)
    private Long initialAmount;

    @Column(name = "bonus_amount", precision = 16, scale = 0)
    private Long bonusAmount;

    @Column(name = "locked_amount", precision = 16, scale = 0)
    private Long lockedAmount;

    @Column(name = "account_id_bank", length = 28)
    private String accountIdBank;

    @Column(name = "add_info", length = 999)
    private String addInfo;

    @Column(name = "credit_limit", precision = 16, scale = 0)
    private Long creditLimit;

    @Column(name = "lock_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockTime;

    @Column(name = "lock_amount_cms", precision = 16, scale = 0)
    private Long lockAmountCms;

    @Column(name = "amount_set_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date amountSetTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "account_ccy", nullable = false, referencedColumnName = "ccy_num")
    private CurrencyCode currencyCode;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "centre_id", insertable = false, updatable = false, referencedColumnName = "centre_id"),
            @JoinColumn(name = "crd_holdr_id", insertable = false, updatable = false, referencedColumnName = "crd_holdr_id")
    })
    private StipClient stipClient;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "stipAccount", fetch = FetchType.LAZY)
    private Set<StipRestracnt> stipRestracnts;

}

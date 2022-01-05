package lv.bank.cards.core.entity.rtps;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "RTPSADMIN.stip_locks")
public class StipLocks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "row_numb", nullable = false)
    private Long rowNumb;

    @Column(name = "request_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    @Column(name = "amount", precision = 12, scale = 0)
    private Long amount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "centre_id", insertable = false, updatable = false, referencedColumnName = "centre_id"),
            @JoinColumn(name = "account_id", insertable = false, updatable = false, referencedColumnName = "account_id")
    })
    private StipAccount stipAccount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "row_numb", insertable = false, updatable = false)
    private StipLocksMatch stipLocksMatch;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "ccy")
    private CurrencyCode currencyCodeByCcy;

}

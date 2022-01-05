package lv.bank.cards.core.entity.linkApp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "pcd_accounts")
public class PcdAccount implements Serializable {

    private static final long serialVersionUID = 1690132993994839557L;

    @Id
    @Column(name = "account_no", nullable = false)
    private java.math.BigDecimal accountNo;

    @Column(name = "begin_bal", nullable = false)
    private long beginBal;

    @Column(name = "end_bal", nullable = false)
    private long endBal;

    @Column(name = "card_acct", nullable = false, length = 34)
    private String cardAcct;

    @Column(name = "client", nullable = false, length = 8)
    private String client;

    @Column(name = "bank_c", nullable = false, length = 2)
    private String bankC;

    @Column(name = "iban", length = 25)
    private String iban;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "account_no", nullable = false)
    private PcdAccParam pcdAccParam;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "client", insertable = false, updatable = false, referencedColumnName = "client"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private PcdClient pcdClient;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pcd_cards_accounts",
            joinColumns = {@JoinColumn(name = "account_no")},
            inverseJoinColumns = {@JoinColumn(name = "card")})
    private List<PcdCard> pcdCards;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdAccount", fetch = FetchType.LAZY)
    private List<PcdSlip> pcdSlips;

}

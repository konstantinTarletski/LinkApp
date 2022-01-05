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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_accounts")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQueries({
        @NamedQuery(name = "getAccountByClientAndExternalId",
                query = "SELECT a from IzdAccount as a where a.cardAcct=:externalId and a.client=:client")
})
public class IzdAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "account_no", nullable = false)
    private BigDecimal accountNo;

    @Column(name = "client", length = 8, nullable = false)
    private String client;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "groupc", length = 2, nullable = false)
    private String groupC;

    @Column(name = "begin_bal", length = 2, nullable = false)
    private long beginBal;

    @Column(name = "end_bal", length = 2, nullable = false)
    private long endBal;

    @Column(name = "avail_amt", length = 2, nullable = false)
    private long availAmt;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "card_acct", length = 34, nullable = false)
    private String cardAcct;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "account_no", nullable = false, insertable = false, updatable = false)
    private IzdAccParam izdAccParam;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdAccount", fetch = FetchType.LAZY)
    private Set<IzdClAcct> izdClAccts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdAccount", fetch = FetchType.LAZY)
    private Set<IzdAccntAc> izdAccntAcs;

}

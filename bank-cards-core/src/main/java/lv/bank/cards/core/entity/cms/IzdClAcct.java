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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "izd_cl_acct")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdClAcct.to.lv.bank.cards.core.entity.linkApp.PcdClAcct",
        query = "select c from IzdClAcct c where c.ctime between :lastWaterMark and :currentWaterMark")
public class IzdClAcct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "tab_key", nullable = false)
    private BigDecimal tabKey;

    @Column(name = "acc_prty", length = 1, nullable = false)
    private String accPrty;

    @Column(name = "client", length = 8, nullable = false)
    private String client;

    @Column(name = "card_acct", length = 20)
    private String cardAcct;

    @Column(name = "tranz_acct", length = 20)
    private String tranzAcct;

    @Column(name = "baccount_no")
    private BigDecimal baccountNo;

    @Column(name = "c_accnt_type", length = 2)
    private String CAccntType;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "b_br_id")
    private Integer BBrId;

    @Column(name = "parent_account_no")
    private BigDecimal parentAccountNo;

    @Column(name = "parent_tranz_acct", length = 20)
    private String parentTranzAcct;

    @Column(name = "parent_bank_c", length = 2)
    private String parentBankC;

    @Column(name = "parent_groupc", length = 2)
    private String parentGroupc;

    @Column(name = "iban", length = 21)
    private String iban;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ccy", insertable = false, updatable = false, referencedColumnName = "ccy"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "groupc", insertable = false, updatable = false, referencedColumnName = "groupc")
    })
    private IzdCardGroupCcy izdCardGroupCcy;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "account_no", referencedColumnName = "account_no")
    private IzdAccount izdAccount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "main_row", referencedColumnName = "tab_key")
    private IzdClAcct izdClAcct;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdClAcct", fetch = FetchType.LAZY)
    private Set<IzdClAcct> izdClAccts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdClAcct", fetch = FetchType.LAZY)
    private Set<IzdCard> izdCards;

}

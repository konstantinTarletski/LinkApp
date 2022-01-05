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
@Table(name = "pcd_agreements")
public class PcdAgreement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "AGREEMENT", nullable = false)
    private Long agreement;

    @Column(name = "CTIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "GROUPC", nullable = false, length = 2)
    private String groupc;

    @Column(name = "STREET", length = 95)
    private String street;

    @Column(name = "CITY", length = 260)
    private String city;

    @Column(name = "COUNTRY", length = 3)
    private String country;

    @Column(name = "POST_IND", length = 15)
    private String postInd;

    @Column(name = "BRANCH", nullable = false, length = 3)
    private String branch;

    @Column(name = "CLIENT", length = 8)
    private String client;

    @Column(name = "BANK_C", length = 2)
    private String bankC;

    @Column(name = "REP_LANG", length = 1)
    private String repLang;

    @Column(name = "DISTRIB_MODE", length = 2)
    private String distribMode;

    @Column(name = "CONTRACT", length = 15)
    private String contract;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "BRANCH", insertable = false, updatable = false, referencedColumnName = "branch"),
            @JoinColumn(name = "BANK_C", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private PcdBranch pcdBranch;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "GROUPC", insertable = false, updatable = false, referencedColumnName = "groupc"),
            @JoinColumn(name = "BANK_C", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private PcdCardGroup pcdCardGroup;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "REP_LANG", insertable = false, updatable = false, referencedColumnName = "lan_code"),
            @JoinColumn(name = "BANK_C", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private PcdRepLang pcdRepLang;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "DISTRIB_MODE", insertable = false, updatable = false, referencedColumnName = "code"),
            @JoinColumn(name = "BANK_C", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private PcdRepDistribut pcdRepDistribut;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "CLIENT", insertable = false, updatable = false, referencedColumnName = "client"),
            @JoinColumn(name = "BANK_C", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private PcdClient pcdClient;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdAgreement", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<PcdCard> pcdCards;

}

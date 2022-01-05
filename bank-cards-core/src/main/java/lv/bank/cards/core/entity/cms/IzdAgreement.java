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
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_agreement")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdAgreement.to.lv.bank.cards.core.entity.linkApp.PcdAgreement",
        query = "select a from IzdAgreement a where a.ctime between :lastWaterMark and :currentWaterMark")
public class IzdAgreement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "agre_nom", nullable = false)
    private Long agreNom;

    @Column(name = "enrolled", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date enrolled;

    @Column(name = "contract", length = 15)
    private String contract;

    @Column(name = "street", length = 95)
    private String street;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    @Column(name = "city", length = 20)
    private String city;

    @Column(name = "country", length = 3)
    private String country;

    @Column(name = "post_ind", length = 7)
    private String postInd;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "coment", length = 200)
    private String coment;

    @Column(name = "e_mails", length = 100)
    private String EMails;

    @Column(name = "u_field3", length = 20)
    private String UField3;

    @Column(name = "u_field4", length = 20)
    private String UField4;

    @Column(name = "in_file_num")
    private Integer inFileNum;

    @Column(name = "b_br_id")
    private Integer BBrId;

    @Column(name = "parent_agre_nom")
    private Long parentAgreNom;

    @Column(name = "parent_client", length = 8)
    private String parentClient;

    @Column(name = "parent_bank_c", length = 2)
    private String parentBankC;

    @Column(name = "parent_groupc", length = 2)
    private String parentGroupc;

    @Column(name = "bincod", length = 9)
    private String binCod;

    @Column(name = "rep_lang", length = 9)
    private String repLang;

    @Column(name = "distrib_mode", length = 2)
    private String distribMode;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "client", insertable = false, updatable = false, referencedColumnName = "client"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private IzdClient izdClient;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "bank_code", insertable = false, updatable = false, referencedColumnName = "bank_code"),
            @JoinColumn(name = "branch", insertable = false, updatable = false, referencedColumnName = "branch")
    })
    private IzdBranch izdBranch;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "rep_lang", insertable = false, updatable = false, referencedColumnName = "lan_code"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private IzdRepLang izdRepLang;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "distrib_mode", insertable = false, updatable = false, referencedColumnName = "code"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private IzdRepDistribut izdRepDistribut;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "product", insertable = false, updatable = false, referencedColumnName = "code"),
            @JoinColumn(name = "groupc", insertable = false, updatable = false, referencedColumnName = "groupc"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private IzdOfferedProduct izdOfferedProduct;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdAgreement", fetch = FetchType.LAZY)
    private Set<IzdCard> izdCards;

}

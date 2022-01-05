package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "izd_offered_products")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
public class IzdOfferedProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdOfferedProductPK comp_id;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "bin", length = 9)
    private String bin;

    @Column(name = "auth_level", length = 1)
    private String authLevel;

    @Column(name = "risk_level", length = 5)
    private String riskLevel;

    @Column(name = "cond_accnt", length = 3)
    private String condAccnt;

    @Column(name = "cond_card", length = 3)
    private String condCard;

    @Column(name = "card_type", length = 2)
    private String cardType;

    @Column(name = "cln_cat", length = 3)
    private String clnCat;

    @Column(name = "branch")
    private Integer branch;

    @Column(name = "cl_type", length = 1)
    private String clType;

    @Column(name = "company", length = 5)
    private String company;

    @Column(name = "rep_distribution", length = 2)
    private String repDistribution;

    @Column(name = "def_credit")
    private Long defCredit;

    @Column(name = "def_non_reduce")
    private Long defNonReduce;

    @Column(name = "min_credit")
    private Long minCredit;

    @Column(name = "min_non_reduce")
    private Long minNonReduce;

    @Column(name = "max_credit")
    private Long maxCredit;

    @Column(name = "max_non_reduce")
    private Long maxNonReduce;

    @Column(name = "loy_scheme", length = 9)
    private String loyScheme;

    @Column(name = "t_accnt_sch")
    private Long TAccntSch;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "chip_app_id")
    private Long chipAppId;

    @Column(name = "chip_design_id")
    private Long chipDesignId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdOfferedProduct", fetch = FetchType.LAZY)
    private Set<IzdValidProductCcy> izdValidProductCcies;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdOfferedProduct", fetch = FetchType.LAZY)
    private Set<IzdAgreement> izdAgreements;

}

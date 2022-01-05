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
import javax.persistence.NamedQueries;
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
@Table(name = "izd_branches")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQueries({
        @NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdBranch.to.lv.bank.cards.core.entity.linkApp.PcdBranch",
                query = "select b from IzdBranch b where b.ctime between :lastWaterMark and :currentWaterMark")
})
public class IzdBranch implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * identifier field
     */
    @EmbeddedId
    private IzdBranchPK comp_id;

    @Column(name = "branch_name", length = 45, nullable = false)
    private String branchName;

    @Column(name = "post_ind", length = 7)
    private String postInd;

    @Column(name = "city", length = 35)
    private String city;

    @Column(name = "street", length = 60)
    private String street;

    @Column(name = "mfo", length = 12)
    private String mfo;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "reg_kods_ur", length = 30)
    private String regKodsUr;

    @Column(name = "reg_kods_vid", length = 30)
    private String regKodsVid;

    @Column(name = "b_br_id", length = 7, nullable = false, unique = true)
    private int BBrId;

    @Column(name = "e_mails", length = 100)
    private String EMails;

    @Column(name = "in_path", length = 256)
    private String inPath;

    @Column(name = "out_path", length = 256)
    private String outPath;

    @Column(name = "fax_no", length = 15)
    private String faxNo;

    @Column(name = "encrypt_key", length = 40)
    private String encryptKey;

    @Column(name = "cor_prefix", length = 10)
    private String corPrefix;

    @Column(name = "xml_branch", length = 10)
    private String xmlBranch;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdBranch", fetch = FetchType.LAZY)
    private Set<NordlbBranch> nordlbBranches;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdBranch", fetch = FetchType.LAZY)
    private Set<IzdAgreement> izdAgreements;

}

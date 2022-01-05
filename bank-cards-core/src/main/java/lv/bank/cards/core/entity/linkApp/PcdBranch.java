package lv.bank.cards.core.entity.linkApp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
@Table(name = "pcd_branches")
public class PcdBranch implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdBranchPK comp_id;

    @Column(name = "branch_name", length = 45)
    private String branchName;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "b_br_id", length = 7)
    private Integer BBrId;

    @Column(name = "reg_kods_vid", length = 30)
    private String regKodsVid;

    @Column(name = "reg_kods_ur", length = 30)
    private String regKodsUr;

    @Column(name = "external_id", length = 10)
    private String externalId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "bank_c", insertable = false, updatable = false)
    private PcdDbOwner pcdDbOwner;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdBranch", fetch = FetchType.LAZY)
    private Set<PcdAgreement> pcdAgreements;

}

package lv.bank.cards.core.entity.linkApp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "pcd_db_owners")
public class PcdDbOwner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "sh_name", length = 25, nullable = false)
    private String shName;

    @Column(name = "f_name", length = 40)
    private String FName;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdDbOwner", fetch = FetchType.LAZY)
    private Set<PcdCompany> pcdCompanies;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdDbOwner", fetch = FetchType.LAZY)
    private Set<PcdBranch> pcdBranches;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdDbOwner", fetch = FetchType.LAZY)
    private Set<PcdCardGroup> pcdCardGroups;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdDbOwner", fetch = FetchType.LAZY)
    private Set<PcdClient> pcdClients;

}

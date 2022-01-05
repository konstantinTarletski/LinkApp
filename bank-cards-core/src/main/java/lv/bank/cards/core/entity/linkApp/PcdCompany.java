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
@Table(name = "pcd_companies")
public class PcdCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdCompanyPK comp_id;

    @Column(name = "short_name", length = 50)
    private String shortName;

    @Column(name = "name", length = 140)
    private String name;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "okp_code", length = 15)
    private String okpCode;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "bank_c", insertable = false, updatable = false)
    private PcdDbOwner pcdDbOwner;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdCompany", fetch = FetchType.LAZY)
    private Set<PcdClient> pcdClients;

}

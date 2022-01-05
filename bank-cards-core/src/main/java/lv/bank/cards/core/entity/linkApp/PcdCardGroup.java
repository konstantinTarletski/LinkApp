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
@Table(name = "pcd_card_groups")
public class PcdCardGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdCardGroupPK comp_id;

    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "prev_qfile_name", length = 20)
    private String prevQfileName;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "bank_c", updatable = false, insertable = false)
    private PcdDbOwner pcdDbOwner;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdCardGroup", fetch = FetchType.LAZY)
    private Set<PcdAgreement> pcdAgreements;

}

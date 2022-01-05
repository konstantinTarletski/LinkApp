package lv.bank.cards.core.entity.rtps;

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
@Table(name = "RTPSADMIN.stip_clients")
public class StipClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StipClientPK comp_id;

    @Column(name = "effective_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name = "purge_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purgeDate;

    @Column(name = "crd_holdr_pwd", length = 20)
    private String crdHoldrPwd;

    @Column(name = "crd_holdr_msg", length = 99)
    private String crdHoldrMsg;

    @Column(name = "param_grp_1", length = 5)
    private String paramGrp1;

    @Column(name = "param_grp_2", length = 5)
    private String paramGrp2;

    @Column(name = "acnt_change", length = 1)
    private String acntChange;

    @Column(name = "crd_holdr_name", length = 55)
    private String crdHoldrName;

    @Column(name = "comm_grp", length = 3)
    private String commGrp;

    @Column(name = "add_info", length = 999)
    private String addInfo;

    @Column(name = "parent_crd_holdr_id", length = 19)
    private String parentCrdHoldrId;

    @Column(name = "parent_centre_id", length = 11)
    private String parentCentreId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "centre_id", insertable = false, updatable = false)
    private ProcessingEntity processingEntity;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "stipClient", fetch = FetchType.LAZY)
    private Set<StipCard> stipCards;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "stipClient", fetch = FetchType.LAZY)
    private Set<StipAccount> stipAccounts;

}

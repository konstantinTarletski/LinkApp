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
import javax.persistence.JoinColumn;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "izd_card_groups")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdCardGroup.to.lv.bank.cards.core.entity.linkApp.PcdCardGroup",
        query = "select c from IzdCardGroup c where c.ctime between :lastWaterMark and :currentWaterMark")
public class IzdCardGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdCardGroupPK comp_id;

    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
    private IzdDbOwner izdDbOwner;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdCardGroup", fetch = FetchType.LAZY)
    private Set<IzdCardGroupCcy> izdCardGroupCcies;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdCardGroup", fetch = FetchType.LAZY)
    private Set<NordlbFileId> nordlbFileIds;

}

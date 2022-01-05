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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "izd_card_group_ccy")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
public class IzdCardGroupCcy implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdCardGroupCcyPK comp_id;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "equivalent", length = 1)
    private String equivalent;

    @Column(name = "equivalent1", length = 1)
    private String equivalent1;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "groupc", insertable = false, updatable = false, referencedColumnName = "groupc")
    })
    private IzdCardGroup izdCardGroup;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdCardGroupCcy", fetch = FetchType.LAZY)
    private Set<IzdClAcct> izdClAccts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdCardGroupCcy", fetch = FetchType.LAZY)
    private Set<IzdAccParam> izdAccParams;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdCardGroupCcy", fetch = FetchType.LAZY)
    private Set<IzdCondCard> izdCondCards;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdCardGroupCcy", fetch = FetchType.LAZY)
    private Set<IzdCondAccnt> izdCondAccnts;

}

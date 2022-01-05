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
@Table(name = "izd_stop_causes")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdStopCause.to.lv.bank.cards.core.entity.linkApp.PcdStopCause",
        query = "select s from IzdStopCause s where s.ctime between :lastWaterMark and :currentWaterMark")
public class IzdStopCause implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdStopCausePK comp_id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "status_code", length = 3, nullable = false)
    private String statusCode;

    @Column(name = "name_by_iso", length = 70)
    private String nameByIso;

    @Column(name = "arcsys_code", length = 2)
    private String arcsysCode;

    @Column(name = "arcsys_int")
    private Integer arcsysInt;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdStopCause", fetch = FetchType.LAZY)
    private Set<IzdStoplist> izdStoplists;

}

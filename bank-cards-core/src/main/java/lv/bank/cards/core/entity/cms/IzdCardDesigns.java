package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "izd_card_designs")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdCardDesigns.to.lv.bank.cards.core.entity.linkApp.PcdCardDesigns",
        query = "select c from IzdCardDesigns c where c.ctime between :lastWaterMark and :currentWaterMark")
public class IzdCardDesigns implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdCardDesignsPK comp_id;

    @Column(name = "design_type", length = 2, nullable = false)
    private String designType;

    @Column(name = "design_type_name", length = 20, nullable = false)
    private String designTypeName;

    @Column(name = "notes", length = 240)
    private String notes;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "active", length = 1, nullable = false)
    private String active;

}

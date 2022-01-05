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
@Table(name = "izd_rep_lang")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdRepLang.to.lv.bank.cards.core.entity.linkApp.PcdRepLang",
        query = "select r from IzdRepLang r where r.ctime between :lastWaterMark and :currentWaterMark")
public class IzdRepLang implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdRepLangPK comp_id;

    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdRepLang", fetch = FetchType.LAZY)
    private Set<IzdAgreement> izdAgreements;


}

package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_ccy_table")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdCcyTable.to.lv.bank.cards.core.entity.linkApp.PcdCurrency",
        query = "select c from IzdCcyTable c where c.ctime between :lastWaterMark and :currentWaterMark")
public class IzdCcyTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ccy", length = 3, nullable = false)
    private String ccy;

    @Column(name = "ccy_code", length = 3)
    private String ccyCode;

    @Column(name = "exp", length = 1, nullable = false)
    private String exp;

    @Column(name = "ccy_name", length = 27)
    private String ccyName;

    @Column(name = "abvr_name", length = 27)
    private String abvrName;

    @Column(name = "change", length = 27)
    private String change;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

}

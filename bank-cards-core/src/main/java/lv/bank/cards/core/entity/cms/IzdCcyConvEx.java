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
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_ccy_conv_ex")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdCcyConvEx.to.lv.bank.cards.core.entity.linkApp.PcdCcyConv",
        query = "select c from IzdCcyConvEx c where c.ctime between :lastWaterMark and :currentWaterMark and c.id.curency='EUR' and c.id.procCcy in('USD','LVL') and c.id.buyOrSell='SELL' and c.id.curency<>c.id.procCcy")
public class IzdCcyConvEx implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdCcyConvExPK id;

    @Column(name = "conv_rate", precision = 17, scale = 9)
    private Double convRate;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "conv_rate2", precision = 17, scale = 9)
    private Double convRate2;

    @Column(name = "conv_rate_abs", precision = 16, scale = 9)
    private Double convRateAbs;

    @Column(name = "from_reconcile", length = 2)
    private String fromReconcile;

}

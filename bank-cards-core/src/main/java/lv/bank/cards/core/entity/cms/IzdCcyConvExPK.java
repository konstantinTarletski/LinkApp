package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IzdCcyConvExPK implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "curency", length = 3, nullable = false)
    private String curency;

    @Column(name = "proc_ccy", length = 3, nullable = false)
    private String procCcy;

    @Column(name = "date_exp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExp;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "from_reconcil", length = 2, nullable = false)
    private String fromReconcil;

    @Column(name = "buy_or_sell", length = 4, nullable = false)
    private String buyOrSell;

}

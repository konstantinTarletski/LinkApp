package lv.bank.cards.core.entity.linkApp;

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
public class PcdCcyConvPK implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "curency", length = 3)
    private String curency;

    @Column(name = "proc_ccy", length = 3)
    private String procCcy;

    @Column(name = "date_exp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExp;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "from_reconcil", length = 2)
    private String fromReconcil;

    @Column(name = "buy_or_sell", length = 4)
    private String buyOrSell;

}

package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IzdAccntAcPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "account_no", nullable = false)
    private BigDecimal accountNo;

    @Column(name = "proc_id", nullable = false, length = 14)
    private long procId;

}

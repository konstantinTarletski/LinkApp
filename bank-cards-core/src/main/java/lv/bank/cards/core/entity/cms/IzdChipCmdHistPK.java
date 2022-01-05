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
public class IzdChipCmdHistPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "HIST_INTERNAL_NO", nullable = false)
    private BigDecimal histInternalNo;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

}

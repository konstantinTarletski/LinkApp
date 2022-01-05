package lv.bank.cards.core.entity.linkApp;

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
public class PcdCardsAccountPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "card", length = 16, nullable = false)
    private String card;

    @Column(name = "account_no", nullable = false)
    private BigDecimal accountNo;

}

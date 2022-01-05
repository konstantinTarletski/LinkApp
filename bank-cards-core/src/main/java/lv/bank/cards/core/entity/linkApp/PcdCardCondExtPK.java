package lv.bank.cards.core.entity.linkApp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PcdCardCondExtPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cond_set", length = 3)
    private String condSet;

    @Column(name = "ccy", length = 3)
    private String ccy;

    @Column(name = "card_type", length = 2)
    private String cardType;

    private PcdCardGroupPK cardGroup;

}

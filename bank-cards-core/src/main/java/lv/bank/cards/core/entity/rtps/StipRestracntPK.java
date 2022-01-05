package lv.bank.cards.core.entity.rtps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StipRestracntPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "centre_id", length = 11, nullable = false)
    private String centreId;

    @Column(name = "account_id", length = 28, nullable = false)
    private String accountId;

    @Column(name = "card_number", length = 19, nullable = false)
    private String cardNumber;

}

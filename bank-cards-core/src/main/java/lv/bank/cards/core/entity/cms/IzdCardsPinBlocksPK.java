package lv.bank.cards.core.entity.cms;

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
public class IzdCardsPinBlocksPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "card", length = 19, nullable = false)
    private String card;

    @Column(name = "card_seq", length = 32, nullable = false)
    private String cardSeq;

}

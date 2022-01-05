package lv.bank.cards.core.entity.linkApp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PcdCondAccntPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    @Column(name = "cond_set", length = 3, nullable = false)
    private String condSet;

    @Column(name = "ccy", length = 3, nullable = false)
    private String ccy;
}

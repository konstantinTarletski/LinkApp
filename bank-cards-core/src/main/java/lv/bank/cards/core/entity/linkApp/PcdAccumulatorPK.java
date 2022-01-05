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
public class PcdAccumulatorPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "accum_id", nullable = false)
    private Long accumId;

    @Column(name = "centre_id", nullable = false, length = 11)
    private String centreId;

}

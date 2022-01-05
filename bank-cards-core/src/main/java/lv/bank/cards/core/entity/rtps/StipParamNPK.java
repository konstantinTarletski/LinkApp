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
public class StipParamNPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "param_grp", nullable = false, length = 5)
    private String paramGrp;

    @Column(name = "centre_id", nullable = false, length = 11)
    private String centreId;

}

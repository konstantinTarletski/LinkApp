package lv.bank.cards.core.entity.rtps;

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
public class StipClientPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "centre_id", length = 11, nullable = false)
    private String centreId;

    @Column(name = "crd_holdr_id", length = 19, nullable = false)
    private String crdHoldrId;

}

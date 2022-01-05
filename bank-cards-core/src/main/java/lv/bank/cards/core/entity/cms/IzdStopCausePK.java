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
public class IzdStopCausePK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cause", length = 1, nullable = false)
    private String cause;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

}

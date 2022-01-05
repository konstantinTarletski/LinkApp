package lv.bank.cards.core.entity.linkApp;

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
public class PcdRepDistributPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "code", length = 2, nullable = false)
    private String code;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

}

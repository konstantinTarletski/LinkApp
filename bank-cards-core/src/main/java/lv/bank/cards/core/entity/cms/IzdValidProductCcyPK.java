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
public class IzdValidProductCcyPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "product", length = 10, nullable = false)
    private String product;

    @Column(name = "ccy", length = 3, nullable = false)
    private String ccy;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

}

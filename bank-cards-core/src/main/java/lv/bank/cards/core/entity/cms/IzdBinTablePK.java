package lv.bank.cards.core.entity.cms;

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
public class IzdBinTablePK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "bin_code", length = 9, nullable = false)
    private String binCode;

    @Column(name = "card_group_code", length = 2, nullable = false)
    private String cardGroupCode;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

}

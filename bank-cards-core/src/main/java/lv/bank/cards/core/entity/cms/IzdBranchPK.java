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
public class IzdBranchPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "bank_code", length = 2, nullable = false)
    private String bankCode;

    @Column(name = "branch", length = 5, nullable = false)
    private String branch;

}

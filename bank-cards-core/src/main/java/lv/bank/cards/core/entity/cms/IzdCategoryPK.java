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
public class IzdCategoryPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cat_code", length = 3, nullable = false)
    private String catCode;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

}

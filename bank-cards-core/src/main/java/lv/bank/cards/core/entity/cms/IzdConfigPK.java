package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IzdConfigPK implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    @Column(name = "param_numb", nullable = false, precision = 8, scale = 0)
    private Integer paramNumb;

}

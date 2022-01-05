package lv.bank.cards.core.entity.rtps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "RTPSADMIN.currency_codes")
public class CurrencyCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ccy_num", nullable = false)
    private String ccyNum;

    @Column(name = "ccy_alpha", length = 3, nullable = false, unique = true)
    private String ccyAlpha;

    @Column(name = "exp_dot", precision = 1, scale = 0)
    private Integer expDot;

    @Column(name = "description", length = 255)
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "currencyCode", fetch = FetchType.LAZY)
    private Set<StipAccount> stipAccounts;

}

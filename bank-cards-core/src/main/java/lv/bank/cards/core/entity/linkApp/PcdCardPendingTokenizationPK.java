package lv.bank.cards.core.entity.linkApp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PcdCardPendingTokenizationPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "card", length = 19, nullable = false)
    private String card;

    @Column(name = "wallet_device_id", length = 19, nullable = false)
    private String walletDeviceId;

}

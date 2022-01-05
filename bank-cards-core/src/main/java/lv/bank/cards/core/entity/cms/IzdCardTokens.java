package lv.bank.cards.core.entity.cms;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_card_tokens")
public class IzdCardTokens {

    @Id
    @Column(name = "token_pan", nullable = false, length = 19)
    private String tokenPan;

    @Column(name = "card", nullable = false)
    private String card;

    @Column(name = "secure_id", nullable = false)
    private String secureId;

    @Column(name = "token_status", nullable = false)
    private Integer tokenStatus;

    @Column(name = "token_ref_id", length = 64)
    private String tokenRefId;

    @Column(name = "requestor_id", length = 11)
    private String requestorId;

    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "token_type")
    private String tokenType;

}

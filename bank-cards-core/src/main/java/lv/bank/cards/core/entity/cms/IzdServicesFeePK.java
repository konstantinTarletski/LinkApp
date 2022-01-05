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
public class IzdServicesFeePK implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "service_type", length = 16, nullable = false)
    private String serviceType;

    @Column(name = "acnt_ccy", length = 3, nullable = false)
    private String acntCcy;

    @Column(name = "comm_grp", length = 12, nullable = false)
    private String commGrp;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "event_area", length = 10, nullable = false)
    private String eventArea;

}

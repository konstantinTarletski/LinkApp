package lv.bank.cards.core.entity.linkApp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PcdServicesFeePK implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "service_type", length = 16)
    private String serviceType;

    @Column(name = "acnt_ccy", length = 3)
    private String acntCcy;

    @Column(name = "comm_grp", length = 12)
    private String commGrp;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "event_area", length = 10)
    private String eventArea;

}

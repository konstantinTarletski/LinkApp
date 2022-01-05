package lv.bank.cards.core.entity.linkApp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "pcd_auth_source")
public class PcdAuthSource implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(nullable = false, length = 75)
    private String source;

    @Column(length = 11)
    private String cmi;

    @Column(name = "terminal_id", nullable = false, length = 24)
    private String terminalId;

    @Column(name = "merchant_id", length = 45)
    private String merchantId;

    @Column(length = 3)
    private String currency;

    @Column(name = "FLD_029")
    private int fld029;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdAuthSource")
    private Set<PcdAuthBatch> pcdAuthBatchs = new HashSet<PcdAuthBatch>();

    public void incrementFld029() {
        fld029++;
        if(fld029 > 999) {
            fld029 = 1;
        }
    }

    public String getFld29AsString() {
        return Integer.toString(fld029);
    }

}

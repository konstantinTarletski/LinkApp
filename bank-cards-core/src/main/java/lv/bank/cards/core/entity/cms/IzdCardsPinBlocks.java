package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_cards_pin_blocks")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
public class IzdCardsPinBlocks implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdCardsPinBlocksPK compId;

    @Column(name = "pin_block", length = 32, nullable = false)
    private String pinBlock;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    public IzdCardsPinBlocks(IzdCardsPinBlocksPK compId, String pinBlock) {
        this.compId = compId;
        this.pinBlock = pinBlock;
    }

}

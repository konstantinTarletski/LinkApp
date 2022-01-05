package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "izd_chip_cmd_hist")
public class IzdChipCmdHist implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdChipCmdHistPK id;

    @Column(name = "card", length = 19, nullable = false)
    private String card;

    @Column(name = "data", length = 512)
    private String data;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "groupc", length = 2, nullable = false)
    private String groupC;

}

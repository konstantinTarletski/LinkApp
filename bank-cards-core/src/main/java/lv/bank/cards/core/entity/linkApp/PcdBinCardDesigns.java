package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "PCD_BIN_CARD_DESIGNS")
public class PcdBinCardDesigns implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdBinCardDesignsPK comp_id;

    @Column(name = "CTIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

}

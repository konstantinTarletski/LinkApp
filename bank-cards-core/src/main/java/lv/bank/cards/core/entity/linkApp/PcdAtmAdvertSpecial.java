package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "pcd_atm_adverts_special")
public class PcdAtmAdvertSpecial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_card", nullable = false)
    private String idCard;

    @Column(name = "advert_id", nullable = false, length = 100)
    private String advertId;

    @Column(name = "rec_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recDate;

    @Column(name = "term_id", nullable = false, length = 2000)
    private String termId;

    @Column(name = "answer", length = 20)
    private String answer;

    @Column(name = "message", length = 100)
    private String message;

}

package lv.bank.cards.core.entity.linkApp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PcdAtmAdvertPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_card", nullable = false, length = 16)
    private String idCard;

    @Column(name = "advert_id", nullable = false, length = 100)
    private String advertId;

    @Column(name = "rec_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date recDate;

}

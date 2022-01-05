package lv.bank.cards.core.entity.linkApp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pcd_atm_adverts")
public class PcdAtmAdvert implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdAtmAdvertPk atmAdvertPk;

    @Column(name = "term_id", nullable = false, length = 8)
    private String termId;

    @Column(name = "answer", length = 20)
    private String answer;

    @Column(name = "message", length = 100)
    private String message;

}

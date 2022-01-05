package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "pcd_cards_accounts")
public class PcdCardsAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdCardsAccountPK comp_id;

}

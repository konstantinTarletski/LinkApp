package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "pcd_card_cond_ext")
public class PcdCardCondExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdCardCondExtPK comp_id;

    @Column(name = "notif_fee", nullable = false)
    private long notifFee;

}

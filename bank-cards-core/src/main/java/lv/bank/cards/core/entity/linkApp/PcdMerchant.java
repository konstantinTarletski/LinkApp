package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "pcd_merchants")
public class PcdMerchant implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "merchant", length = 15)
    private String merchant;

    @Column(name = "parent", length = 15)
    private String parent;

    @Column(name = "reg_nr", length = 25)
    private String regNr;

}

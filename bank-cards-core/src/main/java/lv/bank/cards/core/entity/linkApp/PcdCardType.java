package lv.bank.cards.core.entity.linkApp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "pcd_card_types")
public class PcdCardType implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdCardTypePK comp_id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "groupc", insertable = false, updatable = false, referencedColumnName = "groupc"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private PcdCardGroup pcdCardGroup;

}

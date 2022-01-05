package lv.bank.cards.core.entity.linkApp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "pcd_bins")
public class PcdBin implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdBinPK comp_id;

    @Column(name = "card_name", length = 60, nullable = false)
    private String cardName;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "service_code", length = 3)
    private String serviceCode;

    @Column(name = "special_card_name", length = 60)
    private String specialCardName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "groupc", insertable = false, updatable = false, referencedColumnName = "groupc"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private PcdCardGroup pcdCardGroup;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdBin", fetch = FetchType.LAZY)
    private Set<PcdCard> pcdCards;

}

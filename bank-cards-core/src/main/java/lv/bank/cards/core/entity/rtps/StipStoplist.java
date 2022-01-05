package lv.bank.cards.core.entity.rtps;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "RTPSADMIN.stip_stoplist")
public class StipStoplist implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StipStoplistPK comp_id;

    @Column(name = "effective_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name = "purge_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purgeDate;

    @Column(name = "description", length = 255)
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "centre_id", insertable = false, updatable = false)
    private ProcessingEntity processingEntity;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "action_code", nullable = false)
    private AnswerCode answerCode;

    // Problem to join cards because old cards are removed form stip_cards table but not from stip_stoplist
    //@EqualsAndHashCode.Exclude
//    @ManyToOne
//    @JoinColumns({
//    	@JoinColumn(name = "centre_id", insertable = false, updatable = false, referencedColumnName = "centre_id"),
//    	@JoinColumn(name = "card_number", insertable = false, updatable = false, referencedColumnName = "card_number")
//    })
//    private StipCard stipCard;

}

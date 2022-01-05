package lv.bank.cards.core.entity.rtps;

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
@Table(name = "RTPSADMIN.stip_restracnt")
public class StipRestracnt implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StipRestracntPK comp_id;

    @Column(name = "effective_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name = "purge_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purgeDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "centre_id", insertable = false, updatable = false, referencedColumnName = "centre_id"),
            @JoinColumn(name = "crd_holdr_id", insertable = false, updatable = false, referencedColumnName = "crd_holdr_id")
    })
    private StipClient stipClient;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "centre_id", insertable = false, updatable = false, referencedColumnName = "centre_id"),
            @JoinColumn(name = "account_id", insertable = false, updatable = false, referencedColumnName = "account_id")
    })
    private StipAccount stipAccount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "centre_id", insertable = false, updatable = false, referencedColumnName = "centre_id"),
            @JoinColumn(name = "card_number", insertable = false, updatable = false, referencedColumnName = "card_number")
    })
    private StipCard stipCard;


}

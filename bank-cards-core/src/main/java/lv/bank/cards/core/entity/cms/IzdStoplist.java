package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "izd_stoplist")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
public class IzdStoplist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "card", length = 19, nullable = false)
    private String card;

    @Column(name = "rec_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date recDate;

    @Column(name = "del_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date delDate;

    @Column(name = "text", length = 30)
    private String text;

    @Column(name = "expirity", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirity;

    @Column(name = "proc_id", nullable = false)
    private long procId;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    @Column(name = "calc_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date calcDate;

    @Column(name = "reg_number", length = 2)
    private String regNumber;

    @Column(name = "lock_fee_flag", length = 1, nullable = false)
    private String lockFeeFlag;

    @Column(name = "arcsys_code", length = 2)
    private String arcsysCode;

    @Column(name = "arcsys_region", length = 2)
    private String arcsysRegion;

    @Column(name = "arcsys_list", length = 1)
    private String arcsysList;

    @Column(name = "arcsys_period", length = 12)
    private Long arcsysPeriod;

    @Column(name = "arcsys_text", length = 100)
    private String arcsysText;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "card", insertable = false, updatable = false)
    private IzdCard izdCard;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "cause", insertable = false, updatable = false, referencedColumnName = "cause")
    })
    private IzdStopCause izdStopCause;

}

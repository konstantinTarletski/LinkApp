package lv.bank.cards.core.entity.rtps;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "RTPSADMIN.stip_locks_match")
public class StipLocksMatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "row_numb", nullable = false, precision = 11, scale = 0)
    private Long rowNumb;

    @Column(name = "request_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    @Column(name = "unlock_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date unlockDate;

    @Column(name = "msg_type", length = 4)
    private String msgType;

    @Column(name = "fld_002", length = 19)
    private String fld002;

    @Column(name = "fld_003", length = 6)
    private String fld003;

    @Column(name = "fld_004", precision = 12, scale = 0)
    private Long fld004;

    @Column(name = "fld_006", precision = 12, scale = 0)
    private Long fld006;

    @Column(name = "fld_011", length = 6)
    private String fld011;

    @Column(name = "fld_012")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fld012;

    @Column(name = "fld_022", length = 12)
    private String fld022;

    @Column(name = "fld_024", length = 3)
    private String fld024;

    @Column(name = "fld_026", length = 4)
    private String fld026;

    @Column(name = "fld_032", length = 11)
    private String fld032;

    @Column(name = "fld_037", length = 12)
    private String fld037;

    @Column(name = "fld_038", length = 6)
    private String fld038;

    @Column(name = "fld_041", length = 8)
    private String fld041;

    @Column(name = "fld_042", length = 15)
    private String fld042;

    @Column(name = "fld_043", length = 99)
    private String fld043;

    @Column(name = "fld_046", length = 204)
    private String fld046;

    @Column(name = "fld_049", length = 3)
    private String fld049;

    @Column(name = "fld_051", length = 3)
    private String fld051;

    @Column(name = "child_row", precision = 11, scale = 0)
    private Long childRow;

    @Column(name = "reverse_flag", length = 1)
    private String reverseFlag;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "centre_id", nullable = false)
    private ProcessingEntity processingEntity;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "fld_049", insertable = false, updatable = false)
    private CurrencyCode currencyCodeByFld049;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "fld_051", insertable = false, updatable = false)
    private CurrencyCode currencyCodeByFld051;

}

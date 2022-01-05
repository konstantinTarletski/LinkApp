package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_locks")
@NamedQuery(name = "get.all.items.lv.bank.cards.core.entity.cms.IzdLock.to.lv.bank.cards.core.entity.linkApp.PcdLock",
        query = "select l from IzdLock l where l.lockingFlag=1")
public class IzdLock implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "row_numb", nullable = false, precision = 11, scale = 0)
    private Long rowNumb;

    /**
     * Card group code - local for each bank
     */
    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    /**
     * Bank - row owner code
     */
    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    /**
     * Insert or last update date, time
     */
    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    /**
     * Account internal identifier
     */
    @Column(name = "account_no", nullable = false, precision = 20, scale = 0)
    private BigDecimal accountNo;

    /**
     * Locks sending from RTPS to CMS date and time
     */
    @Column(name = "lock_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockTime;

    /**
     * Unlocking date
     */
    @Column(name = "unlock_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date unlockDate;

    /**
     * Record request date and time
     */
    @Column(name = "request_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    /**
     * Unlocking reason
     */
    @Column(name = "unlocking_reason", length = 1)
    private String unlockingReason;

    /**
     * Request message type
     */
    @Column(name = "msg_type", length = 4, nullable = false)
    private String msgType;

    /**
     * Processing code
     */
    @Column(name = "proc_code", length = 2, nullable = false)
    private String procCode;

    /**
     * Allowed amount difference between authorization and transaction, %
     */
    @Column(name = "amount_diff", precision = 3, scale = 0)
    private Short amountDiff;

    /**
     * Locking flag; 1 - locked; other - not locked
     */
    @Column(name = "locking_flag", precision = 1, scale = 0)
    private Integer lockingFlag;

    /**
     * Locking algorhythme; 1 - A1; 4 - A4; 5 - A5; 6 - A6; 7 - A7
     */
    @Column(name = "locking_alg", precision = 1, scale = 0)
    private Integer lockingAlg;

    /**
     * Locked amount sign; 0 - positive; 1 - negative
     */
    @Column(name = "locking_sign", precision = 1, scale = 0)
    private Integer lockingSign;

    /**
     * Message child request row number
     */
    @Column(name = "child_row", precision = 11, scale = 0, unique = true)
    private Long childRow;

    /**
     * Card number
     */
    @Column(name = "fld_002", length = 19)
    private String fld002;

    /**
     * Authorization amount, authorization currency
     */
    @Column(name = "fld_004", precision = 12, scale = 0)
    private Long fld004;

    /**
     * Authorization amount, account (billing) currency
     */
    @Column(name = "fld_006", precision = 12, scale = 0)
    private Long fld006;

    /**
     * System trace audit number (STAN)
     */
    @Column(name = "fld_011", length = 6)
    private String fld011;

    /**
     * Transactin date, time
     */
    @Column(name = "fld_012", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fld012;

    /**
     * Point code
     */
    @Column(name = "fld_022", length = 12)
    private String fld022;

    /**
     * Function code
     */
    @Column(name = "fld_024", length = 3)
    private String fld024;

    /**
     * MCC code
     */
    @Column(name = "fld_026", length = 4)
    private String fld026;

    /**
     * Acquirer institution identification code
     */
    @Column(name = "fld_032", length = 11)
    private String fld032;

    /**
     * Reference number
     */
    @Column(name = "fld_037", length = 12)
    private String fld037;

    /**
     * Authorization approval code
     */
    @Column(name = "fld_038", length = 6)
    private String fld038;

    /**
     * Terminal ID
     */
    @Column(name = "fld_041", length = 8)
    private String fld041;

    /**
     * Card acceptor identification code
     */
    @Column(name = "fld_042", length = 15)
    private String fld042;

    /**
     * Card acceptor name, location
     */
    @Column(name = "fld_043", length = 99)
    private String fld043;

    /**
     * Transaction currency
     */
    @Column(name = "fld_049", length = 3)
    private String fld049;

    /**
     * Account (billing) currency
     */
    @Column(name = "fld_051", length = 3)
    private String fld051;

    /**
     * User identification number
     */
    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    /**
     * Flag for ierrregular unlocks export: 0, other - not exported; 1 - exported
     */
    @Column(name = "i_export", precision = 1, scale = 0)
    private Integer IExport;

    @Column(name = "fld_095", length = 99)
    private String fld095;

    @Column(name = "fld_072", length = 999)
    private String fld072;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "fld_049", insertable = false, updatable = false)
    private IzdCcyTable izdCcyFld049;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "fld_051", insertable = false, updatable = false)
    private IzdCcyTable izdCcyFld051;

}

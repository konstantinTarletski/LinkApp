package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_batches")
public class IzdBatches implements Serializable {

    private static final long serialVersionUID = 1L;

    // Fields
    @Id
    @Column(name = "proc_id", precision = 14, scale = 0, nullable = false)
    private Long procId;

    /**
     * Bank - row owner code
     */
    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    /**
     * Card group in bank
     */
    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    /**
     * Last line number in Batch process protocol, each new or repeated step to be appended
     */
    @Column(name = "last_line", precision = 8, scale = 0, nullable = false)
    private Integer lastLine;

    /**
     * Recording date for batch process
     */
    @Column(name = "rec_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date recDate;

    /**
     * Posting date
     */
    @Column(name = "post_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;

    /**
     * Batch process end time
     */
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    /**
     * User ID
     */
    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    /**
     * Insert or last update date, time
     */
    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    /**
     * End of day flag
     */
    @Column(name = "end_of_day_flag", length = 1)
    private String endOfDayFlag;

}

package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_trtype_names")
public class IzdTrtypeName implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdTrtypeNameId id;

    /**
     * Transaction name in national language
     */
    @Column(name = "name", length = 45)
    private String name;

    /**
     * User identification number
     */
    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    /**
     * Insert or last update date, time
     */
    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

}

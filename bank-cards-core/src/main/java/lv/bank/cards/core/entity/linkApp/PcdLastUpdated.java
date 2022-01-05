package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "pcd_last_updated")
public class PcdLastUpdated implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "cdate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date cdate;

}

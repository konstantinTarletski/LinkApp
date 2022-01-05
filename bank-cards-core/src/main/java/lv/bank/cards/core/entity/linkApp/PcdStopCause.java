package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "pcd_stop_causes")
public class PcdStopCause implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdStopCausePK comp_id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "status_code", length = 3, nullable = false)
    private String statusCode;

    @Column(name = "name_by_iso", length = 70)
    private String nameByIso;

    @Column(name = "arcsys_code", length = 2)
    private String arcsysCode;

    @Column(name = "arcsys_int")
    private Integer arcsysInt;

    @Column(name = "access_level")
    private Integer accessLevel;

}

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
@Table(name = "izd_config")
public class IzdConfig implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdConfigPK id;

    @Column(name = "param_value", length = 500, nullable = false)
    private String paramValue;

    @Column(name = "param_name", length = 100, nullable = false)
    private String paramName;

    @Column(name = "param_coment", length = 256, nullable = false)
    private String paramComent;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "param_type", length = 2, nullable = false)
    private String paramType;

}

package lv.bank.cards.core.entity.linkApp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "PCD_CARD_DESIGNS")
public class PcdCardDesigns implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdCardDesignsPK comp_id;

    @Column(name = "design_type", length = 2, nullable = false)
    private String designType;

    @Column(name = "design_type_name", length = 20, nullable = false)
    private String designTypeName;

    @Column(name = "notes", length = 240)
    private String notes;

    @Column(name = "CTIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "active", length = 1, nullable = false)
    private String active;

}

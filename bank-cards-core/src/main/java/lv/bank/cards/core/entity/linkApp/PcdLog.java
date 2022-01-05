package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "pcd_log")
@SequenceGenerator(name = "pcdLogSeq", sequenceName = "pcd_log_seq", allocationSize = 1)
public class PcdLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pcdLogSeq")
    private java.lang.Long id;

    @Column(name = "rec_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recDate;

    @Column(name = "source", length = 20)
    private String source;

    @Column(name = "operation", length = 40)
    private String operation;

    @Column(name = "operator", length = 20)
    private String operator;

    @Column(name = "text", length = 300)
    private String text;

    @Column(name = "result", length = 200)
    private String result;

}

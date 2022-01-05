package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@Entity
@Table(name = "pcd_cond_card")
public class PcdCondCard implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdCondCardPK comp_id;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "bin_code", length = 9)
    private String binCode;

    @Column(name = "card_type", length = 2)
    private String cardType;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "card_fee", precision = 10, scale = 0)
    private Long cardFee;

    @Column(name = "card_fee1", precision = 10, scale = 0)
    private Long cardFee1;

    @Column(name = "annual_fee", precision = 10, scale = 0)
    private Long annualFee;

    @Column(name = "annual_fee1", precision = 10, scale = 0)
    private Long annualFee1;

    @Column(name = "repl_fee", precision = 10, scale = 0)
    private Long replFee;

    @Column(name = "repl_fee1", precision = 10, scale = 0)
    private Long replFee1;

    @Column(name = "duplic_fee", precision = 10, scale = 0)
    private Long duplicFee;

    @Column(name = "duplic_fee1", precision = 10, scale = 0)
    private Long duplicFee1;

}

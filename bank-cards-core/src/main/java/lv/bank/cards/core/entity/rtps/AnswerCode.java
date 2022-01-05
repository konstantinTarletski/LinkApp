package lv.bank.cards.core.entity.rtps;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "RTPSADMIN.answer_codes")
public class AnswerCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "action_code", nullable = false)
    private String actionCode;

    @Column(name = "msg_short", length = 99)
    private String msgShort;

    @Column(name = "msg_full", length = 99)
    private String msgFull;

    @Column(name = "stop_usage", length = 1)
    private String stopUsage;

    @Column(name = "status_usage", length = 1)
    private String statusUsage;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "answerCode", fetch = FetchType.LAZY)
    private Set<StipStoplist> stipStoplists;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "answerCodeByStatCode1", fetch = FetchType.LAZY)
    private Set<StipCard> stipCardsByStatCode1;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "answerCodeByStatCode2", fetch = FetchType.LAZY)
    private Set<StipCard> stipCardsByStatCode2;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "answerCode", fetch = FetchType.LAZY)
    private Set<CardsException> cardsExceptions;

}

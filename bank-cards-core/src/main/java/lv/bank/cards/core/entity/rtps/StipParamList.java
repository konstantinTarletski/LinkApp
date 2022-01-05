package lv.bank.cards.core.entity.rtps;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "RTPSADMIN.stip_paramlist")
@NamedNativeQuery(name = "get.updated.items.lv.bank.cards.core.entity.rtps.StipParamList.to.lv.bank.cards.core.entity.linkApp.PcdAccumulator",
        query = "select * from RTPSADMIN.stip_paramlist p " +
                "where centre_id = :centreId and exists ( " +
                "              select 1 from RTPSADMIN.stip_paramlist_jn " +
                "              where centre_id = :centreId " +
                "              and jn_datetime between :lastWaterMark and :currentWaterMark " +
                "             )",
        resultClass = StipParamList.class)
public class StipParamList implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StipParamListPK id;

    @Column(name = "week_length")
    private Long weekLength;

    @Column(name = "param_grp", length = 5)
    private String paramGrp;

    @Column(name = "answer_code", length = 3)
    private String answerCode;

    @Column(name = "abbreviature", length = 20)
    private String abbreviature;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "rule_expr", length = 1000)
    private String ruleExpr;

    @Column(name = "limit_trans")
    private Long limitTrans;

    @Column(name = "count_day")
    private Long countDay;

    @Column(name = "amount_day")
    private Long amountDay;

    @Column(name = "count_week")
    private Long countWeek;

    @Column(name = "amount_week")
    private Long amountWeek;

    @Column(name = "limit_trans_gray")
    private Long limitTransGray;

    @Column(name = "count_day_gray")
    private Long countDayGray;

    @Column(name = "amount_day_gray")
    private Long amountDayGray;

    @Column(name = "count_week_gray")
    private Long countWeekGray;

    @Column(name = "amount_week_gray")
    private Long amountWeekGray;

    @Column(name = "group_flag", length = 1)
    private String groupFlag;

    @Column(name = "off_limit_trans")
    private Long offLimitTrans;

    @Column(name = "off_count_day")
    private Long offCountDay;

    @Column(name = "off_amount_day")
    private Long offAmountDay;

    @Column(name = "off_count_week")
    private Long offCountWeek;

    @Column(name = "off_amount_week")
    private Long offAmountWeek;

    @Column(name = "restr_type", length = 1)
    private String restrType;

}

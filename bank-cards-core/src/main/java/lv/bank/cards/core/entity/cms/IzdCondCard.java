package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_cond_card")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdCondCard.to.lv.bank.cards.core.entity.linkApp.PcdCondCard",
        query = "select c from IzdCondCard c where c.ctime between :lastWaterMark and :currentWaterMark")
public class IzdCondCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdCondCardPK comp_id;

    @Column(name = "name", length = 15)
    private String name;

    @Column(name = "card_type", length = 2)
    private String cardType;

    @Column(name = "stop_fee", nullable = false)
    private long stopFee;

    @Column(name = "card_fee", nullable = false)
    private long cardFee;

    @Column(name = "card_fee1", nullable = false)
    private long cardFee1;

    @Column(name = "annual_fee", nullable = false)
    private long annualFee;

    @Column(name = "annual_fee1", nullable = false)
    private long annualFee1;

    @Column(name = "issu_fee1", nullable = false)
    private long issuFee1;

    @Column(name = "issu_fee2", nullable = false)
    private long issuFee2;

    @Column(name = "repl_fee", nullable = false)
    private long replFee;

    @Column(name = "repl_fee1", nullable = false)
    private long replFee1;

    @Column(name = "renew_fee", nullable = false)
    private long renewFee;

    @Column(name = "duplic_fee", nullable = false)
    private long duplicFee;

    @Column(name = "duplic_fee1", nullable = false)
    private long duplicFee1;

    @Column(name = "ret_fee1", nullable = false)
    private BigDecimal retFee1;

    @Column(name = "mret_fee1", nullable = false)
    private long mretFee1;

    @Column(name = "serv_fee", nullable = false)
    private BigDecimal servFee;

    @Column(name = "serv_min", nullable = false)
    private long servMin;

    @Column(name = "cash_fee", nullable = false)
    private BigDecimal cashFee;

    @Column(name = "cash_fee_frnd", nullable = false)
    private BigDecimal cashFeeFrnd;

    @Column(name = "cash_fee_loc", nullable = false)
    private BigDecimal cashFeeLoc;

    @Column(name = "cash_fee_int", nullable = false)
    private BigDecimal cashFeeInt;

    @Column(name = "cash_feec", nullable = false)
    private BigDecimal cashFeec;

    @Column(name = "cash_feec_frnd", nullable = false)
    private BigDecimal cashFeecFrnd;

    @Column(name = "cash_feec_loc", nullable = false)
    private BigDecimal cashFeecLoc;

    @Column(name = "cash_feec_int", nullable = false)
    private BigDecimal cashFeecInt;

    @Column(name = "mcash_fee", nullable = false)
    private long mcashFee;

    @Column(name = "mcash_fee_frnd", nullable = false)
    private long mcashFeeFrnd;

    @Column(name = "mcash_fee_loc", nullable = false)
    private long mcashFeeLoc;

    @Column(name = "mcash_fee_int", nullable = false)
    private long mcashFeeInt;

    @Column(name = "mcash_feec", nullable = false)
    private long mcashFeec;

    @Column(name = "mcash_feec_frnd", nullable = false)
    private long mcashFeecFrnd;

    @Column(name = "mcash_feec_loc", nullable = false)
    private long mcashFeecLoc;

    @Column(name = "mcash_feec_int", nullable = false)
    private long mcashFeecInt;

    @Column(name = "atm_fee", nullable = false)
    private BigDecimal atmFee;

    @Column(name = "atm_fee_frnd", nullable = false)
    private BigDecimal atmFeeFrnd;

    @Column(name = "atm_fee_loc", nullable = false)
    private BigDecimal atmFeeLoc;

    @Column(name = "atm_fee_int", nullable = false)
    private BigDecimal atmFeeInt;

    @Column(name = "atm_min", nullable = false)
    private long atmMin;

    @Column(name = "atm_min_frnd", nullable = false)
    private long atmMinFrnd;

    @Column(name = "atm_min_loc", nullable = false)
    private long atmMinLoc;

    @Column(name = "atm_min_int", nullable = false)
    private long atmMinInt;

    @Column(name = "atm_feec", nullable = false)
    private BigDecimal atmFeec;

    @Column(name = "atm_feec_frnd", nullable = false)
    private BigDecimal atmFeecFrnd;

    @Column(name = "atm_feec_loc", nullable = false)
    private BigDecimal atmFeecLoc;

    @Column(name = "atm_feec_int", nullable = false)
    private BigDecimal atmFeecInt;

    @Column(name = "atm_minc", nullable = false)
    private long atmMinc;

    @Column(name = "atm_minc_frnd", nullable = false)
    private long atmMincFrnd;

    @Column(name = "atm_minc_loc", nullable = false)
    private long atmMincLoc;

    @Column(name = "atm_minc_int", nullable = false)
    private long atmMincInt;

    @Column(name = "retail_fee", nullable = false)
    private BigDecimal retailFee;

    @Column(name = "retail_fee_frnd", nullable = false)
    private BigDecimal retailFeeFrnd;

    @Column(name = "retail_fee_loc", nullable = false)
    private BigDecimal retailFeeLoc;

    @Column(name = "retail_fee_int", nullable = false)
    private BigDecimal retailFeeInt;

    @Column(name = "mretail_fee", nullable = false)
    private long mretailFee;

    @Column(name = "mretail_fee_frnd", nullable = false)
    private long mretailFeeFrnd;

    @Column(name = "mretail_fee_loc", nullable = false)
    private long mretailFeeLoc;

    @Column(name = "mretail_fee_int", nullable = false)
    private long mretailFeeInt;

    @Column(name = "bonus_rate", nullable = false)
    private BigDecimal bonusRate;

    @Column(name = "card_validity", nullable = false)
    private int cardValidity;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "bin_code", length = 9)
    private String binCode;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ccy", insertable = false, updatable = false, referencedColumnName = "ccy"),
            @JoinColumn(name = "groupc", insertable = false, updatable = false, referencedColumnName = "groupc"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private IzdCardGroupCcy izdCardGroupCcy;

}

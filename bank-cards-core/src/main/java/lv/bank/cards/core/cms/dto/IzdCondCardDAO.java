package lv.bank.cards.core.cms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lv.bank.cards.core.entity.cms.IzdCondCard;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class IzdCondCardDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String condSet;
    private String ccy;
    private String bankC;
    private String groupc;
    private String name;
    private String cardType;
    private long stopFee;
    private long cardFee;
    private long cardFee1;
    private long annualFee;
    private long annualFee1;
    private long issuFee1;
    private long issuFee2;
    private long replFee;
    private long renewFee;
    private long duplicFee;
    private BigDecimal retFee1;
    private long mretFee1;
    private BigDecimal servFee;
    private long servMin;
    private BigDecimal cashFee;
    private BigDecimal cashFeeFrnd;
    private BigDecimal cashFeeLoc;
    private BigDecimal cashFeeInt;
    private BigDecimal cashFeec;
    private BigDecimal cashFeecFrnd;
    private BigDecimal cashFeecLoc;
    private BigDecimal cashFeecInt;
    private long mcashFee;
    private long mcashFeeFrnd;
    private long mcashFeeLoc;
    private long mcashFeeInt;
    private long mcashFeec;
    private long mcashFeecFrnd;
    private long mcashFeecLoc;
    private long mcashFeecInt;
    private BigDecimal atmFee;
    private BigDecimal atmFeeFrnd;
    private BigDecimal atmFeeLoc;
    private BigDecimal atmFeeInt;
    private long atmMin;
    private long atmMinFrnd;
    private long atmMinLoc;
    private long atmMinInt;
    private BigDecimal atmFeec;
    private BigDecimal atmFeecFrnd;
    private BigDecimal atmFeecLoc;
    private BigDecimal atmFeecInt;
    private long atmMinc;
    private long atmMincFrnd;
    private long atmMincLoc;
    private long atmMincInt;
    private BigDecimal retailFee;
    private BigDecimal retailFeeFrnd;
    private BigDecimal retailFeeLoc;
    private BigDecimal retailFeeInt;
    private long mretailFee;
    private long mretailFeeFrnd;
    private long mretailFeeLoc;
    private long mretailFeeInt;
    private BigDecimal bonusRate;
    private int cardValidity;
    private String binCode;

    public IzdCondCardDAO(IzdCondCard izdCondCard) {
        if (izdCondCard == null) return;
        try {
            bankC = izdCondCard.getComp_id().getBankC();
            groupc = izdCondCard.getComp_id().getGroupc();
            ccy = izdCondCard.getComp_id().getCcy();
            condSet = izdCondCard.getComp_id().getCondSet();
            BeanUtils.copyProperties(this, izdCondCard);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}

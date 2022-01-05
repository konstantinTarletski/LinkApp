package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel
@Data
public class CardCreditDetailsDO {

    @ApiModelProperty(value = "Account number", required = true, example = "693651")
    private BigDecimal accountNo;

    @ApiModelProperty(value = "Currency alpha representing", required = true, example = "EUR")
    private String currency;

    @ApiModelProperty(value = "Total Credit Limit", required = true, example = "125.34")
    private BigDecimal creditLimit;

    @ApiModelProperty(value = "Used credit limit", required = true, example = "125.34")
    private BigDecimal usedCredit;

    @ApiModelProperty(value = "Interest Free Period (in days)", required = true, example = "12")
    private String gracePeriod;

    @ApiModelProperty(value = "Available balance", required = true, example = "125.34")
    private BigDecimal availableBalance;

    @ApiModelProperty(value = "Internal Platon account No.", required = true, example = "1300123456")
    private String coreAccountNo;

    @ApiModelProperty(value = "Opening balance", required = true, example = "125.34")
    private BigDecimal openingBalance;

    @ApiModelProperty(value = "Interest rate", required = true, example = "18.00")
    private BigDecimal interestRate;

    public String getGracePeriod() {
        if (gracePeriod == null) {
            return "0";
        }
        return gracePeriod;
    }

}

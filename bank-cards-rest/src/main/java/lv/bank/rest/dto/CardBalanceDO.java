package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel
@Data
public class CardBalanceDO {

    @ApiModelProperty(value = "Currency alpha representing", required = true, example = "EUR")
    private String currency;

    @ApiModelProperty(value = "Available balance", required = true, example = "125.34")
    private BigDecimal availableBalance;

    @ApiModelProperty(value = "Reserved amount", required = true, example = "32.54")
    private BigDecimal reservedAmount;

}

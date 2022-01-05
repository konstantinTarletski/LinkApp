package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardLimitsDO {

    @ApiModelProperty(value = "Limit ID", required = true, example = "QA")
    private String id;

    @ApiModelProperty(value = "Cash amount daily", example = "32.54")
    private BigDecimal cashDaily;

    @ApiModelProperty(value = "Cash amount monthly", example = "32.54")
    private BigDecimal cashMonthly;

    @ApiModelProperty(value = "Sales amount daily", example = "32.54")
    private BigDecimal salesDaily;

}

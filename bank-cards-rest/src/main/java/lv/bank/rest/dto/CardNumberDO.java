package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class CardNumberDO {

    @ApiModelProperty(value = "Card number", required = true, example = "4088830000000001")
    private String cardNumber;

}

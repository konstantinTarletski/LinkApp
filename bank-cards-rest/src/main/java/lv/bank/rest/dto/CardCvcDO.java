package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class CardCvcDO {

    @ApiModelProperty(value = "CVC value", required = true, example = "123")
    private String cvc;

}

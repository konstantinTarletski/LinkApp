package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class VisaTokenServicePhoneDO {

    @ApiModelProperty(value = "phone", required = true, example = "+371 20000000")
    private String phone;

}

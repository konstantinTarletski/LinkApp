package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class VisaTokenServiceOtpDO {

    @ApiModelProperty(value = "Card number", required = true, example = "4088830000000001")
    private String cardNumber;

    @ApiModelProperty(value = "One Time Passcode", required = true, example = "123456")
    private String otp;

}

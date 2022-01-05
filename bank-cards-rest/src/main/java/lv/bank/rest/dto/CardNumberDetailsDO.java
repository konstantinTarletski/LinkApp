package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardNumberDetailsDO {

    @ApiModelProperty
    private String cardNumber;

    @ApiModelProperty
    private String holderName;

    @ApiModelProperty
    private String expiry;

    @ApiModelProperty
    private String cvc;

}

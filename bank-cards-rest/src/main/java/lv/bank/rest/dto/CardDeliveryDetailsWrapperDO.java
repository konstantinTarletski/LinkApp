package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class CardDeliveryDetailsWrapperDO {

    @ApiModelProperty(value = "Delivery details")
    private CardDeliveryDetailsDO deliveryDetails = new CardDeliveryDetailsDO();

}

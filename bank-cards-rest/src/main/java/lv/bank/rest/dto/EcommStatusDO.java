package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EcommStatusDO {

    @ApiModelProperty(value = "ECOMM status", required = true, example = "allowed")
    private ECommerceStatus status;

}

package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class CardDeliveryDetailsDO {

    @ApiModelProperty
    private String country;

    @ApiModelProperty
    private String cityRegion;

    @ApiModelProperty
    private String villageDistrict;

    @ApiModelProperty
    private String street;

    @ApiModelProperty
    private String zip;

    @ApiModelProperty
    private String language;

    @ApiModelProperty
    private EDeliveryType deliveryType;

    @ApiModelProperty
    private String branch;

}

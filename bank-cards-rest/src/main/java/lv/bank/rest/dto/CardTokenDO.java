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
public class CardTokenDO {

    @ApiModelProperty(value = "Card number", required = true, example = "4088830000000001")
    protected String id;

    @ApiModelProperty(value = "Indicates if card product can be tokenized. Returns false if card has a delivery block, otherwise true/false depending on eligible BIN list", required = true, example = "true")
    protected boolean tokenEligible;

    @ApiModelProperty(value = "Token reference ID", required = true, example = "DNITHE302120145281129520")
    protected String tokenRefId;

    @ApiModelProperty(value = "Token status: pending/inactive/active/suspended", required = true, example = "active")
    protected String tokenStatus;

}

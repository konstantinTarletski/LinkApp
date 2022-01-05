package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class ErrorResponse {

    @ApiModelProperty(required = true, value = "HTTP error code", example = "400")
    private int status;

    @ApiModelProperty(required = true, value = "Error code define in confluence", example = "card_not_found")
    private String code;

    @ApiModelProperty(value = "Error message describing error", example = "Card not found")
    private String message;

    @ApiModelProperty(value = "Error causing parameter", example = "customerId")
    private String target;

    public ErrorResponse(int status, String code, String message, String target) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.target = target;
    }

}

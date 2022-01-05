package lv.bank.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactlessStatusDO {

    @ApiModelProperty(value = "Contactless status", required = true, example = "enabled")
    private EContactlessStatus status;

}

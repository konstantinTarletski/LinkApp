package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardStatusDO {

	@ApiModelProperty(value = "Card status", required = true, example = "ACTIVE")
	private ECardStatus cardStatus;

}

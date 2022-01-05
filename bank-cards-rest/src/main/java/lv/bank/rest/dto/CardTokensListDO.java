package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel
@Data
public class CardTokensListDO {

    @ApiModelProperty(value = "List of cards")
    private List<CardTokenDO> cards = new ArrayList<>();

}

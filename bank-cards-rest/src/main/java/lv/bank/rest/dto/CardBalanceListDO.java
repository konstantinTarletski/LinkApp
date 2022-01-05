package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel
@Data
public class CardBalanceListDO {

    @ApiModelProperty(value = "List of balances")
    private List<CardBalanceDO> cardBalances = new ArrayList<>();

}

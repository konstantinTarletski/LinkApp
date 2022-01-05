package lv.bank.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class ReservationListDO  {

    @ApiModelProperty(value = "List of reservations")
    private final List<ReservationDO> reservations = new ArrayList<>();

}

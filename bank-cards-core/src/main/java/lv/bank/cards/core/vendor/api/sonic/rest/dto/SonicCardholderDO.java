package lv.bank.cards.core.vendor.api.sonic.rest.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SonicCardholderDO extends SonicBaseDO {

    private String phoneNumber;
    private AddressDO address;

}


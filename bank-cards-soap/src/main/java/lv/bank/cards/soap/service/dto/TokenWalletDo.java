package lv.bank.cards.soap.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenWalletDo {

    protected String id;
    protected boolean tokenEligible;
    protected String tokenRefId;
    protected String tokenStatus;

}

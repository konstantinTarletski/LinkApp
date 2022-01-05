package lv.bank.cards.soap.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtbDo {

    private String amtInitial = null;
    private String amtBonus;
    private String amtLocked;
    private String amtCrd;
    private String otb;

}

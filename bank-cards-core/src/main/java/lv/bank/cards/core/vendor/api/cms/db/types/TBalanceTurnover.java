package lv.bank.cards.core.vendor.api.cms.db.types;

import lombok.Data;

@Data
public class TBalanceTurnover {

	private Integer pBegin_Bal;
	private Integer pDebit;
	private Integer pCredit;
	private Integer pEnd_Bal;

}

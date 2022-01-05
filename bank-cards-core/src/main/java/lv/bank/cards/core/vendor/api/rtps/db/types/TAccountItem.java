package lv.bank.cards.core.vendor.api.rtps.db.types;

import lombok.Data;

@Data
public class TAccountItem {

    private String centre_id;
    private String effective_date;
    private String update_date;
    private String purge_date;
    private String crd_holdr_id;
    private String account_type;
    private String account_id;
    private String account_ccy;
    private String initial_amount;
    private String bonus_amount;
    private String locked_amount;
    private String lock_amount_cms;

}

package lv.bank.cards.core.vendor.api.rtps.db.types;

import lombok.Data;

@Data
public class TCardOtb {

    private String p_centre_id;
    private String p_otb;
    private String p_locked;
    private String p_ccy;
    private String p_hot_status;
    private String p_hot_status_msg;
    private String p_hot_description;
    private String p_iss_status;
    private String p_iss_status_msg;
    private String p_iss_description;
    private String p_status;
    private String p_status_msg;

}

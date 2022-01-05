package lv.bank.cards.core.vendor.api.rtps.db.types;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Data
public class TAuthHistItem {

    private String msg_type;
    private String auth_date;
    private String amount;
    private String trans_type;
    private String locking;
    private String ccy_num;
    private String ccy_alpha;
    private String location;

    public TAuthHistItem(Object[] fields) throws TAuthHistItemException {
        try {
            if (fields[0] != null)
                this.setMsg_type(fields[0].toString());
            if (fields[1] != null)
                this.setAuth_date(fields[1].toString());
            if (fields[2] != null)
                this.setAmount(fields[2].toString());
            if (fields[3] != null)
                this.setTrans_type(fields[3].toString());
            if (fields[4] != null)
                this.setLocking(fields[4].toString());
            if (fields[5] != null)
                this.setCcy_num(fields[5].toString());
            if (fields[6] != null)
                this.setCcy_alpha(fields[6].toString());
            if (fields[7] != null)
                this.setLocation(fields[7].toString());
        } catch (Exception e) {
            log.error("Error while creating TAuthHistItem object");
            throw new TAuthHistItemException(e);
        }
    }

}

package lv.bank.cards.core.vendor.api.rtps.db.types;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Data
@NoArgsConstructor
public class TRMSStopListItem {

    private String centre_id;
    private String card_number;
    private Date effective_date;
    private Date update_date;
    private Date purge_date;
    private String action_code;
    private String description;
    private String rule_expression;
    private Integer priority;

    public TRMSStopListItem(Object[] fields) throws TRMSStopListItemException {
        try {
            if (fields[0] != null)
                this.setCentre_id(fields[0].toString());
            if (fields[1] != null)
                this.setCard_number(fields[1].toString());
            if (fields[2] != null)
                this.setEffective_date(((java.sql.Timestamp) fields[2]));
            if (fields[3] != null)
                this.setUpdate_date(((java.sql.Timestamp) fields[3]));
            if (fields[4] != null)
                this.setPurge_date(((java.sql.Timestamp) fields[4]));
            if (fields[5] != null)
                this.setAction_code(fields[5].toString());
            if (fields[6] != null)
                this.setDescription(fields[6].toString());
            if (fields[7] != null)
                this.setRule_expression(fields[7].toString());
            if (fields[8] != null)
                this.setPriority(((java.math.BigDecimal) fields[8]).intValue());

        } catch (Exception e) {
            log.error("Error while creating TRMSStopListItem object");
            throw new TRMSStopListItemException(e);
        }
    }

}

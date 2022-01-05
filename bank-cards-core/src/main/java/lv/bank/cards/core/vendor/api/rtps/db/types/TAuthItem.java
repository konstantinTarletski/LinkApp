package lv.bank.cards.core.vendor.api.rtps.db.types;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Data
@NoArgsConstructor
public class TAuthItem {

    private Integer row_numb;
    private String msg_type;
    private String fld_002;
    private String fld_003;
    private Integer fld_004;
    private String fld_049;
    private Integer fld_006;
    private String fld_051;
    private Date fld_012;
    private String fld_026;
    private String fld_032;
    private String fld_033;
    private String fld_038;
    private String fld_041;
    private String fld_042;
    private String fld_043;

    public TAuthItem(Object[] fields) throws TAuthItemException {
        super();
        try {
            if (fields[0] != null)
                this.setRow_numb(((java.math.BigDecimal) fields[0]).intValue());
            if (fields[1] != null)
                this.setMsg_type(fields[1].toString());
            if (fields[2] != null)
                this.setFld_002(fields[2].toString());
            if (fields[3] != null)
                this.setFld_003(fields[3].toString());
            if (fields[4] != null)
                this.setFld_004(((java.math.BigDecimal) fields[4]).intValue());
            if (fields[5] != null)
                this.setFld_049(fields[5].toString());
            if (fields[6] != null)
                this.setFld_006(((java.math.BigDecimal) fields[6]).intValue());
            if (fields[7] != null)
                this.setFld_051(fields[7].toString());
            if (fields[8] != null)
                this.setFld_012(((java.sql.Timestamp) fields[8]));
            if (fields[9] != null)
                this.setFld_026(fields[9].toString());
            if (fields[10] != null)
                this.setFld_032(fields[10].toString());
            if (fields[11] != null)
                this.setFld_033(fields[11].toString());
            if (fields[12] != null)
                this.setFld_038(fields[12].toString());
            if (fields[13] != null)
                this.setFld_041(fields[13].toString());
            if (fields[14] != null)
                this.setFld_042(fields[14].toString());
            if (fields[15] != null)
                this.setFld_043(fields[15].toString());
        } catch (Exception e) {
            log.error("Error while creating TAuthItem object");
            throw new TAuthItemException(e);
        }
    }

}

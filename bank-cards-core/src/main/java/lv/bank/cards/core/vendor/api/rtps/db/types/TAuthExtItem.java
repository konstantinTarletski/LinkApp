package lv.bank.cards.core.vendor.api.rtps.db.types;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class TAuthExtItem extends TAuthItem {

    private String centre_id;
    private Date request_date;
    private String card_type;
    private String dev_type;
    private String fld_007;
    private Integer fld_010;
    private String fld_011;
    private String fld_014;
    private String fld_022;
    private String fld_024;
    private String fld_025;
    private Integer fld_030a;
    private String fld_035;
    private String fld_037;
    private String fld_039;
    private String fld_054;
    private String fld_055;
    private String fld_056a;
    private String fld_056b;
    private Date fld_056c;
    private String fld_056d;
    private String fld_072;
    private String fld_093;
    private String fld_094;
    private String fld_100;
    private String fld_102;
    private String param_grp;
    private Integer child_row;
    private String reverse_flag;
    private Integer locking_sign;
    private String locking_flag;
    private String unlocking_reason;
    private Date unlocking_date;
    private String fld_046;
    private String fld_095;
    private String comm_grp;
    private Integer comm_id;
    private Integer fld_005;
    private Integer fld_009;
    private String fld_023;
    private Date fld_028;
    private String fld_029;
    private String fld_031;
    private String fld_050;
    private String fld_103;
    private String fld_123;

    public TAuthExtItem(Object[] fields) throws TAuthExtItemException {
        try {
            if (fields[0] != null)
                this.setRow_numb(((java.math.BigDecimal) fields[0]).intValue());
            if (fields[1] != null)
                this.setCentre_id(fields[1].toString());
            if (fields[2] != null)
                this.setRequest_date(((java.sql.Timestamp) fields[2]));
            if (fields[3] != null)
                this.setCard_type(fields[3].toString());
            if (fields[4] != null)
                this.setDev_type(fields[4].toString());
            if (fields[5] != null)
                this.setMsg_type(fields[5].toString());
            if (fields[6] != null)
                this.setFld_002(fields[6].toString());
            if (fields[7] != null)
                this.setFld_003(fields[7].toString());
            if (fields[8] != null)
                this.setFld_004(((java.math.BigDecimal) fields[8]).intValue());
            if (fields[9] != null)
                this.setFld_006(((java.math.BigDecimal) fields[9]).intValue());
            if (fields[10] != null)
                this.setFld_007(fields[10].toString());
            if (fields[11] != null)
                this.setFld_010(((java.math.BigDecimal) fields[11]).intValue());
            if (fields[12] != null)
                this.setFld_011(fields[12].toString());
            if (fields[13] != null)
                this.setFld_012(((java.sql.Timestamp) fields[13]));
            if (fields[14] != null)
                this.setFld_014(fields[14].toString());
            if (fields[15] != null)
                this.setFld_022(fields[15].toString());
            if (fields[16] != null)
                this.setFld_024(fields[16].toString());
            if (fields[17] != null)
                this.setFld_025(fields[17].toString());
            if (fields[18] != null)
                this.setFld_026(fields[18].toString());
            if (fields[19] != null)
                this.setFld_030a(((java.math.BigDecimal) fields[19]).intValue());
            if (fields[20] != null)
                this.setFld_032(fields[20].toString());
            if (fields[21] != null)
                this.setFld_033(fields[21].toString());
            if (fields[22] != null)
                this.setFld_035(fields[22].toString());
            if (fields[23] != null)
                this.setFld_037(fields[23].toString());
            if (fields[24] != null)
                this.setFld_038(fields[24].toString());
            if (fields[25] != null)
                this.setFld_039(fields[25].toString());
            if (fields[26] != null)
                this.setFld_041(fields[26].toString());
            if (fields[27] != null)
                this.setFld_042(fields[27].toString());
            if (fields[28] != null)
                this.setFld_043(fields[28].toString());
            if (fields[29] != null)
                this.setFld_049(fields[29].toString());
            if (fields[30] != null)
                this.setFld_051(fields[30].toString());
            if (fields[31] != null)
                this.setFld_054(fields[31].toString());
            if (fields[32] != null)
                this.setFld_055(fields[32].toString());
            if (fields[33] != null)
                this.setFld_056a(fields[33].toString());
            if (fields[34] != null)
                this.setFld_056b(fields[34].toString());
            if (fields[35] != null)
                this.setFld_056c(((java.sql.Timestamp) fields[35]));
            if (fields[36] != null)
                this.setFld_056d(fields[36].toString());
            if (fields[37] != null)
                this.setFld_072(fields[37].toString());
            if (fields[38] != null)
                this.setFld_093(fields[38].toString());
            if (fields[39] != null)
                this.setFld_094(fields[39].toString());
            if (fields[40] != null)
                this.setFld_100(fields[40].toString());
            if (fields[41] != null)
                this.setFld_102(fields[41].toString());
            if (fields[42] != null)
                this.setParam_grp(fields[42].toString());
            if (fields[43] != null)
                this.setChild_row(((java.math.BigDecimal) fields[43]).intValue());
            if (fields[44] != null)
                this.setReverse_flag(fields[44].toString());
            if (fields[45] != null)
                this.setLocking_sign(((java.math.BigDecimal) fields[45]).intValue());
            if (fields[46] != null)
                this.setLocking_flag(fields[46].toString());
            if (fields[47] != null)
                this.setUnlocking_reason(fields[47].toString());
            if (fields[48] != null)
                this.setUnlocking_date(((java.sql.Timestamp) fields[48]));
            if (fields[49] != null)
                this.setFld_046(fields[49].toString());
            if (fields[50] != null)
                this.setFld_095(fields[50].toString());
            if (fields[51] != null)
                this.setComm_grp(fields[51].toString());
            if (fields[52] != null)
                this.setComm_id(((java.math.BigDecimal) fields[52]).intValue());
            if (fields[53] != null)
                this.setFld_005(((java.math.BigDecimal) fields[53]).intValue());
            if (fields[54] != null)
                this.setFld_009(((java.math.BigDecimal) fields[54]).intValue());
            if (fields[55] != null)
                this.setFld_023(fields[55].toString());
            if (fields[56] != null)
                this.setFld_028(((java.sql.Timestamp) fields[56]));
            if (fields[57] != null)
                this.setFld_029(fields[57].toString());
            if (fields[58] != null)
                this.setFld_031(fields[58].toString());
            if (fields[59] != null)
                this.setFld_050(fields[59].toString());
            if (fields[60] != null)
                this.setFld_103(fields[60].toString());
            if (fields[61] != null)
                this.setFld_123(fields[61].toString());
        } catch (Exception e) {
            log.error("Error while creating TAuthExtItem object", e);
            throw new TAuthExtItemException(e);
        }
    }


}

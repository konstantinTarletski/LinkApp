package lv.bank.cards.core.entity.rtps;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "RTPSADMIN.stip_events_n")
public class StipEventsN implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "row_numb", nullable = false)
    private Long rowNumb;

    @Column(name = "request_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    @Column(name = "response_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date responseDate;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name = "purge_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purgeDate;

    @Column(name = "session_numb_in", precision = 11, scale = 0)
    private Long sessionNumbIn;

    @Column(name = "stan_internal", length = 6)
    private String stanInternal;

    @Column(name = "card_type", length = 2)
    private String cardType;

    @Column(name = "dev_type", length = 1)
    private String devType;

    @Column(name = "msg_type", length = 4)
    private String msgType;

    @Column(name = "fld_002", length = 19)
    private String fld002;

    @Column(name = "fld_003", length = 6)
    private String fld003;

    @Column(name = "fld_004", precision = 12, scale = 0)
    private Long fld004;

    @Column(name = "fld_006", precision = 12, scale = 0)
    private Long fld006;

    @Column(name = "fld_007")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fld007;

    @Column(name = "fld_010", precision = 16, scale = 0)
    private BigDecimal fld010;

    @Column(name = "fld_011", length = 6)
    private String fld011;

    @Column(name = "fld_012")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fld012;

    @Column(name = "fld_013", length = 4)
    private String fld013;

    @Column(name = "fld_014", length = 4)
    private String fld014;

    @Column(name = "fld_022", length = 12)
    private String fld022;

    @Column(name = "fld_024", length = 3)
    private String fld024;

    @Column(name = "fld_025", length = 4)
    private String fld025;

    @Column(name = "fld_026", length = 4)
    private String fld026;

    @Column(name = "fld_027")
    private Integer fld027;

    @Column(name = "fld_030a", precision = 12, scale = 0)
    private Long fld030a;

    @Column(name = "fld_032", length = 11)
    private String fld032;

    @Column(name = "fld_033", length = 11)
    private String fld033;

    @Column(name = "fld_035", length = 37)
    private String fld035;

    @Column(name = "fld_037", length = 12)
    private String fld037;

    @Column(name = "fld_038", length = 6)
    private String fld038;

    @Column(name = "fld_041", length = 8)
    private String fld041;

    @Column(name = "fld_042", length = 15)
    private String fld042;

    @Column(name = "fld_043", length = 99)
    private String fld043;

    @Column(name = "fld_044", length = 99)
    private String fld044;


    @Column(name = "fld_045", length = 76)
    private String fld045;

    @Column(name = "fld_049", length = 3)
    private String fld049;

    @Column(name = "fld_051", length = 3)
    private String fld051;

    @Column(name = "fld_052")
    private byte[] fld052;

    @Column(name = "fld_053")
    private byte[] fld053;

    @Column(name = "fld_054", length = 120)
    private String fld054;

    @Column(name = "fld_055")
    private byte[] fld055;

    @Column(name = "fld_056a", length = 4)
    private String fld056a;

    @Column(name = "fld_056b", length = 6)
    private String fld056b;

    @Column(name = "fld_056c")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fld056c;

    @Column(name = "fld_056d", length = 11)
    private String fld056d;

    @Column(name = "fld_058", length = 11)
    private String fld058;

    @Column(name = "fld_059", length = 999)
    private String fld059;

    @Column(name = "fld_071", length = 8)
    private String fld071;

    @Column(name = "fld_072", length = 999)
    private String fld072;

    @Column(name = "fld_093", length = 11)
    private String fld093;

    @Column(name = "fld_094", length = 11)
    private String fld094;

    @Column(name = "fld_100", length = 11)
    private String fld100;

    @Column(name = "fld_102", length = 28)
    private String fld102;

    @Column(name = "orig_fld_003", length = 6)
    private String origFld003;

    @Column(name = "orig_fld_006", precision = 12, scale = 0)
    private Long origFld006;

    @Column(name = "orig_fld_010", precision = 16, scale = 0)
    private BigDecimal origFld010;

    @Column(name = "orig_fld_038", length = 6)
    private String origFld038;

    @Column(name = "orig_fld_039", length = 3)
    private String origFld039;

    @Column(name = "orig_fld_051", length = 3)
    private String origFld051;

    @Column(name = "param_grp", length = 5)
    private String paramGrp;

    @Column(name = "accum_ccy", length = 3)
    private String accumCcy;

    @Column(name = "accum_amount", precision = 12, scale = 0)
    private Long accumAmount;

    @Column(name = "child_row", precision = 11, scale = 0)
    private Long childRow;

    @Column(name = "reverse_flag", length = 1)
    private String reverseFlag;

    @Column(name = "validity_flag", length = 1)
    private String validityFlag;

    @Column(name = "delivery_flag", length = 1)
    private String deliveryFlag;

    @Column(name = "delivery_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    @Column(name = "accum_sign")
    private Integer accumSign;

    @Column(name = "locking_sign")
    private Integer lockingSign;

    @Column(name = "locking_flag", length = 1)
    private String lockingFlag;

    @Column(name = "unlocking_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date unlockingDate;

    @Column(name = "fld_046", length = 204)
    private String fld046;

    @Column(name = "fld_095", length = 99)
    private String fld095;

    @Column(name = "comm_grp", length = 3)
    private String commGrp;

    @Column(name = "comm_id", length = 4)
    private Integer commId;

    @Column(name = "state_id", length = 1)
    private String stateId;

    @Column(name = "state_history", length = 20)
    private String stateHistory;

    @Column(name = "delivery_retries")
    private Integer deliveryRetries;

    @Column(name = "parent_row", precision = 11, scale = 0)
    private Long parentRow;

    @Column(name = "fld_005", precision = 12, scale = 0)
    private Long fld005;

    @Column(name = "fld_008", precision = 8, scale = 0)
    private Integer fld008;

    @Column(name = "fld_009", precision = 8, scale = 0)
    private Integer fld009;

    @Column(name = "fld_015")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fld015;

    @Column(name = "fld_016")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fld016;

    @Column(name = "fld_017")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fld017;

    @Column(name = "fld_018", length = 4)
    private String fld018;

    @Column(name = "fld_019", length = 3)
    private String fld019;

    @Column(name = "fld_020", length = 3)
    private String fld020;

    @Column(name = "fld_021", length = 3)
    private String fld021;

    @Column(name = "fld_023", length = 3)
    private String fld023;

    @Column(name = "fld_028")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fld028;

    @Column(name = "fld_029", length = 3)
    private String fld029;

    @Column(name = "fld_031", length = 99)
    private String fld031;

    @Column(name = "fld_034", length = 28)
    private String fld034;

    @Column(name = "fld_036", length = 104)
    private String fld036;

    @Column(name = "fld_040", length = 3)
    private String fld040;

    @Column(name = "fld_047", length = 999)
    private String fld047;

    @Column(name = "fld_048", length = 999)
    private String fld048;

    @Column(name = "fld_050", length = 3)
    private String fld050;

    @Column(name = "fld_057", length = 3)
    private String fld057;

    @Column(name = "fld_060", length = 999)
    private String fld060;

    @Column(name = "fld_061", length = 999)
    private String fld061;

    @Column(name = "fld_062", length = 999)
    private String fld062;

    @Column(name = "fld_063", length = 999)
    private String fld063;

    @Column(name = "fld_064")
    private byte[] fld064;

    @Column(name = "fld_065")
    private byte[] fld065;

    @Column(name = "fld_066", length = 999)
    private String fld066;

    @Column(name = "fld_067", length = 2)
    private String fld067;

    @Column(name = "fld_068", length = 3)
    private String fld068;

    @Column(name = "fld_069", length = 3)
    private String fld069;

    @Column(name = "fld_070", length = 3)
    private String fld070;

    @Column(name = "fld_073")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fld073;

    @Column(name = "fld_074", precision = 10, scale = 0)
    private Long fld074;

    @Column(name = "fld_075", precision = 10, scale = 0)
    private Long fld075;

    @Column(name = "fld_076", precision = 10, scale = 0)
    private Long fld076;

    @Column(name = "fld_077", precision = 10, scale = 0)
    private Long fld077;

    @Column(name = "fld_078", precision = 10, scale = 0)
    private Long fld078;

    @Column(name = "fld_079", precision = 10, scale = 0)
    private Long fld079;

    @Column(name = "fld_080", precision = 10, scale = 0)
    private Long fld080;

    @Column(name = "fld_081", precision = 10, scale = 0)
    private Long fld081;

    @Column(name = "fld_082", precision = 10, scale = 0)
    private Long fld082;

    @Column(name = "fld_083", precision = 10, scale = 0)
    private Long fld083;

    @Column(name = "fld_084", precision = 10, scale = 0)
    private Long fld084;

    @Column(name = "fld_085", precision = 10, scale = 0)
    private Long fld085;

    @Column(name = "fld_086", precision = 16, scale = 0)
    private Long fld086;

    @Column(name = "fld_087", precision = 16, scale = 0)
    private Long fld087;

    @Column(name = "fld_088", precision = 16, scale = 0)
    private Long fld088;

    @Column(name = "fld_089", precision = 16, scale = 0)
    private Long fld089;

    @Column(name = "fld_090", precision = 10, scale = 0)
    private Long fld090;

    @Column(name = "fld_091", length = 3)
    private String fld091;

    @Column(name = "fld_092", length = 3)
    private String fld092;

    @Column(name = "fld_096", length = 999)
    private String fld096;

    @Column(name = "fld_097", length = 17)
    private String fld097;

    @Column(name = "fld_098", length = 25)
    private String fld098;

    @Column(name = "fld_099", length = 11)
    private String fld099;

    @Column(name = "fld_101", length = 17)
    private String fld101;

    @Column(name = "fld_103", length = 28)
    private String fld103;

    @Column(name = "fld_104", length = 100)
    private String fld104;

    @Column(name = "fld_105", precision = 16, scale = 0)
    private Long fld105;

    @Column(name = "fld_106", precision = 16, scale = 0)
    private Long fld106;

    @Column(name = "fld_107", precision = 10, scale = 0)
    private Long fld107;

    @Column(name = "fld_108", precision = 10, scale = 0)
    private Long fld108;

    @Column(name = "fld_109", length = 84)
    private String fld109;

    @Column(name = "fld_110", length = 84)
    private String fld110;

    @Column(name = "fld_111", length = 999)
    private String fld111;

    @Column(name = "fld_112", length = 999)
    private String fld112;

    @Column(name = "fld_113", length = 999)
    private String fld113;

    @Column(name = "fld_114", length = 999)
    private String fld114;

    @Column(name = "fld_115", length = 999)
    private String fld115;

    @Column(name = "fld_116", length = 999)
    private String fld116;

    @Column(name = "fld_117", length = 999)
    private String fld117;

    @Column(name = "fld_118", length = 999)
    private String fld118;

    @Column(name = "fld_119", length = 999)
    private String fld119;

    @Column(name = "fld_120", length = 999)
    private String fld120;

    @Column(name = "fld_121", length = 999)
    private String fld121;

    @Column(name = "fld_122", length = 999)
    private String fld122;

    @Column(name = "fld_123", length = 999)
    private String fld123;

    @Column(name = "fld_124", length = 999)
    private String fld124;

    @Column(name = "fld_125", length = 999)
    private String fld125;

    @Column(name = "fld_126", length = 999)
    private String fld126;

    @Column(name = "fld_127", length = 999)
    private String fld127;

    @Column(name = "fld_128")
    private byte[] fld128;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "centre_id", nullable = false)
    private ProcessingEntity processingEntity;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "fld_039", nullable = false)
    private AnswerCode answerCode;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "fld_049", insertable = false, updatable = false)
    private CurrencyCode currencyCodeByFld049;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "fld_051", insertable = false, updatable = false)
    private CurrencyCode currencyCodeByFld051;

}

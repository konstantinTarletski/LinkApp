package lv.bank.cards.core.entity.rtps;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "RTPSADMIN.stip_cards")
public class StipCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StipCardPK comp_id;

    @Column(name = "effective_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name = "purge_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purgeDate;

    @Column(name = "crd_holdr_name", length = 24)
    private String crdHoldrName;

    @Column(name = "crd_holdr_pwd", length = 20)
    private String crdHoldrPwd;

    @Column(name = "crd_holdr_msg", length = 99)
    private String crdHoldrMsg;

    @Column(name = "param_grp_1", length = 5)
    private String paramGrp1;

    @Column(name = "param_grp_2", length = 5)
    private String paramGrp2;

    @Column(name = "acnt_restr", length = 1)
    private String acntRestr;

    @Column(name = "avlamnt_flag", length = 1)
    private String avlamntFlag;

    @Column(name = "expiry_date_1")
    private String expiryDate1;

    @Column(name = "expiry_date_2")
    private String expiryDate2;

    @Column(name = "cvc_stripe_1", length = 3)
    private String cvcStripe1;

    @Column(name = "cvc_stripe_2", length = 3)
    private String cvcStripe2;

    @Column(name = "cvc_print_1", length = 3)
    private String cvcPrint1;

    @Column(name = "cvc_print_2", length = 3)
    private String cvcPrint2;

    @Column(name = "pvv_code_1", length = 4)
    private String pvvCode1;

    @Column(name = "pvv_code_2", length = 4)
    private String pvvCode2;

    @Column(name = "track_1_1", length = 76)
    private String track11;

    @Column(name = "track_1_2", length = 76)
    private String track12;

    @Column(name = "track_2_1", length = 37)
    private String track21;

    @Column(name = "track_2_2", length = 37)
    private String track22;

    @Column(name = "comm_grp", length = 3)
    private String commGrp;

    @Column(name = "pvv_code_1_chg", length = 4)
    private String pvvCode1Chg;

    @Column(name = "pvv_code_2_chg", length = 4)
    private String pvvCode2Chg;

    @Column(name = "pvv_code_1_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pvvCode1Date;

    @Column(name = "pvv_code_2_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pvvCode2Date;

    @Column(name = "pvv_code_1_prv", length = 4)
    private String pvvCode1Prv;

    @Column(name = "pvv_code_2_prv", length = 4)
    private String pvvCode2Prv;

    @Column(name = "add_info", length = 999)
    private String addInfo;

    @Column(name = "card_seq_1", length = 3)
    private String cardSeq1;

    @Column(name = "card_seq_2", length = 3)
    private String cardSeq2;

    @Column(name = "pki_1", length = 1)
    private String pki1;

    @Column(name = "pki_2", length = 1)
    private String pki2;

    @Column(name = "dki_1", length = 3)
    private String dki1;

    @Column(name = "dki_2", length = 3)
    private String dki2;

    @Column(name = "app_id", precision = 5, scale = 0)
    private Integer appId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "centre_id", insertable = false, updatable = false, referencedColumnName = "centre_id"),
            @JoinColumn(name = "crd_holdr_id", insertable = false, updatable = false, referencedColumnName = "crd_holdr_id")
    })
    private StipClient stipClient;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "stat_code_1", nullable = false)
    private AnswerCode answerCodeByStatCode1;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "stat_code_2", nullable = false)
    private AnswerCode answerCodeByStatCode2;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "centre_id", insertable = false, updatable = false, referencedColumnName = "centre_id"),
            @JoinColumn(name = "card_number", insertable = false, updatable = false, referencedColumnName = "card_number")
    })
    private Set<StipStoplist> stipStoplist;

}

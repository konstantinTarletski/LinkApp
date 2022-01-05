package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_cards")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdCard.to.lv.bank.cards.core.entity.linkApp.PcdCard",
        query = "select c from IzdCard as c left join c.izdCardsAddFields as f where c.ctime between :lastWaterMark and :currentWaterMark or "
                + "f.ctime between :lastWaterMark and :currentWaterMark or "
                + "c.card in (select ch.card from IzdChipCmdHist ch where ch.ctime between :lastWaterMark and :currentWaterMark and ch.data like '%DF0102%' ) ")
public class IzdCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "card", length = 19, nullable = false)
    private String card;

    @Column(name = "card_num", length = 9, nullable = false)
    private String binCode;

    @Column(name = "base_supp", length = 1, nullable = false)
    private String baseSupp;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "card_type", length = 2)
    private String cardType;

    @Column(name = "cond_set", length = 3, nullable = false)
    private String condSet;

    @Column(name = "status1", length = 1, nullable = false)
    private String status1;

    @Column(name = "status2", length = 1)
    private String status2;

    @Column(name = "expiry1", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry1;

    @Column(name = "expirity2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry2;

    @Column(name = "rec_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date recDate;

    @Column(name = "renew", length = 1)
    private String renew;

    @Column(name = "renew_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date renewDate;

    @Column(name = "card_name", length = 24)
    private String cardName;

    @Column(name = "mc_name", length = 26)
    private String mcName;

    @Column(name = "m_name", length = 20)
    private String MName;

    @Column(name = "relation", length = 25)
    private String relation;

    @Column(name = "id_card", length = 16)
    private String idCard;

    @Column(name = "b_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date BDate;

    @Column(name = "pin_nr", length = 8)
    private String pinNr;

    @Column(name = "pin_encr", length = 16)
    private String pinEncr;

    @Column(name = "cvc1_1", length = 3)
    private String cvc11;

    @Column(name = "cvc1_2", length = 3)
    private String cvc12;

    @Column(name = "cvc2_1", length = 3)
    private String cvc21;

    @Column(name = "cvc2_2", length = 3)
    private String cvc22;

    @Column(name = "pvv_1", length = 4)
    private String pvv1;

    @Column(name = "pvv_2", length = 4)
    private String pvv2;

    @Column(name = "pvv2_1", length = 4)
    private String pvv21;

    @Column(name = "pvv2_2", length = 4)
    private String pvv22;

    @Column(name = "track1_1", length = 79)
    private String track11;

    @Column(name = "track1_2", length = 79)
    private String track12;

    @Column(name = "track2_1", length = 40)
    private String track21;

    @Column(name = "track2_2", length = 40)
    private String track22;

    @Column(name = "pin_flag", length = 1)
    private String pinFlag;

    @Column(name = "pin_oper", length = 6)
    private String pinOper;

    @Column(name = "pin_recdat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pinRecdat;

    @Column(name = "region", length = 3)
    private String region;

    @Column(name = "purge_dat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purgeDat;

    @Column(name = "rout_flag", length = 1, nullable = false)
    private String routFlag;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "oper_owner", length = 6, nullable = false)
    private String operOwner;

    @Column(name = "cmpg_name", length = 24)
    private String cmpgName;

    @Column(name = "stop_cause", length = 1)
    private String stopCause;

    @Column(name = "insuranc_type", length = 2)
    private String insurancType;

    @Column(name = "insurance_flag", length = 1)
    private String insuranceFlag;

    @Column(name = "insuranc_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insurancDate;

    @Column(name = "crd_hold_msg", length = 99)
    private String crdHoldMsg;

    @Column(name = "u_field7", length = 20)
    private String UField7;

    @Column(name = "u_field8", length = 20)
    private String UField8;

    @Column(name = "u_cod9", length = 3)
    private String UCod9;

    @Column(name = "u_cod10", length = 6)
    private String UCod10;

    @Column(name = "in_file_num")
    private Integer inFileNum;

    @Column(name = "br_id")
    private Integer brId;

    @Column(name = "renew1_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date renew1Date;

    @Column(name = "collection", length = 1)
    private String collection;

    @Column(name = "coll_status")
    private Integer collStatus;

    @Column(name = "out_file_num")
    private BigDecimal outFileNum;

    @Column(name = "emb_session")
    private BigDecimal embSession;

    @Column(name = "renew_shadow", length = 1)
    private String renewShadow;

    @Column(name = "expiry2_shadow")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry2Shadow;

    @Column(name = "renewed_card", length = 19)
    private String renewedCard;

    @Column(name = "ls_account_no")
    private BigDecimal lsAccountNo;

    @Column(name = "call_id", length = 10)
    private String callId;

    @Column(name = "f_names", length = 34)
    private String FNames;

    @Column(name = "surname", length = 20)
    private String surname;

    @Column(name = "doc_since")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docSince;

    @Column(name = "play_flag", length = 1, nullable = false)
    private String playFlag;

    @Column(name = "mplay_flag", length = 1, nullable = false)
    private String mplayFlag;

    @Column(name = "sequence_nr", length = 3, nullable = false)
    private String sequenceNr;

    @Column(name = "last_seq_nr", length = 3)
    private String lastSeqNr;

    @Column(name = "last_emb_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEmbDatetime;

    @Column(name = "dki_0", length = 3)
    private String dki0;

    @Column(name = "dki_1", length = 3)
    private String dki1;

    @Column(name = "dki_2", length = 3)
    private String dki2;

    @Column(name = "individualized", length = 1, nullable = false)
    private String individualized;

    @Column(name = "track2_equiv_data", length = 40)
    private String track2EquivData;

    @Column(name = "individualized_prev", length = 1)
    private String individualizedPrev;

    @Column(name = "mb_phone", length = 15)
    private String mbPhone;

    @Column(name = "mb_key_pub", length = 2048)
    private String mbKeyPub;

    @Column(name = "mb_key_priv", length = 2048)
    private String mbKeyPriv;

    @Column(name = "mb_sim_id", length = 30)
    private String mbSimId;

    @Column(name = "mb_mcard", length = 1)
    private Integer mbMcard;

    @Column(name = "mb_status", length = 1)
    private Integer mbStatus;

    @Column(name = "mb_lang", length = 2)
    private String mbLang;

    @Column(name = "pvki_1", length = 1)
    private String pvki1;

    @Column(name = "pvki_2", length = 1)
    private String pvki2;

    @Column(name = "in_order_number")
    private BigInteger inOrderNumber;

    @Column(name = "pin_zpk", length = 16)
    private String pinZpk;

    @Column(name = "card_fees", length = 20, nullable = false)
    private String cardFees;

    @Column(name = "risk_level", length = 5)
    private String riskLevel;

    @Column(name = "design_id", length = 10)
    private BigDecimal designId;

    @Column(name = "ins_begin_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insBeginDate;

    @Column(name = "ins_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insEndDate;

    @Column(name = "chip_app_id")
    private BigDecimal chipAppId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "card", insertable = false, updatable = false, referencedColumnName = "card")
    private IzdStoplist izdStoplist;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "card", insertable = false, updatable = false, referencedColumnName = "card")
    private IzdCardsAddFields izdCardsAddFields;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "card_type", insertable = false, updatable = false, referencedColumnName = "c_type"),
            @JoinColumn(name = "groupc", insertable = false, updatable = false, referencedColumnName = "groupc"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private IzdCardType izdCardType;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "cl_acct_key", referencedColumnName = "tab_key")
    private IzdClAcct izdClAcct;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "agreement_key", referencedColumnName = "agre_nom")
    private IzdAgreement izdAgreement;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany
    @JoinColumn(name = "card", insertable = false, updatable = false, referencedColumnName = "card")
    private Set<IzdCardsPinBlocks> izdCardsPinBlocks;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "card_num", insertable = false, updatable = false, referencedColumnName = "bin_code"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "groupc", insertable = false, updatable = false, referencedColumnName = "card_group_code")
    })
    private IzdBinTable izdBinTable;


    public IzdCardsPinBlocks getIzdCardsPinBlock(){
        if(izdCardsPinBlocks == null || izdCardsPinBlocks.size() == 0)
            return null;
        for (IzdCardsPinBlocks b : izdCardsPinBlocks) {
            if (sequenceNr.equals(b.getCompId().getCardSeq()))
                return b;
        }
        return null;
    }

}

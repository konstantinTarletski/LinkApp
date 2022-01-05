package lv.bank.cards.core.entity.linkApp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "pcd_cards")
public class PcdCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CARD", nullable = false)
    private String card;

    @Column(name = "CARD_NAME", length = 24)
    private String cardName;

    @Column(name = "BIN_CODE", length = 9)
    private String binCode;

    @Column(name = "EXPIRY1", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry1;

    @Column(name = "EXPIRY2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry2;

    @Column(name = "CTIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "GROUPC", length = 2, nullable = false)
    private String groupc;

    @Column(name = "C_TYPE", length = 2)
    private String cardType;

    @Column(name = "BANK_C", length = 2, nullable = false)
    private String bankC;

    @Column(name = "AGREEMENT")
    private Long agreement;

    @Column(name = "MC_NAME", length = 52)
    private String mcName;

    @Column(name = "M_NAME", length = 40)
    private String MName;

    @Column(name = "CVC1_1", length = 3)
    private String cvc11;

    @Column(name = "CVC1_2", length = 3)
    private String cvc12;

    @Column(name = "CVC2_1", length = 3)
    private String cvc21;

    @Column(name = "CVC2_2", length = 3)
    private String cvc22;

    @Column(name = "U_COD9", length = 3)
    private String UCod9;

    @Column(name = "U_COD10", length = 6)
    private String UCod10;

    @Column(name = "ID_CARD", length = 16)
    private String idCard;

    @Column(name = "U_FIELD7", length = 20)
    private String UField7;

    @Column(name = "COND_SET", length = 3, nullable = false)
    private String condSet;

    @Column(name = "RENEW", length = 1)
    private String renew;

    @Column(name = "DELIVERY_BLOCK", length = 1)
    private String deliveryBlock;

    @Column(name = "MAXIMA", length = 1)
    private String maxima;

    @Column(name = "MEDIATOR", length = 1)
    private String mediator;

    @Column(name = "STATUS1", length = 1, nullable = false)
    private String status1;

    @Column(name = "STATUS2", length = 1, nullable = false)
    private String status2;

    @Column(name = "BASE_SUPP", length = 1)
    private String baseSupp;

    @Column(name = "RISK_LEVEL", length = 5)
    private String riskLevel;

    @Column(name = "DESIGN_ID", length = 10)
    private BigDecimal designId;

    @Column(name = "INSURANC_TYPE", length = 2)
    private String insurancType;

    @Column(name = "INSURANCE_FLAG", length = 1)
    private String insuranceFlag;

    @Column(name = "BR_ID", length = 7)
    private Integer brId;

    @Column(name = "CMPG_NAME", length = 24)
    private String cmpgName;

    @Column(name = "IN_ORDER_NUMBER", length = 20)
    private BigInteger inOrderNumber;

    @Column(name = "REC_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recDate;

    @Column(name = "U_FIELD8", length = 20)
    private String UField8;

    @Column(name = "INS_BEGIN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insBeginDate;

    @Column(name = "INS_END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insEndDate;

    @Column(name = "CHIP_APP_ID", length = 10)
    private BigDecimal chipAppId;

    @Column(name = "OPERATOR", length = 24)
    private String operator;

    @Column(name = "CREATION_BRANCH", length = 4)
    private String creationBranch;

    @Column(name = "RENEW_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date renewDate;

    @Column(name = "RENEW1_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date renew1Date;

    @Column(name = "DESIGN", length = 24)
    private String design;

    @Column(name = "U_AFIELD1", length = 200)
    private String uAField1;

    @Column(name = "U_AFIELD2", length = 200)
    private String uAField2;

    @Column(name = "U_AFIELD3", length = 200)
    private String uAField3;

    @Column(name = "U_AFIELD4", length = 200)
    private String uAField4;

    @Column(name = "U_AFIELD5", length = 200)
    private String uAField5;

    @Column(name = "U_AFIELD6", length = 200)
    private String uAField6;

    @Column(name = "U_AFIELD7", length = 200)
    private String uAField7;

    @Column(name = "U_BFIELD1", length = 400)
    private String uBField1;

    @Column(name = "U_ACODE11", length = 3)
    private String uACode11;

    @Column(name = "U_ACODE12", length = 3)
    private String uACode12;

    @Column(name = "PIN_BLOCK", length = 32)
    private String pinBlock;

    @Column(name = "contactless")
    private Integer contactless;

    @Column(name = "renew_old", length = 1)
    private String renewOld;

    @Column(name = "region", length = 3)
    private String region;

    @Column(name = "issue_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;

    @Column(name = "issued_by", length = 24)
    private String issuedBy;

    @Column(name = "renewed_card", length = 19)
    private String renewedCard;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "AGREEMENT", insertable = false, updatable = false)
    private PcdAgreement pcdAgreement;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "C_TYPE", insertable = false, updatable = false, referencedColumnName = "c_type"),
            @JoinColumn(name = "BANK_C", insertable = false, updatable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "GROUPC", insertable = false, updatable = false, referencedColumnName = "groupc")
    })
    private PcdCardType pcdCardType;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "BIN_CODE", insertable = false, updatable = false, referencedColumnName = "bin_code"),
            @JoinColumn(name = "BANK_C", insertable = false, updatable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "GROUPC", insertable = false, updatable = false, referencedColumnName = "groupc")
    })
    private PcdBin pcdBin;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pcd_cards_accounts",
            joinColumns = {@JoinColumn(name = "CARD")},
            inverseJoinColumns = {@JoinColumn(name = "ACCOUNT_NO")})
    private Set<PcdAccount> pcdAccounts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdCard")
    @OrderBy(value = "regDate DESC")
    private List<PcdPpCard> pcdPpCards;

    @Column(name = "STOP_CAUSE", length = 1)
    private String stopCause;

    public String getNextRenewOld() {
        if (Arrays.asList("A", "E", "5", "6").contains(renew)) {
            return "E";
        } else if (Arrays.asList("2", "D").contains(renew)) {
            return "D";
        } else if (Arrays.asList("3", "R").contains(renew)) {
            return "R";
        } else if ("N".equals(renew)) {
            return "N";
        } else {
            return "J";
        }
    }
}

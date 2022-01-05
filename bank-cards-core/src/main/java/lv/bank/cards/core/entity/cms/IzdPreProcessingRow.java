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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "izd_pre_processing_row")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
//@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdPreProcessingRow.to.lv.bank.cards.core.entity.linkApp.PcdPreProcessingRow",
//	query = "from IzdPreProcessingRow pprow where :currentWaterMark and pprow.regDate and pprow.trType in ('325','326','32A','32B','32D','32H','32S','32T') and :currentWaterMark and :lastWaterMark and pprow.accountNo not in (select lvlAccountNo from IzdLvl2eurAccountsMap)")
public class IzdPreProcessingRow implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "internal_no", precision = 22, scale = 0, nullable = false)
    private BigDecimal internalNo;

    /**
     * Related card number
     */
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "r_card")
    private IzdCard izdCards;

    @Column(name = "account_no", precision = 20, scale = 0, nullable = false)
    private BigDecimal accountNo;

    /**
     * Transaction type
     */
    @Column(name = "tr_type", length = 3, nullable = false)
    private String trType;

    /**
     * Related internal account number
     */
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "r_account_no")
    private IzdAccount izdAccountsByRAccountNo;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_code"),
            @JoinColumn(name = "branch", insertable = false, updatable = false, referencedColumnName = "branch")
    })
    private IzdBranch izdBranches;

    /**
     * Registration date
     */
    @Column(name = "reg_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    /**
     * Processing ID
     */
    @Column(name = "proc_id", precision = 22, scale = 0, nullable = false)
    private BigDecimal procId;

    /**
     * Currency
     */
    @Column(name = "ccy", length = 3, nullable = false)
    private String ccy;

    /**
     * Transaction execution type
     */
    @Column(name = "execution_type", precision = 2, scale = 0, nullable = false)
    private Byte executionType;

    /**
     * Date, when transaction should have to be executed
     */
    @Column(name = "execute_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date executeOn;

    /**
     * Transaction debit/credit sign
     */
    @Column(name = "deb_cred", length = 1, nullable = false)
    private String debCred;

    /**
     * Transaction amount
     */
    @Column(name = "amount", precision = 12, scale = 0, nullable = false)
    private Long amount;

    /**
     * Card group code
     */
    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    /**
     * User ID
     */
    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    /**
     * Date and time, when last changes was done
     */
    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    /**
     * Batch number
     */
    @Column(name = "batch_nr", length = 7)
    private String batchNr;

    /**
     * Slip number
     */
    @Column(name = "slip_nr", length = 8)
    private String slipNr;

    /**
     * Deal description
     */
    @Column(name = "deal_desc", length = 140)
    private String dealDesc;

    /**
     * Counterparty
     */
    @Column(name = "counterparty", length = 200)
    private String counterparty;

    /**
     * Lock ID
     */
    @Column(name = "lock_id", length = 111)
    private String lockId;

    @Column(name = "accnt_cond_set", length = 3)
    private String accntCondSet;

    @Column(name = "card_cond_set", length = 3)
    private String cardCondSet;

    @Column(name = "unique_ref_nr", length = 99)
    private String uniqueRefNr;

    /**
     * Card number
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "card")
    private IzdCard card;

    /**
     * Transaction date and time
     */
    @Column(name = "tran_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranDateTime;

    /**
     * Transaction belonging to bank, branch (for views)
     */
    @Column(name = "b_br_id", precision = 7, scale = 0)
    private Integer BBrId;

    /**
     * Bank code (for payments and adjustments)
     */
    @Column(name = "bank", length = 2)
    private String bank;

    /**
     * Card account number
     */
    @Column(name = "card_acct", length = 20)
    private String cardAcct;

    /**
     * Internal account group ID (in izd_cl_acct)
     */
    @Column(name = "tab_key", precision = 20, scale = 0)
    private BigDecimal tabKey;

    /**
     * Client code
     */
    @Column(name = "client", length = 8)
    private String client;

    /**
     * Payment line input seq. (manual input)
     */
    @Column(name = "payment_line_no", precision = 4, scale = 0)
    private Short paymentLineNo;

    /**
     * Transaction amount, issuer bank currency
     */
    @Column(name = "i_amount", precision = 12, scale = 0)
    private Long IAmount;

    /**
     * Net amount (amount - bonus)
     */
    @Column(name = "amount_net", precision = 12, scale = 0)
    private Long amountNet;

    /**
     * Error code
     */
    @Column(name = "err_code", length = 2)
    private String errCode;

    /**
     * Input ASCII file internal ID number
     */
    @Column(name = "infile_num", precision = 20, scale = 0)
    private BigDecimal infileNum;

    /**
     * Control
     */
    @Column(name = "control", length = 1)
    private String control;

    /**
     * Transaction currency code
     */
    @Column(name = "tran_ccy", length = 3)
    private String tranCcy;

    /**
     * Transaction amount
     */
    @Column(name = "tran_amt", precision = 12, scale = 0)
    private Long tranAmt;

    /**
     * Transaction fee (calculated according card conditions)
     */
    @Column(name = "tr_fee", precision = 12, scale = 0)
    private Long trFee;

    /**
     * Transaction code - for transaction fee
     */
    @Column(name = "tr_code", length = 3)
    private String trCode;

    @Column(name = "d_c")
    private Boolean DC;

    /**
     * Reference number
     */
    @Column(name = "acqref_nr", length = 23)
    private String acqrefNr;

    /**
     * Number of decimals in transaction currency
     */
    @Column(name = "ccy_exp", length = 1)
    private String ccyExp;

    /**
     * Transaction owner bank
     */
    @Column(name = "bank_o", length = 2)
    private String bankO;

    /**
     * Transaction owner branch code
     */
    @Column(name = "branch_o", length = 3)
    private String branchO;

    /**
     * Reference number
     */
    @Column(name = "ref_number", length = 12)
    private String refNumber;

    /**
     * Locks sending from RTPS to CMS time
     */
    @Column(name = "lock_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockTime;

    /**
     * Authorization code (for SMS transactions)
     */
    @Column(name = "apr_code", length = 6)
    private String aprCode;

    /**
     * Merchant code
     */
    @Column(name = "merchant", length = 15)
    private String merchant;

    /**
     * System Trace Audit Number
     */
    @Column(name = "stan", length = 12)
    private String stan;

    /**
     * Point Code
     */
    @Column(name = "point_code", length = 12)
    private String pointCode;

    /**
     * Merchant Category Code
     */
    @Column(name = "mcc_code", length = 4)
    private String mccCode;

    /**
     * Merchant name
     */
    @Column(name = "abvr_name", length = 27)
    private String abvrName;

    /**
     * Merchant city
     */
    @Column(name = "city", length = 20)
    private String city;

    /**
     * Merchant country
     */
    @Column(name = "country", length = 3)
    private String country;

    /**
     * Terminal identification
     */
    @Column(name = "term_id", length = 8)
    private String termId;

    /**
     * Processing code
     */
    @Column(name = "proc_code", length = 2)
    private String procCode;

    @Column(name = "fld_098", length = 25)
    private String fld098;

    @Column(name = "fld_104", length = 100)
    private String fld104;

    @Column(name = "card_seq", length = 3)
    private String cardSeq;

    @Column(name = "accnt_amt", precision = 12, scale = 0)
    private Long accntAmt;

    @Column(name = "add_info", length = 4000)
    private String addInfo;

    @Column(name = "service_type", length = 16)
    private String serviceType;

    @Column(name = "service_code", length = 3)
    private String serviceCode;

    /**
     * Row_numb of corresponding offline lock
     */
    @Column(name = "row_numb", precision = 12, scale = 0)
    private Long rowNumb;

    @Column(name = "event_area", length = 10)
    private String eventArea;

    @Column(name = "tr_fee2", precision = 12, scale = 0)
    private Long trFee2;

    @Column(name = "tr_code2", length = 3)
    private String trCode2;

    @Column(name = "tr_fee3", precision = 12, scale = 0)
    private Long trFee3;

    @Column(name = "tr_code3", length = 3)
    private String trCode3;

    @Column(name = "tr_fee4", precision = 12, scale = 0)
    private Long trFee4;

    @Column(name = "tr_code4", length = 3)
    private String trCode4;

    @Column(name = "tr_fee5", precision = 12, scale = 0)
    private Long trFee5;

    @Column(name = "tr_code5", length = 3)
    private String trCode5;

    @Column(name = "tr_fee6", precision = 12, scale = 0)
    private Long trFee6;

    @Column(name = "tr_code6", length = 3)
    private String trCode6;

    @Column(name = "tr_fee7", precision = 12, scale = 0)
    private Long trFee7;

    @Column(name = "tr_code7", length = 3)
    private String trCode7;

    @Column(name = "tr_fee8", precision = 12, scale = 0)
    private Long trFee8;

    @Column(name = "tr_code8", length = 3)
    private String trCode8;

    @Column(name = "tr_fee9", precision = 12, scale = 0)
    private Long trFee9;

    @Column(name = "tr_code9", length = 3)
    private String trCode9;

    @Column(name = "tr_fee10", precision = 12, scale = 0)
    private Long trFee10;

    @Column(name = "tr_code10", length = 3)
    private String trCode10;

}

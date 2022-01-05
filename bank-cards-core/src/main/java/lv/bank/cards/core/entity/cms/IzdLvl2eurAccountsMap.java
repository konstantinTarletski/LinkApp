package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_lvl2eur_accounts_map")
public class IzdLvl2eurAccountsMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "lvl_account_no", precision = 0, nullable = false)
    private Double lvlAccountNo;

    @Column(name = "lvl_tab_key", precision = 0, nullable = false)
    private Double lvlTabKey;

    @Column(name = "lvl_main_row", precision = 0, nullable = false)
    private Double lvlMainRow;

    @Column(name = "lvl_acc_prty", length = 1)
    private String lvlAccPrty;

    @Column(name = "lvl_end_bal", precision = 0)
    private Double lvlEndBal;

    @Column(name = "lvl_crd", precision = 0)
    private Double lvlCrd;

    @Column(name = "lvl_crd_expiry")
    private Timestamp lvlCrdExpiry;

    @Column(name = "lvl_auth_bonus", precision = 0)
    private Double lvlAuthBonus;

    @Column(name = "lvl_ab_expirity")
    private Timestamp lvlAbExpirity;

    @Column(name = "lvl_debt", precision = 0)
    private Double lvlDebt;

    @Column(name = "lvl_debt1", precision = 0)
    private Double lvlDebt1;

    @Column(name = "lvl_initial_amount", precision = 0)
    private Double lvlInitialAmount;

    @Column(name = "lvl_lock_amount_cms", precision = 0)
    private Double lvlLockAmountCms;

    @Column(name = "lvl_locked_amnt", precision = 0)
    private Double lvlLockedAmnt;

    @Column(name = "lvl_non_reduce_bal", precision = 0)
    private Double lvlNonReduceBal;

    @Column(name = "lvl_ac_status", length = 1)
    private String lvlAcStatus;

    @Column(name = "lvl_lock_count", precision = 0)
    private Double lvlLockCount;

    @Column(name = "eur_account_no", precision = 0, nullable = false)
    private Double eurAccountNo;

    @Column(name = "eur_tab_key", precision = 0, nullable = false)
    private Double eurTabKey;

    @Column(name = "eur_main_row", precision = 0, nullable = false)
    private Double eurMainRow;

    @Column(name = "eur_acc_prty", length = 1)
    private String eurAccPrty;

    @Column(name = "eur_end_bal", precision = 0)
    private Double eurEndBal;

    @Column(name = "eur_crd", precision = 0)
    private Double eurCrd;

    @Column(name = "eur_crd_expiry")
    private Timestamp eurCrdExpiry;

    @Column(name = "eur_auth_bonus", precision = 0)
    private Double eurAuthBonus;

    @Column(name = "eur_ab_expirity")
    private Timestamp eurAbExpirity;

    @Column(name = "eur_debt", precision = 0)
    private Double eurDebt;

    @Column(name = "eur_debt1", precision = 0)
    private Double eurDebt1;

    @Column(name = "eur_initial_amount", precision = 0)
    private Double eurInitialAmount;

    @Column(name = "eur_lock_amount_cms", precision = 0)
    private Double eurLockAmountCms;

    @Column(name = "eur_locked_amnt", precision = 0)
    private Double eurLockedAmnt;

    @Column(name = "eur_non_reduce_bal", precision = 0)
    private Double eurNonReduceBal;

    @Column(name = "eur_ac_status", length = 1)
    private String eurAcStatus;

    @Column(name = "eur_lock_count", precision = 0)
    private Double eurLockCount;

    @Column(name = "client", length = 8, nullable = false)
    private String client;

    @Column(name = "card_acct", length = 34, nullable = false)
    private String cardAcct;

    @Column(name = "bank_c", length = 8, nullable = false)
    private String bankC;

    @Column(name = "groupc", length = 8, nullable = false)
    private String groupc;

}

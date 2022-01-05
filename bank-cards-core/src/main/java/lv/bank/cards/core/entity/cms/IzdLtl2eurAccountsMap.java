package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "izd_ltl2eur_accounts_map")
public class IzdLtl2eurAccountsMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "iban_accnt", length = 21, nullable = false)
    private String ibanAccnt;

    @Column(name = "ltl_account_no", precision = 0, nullable = false)
    private Double ltlAccountNo;

    @Column(name = "ltl_tab_key", precision = 0, nullable = false)
    private Double ltlTabKey;

    @Column(name = "ltl_main_row", precision = 0, nullable = false)
    private Double ltlMainRow;

    @Column(name = "ltl_acc_prty", length = 1)
    private String ltlAccPrty;

    @Column(name = "ltl_card_acct", length = 20, nullable = false)
    private String ltlCardAcct;

    @Column(name = "ltl_ac_status", length = 1)
    private String ltlAcStatus;

    @Column(name = "eur_account_no", length = 20)
    private Long eurAccountNo;

    @Column(name = "eur_tab_key", precision = 0)
    private Double eurTabKey;

    @Column(name = "eur_main_row", precision = 0)
    private Double eurMainRow;

    @Column(name = "eur_acc_prty", length = 1)
    private String eurAccPrty;

    @Column(name = "eur_ac_status", length = 1)
    private String eurAcStatus;

    @Column(name = "eur_card_acct", length = 20, nullable = false)
    private String eurCardAcct;

    @Column(name = "client", length = 8)
    private String client;

    @Column(name = "bank_c", length = 2)
    private String bankC;

    @Column(name = "groupc", length = 2)
    private String groupc;

}

package lv.bank.cards.link;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {

    //LT only fields
    @JsonProperty("old_card")
    private String oldCardNumber;
    @JsonProperty("application_type")
    private String applicationType;
    @JsonProperty("pin_delivery_bt")
    private String pinDeliveryBt;
    @JsonProperty("replacement_reason")
    private String replacementReason;
    @JsonProperty("off_cond_set")
    private String offlineConditionSet;
    @JsonProperty("employer_name")
    private String cardHolderEmployerName;
    @JsonProperty("card_account")
    private String cardAccountNoCms;
    @JsonProperty("u_field5")
    private String uField5;
    @JsonProperty("account_condition_set")
    private String accountCondSet;
    @JsonProperty("card_condition_set")
    private String cardConditionSet;
    @JsonProperty("with_insurance")
    private String cardInsuranceFlag;
    @JsonProperty("chip_design_id")
    private String chipDesignId;
    @JsonProperty("maxima")
    private String cardMaximaFlag;
    @JsonProperty("auto_renew_flag")
    private String cardRenewFlag;

    //Custom, not from CORE systems
    private BigDecimal accountNoCms;

    private String action;
    private String bankc;
    private String groupc;
    private String orderId;
    @JsonProperty("card")
    private String cardNumber;
    @JsonProperty("crd_email")
    private String email;
    @JsonProperty("account_cif")
    private String clientNumberInAbs;
    @JsonProperty("client_type")
    private String clientType;
    @JsonProperty("client_category")
    private String clientCategory;
    private String resident;
    private String bin;
    @JsonProperty("account_ccy")
    private String accountCcy;
    @JsonProperty("account_no")
    private String accountNoPlaton;
    @JsonProperty("base_sup")
    private String baseSupp;
    @JsonProperty("crd_name")
    private String cardName;
    @JsonProperty("crd_expiry")
    private String cardExpiry;
    @JsonProperty("crd_period")
    private String cardValidityPeriod;
    private String branch;
    @JsonProperty("rep_lang")
    private String reportLanguage;
    @JsonProperty("design_id")
    private String designId;
    @JsonProperty("application_id")
    private String applicationId;
    private String country;
    @JsonProperty("own_addr_street")
    private String clientStreet;
    @JsonProperty("own_addr_city")
    private String clientCity;
    @JsonProperty("own_addr_zip")
    private String clientZip;
    @JsonProperty("own_addr_country")
    private String clientCountry;
    @JsonProperty("crd_addr_street")
    private String stmtStreet;
    @JsonProperty("crd_addr_city")
    private String stmtCity;
    @JsonProperty("crd_addr_zip")
    private String stmtZip;
    @JsonProperty("crd_addr_country")
    private String stmtCountry;
    @JsonProperty("rep_distribution_mode")
    private String repDistributionMode;
    private String productId;
    @JsonProperty("own_firstname")
    private String ownerFirstname;
    @JsonProperty("own_lastname")
    private String ownerLastname;
    @JsonProperty("own_birthday")
    private String ownerBirthday;
    @JsonProperty("birthday_mask")
    private String birthdayMask;
    @JsonProperty("cl_firstname")
    private String clientFirstname;
    @JsonProperty("cl_lastname")
    private String clientLastname;
    @JsonProperty("cl_birthday")
    private String clientBirthday;
    @JsonProperty("crd_passwd")
    private String crdPasswd;
    @JsonProperty("crd_fee")
    private String crdFee;
    @JsonProperty("crd_sort")
    private String crdSort;
    @JsonProperty("emp_company")
    private String empCompanyRegNr;
    @JsonProperty("own_company_name")
    private String ownCompanyName;
    @JsonProperty("crd_notify")
    private String authNotifyDestination;
    @JsonProperty("own_name")
    private String ownerName;
    @JsonProperty("own_phone")
    private String ownerPhone;
    @JsonProperty("own_code")
    private String ownerPersonId;
    @JsonProperty("own_since")
    private String bankClientSince;
    @JsonProperty("cl_code")
    private String clientPersonId;
    @JsonProperty("u_cod9")
    private String uCod9;
    @JsonProperty("crd_place")
    private String branchToDeliverAt;
    @JsonProperty("fill_place_ng")
    private String fillPlaceNg;
    @JsonProperty("card_type")
    private String cardType;
    @JsonProperty("crd_auto_block")
    private String autoBlockCard;
    private String prettyfyCard;
    @JsonProperty("auth_level")
    private String authLevel;
    private String client;
    @JsonProperty("agreement_key")
    private String agreementKey;
    @JsonProperty("cl_acct_key")
    private String clAcctKey;
    @JsonProperty("dlv_address")
    private String dlvAddress;
    @JsonProperty("dlv_addr_code")
    private String dlvAddrCode;
    @JsonProperty("dlv_addr_country")
    private String dlvAddrCountry;
    @JsonProperty("dlv_addr_city")
    private String dlvAddrCity;
    @JsonProperty("dlv_addr_street1")
    private String dlvAddrStreet1;
    @JsonProperty("dlv_addr_street2")
    private String dlvAddrStreet2;
    @JsonProperty("dlv_addr_zip")
    private String dlvAddrZip;
    @JsonProperty("dlv_company")
    private String dlvCompany;
    @JsonProperty("dlv_language")
    private String dlvLanguage;
    private String repeated;
    @JsonProperty("block_card_no")
    private String blockCardNo;
    @JsonProperty("replace_name")
    private String replaceName;
    @JsonProperty("min_salary_account")
    private String minSalaryAccount;
    @JsonProperty("migrated_card_number")
    private String migratedCardNumber;
    @JsonProperty("migrated_card_pin_block")
    private String migratedCardPinBlock;
    @JsonProperty("migrated_card_pin_key_id")
    private String migratedCardPinKeyId;
    @JsonProperty("risk_level")
    private String riskLevel;
    private String iban;
}

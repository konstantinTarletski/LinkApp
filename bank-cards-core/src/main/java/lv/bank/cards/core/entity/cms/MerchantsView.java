package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchants_view")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.all.items.lv.bank.cards.core.entity.cms.MerchantsView.to.lv.bank.cards.core.entity.linkApp.PcdMerchant",
        query = "select m from MerchantsView m")
public class MerchantsView implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "merchant", length = 15, nullable = false)
    private String merchant;

    @Column(name = "parent", length = 15)
    private String parent;

    @Column(name = "abrv_name", length = 27)
    private String abrvName;

    @Column(name = "fax", length = 11)
    private String fax;

    @Column(name = "full_name", length = 78)
    private String fullName;

    @Column(name = "cntry", length = 3)
    private String cntry;

    @Column(name = "city", length = 15)
    private String city;

    @Column(name = "reg_nr", length = 25)
    private String regNr;

    @Column(name = "street", length = 30)
    private String street;

    @Column(name = "post_ind", length = 8)
    private String postInd;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "cont_person", length = 30)
    private String contPerson;

    @Column(name = "mcc", length = 4)
    private String mcc;

    @Column(name = "p_cntry", length = 3)
    private String PCntry;

    @Column(name = "p_city", length = 15)
    private String PCity;

    @Column(name = "p_street", length = 30)
    private String PStreet;

    @Column(name = "p_post_ind", length = 8)
    private String PPostInd;

    @Column(name = "mrc_phone", length = 11)
    private String mrcPhone;

    @Column(name = "reports", length = 64)
    private String reports;

    @Column(name = "merchant_luhn_code", length = 1)
    private String merchantLuhnCode;

    @Column(name = "region", length = 3)
    private String region;

    @Column(name = "bank", length = 2)
    private String bank;

    @Column(name = "branch", length = 3)
    private String branch;

    @Column(name = "va_certified", length = 1)
    private String vaCertified;

    @Column(name = "user_field_001", length = 32)
    private String userField001;

    @Column(name = "user_field_002", length = 32)
    private String userField002;

    @Column(name = "user_field_003", length = 32)
    private String userField003;

    @Column(name = "user_field_004", length = 64)
    private String userField004;

    @Column(name = "user_field_005", length = 30)
    private String userField005;

    @Column(name = "yd_flag", precision = 0)
    private Double ydFlag;

    @Column(name = "user_field_006", length = 30)
    private String userField006;

    @Column(name = "risk_indicator", length = 1)
    private String riskIndicator;

}

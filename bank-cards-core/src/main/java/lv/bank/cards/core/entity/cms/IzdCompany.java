package lv.bank.cards.core.entity.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "izd_companies")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdCompany.to.lv.bank.cards.core.entity.linkApp.PcdCompany",
        query = "select c from IzdCompany c where c.ctime between :lastWaterMark and :currentWaterMark")
public class IzdCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdCompanyPK comp_id;

    @Column(name = "short_name", length = 24, nullable = false)
    private String shortName;

    @Column(name = "name", length = 70)
    private String name;

    @Column(name = "okp_code", length = 15)
    private String okpCode;

    @Column(name = "reg_code_ur", length = 20)
    private String regCodeUr;

    @Column(name = "reg_code_vid", length = 20)
    private String regCodeVid;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "fax_no", length = 15)
    private String faxNo;

    @Column(name = "e_mails", length = 40)
    private String EMails;

    @Column(name = "in_path", length = 256)
    private String inPath;

    @Column(name = "out_path", length = 256)
    private String outPath;

    @Column(name = "street", length = 60)
    private String street;

    @Column(name = "city", length = 20)
    private String city;

    @Column(name = "country", length = 3)
    private String country;

    @Column(name = "post_ind", length = 7)
    private String postInd;

    @Column(name = "coment", length = 250)
    private String coment;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "comp_search_name", length = 24)
    private String compSearchName;

    @Column(name = "encrypt_key", length = 40)
    private String encryptKey;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "emp_code", insertable = false, updatable = false, referencedColumnName = "code")
    })
    private Set<IzdClient> izdClientsByEmpCodeAndBankC;

}

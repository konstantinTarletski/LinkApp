package lv.bank.cards.core.entity.linkApp;

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
import java.util.List;

@Data
@Entity
@Table(name = "pcd_clients")
public class PcdClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdClientPK comp_id;

    @Column(name = "first_names", length = 70)
    private String firstNames;

    @Column(name = "r_e_mails", length = 200)
    private String REMails;

    @Column(name = "r_street", length = 95)
    private String RStreet;

    @Column(name = "r_city", length = 40)
    private String RCity;

    @Column(name = "r_cntry", length = 3)
    private String RCntry;

    @Column(name = "cl_type", length = 1)
    private String clType;

    @Column(name = "r_pcode", length = 14)
    private String RPcode;

    @Column(name = "r_phone", length = 15)
    private String RPhone;

    @Column(name = "person_code", length = 20)
    private String personCode;

    @Column(name = "client_b", length = 19)
    private String clientB;

    @Column(name = "coment", length = 800)
    private String coment;

    @Column(name = "m_name", length = 40)
    private String MName;

    @Column(name = "resident", length = 1, nullable = false)
    private String resident;

    @Column(name = "emp_name", length = 68)
    private String empName;

    @Column(name = "cmp_name", length = 68)
    private String cmpName;

    @Column(name = "reg_nr", length = 25)
    private String regNr;

    public String getRegNr() {
        return regNr;
    }

    public void setRegNr(String regNr) {
        this.regNr = regNr;
    }

    @Column(name = "last_name", length = 40)
    private String lastName;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "emp_code", length = 5)
    private String empCode;

    @Column(name = "company_code", length = 5)
    private String companyCode;

    @Column(name = "region", length = 2)
    private String region;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "bank_c", insertable = false, updatable = false)
    private PcdDbOwner pcdDbOwner;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "company_code", insertable = false, updatable = false, referencedColumnName = "code"),
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
    })
    private PcdCompany pcdCompany;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdClient", fetch = FetchType.LAZY)
    private List<PcdAgreement> pcdAgreements;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdClient", fetch = FetchType.LAZY)
    private List<PcdAccount> pcdAccounts;

}

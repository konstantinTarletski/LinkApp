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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_clients")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdClient.to.lv.bank.cards.core.entity.linkApp.PcdClient",
        query = "select c from IzdClient c where c.ctime between :lastWaterMark and :currentWaterMark")
public class IzdClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdClientPK comp_id;

    @Column(name = "r_e_mails", length = 100)
    private String REMails;

    @Column(name = "r_street", length = 95)
    private String RStreet;

    @Column(name = "r_city", length = 20)
    private String RCity;

    @Column(name = "r_cntry", length = 3)
    private String RCntry;

    @Column(name = "r_pcode", length = 7)
    private String RPcode;

    @Column(name = "r_phone", length = 15)
    private String RPhone;

    @Column(name = "coment", length = 400)
    private String coment;

    @Column(name = "resident", length = 1, nullable = false)
    private String resident;

    @Column(name = "id_card", length = 16)
    private String idCard;

    @Column(name = "emp_name", length = 34)
    private String empName;

    @Column(name = "cmp_name", length = 34)
    private String cmpName;

    @Column(name = "cln_cat", length = 3)
    private String clnCat;

    @Column(name = "reg_nr", length = 25)
    private String regNr;

    @Column(name = "client_b", length = 19)
    private String clientB;

    @Column(name = "cl_type", length = 1)
    private String clType;

    @Column(name = "rec_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date recDate;

    @Column(name = "f_names", length = 34)
    private String FNames;

    @Column(name = "surname", length = 20)
    private String surname;

    @Column(name = "m_name", length = 20)
    private String MName;

    @Column(name = "person_code", length = 20)
    private String personCode;

    @Column(name = "u_field2", length = 20)
    private String UField2;

    @Column(name = "emp_code", length = 5)
    private String empCode;

    @Column(name = "region", length = 2)
    private String region;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdClient", fetch = FetchType.LAZY)
    private Set<IzdAgreement> izdAgreements;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

}

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
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name = "izd_db_owners")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
@NamedQuery(name = "get.updated.items.lv.bank.cards.core.entity.cms.IzdDbOwner.to.lv.bank.cards.core.entity.linkApp.PcdDbOwner",
        query = "select d from IzdDbOwner d where d.ctime between :lastWaterMark and :currentWaterMark")
public class IzdDbOwner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "sh_name", length = 25, nullable = false)
    private String shName;

    @Column(name = "f_name", length = 40)
    private String FName;

    @Column(name = "client_seq1", length = 30)
    private String clientSeq1;

    @Column(name = "client_seq2", length = 30)
    private String clientSeq2;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "chip_cmd_seq1", length = 30)
    private String chipCmdSeq1;

    @Column(name = "f250_okato", length = 20)
    private String f250Okato;

    @Column(name = "f250_okpo", length = 20)
    private String f250Okpo;

    @Column(name = "f250_regnum", length = 20)
    private String f250Regnum;

    @Column(name = "f250_bik", length = 20)
    private String f250Bik;

    @Column(name = "f250_orgname", length = 255)
    private String f250Orgname;

    @Column(name = "f250_orgpostal", length = 255)
    private String f250Orgpostal;

    @Column(name = "f250_7_1", length = 255)
    private String f25071;

    @Column(name = "f250_7_2", length = 255)
    private String f25072;

    @Column(name = "f250_8", length = 255)
    private String f2508;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "izdDbOwner", fetch = FetchType.LAZY)
    private Set<IzdCardGroup> izdCardGroups;

}

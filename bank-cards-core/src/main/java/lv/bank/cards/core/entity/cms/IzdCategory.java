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
@Table(name = "izd_categories")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
public class IzdCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IzdCategoryPK comp_id;

    @Column(name = "cat_name", length = 20)
    private String catName;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "in_path", length = 256)
    private String inPath;

    @Column(name = "out_path", length = 256)
    private String outPath;

    @Column(name = "encrypt_key", length = 40)
    private String encryptKey;

    @Column(name = "e_mails", length = 100)
    private String EMails;

    @Column(name = "fax_no", length = 15)
    private String faxNo;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c"),
            @JoinColumn(name = "cln_cat", insertable = false, updatable = false, referencedColumnName = "cat_code")
    })
    private Set<IzdClient> izdClients;

}

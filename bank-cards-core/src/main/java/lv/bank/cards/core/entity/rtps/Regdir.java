package lv.bank.cards.core.entity.rtps;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "RTPSADMIN.regdir")
public class Regdir implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "reg_id", precision = 11, scale = 0, nullable = false)
    private Long regId;

    @Column(name = "reg_name", length = 300)
    private String regName;

    @Column(name = "reg_type_id", length = 1)
    private String regTypeId;

    @Column(name = "read_only", length = 1)
    private String readOnly;

    @Column(name = "reg_value_type_id", length = 1)
    private String regValueTypeId;

    @Column(name = "reg_value", length = 300)
    private String regValue;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "reg_parent_id", referencedColumnName = "reg_id")
    private Regdir regdir;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "regdir", fetch = FetchType.LAZY)
    private Set<Regdir> regdirs = new HashSet<Regdir>(0);

}

package lv.bank.cards.core.entity.linkApp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name = "pcd_currencies")
public class PcdCurrency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "iso_alpha", nullable = false, length = 3)
    private String isoAlpha;

    @Column(name = "iso_num", nullable = false, length = 3)
    private String isoNum;

    @Column(name = "exp", length = 11, nullable = false)
    private String exp;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pcdCurrency", fetch = FetchType.LAZY)
    private Set<PcdAccParam> pcdAccParams;

}

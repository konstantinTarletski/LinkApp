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
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "izd_cards_add_fields")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
public class IzdCardsAddFields implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "card", length = 19, nullable = false)
    private String card;

    @Column(name = "u_afield1", length = 200)
    private String uAField1;

    @Column(name = "u_afield2", length = 200)
    private String uAField2;

    @Column(name = "u_afield3", length = 200)
    private String uAField3;

    @Column(name = "u_afield4", length = 200)
    private String uAField4;

    @Column(name = "u_afield5", length = 200)
    private String uAField5;

    @Column(name = "u_afield6", length = 200)
    private String uAField6;

    @Column(name = "u_afield7", length = 200)
    private String uAField7;

    @Column(name = "u_bfield1", length = 400)
    private String uBField1;

    @Column(name = "u_acode11", length = 3)
    private String uACode11;

    @Column(name = "u_acode12", length = 3)
    private String uACode12;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "usrid", length = 6, nullable = false)
    private String usrid;

    @Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

    @Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "izdCardsAddFields")
    private IzdCard izdCard;

}

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nordlb_file_ids")
@FilterDef(name = "bankFilter", defaultCondition = ":bankC = BANK_C", parameters = @ParamDef(name = "bankC", type = "string"))
public class NordlbFileId implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
    private NordlbFileIdPK comp_id;

	@Column(name = "id", nullable = false, precision = 22, scale = 0)
    private BigDecimal id;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "groupc", insertable = false, updatable = false, referencedColumnName = "groupc"),
		@JoinColumn(name = "bank_c", insertable = false, updatable = false, referencedColumnName = "bank_c")
	})
    private IzdCardGroup izdCardGroup;

}

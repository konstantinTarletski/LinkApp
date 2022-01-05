package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "pcd_accumulator_types")
public class PcdAccumulatorType implements Serializable {

	private static final long serialVersionUID = 1690132993394839557L;

	@Id
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "description", nullable = false, length = 255)
	private String description;
	
	@Column(name = "mandatory", nullable = false, length = 1)
	private String mandatory;

}

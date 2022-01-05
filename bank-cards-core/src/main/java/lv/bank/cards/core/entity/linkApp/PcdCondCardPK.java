package lv.bank.cards.core.entity.linkApp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PcdCondCardPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "cond_set", length = 3, nullable = false)
    private String condSet;

	@Column(name = "ccy", length = 3, nullable = false)
    private String ccy;

	@Column(name = "bank_c", length = 2, nullable = false)
    private String bankC;

	@Column(name = "groupc", length = 2, nullable = false)
    private String groupc;

}

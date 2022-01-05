package lv.bank.cards.core.entity.linkApp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;


@Data
@Entity
@Table(name = "pcd_auth_batches")
@SequenceGenerator(name="pcdAuthBatchSeq",sequenceName="pcd_auth_batch_id", allocationSize = 1)
public class PcdAuthBatch  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "batch_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = "pcdAuthBatchSeq")
	private Long batchId;

	@Column(name = "datetime")
   	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;
	
	@Column(precision = 22, scale = 0)
	private Long fld074;
	
	@Column(precision = 22, scale = 0)
	private Long fld075;
	
	@Column(precision = 22, scale = 0)
	private Long fld076;
	
	@Column(precision = 22, scale = 0)
	private Long fld077;
	
	@Column(precision = 22, scale = 0)
	private Long fld078;
	
	@Column(precision = 22, scale = 0)
	private Long fld079;
	
	@Column(precision = 22, scale = 0)
	private Long fld080;
	
	@Column(precision = 22, scale = 0)
	private Long fld081;
	
	@Column(precision = 22, scale = 0)
	private Long fld082;
	
	@Column(precision = 22, scale = 0)
	private Long fld083;
	
	@Column(precision = 22, scale = 0)
	private Long fld084;
	
	@Column(precision = 22, scale = 0)
	private Long fld085;
	
	@Column(precision = 22, scale = 0)
	private Long fld086;
	
	@Column(precision = 22, scale = 0)
	private Long fld087;
	
	@Column(precision = 22, scale = 0)
	private Long fld088;
	
	@Column(precision = 22, scale = 0)
	private Long fld089;
	
	@Column(precision = 22, scale = 0)
	private Long fld090;
	
	@Column(length = 1)
	private String fld097x;
	
	@Column(precision = 22, scale = 0)
	private Long fld097;
	
	@Column(name = "terminal_id", length = 24, nullable = false)
	private String terminalId;
	
	@Column(name = "merchant_id", length = 45)
	private String merchantId;
	
	@Column(name = "auth_id", precision = 22, scale = 0)
	private Long authId;

	//	@OneToOne(mappedBy = "pcdAuthBatch") ???
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Transient
	private PcdAuthPosIsoHostMessage pcdAuthPosIsoHostMessage;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "source_id")
	private PcdAuthSource pcdAuthSource;

}

package lv.bank.cards.core.entity.linkApp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "pcd_acc_params")
public class PcdAccParam implements Serializable {

	private static final long serialVersionUID = 3731313583228307269L;

	@Id
	@Column(name = "account_no", nullable = false)
    private BigDecimal accountNo;

	@Column(name = "status", nullable = false, length = 1)
    private String status;
	
	@Column(name = "u_field5", length = 20)
    private String ufield5;

    @Column(name = "crd", nullable = false)
	private long crd;

    @Column(name = "ctime", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "stop_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date stopDate;

    @Column(name = "created_date", nullable = false)
   	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

    @Column(name = "cond_set", length = 3)
	private String condSet;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "account_no", nullable = false)
    private PcdAccount pcdAccount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "ccy_iso_alpha", nullable  = false)
    private PcdCurrency pcdCurrency;

}

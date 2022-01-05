package lv.bank.cards.core.entity.linkApp;

import java.io.Serializable;
import java.util.Date;

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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "PCD_AUTH_POS_ISO_HOST_MESSAGES")
@SequenceGenerator(name="pcdAuthSeq",sequenceName="pcd_auth_id", allocationSize = 1)
public class PcdAuthPosIsoHostMessage implements Serializable {

	private static final long serialVersionUID = -2571146439267718576L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = "pcdAuthSeq")
    private Long id;

	@Column(name = "to_rtps", length = 1024)
    private String toRtps;

	@Column(name = "to_clnt", length = 1024)
    private String toClnt;

    @Column(name = "datetime")
   	@Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    
    @Column(name = "fld002", length = 19)
    private String fld002;
    
    @Column(name = "fld003", length = 6)
    private String fld003;
 
    @Column(name = "fld004")
    private Long fld004;
    
    @Column(name = "fld011", length = 6)
    private String fld011; 
    
    @Column(name = "fld012", length = 12)
    private String fld012; 
    
    @Column(name = "fld014", length = 4)
    private String fld014;
    
    @Column(name = "fld022", length = 12)
    private String fld022; 
     
//    private Long fld006;
    
    @Column(name = "fld032", length = 11)
    private String fld032;
    
    @Column(name = "fld037", length = 12)
    private String fld037; 
    
    @Column(name = "fld038", length = 6)
    private String fld038;
    
    @Column(name = "fld039", length = 3)
    private String fld039;
    
    @Column(name = "fld041", length = 8)
    private String fld041; 
    
    @Column(name = "fld042", length = 15)
    private String fld042; 
    
    @Column(name = "mti", length = 4)
    private String mti;
    
    @Column(name = "resp_mti", length = 4)
    private String respMti;
    
    @Column(name = "original_msg_id")
    private Long originalMsgId;
    
    @Column(name = "reversal")
    private Long reversalMsgId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "source_id")
    private PcdAuthSource source;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "batch_id")
    private PcdAuthBatch pcdAuthBatch;

}

package lv.bank.cards.core.entity.rtps;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "RTPSADMIN.processing_entities")
public class ProcessingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "centre_id", nullable = false)
    private String centreId;

    @Column(name = "subcentre_flag", length = 1)
    private String subcentreFlag;

    @Column(name = "locality_flag", length = 1)
    private String localityFlag;

    @Column(name = "priority", length = 1)
    private String priority;

    @Column(name = "enable_flag", length = 1)
    private String enableFlag;

    @Column(name = "online_flag", length = 1)
    private String onlineFlag;

    @Column(name = "offline_flag", length = 1)
    private String offlineFlag;

    @Column(name = "centre_service", length = 15)
    private String centreService;

    @Column(name = "centre_address", length = 30)
    private String centreAddress;

    @Column(name = "stip_flag", length = 1)
    private String stipFlag;

    @Column(name = "prevalid_flag", length = 1)
    private String prevalidFlag;

    @Column(name = "advice_flag", length = 1)
    private String adviceFlag;

    @Column(name = "authdat_path", length = 256, unique = true)
    private String authdatPath;

    @Column(name = "capture_flag", length = 1)
    private String captureFlag;

    @Column(name = "process_flag", length = 1)
    private String processFlag;

    @Column(name = "acqdat_flag", length = 1)
    private String acqdatFlag;

    @Column(name = "acqdat_path", length = 256, unique = true)
    private String acqdatPath;

    @Column(name = "issdat_flag", length = 1)
    private String issdatFlag;

    @Column(name = "issdat_path", length = 256, unique = true)
    private String issdatPath;

    @Column(name = "stop_to_flag", length = 1)
    private String stopToFlag;

    @Column(name = "hot_stop_flag", length = 1)
    private String hotStopFlag;

    @Column(name = "soft_stop_flag", length = 1)
    private String softStopFlag;

    @Column(name = "stop_to_path", length = 256, unique = true)
    private String stopToPath;

    @Column(name = "stop_fr_flag", length = 1)
    private String stopFrFlag;

    @Column(name = "stop_fr_path", length = 256, unique = true)
    private String stopFrPath;

    @Column(name = "mac_flag", length = 1)
    private String macFlag;

    @Column(name = "mac_key", length = 256)
    private String macKey;

    @Column(name = "zpk_flag", length = 1)
    private String zpkFlag;

    @Column(name = "zpk_key", length = 256)
    private String zpkKey;

    @Column(name = "dcr_flag", length = 1)
    private String dcrFlag;

    @Column(name = "dcr_key", length = 256)
    private String dcrKey;

    @Column(name = "fcr_flag", length = 1)
    private String fcrFlag;

    @Column(name = "fcr_key", length = 256)
    private String fcrKey;

    @Column(name = "security_info", length = 256)
    private String securityInfo;

    @Column(name = "abbreviature", length = 20)
    private String abbreviature;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "routing_key", length = 16)
    private String routingKey;

    @Column(name = "iss_bank", length = 2)
    private String issBank;

    @Column(name = "params_type", length = 1)
    private String paramsType;

    @Column(name = "stip_substit_svc", length = 15)
    private String stipSubstitSvc;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "processingEntity", fetch = FetchType.LAZY)
    private Set<StipClient> stipClients;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "processingEntity", fetch = FetchType.LAZY)
    private Set<StipStoplist> stipStoplists;

}

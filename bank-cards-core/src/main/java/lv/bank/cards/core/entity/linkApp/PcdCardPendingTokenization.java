package lv.bank.cards.core.entity.linkApp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "pcd_cards_pending_tokenization")
public class PcdCardPendingTokenization implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PcdCardPendingTokenizationPK comp_id;

    @Column(name = "token_pan", length = 19)
    private String tokenPan;

    @Column(name = "bank_app_device_id", length = 64)
    private String bankAppDeviceId;

    @Column(name = "bank_app_push_id", length = 64)
    private String bankAppPushId;

    @Column(name = "device_type", length = 20)
    private String deviceType;

    @Column(name = "source", length = 20)
    private String source;

    @Column(name = "corr_id", length = 15)
    private String corrId;

    @Column(name = "rec_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date recDate;

    @Column(name = "ctime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctime;

    @Column(name = "token_ref_id", length = 64)
    private String tokenRefId;

    @Column(name = "wallet_account_id", length = 64)
    private String walletAccountId;

}

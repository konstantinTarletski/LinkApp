package lv.bank.cards.dbsynchronizer.config.lv;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LV_PROFILE;

@Getter
@Profile(COUNTRY_LV_PROFILE)
@Configuration
public class ApplicationPropertiesLV {

    @Value("${app.config.clean-period.pcd-auth-batches.days}")
    protected Integer pcdAuthBatchesCleanPeriodDays;

    @Value("${app.config.clean-period.pcd-auth-pos-iso-host-messages.days}")
    protected Integer pcdAuthPosIsoHostMessagesCleanPeriodDays;

    @Value("${app.config.clean-period.pcd-atm-adverts.days}")
    protected Integer pcdAtmAdvertsCleanPeriodDays;

    @Value("${app.config.clean-period.sms-reconciliation.days}")
    protected Integer smsReconciliationCleanPeriodDays;


}

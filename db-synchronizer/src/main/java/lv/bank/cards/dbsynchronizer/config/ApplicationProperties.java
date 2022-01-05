package lv.bank.cards.dbsynchronizer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApplicationProperties {

    @Value("${app.config.nordlb-bankc}")
    protected String nordlbBankc;

    @Value("${app.config.bankc}")
    protected String bankc;

    @Value("${app.config.groupc}")
    protected String groupc;

    @Value("${app.config.schema-name}")
    protected String schemaName;

    @Value("${app.config.wsdl-location}")
    protected String wsdlLocation;

    @Value("${app.config.add-card-to-stop-on-update}")
    protected boolean addCardToStopOnUpdate;

    @Value("${app.config.clean-period.pcd-cardspending-tokenization.days}")
    protected Integer pcdCardspendingTokenizationTokenCleanPeriodDays;

    @Value("${app.config.clean-period.pcd-cardspending-tokenization-token-ref-id.minutes}")
    protected Integer pcdCardspendingTokenizationTokenRefIdCleanPeriodMinutes;

}

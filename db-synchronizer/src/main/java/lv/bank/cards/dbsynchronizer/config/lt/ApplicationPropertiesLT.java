package lv.bank.cards.dbsynchronizer.config.lt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LT_PROFILE;
import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LV_PROFILE;

@Getter
@Profile(COUNTRY_LT_PROFILE)
@Configuration
public class ApplicationPropertiesLT {



}

package lv.bank.cards.core.utils.lv;

import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.DeliveryDetailsHelperBase;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DeliveryDetailsHelper extends DeliveryDetailsHelperBase {

    public DeliveryDetailsHelper(String language, String country, String region, String city, String address,
                                 String zipCode, String additionalFields) {
        super();
        this.language = StringUtils.substring(language, 0, 1);
        this.country = StringUtils.upperCase(StringUtils.substring(country, 0, 2));
        this.region = StringUtils.substring(region, 0, 37);
        this.city = StringUtils.substring(city, 0, 40);
        this.address = StringUtils.substring(address, 0, 50);
        this.zipCode = StringUtils.substring(zipCode, 0, 16);
        this.additionalFields = StringUtils.substring(additionalFields, 0, 50);
    }

    public DeliveryDetailsHelper(String details) {
        language = StringUtils.trimToEmpty(StringUtils.substring(details, 0, 1));
        country = StringUtils.trimToEmpty(StringUtils.substring(details, 1, 3));
        region = StringUtils.trimToEmpty(StringUtils.substring(details, 3, 40));
        city = StringUtils.trimToEmpty(StringUtils.substring(details, 40, 80));
        address = StringUtils.trimToEmpty(StringUtils.substring(details, 80, 130));
        zipCode = StringUtils.trimToEmpty(StringUtils.substring(details, 130, 146));
        additionalFields = StringUtils.trimToEmpty(StringUtils.substring(details, 146, 196));
    }

    @Override
    public String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(language), 1, " "));
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(country), 2, " "));
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(region), 37, " "));
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(city), 40, " "));
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(address), 50, " "));
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(zipCode), 16, " "));
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(additionalFields), 50, " "));
        return details.toString();
    }

    @Override
    public void checkData() throws DataIntegrityException {
        List<String> missing = new ArrayList<>();
        if (StringUtils.isBlank(country)) {
            missing.add("country");
        }
        if (StringUtils.isBlank(region)) {
            missing.add("region");
        }
        if (StringUtils.isBlank(address)) {
            missing.add("street address");
        }
        if (StringUtils.isBlank(zipCode)) {
            missing.add("zip code");
        }
        if (!missing.isEmpty()) {
            StringBuilder missingFields = new StringBuilder();
            for (String value : missing) {
                if (missingFields.length() > 0)
                    missingFields.append(", ");
                missingFields.append(value);
            }
            throw new DataIntegrityException("Missing value for delivery address " + missingFields);
        }
        if (StringUtils.isBlank(language)) {
            throw new DataIntegrityException("Missing value for delivery address language");
        }
    }

    public String checkLanguage() {
        if (StringUtils.trimToEmpty(language).length() > 1)
            return "Language value [" + StringUtils.trimToEmpty(language) + "] exceeds max length (1)";
        return "";
    }

    public String checkCountry() {
        if (StringUtils.trimToEmpty(country).length() > 2)
            return "Country value [" + StringUtils.trimToEmpty(country) + "] exceeds max length (2)";
        return "";
    }

    public String checkRegion() {
        if (StringUtils.trimToEmpty(region).length() > 37)
            return "Region value [" + StringUtils.trimToEmpty(region) + "] exceeds max length (37)";
        return "";
    }

    public String checkCity() {
        if (StringUtils.trimToEmpty(city).length() > 40)
            return "City value [" + StringUtils.trimToEmpty(city) + "] exceeds max length (40)";
        return "";
    }

    public String checkAddress() {
        if (StringUtils.trimToEmpty(address).length() > 50)
            return "Address value [" + StringUtils.trimToEmpty(address) + "] exceeds max length (50)";
        return "";
    }

    public String checkZipCode() {
        if (StringUtils.trimToEmpty(zipCode).length() > 16)
            return "ZipCode value [" + StringUtils.trimToEmpty(zipCode) + "] exceeds max length (16)";
        return "";
    }

}

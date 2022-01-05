package lv.bank.cards.core.utils.lt;

import lombok.Data;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.DeliveryDetailsHelperBase;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeliveryDetailsHelper extends DeliveryDetailsHelperBase {

    public DeliveryDetailsHelper(String country, String region, String city, String address, String zipCode,
                                 String additionalFields, String addressCode) {
        super();
        //this.language = StringUtils.substring(language, 0, 1);
        this.country = StringUtils.upperCase(StringUtils.substring(country, 0, 2));
        this.region = StringUtils.substring(region, 0, 37);
        this.city = StringUtils.substring(city, 0, 40);
        this.address = StringUtils.substring(address, 0, 80);
        this.zipCode = StringUtils.substring(zipCode, 0, 16);
        this.additionalFields = StringUtils.substring(additionalFields, 0, 50);
        this.addressCode = StringUtils.substring(addressCode, 0, 24);
    }

    public DeliveryDetailsHelper(String details) {
        //this.language = StringUtils.substring(language, 0, 1);
        country = StringUtils.trimToEmpty(StringUtils.substring(details, 1, 3));
        region = StringUtils.trimToEmpty(StringUtils.substring(details, 3, 40));
        city = StringUtils.trimToEmpty(StringUtils.substring(details, 40, 80));
        address = StringUtils.trimToEmpty(StringUtils.substring(details, 80, 160));
        zipCode = StringUtils.trimToEmpty(StringUtils.substring(details, 160, 176));
        additionalFields = StringUtils.trimToEmpty(StringUtils.substring(details, 176, 226));
        addressCode = StringUtils.trimToEmpty(StringUtils.substring(details, 226, 250));
    }

    @Override
    public String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(""), 1, " ")); // This is place for language
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(country), 2, " "));
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(region), 37, " "));
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(city), 40, " "));
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(address), 80, " "));
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(zipCode), 16, " "));
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(additionalFields), 50, " "));
        details.append(StringUtils.rightPad(StringUtils.trimToEmpty(addressCode), 24, " "));
        return details.toString();
    }

    @Override
    public void checkData() throws DataIntegrityException {
        if (StringUtils.isBlank(addressCode)) {
            throw new DataIntegrityException("Missing value for delivery address");
        }
    }

    public String getAddressForCardOverview() {
        List<String> addressFields = new ArrayList<>();
        if (!StringUtils.isBlank(address)) {
            addressFields.add(address);
        }
        if (!StringUtils.isBlank(city)) {
            addressFields.add(city);
        }
        if (!StringUtils.isBlank(region)) {
            addressFields.add(region);
        }
        if (!StringUtils.isBlank(country)) {
            addressFields.add(country);
        }
        if (!StringUtils.isBlank(zipCode)) {
            addressFields.add(zipCode);
        }
        if (!StringUtils.isBlank(additionalFields)) {
            addressFields.add(additionalFields);
        }
        return StringUtils.trimToEmpty(StringUtils.join(addressFields, ", "));
    }

}

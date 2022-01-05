package lv.bank.cards.core.utils;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public abstract class DeliveryDetailsHelperBase {

    protected String language;
    protected String country;
    protected String region;
    protected String city;
    protected String address;
    protected String zipCode;
    protected String additionalFields;
    protected String addressCode;

    public abstract String getDetails();

    public abstract void checkData() throws DataIntegrityException;

    public String getAddressString() {
        StringBuilder addressString = new StringBuilder();
        if (!StringUtils.isBlank(region)) {
            addressString.append(region);
        }
        if (!StringUtils.isBlank(city) && !city.equals(region)) {
            if (addressString.length() > 0) {
                addressString.append(", ");
            }
            addressString.append(city);
        }
        if (!StringUtils.isBlank(address)) {
            if (addressString.length() > 0) {
                addressString.append(", ");
            }
            addressString.append(address);
        }
        if (!StringUtils.isBlank(zipCode)) {
            if (addressString.length() > 0) {
                addressString.append(", ");
            }
            addressString.append(zipCode);
        }
        return addressString.toString();
    }

}

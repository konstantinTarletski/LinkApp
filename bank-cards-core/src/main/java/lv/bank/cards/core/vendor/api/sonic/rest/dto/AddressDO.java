package lv.bank.cards.core.vendor.api.sonic.rest.dto;

import lombok.Data;

@Data
public class AddressDO {

    private String name;
    private String line1;
    private String line2;
    private String line3;
    private String city;
    private String state;
    private String countryCode;
    private String postalCode;

}

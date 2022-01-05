package lv.bank.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDO {

    @ApiModelProperty(value = "Transaction ID", required = true, example = "InOfDOzcy2EeUotPmbB6")
    private String id;

    @ApiModelProperty(value = "Date when transaction was made", required = true, example = "2018-06-19T11:26:40")
    private String date;

    @ApiModelProperty(value = "Card PAN", required = true, example = "477573******8922")
    private String pan;

    @ApiModelProperty(value = "Card ID", required = true, example = "4088830110000970")
    private String cardId;

    @ApiModelProperty(value = "Amount in original currency", required = true, example = "71.98")
    private BigDecimal originAmount;

    @ApiModelProperty(value = "Transaction origin currency", required = true, example = "USD")
    private String originCurrency;

    @ApiModelProperty(value = "Amount in card currency", required = true, example = "60.80")
    private BigDecimal billingAmount;

    @ApiModelProperty(value = "Transaction billing currency", required = true, example = "EUR")
    private String billingCurrency;

    @ApiModelProperty(value = "Currency rate", required = true, example = "1.16")
    private BigDecimal rate;

    @ApiModelProperty(value = "Is transaction in authorized or cleared status", required = true)
    private ETransactionStatus transactionStatus;

    @ApiModelProperty(value = "Shop name", example = "The best shop in the USA")
    private String shopName;

    @ApiModelProperty(value = "Shop address", example = "829 Gainsway Street Murfreesboro, TN 37128")
    private String shopAddress;

    @ApiModelProperty(value = "Shop country", example = "USA")
    private String shopCountry;

    public ReservationDO calculateRate() {
        if (originAmount != null && billingAmount != null) {
            this.rate = originAmount.divide(billingAmount, 10, BigDecimal.ROUND_HALF_UP);
        }
        return this;
    }

}

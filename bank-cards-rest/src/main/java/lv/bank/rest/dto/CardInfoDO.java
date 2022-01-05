package lv.bank.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardInfoDO {

    @ApiModelProperty
    protected String id;

    @ApiModelProperty
    protected EProduct product;

    @ApiModelProperty
    protected ECardType type;

    @ApiModelProperty
    protected ECardStatus cardStatus;

    @ApiModelProperty
    protected String holderName;

    @ApiModelProperty
    protected String personalCode;

    @ApiModelProperty
    protected String pan;

    @ApiModelProperty
    protected String expirationDate;

    @ApiModelProperty
    protected String companyName;

    @ApiModelProperty
    private String pinStatus;

    @JsonProperty(value="isCardholder")
    @ApiModelProperty
    protected boolean isCardholder = false;

    @ApiModelProperty
    protected String coreAccountNo;

    @ApiModelProperty
    protected String designId;

    @ApiModelProperty
    protected String branch;

    @ApiModelProperty
    protected ECommerceStatus ecom;

    @ApiModelProperty
    protected EContactlessStatus contactless;

    @ApiModelProperty
    protected EActivationStatus activationStatus;

    @ApiModelProperty
    protected boolean autoRenewal;

    /**
     * @param customerId person code of account owner used to verify if card holder is the same person
     */
    public CardInfoDO setIsCardholder(String consumerId) {
        if (this.personalCode != null && consumerId != null && consumerId.equals(this.personalCode)) {
            this.isCardholder = true;
        }
        return this;
    }


}

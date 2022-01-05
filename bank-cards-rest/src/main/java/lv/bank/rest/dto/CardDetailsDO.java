package lv.bank.rest.dto;

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
public class CardDetailsDO {

    @ApiModelProperty
    private String id;

    @ApiModelProperty
    private EProduct product;

    @ApiModelProperty
    private ECardType type;

    @ApiModelProperty
    private ECardStatus cardStatus;

    @ApiModelProperty
    private String holderName;

    @ApiModelProperty
    private String pan;

    @ApiModelProperty
    private String expirationDate;

    @ApiModelProperty
    private EActivationStatus activationStatus;

    @ApiModelProperty
    private ECommerceStatus ecom;

    @ApiModelProperty
    private EContactlessStatus contactless;

    @ApiModelProperty
    private String designId;

    @ApiModelProperty
    private String personalCode;

    @ApiModelProperty
    private String branch;

    @ApiModelProperty
    private String pinStatus;

    @ApiModelProperty
    protected boolean isCardholder = false;

    @ApiModelProperty
    private String companyName;

    @ApiModelProperty
    private String coreAccountNo;

    @ApiModelProperty
    private boolean autoRenewal;

    @ApiModelProperty
    private ESpecialClientCategory specialClientCategory;

    /**
     * @param customerId person code of account owner used to verify if card holder is the same person
     */
    public void setIsCardholder(String customerId) {
        if (this.personalCode != null && customerId != null && customerId.equals(this.personalCode)) {
            this.isCardholder = true;
        }
    }
}

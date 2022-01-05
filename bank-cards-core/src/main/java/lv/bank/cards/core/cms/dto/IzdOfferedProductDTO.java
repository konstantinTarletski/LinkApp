package lv.bank.cards.core.cms.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class IzdOfferedProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bankC;
    private String groupc;
    private String code;
    private String authLevel;
    private String name;
    private String cardType;
    private Integer branch;
    private String bin;
    private String clnCat;
    private String clType;
    private String company;
    private String repDistribution;
    private String ccy;

}

package lv.bank.cards.core.linkApp.dto;

import java.util.Date;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class CardInfoDTO {

	private String prefixDesc;
	private String card;
	private String embossingName;
	private String cardName;
	private Date expiryDate;
	private Date expiryDate2;
	private String cvc;
	private String cif;
	private String billingCurrency;
	private String accountNumber;
	private String groupc;
	private Long endBal;
	private String clientPersonCode;
	private String personCode;
	private String cardStatus1;
	private String design;
	private String uAField2;
	private String uBField1;
	private String uCod10;
	private String pinBlock;
	private String password;
	private String distribMode;
	private String dStreet;
	private String dCity;
	private String dCountry;
	private String dPostInd;
	private String dBranch;
	private String uField8;
	private Integer contactless;
	private String renew;
	private String language;
	private String smsFee;

	public String getStripedUCod10(){
		String striped = StringUtils.stripStart(uCod10, "0");
		return (StringUtils.isBlank(striped) && !StringUtils.isBlank(uCod10)) ? "0" : striped;
	}

	public String getStripedDBranch(){
		String striped = StringUtils.stripStart(dBranch, "0");
		return (StringUtils.isBlank(striped) && !StringUtils.isBlank(dBranch)) ? "0" : striped;
	}

}

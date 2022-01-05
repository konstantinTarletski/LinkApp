package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import lv.bank.cards.core.utils.Constants;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.bank.cards.core.utils.LinkAppProperties;;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

public class GetCardsByPersonalCode extends SubRequestHandler{
	private PcdabaNGManager pcdabaNGManager = null;
	String expiryDateFormat = "yyMM";

	public GetCardsByPersonalCode() throws RequestPreparationException {
		super();	
		try {
			pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
		} catch (NamingException e) {
			throw new RequestPreparationException(e, this);
		}
	}

	@Override
	public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
		super.handle(r);
		List<String> result;
		String personalCode=getStringFromNode("/do/personal-code");
		String country = getStringFromNode("/do/country");
		if(country == null || country.isEmpty()) {
			country = Constants.DEFAULT_COUNTRY_LV;
		}
		if (personalCode == null || personalCode.equals("")) throw new RequestFormatException("Specify personal code", this);
		result =  pcdabaNGManager.getCardsByPersonalCode(personalCode, country);
		ResponseElement response = createElement("get-cards-by-personal-code");

		for (String card : result) {
			response.createElement("card", card);
		}
	}

}


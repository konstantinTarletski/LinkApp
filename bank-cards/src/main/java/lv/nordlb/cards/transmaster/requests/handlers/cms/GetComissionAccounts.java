package lv.nordlb.cards.transmaster.requests.handlers.cms;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import lv.bank.cards.core.entity.cms.IzdPreProcessingRow;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;

public class GetComissionAccounts extends SubRequestHandler{
	private CardManager cardManager = null;
	String dateTimeFormat = "ddMMyyyy";
	
	/**
	 * @throws RequestPreparationException 
	 * 
	 */
	public GetComissionAccounts() throws RequestPreparationException {
		super();	
		try {
			cardManager=(CardManager) new InitialContext().lookup(CardManager.JNDI_NAME);
		} catch (NamingException e) {
			throw new RequestPreparationException(e, this);
		}
	}
	
	@Override
	public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
		super.handle(r);
		;
	
		String limit=getStringFromNode("/do/limit");
		int lim;
		if (limit==null)	{
			lim=10;
		}else{
			lim=Integer.parseInt(limit);
		}
		
		List<IzdPreProcessingRow> c = cardManager.getIzdPreProcessingRowsByAccLimit(lim);
		ResponseElement response = createElement("get-comission-accounts");
		for (IzdPreProcessingRow result : c){
			response.createElement("account", result.getAccountNo().toPlainString());
		}
	}

}
